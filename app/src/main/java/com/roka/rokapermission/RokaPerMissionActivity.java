package com.roka.rokapermission;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import java.util.ArrayList;

/**
 * Created by roka on 2016. 9. 14..
 */
public class RokaPerMissionActivity extends AppCompatActivity {

    private final int SETTING_PERMISSION_INTENT_CALLBACK = 1111;
    private final int SETTING_OVERLAY_INTENT_CALLBACK = 1512;

    private ArrayList<String> mGrantedList = null;
    private ArrayList<String> mDeniedList = null;
    private String[] permission;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTING_PERMISSION_INTENT_CALLBACK) {
            //ActivityCompat.requestPermissions(this, permission, 0);
            finish();
            overridePendingTransition(0, 0);
        } else if (requestCode == SETTING_OVERLAY_INTENT_CALLBACK) {
            resultOverlayPermission();
            finish();
            overridePendingTransition(0, 0);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

        permission = getIntent().getStringArrayExtra("permission");
        checkOverlayOrPermission();

        if (permission != null && permission.length > 0) {
            checkPermissionDialogMsgSetting();
            ActivityCompat.requestPermissions(this, permission, 0);
        }

        if (getIntent().getBooleanExtra("overlay", false)) {
            checkOverlayDialogMsgSetting();
            // TODO : 오버레이 허가 체크
            if (isOverlay()) {
                RokaPermission.mDataListener.overlayGranted();
            } else {
                showOverlayDialog(getIntent().getStringExtra("overlayMsg"));
            }
        }
    }

    private void getApplicationSetting() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getIntent().getStringExtra("package"), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, SETTING_PERMISSION_INTENT_CALLBACK);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length > 0) {
            mGrantedList = new ArrayList<>();
            mDeniedList = new ArrayList<>();
        }
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                mGrantedList.add(permissions[i]);
            } else {
                mDeniedList.add(permissions[i]);
            }
        }

        if (mDeniedList != null && !mDeniedList.isEmpty()) {
            showPermissionDialog(getIntent().getStringExtra("msg"));

        } else {
            deniedListener();
            grantedListener();
            finish();
            overridePendingTransition(0, 0);
        }
    }

    private void checkPermissionDialogMsgSetting() {
        // TODO : 다이얼로그 셋팅이 되어있는지 확인한다.
        if (!getIntent().getBooleanExtra("setting", false)) {
            throw new Error("You should set method setPermissionMsgSetting()");
        }
    }

    private void checkOverlayDialogMsgSetting() {
        // TODO : 다이얼로그 셋팅이 되어있는지 확인한다.
        if (!getIntent().getBooleanExtra("overlaySetting", false)) {
            throw new Error("You should set method setOverlayMsgSetting()");
        }
    }

    private void checkOverlayOrPermission() {
        // TODO : 퍼미션 요청 또는 오버레이 요청 권한이 동시일 경우 에러를 발생 시킨다.
        if (permission != null && getIntent().getBooleanExtra("overlay", false)) {
            throw new Error("You should request Overlay or Permission One!!");
        }
    }

    @TargetApi(23)
    private void setOverlayPermission() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        intent.setData(Uri.fromParts("package", getIntent().getStringExtra("package"), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, SETTING_OVERLAY_INTENT_CALLBACK);

    }

    @TargetApi(23)
    private boolean isOverlay() {
        // TODO : 오버레이가 허가되어 있으면 true
        return Settings.canDrawOverlays(this);
    }

    @TargetApi(23)
    private void resultOverlayPermission() {
        if (isOverlay()) {
            RokaPermission.mDataListener.overlayGranted();
        } else {
            RokaPermission.mDataListener.overlayDenied();
        }
    }


    private void grantedListener() {
        if (mGrantedList != null && !mGrantedList.isEmpty()) {
            RokaPermission.mDataListener.granted(mGrantedList);
        }
    }

    private void deniedListener() {
        if (mDeniedList != null && !mDeniedList.isEmpty()) {
            RokaPermission.mDataListener.denied(mDeniedList);
        }
    }

    private void showPermissionDialog(String msg) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setCancelable(false)
                .setNegativeButton(getIntent().getStringExtra("negativeBtn"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deniedListener();
                        grantedListener();
                    }
                })
                .setPositiveButton(getIntent().getStringExtra("postiveBtn"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getApplicationSetting();
                    }
                })
                .show();
    }

    private void showOverlayDialog(String msg) {
        new AlertDialog.Builder(this)
                .setMessage(msg)
                .setCancelable(false)
                .setPositiveButton(getIntent().getStringExtra("overlayPostiveBtn"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setOverlayPermission();
                    }
                })
                .setNegativeButton(getIntent().getStringExtra("overlayNegativeBtn"), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        RokaPermission.mDataListener.overlayDenied();
                    }
                })
                .show();
    }
}
