package com.example.android.miwok;

import android.media.Image;

public class Word {

    private String mDefaultTranslation;

    private String mMiwokTranslation;

    private int mIMageResourceId;

    public Word(String defaultTranslation, String miwokTranslation){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
    }

    public Word(String defaultTranslation, String miwokTranslation,int imageResourceId){
        mDefaultTranslation = defaultTranslation;
        mMiwokTranslation = miwokTranslation;
        mIMageResourceId = imageResourceId;
    }


    public String  getDefaultTranslation(){
        return mDefaultTranslation;
    }

    public String getMiwokTranslation(){
        return mMiwokTranslation;
    }

    public int getIMageResourceId(){
        return mIMageResourceId;
    }


}
