package com.example.stock;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity implements  CompoundButton.OnCheckedChangeListener{
    //可以监听多选或单选按钮的更改事件
    private Button zc,dl;//声明注册、登录按钮的变量
    CheckBox cb1,cb2,cb3;//声明复选框1,2,3的变量
    EditText et1,et2;//声明输入文本框1,2的变量
    TextView tv;//声明结果文本的变量


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        View v = findViewById(R.id.CL);//找到你要设透明背景的layout 的id

        v.getBackground().setAlpha(70);

        zc=findViewById(R.id.button);//寻找注册按钮id
        dl=findViewById(R.id.button1);//寻找注册按钮id
        cb1=findViewById(R.id.cb1);//寻找复选框1控件id
        cb1.setOnCheckedChangeListener(this);//给复选框控件1安装监听器
        cb2=findViewById(R.id.cb2);//寻找复选框2控件id
        cb2.setOnCheckedChangeListener(this);//给复选框控件2安装监听器
        cb3=findViewById(R.id.cb3);//寻找复选框3控件id
        cb3.setOnCheckedChangeListener(this);//给复选框控件3安装监听器
        et1=findViewById(R.id.et1);//寻找输入框1控件id
        et2=findViewById(R.id.et2);//寻找输入框2控件id
        tv=findViewById(R.id.tv);//寻找返回结果文本控件id

        //注册按钮实现交互功能
        zc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View view){
                String strname=et1.getText().toString();//获取用户名(ID绑定用户名)
                String strPassword=et2.getText().toString();//获取密码(ID绑定密码)

                if (strname.equals(" ")||strPassword.equals(""))//判断用户名或密码是否为空
                    tv.setText("注册失败，请重新修改信息后再来注册");//否则执行结果文本框输出内容为"注册失败，请重新修改信息后再来注册"
                else
                {
                    tv.setText(strname+"注册成功");//如果满足条件的话执行结果文本框输出内容为"注册成功"
                    Intent intent=new Intent(RegisterActivity.this, loginActivity.class);
                    startActivity(intent);
                }
            }

        });
         //进入登录页面
        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void  onClick(View view){
                Intent intent=new Intent(RegisterActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });

    }

    //实现复选框交互功能
    @Override                      //CompoundButton选中或未选中按钮
    public void onCheckedChanged(CompoundButton CompoundButton, boolean b) {
        switch (CompoundButton.getId()) //得到选中或未选中按钮id
        {
            case R.id.cb1: //复选框1id
                if (b==true)//判断复选框1是否为真
                    System.out.println(cb1.getText().toString());
                //如果是真执行复选框按钮输出的结果是得到该文本（cb1对应的text属性文本字符串）字符串
                break;
            case R.id.cb2:
                if (b==true)
                    System.out.println(cb2.getText().toString());
                break;

            case R.id.cb3:
                if (b==true)
                    System.out.println(cb3.getText().toString());
                break;
        }
    }
}
