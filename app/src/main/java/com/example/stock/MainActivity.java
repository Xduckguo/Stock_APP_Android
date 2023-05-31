package com.example.stock;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
    //声明输入的股票代码
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
                //获取输入内容，.trim为去掉收尾空格
                mContent = editText.getText().toString().trim();
                if (mContent.isEmpty()){//判空
                    //设置对话框
                    AlertDialog dialog;
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                            .setTitle("输入的股票代码为空")
                            .setIcon(R.mipmap.ic_launcher)
                            .setMessage("请输入正确的股票代码")
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
                                    MainActivity.this.finish();
                                }
                            });
                    dialog = builder.create();
                    dialog.show();
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
                //将检查结果 check 封装成一个名为 "value" 的字符串，然后存储在一个 Bundle 对象中
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
            //得到返回数据
            String val = data.getString("value");
            //处理数据
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
    /**
     * 进行网络连接测试的方法
     *
     * @return String 返回字符串类型的请求结果
     * @throws IOException 如果网络连接过程中发生任何异常，则抛出 IOException 异常
     */
    //请求接口,进行网络检查
    public String check() throws IOException {
        String result = null;
        // 创建 OkHttpClient 对象来发送 HTTP 请求
        OkHttpClient httpClient = new OkHttpClient();
        // 定义请求
        Request request = new Request.Builder()
                .url(url+mContent)
                .build();
        try {
            // 定义请求
            Response response = httpClient.newCall(request).execute();
            // 获取服务器返回的响应结果，并将其转换为字符串格式
            result = response.body().string();
            System.out.println(result);
            // 返回响应结果字符串
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