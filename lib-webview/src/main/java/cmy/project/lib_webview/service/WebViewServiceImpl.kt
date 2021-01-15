package cmy.project.lib_webview.service

import android.content.Context
import android.content.Intent
import cmy.project.lib_base.service.ConstantsPath
import cmy.project.lib_base.service.webview.WebViewService
import cmy.project.lib_webview.WebViewActivity
import cmy.project.lib_webview.x5.WebViewX5Activity
import com.alibaba.android.arouter.facade.annotation.Route

@Route( path = ConstantsPath.WEB_VIEW_SERVICE_PATH)
class WebViewServiceImpl : WebViewService {
    override fun start(context: Context, title: String, url: String) {
        WebViewActivity.start(context, title, url)
    }

    override fun startX5(context: Context, title: String, url: String) {
        WebViewX5Activity.startX5(context,title,url)
    }

    override fun init(context: Context?) {

    }

}