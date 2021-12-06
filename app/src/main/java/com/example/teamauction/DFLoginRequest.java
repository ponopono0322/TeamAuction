package com.example.teamauction;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DFLoginRequest extends StringRequest{

    // 서버 URL 설정(PHP 파일 연동)
    final static private String URL = "http://ualsgur98.dothome.co.kr/DFLogin.php";
    private Map<String, String> map;

    public DFLoginRequest(String gameID, String gamePW, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("gameID", gameID);
        map.put("gamePW", gamePW);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}