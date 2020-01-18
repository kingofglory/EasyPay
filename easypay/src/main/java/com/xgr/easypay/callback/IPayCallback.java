package com.xgr.easypay.callback;

import android.support.annotation.Nullable;

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
public interface IPayCallback {
    void success();
    void failed(int code,@Nullable String message);
    void cancel();
}
