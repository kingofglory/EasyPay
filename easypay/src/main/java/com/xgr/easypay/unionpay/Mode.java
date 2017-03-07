package com.xgr.easypay.unionpay;

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
public enum Mode {
    RELEASE("00"),
    TEST("01");

    private String mMode;
    Mode(String mode){
        mMode = mode;
    }
    public String getMode(){
       return mMode;
    }
}
