package com.MAVP.ZekrShomarTasbih;

public class Azkar {

    public static final String KEY_ID = "ZekrID";
    public static final String KEY_ZEKR = "Zekr";
    public static final String KEY_TRANSLATE = "Translate";
    public static final String KEY_FULLCOUNT = "FullCount";
    public static final String KEY_LASTCOUNT = "LastCount";
    public static final String KEY_SOUND = "Sound";
    public static final String KEY_SUGGESTED = "Suggested" ;

    private String Zekr , Translate ;
    private int FullCount , LastCount , Sound , ID , Suggested ;

    public Azkar(){}

    public String getZekr() {
        return Zekr;
    }

    public void setZekr(String zekr) {
        Zekr = zekr;
    }

    public String getTranslate() {
        return Translate;
    }

    public void setTranslate(String translate) {
        Translate = translate;
    }

    public int getFullCount() {
        return FullCount;
    }

    public void setFullCount(int fullCount) {
        FullCount = fullCount;
    }

    public int getLastCount() {
        return LastCount;
    }

    public void setLastCount(int lastCount) {
        LastCount = lastCount;
    }

    public int getSound() {
        return Sound;
    }

    public void setSound(int sound) {
        Sound = sound;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getSuggested() {
        return Suggested;
    }

    public void setSuggested(int suggested) {
        Suggested = suggested;
    }

    @Override
    public String toString() {
        return Zekr;
    }
}
