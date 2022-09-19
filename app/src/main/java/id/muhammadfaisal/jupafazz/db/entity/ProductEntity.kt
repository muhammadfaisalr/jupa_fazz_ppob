package id.muhammadfaisal.jupafazz.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import id.muhammadfaisal.jupafazz.utils.Constant
import java.io.Serializable

@Entity(tableName = Constant.TABLE.PRODUCT)
data class ProductEntity(
    @PrimaryKey(autoGenerate = false) var id: String,
    @ColumnInfo(name = "product") var product: String,
    @ColumnInfo(name = "type") var type: String,
    @ColumnInfo(name = "brand") var brand: String,
    @ColumnInfo(name = "category") var category: String,
    @ColumnInfo(name = "price") var price: String,
    @ColumnInfo(name = "status") var status: String,
    @ColumnInfo(name = "sku") var sku: String,
    @ColumnInfo(name = "note") var note: String,
) : Serializable {}