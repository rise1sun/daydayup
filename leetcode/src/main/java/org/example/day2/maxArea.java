package org.example.day2;

/**
 * @author jiangfeng
 * @date 2023/11/3
 */
public class maxArea {
    public static void main(String[] args) {
        //[1,8,6,2,5,4,8,3,7]
        int[] height = {1,8,6,2,5,4,8,3,7};
        int res =0; int i= 0; int j= height.length-1;
        while (i<j){
            res = height[i]<height[j]?Math.max(res,(j-i)*height[i++])
                    :Math.max(res,(j-i)*height[j--]);
        }
        System.out.println(res);
     //   return res;

    }
}
