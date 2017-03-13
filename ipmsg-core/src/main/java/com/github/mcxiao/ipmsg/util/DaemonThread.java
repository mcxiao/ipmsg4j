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

package com.github.mcxiao.ipmsg.util;

/**
 */
public class DaemonThread extends Thread {

    public DaemonThread() {
        super();
        setDaemon(true);
    }

    public DaemonThread(String name) {
        super(name);
        setDaemon(true);
    }

    public DaemonThread(Runnable target) {
        super(target);
        setDaemon(true);
    }

    public DaemonThread(Runnable target, String name) {
        super(target, name);
        setDaemon(true);
    }

}
