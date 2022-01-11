package com.example.finalproject.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executors;

public class InternetChecker {

    private final Context context;

    private final Handler handler = new Handler(Looper.getMainLooper());

    public InternetChecker(Context context) {
        this.context = context;
    }

    public void checkInternetAvailability(FinishCheckInternet onFinishCheckInternet) {
        if (isInternetAvailable()) {
            Executors.newSingleThreadExecutor().execute(() -> {
                handler.post(() -> {
                    HttpURLConnection urlConnection = null;
                    try {
                        urlConnection = (HttpURLConnection) (new URL("http://www.google.com").openConnection());
                        urlConnection.setRequestProperty("User-Agent", "Test");
                        urlConnection.setRequestProperty("Connection", "close");
                        urlConnection.setConnectTimeout(2000);
                        urlConnection.connect();
                        onFinishCheckInternet.onFinish(urlConnection.getResponseCode() == 200);
                    } catch (IOException e) {
                        onFinishCheckInternet.onFinish(false);
                    } finally {
                        if (urlConnection != null)
                            urlConnection.disconnect();
                    }

                });
            });
        } else {
            onFinishCheckInternet.onFinish(false);
        }
    }


    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {

            if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.getActiveNetwork());
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        return true;
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        return true;
                    } else return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET);
                }
            } else {
                try {
                    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                    if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                        return true;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return false;
    }

    public interface FinishCheckInternet {
        void onFinish(boolean isSuccess);
    }
}
