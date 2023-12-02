package com.migueldev.supernac

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.migueldev.supernac.databinding.ActivityCategoriaBinding

class Categoria : AppCompatActivity() {
    private lateinit var binding : ActivityCategoriaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCategoriaBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.toolbar.toolbarCarrinho.setNavigationOnClickListener {
            val intent = Intent(applicationContext, Principal::class.java)
            startActivity(intent)
        }

        binding.crdJogos.setOnClickListener {
            val it = Intent(applicationContext, Jogos::class.java)
            startActivity(it)
        }
        binding.crdEletronicos.setOnClickListener {
            val nova = Intent(applicationContext, Eletronicos::class.java)
            startActivity(nova)
        }
        binding.crdAlimentos.setOnClickListener {
            val lib = Intent(applicationContext, Alimentos::class.java)
            startActivity(lib)
        }
        binding.crdInformatica.setOnClickListener {
            val info = Intent(applicationContext, Informatica::class.java)
            startActivity(info)
        }
        binding.crdCasa.setOnClickListener {
            val house = Intent(applicationContext, Casa::class.java)
            startActivity(house)
        }
        binding.crdBebidas.setOnClickListener {
            val drink = Intent(applicationContext, Bebidas::class.java)
            startActivity(drink)
        }
        binding.crdHortifruti.setOnClickListener {
            val fruit = Intent(applicationContext, Hortifruti::class.java)
            startActivity(fruit)
        }
        binding.crdPadaria.setOnClickListener {
            val cupcake = Intent(applicationContext, Padaria::class.java)
            startActivity(cupcake)
        }
        binding.btCarrinho.setOnClickListener {
            val carrinho = Intent(applicationContext, Carrinho_Compras::class.java)
            startActivity(carrinho)
        }
    }
}