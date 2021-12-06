package com.fulmen.notino.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fulmen.notino.data.api.MainRepository
import com.fulmen.notino.data.model.NotinoResponse
import com.fulmen.notino.other.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotinoViewModel @Inject constructor(
    private val mainRepository: MainRepository
): ViewModel() {
    private val _res = MutableLiveData<Resource<NotinoResponse>>()

    val res: LiveData<Resource<NotinoResponse>>
        get() = _res

    init {
        getNotinoProducts()
    }

    private fun getNotinoProducts() = viewModelScope.launch {
        _res.postValue(Resource.loading(null))
        mainRepository.getNotinoData().let {
            if (it.isSuccessful){
                _res.postValue(Resource.success(it.body()))
            } else{
                _res.postValue((Resource.error(it.errorBody().toString(), null)))
            }
        }
    }
}