package com.xgr.unionpay.unionpay;

public class UnionPayErrCode {
    public static final String RESPONSE_MESSAGE_SUCCESS = "success";
    public static final String RESPONSE_MESSAGE_FAIL = "fail";
    public static final String RESPONSE_MESSAGE_CANCEL = "cancel";

    public static final int CODE_SUCCESS = 0;
    public static final int CODE_FAIL = -1;
    public static final int CODE_CANCEL = -2;
    public static final int CODE_VERIFY_ERROR = -3;
    public static final String MESSAGE_FAIL = "支付失败";
    public static final String MESSAGE_VERIFY_ERROR = "验证未通过，请查询商户后台";

}
