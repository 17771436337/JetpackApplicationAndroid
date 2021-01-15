package cmy.project.cmy_user.repository

import cmy.project.cmy_user.api.RequestCenter
import cmy.project.lib_base.model.user.User
import cmy.project.lib_net.net.BaseRepository
import cmy.project.lib_net.net.RetrofitClient
import cmy.project.lib_net.result.NetResult

class LoginRepository(private val service: RetrofitClient) :BaseRepository() {

    suspend fun login(username: String, password: String): NetResult<User> {
        return callRequest(call = { requestLogin(username, password) })
    }


    private suspend fun requestLogin(username: String, password: String): NetResult<User> =
        handleResponse(
            service.create(RequestCenter::class.java).login(username, password)
        )
}