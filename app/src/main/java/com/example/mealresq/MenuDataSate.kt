package com.example.mealresq

object MenuDataSate{
    private val menuNames = arrayOf("Sate ayam bumbu padang",
        "sate ayam bumbu kacang",
        "Sate ayam bumbu kecap",
        "Sate kambing bumbu kacang")

    private val menuImages = intArrayOf(R.drawable.sateayampadang,
        R.drawable.sateayamkacang,
        R.drawable.sateayamkecap,
        R.drawable.satekambingkacang)

    private val menuDeskripsi = arrayOf("Sate ayam dengan bumbu padang",
        "Sate ayam dengan bumbu kacang",
        "Sate ayam dengan bumbu kecap manis",
        "Sate daging sapi dengan bumbu kacang")

    private val menuPrice = arrayOf("Rp 20.000",
        "Rp 23.000",
        "Rp 24.000",
        "Rp 25.000")

    val listData: ArrayList<Menu>
        get() {
            val list = arrayListOf<Menu>()
            for (position in menuNames.indices) {
                val menu = Menu()
                menu.name = menuNames[position]
                menu.description = menuDeskripsi[position]
                menu.photo = menuImages[position]
                menu.price = menuPrice[position]
                list.add(menu)
            }
            return list
        }
}