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
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.bcd.gifsearch.R
import com.bcd.gifsearch.data.GIF
import com.bcd.gifsearch.databinding.FragmentGifsBinding
import com.bcd.gifsearch.features.gifs.GIFsViewModel
import com.bcd.gifsearch.utilities.onQueryTextChanged
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GIFSearchFragment : Fragment(R.layout.fragment_gifs), MenuProvider,
    SearchAdapter.OnItemClickListener {

    companion object {
        const val TAG = "[GIFsFragment]"
    }

    private val viewModel: GIFsViewModel by viewModels()
    private lateinit var binding: FragmentGifsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)

        val searchAdapter = SearchAdapter(this)
        binding = FragmentGifsBinding.bind(view)

        binding.apply {
            recyclerViewGifs.apply {
                itemAnimator = null
                adapter = searchAdapter.withLoadStateFooter(
                    GIFLoadStateAdapter { searchAdapter.retry() }
                )
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

            viewModel.searchResults.observe(viewLifecycleOwner) { result ->
                searchAdapter.submitData(viewLifecycleOwner.lifecycle, result)
            }

            buttonRetry.setOnClickListener { searchAdapter.retry() }
        }

        searchAdapter.addLoadStateListener { loadState ->
            binding.apply {
                loadingProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                recyclerViewGifs.isVisible = loadState.source.refresh is LoadState.NotLoading
                textViewInfo.isVisible = false

                if (loadState.source.refresh is LoadState.Error) {

                    if (viewModel.searchQuery.value.isBlank()) {
                        textViewInfo.isVisible = true
                    } else {
                        buttonRetry.isVisible = loadState.source.refresh is LoadState.Error
                        textViewError.isVisible = loadState.source.refresh is LoadState.Error
                    }
                }

                if (loadState.source.refresh is LoadState.NotLoading && loadState.append.endOfPaginationReached && searchAdapter.itemCount < 1) {
                    recyclerViewGifs.isVisible = false
                    textViewInfo.isVisible = true
                }
            }
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

        searchView.onQueryTextChanged(searchView) {
            binding.recyclerViewGifs.scrollToPosition(0)
            viewModel.searchQuery.value = it

            Log.d(TAG, "onCreateMenu: $it")
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }

    override fun onItemClick(gif: GIF) {
        val action = GIFSearchFragmentDirections.actionGIFsFragmentToGIFDetailsFragment(gif)
        findNavController().navigate(action)
    }
}