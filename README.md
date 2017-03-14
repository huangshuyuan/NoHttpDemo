# NohttpDemo
github 项目源码 https://github.com/yanzhenjie/NoHttp

##使用方法

Gradle

如果使用HttpURLConnection作为网络层：

```
compile 'com.yanzhenjie.nohttp:nohttp:1.1.1'
```

如果要使用OkHttp作为网络层，请再依赖：

```
compile 'com.yanzhenjie.nohttp:okhttp:1.1.1'
```

####初始化

NoHttp初始化需要一个Context，最好在Application的onCreate()中初始化，记得在manifest.xml中注册Application。

####高级自定义初始化

* 配置超时毫秒数，默认10 * 1000ms
```
NoHttp.initialize(this, new NoHttp.Config()
    // 设置全局连接超时时间，单位毫秒
    .setConnectTimeout(30 * 1000)
    // 设置全局服务器响应超时时间，单位毫秒
    .setReadTimeout(30 * 1000)
);
```

* 配置缓存，控制开关
```
NoHttp.initialize(this, new NoHttp.Config()
    ...
    .setCacheStore(
        // 保存到数据库
        new DBCacheStore(this).setEnable(true) // 如果不使用缓存，设置false禁用。
        // 或者保存到SD卡：new DiskCacheStore(this)
    )
);
```

* 配置Cookie保存的位置，默认保存在数据库
```
NoHttp.initialize(this, new NoHttp.Config()
    ...
    // 默认保存数据库DBCookieStore，开发者也可以自己实现CookieStore接口。
    .setCookieStore(
        new DBCookieStore(this).setEnable(false) // 如果不维护cookie，设置false禁用。
    )
);
```

* 配置网络层
```
NoHttp.initialize(this, new NoHttp.Config()
    ...
    // 使用HttpURLConnection
    .setNetworkExecutor(new URLConnectionNetworkExecutor())
    // 或者使用OkHttp
    // .setNetworkExecutor(new OkHttpNetworkExecutor())
);
```

* 需要的权限
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.INTERNET" />
<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
<uses-permission android:name="android.permission.ACCESS_WIFI_STATE" />
```
* 友好的调试模式
```

Logger.setDebug(true);// 开启NoHttp的调试模式, 配置后可看到请求过程、日志和错误信息。
Logger.setTag("NoHttpSample");// 设置NoHttp打印Log的tag。
```
