package com.example.teamauction;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PHPRequest extends StringRequest {

    private Map<String, String> map;

    // 계정 추가
    public PHPRequest(String URL, String userID, String userPW, String gameName,
                      String gameID, String gamePW, String userCh, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPW);
        map.put("gameName", gameName);
        map.put("gameID", gameID);
        map.put("gamePW", gamePW);
        map.put("gameNickname", userCh);
    }

    // 회원가입
    public PHPRequest(String URL, String userName, String userPhone, String userEmail, String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userName", userName);
        map.put("userPhone", userPhone);
        map.put("userEmail", userEmail);
        map.put("userID", userID);
        map.put("userPassword", userPassword);
    }

    // 게임 넘겨주기
    public PHPRequest(String URL, String gameName, String gameID, String gamePW, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("gameName", gameName);
        map.put("gameID", gameID);
        map.put("gamePW", gamePW);
    }

    // 로그인,판매중인, 내아이템
    public PHPRequest(String URL, String userID, String userPW, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
        map.put("userPassword", userPW);
    }

    // 아이디 검사
    public PHPRequest(String URL, String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", userID);
    }

    // 게임 목록
    public PHPRequest(String URL, Response.Listener<String> listener) {
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
    }


    // 구매하기 기능
    public PHPRequest(String URL, String GameName,String RegisterNumber,String ItemQuantity, String  Buyer,Response.Listener<String> listener ){
        super(Method.POST, URL, listener,null);
        map = new HashMap<>();
        map.put("gameName", GameName);
        map.put("RegisterNumber", RegisterNumber);
        map.put("Quantity",ItemQuantity);
        map.put("Buyer", Buyer);
    }

    //public PHPRequest(String URL, String )
    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}