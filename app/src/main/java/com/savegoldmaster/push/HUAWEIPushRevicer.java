package com.savegoldmaster.push;

import android.content.Context;
import android.os.Bundle;
import com.elvishew.xlog.XLog;
import com.huawei.hms.support.api.push.PushReceiver;

public class HUAWEIPushRevicer extends PushReceiver {

    @Override
    public void onToken(Context context, String token, Bundle extras) {
        String value = extras.getString("");
        XLog.d("token+++++++++++++"+token);
    }
}
