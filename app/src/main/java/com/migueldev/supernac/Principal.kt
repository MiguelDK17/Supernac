package com.migueldev.supernac

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.migueldev.supernac.databinding.ActivityPrincipalBinding

class Principal : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressed()
        val tvRecebeusu: TextView
        val toolbar = binding.toolbarPrincipal.toolbar
        var btcarrinhoflutuante: FloatingActionButton
        val auth = FirebaseAuth.getInstance()
        val usuario = auth.currentUser!!.displayName

       binding.toolbarPrincipal.toolbar.title = "Bem Vindo $usuario"
        /*crdPerfil = (CardView) findViewById(R.id.btPerfil);
        crdTwitter = (CardView) findViewById(R.id.btTwitter);*/
        binding.crdVendas.setOnClickListener {
            val it = Intent(this@Principal, Categoria::class.java)
            startActivity(it)
        }
        binding.crdFacebook.setOnClickListener {
            val feeling = Intent(applicationContext, Facebook::class.java)
            startActivity(feeling)
        }


        /*btTwitter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent Road = new Intent(getApplicationContext(),Twitter.class);
                        startActivity(Road);
                    }
                });*/
        binding.crdLogout.setOnClickListener {
            logout()
        }
    }
    public fun logout(){
        val auth = FirebaseAuth.getInstance()
            val builder = binding.root.context.let {
                AlertDialog.Builder(it)
            }
        builder.apply {
            setTitle(
                "Você será desconectado do app"
            )
            setMessage(
                "Deseja continuar ?"
            )
            setPositiveButton(
                "sim"
            ){dialog, which ->
                auth.signOut()
                val logout = Intent(
                    binding.root.context, MainActivity::class.java
                )
                startActivity(logout)
            }
            setNegativeButton(
                "não"
            ){
                dialog, which ->

            }
        }
        val dialog: AlertDialog =
            builder.create()
        dialog.show()
    }

    override fun onBackPressed() {

    }
}