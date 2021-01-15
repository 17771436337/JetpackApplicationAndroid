package cmy.project.cmy_account.service

import android.content.Context
import cmy.project.cmy_account.ui.AccountMainFragment
import cmy.project.lib_base.service.ConstantsPath
import cmy.project.lib_base.service.account.AccountServer
import com.alibaba.android.arouter.facade.annotation.Route

@Route(path = ConstantsPath.ACCOUNT_SERVICE_PATH)
class AccountServiceImpl:AccountServer {
    override fun getAccountFragment() = AccountMainFragment.newInstance()

    override fun init(context: Context?) {

    }
}