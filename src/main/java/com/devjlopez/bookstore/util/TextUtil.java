package com.devjlopez.bookstore.util;

/**
 *
 * @author jlopez
 */
public class TextUtil {
    public String sanitize(String text) {
        return text.replaceAll("\\s+", " ");
    }
}
