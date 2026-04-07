@file:Suppress("ClassName", "INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package com.highcapable.yukihookapi.hook.xposed.parasitic.activity.delegate

import android.os.Handler
import android.os.Message
import androidx.annotation.Keep
import com.highcapable.yukihookapi.hook.xposed.parasitic.activity.delegate.caller.HandlerDelegateCaller

/**
 * HandlerDelegate Class
 *
 * Compiled from YukiHookXposedProcessor
 *
 * Generate Date: 2025年8月27日 23:55:00
 *
 * Powered by YukiHookAPI (C) HighCapable 2019
 *
 * Project URL: [YukiHookAPI](https://github.com/HighCapable/YukiHookAPI)
 */
@Keep
class HandlerDelegate_(private val baseInstance: Handler.Callback?) : Handler.Callback {

    override fun handleMessage(msg: Message) = HandlerDelegateCaller.callHandleMessage(baseInstance, msg)
}