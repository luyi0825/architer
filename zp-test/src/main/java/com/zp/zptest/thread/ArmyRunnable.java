package com.zp.zptest.thread;

/**
 * @author zhoupei
 * @create 2021/4/11
 **/
public class ArmyRunnable implements Runnable {

    volatile boolean keepRunning = true;

    @Override
    public void run() {
        if (keepRunning){
            //发动五连攻击
            for (int i = 0; i < 5; i++) {
                System.out.println("当前为第"+ i +"次进攻，进攻方为" + Thread.currentThread().getName());
                //进攻结束后释放资源
                Thread.yield();
            }
        }
    }
}
