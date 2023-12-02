package com.migueldev.supernac

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.migueldev.supernac.adapter.AdapterProds
import com.migueldev.supernac.databinding.ActivityInformaticaBinding
import com.migueldev.supernac.model.Produtos

class Informatica: AppCompatActivity() {
    private lateinit var binding: ActivityInformaticaBinding
    private lateinit var myAdapter: AdapterProds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformaticaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        informatica()
        binding.swipeInformatica.setOnRefreshListener {
            informatica()
            binding.swipeInformatica.isRefreshing = false
        }
    }
    private fun informatica(){
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView5.layoutManager = layoutManager
        binding.recyclerView5.setHasFixedSize(true)
        val pubsArray = ArrayList<Produtos>()
        myAdapter = AdapterProds(pubsArray)
        binding.recyclerView5.adapter = myAdapter
        binding.recyclerView5.visibility = View.VISIBLE
        val db = Firebase.firestore
        val query = db.collection("/Produtos/Informática/PC,Notebooks e Periféricos")
        query
            .get()
            .addOnSuccessListener {
                result ->
                for (document in result){
                    document.toObject(Produtos::class.java).let { pubsArray.add(it) }
                }
                Log.d(TAG, "informatica: Os dados já deveriam estar aparecendo")
                myAdapter.notifyDataSetChanged()
                binding.pgsInformatica.visibility = View.GONE
            }
            .addOnFailureListener {
                binding.pgsInformatica.visibility = View.GONE
                Toast.makeText(
                    applicationContext, "Falha ao recuperar os dados do servidor", Toast.LENGTH_SHORT
                ).show()
            }
    }
}