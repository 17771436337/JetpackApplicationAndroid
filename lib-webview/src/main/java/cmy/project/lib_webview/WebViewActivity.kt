package cmy.project.lib_webview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.http.SslError
import android.os.Bundle
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import cmy.project.lib_webview.databinding.ActivityWebviewBinding
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter


class WebViewActivity : AppCompatActivity() {

    @Autowired
    lateinit var title: String

    @Autowired
    lateinit var url: String

     lateinit var mBinding: ActivityWebviewBinding


    companion object {
        fun start(context: Context, title: String, url: String) {
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
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_webview)

        initActionBar()
        initWebView()

//        mCollect.setOnClickListener {
//
////            mCollect.imageTintList =
////                ColorStateList.valueOf(resources.getColor((R.color.imageView_tint)))
//
//        }
    }

    private fun initActionBar() {
        mBinding.mTvTitle.text = title
        mBinding.mIvBack.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("JavascriptInterface")
    private fun initWebView() {
        val settings =  mBinding.mWebView.settings
        settings.allowContentAccess = true
        settings.domStorageEnabled = true
        settings.allowFileAccess = true
        settings.javaScriptEnabled = true

        mBinding.mWebView.addJavascriptInterface(this, "cmy")

        mBinding.mWebView.loadUrl(url)
    }
}