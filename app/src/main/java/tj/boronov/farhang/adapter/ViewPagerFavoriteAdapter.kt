package tj.boronov.farhang.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import tj.boronov.farhang.ui.favorite.note.NoteFavoriteFragment
import tj.boronov.farhang.ui.favorite.phrases.PhrasesFavoriteFragment
import tj.boronov.farhang.ui.favorite.word.WordFavoriteFragment

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