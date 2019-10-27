package com.xgr.wechatpay.wxpay;

import com.tencent.mm.opensdk.modelbase.BaseResp;

import java.util.HashMap;

public class WXErrCodeEx implements BaseResp.ErrCode {
    private static final HashMap<Integer, String> sErrorMap = new HashMap<>();

    public static final int CODE_UNSUPPORT = 1000;
    public static final int CODE_ILLEGAL_ARGURE = 1001;

    public static final String MESSAGE_UNSUPPORT = "未安装微信或者微信版本太低";
    public static final String MESSAGE_ILLEGAL_ARGURE = "订单参数不合法";

    static {
        sErrorMap.put(CODE_UNSUPPORT, MESSAGE_UNSUPPORT);
        sErrorMap.put(CODE_ILLEGAL_ARGURE, MESSAGE_ILLEGAL_ARGURE);
    }

    public static String getMessageByCode(int code) {
        return sErrorMap.get(code);
    }
}
