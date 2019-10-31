# java 发送 Http 请求笔记

## http 代码

这几天使用 java 代码发送 http请求, 真的是搞得我焦头烂额. 在网上找了几十篇文档, 几乎都大同小异.

## 首先是网络上最通用的代码

```java
    public static void callUrl(@NonNull String json, @NonNull String url, Charset charset) throws IOException {
        // 请求url
        log.info("send json: {}", json);
        URL postUrl = new URL(url);

        log.info(charset.name());
        json = URLEncoder.encode(json, charset.name());
        // json = new String(json.getBytes(charset.name()), charset.name());

        // 打开连接
        HttpURLConnection connection = (HttpURLConnection) postUrl.openConnection();
        connection.setDoOutput(true);
        connection.setDoInput(true);
        connection.setRequestMethod("POST");    // 默认是 GET方式
        connection.setUseCaches(false);         // Post 请求不能使用缓存
        connection.setInstanceFollowRedirects(true);   //设置本次连接是否自动重定向
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        connection.setRequestProperty("Content-Type", "application/json;charset=utf-8");
        connection.addRequestProperty("Connection", "Keep-Alive");//设置与服务器保持连接
        connection.setRequestProperty("Accept-Language", "zh-CN,zh;0.9");

        // 连接
        connection.connect();
        try (DataOutputStream out = new DataOutputStream(connection.getOutputStream())) {
            // 这个地方还有个 out.writeBytes(json); 方法, 这个和 out.write(json.getBytes()) 真心不一样, 流传输的时候很可能导致乱码问题, 这个是我踩的一个坑.
            out.write(json.getBytes());
            //流用完记得关
            out.flush();
        }
        int responseCode = connection.getResponseCode();
        String response = IoUtils.inputStreamToString(connection.getInputStream(), charset);
        connection.disconnect();
        log.info("responseCode: {}, response: {}", responseCode, response);
    }
```

在网上找了几十篇文档, 几乎有一大半都是这种代码, 发送的时候使用输出流 `connection.getOutputStream()`, 接收的时候使用输入流 `connection.getInputStream()`, **这是最基本的代码也是最麻烦的代码, 同时也是速度最快的代码**, 但是用这个真的很麻烦, 而且功能很弱, 用着真心不爽.

## 知识

例如我们在使用 `java` 后台接收参数的时候, 一般都会使用 `request.getParameter()` 来获取参数, 简单粗暴, 可是在使用java做请求的时候却发现 `getParameter()` 总是获取不到值, 踩了无数坑之后终于找到原因.

首先对于普通的正常的能够通过 `request.getParameter()` 方法取到数据的方法来说, 调用之前 paramterMap 没有值, 但调用 getParamterMap() 之后, 发现有值, 说明在其它地方取到了值, 研究源码发现是 在输入流 InputStream 中取到了数据, 也就是说 `request.getParameter()` 底层实际上还是通过操作输入输出流的方式取得数据.

后台在收到请求后, 并不会事先将流中的数据直接赋给 `ParameterMap`, 因为流中的数据格式多种多样, 框架事先也不知道你会怎么用.

所以你可以通过 `request.getInputStream()` 来获取数据, 也可以通过 `request.getParameter()` 来获取数据, 但是要注意 **对于输入输出流来讲, 流中的数据只能用一次, 用了之后就没了**, 调了`getParameter` 系列方法, 那么就 `getInputStream()` 就无法获取数据了.

可是在使用的过程中, 我们发现, 在没有调用 `request.getInputStream()` 方法获取流的情况下, `request.getParameter()` 依然获取不到数据.

一般Http默认请求是 `application/x-www-form-urlencoded`, 

其实对于 `post` 方法来说, 接收到请求之后是不会有值的

HttpServletRequest 的 getInputStream 方法和 getParameter 方法

根据Servlet开发规范，需要满足下列条件，调用 `request.getParameter()` 框架才会将数据填充到`request` 的 `parameterMap` 中.

1. 这是一个HTTP/HTTPS请求.
2. 请求方法是POST（querystring无论是否POST都将被设置到parameter中）.
3. 请求的类型（Content-Type头）是 `application/x-www-form-urlencoded`.
4. `request.getInputStream()` 没有被调用过.

上述条件都满足之后就可以发现使用 `request.getParameter()`, 就可以正常获取数据了, 如果使用 `Spring` 的话, `@RequestParam("text") String text` 形式的注解参数就可以愉快的获取到数据了.

## 告诫使用

下面放一个 `post` 请求实例, 使用的是 `org.apache.http` 里面的包, apache 里面的方法真的很好用(事实上理解之后, 会发现很好用, 不理解的话会感觉整个http请求乱七八糟), 下面一个静态方法`callUrlWithPost(String url, Map<String, String> map)` 就能够实现 post **简单**请求的轻松调用, 并且能够使用 `request.getParameter()` 正常获取数据.

```java
package cn.cpf.exercise;

import com.google.common.collect.Lists;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <b>Description : </b>
 *
 * @author CPF
 * @date 2019/9/25 20:38
 **/
public class Test2 {

    /**
     * @param url 请求的Url
     * @param map paramterMap
     */
    public static String callUrlWithPost(String url, Map<String, String> map) throws IOException {
        List<BasicNameValuePair> list = castBasicNameValuePairList(map);
        // UrlEncodedFormEntity 很方便作为表单数据参数对象
        UrlEncodedFormEntity encodedFormEntity = new UrlEncodedFormEntity(list);
        HttpPost post = new HttpPost(url);
        post.setEntity(encodedFormEntity);
        // 默认就是 "application/x-www-form-urlencoded"
        post.setHeader("Content-type", "application/x-www-form-urlencoded");
        HttpResponse response = HttpClientBuilder.create().build().execute(post);
        return EntityUtils.toString(response.getEntity());
    }

    /**
     * MAP类型数组转换成 List<BasicNameValuePair>
     *
     * @param params MAP类型数组
     * @return NameValuePair类型数组
     */
    public static List<BasicNameValuePair> castBasicNameValuePairList(Map<String, String> params) {
        List<BasicNameValuePair> list = Lists.newArrayListWithExpectedSize(params.size());
        for (Map.Entry<String, String> entry : params.entrySet()) {
            list.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        return list;
    }


    public static void main(String[] args) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("key1", "haHa");
        map.put("key2", "heHe");
        String result = callUrlWithPost("url", map);
        System.out.println(result);
    }
}
```

> 注意点 :
> `post.setEntity()` 方法就是 `post` 请求的请求体, 请求体里面有各种类型, 
>

接下来再爆一个我踩得坑, 这个踩的我特别痛, 部门经理让我从一个前台页面找一个url调用的请求, 并在后台封装为一个方法, 然后我通过chrome 获取到url, 请求参数格式后, 在postman里面发起请求, 然后成功接收正确数据, 之后我就开始封装后台java方法, 然后..., 大致如下面的代码, 之后测试发出请求, 然后总接收不到正确的数据. `"Content-type", "application/json"` 设置了, post方法各种也没问题. 但结果就是postman上面可以, java代码就不行. 结果搞了一个下午, 查了几十篇文档, 最后通过看源码, 搞了出来. 

首先 `postman` 里面 `header`头里面什么都没加, 请求体body设置 `raw`, `application/json`, `header`里面多了一个 `"Content-type", "application/json"`, 然后我就写了如下的java代码, 

最后发现不仅需要 `post.setHeader("Content-type", "application/json");`, 关键是请求体body设置 `raw`, `application/json`也需要对应一个 application/json, 
后来将 `StringEntity postingString = new StringEntity(json.toJSONString(), ContentType.APPLICATION_JSON);` 发现数据能够成功调用了.

```java
   JSONObject json = new JSONObject();
   json.put("text", teext);
   StringEntity postingString = new StringEntity(json.toJSONString());

   HttpClient httpClient = HttpClientBuilder.create().build();
   HttpClientBuilder.create().build();
   HttpPost post = new HttpPost(url);
   post.setEntity(postingString);
   post.setHeader("Content-type", "application/json");
   HttpResponse response = httpClient.execute(post);

   String content = EntityUtils.toString(response.getEntity());
   System.out.println(content);
```

StringEntity(String string, ContentType contentType)

> new StringEntity(json.toJSONString()) 是什么鬼, 如果后面有` ContentType contentType`, 还不如去掉 `StringEntity(String string)` 这个单参数方法, 必须填写 `ContentType contentType`, 不然就加一个过时注解 `@Deprecated`, 否则这种不常用的构造方法, 谁知道怎么用, 
 
源码 : `org.apache.http.entity.StringEntity`

```java

    public StringEntity(String string) throws UnsupportedEncodingException {
        this(string, ContentType.DEFAULT_TEXT);
    }

    public StringEntity(String string, ContentType contentType) throws UnsupportedCharsetException {
        Args.notNull(string, "Source string");
        Charset charset = contentType != null ? contentType.getCharset() : null;
        if (charset == null) {
            charset = HTTP.DEF_CONTENT_CHARSET;
        }

        this.content = string.getBytes(charset);
        if (contentType != null) {
            this.setContentType(contentType.toString());
        }

    }

```
