package cmy.project.cmy_user.service

import android.content.Context
import androidx.lifecycle.LiveData
import cmy.project.cmy_user.UserManager
import cmy.project.lib_base.model.user.User
import cmy.project.lib_base.service.ConstantsPath
import cmy.project.lib_base.service.user.LoginService
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = ConstantsPath.LOGIN_SERVICE_PATH)
class LoginServiceImpl :LoginService {
    override fun isLogin(): Boolean {
        return UserManager.isLogin()
    }

    override fun getUserInfo(): User? {
        return UserManager.getUser()
    }

    override fun removeUserInfo() {
        UserManager.removeUser()
    }

    override fun start(context: Context): LiveData<User> {
        return UserManager.start(context)
    }

    override fun getLiveData(): LiveData<User> {
        return UserManager.getLoginLiveData()
    }

    override fun init(context: Context?) {

    }
}