package com.example.mealresq

object MenuDataCart{
    val menuNames: MutableList<String> = arrayListOf()

    val qtyMenu: MutableList<String> = arrayListOf()

    val menuPrice: MutableList<String> = arrayListOf()

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