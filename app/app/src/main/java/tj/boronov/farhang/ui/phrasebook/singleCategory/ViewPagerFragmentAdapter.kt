package tj.boronov.farhang.ui.phrasebook.singleCategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter


class ViewPagerFragmentAdapter(
    _viewModel: SingleCategoryViewModel,
    _fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(_fragmentActivity) {

    private val viewModel: SingleCategoryViewModel = _viewModel

    override fun createFragment(position: Int): Fragment {
        val newFragment = SubcategoriesFragment()

        val newBundle = Bundle()
        newBundle.putInt("categoryID", viewModel.category.id)
        newBundle.putInt("subcategoryID", viewModel.subcategoryList[position].id)

        newFragment.arguments = newBundle

        return newFragment
    }

    override fun getItemCount(): Int {
        return viewModel.subcategoryList.size
    }
}