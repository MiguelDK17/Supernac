package com.migueldev.supernac

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.migueldev.supernac.adapter.AdapterProds
import com.migueldev.supernac.databinding.ActivityAlimentosBinding
import com.migueldev.supernac.model.Produtos
import com.google.firebase.storage.ktx.component1
import com.google.firebase.storage.ktx.component2

class Alimentos() : AppCompatActivity() {
    var edtTotal: TextView? = null
    var quantidade_final: String? = null
    var TotalDefinitivo: String? = null
    private lateinit var binding: ActivityAlimentosBinding
    private lateinit var prods: ArrayList<Produtos>
    private lateinit var myAdapter: AdapterProds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAlimentosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        alimentos()
        binding.swipeAlimentos.setOnRefreshListener {
            alimentos()
            binding.swipeAlimentos.isRefreshing = false
        }

    }
    private fun alimentos(){
        val recyclerAli = findViewById<RecyclerView>(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerAli.layoutManager = layoutManager
        recyclerAli.setHasFixedSize(true)
        prods = arrayListOf()
        myAdapter = AdapterProds(prods)
        recyclerAli.adapter = myAdapter
        val db = Firebase.firestore
        val storage = Firebase.storage
        binding.pgsAlimentos.visibility = View.VISIBLE
        val docRef = db.collection("/Produtos/Alimentos/Natal")
        docRef
            .get()
            .addOnSuccessListener {
                    result ->
                for (document in result){
                    document.toObject(Produtos::class.java).let { prods.add(it) }
                }
                myAdapter.notifyDataSetChanged()
                binding.pgsAlimentos.visibility = View.INVISIBLE
            }
            .addOnFailureListener {
                binding.pgsAlimentos.visibility = View.INVISIBLE
                Toast.makeText(applicationContext, "Falha ao recuperar os dados do servidor", Toast.LENGTH_LONG)
                    .show()
            }
        val query = db.collection("/Produtos/Alimentos/Salgadinhos")
        query
            .get()
            .addOnSuccessListener {
                result ->
                for (document in result){
                    document.toObject(Produtos::class.java).let { prods.add(it) }
                }
                myAdapter.notifyDataSetChanged()
                binding.pgsAlimentos.visibility = View.INVISIBLE
            }
            .addOnFailureListener {
                binding.pgsAlimentos.visibility = View.INVISIBLE
                Toast.makeText(
                    applicationContext, "Falha ao recuperar os dados do servidor", Toast.LENGTH_SHORT
                ).show()
            }

    }
    private fun comprarAlimentos(){

    }
}