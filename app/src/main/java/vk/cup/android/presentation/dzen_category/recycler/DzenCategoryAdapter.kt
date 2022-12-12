package vk.cup.android.presentation.dzen_category.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import vk.cup.android.databinding.DzenCategorySelectedItemBinding
import vk.cup.android.databinding.DzenCategoryUnselectedItemBinding

class DzenCategoryAdapter(
    private val onCategoryChoose: ((Int) -> Unit),
) : ListAdapter<DzenCategoryUiModel, DzenCategoryViewHolder>(DzenCategoryDiffUtils()) {

    class DzenCategoryDiffUtils : DiffUtil.ItemCallback<DzenCategoryUiModel>() {
        override fun areItemsTheSame(
            oldItem: DzenCategoryUiModel,
            newItem: DzenCategoryUiModel
        ): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(
            oldItem: DzenCategoryUiModel,
            newItem: DzenCategoryUiModel
        ): Boolean {
            return oldItem.isChoose == newItem.isChoose
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (currentList[position].isChoose) {
            DZEN_SELECTED_ITEM
        } else {
            DZEN_UNSELECTED_ITEM
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DzenCategoryViewHolder {
        return if (viewType == DZEN_SELECTED_ITEM) {
            DzenCategoryViewHolder.DzenSelectedCategoryViewHolder(
                binding = DzenCategorySelectedItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                onCategoryChoose = onCategoryChoose
            )
        } else DzenCategoryViewHolder.DzenUnselectedCategoryViewHolder(
            binding = DzenCategoryUnselectedItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            onCategoryChoose = onCategoryChoose,
        )
    }

    override fun onBindViewHolder(holder: DzenCategoryViewHolder, position: Int) {
        when (holder) {
            is DzenCategoryViewHolder.DzenSelectedCategoryViewHolder -> holder.bind(currentList[position])
            is DzenCategoryViewHolder.DzenUnselectedCategoryViewHolder -> holder.bind(currentList[position])
        }
    }

    private companion object {
        const val DZEN_SELECTED_ITEM = 1
        const val DZEN_UNSELECTED_ITEM = 2
    }
}
