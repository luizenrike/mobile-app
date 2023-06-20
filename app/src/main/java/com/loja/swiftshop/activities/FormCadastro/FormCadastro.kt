package com.loja.swiftshop.activities.FormCadastro

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.loja.swiftshop.R
import com.loja.swiftshop.activities.FormLogin.FormLogin
import com.loja.swiftshop.activities.dialog.DialogCarregando
import com.loja.swiftshop.databinding.ActivityFormCadastroBinding
import com.loja.swiftshop.databinding.ActivityFormLoginBinding
import com.loja.swiftshop.model.DB

class FormCadastro : AppCompatActivity() {
    lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)

        setContentView(binding.root)
        supportActionBar!!.hide()
        val dialog = DialogCarregando(this)
        val db = DB()

        binding.buttonCadastrar.setOnClickListener{
            val nome = binding.editNome.text.toString()
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()

            if(nome.isEmpty() || email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(it, "Preencha todos os campos de cadastro", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }else{
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener{ tarefa->
                    if(tarefa.isSuccessful){
                        val snackbar = Snackbar.make(it, "Cadastro Realizado", Snackbar.LENGTH_LONG)
                        snackbar.setBackgroundTint(Color.GREEN)
                        snackbar.setTextColor(Color.WHITE)
                        snackbar.show()
                        db.salvarDadosUsuario(nome)
                        dialog.iniciarCarregamentoAlertDialog()
                        Handler(Looper.getMainLooper()).postDelayed({
                            FirebaseAuth.getInstance().signOut()
                            val intentLogin = Intent(this, FormLogin::class.java)
                            startActivity(intentLogin)
                            dialog.liberarAlertDialog()
                        }, 2000)

                    }
                }.addOnFailureListener{ erroCadastro ->
                    val mensagemErro = when(erroCadastro) {
                        is FirebaseAuthWeakPasswordException -> "Digite uma senha com no mínimo seis caracteres"
                        is FirebaseAuthUserCollisionException -> "Este email já foi cadastrado"
                        is FirebaseNetworkException -> "Sem conexão com a internet"
                        else -> "Erro ao cadastrar usuário"
                    }
                    val snackbar = Snackbar.make(it, mensagemErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.setTextColor(Color.WHITE)
                    snackbar.show()
                }
            }
        }
    }
}