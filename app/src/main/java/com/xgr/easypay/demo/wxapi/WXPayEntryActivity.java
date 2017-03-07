/*
******************************* Copyright (c)*********************************\
**
**                 (c) Copyright 2017, King, china
**                          All Rights Reserved
**                                
**                      By(北京新动互联信息技术有限公司)
**                         
**------------------------------------------------------------------------------
*/
package com.xgr.easypay.demo.wxapi;

import com.xgr.easypay.activity.WXPayEntryBaseActivity;

/**
 * 文 件 名: WXPayEntryActivity
 * 创 建 人: King
 * 创建日期: 2017/3/7 14:12
 * 邮   箱: mikey1101@163.com
 * 博   客: www.smilevenus.com
 * 描述 ：请在此配置在微信开放平台申请的AppId
 */
public class WXPayEntryActivity extends WXPayEntryBaseActivity {
    @Override
    public String getWXAppId() {
        return "appid";
    }
}
