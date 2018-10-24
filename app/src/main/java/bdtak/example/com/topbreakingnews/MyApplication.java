package bdtak.example.com.topbreakingnews;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by User on 2/7/2018.
 */

public class MyApplication extends Application{

    public static final String TAG = MyApplication.class.getSimpleName();
    private RequestQueue mRequestQueue;
    private static MyApplication mInstance;

    @Override
    public void onCreate() {

        super.onCreate();
        mInstance = this;

    }
    public static synchronized MyApplication getmInstance(){

        return mInstance;

    }
    public RequestQueue getmRequestQueue(){

        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());

        }

        return mRequestQueue;

    }

    public <T>void addToRequestQueue(Request<T>req, String tag){

        req.setTag(TextUtils.isEmpty(tag)? TAG:tag);
        getmRequestQueue().add(req);

    }

    public <T>void addToRequestQueue(Request<T>req){

        req.setTag(TAG);
        getmRequestQueue().add(req);

    }

    public void cancelPendingRequests(Object tag){

        if (mRequestQueue != null){
            mRequestQueue.cancelAll(tag);

        }
    }
}
