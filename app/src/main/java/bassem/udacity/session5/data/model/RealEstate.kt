package bassem.udacity.session5.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "real_estate_table")
data class RealEstate(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @Json(name = "img_src")
    @ColumnInfo(name = "img_url")
    val imageUrl: String,
    val type: Type,
    val price: Int
)

enum class Type {
    @Json(name = "rent")
    RENT,

    @Json(name = "buy")
    BUY,

    @Ignore
    ALL

}
