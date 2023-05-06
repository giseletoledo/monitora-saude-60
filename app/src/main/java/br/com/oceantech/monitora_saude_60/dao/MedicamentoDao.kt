package br.com.oceantech.monitora_saude_60.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import br.com.oceantech.monitora_saude_60.model.Medicamento

@Dao
interface MedicamentoDao {

    @Query("SELECT * FROM medicamentos")
    suspend fun getAll(): List<Medicamento>

    @Query("SELECT * FROM medicamentos WHERE id = :id")
    suspend fun getById(id: Int): Medicamento?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(medicamento: Medicamento)

    @Delete
    suspend fun delete(medicamento: Medicamento)

}

