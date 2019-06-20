# EasyPay(易支付)

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/mit-license.php)

##Introduction（介绍）
This is a library for Developers easily to use Alipay,WechatPay and UnionPay in Android project.
EasyPay(易支付)集成并高度封装了Android平台的微信支付，支付宝支付以银联支付。使用此库，开发者可以使用简单轻松方便的api实现支付功能，大量节省集成配置时间。

易支付编码实现遵循设计模式六大原则，并且使用了单例以及策略模式来实现整个库,扩展性良好,可以轻松扩展其他支付方式如百度，美团等。支付方式类继承IPayStrategy接口，支付信息类实现IPayInfo接口，再分别传入场景类EasyPay中即可。具体请参照代码中支付宝或者微信或者银联支付方式封装。

------
``` java
##项目结构
├── activity
│   ├── UnionPayAssistActivity.java     //银联辅助Activity，负责调起银联支付接口以及接收回调。客户端无需关心。
│   └── WXPayEntryBaseActivity.java     //微信支付回调Activity封装。客户端需继承该Activity并实现getAppId()方法。
├── alipay
│   ├── AliPay.java                     //支付宝支付api封装，实现了IPayStrategy接口
│   ├── AlipayInfoImpli.java
│   └── AliPayResult.java
├── base
│   ├── IPayInfo.java                   //易支付支付信息基类接口
│   └── IPayStrategy.java               //易支付支付策略基类接口
├── callback
│   └── IPayCallback.java               //易支付统一回调接口
├── EasyPay.java                        //易支付场景类，客户端调用者
├── unionpay
│   ├── Mode.java
│   ├── UnionPay.java                   //银联支付api封装，，实现了IPayStrategy接口
│   └── UnionPayInfoImpli.java
└── wxpay
      ├── WXPay.java                      //微信支付api封装，实现了IPayStrategy接口
      └── WXPayInfoImpli.java

```
------

##Screenshot（截屏）

![screenshot.gif](https://github.com/kingofglory/EasyPay/blob/master/screensshot/screenshot.gif)

##Usage（使用）

###step 1

在build.gradle直接引用 :

	compile 'com.xgr.easypay:EasyPay:1.0.2'

	
下载库后作为module导入：


    compile project(':easypay')

Or Maven :

	<dependency>
      <groupId>com.xgr.easypay</groupId>
      <artifactId>EasyPay</artifactId>
      <version>1.0.2</version>
      <type>pom</type>
    </dependency>

###step 2

#### 银联支付:
##### 配置：无需配置
##### 编码：
``` java
    private void unionpay(){
        //实例化银联支付策略
        UnionPay unionPay = new UnionPay();
        //构造银联订单实体。一般都是由服务端直接返回。测试时可以用Mode.TEST,发布时用Mode.RELEASE。
        UnionPayInfoImpli unionPayInfoImpli = new UnionPayInfoImpli();
        unionPayInfoImpli.setTn("814144587819703061900");
        unionPayInfoImpli.setMode(Mode.TEST);
        //策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(unionPay, this, unionPayInfoImpli, new IPayCallback() {
            @Override
            public void success() {
                toast("支付成功");
            }

            @Override
            public void failed() {
                toast("支付失败");
            }

            @Override
            public void cancel() {
                toast("支付取消");
            }
        });
    }
```

#### 微信支付:
##### 配置：（具体可参考demo项目）
- 在你的项目包名(applicationId：com.xxx.xxx)目录下建立.wxapi（如com.xxx.xxx.wxapi）目             录。在目录下新建WXPayEntryActivity.java,继承WXPayEntryBaseActivity.java,实现getWXAppId()方法。
``` java
public class WXPayEntryActivity extends WXPayEntryBaseActivity {
    @Override
    public String getWXAppId() {
        return "appid";
    }
}
```
- 在AndroidManifest.xml中注册上述Activity.

``` xml
        <activity
            android:name=".wxapi.WXPayEntryActivity"
            android:exported="true"
            android:launchMode="singleTop"
            android:theme="@android:style/Theme.Translucent.NoTitleBar"/>
```
##### 编码：
``` java
    private void wxpay(){
        //实例化微信支付策略
        String wxAppId = "";
        WXPay wxPay = WXPay.getInstance(this,wxAppId);
        //构造微信订单实体。一般都是由服务端直接返回。
        WXPayInfoImpli wxPayInfoImpli = new WXPayInfoImpli();
        wxPayInfoImpli.setTimestamp("");
        wxPayInfoImpli.setSign("");
        wxPayInfoImpli.setPrepayId("");
        wxPayInfoImpli.setPartnerid("");
        wxPayInfoImpli.setAppid("");
        wxPayInfoImpli.setNonceStr("");
        wxPayInfoImpli.setPackageValue("");
        //策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(wxPay, this, wxPayInfoImpli, new IPayCallback() {
            @Override
            public void success() {
                toast("支付成功");
            }

            @Override
            public void failed() {
                toast("支付失败");
            }

            @Override
            public void cancel() {
                toast("支付取消");
            }
        });
    }
```

#### 支付宝支付:
##### 配置：无需配置。
##### 编码：
``` java
    private void alipay(){
        //实例化支付宝支付策略
        AliPay aliPay = new AliPay();
        //构造支付宝订单实体。一般都是由服务端直接返回。
        AlipayInfoImpli alipayInfoImpli = new AlipayInfoImpli();
        alipayInfoImpli.setOrderInfo("");
        //策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(aliPay, this, alipayInfoImpli, new IPayCallback() {
            @Override
            public void success() {
                toast("支付成功");
            }

            @Override
            public void failed() {
                toast("支付失败");
            }

            @Override
            public void cancel() {
                toast("支付取消");
            }
        });
    }
```

没错，就是这样，这就搞定了。

由于水平有限，难免会有错误。请大家多多指教。
有任何问题请在issues里面留言交流。[Issues](https://github.com/kingofglory/EasyPay/issues).

##Contact Me（联系我）
* Email : kingofglory@yeah.net
* Weibo : [@King的沉积时代](http://weibo.com/u/2255395234)

##License

	MIT License

	
	Copyright (c) 2017 kingofglory
	

	Permission is hereby granted, free of charge, to any person obtaining a copy
	of this software and associated documentation files (the "Software"), to deal
	in the Software without restriction, including without limitation the rights
	to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
	copies of the Software, and to permit persons to whom the Software is
	furnished to do so, subject to the following conditions:

	
	The above copyright notice and this permission notice shall be included in all
	copies or substantial portions of the Software.

	THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
	IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
	FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
	AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
	LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
	OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
	SOFTWARE.
