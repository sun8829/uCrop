package com.yalantis.ucrop.util;

import android.net.Uri;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ImagePicker {
    private static volatile ImagePicker INSTANCE = new ImagePicker();
    private ConcurrentHashMap<String, Observable> data = new ConcurrentHashMap<>();

    private ImagePicker() {
    }

    public static ImagePicker getInstance() {
        return INSTANCE;
    }


    public void addObservable(String key, Observable observable) {
        data.put(key, observable);
    }

    public void removeObservable(String key) {
        data.remove(key);
    }

    public void notifyObservable(int event, Uri uri) {
        for (Map.Entry<String, Observable> entry : data.entrySet()) {
            Observable observable = entry.getValue();
            if (observable != null) {
                observable.onResult(event, uri);
            }
        }
    }

    public interface Observable {
        public static int EVENT_SUCCESS = 0;
        public static int EVENT_SUCCESS_FINISH = 1;
        public static int EVENT_ERROR = -1;

        void onResult(int event, Uri uri);
    }
}
