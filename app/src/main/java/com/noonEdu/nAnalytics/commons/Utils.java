package com.noonEdu.nAnalytics.commons;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
}
