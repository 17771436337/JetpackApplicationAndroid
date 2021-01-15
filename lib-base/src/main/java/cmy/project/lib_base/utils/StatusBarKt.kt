package cmy.project.lib_base.utils

import android.R
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.WindowManager

/**
 * Create by liwen on 2020-03-26
 */
object StatusBarKt {

    /**
     * 沉浸式状态栏 > 6.0
     */
    fun fitSystemBar(activity: Activity) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return

        val window = activity.window
        val decorView = window.decorView
        decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR)

        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = Color.TRANSPARENT

    }

    /**
     * 调整状态栏文字、图标颜色 > 6.0
     * true:白底黑字,false:黑底白字
     */
    fun lightStatusBar(activity: Activity, light: Boolean) {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return
        val window = activity.window
        val decorView = window.decorView
        var visibility = decorView.systemUiVisibility

        visibility = if (light) {
            visibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        } else {
            visibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv() //inv 按位取反
        }

        decorView.systemUiVisibility = visibility
    }


    private const val UNSTABLE_STATUS = View.SYSTEM_UI_FLAG_FULLSCREEN
    private const val UNSTABLE_NAV = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    private const val STABLE_STATUS = View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    private const val STABLE_NAV = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
    private const val EXPAND_STATUS = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)
    private const val EXPAND_NAV = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE)


    //设置隐藏StatusBar(点击任意地方会恢复)
    fun hideUnStableStatusBar(activity: Activity) {
        //App全屏，隐藏StatusBar
        setFlag(activity, UNSTABLE_STATUS)
    }

    fun showUnStableStatusBar(activity: Activity) {
        clearFlag(activity, UNSTABLE_STATUS)
    }

    //隐藏NavigationBar(点击任意地方会恢复)
    fun hideUnStableNavBar(activity: Activity) {
        setFlag(activity, UNSTABLE_NAV)
    }

    fun showUnStableNavBar(activity: Activity) {
        clearFlag(activity, UNSTABLE_NAV)
    }

    fun hideStableStatusBar(activity: Activity) {
        //App全屏，隐藏StatusBar
        setFlag(activity, STABLE_STATUS)
    }

    fun showStableStatusBar(activity: Activity) {
        clearFlag(activity, STABLE_STATUS)
    }

    fun hideStableNavBar(activity: Activity) {
        //App全屏，隐藏StatusBar
        setFlag(activity, STABLE_NAV)
    }

    fun showStableNavBar(activity: Activity) {
        clearFlag(activity, STABLE_NAV)
    }

    /**
     * 视图扩充到StatusBar
     */
    fun expandStatusBar(activity: Activity) {
        setFlag(activity, EXPAND_STATUS)
    }

    /**
     * 视图扩充到NavBar
     * @param activity
     */
    fun expandNavBar(activity: Activity) {
        setFlag(activity, EXPAND_NAV)
    }

    fun transparentStatusBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            expandStatusBar(activity)
            activity.window.statusBarColor = activity.resources.getColor(R.color.transparent)
        } else if (Build.VERSION.SDK_INT >= 19) {
            val attrs = activity.window.attributes
            attrs.flags = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or attrs.flags
            activity.window.attributes = attrs
        }
    }

    fun transparentNavBar(activity: Activity) {
        if (Build.VERSION.SDK_INT >= 21) {
            expandNavBar(activity)
            //下面这个方法在sdk:21以上才有
            activity.window.navigationBarColor = activity.resources.getColor(R.color.transparent)
        }
    }

    fun setFlag(activity: Activity, flag: Int) {
        if (Build.VERSION.SDK_INT >= 19) {
            val decorView = activity.window.decorView
            val option = decorView.systemUiVisibility or flag
            decorView.systemUiVisibility = option
        }
    }

    //取消flag
    fun clearFlag(activity: Activity, flag: Int) {
        if (Build.VERSION.SDK_INT >= 19) {
            val decorView = activity.window.decorView
            val option = decorView.systemUiVisibility and flag.inv()
            decorView.systemUiVisibility = option
        }
    }

    fun setToggleFlag(activity: Activity, option: Int) {
        if (Build.VERSION.SDK_INT >= 19) {
            if (isFlagUsed(activity, option)) {
                clearFlag(activity, option)
            } else {
                setFlag(activity, option)
            }
        }
    }

    /**
     * @param activity
     * @return flag是否已被使用
     */
    fun isFlagUsed(activity: Activity, flag: Int): Boolean {
        val currentFlag = activity.window.decorView.systemUiVisibility
        return if (currentFlag and flag
            == flag
        ) {
            true
        } else {
            false
        }
    }

}