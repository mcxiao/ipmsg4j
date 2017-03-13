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

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 */
public final class LogUtil {

    private static final Logger logger;

    static {
        logger = Logger.getLogger("LogUtil");
    }

    public static String createTag(Class clazz, @Nullable String resource) {
        String name = clazz.getName();
        return createTag(name, resource);
    }

    public static String createTag(String name, @Nullable String resource) {
        name += ":";
        name += resource;
        return name;
    }

    public static void err(String tag, String message, @Nullable Throwable e) {
        log(Level.SEVERE, tag, message, e);
    }

    public static void errWithParams(String tag, @NotNull String message, Object... objects) {
        logWithParams(Level.SEVERE, tag, message, objects);
    }

    public static void warn(String tag, String message, @Nullable Throwable e) {
        log(Level.WARNING, tag, message, e);
    }

    public static void warnWithParams(String tag, @NotNull String message, Object... objects) {
        logWithParams(Level.WARNING, tag, message, objects);
    }
    public static void info(String tag, String message, @Nullable Throwable e) {
        log(Level.INFO, tag, message, e);
    }

    public static void infoWithParams(String tag, @NotNull String message, Object... objects) {
        logWithParams(Level.INFO, tag, message, objects);
    }

    public static void fine(String tag, String message, @Nullable Throwable e) {
        log(Level.FINE, tag, message, e);
    }

    public static void fineWithParams(String tag, @NotNull String message, Object... objects) {
        logWithParams(Level.FINE, tag, message, objects);
    }

    private static void log(Level level, String tag, String message, Throwable e) {
        logger.log(level, buildTagAndMessage(tag, message), e);
    }

    private static void logWithParams(Level level, String tag, @NotNull String message, Object... objects) {
        String format = String.format(message, objects);
        log(level, tag, format, null);
    }

    private static String buildTagAndMessage(String tag, String message) {
        return tag + " - " + message;
    }
}
