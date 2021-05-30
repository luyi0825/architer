package com.lz.test.jvm;

/**
 * 添加
 *
 * @author luyi
 */
public class Add {

    public double calculate(double... nums) {
        double sum = 0;
        for (double num : nums) {
            sum += num;
        }
        return sum;
    }
}
