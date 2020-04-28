package com.anton.dyukarev.config;

import com.anton.dyukarev.config.enums.Property;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

    private static final Properties PROPERTIES;

    static {
        PROPERTIES = new Properties();
        InputStream inputStream = PropertyManager.class.getClassLoader().getResourceAsStream("allure.properties");
        try {
            PROPERTIES.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод возвращает значение указанного параметра
     *
     * @param property название параметра из файла .properies
     * @return String
     */
    public static String getPropertyValue(Property property) {
        return PROPERTIES.getProperty(property.getName());
    }
}
