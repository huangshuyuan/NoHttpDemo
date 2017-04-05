package com.app.nohttpdemo.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.app.nohttpdemo.MainActivity;
import com.app.nohttpdemo.R;
import com.app.nohttpdemo.utils.DisplayUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WelActivity extends AppCompatActivity {
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.rv_start_activity)
    LinearLayout linearLayout;
    @BindView(R.id.toolbar_root)
    ViewGroup mToolbarRoot;
    private int headViewSize;

    @BindView(R.id.iv_head_background)
    ImageView mIvHeadBackground;
    @BindView(R.id.iv_toolbar_head)
    ImageView mIvToolbarHead;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DisplayUtils.initScreen(this);
        headViewSize = DisplayUtils.dip2px(42);
        setContentView(R.layout.activity_wel);
        ButterKnife.bind(this);

        mIvHeadBackground.getLayoutParams().height = DisplayUtils.screenWidth * 12 / 13;
        mIvHeadBackground.requestLayout();

        // 让toolbar下来。
        boolean isHigh = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
        if (isHigh) {
            ViewGroup mContentView = (ViewGroup) findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                ViewCompat.setFitsSystemWindows(mChildView, false);
            }
        }
        ((ViewGroup.MarginLayoutParams) mToolbarRoot.getLayoutParams()).setMargins(-headViewSize, isHigh ?
                DisplayUtils.statusBarHeight : 0, 0, 0);

        mAppBarLayout.addOnOffsetChangedListener(offsetChangedListener);
    }

    /**
     * AppBarLayout的offset监听。
     */
    private AppBarLayout.OnOffsetChangedListener offsetChangedListener = (appBarLayout, verticalOffset) -> {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(verticalOffset) / (float) maxScroll;
        mIvToolbarHead.setAlpha(percentage);

        int background = (int) (250 * percentage);
        mToolbarRoot.getBackground().mutate().setAlpha(background);

        int realSize = (int) (headViewSize * percentage);
        mToolbarRoot.setTranslationX(realSize);
    };


    @OnClick(R.id.wel)
    public void onClick() {
        startActivity(new Intent(this, MainActivity.class));
//        finish();
    }

    @OnClick(R.id.wel2)
    public void onClick2() {
        startActivity(new Intent(this, TestActivity.class));
//        finish();
    }


    @OnClick(R.id.wel3)
    public void onClick3() {
        startActivity(new Intent(this, ButterKnifeActivity.class));
//        finish();
    }

    @Override
    public void onBackPressed() {
        finish();
        Process.killProcess(Process.myPid());
    }

    @OnClick(R.id.wel4)
    public void onClick4() {
        startActivity(new Intent(this, PropertiesActivity.class));
    }

    @OnClick(R.id.wel5)
    public void onClick5() {
        startActivity(new Intent(this, ImageActivity.class));
    }
}
