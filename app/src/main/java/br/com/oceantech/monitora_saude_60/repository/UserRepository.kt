package br.com.oceantech.monitora_saude_60.repository

import br.com.oceantech.monitora_saude_60.dao.UserDao
import br.com.oceantech.monitora_saude_60.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user: User) = userDao.insert(user)

    suspend fun getUserByPhoneAndPassword(phone: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUserByPhoneAndPassword(phone, password)
        }
    }
    suspend fun update(user: User) = userDao.update(user)
    suspend fun delete(user: User) = userDao.delete(user)
    suspend fun getUserById(id: Int): User? = userDao.getUserById(id)
    suspend fun getAllUsers(): List<User> = userDao.getAllUsers()
}

