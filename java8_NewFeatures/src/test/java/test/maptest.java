package test;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangfeng
 * @date 2023/11/6
 */
public class maptest {

    @Test
    public  Map<String, String> getMap() {
        HashMap<String, String> map = new HashMap<>();
        map.put("key", "value");
        try {
            map.put("key", "try");
            return map;
        } catch (Exception e) {
            map.put("key", "catch");
        } finally {
            map.put("key", "finally");
            map = null;
        }
        return map;
    }

    @Test
    public  void test(String[] args) {
        String value = getMap().get("key");
        System.out.println(value);
    }
}
