package com.example.calculation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    NavController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        controller= Navigation.findNavController(this,R.id.fragment);
        NavigationUI.setupActionBarWithNavController(this,controller);
    }
//设置页面的左上角的返回操作
    @Override
    public boolean onSupportNavigateUp() {
        if(controller.getCurrentDestination().getId()==R.id.questionFragment){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("你确定要退出吗？");
            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    controller.navigateUp();
                }
            });
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            AlertDialog dialog=builder.create();
            dialog.show();
        }else{
            controller.navigate(R.id.titleFragment);
        }
        return super.onSupportNavigateUp();
    }
//设置手机的左三角的返回操作
@Override
    public void onBackPressed() {
        onSupportNavigateUp();
    }
}
