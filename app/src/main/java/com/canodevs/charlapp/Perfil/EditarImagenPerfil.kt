package com.canodevs.charlapp.Perfil

import android.app.Dialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import com.canodevs.charlapp.R

class EditarImagenPerfil : AppCompatActivity() {

    private lateinit var ImagenPerfilActualizar : ImageView
    private lateinit var BtnElegirImagenDe : Button
    private lateinit var BtnActualizarImagen : Button
    private var imageUri : Uri?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_imagen_perfil)

        ImagenPerfilActualizar = findViewById(R.id.ImagenPerfilActualizar)
        BtnElegirImagenDe = findViewById(R.id.BtnElegirImagenDe)
        BtnActualizarImagen = findViewById(R.id.BtnActualizarImagen)

        BtnElegirImagenDe.setOnClickListener {
            // Toast.makeText(applicationContext, "Seleccionar imagen de", Toast.LENGTH_SHORT).show()
            mostrarDialog()
        }

        BtnActualizarImagen.setOnClickListener {
            Toast.makeText(applicationContext, "Actualizar imagen", Toast.LENGTH_SHORT).show()
        }
    }

    private fun abrirGaleria() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galeriaActivityResultLauncher.launch(intent)
    }

    private val galeriaActivityResultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult(),
        ActivityResultCallback<ActivityResult> { resultado ->
            if (resultado.resultCode == RESULT_OK) {
                val data = resultado.data
                imageUri = data!!.data
                ImagenPerfilActualizar.setImageURI(imageUri)
            } else {
                Toast.makeText(applicationContext, "Cancelado por el usuario", Toast.LENGTH_SHORT).show()
            }
        }
    )



    private fun mostrarDialog() {
        val Btn_abrir_galeria : Button
        val Btn_abrir_camara : Button

        val dialog = Dialog(this@EditarImagenPerfil)

        dialog.setContentView(R.layout.cuadro_d_seleccionar)

        Btn_abrir_galeria = dialog.findViewById(R.id.Btn_abrir_galeria)
        Btn_abrir_camara = dialog.findViewById(R.id.Btn_abrir_camara)

        Btn_abrir_galeria.setOnClickListener {
            // Toast.makeText(applicationContext, "Abrir galería", Toast.LENGTH_SHORT).show()
            abrirGaleria()
            dialog.dismiss()
        }

        Btn_abrir_camara.setOnClickListener {
            Toast.makeText(applicationContext, "Abrir cámara", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()


    }
}