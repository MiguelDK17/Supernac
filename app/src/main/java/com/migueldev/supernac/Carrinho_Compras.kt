package com.migueldev.supernac


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.migueldev.supernac.adapter.Adapter_Carrinho
import com.migueldev.supernac.databinding.ActivityCarrinhoComprasBinding
import com.migueldev.supernac.model.Produtos


class Carrinho_Compras : AppCompatActivity() {
    private lateinit var binding: ActivityCarrinhoComprasBinding
    private lateinit var myAdapter: Adapter_Carrinho
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCarrinhoComprasBinding.inflate(layoutInflater)
        val view = binding.root
        val context = binding.root.context
        setContentView(view)
        trazerProdutos(context, view)

        binding.toolbarCarrinho.toolbarCarrinho.setNavigationOnClickListener {
            val intent = Intent(context, Categoria::class.java)
            startActivity(intent)
        }

        binding.btConfirmar.setOnClickListener {
            Toast.makeText(
                context, "Você confirmou a compra kkk", Toast.LENGTH_SHORT
            ).show()
        }


        //Comando para colocar R$ no valor pago pelo cliente
        /*SimpleMaskFormatter smf = new SimpleMaskFormatter("R$NNNN.NN ");
        MaskTextWatcher mtw = new MaskTextWatcher(edtValorPago, smf);
        edtValorPago.addTextChangedListener(mtw);*/

        //Fim da Máscara


        }
    fun trazerProdutos(context: Context, view: View){
        val db = Firebase.firestore
        val idDoUsuario = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val layoutManager = LinearLayoutManager(context)
        val carArray = ArrayList<Produtos>()
        binding.recyclerviewCarrinho.layoutManager = layoutManager
        binding.recyclerviewCarrinho.setHasFixedSize(true)
        myAdapter = Adapter_Carrinho(ArrayList())
        binding.recyclerviewCarrinho.adapter = myAdapter

        val caminhoUser = db.collection("Users")
            .document(idDoUsuario)
        val caminhoCarrinho = caminhoUser.collection("carrinho")
            .get()
        caminhoCarrinho
            .addOnSuccessListener {
                query ->
                if (query.isEmpty){
                    binding.tvErro.visibility = View.VISIBLE
                } else {
                    for (document in query.documents){
                        binding.recyclerviewCarrinho.visibility = View.VISIBLE
                        binding.btConfirmar.visibility = View.VISIBLE
                        document.toObject(Produtos::class.java).let {
                            if(it != null){
                                carArray.add(it)
                                myAdapter.updateCarrinho(carArray)
                            }
                        }
                    }
                }
            }
            .addOnFailureListener {
                Toast.makeText(
                    context, "Algo deu errado", Toast.LENGTH_SHORT
                ).show()
            }
    }

}