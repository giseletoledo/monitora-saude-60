package br.com.oceantech.monitora_saude_60.repository

import androidx.lifecycle.LiveData
import br.com.oceantech.monitora_saude_60.dao.ResponsavelDao
import br.com.oceantech.monitora_saude_60.model.Responsavel

class ResponsavelRepository(private val responsavelDao: ResponsavelDao) {

    val allResponsaveis: LiveData<List<Responsavel>> = responsavelDao.getAllResponsaveis()

    suspend fun insertResponsavel(responsavel: Responsavel) {
        responsavelDao.insertResponsavel(responsavel)
    }

    suspend fun updateResponsavel(responsavel: Responsavel) {
        responsavelDao.updateResponsavel(responsavel)
    }

    suspend fun deleteResponsavel(responsavel: Responsavel) {
        responsavelDao.deleteResponsavel(responsavel)
    }

    fun getResponsavelById(responsavelId: Long): LiveData<Responsavel> {
        return responsavelDao.getResponsavelById(responsavelId)
    }

}
