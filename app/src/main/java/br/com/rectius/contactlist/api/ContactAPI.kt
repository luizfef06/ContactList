package br.com.rectius.contactlist.api

import br.com.rectius.contactlist.model.Contact
import retrofit2.Call
import retrofit2.http.*

interface ContactAPI {

    @GET("/api/contacts")
    fun getContacts() : Call<List<Contact>>

    @POST("/api/contacts")
    fun create(@Body contact: Contact): Call<Contact>

    @PUT("/api/contacts/{id}")
    fun update(@Path("id") id: String?, @Body contact: Contact): Call<Contact>

    @DELETE("/api/contacts/{id}")
    fun delete(@Path("id") id: String?): Call<Void>
}