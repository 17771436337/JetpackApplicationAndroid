package cmy.project.cmy_account.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/***
 * 账号信息表
 */
@Entity
data class AccountMessageEntity(
    val accountId:Long,
    val name:String,
    val detail:String,
    val sortno:Int
    ) {
    @PrimaryKey(autoGenerate = true)//主键是否自动增长，默认为false
    val id:Long? = null
}