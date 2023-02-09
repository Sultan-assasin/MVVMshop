package com.sultan.mvvmshop.data

data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: Float,
    val offerPercentage: Float? = null,
    val description: String? = null,
    val colors:List<Int>? = null,
    val sizes:List<String>? = null,
    val images :List<String>?= null
    ){
    constructor():this("0","","",0f, images = emptyList())
}