package ru.cpsmi.jee;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        try(ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("app-config.xml")) {
            String[] beanNames = appCtx.getBeanDefinitionNames();
            for(String s : beanNames) {
                System.out.println(s);
            }
            appCtx.getBean(WindowsPlatform.class).printPlatformName();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
