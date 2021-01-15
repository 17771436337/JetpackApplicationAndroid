package cmy.project.lib_net.result

import cmy.project.lib_net.exception.ResultException

/**返回接口数据*/
sealed class NetResult<out T : Any> {

    /**成功的接口数据*/
    data class Success<out T : Any>(val data: T) : NetResult<T>()

    /**失败的接口数据*/
    data class Error(val exception: ResultException) : NetResult<Nothing>()


}