package com.sultan.mvvmshop.viewmodel.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import com.sultan.mvvmshop.data.Category
import com.sultan.mvvmshop.viewmodel.CategoryViewModel

class BaseCategoryViewModelFactory constructor(
    private val firestore: FirebaseFirestore,
    private val category: Category
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CategoryViewModel(firestore,category) as T
    }
}