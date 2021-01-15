package cmy.project.lib_net.model

/**基本数据类型*/
data class BaseModel<out T>(val errorCode: Int, val errorMsg: String, val data: T)