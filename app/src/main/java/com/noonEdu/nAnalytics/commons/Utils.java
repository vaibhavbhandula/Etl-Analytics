package com.noonEdu.nAnalytics.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.zip.GZIPInputStream;

import okhttp3.Response;

/**
 * @author Vaibhav Bhandula on 05/03/18.
 */

public class Utils {

    public static InputStream getStream(Response response) throws IllegalStateException, IOException {

        InputStream inStream = response.body().byteStream();

        String contentEncoding = response.header("Content-Encoding");
        if ((contentEncoding != null) && contentEncoding.equalsIgnoreCase("gzip")) {
            inStream = new GZIPInputStream(inStream);
        }
        return inStream;
    }

    public static String convertStreamToString(InputStream is) {
        try {
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return total.toString();
        } catch (Exception w) {
            LogUtils.printStackTrace(w);
        }
        return "";
    }

    public static String mapToString(HashMap<String, Object> map) {
        StringBuilder mapString = new StringBuilder();
        if (map == null || map.isEmpty()) {
            return "";
        }
        for (String key : map.keySet()) {
            mapString.append(key);
            mapString.append("=");
            mapString.append(map.get(key));
            mapString.append("&");
        }
        String string = mapString.toString();
        string = string.substring(0, string.length() - 1);
        return string;
    }

    public static HashMap<String, Object> stringToMap(String string) {
        HashMap<String, Object> map = new HashMap<>();
        if (string == null || string.isEmpty()) {
            return map;
        }
        String[] nameValuePairs = string.split("&");
        for (String nameValue : nameValuePairs) {
            String[] nameValueArr = nameValue.split("=");
            if (nameValueArr.length == 2) {
                map.put(nameValueArr[0], nameValueArr[1]);
            }
        }
        return map;
    }
}
