package com.company.generator

import com.company.model.Photo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import java.io.FileReader
import java.util.*

object ImageGenerator {
    private val gson = Gson()
    private val random = Random()
    private val photos = this.getList("photos.json")

    private val purchProbArray = arrayOf(
            PurchProbEntry(1, 4),
            PurchProbEntry(2, 8),
            PurchProbEntry(3, 12),
            PurchProbEntry(4, 16),
            PurchProbEntry(5, 20),
            PurchProbEntry(6, 20),
            PurchProbEntry(7, 10),
            PurchProbEntry(8, 5),
            PurchProbEntry(9, 3),
            PurchProbEntry(10, 2)
    )

    private val photoProbArray = arrayOf(
            PurchProbEntry(2, 70),
            PurchProbEntry(3, 20),
            PurchProbEntry(4, 5),
            PurchProbEntry(5, 3),
            PurchProbEntry(6, 2)
    )

    private val prodProbArray = arrayOf(
            PurchProbEntry(1, 500),
            PurchProbEntry(2, 250),
            PurchProbEntry(3, 130),
            PurchProbEntry(4, 60),
            PurchProbEntry(5, 30),
            PurchProbEntry(6, 15),
            PurchProbEntry(7, 8),
            PurchProbEntry(8, 4),
            PurchProbEntry(9, 2),
            PurchProbEntry(10, 1)
    )

    private fun getList(filename: String): List<Photo> {
        val reader = JsonReader(FileReader(filename))
        return this.gson.fromJson(reader, object : TypeToken<List<Photo>>() {}.type)
    }


    fun getPhotosList(): ArrayList<Photo> {
        val result = ArrayList<Photo>()

        val count = this.randomPhotoNum()
        for(i in 1..count) {
            val p = this.randomPhoto()
            result.add(p)
        }

        return result
    }

    private fun randomPhoto(): Photo {
        return this.photos[this.random.nextInt(this.photos.size)]
    }

    fun getPurchasesNumber(): Int {
        val len = this.purchProbArray.size
        var max = 0
        this.purchProbArray.forEach { entry ->
            if(entry.prob > max)
                max = entry.prob
        }

        while(true) {
            val index: Int = this.random.nextInt(len)
            val value: Int = this.random.nextInt(max)

            if(value < this.purchProbArray[index].prob)
                return this.purchProbArray[index].count
        }
    }

    private fun randomPhotoNum(): Int {
        val len = this.photoProbArray.size
        var max = 0
        this.photoProbArray.forEach { entry ->
            if(entry.prob > max)
                max = entry.prob
        }

        while(true) {
            val index: Int = this.random.nextInt(len)
            val value: Int = this.random.nextInt(max)

            if(value < this.photoProbArray[index].prob)
                return this.photoProbArray[index].count
        }
    }

    fun getProductsInPurchase(): Int {
        val len = this.prodProbArray.size
        var max = 0
        this.prodProbArray.forEach { entry ->
            if(entry.prob > max)
                max = entry.prob
        }

        while(true) {
            val index: Int = this.random.nextInt(len)
            val value: Int = this.random.nextInt(max)

            if(value < this.prodProbArray[index].prob)
                return this.prodProbArray[index].count
        }
    }

    fun getPurchaseStatus(): Int {
        return if (this.random.nextInt(10) < 2) 0 else 1
    }

    class PurchProbEntry(val count: Int, val prob: Int)
}