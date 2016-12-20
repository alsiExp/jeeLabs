package ru.cpsmi.jee;

@Profiling
public class LinuxPlatform implements Platform {
    private String platformName;

    public void printName() {
        System.out.println(platformName);
    }

    public void setPlatformName(String platformName) {
        this.platformName = platformName;
    }

}
