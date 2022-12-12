package vk.cup.android.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import vk.cup.android.domain.DzenCategoriesRepository
import vk.cup.android.domain.entity.DzenCategoryModel
import javax.inject.Inject

class DzenCategoriesRepositoryImpl @Inject constructor() : DzenCategoriesRepository {

    override fun loadDzenCategories(): Flow<List<DzenCategoryModel>> = flowOf(DzenCategoriesMockData
        .dzenCategories
        .map { it.mapToDomain() }
    )

}
