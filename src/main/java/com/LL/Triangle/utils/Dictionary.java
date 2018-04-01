package com.LL.Triangle.utils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Dictionary {
    public final static String RECOVER_MANA = "recoverMana";
    public final static String ATTACK_1 = "attack1";
    public final static String ATTACK_2 = "attack2";
    public final static String ATTACK_3 = "attack3";
    public final static String ATTACK_4 = "attack4";
    public final static String DEFENCE_1 = "defence1";
    public final static String DEFENCE_2 = "defence2";
    public final static String DEFENCE_3 = "defence3";
    public final static String MANA_ATTACK = "manaAttack";
    public final static String MANA_GRAB = "manaGrab";
    public final static String BE_INVINCIBLE = "beInvincible";
    public final static Map<String,Integer> manaUseMap = new ConcurrentHashMap<>();
    static {
        manaUseMap.put(RECOVER_MANA,0);
        manaUseMap.put(ATTACK_1,1);
        manaUseMap.put(ATTACK_2,2);
        manaUseMap.put(ATTACK_3,3);
        manaUseMap.put(ATTACK_4,4);
        manaUseMap.put(DEFENCE_1,0);
        manaUseMap.put(DEFENCE_2,0);
        manaUseMap.put(DEFENCE_3,0);
        manaUseMap.put(MANA_ATTACK,1);
        manaUseMap.put(MANA_GRAB,1);
        manaUseMap.put(BE_INVINCIBLE,1);
    }

}
