package cmy.project.lib_base.service.account

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface AccountServer:IProvider {

    /**获取账号管理首页碎片*/
    fun getAccountFragment(): Fragment
}