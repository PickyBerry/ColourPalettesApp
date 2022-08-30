package com.example.recyclerviewhw.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerviewhw.databinding.ItemPaletteBinding
import com.example.recyclerviewhw.model.Palette


class PalettesAdapter() :
    RecyclerView.Adapter<PalettesAdapter.PalettesViewHolder>() {


    private val differCallback = object : DiffUtil.ItemCallback<Palette>() {
        override fun areItemsTheSame(oldItem: Palette, newItem: Palette): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Palette, newItem: Palette): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    var loading = false


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PalettesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPaletteBinding.inflate(inflater, parent, false)
        return PalettesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PalettesViewHolder, position: Int) {
        val palettes = differ.currentList[position]
        val binding = holder.binding

        holder.itemView.apply {
            if (palettes.colors == null) showProgressbar(holder)
            else {
                hideProgressbar(holder)
                setUpItem(palettes.colors!![0], binding.color1, binding.colorCode1)
                setUpItem(palettes.colors[1], binding.color2, binding.colorCode2)
                setUpItem(palettes.colors[2], binding.color3, binding.colorCode3)
                setUpItem(palettes.colors[3], binding.color4, binding.colorCode4)
                setUpItem(palettes.colors[4], binding.color5, binding.colorCode5)
            }
        }
    }


    fun removeLoadingView() {
        loading = false
        val list = differ.currentList.toMutableList()
        list.removeAt(list.size - 1)
        differ.submitList(list)
        notifyItemRemoved(list.size)
    }

    fun addLoadingView() {
        loading = true
        val list = differ.currentList.toMutableList()
        list.add(Palette(null))
        differ.submitList(list)
        notifyItemInserted(list.size - 1)
    }

    override fun getItemCount() = differ.currentList.size

    private inline fun showProgressbar(holder: PalettesViewHolder){
        val binding=holder.binding
        binding.item1.visibility = View.GONE
        binding.item2.visibility = View.GONE
        binding.item3.visibility = View.GONE
        binding.item4.visibility = View.GONE
        binding.item5.visibility = View.GONE
        binding.progressBar.visibility = View.VISIBLE
    }

    private inline fun hideProgressbar(holder: PalettesAdapter.PalettesViewHolder) {
        val binding=holder.binding
        binding.item1.visibility = View.VISIBLE
        binding.item2.visibility = View.VISIBLE
        binding.item3.visibility = View.VISIBLE
        binding.item4.visibility = View.VISIBLE
        binding.item5.visibility = View.VISIBLE
        binding.progressBar.visibility = View.GONE
    }

    private fun setUpItem(palette: Array<Int>, colorView: View, tvColor: TextView) {
        val color = getIntFromColor(palette[0], palette[1], palette[2])
        colorView.setBackgroundColor(color)
        tvColor.text = "#".plus(Integer.toHexString(color).substring(startIndex = 2).uppercase())
    }

    private fun getIntFromColor(red: Int, green: Int, blue: Int): Int {
        val red = red shl 16 and 0x00FF0000 //Shift red 16-bits and mask out other stuff
        val green = green shl 8 and 0x0000FF00 //Shift Green 8-bits and mask out other stuff
        val blue = blue and 0x000000FF //Mask out anything not blue.
        return -0x1000000 or red or green or blue //0xFF000000 for 100% Alpha. Bitwise OR everything together.
    }


    class PalettesViewHolder(
        val binding: ItemPaletteBinding
    ) : RecyclerView.ViewHolder(binding.root)

}



