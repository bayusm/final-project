package com.example.finalproject.helper.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import java.util.HashSet;
import java.util.Set;

public class SharedPrefHelper {

    private static final Object mLock = new Object();

    private static final String DATA_NAME = "global";

    public static void deleteValue(Context context, @SharedPrefKeyName String keyName) {
        synchronized (mLock) {
            SharedPreferences.Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
            editor.remove(keyName);
            editor.apply();
        }
    }

    public static void saveString(Context context, @SharedPrefKeyName String keyName, String value) {
        synchronized (mLock) {
            SharedPreferences.Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(keyName, value);
            editor.apply();
        }
    }

    public static String getSavedString(Context context, @SharedPrefKeyName String keyName) {
        synchronized (mLock) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
            if (sharedPreferences.contains(keyName)) {
                return sharedPreferences.getString(keyName, null);
            }
            return null;
        }
    }

    public static void saveSetString(Context context, @SharedPrefKeyName String keyName, Set<String> value) {
        synchronized (mLock) {
            SharedPreferences.Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
            editor.putStringSet(keyName, value);
            editor.apply();
        }
    }

    public static Set<String> getSavedSetString(Context context, @SharedPrefKeyName String keyName) {
        synchronized (mLock) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
            if (sharedPreferences.contains(keyName)) {
                return sharedPreferences.getStringSet(keyName, null);
            }
            return null;
        }
    }

    public static <T> void saveObject(Context context, @SharedPrefKeyName String keyName, T obj) {
        synchronized (mLock) {
            Gson gson = new Gson();
            String serializedObject = gson.toJson(obj);

            SharedPreferences.Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
            editor.putString(keyName, serializedObject);
            editor.apply();
        }
    }

    public static <T> T getSavedObject(Context context, @SharedPrefKeyName String keyName, Class<T> classType) {
        synchronized (mLock) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
            if (sharedPreferences.contains(keyName)) {
                Gson gson = new Gson();
                return gson.fromJson(sharedPreferences.getString(keyName, null), classType);
            }
            return null;
        }
    }

    public static <T> void saveSetObject(Context context, @SharedPrefKeyName String keyName, Set<T> objectSet) {
        synchronized (mLock) {
            Gson gson = new Gson();
            Set<String> stringSet = new HashSet<>();
            for (T obj : objectSet) {
                stringSet.add(gson.toJson(obj));
            }

            SharedPreferences.Editor editor = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE).edit();
            editor.putStringSet(keyName, stringSet);
            editor.apply();
        }
    }

    public static <T> Set<T> getSavedSetObject(Context context, @SharedPrefKeyName String keyName, Class<T> classType) {
        synchronized (mLock) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE);
            if (sharedPreferences.contains(keyName)) {
                Gson gson = new Gson();
                Set<T> objectSet = new HashSet<>();
                Set<String> stringSet = sharedPreferences.getStringSet(keyName, null);
                for (String str : stringSet) {
                    objectSet.add(gson.fromJson(str, classType));
                }
                return objectSet;
            }
            return null;
        }
    }

}
