package com.example.stock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private String url = "http://qt.gtimg.cn/q=";
    private String mContent;
    private Button main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//视图绑定
        //找控件
        EditText editText = findViewById(R.id.editText_stockId);
        RecyclerView recyclerView = findViewById(R.id.listView);
        //设置点击事件
        findViewById(R.id.btnCheck).setOnClickListener( new View.OnClickListener() {
            //获取输入内容
            @Override
            public void onClick(View view) {
                //获取输入内容
                mContent = editText.getText().toString().trim();
                if (mContent.isEmpty()){//判空
                    return;
                }
                 new Thread(networkTask).start();//开子线程
            }
        });
        recyclerView.setAdapter(adapter);//绑定适配器

        main=findViewById(R.id.button);//寻找退出按钮id
        main.setOnClickListener(this);//给退出按钮安装监听器
    }

    public void  onClick(View view){
        Intent intent=new Intent(MainActivity.this, loginActivity.class);
        startActivity(intent);
    }


    //线程执行
    Runnable networkTask = new Runnable() {
        @Override
        public void run() {
            try {
                String check = check();
                Message msg = new Message();
                Bundle data = new Bundle();
                data.putString("value", check);
                msg.setData(data);
                handler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };
    //切换主线程
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle data = msg.getData();
            String val = data.getString("value");
            String a = val.substring(13,val.length()-2);
            String[] b = a.split("~");
            System.out.println(val);
            System.out.println(a);
            System.out.println(b[1]);
            System.out.println(b[3]);
            System.out.println(b[4]);
            String c = b[1]+"                             "+b[3]+"                         "+b[4];
            adapter.getData().add(c);//添加数据
            adapter.notifyDataSetChanged();//刷新适配器
        }
    };

    //请求接口
    public String check() throws IOException {
        String result = null;
        OkHttpClient httpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url+mContent)
                .build();
        try {
            Response response = httpClient.newCall(request).execute();
            result = response.body().string();
            System.out.println(result);
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }


    //创建适配器
    private CommonAdapter<String> adapter = new CommonAdapter<String>(R.layout.item_main) {
        @Override
        public void bind(ViewHolder holder, String user, int position) {
            holder.setText(R.id.tvContent, user);
        }
    };
}