package com.hd.mall.testpac;

public class SThread extends Thread {


    void contextLoads() {
        Thread t = new Thread();
        t.run();
    }

    @Override
    public void run() {
        System.out.println("222");
    }
}
