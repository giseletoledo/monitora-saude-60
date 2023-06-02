package br.com.oceantech.monitora_saude_60.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.com.oceantech.monitora_saude_60.model.Responsavel

@Dao
interface ResponsavelDao {
    @Query("SELECT * FROM responsaveis")
    fun getAllResponsaveis(): LiveData<List<Responsavel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResponsavel(responsavel: Responsavel)

    @Update
    suspend fun updateResponsavel(responsavel: Responsavel)

    @Delete
    suspend fun deleteResponsavel(responsavel: Responsavel)

    @Query("SELECT * FROM responsaveis WHERE celular = :responsavelId")
    fun getResponsavelById(responsavelId: Long): LiveData<Responsavel>
}
