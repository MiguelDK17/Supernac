package com.migueldev.supernac

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.migueldev.supernac.adapter.AdapterProds
import com.migueldev.supernac.databinding.ActivityEletronicosBinding
import com.migueldev.supernac.model.Produtos

class Eletronicos(): AppCompatActivity() {
    private lateinit var binding: ActivityEletronicosBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEletronicosBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragment = EletronicosFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.container_1, fragment)
            .commit()

    }
    class MeuViewModel: ViewModel(){
        private val _progressBArVisibility = MutableLiveData<Int>(View.GONE)
        val progressBarVisibility: LiveData<Int>
        get() = _progressBArVisibility

        // Método para atualizar a visiilidade do ProgressBar

        fun setProgressBarVisibiity(visibility: Int){
            _progressBArVisibility.value = visibility
        }
    }
     class EletronicosFragment: Fragment(){
        private lateinit var binding: ActivityEletronicosBinding
        private lateinit var meuViewModel: MeuViewModel
        private lateinit var myAdapter: AdapterProds
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View {
            binding = ActivityEletronicosBinding.inflate(inflater, container, false)
            meuViewModel = ViewModelProvider(this).get(MeuViewModel::class.java)
            binding.pgsEletronicos.visibility = View.VISIBLE

            //Vinculação do ViewModel ao Data Binding
            EletronicosFun()
            binding.swipeEletronicos.setOnRefreshListener {
                EletronicosFun()
                binding.swipeEletronicos.isRefreshing = false
            }
            binding.pgsEletronicos.visibility = View.GONE
            return binding.root
        }
        @SuppressLint("SuspiciousIndentation")
        fun EletronicosFun(){
            val db = Firebase.firestore
            //val recyclerView = rootView.findViewById<RecyclerView>(R.id.recyclerView_3)
            val layoutManager = LinearLayoutManager(binding.root.context)
            val pubsArray = ArrayList<Produtos>()
            //val progressBar = rootView.findViewById<ProgressBar>(R.id.pgs_eletronicos)
            binding.recyclerView3.layoutManager = layoutManager
            binding.recyclerView3.setHasFixedSize(true)
            myAdapter = AdapterProds(ArrayList())
            binding.recyclerView3.adapter = myAdapter
            val query = db.collection("Produtos")
                .whereEqualTo("categoria", "eletronicos")
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
}