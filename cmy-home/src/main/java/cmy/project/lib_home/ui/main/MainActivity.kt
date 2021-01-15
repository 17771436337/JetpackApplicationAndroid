package cmy.project.lib_home.ui.main


import android.os.Build
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.FragmentTransaction
import cmy.project.lib_base.base.BaseActivity
import cmy.project.lib_base.service.account.warp.AccountWarpServer
import cmy.project.lib_home.R
import cmy.project.lib_home.databinding.ActivityHomeMainBinding
import cmy.project.lib_home.model.HomeMainViewModel


class MainActivity : BaseActivity<HomeMainViewModel, ActivityHomeMainBinding>() {
    override fun getLayoutResId(): Int = R.layout.activity_home_main

    override fun initView() {
//        setSupportActionBar(mViewBinding.toolbar)//将toolbar与ActionBar关联
//        val toggle = ActionBarDrawerToggle(
//            this, mViewBinding.drawer, mViewBinding.toolbar, 0, 0
//        )
//        mViewBinding.drawer.addDrawerListener(toggle) //初始化状态
//        toggle.syncState()


        /*---------------------------添加头布局和尾布局-----------------------------*/
        //获取xml头布局view
        val headerView: View =  mViewBinding.navigationView.getHeaderView(0)
        //添加头布局的另外一种方式
        //View headview=navigationview.inflateHeaderView(R.layout.navigationview_header);

        //寻找头部里面的控件
        //添加头布局的另外一种方式
        //View headview=navigationview.inflateHeaderView(R.layout.navigationview_header);
        val ivHead  = headerView.findViewById<ImageView>(R.id.iv_head)
        //寻找头部里面的控件
        ivHead.setOnClickListener {
            Toast.makeText(applicationContext, "点击了头像", Toast.LENGTH_LONG).show()
        }
        val csl = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) resources.getColorStateList(R.color.nav_menu_text_color,this.theme) else resources.getColorStateList(R.color.nav_menu_text_color)
        //设置item的条目颜色
        mViewBinding.navigationView.itemTextColor = csl
        //去掉默认颜色显示原来颜色  设置为null显示本来图片的颜色
        mViewBinding.navigationView.itemIconTintList = csl
        //设置条目点击监听
        mViewBinding.navigationView.setNavigationItemSelectedListener { item -> //安卓
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            when(item.itemId){
                R.id.account ->{//账号管理
                    transaction.replace(R.id.content_frame,AccountWarpServer.instance.getAccountFragment())
                }
                R.id.book ->{//书籍管理
//                    transaction.replace(R.id.content_frame,BookWarpService.instance.getBookCaseFragment())
                    Toast.makeText(applicationContext, item.title, Toast.LENGTH_LONG).show()
                }else ->{
                    Toast.makeText(applicationContext, item.title, Toast.LENGTH_LONG).show()
                }
            }
            transaction.commit()

            //设置哪个按钮被选中
            item.isChecked = true
            //关闭侧边栏
            mViewBinding.drawer.closeDrawers();
            true
        }


    }


    override fun initData() {
        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.content_frame,AccountWarpServer.instance.getAccountFragment())
        transaction.commit()
        mViewBinding.navigationView.setCheckedItem(R.id.account)

    }
}