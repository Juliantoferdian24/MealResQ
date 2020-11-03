package com.example.mealresq

object MenuData{
    private val menuNames = arrayOf("Roti Bakar Coklat",
        "Roti Bakar Keju",
        "Roti Bakar Spesial",
        "Roti Bakar Polos",
        "Roti Bakar Campur")

    private val menuImages = intArrayOf(R.drawable.martabak,
        R.drawable.berger,
        R.drawable.warteg,
        R.drawable.sate,
        R.drawable.nasgor)

    private val menuDeskripsi = arrayOf("Roti bakar yang sudah terkenal nikmat kelezatannya berlokasi di angke",
        "Roti bakar yang sudah terkenal nikmat kelezatannya berlokasi di angke",
        "Roti bakar yang sudah terkenal nikmat kelezatannya berlokasi di angke",
        "Roti bakar yang sudah terkenal nikmat kelezatannya berlokasi di angke",
        "Roti bakar yang sudah terkenal nikmat kelezatannya berlokasi di angke")

    private val menuPrice = arrayOf("Rp 20.000",
        "Rp 23.000",
        "Rp 24.000",
        "Rp 25.000",
        "Rp 21.000")

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