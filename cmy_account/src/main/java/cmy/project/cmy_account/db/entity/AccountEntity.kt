package cmy.project.cmy_account.db.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * 账号表
 */
@Entity
data class AccountEntity (
    val account:String,
    val time:Long,
    val classesId:Long){
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    val id:Long? = null
}