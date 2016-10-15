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
    private static String mOverlayMsg;
    private static String mPostiveBtnName, mNegativeBtnName;
    private static String mOverlayPostiveBtnName, mOverlayNegativeBtnName;

    private static PermissionDeniedListener mPermissionDeniedListener;
    private static PermissionGrantedListener mPermissionGrantedListener;
    private static OverlayGrantedListener mOverlayGrantedListener;
    private static OverlayDeniedListener mOverlayDeniedListener;

    private boolean mSettingViewFlag = false;
    private boolean mOverlaySettingViewFlag = false;
    private boolean mOverlayCheckFlag = false;



    private RokaPermission() {
    }

    public static RokaPermission Init(Activity activity) {
        if (mRokaPermission == null) {
            mRokaPermission = new RokaPermission();
        }
        mActivity = activity;
        mPermission = null;
        mMsg = null;
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

    public RokaPermission setOverlayGrantedListener(@NonNull OverlayGrantedListener listener) {
        this.mOverlayGrantedListener = listener;
        return this;
    }

    public RokaPermission setOverlayDeniedListener(@NonNull OverlayDeniedListener listener) {
        this.mOverlayDeniedListener = listener;
        return this;
    }

    public RokaPermission permission(@NonNull String... permission) {
        this.mPermission = permission;
        return this;
    }

    @Deprecated
    public RokaPermission setAplicationSetting(@NonNull String postiveBtnName, @NonNull String negativeBtnName, @NonNull String msg) {
        this.mPostiveBtnName = postiveBtnName;
        this.mNegativeBtnName = negativeBtnName;
        this.mMsg = msg;
        this.mSettingViewFlag = true;
        return this;
    }
    public RokaPermission setPermissionMsgSetting(@NonNull String postiveBtnName, @NonNull String negativeBtnName, @NonNull String msg) {
        this.mPostiveBtnName = postiveBtnName;
        this.mNegativeBtnName = negativeBtnName;
        this.mMsg = msg;
        this.mSettingViewFlag = true;
        return this;
    }

    public RokaPermission setOverlayMsgSetting(@NonNull String postiveBtnName, @NonNull String negativeBtnName, @NonNull String msg) {
        this.mOverlayPostiveBtnName = postiveBtnName;
        this.mOverlayNegativeBtnName = negativeBtnName;
        this.mOverlayMsg = msg;
        this.mOverlaySettingViewFlag = true;
        return this;
    }

    public RokaPermission overlay() {
        this.mOverlayCheckFlag = true;
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
        intent.putExtra("overlayPostiveBtn", mOverlayPostiveBtnName);
        intent.putExtra("overlayNegativeBtn", mOverlayNegativeBtnName);
        intent.putExtra("overlayMsg",mOverlayMsg);
        intent.putExtra("overlay", mOverlayCheckFlag);
        intent.putExtra("overlaySetting", mOverlaySettingViewFlag);
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

        @Override
        public void overlayGranted() {
            if (mOverlayGrantedListener != null)
                mOverlayGrantedListener.onOverlayGrantedListener();
        }

        @Override
        public void overlayDenied() {
            if (mOverlayDeniedListener != null)
                mOverlayDeniedListener.onOverlayDeniedListener();
        }
    };



}