package com.yy.stock.bot.helper;

public class MessageHelper {
    public static String getValueFromJsonMessage(String message, String key) {
        key = "\"" + key + "\"";
        int keyStartIndex = message.indexOf(key);
        if (keyStartIndex > 0) {
            try {
                int valueStartIndex = message.indexOf("\"", keyStartIndex + key.length() + 1);
                int valueEndIndex = message.indexOf("\"", keyStartIndex + key.length() + 2);
                String value = message.substring(valueStartIndex + 1, valueEndIndex);
                return value;
            } catch (Exception ex) {
                return null;
            }
        } else {
            return null;
        }
    }
}
