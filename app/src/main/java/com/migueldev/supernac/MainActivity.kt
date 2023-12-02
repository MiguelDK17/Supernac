package com.migueldev.supernac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import android.graphics.Paint
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.migueldev.supernac.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //declaração de variável
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    var usuario: String? = null
    var senha: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        if (user != null){
            val direto = Intent(applicationContext, Principal::class.java)
            startActivity(direto)
        }



        binding.btLogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View) {
                usuario = binding.edtUsuario.text.toString()
                senha = binding.edtSenha.text.toString()
                if (binding.edtUsuario.text.toString().equals("")){
                    Toast.makeText(
                        applicationContext, "Ops os campos não podem ficar vazios", Toast.LENGTH_SHORT
                    ).show()
                }
                else if (binding.edtSenha.text.toString().equals("")){
                    Toast.makeText(
                        applicationContext, "Ops os campos não podem ficar vazios", Toast.LENGTH_SHORT
                    ).show()
                }

                else if (binding.edtUsuario.text.toString().equals("") && binding.edtSenha.text.toString().equals("")) {
                    Toast.makeText(
                        applicationContext, "Ops os campos não podem ficar vazios", Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    login()
                }
            }
        })
        binding.tvCadastro.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                val cadastro = Intent (applicationContext, Criar_Conta::class.java)
                startActivity(cadastro)
            }
        })
        binding.tvVersO.setOnClickListener(View.OnClickListener {
            val sublinhado = Intent(applicationContext, Let_It_Be_Naked::class.java)
            startActivity(sublinhado)
        })
            binding.tvesqueceu.paintFlags = binding.tvesqueceu.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            binding.tvCadastro.paintFlags = binding.tvCadastro.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            binding.tvVersO.paintFlags = binding.tvVersO.paintFlags or Paint.UNDERLINE_TEXT_FLAG
    }
    private fun login(){
        val email: String =  binding.edtUsuario.text.toString()
        val password: String = binding.edtSenha.text.toString()
        auth = FirebaseAuth.getInstance()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                task ->
                if (task.isSuccessful){
                    val login = Intent (applicationContext, Principal::class.java)
                    startActivity(login)
                }
                else {
                    Toast.makeText(applicationContext, "nome de usuario ou senha incorretos", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            .addOnFailureListener(this){ error ->
                Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT)
                    .show()
            }
    }

    override fun onBackPressed() {
        application.onTerminate()
    }
}