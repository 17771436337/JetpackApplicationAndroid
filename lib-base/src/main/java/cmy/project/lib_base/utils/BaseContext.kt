package cmy.project.lib_base.utils

import android.app.Application
import android.content.Context

/**
 * Create by liwen on 2020/6/4
 */
class BaseContext private constructor() {


    private lateinit var mContext: Application

    fun init(context: Application) {
        mContext = context
    }

    fun getContext(): Application {
        return mContext
    }

    companion object {

        val instance = Singleton.holder

        object Singleton {
            val holder = BaseContext()
        }

    }

}