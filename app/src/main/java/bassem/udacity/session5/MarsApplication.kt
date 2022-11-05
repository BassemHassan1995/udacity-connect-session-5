package bassem.udacity.session5

import android.app.Application
import androidx.work.*
import bassem.udacity.session5.data.api.ApiService
import bassem.udacity.session5.data.database.RealEstateDatabase
import bassem.udacity.session5.data.repo.RealEstateRepository
import bassem.udacity.session5.data.work.DailyWorker
import bassem.udacity.session5.utils.Constants.REFRESH_DATA_WORK_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MarsApplication : Application() {

    private val apiService by lazy { ApiService.retrofitService }
    private val database by lazy { RealEstateDatabase.getInstance(this) }

    val repository by lazy { RealEstateRepository(database.dao, apiService) }

    override fun onCreate() {
        super.onCreate()
        CoroutineScope(Dispatchers.Default).launch {
            setupDailyWork()
        }
    }

    private fun setupDailyWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
//            .setRequiresDeviceIdle(true)
            .setRequiresCharging(true)
            .setRequiresStorageNotLow(true)
            .build()

        val refreshRequest = PeriodicWorkRequestBuilder<DailyWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            REFRESH_DATA_WORK_NAME,
            ExistingPeriodicWorkPolicy.KEEP, // Will Neglect the newly created request
            refreshRequest
        )
//        WorkManager.getInstance(applicationContext).enqueue(refreshRequest)
    }

}