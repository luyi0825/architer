package com.lz.test.jvm;

/**
 * 算数类
 *
 * @author luyi
 */
public class Math {
    private double a = 1;
    private final static int b = 2;

    private final Add add = new Add();

    public static void main(String[] args) {
        Math math = new Math();
        System.out.println(math.add());

    }

    public double add() {
        return add.calculate(a, b);
    }
}
