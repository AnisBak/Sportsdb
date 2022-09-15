package com.anis.sportsdb.ui

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.anis.sportsdb.util.PlayerAdapter
import com.anis.sportsdb.R
import com.anis.sportsdb.databinding.FragmentPlayersBinding
import com.anis.sportsdb.viewmodel.PlayersViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase

import kotlinx.coroutines.launch

class PlayersFragment : Fragment(), MenuProvider {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    private var _binding: FragmentPlayersBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null. Is the view visible?"
        }

    private val playersViewModel: PlayersViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = Firebase.analytics
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentPlayersBinding.inflate(inflater, container, false)
        binding.players.layoutManager = LinearLayoutManager(context)
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                playersViewModel.galleryItems.collect { items ->
                    binding.progressBarPlayers.visibility=View.INVISIBLE
                    binding.players.adapter = PlayerAdapter(items){
                        findNavController().navigate(PlayersFragmentDirections.actionPlayersFragmentToDetailFragment(it))
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.fragment_players, menu)
        val searchItem: MenuItem = menu.findItem(R.id.menu_item_search)
        val searchView = searchItem.actionView as? SearchView

        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.clearFocus()
                binding.progressBarPlayers.visibility=View.VISIBLE
                firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SEARCH){
                    param(FirebaseAnalytics.Param.SEARCH_TERM, query)
                }
                playersViewModel.setQuery(query)
                return true
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return false
    }
}
