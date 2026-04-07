@file:Suppress("ClassName")

package com.highcapable.yukihookapi.hook.xposed.application

import yby1306.wx_notification_messagingstyle.HookEntry

/**
 * ModuleApplication_Impl Class
 *
 * Compiled from YukiHookXposedProcessor
 *
 * Generate Date: 2025年8月27日 23:55:00
 *
 * Powered by YukiHookAPI (C) HighCapable 2019
 *
 * Project URL: [YukiHookAPI](https://github.com/HighCapable/YukiHookAPI)
 */
object ModuleApplication_Impl {

    fun callHookEntryInit() = try {
        HookEntry.onInit()
    } catch (_: Throwable) {
    }
}