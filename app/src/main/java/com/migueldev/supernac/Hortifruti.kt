package com.migueldev.supernac

import android.content.AbstractThreadedSyncAdapter
import android.os.Bundle
import android.os.TokenWatcher
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.migueldev.supernac.adapter.AdapterProds
import com.migueldev.supernac.databinding.ActivityHortifrutiBinding
import com.migueldev.supernac.model.Produtos

class Hortifruti: AppCompatActivity() {
    private lateinit var binding: ActivityHortifrutiBinding
    private lateinit var myAdapter: AdapterProds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHortifrutiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        hortifruti()
        binding.swipeHortifruti.setOnRefreshListener {
            hortifruti()
            binding.swipeHortifruti.isRefreshing = false
        }
    }
    private fun hortifruti(){
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView4.layoutManager = layoutManager
        binding.recyclerView4.setHasFixedSize(true)
        val pubsArray = ArrayList<Produtos>()
        myAdapter = AdapterProds(pubsArray)
        binding.recyclerView4.adapter = myAdapter
        val db = Firebase.firestore
        binding.pgsHortifruti.visibility = View.VISIBLE
        val query = db.collection("/Produtos/Alimentos/Hortifruti")
        query
            .get()
            .addOnSuccessListener {
                result ->
                for (document in result){
                    document.toObject(Produtos::class.java).let { pubsArray.add(it) }
                }
                myAdapter.notifyDataSetChanged()
                binding.pgsHortifruti.visibility = View.GONE
            }
            .addOnFailureListener{
                binding.pgsHortifruti.visibility = View.GONE
                Toast.makeText(
                    applicationContext, "Falha ao recuperar os dados do servidor", Toast.LENGTH_SHORT
                ).show()
            }
    }
}