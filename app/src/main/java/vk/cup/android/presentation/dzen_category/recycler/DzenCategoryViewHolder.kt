package vk.cup.android.presentation.dzen_category.recycler

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import vk.cup.android.databinding.DzenCategorySelectedItemBinding
import vk.cup.android.databinding.DzenCategoryUnselectedItemBinding

sealed class DzenCategoryViewHolder(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {

    class DzenUnselectedCategoryViewHolder(
        private val binding: DzenCategoryUnselectedItemBinding,
        private val onCategoryChoose: (Int) -> Unit
    ) : DzenCategoryViewHolder(binding) {

        fun bind(dzenCategoryUiModel: DzenCategoryUiModel) {
            binding.root.setOnClickListener { onCategoryChoose.invoke(adapterPosition) }
            binding.categoryTitle.text = dzenCategoryUiModel.title
        }

    }

    class DzenSelectedCategoryViewHolder(
        private val binding: DzenCategorySelectedItemBinding,
        private val onCategoryChoose: (Int) -> Unit,
    ) : DzenCategoryViewHolder(binding) {

        fun bind(dzenCategoryUiModel: DzenCategoryUiModel) {
            binding.root.setOnClickListener { onCategoryChoose.invoke(adapterPosition) }
            binding.categoryTitle.text = dzenCategoryUiModel.title
        }

    }

}
