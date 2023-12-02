package com.migueldev.supernac

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.migueldev.supernac.adapter.AdapterProds

import com.migueldev.supernac.databinding.ActivityPadariaBinding
import com.migueldev.supernac.model.Produtos

class Padaria: AppCompatActivity() {
    private lateinit var binding: ActivityPadariaBinding
    private lateinit var myAdapter: AdapterProds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPadariaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        padaria()
        binding.swipePadaria.setOnRefreshListener {
            padaria()
            binding.swipePadaria.isRefreshing = false
        }
    }
    private fun padaria(){
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView5.layoutManager = layoutManager
        binding.recyclerView5.setHasFixedSize(true)
        val pubsArray = ArrayList<Produtos>()
        myAdapter = AdapterProds(pubsArray)
        binding.recyclerView5.adapter = myAdapter
        val db = Firebase.firestore
        binding.pgsPadaria.visibility = View.VISIBLE
        val query = db.collection("/Produtos/Alimentos/Padaria")
        query
            .get()
            .addOnSuccessListener {
                result ->
                for (document in result){
                    document.toObject(Produtos::class.java).let { pubsArray.add(it) }
                }
                myAdapter.notifyDataSetChanged()
                binding.pgsPadaria.visibility = View.GONE
            }
            .addOnFailureListener{
                Toast.makeText(
                    applicationContext, "Falha ao recuperar os dados do servidor", Toast.LENGTH_SHORT
                ).show()
            }
        //Tem que declarar o arquivo no manifest
    }
}