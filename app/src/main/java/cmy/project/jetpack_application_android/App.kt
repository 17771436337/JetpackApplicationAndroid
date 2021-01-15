package cmy.project.jetpack_application_android

import android.app.Application
import cmy.project.jetpack_application_android.di.allModule
import cmy.project.lib_base.utils.BaseContext
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.mmkv.MMKV
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        BaseContext.instance.init(this)
        ARouter.init(this)
        MMKV.initialize(this)

        startKoin {
            androidContext(this@App)
            modules(allModule)
        }

    }
}