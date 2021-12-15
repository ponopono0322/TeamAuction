package com.example.teamauction;

import java.io.Serializable;

/**
 * Intent 객체 전달을 위한 클래스
 * 계정 정보를 저장하기 위한 클래스이기도 함
 */
public class GameAccountInfo implements Serializable {
    private String LoginID;         // 계정 아이디
    private String LoginPW;         // 계정 비밀번호
    private String gameName;        // 게임 이름
    private String characterName;   // 캐릭터 이름
    private String gamePublisherID; // 게임 아이디
    private String gamePublisherPW; // 게임 비밀번호

    public GameAccountInfo() { }    // 생성자

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
    public void setCharacterName(String characterName) {
        this.characterName = characterName;
    }
    public void setGamePublisherID(String gamePublisherID) { this.gamePublisherID = gamePublisherID; }
    public void setGamePublisherPW(String gamePublisherPW) { this.gamePublisherPW = gamePublisherPW; }
    public void setLoginID(String loginID) { this.LoginID = loginID; }
    public void setLoginPW(String loginPW) { this.LoginPW = loginPW; }

    public String getGameName() {
        return gameName;
    }
    public String getCharacterName() {
        return characterName;
    }
    public String getGamePublisherID() {
        return gamePublisherID;
    }
    public String getGamePublisherPW() {
        return gamePublisherPW;
    }
    public String getLoginID() { return LoginID; }
    public String getLoginPW() { return LoginPW; }

    public void resetData() {       // 필요한 데이터만 남기고 초기화
        this.gameName = "";
        this.gamePublisherID = "";
        this.gamePublisherPW = "";
        this.characterName = "";
    }
}
