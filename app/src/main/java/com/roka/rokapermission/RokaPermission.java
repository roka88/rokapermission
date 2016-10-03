package com.roka.rokapermission;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by roka on 2016. 9. 14..
 */
public class RokaPermission {


    private static RokaPermission mRokaPermission;

    private static Activity mActivity;
    private static String[] mPermission;
    private static String mMsg;
    private static String mPostiveBtnName, mNegativeBtnName;

    private static PermissionDeniedListener mPermissionDeniedListener;
    private static PermissionGrantedListener mPermissionGrantedListener;

    private static boolean mSettingViewFlag = false;




    private RokaPermission() {
    }

    public static RokaPermission Init(Activity activity) {
        if (mRokaPermission == null) {
            mRokaPermission = new RokaPermission();
        }
        mActivity = activity;
        mPermission = null;
        mMsg = null;
        mSettingViewFlag = false;
        mPostiveBtnName = null;
        mNegativeBtnName = null;
        return mRokaPermission;
    }

    public RokaPermission setDeniedListener(@NonNull PermissionDeniedListener listener) {
        this.mPermissionDeniedListener = listener;
        return this;
    }

    public RokaPermission setGrantedListener(@NonNull PermissionGrantedListener listener) {
        this.mPermissionGrantedListener = listener;
        return this;
    }

    public RokaPermission permission(@NonNull String... permission) {
        this.mPermission = permission;
        return this;
    }

    public RokaPermission setAplicationSetting(@NonNull String postiveBtnName, @NonNull String negativeBtnName, @NonNull String msg) {
        this.mPostiveBtnName = postiveBtnName;
        this.mNegativeBtnName = negativeBtnName;
        this.mMsg = msg;
        this.mSettingViewFlag = true;
        return this;
    }

    @TargetApi(23)
    public void start() {
        Intent intent = new Intent(mActivity, RokaPerMissionActivity.class);
        intent.putExtra("permission", mPermission);
        intent.putExtra("package", mActivity.getPackageName());
        intent.putExtra("setting", mSettingViewFlag);
        intent.putExtra("msg", mMsg);
        intent.putExtra("postiveBtn", mPostiveBtnName);
        intent.putExtra("negativeBtn", mNegativeBtnName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(intent);
    }

    public static DataListener mDataListener = new DataListener() {
        @Override
        public void denied(ArrayList<String> result) {
            if (mPermissionDeniedListener != null)
                mPermissionDeniedListener.onPermissionDeniedListener(result);
        }

        @Override
        public void granted(ArrayList<String> result) {
            if (mPermissionGrantedListener != null)
                mPermissionGrantedListener.onPermissionGrantedListener(result);
        }
    };



}
