package ru.cpsmi.jee;

public class NetworkElements {
    @InjectNetworkScanCount(min = 10, max = 1000)
    private int NECount;

    public void printNECount() {
        System.out.println(NECount);
    }
}
