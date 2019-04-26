package br.com.rectius.contactlist.view.formulario

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import br.com.rectius.contactlist.model.Contact
import br.com.rectius.contactlist.model.Phone
import br.com.rectius.contactlist.model.ResponseStatus
import br.com.rectius.contactlist.repository.ContactRepository

class FormularioViewModel : ViewModel() {

    val contactRepository = ContactRepository()
    val responseStatus: MutableLiveData<ResponseStatus> = MutableLiveData()

    val isLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun salvar(
        id: String? = null,
        name: String,
        email: String,
        phoneWork: String,
        phoneMobile: String
    ) {
        isLoading.value = true
        val phone = Phone(work = phoneWork, mobile = phoneMobile)
        val contact = Contact(id = id, name = name, email = email, phone = phone)
        contactRepository.salvar(contact,
            onComplete = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    true,
                    "Success!"
                )
            }, onError = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    false,
                    it?.message!!
                )
            })

    }

    fun deletar(
        id: String? = null,
        name: String,
        email: String,
        phoneWork: String,
        phoneMobile: String
    ){
        isLoading.value = true
        val phone = Phone(work = phoneWork, mobile = phoneMobile)
        val contact = Contact(id = id, name = name, email = email, phone = phone)

        contactRepository.deletar(contact,
            onComplete = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    true,
                    "Success!"
                )
            }, onError = {
                isLoading.value = false
                responseStatus.value = ResponseStatus(
                    false,
                    it?.message!!
                )
            })

    }

}