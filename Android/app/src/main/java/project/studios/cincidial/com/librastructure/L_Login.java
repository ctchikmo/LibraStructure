package project.studios.cincidial.com.librastructure;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

import project.studios.cincidial.com.librastructure.Sens.SensD;

public class L_Login extends AppCompatActivity
{
    TextView view;
    String phpUsername = null;
    String phpPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l__login);

        view = findViewById(R.id.press);
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View a)
            {
                try
                {
                    if(phpUsername == null)
                        makeRequest();
                    else
                        makeRequestBranch();
                }
                catch (UnsupportedEncodingException | JSONException e) {e.printStackTrace();}
            }
        });
    }

    private void makeRequest() throws UnsupportedEncodingException, JSONException
    {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SensD.serverLoginURL, new JSONObject(SensD.params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            phpUsername = response.getString(SensD.serverLoginR_username);
                            phpPassword = response.getString(SensD.serverLoginR_password);
                            //view.setText(phpUsername + " :: " + phpPassword);
                        }
                        catch (JSONException e)
                        {
                            try {view.setText(response.getString(SensD.serverErrorR));}
                            catch (JSONException e1) {e1.printStackTrace();}
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        view.setText(error.getMessage());
                    }
                }
        );
        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest, this);
    }

    private void makeRequestBranch() throws UnsupportedEncodingException, JSONException
    {
        HashMap<String, String> params = new HashMap<>(2);
        params.put(SensD.serverLoginR_username, phpUsername);
        params.put(SensD.serverLoginR_password, phpPassword);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, SensD.branchURL, new JSONObject(params),
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response)
                    {
                        try
                        {
                            String msg = response.getString(SensD.cp471r);
                            view.setText(msg);
                        }
                        catch (JSONException e)
                        {
                            try {view.setText(response.getString(SensD.serverErrorR));}
                            catch (JSONException e1) {e1.printStackTrace();}
                            e.printStackTrace();
                        }
                    }
                },

                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        view.setText(error.getMessage());
                    }
                }
        );
        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest, this);
    }
}
