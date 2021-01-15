package cmy.project.lib_base.service.account.warp

import cmy.project.lib_base.service.ConstantsPath
import cmy.project.lib_base.service.account.AccountServer
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter

class AccountWarpServer private constructor(){
    @Autowired(name = ConstantsPath.ACCOUNT_SERVICE_PATH)
    lateinit var service: AccountServer

    init {
        ARouter.getInstance().inject(this)
    }

    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = AccountWarpServer()
        }
    }


    /**账号信息首页碎片*/
    fun getAccountFragment() = service.getAccountFragment()
}