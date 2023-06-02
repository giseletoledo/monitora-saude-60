package br.com.oceantech.monitora_saude_60.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import br.com.oceantech.monitora_saude_60.database.AppDatabase
import br.com.oceantech.monitora_saude_60.model.Responsavel
import br.com.oceantech.monitora_saude_60.repository.ResponsavelRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ResponsavelViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ResponsavelRepository = ResponsavelRepository(
        AppDatabase.getInstance(application).responsavelDao()
    )

    val allResponsaveis: LiveData<List<Responsavel>> = repository.allResponsaveis

    fun insertResponsavel(responsavel: Responsavel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertResponsavel(responsavel)
        }
    }

    fun updateResponsavel(responsavel: Responsavel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateResponsavel(responsavel)
        }
    }

    fun deleteResponsavel(responsavel: Responsavel) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteResponsavel(responsavel)
        }
    }

    fun getResponsavelById(responsavelId: Long): LiveData<Responsavel> {
        return repository.getResponsavelById(responsavelId)
    }

}
