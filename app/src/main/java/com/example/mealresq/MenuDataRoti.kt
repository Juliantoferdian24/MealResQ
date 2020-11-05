package com.example.mealresq

object MenuDataRoti{
    private val menuNames = arrayOf("Roti Bakar Keju Coklat",
        "Roti Bakar Mozarella",
        "Roti Bakar Nutella",
        "Roti Bakar Strawberry")

    private val menuImages = intArrayOf(R.drawable.rotikejucoklat,
        R.drawable.rotimozarela,
        R.drawable.rotinutella,
        R.drawable.rotistroberi)

    private val menuDeskripsi = arrayOf("Roti bakar dengan topping keju dan coklat",
        "Roti bakar dengan topping keju mozarella",
        "Roti bakar dengan topping nutella",
        "Roti bakar dengan topping strawberry")

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