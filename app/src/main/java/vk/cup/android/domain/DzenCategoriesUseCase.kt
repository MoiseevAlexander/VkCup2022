package vk.cup.android.domain

import kotlinx.coroutines.flow.Flow
import vk.cup.android.domain.entity.DzenCategoryModel
import javax.inject.Inject

class DzenCategoriesUseCase @Inject constructor(
    private val dzenCategoriesRepository: DzenCategoriesRepository
) {

    fun getDzenCategories(): Flow<List<DzenCategoryModel>> = dzenCategoriesRepository.loadDzenCategories()

}
