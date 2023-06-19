package com.dta.dtawallpaper.util;

import java.io.IOException;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtil {

    static OkHttpClient client = new OkHttpClient();

    public static void post(String url, Map<String,String> params, Callback callback)  {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key:params.keySet()){
            builder.add(key,params.get(key));

        }
        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);

    }
}
