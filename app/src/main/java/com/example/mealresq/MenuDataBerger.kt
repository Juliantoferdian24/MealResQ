package com.example.mealresq

object MenuDataBerger{
    private val menuNames = arrayOf("Burger ayam",
        "Burger sapi",
        "Burger keju",
        "Burger spesial")

    private val menuImages = intArrayOf(R.drawable.burgerayam,
        R.drawable.beefburger,
        R.drawable.burgerkeju,
        R.drawable.burgerspecial)

    private val menuDeskripsi = arrayOf("Burger daging ayam",
        "Burger daging sapi",
        "Burger daging keju",
        "Burger daging spesial")

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