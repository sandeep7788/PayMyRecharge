package com.codeunite.paymyrch.adapter;

import android.content.Context;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

public class VolleySingleton {
    private static Context mCtx;
    private static VolleySingleton mInstance;
    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue = getRequestQueue();

    private VolleySingleton(Context context) {
        mCtx = context;
    }

    public static synchronized VolleySingleton getInstance(Context context) {
        VolleySingleton volleySingleton;
        synchronized (VolleySingleton.class) {
            if (mInstance == null) {
                mInstance = new VolleySingleton(context);
            }
            volleySingleton = mInstance;
        }
        return volleySingleton;
    }

    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}
