package com.example.walley.ui.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class BottomSheetViewModel : ViewModel() {

    class Factory : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return BottomSheetViewModel() as T
        }
    }
}