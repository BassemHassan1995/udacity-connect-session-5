package bassem.udacity.session5.data.repo

import android.util.Log
import androidx.lifecycle.LiveData
import bassem.udacity.session5.data.api.ApiService
import bassem.udacity.session5.data.database.RealEstateDao
import bassem.udacity.session5.data.model.RealEstate
import bassem.udacity.session5.data.model.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class RealEstateRepository(private val dao: RealEstateDao, private val apiService: ApiService) {

    fun getRealEstateList(type: Type = Type.ALL): LiveData<List<RealEstate>> = when (type) {
        Type.ALL -> dao.getRealEstates()
        else -> dao.getRealEstatesByType(type)
    }

//    1. Get list from api
//    2. Save returned list to database
//    3. In case of error... do nothing
    suspend fun refreshRealEstates(){
        try {
            val response = apiService.getRealEstates()
            if (response.isSuccessful) {
                val result = response.body() ?: emptyList()

                withContext(Dispatchers.IO) {
                    dao.insert(result)
                }
            }
        } catch (exception: Exception) {
            Log.e("Repository", exception.message.toString())
        }
    }

    suspend fun deleteAll() = dao.deleteAll()
}