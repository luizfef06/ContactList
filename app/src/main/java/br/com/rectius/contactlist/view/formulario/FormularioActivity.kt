package br.com.rectius.contactlist.view.formulario

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import br.com.rectius.contactlist.R
import br.com.rectius.contactlist.model.Contact
import br.com.rectius.contactlist.model.ResponseStatus
import kotlinx.android.synthetic.main.activity_formulario.*
import kotlinx.android.synthetic.main.loading.*

class FormularioActivity : AppCompatActivity() {

    private lateinit var formularioViewModel: FormularioViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_formulario)

        var idContact : String? = null

        val bundle : Bundle? = intent.extras
        bundle?.let {
            bundle.apply {
                val contact = intent.getSerializableExtra("CONTACT") as Contact
                idContact = contact?.id
                inputName.editText?.setText(contact?.name)
                inputEmail.editText?.setText(contact?.email)
                inputPhoneWork.editText?.setText(contact?.phone?.work)
                inputPhoneMobile.editText?.setText(contact?.phone?.mobile)
            }
        }

        formularioViewModel = ViewModelProviders.of(this)
            .get(FormularioViewModel::class.java)

        btSalvar.setOnClickListener {
            formularioViewModel.salvar(
                idContact,
                inputName.editText?.text.toString(),
                inputEmail.editText?.text.toString(),
                inputPhoneWork.editText?.text.toString(),
                inputPhoneMobile.editText?.text.toString()
            )
        }

        btDeletar.setOnClickListener {
            formularioViewModel.deletar(
                idContact,
                inputName.editText?.text.toString(),
                inputEmail.editText?.text.toString(),
                inputPhoneWork.editText?.text.toString(),
                inputPhoneMobile.editText?.text.toString()
            )
        }

        registerObserver()
    }

    private fun registerObserver() {
        formularioViewModel.responseStatus.observe(this, responseObserver)
        formularioViewModel.isLoading.observe(this, loadingObserver)
    }

    private var loadingObserver = Observer<Boolean> {
        if (it == true) {
            containerLoading.visibility = View.VISIBLE
        } else {
            containerLoading.visibility = View.GONE
        }
    }

    private var responseObserver = Observer<ResponseStatus> {
        Toast.makeText(this, it?.mensagem, Toast.LENGTH_SHORT).show()
        if (it?.sucesso == true) {
            setResult(Activity.RESULT_OK)
            finish()
        }

    }
}
