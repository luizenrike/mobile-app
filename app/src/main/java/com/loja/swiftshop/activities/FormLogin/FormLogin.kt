package com.loja.swiftshop.activities.FormLogin


import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.google.firebase.ktx.Firebase
import com.loja.swiftshop.R
import com.loja.swiftshop.activities.FormCadastro.FormCadastro
import com.loja.swiftshop.activities.TelaPrincipal.TelaPrincipal
import com.loja.swiftshop.activities.dialog.DialogCarregando
import com.loja.swiftshop.databinding.ActivityFormLoginBinding

class FormLogin : AppCompatActivity() {
    lateinit var binding : ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        val dialogCarregando = DialogCarregando(this)

        binding.buttonLogin.setOnClickListener{ view ->
            val email = binding.editTextEmail.text.toString()
            val senha = binding.editTextPassword.text.toString()

            if(email.isEmpty() || senha.isEmpty()){
                val snackbar = Snackbar.make(view, "Preencha todos os campos", Snackbar.LENGTH_SHORT)
                snackbar.setBackgroundTint(Color.RED)
                snackbar.setTextColor(Color.WHITE)
                snackbar.show()
            }else{
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener{ tarefa ->
                    if(tarefa.isSuccessful){
                        dialogCarregando.iniciarCarregamentoAlertDialog()
                        Handler(Looper.getMainLooper()).postDelayed({
                            iniciarTelaPrincipal()
                            dialogCarregando.liberarAlertDialog()
                        }, 2000)

                    }
                }.addOnFailureListener{ erroLogin ->
                    val mensagemErro = when(erroLogin){
                        is FirebaseAuthInvalidUserException ->  "Email ou senha incorretos"
                        is FirebaseAuthEmailException -> "Email ou senha incorretos"
                        is FirebaseAuthWeakPasswordException -> "Email ou senha incorretos"
                        else -> "Email ou senha incorretos"
                    }
                    val snackbar = Snackbar.make(view, mensagemErro, Snackbar.LENGTH_SHORT)
                    snackbar.setBackgroundTint(Color.RED)
                    snackbar.setTextColor(Color.WHITE)
                }
            }
        }
        binding.textTelaCadastro.setOnClickListener{
            val intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun iniciarTelaPrincipal(){
        val intent = Intent(this, TelaPrincipal::class.java)
        startActivity(intent)
        finish()
    }

    override fun onStart(){
        super.onStart()

        val usuarioAtual = FirebaseAuth.getInstance().currentUser
        if(usuarioAtual != null){
            iniciarTelaPrincipal()
        }
    }
}