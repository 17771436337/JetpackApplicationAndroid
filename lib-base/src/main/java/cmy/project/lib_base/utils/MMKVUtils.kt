package cmy.project.lib_base.utils

import com.tencent.mmkv.MMKV

class MMKVUtils {

    companion object{

        val instance: MMKV by lazy{
            MMKV.defaultMMKV()
        }

    }
}