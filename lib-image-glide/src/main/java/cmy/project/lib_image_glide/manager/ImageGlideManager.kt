package cmy.project.lib_image_glide.manager

import android.annotation.SuppressLint
import android.app.Notification
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import android.widget.RemoteViews
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory
import cmy.project.lib_image_glide.CustomRequestListener
import cmy.project.lib_image_glide.R
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.target.NotificationTarget
import com.bumptech.glide.request.target.Target

/**
 * 图处加载类，外界唯一调用类,直持为view,appwidget加载图片
 */
class ImageGlideManager private constructor(){
    private object SingletonHolder {
        val holder = ImageGlideManager()
    }

    companion object {
        val instance: ImageGlideManager = SingletonHolder.holder
    }


    /**
     * 带回调的加载图片方法
     *
     * @param imageView
     * @param url
     * @param requestListener
     */
    /**
     * 不带回调的加载
     *
     * @param imageView
     * @param url
     */
    @JvmOverloads
    fun displayImageForView(
        imageView: ImageView, url: String?,
        requestListener: CustomRequestListener? = null
    ) {
        Glide.with(imageView.context)
            .asBitmap()
            .load(url) //                .apply(initCommonRequestOption())
            //                .transition(withCrossFade())
            .into(imageView)
    }

    /**
     * 带回调的加载圆形图片方法
     *
     * @param imageView
     * @param url
     */
    fun displayImageForCircle(
        imageView: ImageView,
        url: String?
    ) {
        Glide.with(imageView.context)
            .asBitmap()
            .load(url)
            .apply(initCommonRequestOption())
            .into(object : BitmapImageViewTarget(imageView) {
                override fun setResource(resource: Bitmap?) {
                    val circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(
                            imageView.resources,
                            resource
                        )
                    circularBitmapDrawable.isCircular = true
                    imageView.setImageDrawable(circularBitmapDrawable)
                }
            })
    }


    /*
     * 初始化Notification Target
     */
    private fun initNotificationTarget(
        context: Context, id: Int, rv: RemoteViews,
        notification: Notification, NOTIFICATION_ID: Int
    ): NotificationTarget {
        return NotificationTarget(context, id, rv, notification, NOTIFICATION_ID)
    }

    @SuppressLint("CheckResult")
    private fun initCommonRequestOption(): RequestOptions {
        val options = RequestOptions()
        options.placeholder(R.drawable.place_holder)
            .error(R.drawable.place_holder)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .skipMemoryCache(false)
            .priority(Priority.NORMAL)
        return options
    }
}