package tw.teng.practice.contact.resource.network.api_interface

import retrofit2.Call
import retrofit2.http.GET
import tw.teng.practice.contact.resource.network.model.APIContacts

interface ContactInterface {
    @GET("/users")
    fun getUsers(): Call<APIContacts>
}