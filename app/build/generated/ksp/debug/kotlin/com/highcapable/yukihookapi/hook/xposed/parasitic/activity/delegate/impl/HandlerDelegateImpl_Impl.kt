@file:Suppress("ClassName", "INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package com.highcapable.yukihookapi.hook.xposed.parasitic.activity.delegate.impl

import android.os.Handler
import com.highcapable.yukihookapi.hook.xposed.parasitic.activity.delegate.HandlerDelegate_

/**
 * HandlerDelegateImpl_Impl Class
 *
 * Compiled from YukiHookXposedProcessor
 *
 * Generate Date: 2025年8月27日 23:55:00
 *
 * Powered by YukiHookAPI (C) HighCapable 2019
 *
 * Project URL: [YukiHookAPI](https://github.com/HighCapable/YukiHookAPI)
 */
object HandlerDelegateImpl_Impl {

    val wrapperClassName get() = "com.highcapable.yukihookapi.hook.xposed.parasitic.activity.delegate.HandlerDelegate_"

    fun createWrapper(baseInstance: Handler.Callback? = null): Handler.Callback = HandlerDelegate_(baseInstance)
}