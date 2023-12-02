package com.migueldev.supernac.adapter

import android.content.DialogInterface
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.migueldev.supernac.R
import com.migueldev.supernac.model.Produtos
import java.text.DecimalFormat

class AdapterProds(private var prods: ArrayList<Produtos>): RecyclerView.Adapter<AdapterProds.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val produtos: Produtos = prods[position]
        holder.idProduto = produtos.id
        holder.nome.text = produtos.nome
        holder.preco.text = produtos.preco
        val nome: String = holder.nome.text.toString()
        val preco: String = holder.preco.text.toString()
        Glide.with(holder.itemView.context)
            .load(produtos.imagem)
            .apply(RequestOptions().placeholder(android.R.drawable.progress_horizontal))
            .into(holder.imagem)
        holder.btComprar.setOnClickListener(object: View.OnClickListener{
            override fun onClick(p0: View?) {
                val builder = AlertDialog.Builder(holder.context)
                builder.apply {
                    setTitle(
                        "CARRINHO"
                    )
                    setMessage(
                        "Insira a quantidade"
                    )

                    //criação de variável e componente EditText
                    val quantidade = EditText(context)
                    //tipo do EditText EditText definido para apenas números
                    quantidade.inputType = InputType.TYPE_CLASS_NUMBER
                    // a View AlertDialog recebe o EditText
                    builder.setView(quantidade)

                    setPositiveButton(
                        "ok",
                        DialogInterface.OnClickListener { dialog, which ->


                            //variavel que irá conter a quantidade desejada
                            val quantidadeFinal = quantidade.text.toString()
                            if (quantidadeFinal == "0"){
                                Toast.makeText(
                                    holder.context, "A quantidade precisa ser superior a 0", Toast.LENGTH_SHORT
                                ).show()
                            }
                            else {

                                //variavel que irá guardar o preço final calculado com base no preço base do produto mais a quantidade
                                val precoFinal: Float = preco.toFloat() * quantidadeFinal.toFloat()
                                val precoFormatado = limitarCasasDecimais(precoFinal)


                                val db = Firebase.firestore
                                val idDoUsuario = FirebaseAuth.getInstance().currentUser?.uid.toString()
                                val dados = hashMapOf(
                                    "nome" to nome,
                                    "preco" to preco,
                                    "imagem" to produtos.imagem,
                                    "quantidade" to quantidadeFinal,
                                    "preco_final" to precoFormatado
                                )
                                val referenciaUsuario = db.collection("Users")
                                    .document(idDoUsuario)
                                    val referenciaCarrinho = referenciaUsuario.collection("carrinho")
                                            referenciaCarrinho
                                    .document(nome)
                                                .set(dados)
                                    .addOnSuccessListener {query ->
                                        Toast.makeText(
                                            context.applicationContext,
                                            "O produto $nome com o preço R$:$precoFormatado foi enviado ao carrinho",
                                            Toast.LENGTH_LONG
                                        ).show()
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(
                                            context, "Ocorreu um erro", Toast.LENGTH_SHORT
                                        ).show()
                                    }
                            }
                        }
                    )
                    setNegativeButton(
                        "CANCELAR",
                        object : DialogInterface.OnClickListener {
                            override fun onClick(p0: DialogInterface?, p1: Int) {
                                p0?.cancel()
                            }
                        }
                    )
                }
                val dialog: AlertDialog = builder.create()
                dialog.setCancelable(false)
                dialog.show()
            }
        })

    }
    fun limitarCasasDecimais(numero: Float): String{
        val df = DecimalFormat("0.00")
        return df.format(numero)
    }


    override fun getItemCount(): Int {
        return prods.size
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var idProduto: String? = null
        var nome =  itemView.findViewById<TextView>(R.id.nome)
        val preco =  itemView.findViewById<TextView>(R.id.preco)
        val btComprar = itemView.findViewById<Button>(R.id.comprar)
        val imagem = itemView.findViewById<ImageView>(R.id.img)
        val context = itemView.context

        fun OnBindView(prods: ArrayList<Produtos>) {
            var idProduto: String? = null
            val nome = itemView.findViewById<TextView>(R.id.none)
            val preco = itemView.findViewById<TextView>(R.id.preco)
            val imagem = itemView.findViewById<ImageView>(R.id.img)
        }
    }
    fun updateList(newList: List<Produtos>){
        prods.clear()
        prods.addAll(newList)
        notifyDataSetChanged()
    }

}