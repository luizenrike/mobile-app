package com.loja.swiftshop.activities.dialog

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import com.google.firebase.auth.FirebaseAuth
import com.loja.swiftshop.activities.FormLogin.FormLogin
import com.loja.swiftshop.databinding.DialogPerfilUsuarioBinding
import com.loja.swiftshop.model.DB

class DialogPerfilUsuario(private val activity: Activity) {

    lateinit var dialog: AlertDialog
    lateinit var binding: DialogPerfilUsuarioBinding

    fun iniciarPerfilUsuario(){
        val builder = AlertDialog.Builder(activity)
        binding = DialogPerfilUsuarioBinding.inflate(activity.layoutInflater)
        builder.setView(binding.root)
        builder.setCancelable(true)
        dialog = builder.create()
        dialog.show()
    }

    fun recuperarDadosUsuarioBanco(){
        val nomeUsuario = binding.txtNomeUsuario
        val emailUsuario = binding.txtEmailUsuario
        val fotoUsuario = binding.containerCirculo
        val db = DB()
        db.recuperarDadosUsuarioPerfil(activity, nomeUsuario, emailUsuario, fotoUsuario)

        binding.btDeslogar.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(activity, FormLogin::class.java)
            activity.startActivity(intent)
            activity.finish()
        }
    }

}