package ru.cpsmi.jee;

public class WindowsPlatform implements Platform {

    private String platformName;

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

    public void printPlatformName() {
        System.out.println(platformName);
    }
}
