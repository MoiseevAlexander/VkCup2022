package vk.cup.android.presentation.dzen_category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.view.postOnAnimationDelayed
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vk.cup.android.R
import vk.cup.android.databinding.DzenCategoriesFragmentBinding
import vk.cup.android.presentation.dzen_category.recycler.DzenCategoryAdapter
import vk.cup.android.presentation.dzen_category.recycler.DzenCategoryUiModel
import vk.cup.android.presentation.utils.startAnimate

@AndroidEntryPoint
class DzenCategoriesFragment : Fragment() {

    private val viewModel by viewModels<DzenCategoriesViewModel>()

    private val dzenCategoriesAdapter by lazy { DzenCategoryAdapter { viewModel.onCategoryChoose(it) } }

    private lateinit var binding: DzenCategoriesFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DzenCategoriesFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getDzenCategories()

        initRecyclerView()
        initSkipOnboarding()

        viewModel.getDzenCategoriesLiveData()
            .observe(viewLifecycleOwner, ::onDzenCategoriesReceived)
        viewModel.getNextScreenAvailabilityLiveData()
            .observe(viewLifecycleOwner, ::isNextScreenButtonVisible)
        viewModel.getSkipOnboardingLiveData()
            .observe(viewLifecycleOwner, ::canSkipOnboarding)
    }

    private fun initRecyclerView() {
        val flexBoxLayoutManager = FlexboxLayoutManager(requireContext())
        flexBoxLayoutManager.flexDirection = FlexDirection.ROW
        binding.dzenCategoriesRecycler.apply {
            layoutAnimation = AnimationUtils.loadLayoutAnimation(
                requireContext(),
                R.anim.recycler_layout_animation
            )
            adapter = dzenCategoriesAdapter
            layoutManager = flexBoxLayoutManager
        }
    }

    private fun initSkipOnboarding() {
        binding.skipOnboardingButton.setOnClickListener {
            binding.skipOnboardingButton.startAnimate(R.anim.next_screen_button_animation_cancel)
            binding.onboardingTitle.startAnimate(R.anim.next_screen_button_animation_cancel)

            binding.onboardingTitle.postOnAnimationDelayed(500) {
                binding.skipOnboardingTitle.startAnimate(R.anim.next_screen_button_animation_open)
                viewModel.onCloseOnboardingButtonClick()
            }
        }
    }

    private fun onDzenCategoriesReceived(dzenCategoriesList: List<DzenCategoryUiModel>) {
        dzenCategoriesAdapter.submitList(dzenCategoriesList)
    }

    private fun isNextScreenButtonVisible(isAnyItemChoose: Boolean) {
        if (isAnyItemChoose && binding.openNextScreenButton.isVisible.not()) {
            binding.openNextScreenButton.startAnimate(R.anim.next_screen_button_animation_open)
        } else if (isAnyItemChoose.not() && binding.openNextScreenButton.isVisible) {
            binding.openNextScreenButton.startAnimate(R.anim.next_screen_button_animation_cancel)
        }

        binding.openNextScreenButton.postOnAnimation {
            binding.openNextScreenButton.isVisible = isAnyItemChoose
            binding.openNextScreenButton.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.onboarding_ending_text),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun canSkipOnboarding(canSkip: Boolean) {
        binding.instructionsGroup.isVisible = canSkip.not()
        binding.skipOnboardingTitle.isVisible = canSkip
    }

}
