package com.example.mealresq

object MenuDataNasgor{
    private val menuNames = arrayOf("Nasi goreng ayam telur mata sapi",
        "Nasi goreng ayam telur dadar",
        "Nasi goreng sapi telur mata sapi",
        "Nasi goreng sapi telur dadar")

    private val menuImages = intArrayOf(R.drawable.nasgormatasapi,
        R.drawable.nasgordadar,
        R.drawable.nasgormatasapi1,
        R.drawable.nasgorsapidadar)

    private val menuDeskripsi = arrayOf("Nasi goreng dengan ayam dan telur mata sapi",
        "Nasi goreng dengan ayam dan telur dadar",
        "Nasi goreng dengan sapi dan telur mata sapi",
        "Nasi goreng dengan sapi dan telur dadar")

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