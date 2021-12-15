package com.example.teamauction;

import java.io.Serializable;

public class GameAccountInfo implements Serializable {
    private String LoginID;
    private String LoginPW;
    private String gameName;
    private String characterName;
    private String gamePublisherID;
    private String gamePublisherPW;

    public GameAccountInfo() {

    }

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
    public String getCharacterName() { return characterName; }
    public String getGamePublisherID() {
        return gamePublisherID;
    }
    public String getGamePublisherPW() {
        return gamePublisherPW;
    }
    public String getLoginID() { return LoginID; }
    public String getLoginPW() { return LoginPW; }

    public void resetData() { //게임 아이디 정보 다 리셋
        this.gameName = "";
        this.gamePublisherID = "";
        this.gamePublisherPW = "";
        this.characterName = "";
    }
}
