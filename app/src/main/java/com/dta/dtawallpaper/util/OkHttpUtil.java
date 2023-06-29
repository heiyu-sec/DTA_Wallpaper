package com.dta.dtawallpaper.util;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtil {

    static OkHttpClient client = null;
    public static InputStream in_cer = null;

    private static OkHttpClient getClient(){
        if (client == null){
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
                Certificate certificate = certificateFactory.generateCertificate(in_cer);
                KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
                ks.load(null,null);
                ks.setCertificateEntry("wallpaper",certificate);
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init(ks);
                TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
                SSLContext context = SSLContext.getInstance("TLS");
                context.init(null,trustManagers,new SecureRandom());
                SSLSocketFactory factory = context.getSocketFactory();
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                builder.sslSocketFactory(factory, (X509TrustManager) trustManagers[0]);
                builder.hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        if (hostname.equals("www.dtasecurity.cn")){
                            return true;
                        }
                        return false;
                    }
                });
                return builder.build();
            }catch (Exception e){
                return null;
            }
        }
        return client;
    }

    public static void post(String url, Map<String, String> params, Callback callback) {
        FormBody.Builder builder = new FormBody.Builder();
        for (String key : params.keySet()){
            builder.add(key,params.get(key));
        }
        FormBody body = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        getClient().newCall(request).enqueue(callback);
    }



}