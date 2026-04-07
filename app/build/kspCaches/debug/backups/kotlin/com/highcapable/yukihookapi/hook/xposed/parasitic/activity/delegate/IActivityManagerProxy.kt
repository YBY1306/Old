@file:Suppress("ClassName", "INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package com.highcapable.yukihookapi.hook.xposed.parasitic.activity.delegate

import androidx.annotation.Keep
import com.highcapable.yukihookapi.hook.xposed.parasitic.activity.delegate.caller.IActivityManagerProxyCaller
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

/**
 * IActivityManagerProxy Class
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
class IActivityManagerProxy_(private val baseInstance: Any) : InvocationHandler {

    override fun invoke(proxy: Any?, method: Method?, args: Array<Any>?) = IActivityManagerProxyCaller.callInvoke(baseInstance, method, args)
}