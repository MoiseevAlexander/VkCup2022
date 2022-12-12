package vk.cup.android.presentation.dzen_category

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import vk.cup.android.domain.DzenCategoriesUseCase
import vk.cup.android.presentation.dzen_category.recycler.DzenCategoryUiModel
import javax.inject.Inject

@HiltViewModel
class DzenCategoriesViewModel @Inject constructor(
    private val dzenCategoriesUseCase: DzenCategoriesUseCase
) : ViewModel() {

    private val dzenCategoriesLiveData = MutableLiveData<List<DzenCategoryUiModel>>()

    private val nextScreenAvailabilityLiveData = MutableLiveData<Boolean>()

    private val canSkipOnboardingLiveData = MutableLiveData<Boolean>(false)

    fun getDzenCategories() {
        viewModelScope.launch {
            if (dzenCategoriesLiveData.value != null) return@launch
            dzenCategoriesUseCase
                .getDzenCategories()
                .map { dzenCategoriesList ->
                    dzenCategoriesList.map {
                        DzenCategoryUiModel.mapFromDomain(it)
                    }
                }
                .catch { error -> Log.e("LoadCategoriesError", error.message.toString()) }
                .collect { dzenCategoriesList -> dzenCategoriesLiveData.postValue(dzenCategoriesList) }
        }
    }

    fun onCategoryChoose(position: Int) {
        val itemsList = dzenCategoriesLiveData.value?.map { it.copy() } as ArrayList
        val oldItem = itemsList[position]
        itemsList.removeAt(position)
        itemsList.add(position, oldItem.copy(isChoose = oldItem.isChoose.not()))
        dzenCategoriesLiveData.postValue(itemsList)
        nextScreenAvailabilityLiveData.postValue(
            itemsList.find { it.isChoose } != null || canSkipOnboardingLiveData.value == true
        )
    }

    fun onCloseOnboardingButtonClick() {
        nextScreenAvailabilityLiveData.postValue(true)
        canSkipOnboardingLiveData.postValue(true)
    }

    fun getDzenCategoriesLiveData() = dzenCategoriesLiveData

    fun getNextScreenAvailabilityLiveData() = nextScreenAvailabilityLiveData

    fun getSkipOnboardingLiveData() = canSkipOnboardingLiveData
}
