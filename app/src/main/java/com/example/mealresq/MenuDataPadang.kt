package com.example.mealresq

object MenuDataPadang{
    private val menuNames = arrayOf("Nasi padang ayam goreng",
        "Nasi padang ayam bakar",
        "Nasi padang ayam gulai",
        "Nasi padang rendang")

    private val menuImages = intArrayOf(R.drawable.nasipadangayam,
        R.drawable.nasipadangbakar,
        R.drawable.gulai,
        R.drawable.rendang)

    private val menuDeskripsi = arrayOf("Nasi padang dengan ayam goreng",
        "Nasi padang dengan ayam bakar",
        "Nasi padang dengan ayam gulai",
        "Nasi padang dengan rendang daging sapi")

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