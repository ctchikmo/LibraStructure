package project.studios.cincidial.com.librastructure;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Singleton class for volley functionality.
 */
public class VolleySingleton
{
    private static VolleySingleton mInstance;
    private RequestQueue requestQueue = null;

    private VolleySingleton()
    {
    }

    public static synchronized VolleySingleton getInstance()
    {
        if(mInstance == null)
            mInstance = new VolleySingleton();
        return mInstance;
    }

    public RequestQueue getRequestQueue(Context context)
    {
        if (requestQueue == null)
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, Context context)
    {
        getRequestQueue(context).add(req);
    }
}