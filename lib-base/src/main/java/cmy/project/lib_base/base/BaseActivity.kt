package cmy.project.lib_base.base

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import cmy.project.lib_base.utils.StatusBarKt
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

abstract class BaseActivity <T : ViewModel, M : ViewDataBinding> : AppCompatActivity() {
    lateinit var mViewModel: T
    lateinit var mViewBinding: M

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        StatusBarKt.fitSystemBar(this)

        mViewBinding = DataBindingUtil.setContentView(this, getLayoutResId())

        initViewModel()
        initView()
        initData()


    }

    abstract fun initData()

    abstract fun initView()

    abstract fun getLayoutResId(): Int


    fun toast(str : String){
        Toast.makeText(this,str,Toast.LENGTH_SHORT).show()
    }


    @SuppressLint("NewApi")
    private fun initViewModel() {

//        val types = (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments
//        mViewModel = ViewModelProvider(this).get<T>(types[0] as Class<T>)

        val clazz =
            this.javaClass.kotlin.supertypes[0].arguments[0].type!!.classifier!! as KClass<T>
        mViewModel = getViewModel<T>(clazz) //koin 注入

    }

}