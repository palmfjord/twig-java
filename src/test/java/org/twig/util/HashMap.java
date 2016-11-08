package org.twig.util;

public class HashMap extends java.util.HashMap<String, Object> {
    /**
     * Put a value to the HashMap and return itself
     *
     * @param key The entity key
     * @param value The entity
     * @return
     */
    public java.util.HashMap<String, Object> put(String key, Object value) {
        super.put(key, value);

        return this;
    }
}
