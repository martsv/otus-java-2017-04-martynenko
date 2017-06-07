package ru.otus.hw9.model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ModelField {

    private String fieldName;
    private String dbFieldName;
    private Class clazz;
    private Method getMethod;
    private Method setMethod;

    public ModelField(String fieldName, String dbFieldName, Class clazz, Class<? extends DataSet> model) {
        this.fieldName = fieldName;
        this.dbFieldName = dbFieldName;
        this.clazz = clazz;
        setMethods(model);
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getDbFieldName() {
        return dbFieldName;
    }

    public Class getClazz() {
        return clazz;
    }

    public String getValue(Object object) {
        try {
            return (isQuoted() ? "'" : "") + getMethod.invoke(object).toString() + (isQuoted() ? "'" : "");
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public void setValue(Object object, Object value) {
        try {
            setMethod.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void setMethods(Class<? extends DataSet> model) {
        try {
            String capitalizedField = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            this.getMethod = model.getMethod("get" + capitalizedField, null);
            this.setMethod = model.getMethod("set" + capitalizedField, clazz);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isQuoted() {
        return clazz.equals(String.class);
    }

    @Override
    public String toString() {
        return "ModelField{" +
                "fieldName='" + fieldName + '\'' +
                ", dbFieldName='" + dbFieldName + '\'' +
                ", clazz=" + clazz +
                ", getMethod=" + getMethod +
                ", setMethod=" + setMethod +
                '}';
    }

}
