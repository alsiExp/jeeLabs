package ru.cpsmi.jee;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Field;

public class InjectNetworkScanCountPostProcessor implements BeanPostProcessor {

    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        Field[] fields = o.getClass().getDeclaredFields();
        for (Field f: fields){
            InjectNetworkScanCount annotation = f.getAnnotation(InjectNetworkScanCount.class);
            if (annotation != null) {
                int min = annotation.min();
                int max = annotation.max();
                f.setAccessible(true);
                try {
                    f.set(o, max + min);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return o;
    }

    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return o;
    }
}
