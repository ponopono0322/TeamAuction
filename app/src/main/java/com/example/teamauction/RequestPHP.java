package com.example.teamauction;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestPHP extends StringRequest {

    private Map<String, String> map;

    public RequestPHP(String URL, String userName, String userPhone, String userEmail, String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userName", userName);
        map.put("userPhone", userPhone);
        map.put("userEmail", userEmail);
        map.put("userID", userID);
        map.put("userPassword", userPassword);
    }

    public RequestPHP(String URL, String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}