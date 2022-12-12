package vk.cup.android.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import vk.cup.android.data.DzenCategoriesRepositoryImpl
import vk.cup.android.domain.DzenCategoriesRepository

@Module
@InstallIn(ViewModelComponent::class)
interface DzenCategoriesModule {

    @Binds
    fun bindDzenCategoriesRepository(
        dzenCategoriesRepositoryImpl: DzenCategoriesRepositoryImpl
    ): DzenCategoriesRepository

}
