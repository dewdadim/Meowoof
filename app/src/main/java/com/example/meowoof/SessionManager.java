package com.example.meowoof;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SessionManager(Context context){
        sharedPreferences = context.getSharedPreferences("appkey", 0);
        editor = sharedPreferences.edit();
        editor.commit();
    }

    public void setSignIn(boolean signin){
        editor.putBoolean("key_signin", signin);
        editor.commit();
    }

    public boolean getSignIn(){
        return sharedPreferences.getBoolean("key_signin", false);
    }

    public void setName(String name){
        editor.putString("key_name", name);
        editor.commit();
    }

    public String getName(){
        return sharedPreferences.getString("key_name", "");
    }

    public void setUsername(String username){
        editor.putString("key_username", username);
        editor.commit();
    }

    public int getPreviewsTotalSteps(){
        return sharedPreferences.getInt("key_previewsTotalSteps", 0);
    }

    public void setPreviewsTotalSteps(int previewsTotalSteps){
        editor.putInt("key_previewsTotalSteps", previewsTotalSteps);
        editor.commit();
    }

    public String getUsername(){
        return sharedPreferences.getString("key_username", "");
    }
}
