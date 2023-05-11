package br.com.oceantech.monitora_saude_60.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.com.oceantech.monitora_saude_60.model.User

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM users WHERE login = :login AND password = :password")
    fun getUserByLoginAndPassword(login: String, password: String): User?

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Int): User?

    @Query("SELECT * FROM users")
    suspend fun getAllUsers(): List<User>
}

