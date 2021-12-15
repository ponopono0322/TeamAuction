package com.example.teamauction;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * php 통신을 할 때 쓰는 클래스
 * 생성자를 통해 여러 형태로 데이터를 저장할 수 있다
 */
public class PHPRequest extends StringRequest {

    private Map<String, String> map;
    private Map<String, Integer> map2;

    // 계정 추가
    public PHPRequest(String URL, String userID, String userPW, String gameName,
                      String gameID, String gamePW, String userCh, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();              // 해시맵 생성
        map.put("userID", userID);       // 계정 아이디 추가
        map.put("userPassword", userPW); // 계정 비밀번호 추가
        map.put("gameName", gameName);   // 게임 이름 추가
        map.put("gameID", gameID);       // 게임 아이디 추가
        map.put("gamePW", gamePW);       // 게임 비밀번호 추가
        map.put("gameNickname", userCh); // 게임 캐릭터이름 추가
    }

    // 회원가입
    public PHPRequest(String URL, String userName, String userPhone, String userEmail, String userID, String userPassword, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();                   // 해시맵 생성
        map.put("userName", userName);        // 계정 이름 추가
        map.put("userPhone", userPhone);      // 계정 전화번호 추가
        map.put("userEmail", userEmail);      // 계정 이메일 추가
        map.put("userID", userID);            // 계정 아이디 추가
        map.put("userPassword", userPassword);// 계정 비밀번호 추가
    }

    // 게임 넘겨주기
    public PHPRequest(String URL, String gameName, String gameID, String gamePW, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();              // 해시맵 생성
        map.put("gameName", gameName);   // 계정 아이디 추가
        map.put("gameID", gameID);       // 게임 아이디 추가
        map.put("gamePW", gamePW);       // 게임 비밀번호 추가
    }

    // 로그인
    public PHPRequest(String URL, String userID, String userPW, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();              // 해시맵 생성
        map.put("userID", userID);       // 계정 아이디 추가
        map.put("userPassword", userPW); // 계정 비밀번호 추가
    }

    // 아이디 검사
    public PHPRequest(String URL, String userID, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();          // 해시맵 생성
        map.put("userID", userID);   // 계정 아이디 추가
    }

    // 게임 목록
    public PHPRequest(String URL, Response.Listener<String> listener) {
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();      // 해시맵 생성
    }

    // 경매장 리스트, 판매중인, 내아이템
    public PHPRequest(String URL, String GameName,String ItemName,Integer ItemQuantity,Integer ItemPrice,String GameID, String GameNickname,Response.Listener<String> listener ){
        super(Method.POST, URL, listener,null);
        map = new HashMap<>();
        map2 = new HashMap<>();
        map.put("GameName",GameName);
        map.put("ItemName",ItemName);
        map2.put("ItemQuantity",ItemQuantity);
        map2.put("ItemPrice",ItemPrice);
        map.put("GameID",GameID);
        map.put("GameNickname",GameNickname);
    }

    //public PHPRequest(String URL, String )
    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;     // 해시맵 리턴
    }
}