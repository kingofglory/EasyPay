package com.xgr.easypay.callback;

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
public interface IPayCallback {
    void success();
    void failed();
    void cancel();
}
