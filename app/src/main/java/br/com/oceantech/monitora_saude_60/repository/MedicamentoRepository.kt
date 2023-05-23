package br.com.oceantech.monitora_saude_60.repository

import br.com.oceantech.monitora_saude_60.database.MedicamentoDataSource
import br.com.oceantech.monitora_saude_60.model.Medicamento

class MedicamentoRepository(private val dataSource: MedicamentoDataSource) {

    suspend fun getAll(): List<Medicamento> = dataSource.getAll()

    suspend fun getById(id: Int): Medicamento? = dataSource.getById(id)

    suspend fun insert(medicamento: Medicamento) {
        dataSource.insert(medicamento)
    }
    suspend fun delete(medicamento: Medicamento) = dataSource.delete(medicamento)

    suspend fun getAllMed(): List<Medicamento> {
        return dataSource.getAllMed()
    }
}


