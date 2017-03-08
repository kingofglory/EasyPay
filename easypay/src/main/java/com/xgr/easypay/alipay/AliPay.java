/*
******************************* Copyright (c)*********************************\
**
**                 (c) Copyright 2017, King, china
**                          All Rights Reserved
**                                
**                               By(King)
**                         
**------------------------------------------------------------------------------
*/
package com.xgr.easypay.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.xgr.easypay.base.IPayStrategy;
import com.xgr.easypay.callback.IPayCallback;

/**
 * 文 件 名: AliPay
 * 创 建 人: King
 * 创建日期: 2017/2/13 17:36
 * 邮   箱: mikey1101@163.com
 * 博   客: www.smilevenus.com
 * @see <a href="https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.OIz6cp&treeId=59&articleId=104352&docType=1">Des</a>
 */
public class AliPay implements IPayStrategy<AlipayInfoImpli> {

    private static final int SDK_PAY_FLAG = 6406;
    private Activity mActivity;
    private AlipayInfoImpli alipayInfoImpli;
    private static IPayCallback sPayCallback;

    @Override
    public void pay(Activity activity, AlipayInfoImpli payInfo, IPayCallback payCallback) {
        this.mActivity = activity;
        this.alipayInfoImpli = payInfo;
        sPayCallback = payCallback;
        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                // 构造PayTask 对象
                PayTask alipay = new PayTask(mActivity);
                // 调用支付接口，获取支付结果
                String result = alipay.pay(alipayInfoImpli.getOrderInfo(), true);
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    @SuppressLint("HandlerLeak")
    private static Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    AliPayResult payResult = new AliPayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档:
                    //https://doc.open.alipay.com/docs/doc.htm?spm=a219a.7629140.0.0.IXE2Zj&treeId=59&articleId=103671&docType=1
                    if (TextUtils.equals(resultStatus, "9000")) {
                        if (sPayCallback != null) {
                            sPayCallback.success();
                        }
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {

                        }else if(TextUtils.equals(resultStatus, "6001")){
                            if(sPayCallback != null){
                                sPayCallback.cancel();
                            }
                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            if (sPayCallback != null) {
                                sPayCallback.failed();
                            }
                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }
    };

}
