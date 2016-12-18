package ru.cpsmi.jee;

public class Demo {

    public static void main(String[] args) {
        Image image = new ProxyImage("test_image.jpg");
        
        //image will be loaded from disk
        image.display();
        System.out.println("");

        //image will not be loaded from disk
        image.display();
    }
}
