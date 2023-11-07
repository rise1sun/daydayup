package org.example.lambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * @author jiangfeng
 * @date 2023/11/6
 */
public class lambda {
    public static void main(String[] args) {
      /*  Comparator<Integer> comparator =(a,b)->{
            System.out.println("比较接口");
            return Integer.compare(a,b);};*/

        Comparator<Integer> comparator =(a,b)->Integer.compare(a,b);
    }
}
