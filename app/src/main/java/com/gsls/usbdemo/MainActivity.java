package com.gsls.usbdemo;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gsls.gt.GT;
import com.gsls.usbdemo.utils.FileUtils;

@GT.Annotations.GT_AnnotationActivity(R.layout.activity_main)
public class MainActivity extends GT.GT_Activity.AnnotationActivity {

    @GT.Annotations.GT_View(R.id.tv_show)
    private TextView tv_show;
    @GT.Annotations.GT_View(R.id.gl_bg)
    private View view;

    private UiReceiver uiReceiver;//定义一个刷新UI的广播

    @Override
    protected void initView(Bundle savedInstanceState) {
        build(this);

        uiReceiver = new UiReceiver();
        //实例化过滤器并设置要过滤的广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(getClass().getName());
        registerReceiver(uiReceiver, intentFilter); //注册广播

        identification();

    }

    @SuppressLint("ResourceAsColor")
    private void identification() {

        String usbDisk = FileUtils.getUsbName();
        log("usbDisk:" + usbDisk);

        if (usbDisk != null) {
            usbDisk = "/storage" + usbDisk.substring(usbDisk.lastIndexOf("/")) + "/TK/";
            log("文件路径:" + usbDisk);
            String query = FileUtils.query(usbDisk, FileUtils.FILE_NAME);
            log("query:" + query);
            String data = GT.Encryption.DES.decryptPassword(query, FileUtils.PASS_WORD);
            log("解密后的数据:" + data);

            if (data.contains("COMMON")) {
                toast("身份识别成功！");
                tv_show.setText("普通人员界面");
                view.setBackgroundResource(R.color.color1);
            } else if (data.contains("ADMIN")) {
                toast("身份识别成功！");
                tv_show.setText("管理员人员界面");
                view.setBackgroundResource(R.color.color2);
            }

        } else {
            tv_show.setText("普通人员界面");
            view.setBackgroundResource(R.color.color1);
        }

    }


    /**
     * 定义一个接收到消息后刷新UI的内部类广播
     */
    private class UiReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String operationType = intent.getStringExtra("operationType");
            log("operationType:" + operationType);
            if ("插入U盘".equals(operationType)) {
                identification();
            }

        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(uiReceiver); //注销广播
    }

}