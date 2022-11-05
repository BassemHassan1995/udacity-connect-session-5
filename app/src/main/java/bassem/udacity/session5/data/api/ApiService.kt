package bassem.udacity.session5.data.api

import bassem.udacity.session5.data.model.RealEstate
import bassem.udacity.session5.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("/realestate")
    suspend fun getRealEstates(): Response<List<RealEstate>>

    companion object {

        val retrofitService: ApiService by lazy {
            RetrofitClient.getInstance().create(ApiService::class.java)
        }
    }

}