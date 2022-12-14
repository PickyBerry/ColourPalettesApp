package com.example.recyclerviewhw.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewhw.databinding.FragmentFavoritesBinding
import com.example.recyclerviewhw.viewmodel.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint


//Second screen with favorite palettes
@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: PalettesAdapter
    private val viewModel: FavoritesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        setupViews()
        setupObservers()

        return binding.root
    }

    //Setting up recyclerview
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
    }


    //Setting up livedata subscription
    private fun setupObservers() {
        viewModel.favorites.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                binding.emptyListNotification.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            } else {
                binding.emptyListNotification.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
            }
            adapter.differ.submitList(list)
            adapter.notifyDataSetChanged()
        }

        binding.btn.setOnClickListener {
            requireActivity().onBackPressed()
        }
    }
}

