package com.example.recyclerviewhw.ui

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewhw.databinding.FragmentPaletteListBinding
import android.widget.AbsListView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhw.R
import com.example.recyclerviewhw.model.*
import com.example.recyclerviewhw.repository.Repository
import com.example.recyclerviewhw.viewmodel.FavoritesViewModel
import com.example.recyclerviewhw.viewmodel.PaletteListViewModel

import dagger.hilt.EntryPoint
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PaletteListFragment : Fragment() {

    private lateinit var binding: FragmentPaletteListBinding
    private lateinit var adapter: PalettesAdapter
    private val viewModel: PaletteListViewModel by viewModels()
    private var loadedList = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaletteListBinding.inflate(layoutInflater, container, false)

        loadedList = false
        setupViews()
        setupObservers()
        getPalettes()
        return binding.root
    }


    private fun setupViews() {
        adapter = PalettesAdapter(viewModel)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )


        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    // Scrolling up
                    if (layoutManager.findLastCompletelyVisibleItemPosition() >= adapter.differ.currentList.size - 25)
                        if (!adapter.loading) {
                            getPalettes()
                        }
                }
            }
        })
    }


    private fun setupObservers() {

        binding.btn.setOnClickListener {
            Navigation.findNavController(binding.root)
                .navigate(R.id.action_paletteListFragment_to_favoritesFragment)
        }


        val myCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT
        ) {

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(
                viewHolder: RecyclerView.ViewHolder,
                direction: Int
            ) {
                adapter.deleteItem(viewHolder.adapterPosition)
            }

        }
        val myHelper = ItemTouchHelper(myCallback)
        myHelper.attachToRecyclerView(binding.recyclerView)


    }

    private fun getPalettes() {
        lifecycleScope.launch {
            if (viewModel.getCurrentList().size == 0) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                adapter.differ.submitList(viewModel.getCurrentList())
                binding.recyclerView.visibility = View.VISIBLE
            }
            viewModel.paletteFlow(20).collectLatest { response ->
                when (response) {
                    is Resource.Success -> {
                        response.data?.let { picsResponse ->
                            if (adapter.loading) adapter.removeLoadingView()
                            adapter.differ.submitList(picsResponse)

                            if (!loadedList) {
                                binding.progressBar.visibility = View.GONE
                                binding.recyclerView.visibility = View.VISIBLE
                                loadedList = true
                            }
                        }
                    }

                    is Resource.Error -> {
                        response.message?.let { message ->
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT)
                        }

                    }

                    is Resource.Loading -> {
                        adapter.addLoadingView()
                    }
                }
            }
        }
    }

}