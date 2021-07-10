package com.gsls.usbdemo.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.gsls.gt.GT;
import com.gsls.usbdemo.MainActivity;

/**
 * @author：King
 * @time：2021/3/1-9:40
 * @moduleName：监听U盘插入拔出的
 * @businessIntroduction：
 * @loadLibrary：GT库
 */
public class USBReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {
            // U盘根目录
            String mountPath = intent.getData().getPath();
            if (!TextUtils.isEmpty(mountPath)) {
                Intent intent2 = new Intent(MainActivity.class.getName());
                intent2.putExtra("operationType", "插入U盘");
                context.sendOrderedBroadcast(intent2, null);//发送文件管理界面的广播让它更新UI
            }
        } else if (action.equals(Intent.ACTION_MEDIA_UNMOUNTED) || action.equals(Intent.ACTION_MEDIA_EJECT)) {
            Intent intent2 = new Intent(MainActivity.class.getName());
            intent2.putExtra("operationType", "移除U盘");
            context.sendOrderedBroadcast(intent2, null);//发送文件管理界面的广播让它更新UI
        } else if (action.equals("android.intent.action.BOOT_COMPLETED")) {
            GT.log("其他");
        }
    }
}
