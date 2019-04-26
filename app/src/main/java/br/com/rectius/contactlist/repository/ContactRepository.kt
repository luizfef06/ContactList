package br.com.rectius.contactlist.repository

import br.com.rectius.contactlist.api.getContactAPI
import br.com.rectius.contactlist.model.Contact
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ContactRepository {

    fun buscarTodos(
        onComplete:(List<Contact>?) -> Unit,
        onError: (Throwable?) -> Unit
    ) {

        getContactAPI()
            .getContacts()
            .enqueue(object : Callback<List<Contact>>{
                override fun onFailure(call: Call<List<Contact>>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<List<Contact>>, response: Response<List<Contact>>) {
                    if(response.isSuccessful) {
                        onComplete(response.body())
                    } else {
                        onError(Throwable("Erro ao buscar os dados"))
                    }
                }
            })

    }



    fun salvar(contact: Contact,
               onComplete: (Contact) -> Unit,
               onError: (Throwable?) -> Unit) {

        val idContact : String? = contact.id

        if (idContact == null) {
            getContactAPI()
                .create(contact)
                .enqueue(object : Callback<Contact>{
                    override fun onFailure(call: Call<Contact>, t: Throwable) {
                        onError(t)
                    }

                    override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                        if(response.isSuccessful) {
                            onComplete(response.body()!!)
                        } else {
                            onError(Throwable("Creation error!"))
                        }
                    }
                })
        } else {
            getContactAPI()
                .update(idContact, contact)
                .enqueue(object : Callback<Contact>{
                    override fun onFailure(call: Call<Contact>, t: Throwable) {
                        onError(t)
                    }

                    override fun onResponse(call: Call<Contact>, response: Response<Contact>) {
                        if(response.isSuccessful) {
                            onComplete(response.body()!!)
                        } else {
                            onError(Throwable("Updating error!"))
                        }
                    }
                })
        }
    }

    fun deletar(contact: Contact,
                onComplete:(Void?) -> Unit,
                onError: (Throwable?) -> Unit) {
        val idContact : String? = contact.id
        getContactAPI()
            .delete(idContact)
            .enqueue(object : Callback<Void>{
                override fun onFailure(call: Call<Void>, t: Throwable) {
                    onError(t)
                }

                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if(response.isSuccessful) {
                        onComplete(response.body())
                    } else {
                        onError(Throwable("An error has occurred during delete operation!"))
                    }
                }
            })
    }




}