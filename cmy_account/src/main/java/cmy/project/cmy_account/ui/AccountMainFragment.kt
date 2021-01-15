package cmy.project.cmy_account.ui

import androidx.lifecycle.ViewModel
import cmy.project.cmy_account.R
import cmy.project.cmy_account.databinding.FragmentAccountMainBinding
import cmy.project.cmy_account.model.MainViewModel
import cmy.project.lib_base.base.BaseFragment

class AccountMainFragment:BaseFragment<MainViewModel,FragmentAccountMainBinding>() {
    override fun getLayoutResId() = R.layout.fragment_account_main

    override fun initData() {

    }

    override fun initView() {

    }


    companion object{
        fun newInstance() = AccountMainFragment()
    }
}