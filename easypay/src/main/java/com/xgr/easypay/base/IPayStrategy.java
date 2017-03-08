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
package com.xgr.easypay.base;

import android.app.Activity;

import com.xgr.easypay.callback.IPayCallback;

/**
 * 文 件 名: IPayStrategy
 * 创 建 人: King
 * 创建日期: 2017/2/13 16:53
 * 邮   箱: mikey1101@163.com
 * 博   客: www.smilevenus.com
 * 描述 ：统一支付接口。策略模式中统一算法接口。
 */
public interface IPayStrategy<T extends IPayInfo> {
    void pay(Activity activity, T payInfo, IPayCallback payCallback);
}
