package com.example.stock;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class loginActivity extends AppCompatActivity {
    public Button btn;
    //分别声明布局文件中用到的变量
    private EditText account;
    private EditText password;
    private CheckBox remember;
    private Button login;
    //声明数据存储对象
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        //所有初始化封装到此方法中
        initView();
    }

    private void initView(){
        pref= PreferenceManager.getDefaultSharedPreferences(this);
        //绑定控件，这样变量就代表了被绑定的控件
        account=findViewById(R.id.et_account);
        password=findViewById(R.id.et_password);
        remember=findViewById(R.id.remember);
        login=findViewById(R.id.login);
        //设置背景
        View v = findViewById(R.id.loginCL);//找到你要设透明背景的layout 的id
        v.getBackground().setAlpha(70);
        //获取键为remember_password的值，若不存在则为false
        boolean isRemember=pref.getBoolean("remember_password",false);
        if(isRemember){
            //将账号和密码都设置到文本框中
            String acc=pref.getString("account","");
            String pass=pref.getString("password","");
            account.setText(acc);
            password.setText(pass);
            //更改记住密码状态
            remember.setChecked(true);
        }

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ed_account = account.getText().toString();
                String ed_password = password.getText().toString();
                //如果账号是admin，密码是123456，就认为登录成功
                if (ed_account.equals("admin") && ed_password.equals("123456")) {
                    Intent intent=new Intent(loginActivity.this, MainActivity.class);
                    startActivity(intent);
                    editor = pref.edit();
                    if (remember.isChecked()) {
                        //若复选框被选中，则保存账号和密码到pref中
                        editor.putBoolean("remember_password", true);
                        editor.putString("account", ed_account);
                        editor.putString("password", ed_password);
                    } else {
                        editor.clear();
                    }
                    //提交偏好数据并修改
                    editor.apply();
                    Toast.makeText(loginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                } else {
                    //设置对话框
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(loginActivity.this)
                            .setTitle("账号或密码输入有误")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("请输入正确的账号和密码")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                   loginActivity.this.finish();
                                }
                            });
                    dialog = builder.create();
                    dialog.show();
                }
            }
        });
    }

}


