package com.xgr.alipay.alipay;

import java.util.HashMap;

/**
******************************* Copyright (c)*********************************\
**
**                 (c) Copyright 2017, King, china
**                          All Rights Reserved
**                                
**                              By(King)
**                         
**------------------------------------------------------------------------------
*/
public class ResultCode {

    private static final HashMap<String, String> sErrorMap = new HashMap<>();

    public static final String CODE_SUCCESS = "9000";
    public static final String CODE_HANDLING = "8000";
    public static final String CODE_FAIL = "4000";
    public static final String CODE_REPEAT = "5000";
    public static final String CODE_CANCEL = "6001";
    public static final String CODE_NETWORK = "6002";
    public static final String CODE_UNKNOWN = "6004";

    private static final String TEXT_SUCCESS = "订单支付成功";
    private static final String TEXT_HANDLING = "正在处理中";
    private static final String TEXT_FAIL = "订单支付失败";
    private static final String TEXT_REPEAT = "重复请求";
    private static final String TEXT_CANCEL = "用户中途取消";
    private static final String TEXT_NETWORK = "网络连接出错";
    private static final String TEXT_UNKNOWN = "支付结果未知";
    private static final String TEXT_ERROR = "未知错误";

    static {
        sErrorMap.put(CODE_SUCCESS, TEXT_SUCCESS);
        sErrorMap.put(CODE_HANDLING, TEXT_HANDLING);
        sErrorMap.put(CODE_FAIL, TEXT_FAIL);
        sErrorMap.put(CODE_REPEAT, TEXT_REPEAT);
        sErrorMap.put(CODE_CANCEL, TEXT_CANCEL);
        sErrorMap.put(CODE_NETWORK, TEXT_NETWORK);
        sErrorMap.put(CODE_UNKNOWN, TEXT_UNKNOWN);
    }

    private ResultCode() {

    }

    public static String getTextByCode(String code) {
        String text = sErrorMap.get(code);
        if (text == null) {
            return TEXT_ERROR;
        }
        return text;
    }

    public static int getIntCodeByString(String errorCode) {
        return Integer.parseInt(errorCode);
    }
}
