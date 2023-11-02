package org.example.day1;

import java.util.HashMap;

/**
 * @author jiangfeng
 * @date 2023/11/1
 */
public class twoSum {
    public static int[] main(int[] nums, int target) {
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums.length; i++) {
            int num =nums[i];
            int get =target-num;

            if(map.containsKey(num)){
                int[] result ={i,map.get(num)};
                return result;
            }
            map.put(get,i);
        }
        throw new RuntimeException("没有找到");
    }
}
