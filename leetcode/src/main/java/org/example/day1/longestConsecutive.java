package org.example.day1;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangfeng
 * @date 2023/11/1
 */
public class longestConsecutive {
    public static void main(String[] args) {
        // key表示num，value表示num所在连续区间的长度
        int[] nums  = new int[]{0,3,7,2,5,8,4,6,0,1};
        Map<Integer, Integer> map = new HashMap<>();
        int length =0;
        for (int num : nums) {
            if(!map.containsKey(num)){

                Integer left = map.getOrDefault(num - 1, 0);
                Integer right = map.getOrDefault(num + 1, 0);

                int curLen = left+right+1;
                length =Math.max(curLen,length);
                //左右边界重新赋值长度

                //这个1随便赋值的
                map.put(num,1);
                //左边界和右边界的值是一样的
                map.put(num-left,curLen);
                map.put(num+right,curLen);
            }
        }
        System.out.println(length);

    }
}
