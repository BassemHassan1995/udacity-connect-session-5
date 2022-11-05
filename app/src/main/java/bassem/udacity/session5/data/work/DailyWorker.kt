package bassem.udacity.session5.data.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import bassem.udacity.session5.data.repo.RealEstateRepository
import retrofit2.HttpException

class DailyWorker(
    appContext: Context,
    params: WorkerParameters,
    private val repository: RealEstateRepository
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result = try {
        repository.refreshRealEstates()
        Result.success()
    } catch (exception: HttpException) {
        Result.retry()
    }
}