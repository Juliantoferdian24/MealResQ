package com.example.mealresq

object RestoranData{
    private val restoranNames = arrayOf("Roti Bakar Bang Ali",
        "Burger Bang Deni",
        "Rumah Makan Padang Setia",
        "Sate Padang Pak Tomy",
        "Nasi Goreng Pemuda")

    private val restoranImages = intArrayOf(R.drawable.rotinutella,
        R.drawable.burgerayam,
        R.drawable.nasipadangayam,
        R.drawable.sateayamkacang,
        R.drawable.nasgordadar)

    private val restoranRatings = arrayOf("4.0",
        "4.2",
        "4.0",
        "4.0",
        "4.1")

    val listData: ArrayList<Restaurant>
        get() {
            val list = arrayListOf<Restaurant>()
            for (position in restoranNames.indices) {
                val restoran = Restaurant()
                restoran.name = restoranNames[position]
                restoran.rating = restoranRatings[position]
                restoran.photo = restoranImages[position]
                list.add(restoran)
            }
            return list
        }
}