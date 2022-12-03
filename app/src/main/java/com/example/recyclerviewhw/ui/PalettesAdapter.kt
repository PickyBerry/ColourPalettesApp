package com.example.recyclerviewhw.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.AndroidViewModel
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhw.R
import com.example.recyclerviewhw.databinding.ItemPaletteBinding
import com.example.recyclerviewhw.data.Palette
import com.example.recyclerviewhw.data.PaletteItem
import com.example.recyclerviewhw.viewmodel.FavoritesViewModel
import com.example.recyclerviewhw.viewmodel.PaletteListViewModel


//RecyclerView adapter for showing color palettes
class PalettesAdapter(val viewModel: AndroidViewModel) :
    RecyclerView.Adapter<PalettesAdapter.PalettesViewHolder>() {

    var loading = false

    //Using DiffUtils
    private val differCallback = object : DiffUtil.ItemCallback<PaletteItem>() {
        override fun areItemsTheSame(oldItem: PaletteItem, newItem: PaletteItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PaletteItem, newItem: PaletteItem): Boolean {
            return oldItem == newItem
        }
    }
    val differ = AsyncListDiffer(this, differCallback)


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PalettesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPaletteBinding.inflate(inflater, parent, false)
        return PalettesViewHolder(binding)
    }

    //On binding of the view holder,
    override fun onBindViewHolder(holder: PalettesViewHolder, position: Int) {
        val paletteItem = differ.currentList[position]
        val palette = paletteItem.palette
        val binding = holder.binding

        holder.itemView.apply {
            if (palette.colors == null) showProgressbar(holder)
            else {

                //Setting up the five color rectangles
                hideProgressbar(holder)
                setUpItem(palette[0], binding.color1, binding.colorCode1)
                setUpItem(palette[1], binding.color2, binding.colorCode2)
                setUpItem(palette[2], binding.color3, binding.colorCode3)
                setUpItem(palette[3], binding.color4, binding.colorCode4)
                setUpItem(palette[4], binding.color5, binding.colorCode5)


                //Setting up the "add to favorites" default background
                if (paletteItem.favorite) binding.btnFavorite.setBackgroundResource(R.drawable.added_to_favorites)
                else binding.btnFavorite.setBackgroundResource(R.drawable.add_to_favorites)


                //Setting up the "add to favorites" button logic
                binding.btnFavorite.setOnClickListener {
                    paletteItem.favorite = !paletteItem.favorite
                    if (paletteItem.favorite) {
                        if (viewModel is PaletteListViewModel) viewModel.addFavorite(paletteItem)
                        binding.btnFavorite.setBackgroundResource(R.drawable.added_to_favorites)
                    } else {
                        if (viewModel is PaletteListViewModel) viewModel.removeFavorite(paletteItem)
                        else if (viewModel is FavoritesViewModel) {
                            viewModel.removeFavorite(paletteItem)
                            notifyItemRemoved(position)
                        }
                        binding.btnFavorite.setBackgroundResource(R.drawable.add_to_favorites)
                    }
                }
            }
        }
    }


    //Showing the little progress bar at the end of the list
    fun addLoadingView() {
        loading = true
        val list = differ.currentList.toMutableList()
        list.add(PaletteItem(Palette(null), false))
        differ.submitList(list)
    }

    //Removing the little progress bar at the end of the list
    fun removeLoadingView() {
        loading = false
        val list = differ.currentList.toMutableList()
        list.removeAt(list.size - 1)
        differ.submitList(list)
    }

    //Removing palette from list
    fun deleteItem(position: Int) {
        val list = differ.currentList.toMutableList()
        list.removeAt(position)
        differ.submitList(list)
    }


    override fun getItemCount() = differ.currentList.size

    //Showing the big progress bar before nothing is loaded
    private inline fun showProgressbar(holder: PalettesViewHolder) {
        val binding = holder.binding
        binding.item1.visibility = View.GONE
        binding.item2.visibility = View.GONE
        binding.item3.visibility = View.GONE
        binding.item4.visibility = View.GONE
        binding.item5.visibility = View.GONE
        binding.btnFavorite.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    //Hiding the big progress bar after the first palettes loaded
    private inline fun hideProgressbar(holder: PalettesViewHolder) {
        val binding = holder.binding
        binding.item1.visibility = View.VISIBLE
        binding.item2.visibility = View.VISIBLE
        binding.item3.visibility = View.VISIBLE
        binding.item4.visibility = View.VISIBLE
        binding.item5.visibility = View.VISIBLE
        binding.btnFavorite.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    //Display each color as a rectangle with its color code
    private fun setUpItem(color: Int, colorView: View, tvColor: TextView) {
        colorView.setBackgroundColor(color)
        tvColor.text = "#".plus(Integer.toHexString(color).substring(startIndex = 2).uppercase())
    }


    class PalettesViewHolder(
        val binding: ItemPaletteBinding
    ) : RecyclerView.ViewHolder(binding.root)

}



