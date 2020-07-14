package com.example.kiolyn

class Items(val title: String) {
    companion object {
        fun createItems(num: Int): ArrayList<Items> {
            val items = ArrayList<Items>()
            for (i in 1..num) {
                items.add(Items("Item $i"))
            }
            return items
        }
    }
}