package com.migueldev.supernac

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.migueldev.supernac.databinding.ActivityCriarContaBinding

class Criar_Conta : AppCompatActivity() {
    private lateinit var binding: ActivityCriarContaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCriarContaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btConfirmar.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if (binding.edtNome.text.equals("") && binding.edtSobrenome.text.equals("") && binding.edtEmail.text.equals("") && binding.edtSenha.text.equals("")){
                    Toast.makeText(
                        applicationContext, "Os campos precisam estar preenchidos", Toast.LENGTH_SHORT
                    ).show()
                }
                else {
                    criarConta()
                }
            }
        })
    }
    private fun criarConta(){
        val db = Firebase.firestore
        val nome = binding.edtNome.text.toString()
        val sobrenome = binding.edtSobrenome.text.toString()
        val email: String = binding.edtEmail.text.toString()
        val password: String = binding.edtSenha.text.toString()
        val id = FirebaseAuth.getInstance().currentUser?.uid.toString()
        var auth= FirebaseAuth.getInstance()
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this){
                task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    val profileUpdates = userProfileChangeRequest {
                        displayName = binding.edtNome.text.toString()
                    }
                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener(this) {
                            task ->
                            if (task.isSuccessful){
                                db.collection("Users")
                                    .add(id)
                                    .addOnSuccessListener {query ->
                                        if(query != null){

                                            val dados = hashMapOf(
                                                "nome" to nome,
                                                "sobrenome" to sobrenome,
                                                 "id" to id
                                            )

                                            db.collection("Users")
                                                .document(id)
                                                .set(dados)
                                                .addOnSuccessListener { query ->
                                                    if(query != null){
                                                        val after = Intent (applicationContext, Principal::class.java)
                                                        startActivity(after)
                                                    }
                                                }
                                        }
                                    }

                                }
                            else {
                                Toast.makeText(
                                    applicationContext, "Falha ao enviar as informações do usuário", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }


                }
                else {
                    Toast.makeText(applicationContext, "Falha na criação da conta", Toast.LENGTH_SHORT)
                        .show()
                }
            }

    }
}