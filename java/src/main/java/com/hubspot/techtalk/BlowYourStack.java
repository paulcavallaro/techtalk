package com.hubspot.techtalk;

public class BlowYourStack {

    public int boom(int a) {
        if (a == 0)
            return a;
        return boom(a - 1);
    }

    public static void main(String[] args) {
        System.out.println(new BlowYourStack().boom(100000));
    }

}
