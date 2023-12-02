package com.migueldev.supernac

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.migueldev.supernac.adapter.AdapterProds
import com.migueldev.supernac.databinding.ActivityJogosBinding
import com.migueldev.supernac.model.Produtos

class Jogos(): AppCompatActivity() {
    private lateinit var binding: ActivityJogosBinding
    private lateinit var myAdapter: AdapterProds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJogosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        jogosFun()
        binding.swipeJogos.setOnRefreshListener {
            jogosFun()
            binding.swipeJogos.isRefreshing = false
        }
    }
    private fun jogosFun(){
        val db = Firebase.firestore
        Log.d(TAG, "JogosFun: Dentro da função jogos")
        val layoutManager = LinearLayoutManager(this)
        val pubsArray = ArrayList<Produtos>()
        binding.recyclerView6.layoutManager = layoutManager
        binding.recyclerView6.setHasFixedSize(true)
        myAdapter = AdapterProds(pubsArray)
        binding.recyclerView6.adapter = myAdapter
        Log.d(TAG, "JogosFun: Iniciando preparação para a função get")
        val query = db.collection("Produtos/Jogos/Consoles")
        query
            .get()
            .addOnSuccessListener {
                result ->
                for (document in result){
                    document.toObject(Produtos::class.java).let { pubsArray.add(it) }
                    Log.d(TAG, "JogosFun: Função for concluida e os dados já estão no recyclerView")
                }
                Log.d(TAG, "JogosFun: Era pro recyclerView estar funcionando")
                myAdapter.notifyDataSetChanged()
                binding.pgsJogos.visibility = View.GONE
            }
            .addOnFailureListener {
                Log.d(TAG, "JogosFun: Caiu na excessão")
                binding.pgsJogos.visibility = View.GONE
                Toast.makeText(
                    applicationContext, "Falha ao recuperar os dados do servidor", Toast.LENGTH_SHORT
                ).show()
            }

    }
}