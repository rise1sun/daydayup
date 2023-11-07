package org.example.lambda;

/**
 * @author jiangfeng
 * @date 2023/11/6
 */
import java.util.HashMap;
import java.util.Map;

class test {
    public static Map<String,String> getMap(){
        HashMap<String, String> map = new HashMap<>();
        map.put("key","value");
        try {
            map.put("key","try");
            return map;
        }catch (Exception e){
            map.put("key","catch");
        }finally {
            map.put("key","finally");
          //  System.out.println(map);
            map=null;
          //  System.out.println(map);
        }
        return map;
    }

    public static void main(String[] args) {
        Map<String, String> map = getMap();
        System.out.println("main"+map);
        String value = getMap().get("key");
        System.out.println(value);
    }
}