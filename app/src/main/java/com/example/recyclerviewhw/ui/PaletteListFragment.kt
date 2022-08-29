package com.example.recyclerviewhw.ui

import android.os.Bundle
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
import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhw.model.*
import com.example.recyclerviewhw.repository.Repository
import com.example.recyclerviewhw.viewmodel.PaletteListViewModel
import com.example.recyclerviewhw.viewmodel.ViewModelFactory


class PaletteListFragment : Fragment() {

    private lateinit var binding: FragmentPaletteListBinding
    private lateinit var adapter: PalettesAdapter
    private lateinit var viewModel: PaletteListViewModel
    private var loading=false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaletteListBinding.inflate(layoutInflater, container, false)
        viewModel = ViewModelProvider(this, ViewModelFactory(requireActivity().application, Repository())).get(
            PaletteListViewModel::class.java)
        setupViews()
        setupObservers()

        binding.btn.setOnClickListener({
            viewModel.getPalettes()
        })

        return binding.root
    }



    private fun setupViews() {
        adapter = PalettesAdapter()
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
                    if (layoutManager.findLastCompletelyVisibleItemPosition()>=adapter.differ.currentList.size-3)
                        if (!loading) {
                            Log.e("lol",layoutManager.findLastCompletelyVisibleItemPosition().toString())
                            loading=true
                            viewModel.getPalettes()
                        }
                } else {
                    // Scrolling down

                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    // Do something
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    // Do something
                } else {
                    // Do something
                }
            }
        })
    }



    private fun setupObservers() {
            viewModel.palettes.observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                       // hideProgressBar()
                        response.data?.let { picsResponse ->
                            adapter.differ.submitList(picsResponse)
                           // binding.recyclerView.adapter = adapter
                            adapter.notifyDataSetChanged()
                            loading=false
                        }
                    }

                    is Resource.Error -> {
                      //  hideProgressBar()
                        response.message?.let { message ->
                            Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT)
                        }

                    }

                    is Resource.Loading -> {
                     //   showProgressBar()
                    }
                }
            }

    }


/*
    private lateinit var binding: FragmentPaletteListBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPaletteListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }
*/
}