package com.roka.rokapermission;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
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

    private final int SETTING_INTENT_CALLBACK = 1111;

    private ArrayList<String> mGrantedList = null;
    private ArrayList<String> mDeniedList = null;
    private String[] permission;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SETTING_INTENT_CALLBACK) {
            //ActivityCompat.requestPermissions(this, permission, 0);
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
        ActivityCompat.requestPermissions(this, permission, 0);
    }

    private void getApplicationSetting() {
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", getIntent().getStringExtra("package"), null));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivityForResult(intent, SETTING_INTENT_CALLBACK);

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

        if (mDeniedList != null && !mDeniedList.isEmpty() && getIntent().getBooleanExtra("setting", false)) {
            showDialog(getIntent().getStringExtra("msg"));

        } else {
            deniedListener();
            grantedListener();
            finish();
            overridePendingTransition(0, 0);
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

    private void showDialog(String msg) {
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
}
