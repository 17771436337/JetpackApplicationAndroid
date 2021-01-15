package cmy.project.cmy_user.model

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cmy.project.cmy_user.repository.LoginRepository
import cmy.project.lib_base.model.user.User
import cmy.project.lib_base.utils.BaseContext
import cmy.project.lib_net.result.NetResult
import kotlinx.coroutines.launch

class LoginViewModel(private val loginRepo: LoginRepository) : ViewModel() {
    private val loginLiveData = MutableLiveData<User>()

    fun login(username: String, password: String): MutableLiveData<User> {
        viewModelScope.launch {
            val user = loginRepo.login(username, password)
            if (user is NetResult.Success) {
                loginLiveData.postValue(user.data)
            } else if (user is NetResult.Error) {
                Toast.makeText(
                    BaseContext.instance.getContext(),
                    user.exception.msg,
                    Toast.LENGTH_LONG
                ).show()
            }

        }

        return loginLiveData
    }
}