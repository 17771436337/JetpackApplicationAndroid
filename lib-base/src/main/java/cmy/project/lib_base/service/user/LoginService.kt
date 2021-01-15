package cmy.project.lib_base.service.user

import android.content.Context
import androidx.lifecycle.LiveData
import cmy.project.lib_base.model.user.User
import com.alibaba.android.arouter.facade.template.IProvider

/**登录相关信息*/
interface LoginService : IProvider {

    /**判断是否登录*/
    fun isLogin(): Boolean

    /**返回用户信息*/
    fun getUserInfo(): User?

    /**清除用户信息*/
    fun removeUserInfo()

    fun start(context: Context): LiveData<User>

    fun getLiveData(): LiveData<User>
}