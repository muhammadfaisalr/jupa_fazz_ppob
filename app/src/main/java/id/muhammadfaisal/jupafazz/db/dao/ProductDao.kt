package id.muhammadfaisal.jupafazz.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import id.muhammadfaisal.jupafazz.db.entity.ProductEntity
import id.muhammadfaisal.jupafazz.utils.Constant

@Dao
interface ProductDao {

    @Insert
    fun insert(productEntity: ProductEntity)

    @Query("SELECT * FROM ${Constant.TABLE.PRODUCT}")
    fun getAll() : List<ProductEntity>

    @Query("SELECT * FROM ${Constant.TABLE.PRODUCT} WHERE category = :category GROUP BY brand")
    fun getAllByCategory(category: String) : List<ProductEntity>

    @Query("SELECT * FROM ${Constant.TABLE.PRODUCT} WHERE type = :type ORDER BY id ASC")
    fun getAllByType(type: String) : List<ProductEntity>

    @Query("SELECT * FROM ${Constant.TABLE.PRODUCT} WHERE brand = :brand ORDER BY id ASC")
    fun getAllByBrand(brand: String) : List<ProductEntity>

    @Query("SELECT * FROM ${Constant.TABLE.PRODUCT} WHERE brand = :brand AND category = :category ORDER BY id ASC")
    fun getAllByBrandAndCategory(brand: String, category: String) : List<ProductEntity>

    @Query("SELECT * FROM ${Constant.TABLE.PRODUCT} WHERE id = :id")
    fun getById(id: String) : ProductEntity

    @Query("SELECT category FROM ${Constant.TABLE.PRODUCT} GROUP BY category")
    fun getCategories() : List<String>

    @Query("SELECT category FROM ${Constant.TABLE.PRODUCT} WHERE category = :category GROUP BY category")
    fun getCategories(category: String) : List<String>

    @Query("SELECT * FROM ${Constant.TABLE.PRODUCT} WHERE product LIKE :q AND category = :category")
    fun query(q: String, category: String) : List<ProductEntity>

    @Query("SELECT * FROM ${Constant.TABLE.PRODUCT} WHERE product LIKE :q")
    fun query(q: String) : List<ProductEntity>
}