package cmy.project.cmy_account.db

import android.content.Context
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import cmy.project.cmy_account.db.dao.AccountDao
import cmy.project.cmy_account.db.entity.AccountCategoryEntity
import cmy.project.cmy_account.db.entity.AccountEntity
import cmy.project.cmy_account.db.entity.AccountMessageEntity
import cmy.project.lib_base.utils.BaseContext
import kotlinx.coroutines.CoroutineScope

/**
 * 重点！应用数据库class必需使用Database注释
 * entities 实体 = 我们的数据class MyData，注意它使用了{}包裹
 * version = 1  数据库版本号
 * exportSchema = false 导出模式
 */
@Database(entities = arrayOf(AccountEntity::class,AccountMessageEntity::class,AccountCategoryEntity::class),version = 1,exportSchema = false)
abstract class AccountDatabase :RoomDatabase() {

    abstract fun accountDao(): AccountDao

    companion object {
        val instance = Single.sin
    }

    private object Single {
        val sin : AccountDatabase = Room.databaseBuilder(
            BaseContext.instance.getContext(),
            AccountDatabase::class.java,
            "cmy_acount.db"
        ).allowMainThreadQueries()
            .build()
    }
}