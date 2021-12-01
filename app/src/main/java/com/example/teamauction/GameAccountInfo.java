package com.example.teamauction;

import java.io.Serializable;

public class GameAccountInfo implements Serializable {
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
    public void setGamePublisherID(String gamePublisherID) {
        this.gamePublisherID = gamePublisherID;
    }
    public void setGamePublisherPW(String gamePublisherPW) {
        this.gamePublisherPW = gamePublisherPW;
    }

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
}
