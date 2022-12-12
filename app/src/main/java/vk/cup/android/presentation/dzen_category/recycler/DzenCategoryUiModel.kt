package vk.cup.android.presentation.dzen_category.recycler

import vk.cup.android.domain.entity.DzenCategoryModel

data class DzenCategoryUiModel(
    val title: String,
    val isChoose: Boolean,
) {

    companion object {
        fun mapFromDomain(dzenCategoryModel: DzenCategoryModel) = DzenCategoryUiModel(
            title = dzenCategoryModel.title,
            isChoose = false,
        )
    }

}
