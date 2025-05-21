package com.linhnt.taskmanagementservice.utils;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

public final class MessageUtil {
    private static final Locale DEFAULT_LOCALE = Locale.JAPAN;

    private static ResourceBundle resourceBundle;

    private MessageUtil() {
    }

    private static ResourceBundle getResourceBundle(Locale... locale) {
        if (resourceBundle == null) {
            resourceBundle = ResourceBundle.getBundle("messages",
                    locale.length > 0 ? locale[0] : DEFAULT_LOCALE, Thread.currentThread().getContextClassLoader());
        }
        return resourceBundle;
    }

    public static String getMessage(String errorCode) {
        return getResourceBundle().getString(errorCode);
    }

    public static String getMessage(Locale locale, String errorCode) {
        return getResourceBundle(locale).getString(errorCode);
    }

    public static String getMessage(String errorCode, String... params) {
        String message = getResourceBundle().getString(errorCode);
        return MessageFormat.format(message, (Object[]) params);
    }

    public static String getMessage(Locale locale, String errorCode, String... params) {
        String message = getResourceBundle(locale).getString(errorCode);
        return MessageFormat.format(message, (Object[]) params);
    }

    public static String getMessageWithParamCodes(String errorCode, String... paramCodes) {
        String[] params = Arrays.stream(paramCodes)
                .map(MessageUtil::getMessage)
                .toArray(String[]::new);
        return getMessage(errorCode, params);
    }

    public static String getMessageWithParamCodes(Locale locale, String errorCode, String... paramCodes) {
        String[] params = Arrays.stream(paramCodes)
                .map(paramCode -> getMessage(locale, paramCode))
                .toArray(String[]::new);
        return getMessage(locale, errorCode, params);
    }

    public static boolean containsKey(String key) {
        return getResourceBundle().containsKey(key);
    }
}
