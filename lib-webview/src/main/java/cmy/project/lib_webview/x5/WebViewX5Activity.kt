package cmy.project.lib_webview.x5

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cmy.project.lib_webview.R
import cmy.project.lib_webview.StatusBarKt
import cmy.project.lib_webview.WebViewActivity
import cmy.project.lib_webview.databinding.ActivityWebviewX5Binding
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.*

/**使用x5内核*/
class WebViewX5Activity : AppCompatActivity() {
    @Autowired
    lateinit var title: String

    @Autowired
    lateinit var url: String

    lateinit var mBinding:ActivityWebviewX5Binding


    companion object {
        fun startX5(context: Context, title: String, url: String) {
            // 在调用TBS初始化、创建WebView之前进行如下配置
            val map = HashMap<String, Any>()
            map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
            map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
            QbSdk.initTbsSettings(map)
            //------------------------------
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("title", title)
            intent.putExtra("url", url)
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        StatusBarKt.fitSystemBar(this)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_webview_x5)
        initActionBar()
        initWebView()
    }

    private fun initActionBar() {
        mBinding.mTvTitle.text = title
        mBinding.mIvBack.setOnClickListener {
            finish()
        }
    }
    private fun initWebView() {


        mBinding.mWebView.getSettings().setSupportZoom(true) //支持缩放

        mBinding.mWebView.getSettings().setBuiltInZoomControls(true) //出现缩放工具

        //扩大比例的缩放
        //扩大比例的缩放
        mBinding.mWebView.getSettings().setUseWideViewPort(true)
        //自适应屏幕
        //自适应屏幕
        mBinding.mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN)
        mBinding.mWebView.getSettings().setLoadWithOverviewMode(true)


        mBinding.mWebView.getSettings().setUserAgent("cmy")

        mBinding.mWebView.addJavascriptInterface(this, "android")

        //再次加载时在WebView中显示

        //再次加载时在WebView中显示
        mBinding.mWebView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {

                view.loadUrl(url)

                return true
            }
        })

        mBinding.mWebView.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {

                super.onProgressChanged(view, newProgress)
            }
        })

        mBinding.mWebView.loadUrl(url);

    }
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && mBinding.mWebView.canGoBack()) {
            mBinding.mWebView.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        mBinding.mWebView.destroy()
        super.onDestroy()
    }

}