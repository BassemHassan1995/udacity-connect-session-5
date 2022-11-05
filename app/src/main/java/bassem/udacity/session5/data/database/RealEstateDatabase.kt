package bassem.udacity.session5.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import bassem.udacity.session5.data.model.RealEstate

@Database(entities = [RealEstate::class], version = 1, exportSchema = false)
abstract class RealEstateDatabase : RoomDatabase() {

    abstract val dao: RealEstateDao

    companion object {
        @Volatile
        private var INSTANCE: RealEstateDatabase? = null

        fun getInstance(context: Context): RealEstateDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RealEstateDatabase::class.java,
                        "real_estate_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }

    }

}