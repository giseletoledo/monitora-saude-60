package br.com.oceantech.monitora_saude_60.viewModel


import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.oceantech.monitora_saude_60.database.AppDatabase
import br.com.oceantech.monitora_saude_60.database.UserDataSource
import br.com.oceantech.monitora_saude_60.model.User
import br.com.oceantech.monitora_saude_60.repository.UserRepository
import java.time.LocalDate

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    private var _dataNascimento = MutableLiveData<LocalDate>()
    val dataNascimento: LiveData<LocalDate> = _dataNascimento

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

    fun onDataNascSelecionada(data: LocalDate?) {
        _dataNascimento.value = data
    }
}




