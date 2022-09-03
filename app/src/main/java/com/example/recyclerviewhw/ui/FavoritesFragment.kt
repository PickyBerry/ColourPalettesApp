package com.example.recyclerviewhw.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerviewhw.databinding.FragmentFavoritesBinding
import com.example.recyclerviewhw.repository.Repository
import com.example.recyclerviewhw.viewmodel.FavoritesViewModel
import com.example.recyclerviewhw.viewmodel.ViewModelFactory


class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: PalettesAdapter
    private lateinit var viewModel: FavoritesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFavoritesBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(requireActivity().application, Repository)
        ).get(
            FavoritesViewModel::class.java
        )
        setupViews()
        setupObservers()

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
    }


    private fun setupObservers() {
        viewModel.favorites.observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                binding.emptyListNotification.visibility = View.VISIBLE
                binding.recyclerView.visibility=View.GONE
            }
            else{
                binding.emptyListNotification.visibility = View.GONE
                binding.recyclerView.visibility=View.VISIBLE
            }
            adapter.differ.submitList(list)
            adapter.notifyDataSetChanged()
        }

        binding.btn.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }
}

