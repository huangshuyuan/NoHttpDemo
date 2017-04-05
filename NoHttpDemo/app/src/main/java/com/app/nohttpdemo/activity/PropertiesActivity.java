package com.app.nohttpdemo.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.nohttpdemo.R;
import com.app.nohttpdemo.nohttp.HttpListener;
import com.app.nohttpdemo.nohttp.JavaBeanRequest;
import com.app.nohttpdemo.utils.PropertyUtils;
import com.app.nohttpdemo.utils.img.ShowImageUtils;
import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.RequestMethod;
import com.yanzhenjie.nohttp.rest.CacheMode;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;

import java.util.ArrayList;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PropertiesActivity extends BaseActivity {

    @BindView(R.id.text3)
    TextView text;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;


    @Override
    protected void onActivityCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_properties);
//        ButterKnife.bind(this);
        initView();
    }

    LinearLayoutManager mLinearLayoutManager;

    /**
     * 初始化recycleview
     */
    private void initView() {
        setTitle(R.string.title3);
        getMessage();
        mLinearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


    }

    private int HTTP_STATUS = 2;

    @OnClick(R.id.button)
    public void onClick() {
        getMessage();

    }

    private void getMessage() {
        //自定义网络请求

        Request<Map> request = new JavaBeanRequest<Map>(PropertyUtils.read(this, "ip", "test.properties"), RequestMethod.POST, Map.class);
        request.add(PropertyUtils.read(this, "key1", "test.properties"), PropertyUtils.read(this, "value1", "test.properties"));
        request.setCacheKey("Massage");//
        // 这里的key是缓存数据的主键，默认是url，使用的时候要保证全局唯一，否则会被其他相同url数据覆盖。
        request.setCacheMode(CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE);
        //设置为REQUEST_NETWORK_FAILED_READ_CACHE表示请求服务器失败，就返回上次的缓存，如果缓存为空才会请求失败。
        request(HTTP_STATUS, request, onResponseListener,  true, false);
    }

    ArrayList<Map> list;
    private HttpListener<Map> onResponseListener = new HttpListener<Map>() {
        @Override
        public void onSucceed(int what, Response<Map> response) {
            String string = response.isFromCache() ? getString(R.string.request_from_cache) : getString(R.string
                    .request_from_network);
//            if (what == HTTP_STATUS) {

                Map map = response.get();
                Map<String, Object> data = (Map<String, Object>) map.get("data");
                list = (ArrayList<Map>) data.get("infos");
                //请求成功
                text.setText("总共有：" + list.size() + "消息" + string);
                Logger.e("------------");
                Logger.e(list.size());
                Logger.e("--------------");
                recyclerView.setAdapter(new MyAdapter(PropertiesActivity.this, list));
                recyclerView.setLayoutManager(mLinearLayoutManager);
//            } else {
//                text.setText("没有数据");
//            }

        }

        @Override
        public void onFailed(int what, Response<Map> response) {
            text.setText("网络请求失败");
        }
    };


    public class MyAdapter extends RecyclerView.Adapter {
        ArrayList<Map> list;
        String fileName;
        Context c;


        public MyAdapter(Context c, ArrayList<Map> list, String fileName) {
            this.c = c;
            this.list = list;
            this.fileName = fileName;
        }

        public MyAdapter(Context context, ArrayList<Map> list) {
            this.c = context;
            this.list = list;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(c).inflate(R.layout.item_msg_layout, parent, false);
            return new ItemViewHolder(itemView);
        }


        public class ItemViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.title)
            TextView title;
            @BindView(R.id.content)
            TextView content;
            @BindView(R.id.image)
            ImageView image;

            public ItemViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);

            }


        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if (holder instanceof ItemViewHolder) {

                ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
                Map<String, String> str = list.get(position);

                itemViewHolder.title.setText(str.get("title"));
                itemViewHolder.content.setText(str.get("txt"));
                String path = PropertyUtils.getIP(c) + str.get("img");//图片路径
                //加载图片
                ShowImageUtils.showImageView(c, R.mipmap.ic_launcher, path, itemViewHolder.image);


            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }
}
