package ru.otus.hw9.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class Model {

    private String tableName;
    private Class<? extends DataSet> clazz;
    private ModelField idField;
    private List<ModelField> modelFields;

    private Model() {}

    public static Model loadModel(Class<? extends DataSet> klass) {
        Model model = new Model();

        if (!klass.isAnnotationPresent(Entity.class)) {
            throw new RuntimeException("Class should be annotated by Entity");
        }

        model.clazz = klass;
        if (klass.isAnnotationPresent(Table.class)) {
            Table table = klass.getAnnotation(Table.class);
            model.tableName = table.name();
        } else {
            model.tableName = klass.getName();
        }

        model.idField = getIdField(klass);
        model.modelFields = getModelFields(klass);

        return model;
    }

    public String getTableName() {
        return tableName;
    }

    public Class getClazz() {
        return clazz;
    }

    public ModelField getIdField() {
        return idField;
    }

    public List<ModelField> getModelFields() {
        return modelFields;
    }

    public <T extends DataSet> T getInstance() {
        T object;

        try {
            object = (T) clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            throw new RuntimeException(e);
        }

        return object;
    }

    private static ModelField getIdField(Class<? extends DataSet> klass) {
        for (Class<?> superclass : getSuperClasses(klass)) {
            for (Field field: superclass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Id.class)) {
                    return new ModelField(field.getName(), field.getName(), field.getType(), klass);
                }
            }
        }

        throw new RuntimeException("Model should have id field");
    }

    private static List<ModelField> getModelFields(Class<? extends DataSet> klass) {
        List<ModelField> modelFields = new ArrayList<>();

        for (Class<?> superclass : getSuperClasses(klass)) {
            for (Field field: superclass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    ModelField modelField = new ModelField(field.getName(), column.name(), field.getType(), klass);
                    modelFields.add(modelField);
                }
            }
        }

        return modelFields;
    }

    private static List<Class<?>> getSuperClasses(Class<?> klass) {
        List<Class<?>> classes = new ArrayList<>();
        Class<?> superClass = klass;
        while (superClass != null) {
            classes.add(superClass);
            superClass = superClass.getSuperclass();
        }
        return classes;
    }

    @Override
    public String toString() {
        return "Model{" +
                "tableName='" + tableName + '\'' +
                ", clazz=" + clazz +
                ", idField=" + idField +
                ", modelFields=" + modelFields +
                '}';
    }

}
