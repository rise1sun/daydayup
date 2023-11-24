package org.example.threeSum;

import java.security.Key;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @author jiangfeng
 * @date 2023/11/24
 */
public class threeSum {
    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> list = threeSum(nums);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.size());
        }
    }

 //   -4 -1 -1 0 1 2
  //  -1 0 1 2 -1 -4
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for (int i = 0; i < nums.length ; i++) {
            int start = nums[i];
            //定义两个双向指针 j,k
            int j = i+1;
            int k =nums.length-1;
            if(k > 0 && nums[k] == nums[k - 1]){
                continue;
            }
            if(start>0){
                //如果最小值都大于0,则固定值往后移动,其他的不用比较了
                break;
            }
            while (j < k){
                if(start+nums[j]+nums[k]==0){
                    List<Integer> integers = new ArrayList<>();
                    integers.add(start);
                    integers.add(nums[j]);
                    integers.add(nums[k]);
                    result.add(integers);

                }else if(start+nums[j]+nums[k]<0){
                    while(i<j && nums[j]==nums[++j]);
                }else if(start+nums[j]+nums[k]>0){
                    while(i<j && nums[k]==nums[--k]);
                }

            }


        }
        return result;
    }
}


