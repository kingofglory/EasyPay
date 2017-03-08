/*
******************************* Copyright (c)*********************************\
**
**                 (c) Copyright 2017, King, china
**                          All Rights Reserved
**                                
**                              By(King)
**                         
**------------------------------------------------------------------------------
*/
package com.xgr.easypay;

import android.app.Activity;

import com.xgr.easypay.base.IPayInfo;
import com.xgr.easypay.base.IPayStrategy;
import com.xgr.easypay.callback.IPayCallback;

/**
 * 文 件 名: EasyPay
 * 创 建 人: King
 * 创建日期: 2017/2/13 16:52
 * 邮   箱: mikey1101@163.com
 * 博   客: www.smilevenus.com
 * 描述 ：策略模式场景类。
 * 调用 : 实例化支付策略payway,以及支付订单信息，作为参数直接传入。
 *      使用方法1：调用EasyPay.pay()方法即可。
 *      使用方法2：实例化payStrategy,直接调用其pay方法。如：new Alipay().pay(...)
 */
public class EasyPay {
    public static <T extends IPayInfo> void pay(IPayStrategy<T> payWay, Activity mActivity, T payinfo, IPayCallback callback){
        payWay.pay(mActivity, payinfo, callback);
    }
}
