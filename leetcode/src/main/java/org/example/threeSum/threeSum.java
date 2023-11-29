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
//0,0,0
 //   -4 -1 -1 0 1 2
  //  -1 0 1 2 -1 -4
    public static List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        for (int i = 0; i < nums.length ; i++) {
            int start = nums[i];
            if(start>0){
                //如果最小值都大于0,则固定值往后移动,其他的不用比较了
                break;
            }
            //定义两个双向指针 j,k
            int j = i+1;
            int k =nums.length-1;
            if(i > 0 && nums[i] == nums[i - 1]){
                continue;
            }

            while (j < k){
                if(start+nums[j]+nums[k]==0){
                    result.add(Arrays.asList(start,nums[j],nums[k]));
                    while(j<k && nums[j]==nums[j+1]) {j++;}
                    while(j<k && nums[k]==nums[k-1]){k--;}
                    j++;
                    k--;
                }else if(start+nums[j]+nums[k]<0){
                    j++;
                }else if(start+nums[j]+nums[k]>0){
                    k--;
                }

            }


        }
        return result;
    }
}


