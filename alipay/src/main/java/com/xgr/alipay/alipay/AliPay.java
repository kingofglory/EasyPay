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
package com.xgr.alipay.alipay;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.alipay.sdk.app.PayTask;
import com.xgr.easypay.base.IPayStrategy;
import com.xgr.easypay.callback.IPayCallback;

import java.util.Map;

/**
 * 文 件 名: AliPay
 * 创 建 人: King
 * 创建日期: 2017/2/13 17:36
 * 邮   箱: mikey1101@163.com
 * 博   客: www.smilevenus.com
 *
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
                PayTask alipay = new PayTask(mActivity);
                Map<String, String> result = alipay.payV2(alipayInfoImpli.getOrderInfo(), true);

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
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(mActivity, "成功" + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(mActivity, "失败" + payResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton("确认", null)
                .setOnDismissListener(onDismiss)
                .show();
    }
}
