## EasyPay(易支付)---- 一个便捷易用的 Android 平台聚合支付框架

[![License](https://img.shields.io/badge/license-MIT-blue.svg)](https://opensource.org/licenses/mit-license.php)

[项目GitHub链接](https://github.com/kingofglory/EasyPay)

## Vision (愿景)
成为 Android 平台最便捷易用的支付框架

------

## Introduction（介绍）
EasyPay(易支付)集成并高度封装了Android平台的微信支付，支付宝支付以银联支付。使用此库，开发者可以使用简单轻松方便的 Api 实现支付功能，大量节省集成配置时间。

This is a library for Android Developers easily to use Alipay, WechatPay and UnionPay in Android project.

------

着急使用请直接去看下面的 **Usage(使用)**

## Why （为什么有这个库）

1. 支付平台众多。微信、支付宝、银联、京东、美团、百度等等，需要阅读各家文档，耗费大量时间精力。
2. 集成步骤繁琐。集成过微信支付都知道，必须在包名下配置 ${applicationId}.wxapi.WXPayEntryActivity。。。
3. Api 调用方式不一致。同样的功能，代码差异很大，增加理解成本。
4. 支付结果接收逻辑分散。调用方法与接受结果代码不在同一个地方，逻辑分散，可读性和可维护性差。

## Think (思考与期望)

我们能不能有一种多合一的库，可以省去纷繁的集成步骤，以统一的集成方式，统一的Api调用，统一的回调接收逻辑，同时兼具轻松扩展新的支付方式的能力，以满足支付功能需求。
我给出的答案就是 EasyPay。EasyPay 已诞生三年，使用其实现支付功能的App超3000+，以其极简单的Api实现了多种支付功能得到开发者认可。

## How (如何解决）

1. EasyPay 用巧妙方式省去了微信支付必须配置wxapi.WXPayActivity的步骤，在客户端甚至你都不需要配置微信AppId
2. EasyPay 分别封装了微信支付、支付宝支付、银联支付，单独成库，按需集成，所有库集成均一句代码完成
3. EasyPay 统一支付调用接口，统一支付结果接收回调，调用代码与接收结果代码集中在一起
4. EasyPay 预留扩展其他支付方式接口
5. EasyPay 甚至抽空用反射解决了银联SDK因持有静态Context导致的内存泄漏问题（该问题三年前我反映后六个月也没解决。。。，所以只有自己动手，丰衣足食）
6. EasyPay 让你省出时间陪女朋友，哈哈哈哈哈哈（if 没有 那省出时间让你去找啊，还指望我给你new 一个对象啊。。。）

上帝说：“有要图”。于是，就有了下面这张图：）手动滑稽
## Screenshot（支付效果图）

![screenshot.gif](https://github.com/kingofglory/EasyPay/blob/master/screensshot/screenshot.gif)

------

## Usage（使用）
使用步骤非常简单，总共两步：

1.集成依赖库；

2.相关支付Api调用。

### 使用步骤一、 集成依赖库
集成方式有以下两种，根据需要选择其中一种集成即可：

1. **jcenter 集成方式**
2. **下载源码作为Module导入集成方式**


#### 1. jcenter集成方式

在 Project中主 App 模块中的 build.gradle 的dependencies 块中添加以下依赖。EasyPay 基类库为**必选**， wxchatpay, alipay, unionpay根据业务需要自行选择接入。

```java
implementation 'com.xgr.easypay:EasyPay:2.0.4'   // 基类库，必选
implementation 'com.xgr.easypay:wechatpay:2.0.4' // 微信支付，可选
implementation 'com.xgr.easypay:alipay:2.0.4'    // 支付宝支付，可选
implementation 'com.xgr.easypay:unionpay:2.0.4'  // 银联支付，可选
```

jcenter 集成方式到此结束。

#### 2. 下载源码后作为module导入：

#### 1) 集成基类依赖库（必选）：
```java
implementation project(':easypay')   // 基类库，必选
implementation project(':wechatpay') // 微信支付，可选
implementation project(':alipay')    // 支付宝支付，可选
implementation project(':unionpay')  // 银联支付，可选
```
下载源码作为Module导入集成方式到此结束。

------

### 使用步骤二、相关支付Api调用
本步骤将服务端生成的订单相关信息传入相关支付api。

#### 微信支付（共一步）
``` java
    private void wxpay(){
        //实例化微信支付策略
        WXPay wxPay = WXPay.getInstance();
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
            public void failed(int code, String msg) {
                toast("支付失败");
            }

            @Override
            public void cancel() {
                toast("支付取消");
            }
        });
    }
```
微信支付到此结束

#### 支付宝支付（共一步）
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
            public void failed(int code, String msg) {
                toast("支付失败");
            }

            @Override
            public void cancel() {
                toast("支付取消");
            }
        });
    }
```
支付宝支付到此结束

#### 银联支付（共一步）
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
            public void failed(int code, String msg) {
                toast("支付失败");
            }

            @Override
            public void cancel() {
                toast("支付取消");
            }
        });
    }
```

银联支付到此结束

------

## 项目实现介绍
易支付编码实现遵循设计模式六大原则，并且使用了单例以及策略模式来实现整个库,扩展性良好,可以轻松扩展其他支付方式。

``` java
├── activity
│   ├── UnionPayAssistActivity.java     //银联辅助Activity，负责调起银联支付接口以及接收回调。客户端无需关心。
│   └── WXPayEntryBaseActivity.java     //微信支付回调Activity封装。客户端需继承该Activity并实现getAppId()方法。
├── alipay
│   ├── AliPay.java                     //支付宝支付api封装，实现了IPayStrategy接口
│   ├── AlipayInfoImpli.java
│   └── AliPayResult.java
│   └── ResultCode.java
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
│   └── UnionPayErrCode.java
└── wxpay
      ├── WXPay.java                      //微信支付api封装，实现了IPayStrategy接口
      └── WXPayInfoImpli.java
      └── WXErrCodeEx.java

```
------

## 框架扩展新的支付平台（如美团、京东等其他支付）
EasyPay从立项之初，就充分考虑了代码扩展性，启用策略模式，全部采用面向接口编程，遵循依赖倒置设计原则。从支付基类扩展出新的支付非常容易。仅需三步。下面给出参考步骤。更具体请参照项目中支付宝或者微信或者银联支付方式封装。
### 1) 支付订单信息类实现IPayInfo接口
```java
public class XXpayInfoImpli implements IPayInfo {
    public xxType xxField = xxx;
    public yyTYpe xxFiled = yyy;
    ...other Field
}
```
### 2) 支付策略类实现IPayStrategy。
将第一步中支付实体类传入泛型。支付策略的初衷是将某种支付所有操作都进行集中封装，凡是业务需要用到该支付的地方，都调用这个类即可。
```java
public class XXPay implements IPayStrategy<XXpayInfoImpli> {
    private AlipayInfoImpli alipayInfoImpli;
    private static IPayCallback sPayCallback;

    @Override
    public void pay(Activity activity, AlipayInfoImpli payInfo, IPayCallback payCallback) {
        this.mActivity = activity;
        this.alipayInfoImpli = payInfo;
        sPayCallback = payCallback;
    }

    ...other method
}
```
完成上述两步后，根据业务在需要地方调用即可，需要注意是当某支付平台支付回调比较分散时，可在对应地方将调用转发给上述支付类即可。这样，可以将逻辑集中到一个类处理。如不理解这段话，可以看银联支付UnionPayAssistActivity中的onActivityResult()方法，就将逻辑转给 UnionPay.handleResult(this,data)处理了。

### 3）调用Api
```java
        //实例化支付策略
        XXpay xxPay = new XXPay();
        //构造支付宝订单实体。一般都是由服务端直接返回。
        XXpayInfoImpli xxpayInfoImpli = new XXpayInfoImpli();
        xxpayInfoImpli.setXXFiled();
        ...
        //策略场景类调起支付方法开始支付，以及接收回调。
        EasyPay.pay(xxPay, this, xxpayInfoImpli, new IPayCallback() {
            @Override
            public void success() {
                toast("支付成功");
            }

            @Override
            public void failed(int code, String message) {
                toast("支付失败");
            }

            @Override
            public void cancel() {
                toast("支付取消");
            }
        });
```
扩展介绍到此结束

------

## (ChangeLog) 更新日志

#### v2.0.4 更新 （2020/05/24)
1. 更新银联信赖库，补全armeabi-v7a, arm64-v8a以及x86_64架构so文件

#### v2.0.3 更新 （2020/05/23)
1. 解决支付宝PayTask找不到类

#### v2.0.2 更新 （2020/05/10)
1. 实现微信支付零配置

#### v2.0.1 更新 （2020/01/18)
1. Fixed issue [Kotlin项目中调用支付回调失败时Msg为空会报错](https://github.com/kingofglory/EasyPay/issues/4)
2. 将支付宝依赖库从impletation引用方式改为 api 引用。以便在库外部也可以引用相关类。
3. 添加微信和支付宝混淆配置。

#### v2.0.0更新(2019/10/27)
1. 极限地精简微信支付集成和使用步骤，并更新微信支付SDK
2. 更新支付宝SDK
3. 更新银联SDK
4. 支付回调fail方法返回code和message

------

## 联系我

#### 1) 有问题提[Issues](https://github.com/kingofglory/EasyPay/issues)。欢迎大家交流想法。

#### 2) 邮箱联系(Email : kingofglory@yeah.net)

#### 3) 限时免费加QQ群（群号：797559567）

感谢大家，希望一起起步。

------

## 致谢

在EasyPay发布以来，有不少开发者提出过宝贵意见，还有一些动手能力强的提交了优化的pr，在此表示万分感谢，很高兴你们深入地去使用了这个项目，并且提出了自己的想法，让EasyPay变得更好。以下排名仅根据提交pr时间， 不分先后。

### 1. [HadesHe](https://github.com/HadesHe)
### 2. [JasonHezz](https://github.com/JasonHezz)

## 谁在用EasyPay

发布以来，EasyPay在jcenter下载量为3000+，我本想在这里罗列一些使用者应用，不过联系到的人很少，希望使用了EasyPay的开发者联系上，在这项加上你们的应用，增加可信度，让EasyPay帮助到更多开发者，节省更多时间。

 图标 | 名字 | 链接 
-|-|-
![我一点都不可口](https://img.tapimg.com/market/lcs/ccd6c2a135ded0ecd74c9302b6162dd8_360.png?imageMogr2/auto-orient/striphttps://img.tapimg.com/market/lcs/ccd6c2a135ded0ecd74c9302b6162dd8_360.png?imageMogr2/auto-orient/strip) | 我一点都不可口 | https://www.taptap.com/app/42311

## License

	MIT License

	
	Copyright (c) 2020 kingofglory
	

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
