package br.com.oceantech.monitora_saude_60.repository

import br.com.oceantech.monitora_saude_60.dao.UserDao
import br.com.oceantech.monitora_saude_60.model.User

class UserRepository(private val userDao: UserDao) {
    suspend fun insert(user: User) = userDao.insert(user)
    fun getUserByLoginAndPassword(login: String, password: String): User? = userDao.getUserByLoginAndPassword(login, password)
    suspend fun update(user: User) = userDao.update(user)
    suspend fun delete(user: User) = userDao.delete(user)
    suspend fun getUserById(id: Int): User? = userDao.getUserById(id)
    suspend fun getAllUsers(): List<User> = userDao.getAllUsers()
}

