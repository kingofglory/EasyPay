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
package com.xgr.easypay.unionpay;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import com.unionpay.UPPayAssistEx;
import com.xgr.easypay.base.IPayStrategy;
import com.xgr.easypay.activity.UnionPayAssistActivity;
import com.xgr.easypay.callback.IPayCallback;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * 文 件 名: UnionPay
 * 创 建 人: King
 * 创建日期: 2017/2/14 18:58
 * 邮   箱: mikey1101@163.com
 * 博   客: www.smilevenus.com
 * @see <a href="https://open.unionpay.com/ajweb/help/file/techFile?productId=3">Des</a>
 */
public class UnionPay implements IPayStrategy<UnionPayInfoImpli> {
    public static final String EXTRA_UNIONPAYINFO  = "unionpay_info";
    public static IPayCallback sPayCallback;

    @Override
    public void pay(@NonNull Activity activity, @NonNull UnionPayInfoImpli payInfo, IPayCallback payCallback) {
        sPayCallback = payCallback;
        Intent intent = new Intent(activity, UnionPayAssistActivity.class);
        intent.putExtra(EXTRA_UNIONPAYINFO,payInfo);
        activity.startActivity(intent);
    }

    public static void pay(@NonNull Activity activity,@NonNull UnionPayInfoImpli payInfoImpli){
        UPPayAssistEx.startPay(activity,null,null,payInfoImpli.getTn(),payInfoImpli.getMode().getMode());
    }

    public static void handleResult(Activity activity, Intent data){
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            activity.finish();
            return;
        }
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 验签证书同后台验签证书
                    // 此处的verify，商户需送去商户后台做验签
                    boolean ret = verify(dataOrg, sign, "mode");
                    if (ret) {
                        // 验证通过后，显示支付结果
                        if(sPayCallback !=null){
                            sPayCallback.success();
                        }
                    } else {
                        // 验证不通过后的处理
                        // 建议通过商户后台查询支付结果
                        if(sPayCallback !=null){
                            sPayCallback.failed();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                // 未收到签名信息
                // 建议通过商户后台查询支付结果
                if(sPayCallback !=null){
                    sPayCallback.success();
                }
            }
        } else if (str.equalsIgnoreCase("fail")) {
            if(sPayCallback !=null){
                sPayCallback.failed();
            }
        } else if (str.equalsIgnoreCase("cancel")) {
            if(sPayCallback !=null){
                sPayCallback.cancel();
            }
        }
        activity.finish();
    }

    private static boolean verify(String msg, String sign64, String mode) {
        // 此处的verify，商户需送去商户后台做验签
        return true;
    }
}
