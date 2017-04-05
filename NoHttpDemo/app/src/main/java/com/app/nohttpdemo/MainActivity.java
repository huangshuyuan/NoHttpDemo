package com.app.nohttpdemo;

import android.app.Activity;
import android.os.Bundle;

import android.widget.TextView;

import com.app.nohttpdemo.activity.BaseActivity;
import com.app.nohttpdemo.applications.Httpconstant;
import com.app.nohttpdemo.model.Car;
import com.app.nohttpdemo.nohttp.HttpListener;
import com.app.nohttpdemo.nohttp.JavaBeanRequest;
import com.app.nohttpdemo.result.CarListResult;
import com.app.nohttpdemo.result.ResultCode;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv)
    TextView tv;




    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt)
    public void onClick() {
        Request<CarListResult> request = new JavaBeanRequest<CarListResult>(Httpconstant.REQ_ACTION_CAR_LIST, CarListResult.class);
        request(0, request, carListResultHttpListener, false, true);
    }

    private HttpListener<CarListResult> carListResultHttpListener = new HttpListener<CarListResult>() {
        @Override
        public void onSucceed(int what, Response<CarListResult> response) {
            System.out.println("成功");
            CarListResult carListResult = response.get();
            ;

            if (carListResult.getResultCode().equals(ResultCode.SUCCESS)) {
                tv.setText(carListResult.getCars()[0].getCAR_BH());
            }

        }

        @Override
        public void onFailed(int what, Response<CarListResult> response) {
            System.out.println("失败");
            showMessageDialog(R.string.request_failed, response.getException().getMessage());
        }
    };
}

