package com.mobdice.indianexpress.network_call;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.mobdice.indianexpress.R;

import org.json.JSONObject;

public class RequestStandard
{
    public static void requestJsonObject(Context context, String url, final OwnVolleyResponse ownVolleyResponse) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (ownVolleyResponse != null)
                            ownVolleyResponse.response(true, response, "");
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (ownVolleyResponse != null)
                            ownVolleyResponse.response(false, null, error.getMessage());
                    }
                });
        VolleyInstance.getInstance(context.getApplicationContext()).addToRequestQueue(jsonObjectRequest);
    }

    public static void requestStringObject(Context context, String url, final OwnVolleyResponse ownVolleyResponse)
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (ownVolleyResponse != null)
                            ownVolleyResponse.response(true, response, null);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ownVolleyResponse != null)
                    ownVolleyResponse.response(false, null, error.getMessage());
            }
        });

        VolleyInstance.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
    }

    public static void requestGsonObject(Context context, String url, Class clazz, final OwnVolleyResponse ownVolleyResponse) {

        GsonRequest gsonRequest = new GsonRequest(url, clazz, null, new Response.Listener() {
            @Override
            public void onResponse(Object response) {
                if (ownVolleyResponse != null)
                    ownVolleyResponse.response(true, response, "");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (ownVolleyResponse != null)
                    ownVolleyResponse.response(false, null, "" + error);
                // Handle error
            }
        });

        VolleyInstance.getInstance(context.getApplicationContext()).addToRequestQueue(gsonRequest);
    }

//    public static void loadImage(String url, NetworkImageView networkImageView, Context context)
//    {
//        ImageLoader imageLoader = VolleyInstance.getInstance(context.getApplicationContext()).getImageLoader();
//        imageLoader.get(url, ImageLoader.getImageListener(networkImageView, R.drawable.ie_image_default, android.R.drawable.ic_dialog_alert));
//        networkImageView.setImageUrl(url, imageLoader);
//    }
}
