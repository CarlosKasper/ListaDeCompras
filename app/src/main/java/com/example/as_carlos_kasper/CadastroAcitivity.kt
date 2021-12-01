package com.example.as_carlos_kasper

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_cadastro.*

class CadastroActivity : Activity() {
    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        btn_inserir.setOnClickListener {
            val produto = txt_item_produto.text.toString()
            val qtd = txt_item_qtd.text.toString()
            val valor = txt_item_valor.text.toString()
            if (produto.isNotEmpty() && qtd.isNotEmpty() && valor.isNotEmpty()) {
                val prod = Produto(produto, qtd.toInt(), valor.toDouble(), imageBitMap)
                produtosGlobal.add(prod)
                txt_item_produto.text.clear()
                txt_item_qtd.text.clear()
                txt_item_valor.text.clear()

            } else {
                txt_item_produto.error = if (txt_item_produto.text.isEmpty()) "Preencha o nome do produto" else null
                txt_item_qtd.error = if (txt_item_qtd.text.isEmpty()) "Preencha a quantidade" else null
                txt_item_valor.error = if (txt_item_valor.text.isEmpty()) "Preencha o valor" else null
            }
        }

        img_foto_produto.setOnClickListener{
            abrirGaleria()
        }
    }

    fun abrirGaleria(){
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                val inputStream = data.getData()?.let { contentResolver.openInputStream(it) };
                imageBitMap = BitmapFactory.decodeStream(inputStream)
                img_foto_produto.setImageBitmap(imageBitMap)
            }
        }
    }
}