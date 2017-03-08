/*
******************************* Copyright (c)*********************************\
**
**                 (c) Copyright 2017, King, china
**                          All Rights Reserved
**                                
**                      By(King)
**                         
**------------------------------------------------------------------------------
*/
package com.xgr.easypay.unionpay;

import android.os.Parcel;
import android.os.Parcelable;

import com.xgr.easypay.base.IPayInfo;


/**
 * 文 件 名: UnionPayInfoImpli
 * 创 建 人: King
 * 创建日期: 2017/2/13 17:19
 * 邮   箱: mikey1101@163.com
 * 博   客: www.smilevenus.com
 * 描述 ：
 */
public class UnionPayInfoImpli implements IPayInfo, Parcelable {

    private Mode mode;
    private String tn;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mode == null ? -1 : this.mode.ordinal());
        dest.writeString(this.tn);
    }

    public UnionPayInfoImpli() {
    }

    protected UnionPayInfoImpli(Parcel in) {
        int tmpMode = in.readInt();
        this.mode = tmpMode == -1 ? null : Mode.values()[tmpMode];
        this.tn = in.readString();
    }

    public static final Parcelable.Creator<UnionPayInfoImpli> CREATOR = new Parcelable.Creator<UnionPayInfoImpli>() {
        @Override
        public UnionPayInfoImpli createFromParcel(Parcel source) {

            return new UnionPayInfoImpli(source);
        }
        @Override
        public UnionPayInfoImpli[] newArray(int size) {
            return new UnionPayInfoImpli[size];
        }
    };
}
