package id.muhammadfaisal.jupafazz.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var list = MutableLiveData<ArrayList<String>>();
    var newList = arrayListOf<String>()

    fun add(string: String) {

    }

}