package com.example.recyclerviewhw.data

import android.content.Context
import android.util.Log
import com.example.recyclerviewhw.network.API
import com.example.recyclerviewhw.network.MyCall
import com.example.recyclerviewhw.data.PaletteItem
import kotlinx.coroutines.runBlocking
import java.io.*
import java.lang.StringBuilder

//Repository for getting palettes from api as well as saving and loading favorite palettes logic
class Repository(private val api: API, private val context: Context) {

    private val favorites = mutableListOf<PaletteItem>()

    //load favorite palettes from file when initialized
    init {
        var fileInputStream: FileInputStream? = null
        val savedData = File(context.getFilesDir(), "data_favorites.txt")
        if (!savedData.exists()) savedData.createNewFile()
        fileInputStream = context.openFileInput("data_favorites.txt")
        var inputStreamReader = InputStreamReader(fileInputStream)
        val bufferedReader = BufferedReader(inputStreamReader)
        var text = bufferedReader.readLine();

        while (text != null) {
            val values = text.split(",").map { it -> it.toInt() }.toList()
            addFavorite(PaletteItem(Palette(values), true))
            text = bufferedReader.readLine();
        }

    }


    //Save palettes to file
    private fun saveFavorites() {
        val file = "data_favorites.txt"
        try {
            val bufferedWriter = BufferedWriter(
                OutputStreamWriter(
                    context.openFileOutput(
                        file,
                        Context.MODE_PRIVATE
                    )
                )
            )
            favorites.forEach {
                val data: String = it.toString()
                bufferedWriter.write(data)
                bufferedWriter.newLine()
            }
            bufferedWriter.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    //get palettes from api
    suspend fun getPalettes() = api.getPalette(MyCall())


    fun addFavorite(paletteItem: PaletteItem) {
        favorites.add(paletteItem)
        saveFavorites()
    }

    fun getFavorites() = favorites

    fun removeFavorite(paletteItem: PaletteItem) {
        favorites.remove(paletteItem)
        saveFavorites()
    }


}