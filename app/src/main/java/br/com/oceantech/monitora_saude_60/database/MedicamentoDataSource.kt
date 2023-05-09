package br.com.oceantech.monitora_saude_60.database

import br.com.oceantech.monitora_saude_60.dao.MedicamentoDao
import br.com.oceantech.monitora_saude_60.model.Medicamento
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MedicamentoDataSource(medicamentoDao: MedicamentoDao) {

    private val dao = medicamentoDao

    suspend fun getAll(): List<Medicamento> = withContext(Dispatchers.IO) {
        dao.getAll()
    }

    suspend fun getById(id: Int): Medicamento? = withContext(Dispatchers.IO) {
        dao.getById(id)
    }

    suspend fun insert(medicamento: Medicamento) = withContext(Dispatchers.IO) {
        dao.insert(medicamento)
    }

    suspend fun delete(medicamento: Medicamento) = withContext(Dispatchers.IO) {
        dao.delete(medicamento)
    }

    suspend fun getAllMed(): List<Medicamento> {
        return dao.getAll()
    }

}

