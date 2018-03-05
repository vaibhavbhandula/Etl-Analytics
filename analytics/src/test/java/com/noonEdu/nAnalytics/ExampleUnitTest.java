package com.noonEdu.nAnalytics;

import com.noonEdu.nAnalytics.commons.Utils;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void mapTest() throws Exception {
        HashMap<String, Object> map = new HashMap<>();
        map.put("hello", 1);
        map.put("test", 2 + 5);
        map.put("haha", "hakdhka adada");
        String str = Utils.mapToString(map);
        System.out.println(str);
        HashMap<String, Object> hashMap = Utils.stringToMap(str);
        System.out.println(hashMap.toString());
    }
}