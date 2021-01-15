package cmy.project.cmy_account.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import cmy.project.cmy_account.db.entity.AccountCategoryEntity
import cmy.project.cmy_account.db.entity.AccountEntity
import cmy.project.cmy_account.db.entity.AccountMessageEntity

@Dao
interface AccountDao {

    /**添加账号*/
    @Insert
    fun insertAccount(account: AccountEntity)

    /**添加账号<添加多个账号>*/
    @Insert
    fun insertAccounts(accounts: MutableList<AccountEntity>)

    /**添加账号信息数据*/
    @Insert
    fun insertAccountMessage(message: AccountMessageEntity)

    //----------------------------------------------------

    /**修改账号*/
    @Update
    fun updataAccount(account: AccountEntity)

    /**修改账号信息*/
    fun updataAccountMessage(message: AccountMessageEntity)

    //------------------------------------------------------

    /**获取所有账号*/
    @Query("select * from AccountEntity order by AccountEntity.time desc")
    fun queryAccounts():LiveData<MutableList<AccountEntity>?>

    /**获取单个账号信息*/
    @Query("select * from AccountEntity where AccountEntity.id = :id")
    fun queryAccount(id: Long):AccountEntity?

    /**获取账号信息数据*/
    @Query("select * from AccountMessageEntity where AccountMessageEntity.accountId = :id order by AccountMessageEntity.sortno asc")
    fun queryAccountEntitys(id: Long):LiveData<MutableList<AccountMessageEntity>?>

    /**查询账号种类*/
    @Query("select * from AccountCategoryEntity where AccountCategoryEntity.id = :classesId")
    fun queryAccountCategory(classesId: Long): AccountCategoryEntity?


    //------------------------------------------------------------------------------------
    @Delete
    fun deleteAccount(vararg account: AccountEntity)

    @Delete
    fun deleteAccountMessage(vararg account: AccountMessageEntity)

}
