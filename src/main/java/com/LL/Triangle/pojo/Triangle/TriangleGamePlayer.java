package com.LL.Triangle.pojo.Triangle;

import com.LL.Triangle.pojo.User;
import com.LL.Triangle.utils.DictionaryUtil;

public class TriangleGamePlayer extends User {
    private Integer tablePos;
    private boolean isAlive;

    private int healthPoint = 3;
    private int mana;
    private int recoverManaNum = 2;

    private int atkPow = 0;
    private int atkNum = 0;
    private int defenceNum = 0;
    private boolean manaDefence = false;
    private boolean invincible = false;
    private boolean recoverMana = false;
    private boolean manaAtk = false;
    private boolean manaGrab = false;

    //For Json本回合决策
    private String decision;

    private void setAttributeValue(Object[] dec){
        atkPow = (int) dec[0]; atkNum=(int) dec[1]; defenceNum = (int) dec[2];
        manaDefence = (boolean) dec[3]; invincible = (boolean) dec[4];
        recoverMana = (boolean) dec[5]; manaAtk = (boolean) dec[6];
        manaGrab = (boolean) dec[7];
    }

    public void makeDecision(String decision){
        this.decision = decision;
        Object[] dec = new Object[8];
        //dec[] 0攻击威力 1攻击值 2防御值 3法力防御 4无敌 5恢复法力 6法力攻击 7法力抢夺
        switch(decision){
            case DictionaryUtil.RECOVER_MANA:
                dec[0]=0; dec[1]=0;dec[2]=0;dec[3]=false;dec[4]=false;dec[5]=true;dec[6]=false;dec[7]=false;break;
            case DictionaryUtil.ATTACK_1:
                dec[0]=1; dec[1]=1;dec[2]=0;dec[3]=false;dec[4]=false;dec[5]=false;dec[6]=false;dec[7]=false;break;
            case DictionaryUtil.ATTACK_2:
                dec[0]=2; dec[1]=2;dec[2]=0;dec[3]=false;dec[4]=false;dec[5]=false;dec[6]=false;dec[7]=false;break;
            case DictionaryUtil.ATTACK_3:
                dec[0]=1; dec[1]=3;dec[2]=0;dec[3]=false;dec[4]=false;dec[5]=false;dec[6]=false;dec[7]=false;break;
            case DictionaryUtil.ATTACK_4:
                dec[0]=1; dec[1]=4;dec[2]=0;dec[3]=false;dec[4]=false;dec[5]=false;dec[6]=false;dec[7]=false;break;
            case DictionaryUtil.DEFENCE_1:
                dec[0]=0; dec[1]=0;dec[2]=1;dec[3]=true;dec[4]=false;dec[5]=false;dec[6]=false;dec[7]=false;break;
            case DictionaryUtil.DEFENCE_2:
                dec[0]=0; dec[1]=0;dec[2]=2;dec[3]=false;dec[4]=false;dec[5]=false;dec[6]=false;dec[7]=false;break;
            case DictionaryUtil.DEFENCE_3:
                dec[0]=0; dec[1]=0;dec[2]=3;dec[3]=false;dec[4]=false;dec[5]=false;dec[6]=false;dec[7]=false;break;
            case DictionaryUtil.MANA_ATTACK:
                dec[0]=0; dec[1]=0;dec[2]=0;dec[3]=true;dec[4]=true;dec[5]=false;dec[6]=true;dec[7]=false;break;
            case DictionaryUtil.MANA_GRAB:
                dec[0]=0; dec[1]=0;dec[2]=0;dec[3]=true;dec[4]=true;dec[5]=false;dec[6]=false;dec[7]=true;break;
            case DictionaryUtil.BE_INVINCIBLE:
                dec[0]=0; dec[1]=0;dec[2]=0;dec[3]=true;dec[4]=true;dec[5]=false;dec[6]=false;dec[7]=false;break;
        }
        setAttributeValue(dec);
    }

    public void reset(){
        healthPoint = 3;
        mana = 0;
        recoverManaNum = 2;
        atkPow =0;
        atkNum =0;
        defenceNum =0;
        manaDefence = false;
        invincible = false;
        recoverMana = false;
        manaAtk = false;
        manaGrab = false;
    }

    /**
     * 使用法力
     * return true：成功。
     * return false：玩家法力不足，死掉。
     */
    public boolean useMana(){
        Integer cost = DictionaryUtil.manaUseMap.get(decision);
        mana -= cost;
        if(mana<0){
            healthPoint = -1;
            return false;
        }
        return true;
    }

    /**
     * 获取玩家本回合信息
     * return: res[] 0攻击数值 1攻击威力 2法力攻击 3法力夺取 4回复法力值
     */
    public Object[] getInterplayInfo(){
        Object[] res = {atkNum,atkPow,manaAtk,manaGrab,recoverMana?recoverManaNum:0};
        Object[] dead = {0,0,false,false,0};
        return healthPoint>0?res:dead;
    }

    public void recoverMana(){
        if(recoverMana){
            mana += recoverManaNum;
        }
    }

    public void grabMana(int manaNum){
        if(manaGrab){
            mana += manaNum;
        }
    }

    /**
     * 玩家防御
     * @return true玩家存活 false玩家死亡
     */
    public boolean defend(int atkNum,int atkPow,boolean manaAtk){
        if(mana<0){return false;}
        if(invincible){return true;}
        if(manaAtk&&!manaDefence){mana=0;}
        if(atkNum==4){
            if(this.atkNum<atkNum&&defenceNum!=1){healthPoint -= atkPow;}
        }else{
            if(defenceNum==atkNum){mana+=atkNum;}
            if(this.atkNum<atkNum&&defenceNum!=atkNum){healthPoint -= atkPow;}
        }
        return healthPoint>0;
    }

    public boolean isAlive(){
        return healthPoint > 0;
    }

    public int getHealthPoint() {
        return healthPoint;
    }

    public int getMana() {
        return mana;
    }

    public String getDecision() {
        return decision;
    }
}
