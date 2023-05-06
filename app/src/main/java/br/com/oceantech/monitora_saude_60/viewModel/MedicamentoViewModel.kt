package br.com.oceantech.monitora_saude_60.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.oceantech.monitora_saude_60.database.AppDatabase
import br.com.oceantech.monitora_saude_60.database.MedicamentoDataSource
import br.com.oceantech.monitora_saude_60.model.Medicamento
import br.com.oceantech.monitora_saude_60.repository.MedicamentoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MedicamentoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MedicamentoRepository(
        MedicamentoDataSource(
            application,
            AppDatabase.getInstance(application).medicamentoDao()
        )
    )

    private val _medicamentos = MutableLiveData<List<Medicamento>>()
    val medicamentos: LiveData<List<Medicamento>> = _medicamentos

    init {
        loadMedicamentos()
    }

    private fun loadMedicamentos() {
        viewModelScope.launch {
            _medicamentos.value = withContext(Dispatchers.IO) { repository.getAll() }
        }
    }

    fun insert(medicamento: Medicamento) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertOrUpdate(medicamento)
            }
            loadMedicamentos()
        }
    }

    fun delete(medicamento: Medicamento) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { repository.delete(medicamento) }
            loadMedicamentos()
        }
    }

    fun getById(id: Int): LiveData<Medicamento> {
        val liveData = MutableLiveData<Medicamento>()
        viewModelScope.launch {
            val medicamento = withContext(Dispatchers.IO) { repository.getById(id) }
            liveData.postValue(medicamento)
        }
        return liveData
    }
}

