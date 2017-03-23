/*
 * Copyright [2017] [$author]
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mcxiao.ipmsg;

import com.github.mcxiao.ipmsg.IPMsgException.NoResponseException;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 */
public class SimpleSynchronizationPoint<E extends Exception> {

    private static final int STATE_INITIAL          = 0;
    private static final int STATE_REQUEST_SENT     = 1;
    private static final int STATE_NO_RESPONSE      = 2;
    private static final int STATE_SUCCESS          = 3;
    private static final int STATE_FAILURE          = 4;

    private final AbstractConnection connection;
    private final Lock connectionLock;
    private final Condition condition;
    private final String waitFor;

    // Note that there is no need to make 'state' and 'failureException' volatile.
    // Since 'lock' and 'unlock' have the same memory synchronization effects
    // as synchronization block enter and leave.(Note by Smack.SynchronizationPoint)
    private int state;
    private E failureException;

    public SimpleSynchronizationPoint(AbstractConnection connection, String waitFor) {
        this.connection = connection;
        this.connectionLock = connection.getConnectionLock();
        this.condition = connectionLock.newCondition();
        this.waitFor = waitFor;
        init();
    }

    public void init() {
        connectionLock.lock();
        state = STATE_INITIAL;
        failureException = null;
        connectionLock.unlock();
    }

    public E checkIfSuccessOrWait() throws NoResponseException, InterruptedException {
        connectionLock.lock();
        try {
            switch (state) {
                case STATE_SUCCESS:
                    return null;
                case STATE_FAILURE:
                    return failureException;
                default:
                    break;
            }
            waitForConditionOrTimeOut();
        } finally {
            connectionLock.unlock();
        }
        return checkForResponse();
    }

    public E checkForResponse() throws NoResponseException {
        switch (state) {
            case STATE_INITIAL:
            case STATE_NO_RESPONSE:
            case STATE_REQUEST_SENT:
                throw new NoResponseException("No response received within timeout, while wait for " + waitFor);
            case STATE_SUCCESS:
                return null;
            case STATE_FAILURE:
                return failureException;
            default:
                throw new AssertionError("Unknown state " + state);
        }
    }

    /**
     * Caution: change the method implement.
     */
    public void waitForConditionOrTimeOut() throws InterruptedException {
        long wait = TimeUnit.MILLISECONDS.toNanos(connection.getPacketReplyTimeout());
        while (state == STATE_REQUEST_SENT || state == STATE_INITIAL) {
            if (wait <= 0) {
                state = STATE_NO_RESPONSE;
                break;
            }

            wait = condition.awaitNanos(wait);
        }
    }

    public void reportSuccess() {
        connectionLock.lock();
        try {
            state = STATE_SUCCESS;
            condition.signalAll();
        } finally {
            connectionLock.unlock();
        }
    }
    
    public void reportFailure(E failureException) {
        assert failureException != null;
        
        try {
            state = STATE_FAILURE;
            this.failureException = failureException;
            condition.signalAll();
        } finally {
            connectionLock.unlock();
        }
    }

}
