package ru.cpsmi.jee;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("app.xml");
        for (int i = 0; i <1000; i++) {
            context.getBean(Platform.class).printName();
            Thread.sleep(300);
        }
    }

}
