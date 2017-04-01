@@ -1,83 +1 @@
# NohttpDemo
github 项目源码： https://github.com/yanzhenjie/NoHttp

开发文档：http://doc.nohttp.net/162186

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
#### 请求数据格式
```
StringRequest

JsonObjectRequest

JsonArrayRequest

BitmapRequest

ByteArrayRequest

自定义请求FastJson、JavaBean

```

#### 指定Request的Method
请求方法需要在构造的时候指定，下面以StringRequest举例说明：

* GET
如果不传入第二个参数，默认为GET方法，当然你也可以选择传入：
```
Request<String> stringReq = NoHttp.createStringRequest(url);
// 或者
Request<String> stringReq = NoHttp.createStringRequest(url , RequestMethod.GET);
```

* POST
```
Request<String> stringReq = NoHttp.createStringRequest(url , RequestMethod.POST);

```


#### 同步请求
Android同步网络请求就是在当前线程发起请求。当然同步请求不能直接在主线程使用，所以一般是在子线程使用这种方法。

NoHttp的核心还是同步请求，下一章要讲的异步请求也是基于同步请求的。

这里以StringRequest为例：
```
// 创建请求。
Request<String> request = NoHttp.createStringRequest(url, RequestMethod.GET);

// 调用同步请求，直接拿到请求结果。
Response<String> response = NoHttp.startRequestSync(request);
```

#### 异步请求

NoHttp的核心还是同步请求，本章要讲的异步请求也是基于同步请求的。

NoHttp的异步请求是在同步请求的基础上用任务队列 + Handler实现的，如果你想更好的理解NoHttp，更好的使用队列的特性，强烈建议看这里。

* 异步请求的步骤

* 1、创建队列（队列特性讲解点我）

```
RequestQueue queue = NoHttp.newRequestQueue();
```
* 2、创建请求

```

Request<String> request = new StringRequest(url);

// 添加url?key=value形式的参数
request.add("enName", "yanzhenjie");
request.add("zhName", "严振杰");
request.add("website", "http://www.yanzhenjie.com");
```


*　3、添加请求到队列

```
queue.add(0, request, new OnResponseListener<String>(){

    @Override
    public void onSucceed(int what, Response<String> response) {
        if(response.responseCode() == 200) {// 请求成功。
            String result = response.get();
        }
    }

    @Override
    public void onFailed(int what, Response<String> response) {
        Excepition exception = response.getException();
        if(exception instanceof NetworkError) {// 网络不好。
        }

        // 这里还有很多错误类型，可以看demo：
        https://github.com/yanzhenjie/NoHttp
        ...
    }

        @Override
    public void onStart(int what) {
        // 这里可以show()一个wait dialog。
    }

    @Override
    public void onFinish(int what) {
        // 这里可以dismiss()上面show()的wait dialog。
    }
});
```
这里对其中queue.add(what, request, listener)中的what做个说明，任意添加多个请求到队列时，使用同一个Listener接受结果，listener的任何一个方法被回调时都会返回在添加请求到队列时写的相应what值，可以用这个what来区分是哪个请求的回调结果，你可以理解为它的作用和handler的what一样的作用，就是用来区分信号来源的。

这样做的好处是不像其它框架一样，每个请求都new listener()来接受结果，这样及省了代码量，又让代码更加整洁。

当然如果你不想这么用，那么你可以每个请求都new listener()。




