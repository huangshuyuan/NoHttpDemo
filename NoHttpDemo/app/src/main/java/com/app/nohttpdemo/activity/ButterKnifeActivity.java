package com.app.nohttpdemo.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.app.nohttpdemo.R;
import com.app.nohttpdemo.tools.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import butterknife.Unbinder;

public class ButterKnifeActivity extends BaseActivity {
    /*绑定组件*/
    @BindView(R.id.text)
    TextView textView;

    @BindView(R.id.button)
    public Button button;
/*
    注意：button 的修饰类型不能是：private 或者 static 。
     否则会报错：错误: @BindView fields must not be private or static. (com.zyj.wifi.ButterknifeActivity.button1)*/


    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_butter_knife);
///*   绑定activity*/
//        ButterKnife.bind(this);

// TODO: add setContentView(...) invocation
        Unbinder bind = ButterKnife.bind(this);
    }


    @OnClick({R.id.text, R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text:
                Toast.show(ButterKnifeActivity.this, "hello");
                break;

            case R.id.button:
                Toast.show(ButterKnifeActivity.this, "hello2");
                break;
        }
    }

//    /*点击事件*/
//
//    @OnClick(R.id.button)
//    public void button() {
//        Toast.show(ButterKnifeActivity.this, "hello");
//    }

    /*长按事件*/
    @OnLongClick(R.id.button)
    public boolean buttonlong() {
        Toast.show(ButterKnifeActivity.this, "哈哈哈");
        return true;
    }

}
