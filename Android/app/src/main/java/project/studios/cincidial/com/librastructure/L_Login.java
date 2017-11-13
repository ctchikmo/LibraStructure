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

import project.studios.cincidial.com.librastructure.Sens.SensD;

public class L_Login extends AppCompatActivity
{
    TextView view;
    String serverUsername;
    String serverPassword;

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
                try {makeRequest();}
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
                            serverUsername = response.getString(SensD.serverLoginR_username);
                            serverPassword = response.getString(SensD.serverLoginR_password);
                            view.setText(serverUsername + " :: " + serverPassword);
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
