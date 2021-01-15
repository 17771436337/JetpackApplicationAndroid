package cmy.project.lib_base.utils

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes
import com.zqc.opencc.android.lib.ChineseConverter
import com.zqc.opencc.android.lib.ConversionType
import java.text.DateFormat
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

object StringUtils {
    private const val TAG = "StringUtils"
    private const val HOUR_OF_DAY = 24
    private const val DAY_OF_YESTERDAY = 2
    private const val TIME_UNIT = 60

    //将时间转换成日期
    @SuppressLint("SimpleDateFormat")
    fun dateConvert(time: Long, pattern: String?): String {
        val date = Date(time)
        val format = SimpleDateFormat(pattern)
        return format.format(date)
    }

    //将data转换为标准模式
    @SuppressLint("SimpleDateFormat")
    fun dateConvert(data: Date, pattern: String): String {
        val format = SimpleDateFormat(pattern)
        return format.format(data)
    }

    @SuppressLint("SimpleDateFormat")
    fun convertData(time: String, pattern: String): Date? {
        val format = SimpleDateFormat(pattern)
        try {
            return format.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return null
    }


    //将日期转换成昨天、今天、明天
    @SuppressLint("SimpleDateFormat")
    fun dateConvert(source: String, pattern: String): String {
        val format: DateFormat = SimpleDateFormat(pattern)
        val calendar = Calendar.getInstance()
        try {
            val date = format.parse(source)!!
            val curTime = calendar.timeInMillis
            calendar.time = date
            //将MISC 转换成 sec
            val difSec = abs((curTime - date.time) / 1000)
            val difMin = difSec / 60
            val difHour = difMin / 60
            val difDate = difHour / 60
            val oldHour = calendar[Calendar.HOUR]
            //如果没有时间
            if (oldHour == 0) {
                //比日期:昨天今天和明天
                return if (difDate == 0L) {
                    "今天"
                } else if (difDate < DAY_OF_YESTERDAY) {
                    "昨天"
                } else {
                    val convertFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    convertFormat.format(date)
                }
            }
            return when {
                difSec < TIME_UNIT -> {
                    difSec.toString() + "秒前"
                }
                difMin < TIME_UNIT -> {
                    difMin.toString() + "分钟前"
                }
                difHour < HOUR_OF_DAY -> {
                    difHour.toString() + "小时前"
                }
                difDate < DAY_OF_YESTERDAY -> {
                    "昨天"
                }
                else -> {
                    val convertFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
                    convertFormat.format(date)
                }
            }
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return ""
    }

    fun toFirstCapital(str: String): String {
        return str.substring(0, 1).toUpperCase(Locale.ROOT) + str.substring(1)
    }

    fun getString(@StringRes id: Int): String {
        return BaseContext.instance.getContext().resources.getString(id)
    }

    fun getString(@StringRes id: Int, vararg formatArgs: Any?): String {
        return BaseContext.instance.getContext().resources.getString(id, formatArgs)
    }

    /**
     * 将文本中的半角字符，转换成全角字符
     *
     * @param input
     * @return
     */
    fun halfToFull(input: String): String {
        val c = input.toCharArray()
        for (i in c.indices) {
            if (c[i].toInt() == 32
            ) //半角空格
            {
                c[i] = 12288.toChar()
                continue
            }
            //根据实际情况，过滤不需要转换的符号
            //if (c[i] == 46) //半角点号，不转换
            // continue;
            if (c[i].toInt() in 33..126) //其他符号都转换为全角
                c[i] = (c[i] + 65248)
        }
        return String(c)
    }

    //功能：字符串全角转换为半角
    fun fullToHalf(input: String): String? {
        val c = input.toCharArray()
        for (i in c.indices) {
            if (c[i].toInt() == 12288) //全角空格
            {
                c[i] = 32.toChar()
                continue
            }
            if (c[i].toInt() in 65281..65374) c[i] = (c[i] - 65248)
        }
        return String(c)
    }

    //繁簡轉換
    fun convertCC(input: String, context: Context?): String {
        var currentConversionType: ConversionType = ConversionType.S2TWP
        val convertType: Int = MMKVUtils.instance.getInt("shared_read_convert_type", 0)
        if (input.isEmpty()) return ""
        when (convertType) {
            1 -> currentConversionType = ConversionType.TW2SP
            2 -> currentConversionType = ConversionType.S2HK
            3 -> currentConversionType = ConversionType.S2T
            4 -> currentConversionType = ConversionType.S2TW
            5 -> currentConversionType = ConversionType.S2TWP
            6 -> currentConversionType = ConversionType.T2HK
            7 -> currentConversionType = ConversionType.T2S
            8 -> currentConversionType = ConversionType.T2TW
            9 -> currentConversionType = ConversionType.TW2S
            10 -> currentConversionType = ConversionType.HK2S
        }
        return if (convertType != 0) ChineseConverter.convert(
            input,
            currentConversionType,
            context
        ) else input
    }


    fun convertByte(size: Long): String {
        val df = DecimalFormat("###.#")
        val f: Float
        return when {
            size < 1024 -> {
                f = size / 1.0f
                df.format(f.toDouble()) + "B"
            }
            size < 1024 * 1024 -> {
                f = (size.toFloat() / 1024.toFloat())
                df.format(f.toDouble()) + "KB"
            }
            size < 1024 * 1024 * 1024 -> {
                f = (size.toFloat() / (1024 * 1024).toFloat())
                df.format(f.toDouble()) + "MB"
            }
            else -> {
                f = (size.toFloat() / (1024 * 1024 * 1024).toFloat())
                df.format(f.toDouble()) + "GB"
            }
        }
    }
}