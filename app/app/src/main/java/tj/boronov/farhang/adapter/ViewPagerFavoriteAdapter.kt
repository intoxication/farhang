package tj.boronov.farhang.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import tj.boronov.farhang.ui.favorite.NoteFavoriteFragment
import tj.boronov.farhang.ui.favorite.PhrasesFavoriteFragment
import tj.boronov.farhang.ui.favorite.WordFavoriteFragment

class ViewPagerFavoriteAdapter(
    _fragmentActivity: FragmentActivity
) :
    FragmentStateAdapter(_fragmentActivity) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> WordFavoriteFragment()
            1 -> PhrasesFavoriteFragment()
            else -> NoteFavoriteFragment()
        }
    }

    override fun getItemCount(): Int {
        return 3
    }
}