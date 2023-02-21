package com.game.myPac;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

public class Test {
    public static void main(String[] args) {
        double d = Math.sqrt(2500+200*2500);
        double m = 6.32;
        System.out.println(d);
        System.out.println((int)d);

        int level = (int)((Math.sqrt(2500+200*2500)-50)/100);
        int nextLevel = 50*(level+1)*(level+2) - 2500;

        System.out.println(level);
        System.out.println(nextLevel);
    }
}
