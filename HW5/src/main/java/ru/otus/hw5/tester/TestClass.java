package ru.otus.hw5.tester;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * mart
 * 08.05.2017
 */
class TestClass {

    private static final double MILLIS_IN_SECOND = 1000.0;

    private Class<?> clazz;
    private boolean initialized = false;

    private List<Method> befores = new ArrayList<>();
    private List<Method> afters = new ArrayList<>();
    private List<Method> tests = new ArrayList<>();

    TestClass(Class<?> klass) {
        this.clazz = klass;
        this.initialized = initialize();
    }

    private boolean initialize() {
        try {
            Constructor<?>[] constructors = clazz.getDeclaredConstructors();
            checkConstructors(constructors);

            for (Class<?> superclass : getSuperClasses(clazz)) {
                Method[] methods = superclass.getDeclaredMethods();

                for (Method method : methods) {
                    Annotation[] annotations = method.getDeclaredAnnotations();

                    for (Annotation annotation : annotations) {
                        if (checkMethodAnnotation(method, annotation, Test.class)) {
                            tests.add(method);
                        } else if (checkMethodAnnotation(method, annotation, Before.class)) {
                            befores.add(0, method);
                        } else if (checkMethodAnnotation(method, annotation, After.class)) {
                            afters.add(method);
                        }
                    }
                }
            }
        } catch (InitializationException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    void run() {
        System.out.println("Running " + clazz.getName());
        long start = System.currentTimeMillis();
        int count = 0;
        int failures = 0;

        if (!initialized) {
            failures++;
        } else {
            for (Method test: tests) {
                count++;
                if (!runTest(test)) {
                    failures++;
                }
            }
        }

        long end = System.currentTimeMillis();
        System.out.println("Tests run: " + count
                + ", Failures: " + failures
                + ", Time elapsed: " + (end - start) / MILLIS_IN_SECOND + " sec");
    }

    private boolean runTest(Method test) {
        Object object = instantiate();

        if (object == null) {
            return false;
        }

        int errors = 0;
        for (Method before : befores) {
            if (!invoke(object, before)) {
                errors++;
            }
        }

        if (!invoke(object, test)) {
            errors++;
        }

        for (Method after : afters) {
            if (!invoke(object, after)) {
                errors++;
            }
        }

        return errors == 0;
    }

    private Object instantiate() {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean invoke(Object object, Method method) {
        try {
            method.invoke(object);
            return true;
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    private List<Class<?>> getSuperClasses(Class<?> klass) {
        List<Class<?>> classes = new ArrayList<>();
        Class<?> superClass = klass;
        while (superClass != null) {
            classes.add(superClass);
            superClass = superClass.getSuperclass();
        }
        return classes;
    }

    private void checkConstructors(Constructor<?>[] constructors) {
        if (constructors.length != 1
                || !isPublic(constructors[0])
                || constructors[0].getParameterCount() != 0) {
            throw new InitializationException("Test class should have only one public constructor without parameters");
        }
    }

    private boolean checkMethodAnnotation(Method method, Annotation annotation, Class<? extends Annotation> annotationClass) {
        if (annotation.annotationType().equals(annotationClass)) {
            if (!isPublic(method)) {
                throw new InitializationException(annotationClass.getSimpleName() + " method should be public");
            }
            return true;
        }
        return false;
    }

    private boolean isPublic(Method method) {
        return (method.getModifiers() & Modifier.PUBLIC) != 0;
    }

    private boolean isPublic(Constructor<?> constructor) {
        return (constructor.getModifiers() & Modifier.PUBLIC) != 0;
    }

}
