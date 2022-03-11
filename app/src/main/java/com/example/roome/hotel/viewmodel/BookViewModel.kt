package com.example.roome.hotel.viewmodel

import android.app.AlertDialog
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookViewModel : ViewModel(){
    var startDate = MutableLiveData<String>()
    var endDate = MutableLiveData<String>()

    private var _room  =  MutableLiveData<Int>(0) //allow editable in VM,
    val room: LiveData<Int> //
        get() = _room

    private var _adult  =  MutableLiveData<Int>(0)
    var adult = MutableLiveData<Int>()
        get() = _adult

    private var _children  =  MutableLiveData<Int>(0)
    var children = MutableLiveData<Int>()
        get() = _children

    fun plusRoom(){
            _room.value = _room.value?.plus(1)
            Log.i("BookViewModel",_room.value.toString())
    }
    fun minusRoom(){
        if ( _room.value!! >= 1){
            _room.value = _room.value?.minus(1)
            Log.i("BookViewModel",_room.value.toString())
        }

    }

    fun plusAdult(){
        _adult.value = _adult.value?.plus(1)
        Log.i("BookViewModel",_adult.value.toString())
    }
    fun minusAdult(){
        if ( _adult.value!! >= 1) {
            _adult.value = _adult.value?.minus(1)
            Log.i("BookViewModel", _adult.value.toString())
        }
    }


    fun plusChild(){
        _children.value = _children.value?.plus(1)
        Log.i("BookViewModel",_children.value.toString())
    }
    fun minusChild(){
        if ( _children.value!! >= 1) {
            _children.value = _children.value?.minus(1)
            Log.i("BookViewModel", _children.value.toString())
        }
    }

    fun btnApply(dialog :AlertDialog){
        dialog.dismiss()
    }
}
