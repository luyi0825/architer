package com.zp.zptest.thread;

import lombok.SneakyThrows;

/**
 * @author zhoupei
 * @create 2021/4/11
 **/
public class ThreadTest {
    public static void main(String[] args) {
        Actor actor = new Actor();
        actor.start();
        Actress actress = new Actress();
        new Thread(actress).start();
    }
}

class Actor extends Thread{

    @SneakyThrows
    @Override
    public void run(){
        System.out.println("我是Thread先生，我开始表演...");

        int count = 0;
        boolean act = true;

        while (act){
            if (count < 10){
                System.out.println("我是Thread先生，这是我的第"+ (++count) +"次表演...");
                Thread.sleep(100);
            }else {
                act = false;
            }
        }

        System.out.println("我是Thread先生，我结束表演...");
    }
}

class Actress implements Runnable{

    @SneakyThrows
    @Override
    public void run() {
        System.out.println("我是Runnable小姐，我开始表演...");

        int count = 0;
        boolean act = true;

        while (act){
            if (count < 10){
                System.out.println("我是Runnable小姐，这是我的第"+ (++count) +"次表演...");
                Thread.sleep(100);
            }else {
                act = false;
            }
        }

        System.out.println("我是Runnable小姐，我结束表演...");
    }
}
