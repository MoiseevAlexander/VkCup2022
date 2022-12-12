package vk.cup.android.data.entity

import vk.cup.android.domain.entity.DzenCategoryModel

data class DzenCategoryMockModel(
    val title: String,
){

    fun mapToDomain(): DzenCategoryModel = DzenCategoryModel(title = title)

}
