package com.bcd.gifsearch.ui.gifs

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bcd.gifsearch.R
import com.bcd.gifsearch.databinding.FragmentGifsBinding
import com.bcd.gifsearch.features.gifs.GIFsViewModel
import com.bcd.gifsearch.utilities.NetworkResource
import com.bcd.gifsearch.utilities.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GIFSearchFragment : Fragment(R.layout.fragment_gifs), MenuProvider {

    companion object {
        const val TAG = "[GIFsFragment]"
    }

    private val viewModel: GIFsViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val searchAdapter = SearchAdapter()
        val binding = FragmentGifsBinding.bind(view)

        binding.apply {
            recyclerViewGifs.apply {
                adapter = searchAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.searchResults.observe(viewLifecycleOwner) { result ->

                searchAdapter.submitList(result.data?.results)

                loadingProgressBar.isVisible = result is NetworkResource.Loading
                textViewInfo.isVisible =
                    result is NetworkResource.Error && result.data?.results.isNullOrEmpty()
            }

            recyclerViewGifs.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                    super.onScrollStateChanged(recyclerView, newState)

                    if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                        Log.d(TAG, "RecyclerView End");
                    }
                }
            })
        }
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_fragment_gifs, menu)

        val searchView = menu.findItem(R.id.action_search).actionView as SearchView

        val pendingQuery = viewModel.searchQuery.value
        if (pendingQuery.isNotEmpty()) {
            menu.findItem(R.id.action_search).expandActionView()
            searchView.setQuery(pendingQuery, false)
        }

        searchView.onQueryTextChanged {
            viewModel.searchQuery.value = it
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }
}