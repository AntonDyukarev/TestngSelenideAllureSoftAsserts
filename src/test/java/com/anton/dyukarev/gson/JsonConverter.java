package com.anton.dyukarev.gson;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Класс для конвертирования json файлов в структуру подготовленных классов, и обратно.
 *
 * @author Anton Dyukarev
 */
public class JsonConverter implements ParameterizedType {

    private final Class<?> ACTUAL_TYPE;
    private final Class<?> RAW_TYPE;

    public JsonConverter(Class<?> actualType, Class<?> rawType) {
        this.ACTUAL_TYPE = actualType;
        this.RAW_TYPE = rawType;
    }

    public JsonConverter(Class<?> actualType) {
        this.ACTUAL_TYPE = actualType;
        this.RAW_TYPE = null;
    }

    @Override
    public Type[] getActualTypeArguments() {
        return new Type[] {ACTUAL_TYPE};
    }

    @Override
    public Type getRawType() {
        if (Objects.nonNull(RAW_TYPE))
            return RAW_TYPE;
        else return ACTUAL_TYPE;
    }

    @Override
    public Type getOwnerType() {
        return null;
    }

    public <T> T get(Reader reader) {
        T t = new Gson().fromJson(reader, this);
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return t;
    }

    public <T> T get(String json) {
        return new Gson().fromJson(json, this);
    }

    public void saveJson(Object object, Writer writer) {
        new Gson().toJson(object, this, writer);
        try {
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
