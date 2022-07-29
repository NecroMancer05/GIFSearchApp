package com.bcd.gifsearch.utilities

import androidx.appcompat.widget.SearchView

inline fun SearchView.onQueryTextChanged(searchView: SearchView, crossinline listener: (String) -> Unit) {
    this.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            searchView.clearFocus()
            return true
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            listener(newText.orEmpty())
            return true
        }
    })
}