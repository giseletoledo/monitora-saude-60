package br.com.oceantech.monitora_saude_60.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.oceantech.monitora_saude_60.database.AppDatabase
import br.com.oceantech.monitora_saude_60.database.UserDataSource
import br.com.oceantech.monitora_saude_60.model.User
import br.com.oceantech.monitora_saude_60.repository.UserRepository

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    init {
        val dao = AppDatabase.getInstance(application).userDao()
        repository = UserRepository(UserDataSource(dao))
    }

    suspend fun insert(user: User) = repository.insert(user)
    fun getUserByLoginAndPassword(login: String, password: String): User? = repository.getUserByLoginAndPassword(login, password)
    suspend fun update(user: User) = repository.update(user)
    suspend fun delete(user: User) = repository.delete(user)
    suspend fun getUserById(id: Int): User? = repository.getUserById(id)
    suspend fun getAllUsers(): List<User> = repository.getAllUsers()
}




