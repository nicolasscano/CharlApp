package com.canodevs.charlapp.Perfil

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.canodevs.charlapp.R

class EditarImagenPerfil : AppCompatActivity() {

    private lateinit var BtnElegirImagenDe : Button
    private lateinit var BtnActualizarImagen : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_imagen_perfil)

        BtnElegirImagenDe = findViewById(R.id.BtnElegirImagenDe)
        BtnActualizarImagen = findViewById(R.id.BtnActualizarImagen)

        BtnElegirImagenDe.setOnClickListener {
            Toast.makeText(applicationContext, "Seleccionar imagen de", Toast.LENGTH_SHORT).show()
        }

        BtnActualizarImagen.setOnClickListener {
            Toast.makeText(applicationContext, "Actualizar imagen", Toast.LENGTH_SHORT).show()
        }
    }
}