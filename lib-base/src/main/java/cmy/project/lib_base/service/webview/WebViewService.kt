package cmy.project.lib_base.service.webview

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

interface WebViewService :IProvider {

    /**启动浏览器*/
    fun start(context: Context, title: String, url: String)


    /**x5内核浏览器的启动*/
    fun startX5(context: Context, title: String, url: String)
}