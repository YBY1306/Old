package yby1306.wx_notification_messagingstyle

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Notification
import android.app.Notification.EXTRA_MESSAGES
import android.app.Notification.EXTRA_MESSAGING_PERSON
import android.app.Notification.EXTRA_TEMPLATE
import android.app.PendingIntent
import android.app.Person
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.LocusId
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Bundle
import android.util.Log
import com.highcapable.kavaref.KavaRef.Companion.resolve
import com.highcapable.yukihookapi.YukiHookAPI
import com.highcapable.yukihookapi.annotation.xposed.InjectYukiHookWithXposed
import com.highcapable.yukihookapi.hook.log.YLog
import com.highcapable.yukihookapi.hook.type.android.ApplicationClass
import com.highcapable.yukihookapi.hook.type.android.LogClass
import com.highcapable.yukihookapi.hook.xposed.prefs.data.PrefsData
import com.highcapable.yukihookapi.hook.xposed.proxy.IYukiHookXposedInit
import java.io.InputStream
import java.lang.reflect.Method


@InjectYukiHookWithXposed//(modulePackageName = "yby1306.wx_notification_messagingstyle")
object HookEntry : IYukiHookXposedInit {

    override fun onHook() = YukiHookAPI.encase {


        loadApp(name = "com.tencent.mm") {


            Thread {
                Thread.sleep(10000)
                "$packageName.booter.notification.e0".toClass().resolve().firstMethod {
                    name = "c"
                    parameters(
                        Notification::class,//notification0
                        Int::class,//图标资源ID
                        Int::class,
                        PendingIntent::class,//pendingIntent0
                        String::class,//昵称或通知标题
                        String::class,//通知正文
                        String::class,//谁说了什么
                        Bitmap::class,//bitmap0
                        String::class//wxid
                    )
                }.hook() {
                    before {

                        Log.e("AAA", "PPP0")
                    }
                    after {

                        Log.e("AAA", "PPP")
                        if (result is Notification) {
                            val notification = result as Notification
//                        val notification0 =   args(0).any()
//                        Int::class,//图标资源ID
//                        Int::class,
//                        PendingIntent::class,//pendingIntent0
                            val 昵称或通知标题 = args(4).string()
                            val 通知正文 = args(5).string()
                            val 谁说了什么 = args(6).string()
                            var bitmap0 =
                                if (args(7).any() is Bitmap) args(7).any() as Bitmap else null
                            val wxid = args(8).string()


                            //设置通知的样式为MessagingStyle
                            notification.extras.putString(
                                EXTRA_TEMPLATE,
                                Notification.MessagingStyle::class.java.getName()
                            );//setStyle


                            //  创建shortcut
                            val shortcutid = "shortcut_${wxid}"
                            val shortcutlocusid = "shortcut_locus_${wxid}"
                            val shortcutBuilder =
                                appContext?.let {
                                    ShortcutInfo.Builder(appContext, shortcutid)
                                        .setLocusId(LocusId(shortcutlocusid))
                                        .setActivity(
                                            ComponentName(
                                                it,
                                                "com.tencent.mm.ui.LauncherUI".toClass()
                                            )
                                        )
                                }?.setShortLabel(昵称或通知标题)
                                    ?.setLongLived(true)
//                            .setCategories(set1)
                                    ?.setIntent(
                                        Intent(
                                            appContext,
                                            "com.tencent.mm.ui.LauncherUI".toClass()
                                        ).setAction(Intent.ACTION_VIEW)
                                            .putExtra("Intro_Is_Muti_Talker", false)
                                            .putExtra("Main_User", wxid)
//                                        .putExtra("talkerCount", 1)
//                                        .putExtra("notification_title", "服务通知")
//                                        .putExtra("nofification_type", "new_msg_nofification")
//                                        .putExtra("MainUI_FromFinderNotification", false)
//                                        .putExtra("notification_create_time", 123456789L)
//                                        .putExtra("notification_msg_id", 123456789L)
//                                        .putExtra("MainUI_User_Last_Msg_Type", 1)
                                    )
                            if (bitmap0 != null) shortcutBuilder?.setIcon(
                                Icon.createWithAdaptiveBitmap(bitmap0)
                            )
                            val shortcut = shortcutBuilder?.build()


                            val shortcutManager =
                                appContext?.getSystemService(Context.SHORTCUT_SERVICE) as ShortcutManager
                            if (shortcut != null) shortcutManager.pushDynamicShortcut(shortcut)//添加图标
//                      if (shortcut != null) shortcutManager.removeDynamicShortcuts(
//                            listOf<String>(shortcutid)
//                      )//移除图标
//                      if (shortcut != null) shortcutManager.removeAllDynamicShortcuts()//移除图标
                            Thread {
                                Thread.sleep(1000)
                                if (shortcut != null) shortcutManager.removeDynamicShortcuts(
                                    listOf<String>(shortcutid)
                                )//移除图标
                            }//.start()

                            //设置通知绑定的shortcutid
                            val mShortcutIdField =
                                "android.app.Notification".toClass().getDeclaredField("mShortcutId")
                            mShortcutIdField.isAccessible = true;
                            mShortcutIdField.set(notification, shortcutid);


                            //发送者

                            //    notification.     extras.putCharSequence(EXTRA_SELF_DISPLAY_NAME, mUser.getName());
                            notification.extras.putParcelable(
                                EXTRA_MESSAGING_PERSON,
                                /*mUser*/
                                Person.Builder().setName(昵称或通知标题).build()
                            );


                            //通知里对应的聊天消息

                            //android.app.Notification.MessagingStyle.Message.
                            val KEY_TEXT = "text";
                            val KEY_TIMESTAMP = "time";
                            val KEY_SENDER = "sender";
                            val KEY_SENDER_PERSON = "sender_person";
                            val KEY_DATA_MIME_TYPE = "type";
                            val KEY_DATA_URI = "uri";
                            val KEY_EXTRAS_BUNDLE = "extras";
                            val KEY_REMOTE_INPUT_HISTORY = "remote_input_history";

                            //模拟Notification.MessagingStyle.Message变成Bundle
                            val bundle9 = Bundle()
                            bundle9.putCharSequence(KEY_TEXT, 通知正文)
                            bundle9.putLong(KEY_TIMESTAMP, 0)//TODO 我也不知道这里填什么

                            //将聊天消息添加到通知
                            notification.extras.putParcelableArray(
                                EXTRA_MESSAGES,
                                arrayOf<Bundle?>(bundle9)
                            )

                            //builder.setBubbleMetadata(new Notification.BubbleMetadata.Builder(pendingIntent, icon9).build());
                            try {
                                @SuppressLint("BlockedPrivateApi")
                                val setBubbleMetadataMethod: Method =
                                    Notification::class.java.getDeclaredMethod(
                                        "setBubbleMetadata",
                                        Notification.BubbleMetadata::class.java
                                    )
                                setBubbleMetadataMethod.isAccessible = true
                                if (bitmap0 != null)
                                    setBubbleMetadataMethod.invoke(
                                        notification,
                                        android.app.Notification.BubbleMetadata.Builder(
                                            PendingIntent.getActivity(
                                                appContext,
                                                wxid.hashCode(),  // Launch BubbleActivity as the expanded bubble.
                                                Intent(
                                                    appContext,
                                                    "com.tencent.mm.ui.LauncherUI".toClass()
                                                ).setAction(Intent.ACTION_VIEW)
                                                    .putExtra("Intro_Is_Muti_Talker", false)
                                                    .putExtra("Main_User", wxid)
//                                        .putExtra("talkerCount", 1)
//                                        .putExtra("notification_title", "服务通知")
//                                        .putExtra("nofification_type", "new_msg_nofification")
//                                        .putExtra("MainUI_FromFinderNotification", false)
//                                        .putExtra("notification_create_time", 123456789L)
//                                        .putExtra("notification_msg_id", 123456789L)
//                                        .putExtra("MainUI_User_Last_Msg_Type", 1)
                                                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                                    .setFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK),
                                                PendingIntent.FLAG_MUTABLE /*0x4000000*/
                                            ),
                                            Icon.createWithAdaptiveBitmap(bitmap0)
                                        ).setDesiredHeight(Int.MAX_VALUE).build()
                                    )
                                else {
                                    val inputStream: InputStream =
                                        appContext!!.resources.assets.open("marker.png")//test
                                    val bitmap = BitmapFactory.decodeStream(inputStream)
                                    var icon9 = Icon.createWithAdaptiveBitmap(bitmap)
                                    setBubbleMetadataMethod.invoke(
                                        notification,
                                        android.app.Notification.BubbleMetadata.Builder(
                                            PendingIntent.getActivity(
                                                appContext,
                                                wxid.hashCode(),  // Launch BubbleActivity as the expanded bubble.
                                                Intent(
                                                    appContext,
                                                    "com.tencent.mm.ui.LauncherUI".toClass()
                                                ).setAction(Intent.ACTION_VIEW)
                                                    .putExtra("Intro_Is_Muti_Talker", false)
                                                    .putExtra("Main_User", wxid),
                                                PendingIntent.FLAG_MUTABLE /*0x4000000*/
                                            ),
                                            Icon.createWithAdaptiveBitmap(bitmap)
                                        ).setDesiredHeight(Int.MAX_VALUE).build()
                                    )
                                }
                            } catch (e: java.lang.Exception) {
                                YLog.error(e = e);
                            }
                        }
                    }
                }

                "com.tencent.mm.ui.LauncherUI".toClass().resolve().lastMethod {
                    name = "onCreate"
                    parameters(Bundle::class)
                }.hook().before { Log.e("AAA", "XxSAS") }

                Activity::class.resolve().firstMethod {
                    name = "onCreate"
                    parameters(Bundle::class)
                }.hook().before { Log.e("AAA", "XxSASZZ")
                    val stack = Throwable().stackTrace
                        Log.e("AAA:" + "stack:", stack.toString())
                        for (s in stack) {
                            Log.e("AAA:" + "s:", s.toString())
                        }
                }
            }.start()

            "$packageName.booter.notification.i0".toClass().resolve().firstMethod {
                name = "b"
                emptyParameters()
            }.hook().before { result = null }//防止打开微信后自动清理通知导致气泡通知悬浮窗被关闭


            //            val stack = Throwable().stackTrace
            //                    Log.e("YUNYUNYUN:"+"stack:",stack.toString())
            //                    for ( s in stack) {
            //                        Log.e("YUNYUNYUN:"+"s:",s.toString())
            //                    }
            //点开通知的是
            //08-13 13:35:36.663 29945 29945 E YUNYUNYUN:s:: org.lsposed.lspd.impl.LSPosedBridge$NativeHooker.callback(Unknown Source:176)
            //08-13 13:35:36.663 29945 29945 E YUNYUNYUN:s:: LSPHooker_.finish(Unknown Source:8)
            //08-13 13:35:36.663 29945 29945 E YUNYUNYUN:s:: com.tencent.mm.hellhoundlib.activities.HellActivity.finish(Unknown Source:7)
            //08-13 13:35:36.663 29945 29945 E YUNYUNYUN:s:: com.tencent.mm.ui.component.UIComponentActivity.finish(Unknown Source:150)
            //08-13 13:35:36.663 29945 29945 E YUNYUNYUN:s:: com.tencent.mm.ui.MMFragmentActivity.finish(Unknown Source:0)
            //08-13 13:35:36.663 29945 29945 E YUNYUNYUN:s:: com.tencent.mm.ui.LauncherUI.finish(Unknown Source:0)
            //08-13 13:35:36.663 29945 29945 E YUNYUNYUN:s:: com.tencent.mm.ui.LauncherUI.onCreate(Unknown Source:474)
            //08-13 13:35:36.663 29945 29945 E YUNYUNYUN:s:: android.app.Activity.performCreate(Activity.java:9079)
            //08-13 13:35:36.663 29945 29945 E YUNYUNYUN:s:: android.app.Activity.performCreate(Activity.java:9057)
            //分享界面的是这个，不要拦截分享界面的
            //08-13 21:52:23.913 17463 17463 E YUNYUNYUN:s:: com.highcapable.yukihookapi.hook.core.api.compat.HookCompatHelper$compat$9.afterHookedMethod(HookCompatHelper.kt:86)
            //08-13 21:52:23.913 17463 17463 E YUNYUNYUN:s:: de.robv.android.xposed.XposedBridge$LegacyApiSupport.handleAfter(Unknown Source:33)
            //08-13 21:52:23.913 17463 17463 E YUNYUNYUN:s:: org.lsposed.lspd.impl.LSPosedBridge$NativeHooker.callback(Unknown Source:281)
            //08-13 21:52:23.913 17463 17463 E YUNYUNYUN:s:: LSPHooker_.finish(Unknown Source:8)
            //08-13 21:52:23.913 17463 17463 E YUNYUNYUN:s:: com.tencent.mm.ui.transmit.MsgRetransmitUI.finish(Unknown Source:0)
            //08-13 21:52:23.913 17463 17463 E YUNYUNYUN:s:: a05.j0.invoke(Unknown Source:23)
            //08-13 21:52:23.913 17463 17463 E YUNYUNYUN:s:: sp4.a.run(Unknown Source:2)
            //08-13 21:52:23.913 17463 17463 E YUNYUNYUN:s:: java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:524)
            //08-13 21:52:23.913 17463 17463 E YUNYUNYUN:s:: java.util.concurrent.FutureTask.run(FutureTask.java:317)
            //08-13 21:52:23.914 17463 17463 E YUNYUNYUN:s:: m95.l.run(Unknown Source:242)
            //08-13 21:52:23.914 17463 17463 E YUNYUNYUN:s:: android.os.Handler.handleCallback(Handler.java:991)
            //08-13 21:52:23.914 17463 17463 E YUNYUNYUN:s:: android.os.Handler.dispatchMessage(Handler.java:102)
            //08-13 21:52:23.914 17463 17463 E YUNYUNYUN:s:: l95.n.dispatchMessage(Unknown Source:27)
            //08-13 21:52:23.914 17463 17463 E YUNYUNYUN:s:: android.os.Looper.loopOnce(Looper.java:232)
            //08-13 21:52:23.914 17463 17463 E YUNYUNYUN:s:: android.os.Looper.loop(Looper.java:317)
            //08-13 21:52:23.914 17463 17463 E YUNYUNYUN:s:: android.app.ActivityThread.main(ActivityThread.java:8934)
            //08-13 21:52:23.914 17463 17463 E YUNYUNYUN:s:: java.lang.reflect.Method.invoke(Native Method)
            //08-13 21:52:23.914 17463 17463 E YUNYUNYUN:s:: com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:591)
            //08-13 21:52:23.914 17463 17463 E YUNYUNYUN:s:: com.android.internal.os.ZygoteInit.main(ZygoteInit.java:911)


            //  Activity::class.resolve().firstMethod {//  不要一刀切
            //  "com.tencent.mm.hellhoundlib.activities.HellActivity".toClass().resolve()
            ApplicationClass.superclass//pulic class Application extends ContextWrapper implements ComponentCallbacks2
                .resolve().firstMethod {
                    name = "attachBaseContext"
                    parameters(Context::class)
                }.hook {//窗口多开
                    before {
                        Log.e("AAA", "BBBB")
                        val stack = Throwable().stackTrace
//                        Log.e("YUNYUNYUN:" + "stack:", stack.toString())
//                        for (s in stack) {
//                            Log.e("YUNYUNYUN:" + "s:", s.toString())
//                        }
                        if (stack.any { it.className == "com.tencent.mm.ui.LauncherUI" && it.methodName == "onCreate" })
                            result = null
                    }
                }
        }
    }
}
