package vk.cup.android.domain

import kotlinx.coroutines.flow.Flow
import vk.cup.android.domain.entity.DzenCategoryModel

interface DzenCategoriesRepository {

    fun loadDzenCategories(): Flow<List<DzenCategoryModel>>
}
