package bassem.udacity.session5.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import bassem.udacity.session5.data.model.RealEstate
import bassem.udacity.session5.data.model.Type

@Dao
interface RealEstateDao {

    @Query("SELECT * FROM real_estate_table")
    fun getRealEstates(): LiveData<List<RealEstate>>

    @Query("SELECT * FROM real_estate_table WHERE type == :queryType")
    fun getRealEstatesByType(queryType: Type): LiveData<List<RealEstate>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(realEstates: List<RealEstate>)

    @Query("DELETE FROM real_estate_table")
    suspend fun deleteAll()
}