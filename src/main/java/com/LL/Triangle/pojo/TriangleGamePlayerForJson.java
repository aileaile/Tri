package com.LL.Triangle.pojo;

public class TriangleGamePlayerForJson {
    private String userName;
    private int healthPoint;
    private int mana;
    private String decision;

    public TriangleGamePlayerForJson(){
    }
    public TriangleGamePlayerForJson(TriangleGamePlayer t){
        this.userName = t.getUserName();
        this.healthPoint = t.getHealthPoint();
        this.mana = t.getMana();
        if(t.getDecision()==null) {
            this.decision = "-";
        }else {
            this.decision = t.getDecision();
        }

    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public void setHealthPoint(int healthPoint) {
        this.healthPoint = healthPoint;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
