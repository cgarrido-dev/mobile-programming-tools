package com.example.claudio_garrido_20240531_continuidad_ing_informatica

import android.os.AsyncTask
import retrofit2.Response

class FetchPharmacyTask(private val callback: (List<Pharmacy>?) -> Unit) : AsyncTask<Void, Void, List<Pharmacy>?>() {

    override fun doInBackground(vararg params: Void?): List<Pharmacy>? {
        return try {
            val call = RetrofitClient.instance.getPharmacies()
            val response: Response<List<Pharmacy>> = call.execute()
            response.body()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    override fun onPostExecute(result: List<Pharmacy>?) {
        super.onPostExecute(result)
        callback(result)
    }
}
