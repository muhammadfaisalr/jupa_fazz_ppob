package id.muhammadfaisal.jupafazz.listener

import android.view.View
import id.muhammadfaisal.jupafazz.db.entity.ProductEntity

interface ProductListener {
    fun onProductClicked(productEntity: ProductEntity)
}