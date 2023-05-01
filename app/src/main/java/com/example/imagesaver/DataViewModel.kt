package com.example.imagesaver

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DataViewModel(application: Application) : AndroidViewModel(application) {

    val apiResponse: MutableLiveData<List<ApiResponse>> = MutableLiveData()

    fun getData() {

        val result = RetrofitEndpoint.create().getData()

        result.enqueue(object : Callback<List<ApiResponse>> {

            override fun onResponse(call: Call<List<ApiResponse>>, response: Response<List<ApiResponse>>) {
                if (response.isSuccessful) {
                    val res = response.body()
                    if (res != null) {
                        apiResponse.value = res
                    }
                } else {
                    Toast.makeText(getApplication(), "Request failed: " + response.message(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<ApiResponse>>, t: Throwable) {
                Toast.makeText(getApplication(), "Failed to get response", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
