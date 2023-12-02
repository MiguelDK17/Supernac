package com.migueldev.supernac

import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.migueldev.supernac.adapter.AdapterProds
import com.migueldev.supernac.databinding.ActivityCasaBinding
import com.migueldev.supernac.model.Produtos

class Casa(): AppCompatActivity() {
    private lateinit var binding: ActivityCasaBinding
    private lateinit var myAdapter: AdapterProds
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCasaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CasaFun()
        binding.swipeCasa.setOnRefreshListener {
            CasaFun()
            binding.swipeCasa.isRefreshing = false
        }
    }
        private fun CasaFun(){
            val db = Firebase.firestore
            val layoutManager = LinearLayoutManager(binding.root.context)
            val pubsArray = ArrayList<Produtos>()
            binding.recyclerView2.layoutManager = layoutManager
            binding.recyclerView2.setHasFixedSize(true)
            myAdapter = AdapterProds(ArrayList())
            binding.recyclerView2.adapter = myAdapter
            val query = db.collection("Produtos")
                .whereEqualTo("categoria", "casa")
                query
                .get()
                .addOnSuccessListener { query ->
                    for (document in query.documents){
                        document.toObject(Produtos::class.java).let {
                            if (it != null) {
                                pubsArray.add(it)
                                myAdapter.updateList(pubsArray)
                            }
                        }

                    }
                }
                .addOnFailureListener { error ->
                    Toast.makeText(
                        binding.root.context, "Algo deu errado", Toast.LENGTH_SHORT
                    ).show()
                    Toast.makeText(
                        binding.root.context, error.toString(), Toast.LENGTH_LONG
                    ).show()
                }
        }
    }
