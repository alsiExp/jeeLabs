package ru.cpsmi.jee;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) {
        try(ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("app.xml")) {
            String[] beanNames = appCtx.getBeanDefinitionNames();
            for(String s : beanNames) {
                System.out.println(s);
            }
            NetworkElements ne = appCtx.getBean(ru.cpsmi.jee.NetworkElements.class);
            ne.printNECount();
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
