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
import com.migueldev.supernac.databinding.ActivityBebidasBinding
import com.migueldev.supernac.model.Produtos

class Bebidas: AppCompatActivity() {
    private lateinit var binding: ActivityBebidasBinding
    private lateinit var myAdapter: AdapterProds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBebidasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bebidas()
        binding.sipeBebidas.setOnRefreshListener {
            bebidas()
            binding.sipeBebidas.isRefreshing = false
        }
    }
     private fun bebidas(){
        val layoutManager = LinearLayoutManager(this)
         binding.recyclerView1.layoutManager = layoutManager
         binding.recyclerView1.setHasFixedSize(true)
         val pubsArray = ArrayList<Produtos>()
         myAdapter = AdapterProds(pubsArray)
         binding.recyclerView1.adapter = myAdapter
         val db = Firebase.firestore
         binding.pgsBebidas.visibility = View.VISIBLE
         val query = db.collection("/Produtos/Alimentos/Bebidas")
         query
             .get()
             .addOnSuccessListener {
                 result ->
                 for (document in result){
                     document.toObject(Produtos::class.java).let { pubsArray.add(it) }
                 }
                 myAdapter.notifyDataSetChanged()
                 binding.pgsBebidas.visibility = View.GONE
             }
             .addOnFailureListener{
                 binding.pgsBebidas.visibility = View.GONE
                 Toast.makeText(
                     applicationContext, "Falha ao recuperar os dados do servidor", Toast.LENGTH_SHORT
                 ).show()
             }
    }
}