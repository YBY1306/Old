pluginManagement {
    repositories {
        google ()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // 作为 Xposed 模块使用务必添加，其它情况可选
        maven("https://api.xposed.info/")
    }
}

rootProject.name = "WechatNotificationMessagingStyle"
include(":app")
