package cmy.project.lib_base.service.user.warp

import android.content.Context
import androidx.lifecycle.LiveData
import cmy.project.lib_base.model.user.User
import cmy.project.lib_base.service.ConstantsPath
import cmy.project.lib_base.service.user.LoginService
import cmy.project.lib_base.service.webview.warp.WebViewWarpService
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter

class UserWarpService private constructor(){
    @Autowired(name = ConstantsPath.LOGIN_SERVICE_PATH)
    lateinit var service: LoginService

    init {
        ARouter.getInstance().inject(this)
    }

    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = UserWarpService()
        }
    }


    /**判断是否登录*/
    fun isLogin(): Boolean {
        return service.isLogin()
    }


    /**返回用户信息*/
    fun getUserInfo(): User? {
        return service.getUserInfo()
    }

    /**清除用户信息*/
    fun removeUserInfo() {
        service.removeUserInfo()
    }

    fun start(context: Context): LiveData<User> {
        return service.start(context)
    }


    fun getLiveData(): LiveData<User> {
        return service.getLiveData()
    }

}