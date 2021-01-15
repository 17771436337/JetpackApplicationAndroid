package cmy.project.lib_base.utils

import android.text.TextUtils
import java.io.File
import java.io.FileInputStream
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.*
import kotlin.experimental.and

object MD5Utils {
    /**
     * 获取单个文件的MD5值！
     *
     * @param file
     * @return
     */
    fun getFileMD5(file: File): String? {
        if (!file.isFile) {
            return null
        }
        var digest: MessageDigest? = null
        var ins: FileInputStream? = null
        val buffer = ByteArray(1024)
        var len: Int
        try {
            digest = MessageDigest.getInstance("MD5")
            ins = FileInputStream(file)
            while (ins.read(buffer, 0, 1024).also { len = it } != -1) {
                digest.update(buffer, 0, len)
            }
            ins.close()
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
        val bigInt = BigInteger(1, digest.digest())
        return bigInt.toString(16)
    }

    /**
     * 获取文件夹中文件的MD5值
     *
     * @param file
     * @param listChild ;true递归子目录中的文件
     * @return
     */
    fun getDirMD5(file: File, listChild: Boolean): Map<String, String>? {
        if (!file.isDirectory) {
            return null
        }
        val map: MutableMap<String, String> = HashMap()
        var md5: String?
        val files = file.listFiles()
        for (i in files.indices) {
            val f = files[i]
            if (f.isDirectory && listChild) {
                map.putAll(getDirMD5(f, listChild)!!)
            } else {
                md5 = getFileMD5(f)
                if (md5 != null) {
                    map[f.path] = md5
                }
            }
        }
        return map
    }

    fun checkFileMd5(path: String, md5: String): Boolean {
        return if (FileUtils.checkFileExist(path)) {
            val hexdigest = getFileMD5(File(path))!!
            !TextUtils.isEmpty(hexdigest) && hexdigest == md5
        } else {
            false
        }
    }

    fun strToMd5By32(str: String): String {
        var reStr = "MD5"
        try {
            val md5 = MessageDigest.getInstance("MD5")
            val bytes = md5.digest(str.toByteArray())
            val stringBuffer = StringBuffer()
            for (b in bytes) {
                val bt: Byte = b and 0xff.toByte()
                if (bt < 16) {
                    stringBuffer.append(0)
                }
                stringBuffer.append(Integer.toHexString(bt.toInt()))
            }
            reStr = stringBuffer.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }
        return reStr
    }

    fun strToMd5By16(str: String): String? {
        var reStr = strToMd5By32(str)
        if (reStr != null) {
            reStr = reStr.substring(8, 24)
        }
        return reStr
    }
}