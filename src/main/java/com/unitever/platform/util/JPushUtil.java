package com.unitever.platform.util;

import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;

public class JPushUtil {
	
	public static PushPayload push(String type,int count){
		return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.all())
                .setNotification(Notification.alert("教会有新的通知！"))
                .setMessage(Message.newBuilder()
                        .setMsgContent("")
                        .addExtra("type", type)
                        .addExtra("count", count)
                        .build())
                .build();
	}

}
