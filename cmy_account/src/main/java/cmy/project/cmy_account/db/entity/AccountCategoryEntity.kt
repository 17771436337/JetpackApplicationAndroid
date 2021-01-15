package cmy.project.cmy_account.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 账号类别表
 */
@Entity
data class AccountCategoryEntity (
    val name:String,
    val level:Int
    ){
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    val id:Long? = null
}