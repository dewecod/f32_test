package com.example.f32.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.f32.net.Resource
import com.example.f32.net.repository.IndexRepository
import com.example.f32.net.response.CartResponse
import com.example.f32.net.response.IndexResponse
import com.example.f32.net.response.ProductResponse
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope

class MainViewModel constructor(
    private val indexRepository: IndexRepository,
) : ViewModel() {

    /****************************************************************************************************
     ** MutableLiveData
     ****************************************************************************************************/
    private val indexLiveData: MutableLiveData<Resource<IndexResponse>> = MutableLiveData()
    private val productLiveData: MutableLiveData<Resource<ProductResponse>> = MutableLiveData()
    private val cartLiveData: MutableLiveData<Resource<CartResponse>> = MutableLiveData()

    /****************************************************************************************************
     ** LiveData
     ****************************************************************************************************/
    fun indexResponse(): LiveData<Resource<IndexResponse>> = indexLiveData
    fun productResponse(): LiveData<Resource<ProductResponse>> = productLiveData
    fun cartResponse(): LiveData<Resource<CartResponse>> = cartLiveData

    fun loadIndex() {
        viewModelScope.launch {
            supervisorScope {
                try {
                    indexLiveData.postValue(Resource.Loading)

                    val callFromApi = async { indexRepository.loadIndex() }

                    val apiResponse = callFromApi.await()
                    val remoteData = apiResponse.body()
                    if (apiResponse.isSuccessful && remoteData != null) {
                        indexLiveData.postValue(Resource.Success(remoteData))
                    } else {
                        indexLiveData.postValue(Resource.Error(Exception("Not found")))
                    }

                } catch (ex: Exception) {
                    indexLiveData.postValue(Resource.Error(Exception("No connection")))
                }
            }
        }
    }

    fun loadProduct() {
        viewModelScope.launch {
            supervisorScope {
                try {
                    productLiveData.postValue(Resource.Loading)

                    val callFromApi = async { indexRepository.loadProduct() }

                    val apiResponse = callFromApi.await()
                    val remoteData = apiResponse.body()
                    if (apiResponse.isSuccessful && remoteData != null) {
                        productLiveData.postValue(Resource.Success(remoteData))
                    } else {
                        productLiveData.postValue(Resource.Error(Exception("Not found")))
                    }

                } catch (ex: Exception) {
                    productLiveData.postValue(Resource.Error(Exception("No connection")))
                }
            }
        }
    }

    fun loadCart() {
        viewModelScope.launch {
            supervisorScope {
                try {
                    cartLiveData.postValue(Resource.Loading)

                    val callFromApi = async { indexRepository.loadCart() }

                    val apiResponse = callFromApi.await()
                    val remoteData = apiResponse.body()
                    if (apiResponse.isSuccessful && remoteData != null) {
                        cartLiveData.postValue(Resource.Success(remoteData))
                    } else {
                        cartLiveData.postValue(Resource.Error(Exception("Not found")))
                    }

                } catch (ex: Exception) {
                    cartLiveData.postValue(Resource.Error(Exception("No connection")))
                }
            }
        }
    }
}
