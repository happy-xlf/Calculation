package com.example.calculation;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.Random;

public class MyViewModel extends AndroidViewModel {
    private SavedStateHandle handle;
    private static String KEY_HIGH_SCORE="key_high_score";
    private static String KEY_LEFT_NUMBER="key_left_number";
    private static String KEY_RIGHT_NUMBER="key_right_number";
    private static String KEY_OPERATOR="key_operator";
    private static String KEY_ANSWER="key_answer";
    private static String SAVE_SHP_DATA_NAME="save_shp_data_name";
    private static String KEY_CURRENT_SCORE="key_current_score";
    public boolean win_flag=false;



    public MyViewModel(@NonNull Application application,SavedStateHandle handle) {
        super(application);
        if(!handle.contains(KEY_HIGH_SCORE)){
            SharedPreferences shp=getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME, Context.MODE_PRIVATE);
            handle.set(KEY_HIGH_SCORE,shp.getInt(KEY_HIGH_SCORE,0));
            handle.set(KEY_LEFT_NUMBER,0);
            handle.set(KEY_RIGHT_NUMBER,0);
            handle.set(KEY_OPERATOR,"+");
            handle.set(KEY_ANSWER,0);
            handle.set(KEY_CURRENT_SCORE,0);
        }
        this.handle=handle;
    }
    public MutableLiveData<Integer> getLeftNumber(){
        return handle.getLiveData(KEY_LEFT_NUMBER);
    }
    public MutableLiveData<Integer> getRigthNumber(){
        return handle.getLiveData(KEY_RIGHT_NUMBER);
    }
    public MutableLiveData<String> getOperator(){
        return handle.getLiveData(KEY_OPERATOR);
    }
    public MutableLiveData<Integer> getHighScore(){
        return handle.getLiveData(KEY_HIGH_SCORE);
    }
    public MutableLiveData<Integer> getCurrentScore(){
        return handle.getLiveData(KEY_CURRENT_SCORE);
    }
    public MutableLiveData<Integer> getAnswer(){
        return handle.getLiveData(KEY_ANSWER);
    }

    public void setCurrentScore(){
        handle.set(KEY_CURRENT_SCORE,0);
    }

    void generator(){
        int level=100;
        Random random=new Random();
        int a=random.nextInt(level)+1;
        int b=random.nextInt(level)+1;
        if(a%4==0){
            getOperator().setValue("+");
            if(a>b){
                getAnswer().setValue(a);
                getLeftNumber().setValue(b);
                getRigthNumber().setValue(a-b);
            }else{
                getOperator().setValue("+");
                getAnswer().setValue(b);
                getLeftNumber().setValue(a);
                getRigthNumber().setValue(b-a);
            }
        }
        else if(a%4==1){
            getOperator().setValue("-");
            if(a>b){
                getAnswer().setValue(a-b);
                getLeftNumber().setValue(a);
                getRigthNumber().setValue(b);
            }else{
                getOperator().setValue("-");
                getAnswer().setValue(b-a);
                getLeftNumber().setValue(b);
                getRigthNumber().setValue(a);
            }
        }
        else if(a%4==2){
            getOperator().setValue("*");
            getLeftNumber().setValue(a);
            getRigthNumber().setValue(b);
            getAnswer().setValue(a*b);
        }
        else if(a%4==3){
            getOperator().setValue("/");
            while(a%b!=0){
                a=random.nextInt(level)+1;
                b=random.nextInt(level)+1;
            }
            getLeftNumber().setValue(a);
            getRigthNumber().setValue(b);
            getAnswer().setValue(a/b);
        }
    }
    void save(){
        SharedPreferences shp=getApplication().getSharedPreferences(SAVE_SHP_DATA_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=shp.edit();
        editor.putInt(KEY_HIGH_SCORE,getHighScore().getValue());
        editor.apply();
    }
    void answerCurrent(){
        getCurrentScore().setValue(getCurrentScore().getValue()+1);
        if(getCurrentScore().getValue()>getHighScore().getValue()){
            getHighScore().setValue(getCurrentScore().getValue());
            win_flag=true;
        }
        generator();
    }
}
