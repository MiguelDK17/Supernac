package com.migueldev.supernac.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.migueldev.supernac.R
import com.migueldev.supernac.model.Produtos

class Adapter_Carrinho(private var carrinho: ArrayList<Produtos>): RecyclerView.Adapter<Adapter_Carrinho.ViewHolderCarrinho>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderCarrinho {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_carrinho, parent,false)
        return ViewHolderCarrinho(view)
    }

    override fun onBindViewHolder(holder: ViewHolderCarrinho, position: Int) {
        val carrinhos: Produtos = carrinho[position]
        holder.nome.text = carrinhos.nome
        holder.preco_original.text = carrinhos.preco
        holder.preco_final.text = carrinhos.preco_final
        holder.quantidade.text = carrinhos.quantidade
        Glide.with(holder.itemView.context)
            .load(carrinhos.imagem)
            .apply(RequestOptions().placeholder(android.R.drawable.progress_horizontal))
            .into(holder.imagem)
        holder.btExcluir.setOnClickListener {
            val db = Firebase.firestore
            val id = holder.nome.text.toString()
            val itemIndex = holder.adapterPosition
            val idDoUsuario = FirebaseAuth.getInstance().currentUser?.uid.toString()
            val caminhoUsuario = db.collection("Users")
                .document(idDoUsuario)
            val caminhoCarrinho = caminhoUsuario.collection("carrinho")
                .document(id)
            caminhoCarrinho
                .delete()
                .addOnSuccessListener {
                    carrinho.removeAt(itemIndex)
                    notifyItemRemoved(itemIndex)
                    notifyItemRangeRemoved(itemIndex, carrinho.size - holder.adapterPosition)
                    Toast.makeText(
                        holder.context, "O produto foi excluído do carrinho", Toast.LENGTH_SHORT
                    ).show()
                }
                .addOnFailureListener {
                    Toast.makeText(
                        holder.context, "Falha ao excluir produto do carrinho", Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    override fun getItemCount(): Int {
       return carrinho.size
    }
    class ViewHolderCarrinho(itemView: View): RecyclerView.ViewHolder(itemView) {
        var nome =  itemView.findViewById<TextView>(R.id.nome_produto)
        val preco_original =  itemView.findViewById<TextView>(R.id.preco_original)
        val preco_final =  itemView.findViewById<TextView>(R.id.preco_final)
        val imagem = itemView.findViewById<ImageView>(R.id.foto_produto)
        val quantidade = itemView.findViewById<TextView>(R.id.quantidade_do_produto)
        val btExcluir = itemView.findViewById<Button>(R.id.bt_excluir)
        val context = itemView.context
        fun OnBindView(prods: ArrayList<Produtos>) {
            val nome = itemView.findViewById<TextView>(R.id.none)
            val preco = itemView.findViewById<TextView>(R.id.preco)
            val imagem = itemView.findViewById<ImageView>(R.id.img)
        }

    }
    fun updateCarrinho(newList: List<Produtos>){
        carrinho.clear()
        carrinho.addAll(newList)
        notifyDataSetChanged()
    }
}