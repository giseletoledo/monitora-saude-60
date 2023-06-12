package br.com.oceantech.monitora_saude_60.viewModel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.oceantech.monitora_saude_60.database.AppDatabase
import br.com.oceantech.monitora_saude_60.database.MedicamentoDataSource
import br.com.oceantech.monitora_saude_60.model.HorarioMedicamento
import br.com.oceantech.monitora_saude_60.model.Medicamento
import br.com.oceantech.monitora_saude_60.repository.MedicamentoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDate
import java.time.LocalTime

class MedicamentoViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: MedicamentoRepository

    private val _medicamentos = MutableLiveData<List<Medicamento>>()
    val medicamentos: LiveData<List<Medicamento>> = _medicamentos

    private val _horarios = MutableLiveData<List<LocalTime>>()
    val horarios: LiveData<List<LocalTime>> = _horarios

    private var _dataInicial = MutableLiveData<LocalDate>()
    val dataInicial: LiveData<LocalDate> = _dataInicial

    private val _recentHorarios = MutableLiveData<List<HorarioMedicamento>>()
    val recentHorarios: MutableLiveData<List<HorarioMedicamento>> = _recentHorarios

    init {
        val dao = AppDatabase.getInstance(application).medicamentoDao()
        repository = MedicamentoRepository(MedicamentoDataSource(dao))
        loadMedicamentos()
    }

    suspend fun getMedicamentos(): List<Medicamento> {
        return withContext(Dispatchers.IO) {
            repository.getAllMed()
        }
    }
    fun loadMedicamentos() {
        viewModelScope.launch {
            val medicamentosList = withContext(Dispatchers.IO) { repository.getAll() }
            medicamentosList.forEach { medicamento ->
                Log.d("Medicamento", "Item: $medicamento")
            }
            _medicamentos.value = medicamentosList
        }
    }
    fun insert(medicamento: Medicamento) {
        Log.d("Medicamento", "Inserindo no banco de dados: $medicamento") // adiciona um log com os valores do medicamento

        viewModelScope.launch {
         repository.insert(medicamento)
        }
    }
    fun delete(medicamento: Medicamento) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) { repository.delete(medicamento) }
            loadMedicamentos()
        }
    }
    fun getMedicamentoById(id: Int): LiveData<Medicamento> {
        val liveData = MutableLiveData<Medicamento>()
        viewModelScope.launch {
            val medicamento = withContext(Dispatchers.IO) { repository.getById(id) }
            liveData.postValue(medicamento)
        }
        return liveData
    }
    fun onDataInicialSelecionada(data: LocalDate?) {
        _dataInicial.value = data
    }
    fun onHorariosSelecionados(horarios: List<LocalTime>) {
        _horarios.value = horarios
    }
    suspend fun getRecentHorarios() {
        val horariosNomes = withContext(Dispatchers.IO) {
            val medicamentos = repository.getAllMed()

            val horariosMedicamento = mutableListOf<HorarioMedicamento>()

            // Itera sobre os medicamentos e extrai os horários, nome e dia
            for (medicamento in medicamentos) {
                for (horario in medicamento.horarios) {
                    Log.d("horarios", horario.toString())
                    val horarioMedicamento = HorarioMedicamento(medicamento.nome, horario)
                    horariosMedicamento.add(horarioMedicamento)
                }
            }

            // Ordena os horários em ordem crescente
            horariosMedicamento.sortBy { it.hora }

            // Retorna os 5 horários mais recentes, se houver
            if (horariosMedicamento.size > 5) {
                horariosMedicamento.takeLast(5)
            } else {
                horariosMedicamento
            }
        }

        _recentHorarios.value = horariosNomes
    }
}

