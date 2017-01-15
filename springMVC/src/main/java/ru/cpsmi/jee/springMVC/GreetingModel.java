package ru.cpsmi.jee.springMVC;

import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class GreetingModel {
    private static Map<String, String> greetingMap;

    static {
        greetingMap = new HashMap<>();
        greetingMap.put("admin", "from admin");
        greetingMap.put("user", "from user");
        greetingMap.put("default", "World");
    }

    public String getGreeting(String s) {
        return greetingMap.get(s);
    }

}
