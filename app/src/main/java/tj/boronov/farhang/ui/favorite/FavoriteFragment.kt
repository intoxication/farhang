package tj.boronov.farhang.ui.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import tj.boronov.farhang.adapter.ViewPagerFavoriteAdapter
import tj.boronov.farhang.databinding.FragmentFavoriteBinding
import tj.boronov.farhang.ui.BaseFragment

class FavoriteFragment : BaseFragment() {

    lateinit var binding: FragmentFavoriteBinding
    var favoriteNameList = listOf("Словарь", "Разговорник", "Ёднома")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(inflater, container, false)

        binding.viewPager.adapter = ViewPagerFavoriteAdapter(requireActivity())
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab: TabLayout.Tab, position: Int ->
            tab.text = favoriteNameList[position]
        }.attach()

        return binding.root
    }
}