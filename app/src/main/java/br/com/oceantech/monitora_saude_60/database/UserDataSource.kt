package br.com.oceantech.monitora_saude_60.database

import br.com.oceantech.monitora_saude_60.dao.UserDao
import br.com.oceantech.monitora_saude_60.model.User

class UserDataSource(private val userDao: UserDao) : UserDao {
    override suspend fun insert(user: User) {
        userDao.insert(user)
    }

    override fun getUserByLoginAndPassword(login: String, password: String): User? {
        return userDao.getUserByLoginAndPassword(login, password)
    }

    override suspend fun update(user: User) {
        userDao.update(user)
    }

    override suspend fun delete(user: User) {
        userDao.delete(user)
    }

    override suspend fun getUserById(id: Int): User? {
        return userDao.getUserById(id)
    }

    override suspend fun getAllUsers(): List<User> {
        return userDao.getAllUsers()
    }
}

