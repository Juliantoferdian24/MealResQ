package com.example.mealresq

object MenuDataCart{
    private val menuNames = arrayOf("Burger spesial",
        "Nasi goreng ayam telur dadar",
        "Nasi goreng sapi telur mata sapi",
        "Sate ayam bumbu kecap")

    private val qtyMenu = arrayOf("2x",
        "1x",
        "1x",
        "1x")

    private val menuPrice = arrayOf("Rp 40.000",
        "Rp 23.000",
        "Rp 24.000",
        "Rp 25.000")

    val listData: ArrayList<MenuCheckout>
        get() {
            val list = arrayListOf<MenuCheckout>()
            for (position in menuNames.indices) {
                val menuCheckout = MenuCheckout()
                menuCheckout.name = menuNames[position]
                menuCheckout.qty = qtyMenu[position]
                menuCheckout.price = menuPrice[position]
                list.add(menuCheckout)
            }
            return list
        }
}