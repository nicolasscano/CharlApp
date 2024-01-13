package com.canodevs.charlapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class RegistroActivity : AppCompatActivity() {

    // Instanciamos los botones donde ingresamos los datos
    private lateinit var R_Et_nombre_usuario: EditText
    private lateinit var R_Et_email: EditText
    private lateinit var R_Et_password: EditText
    private lateinit var R_Et_r_password: EditText
    private lateinit var Btn_registrar: Button

    // Instanciamos la autenticación con Firebase
    private lateinit var auth: FirebaseAuth
    private lateinit var reference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        //supportActionBar!!.title = "Registro"
        inicializarVariables()

        Btn_registrar.setOnClickListener() {
            validarDatos()
        }
    }

    private fun inicializarVariables() {
        R_Et_nombre_usuario = findViewById(R.id.R_Et_nombre_usuario)
        R_Et_email = findViewById(R.id.R_Et_email)
        R_Et_password = findViewById(R.id.R_Et_password)
        R_Et_r_password = findViewById(R.id.R_Et_r_password)
        Btn_registrar = findViewById(R.id.Btn_registrar)

        // Creamos la instancia de Firebase Authentication
        auth = FirebaseAuth.getInstance()
    }

    private fun validarDatos() {
        val nombreUsuario: String = R_Et_nombre_usuario.text.toString()
        val email: String = R_Et_email.text.toString()
        val password: String = R_Et_password.text.toString()
        val r_password: String = R_Et_r_password.text.toString()

        if (nombreUsuario.isEmpty()) {
            Toast.makeText(applicationContext, "Escriba su nombre de usuario", Toast.LENGTH_SHORT)
                .show()
        } else if (email.isEmpty()) {
            Toast.makeText(applicationContext, "Escriba su correo electrónico", Toast.LENGTH_SHORT)
                .show()
        } else if (password.isEmpty()) {
            Toast.makeText(applicationContext, "Escriba su contraseña", Toast.LENGTH_SHORT).show()
        } else if (r_password.isEmpty()) {
            Toast.makeText(
                applicationContext,
                "Por favor, repita su contraseña",
                Toast.LENGTH_SHORT
            ).show()
        } else if (!password.equals(r_password)) {
            Toast.makeText(
                applicationContext,
                "Las contraseñas deben coincidir",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            registrarUsuario(email, password)
        }
    }

    private fun registrarUsuario(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    var uid: String = ""
                    uid = auth.currentUser!!.uid
                    reference =
                        FirebaseDatabase.getInstance().reference.child("Usuarios").child(uid)

                    val hashMap = HashMap<String, Any>()
                    val h_nombre_usuario: String = R_Et_nombre_usuario.text.toString()
                    val h_email: String = R_Et_email.text.toString()

                    hashMap["uid"] = uid
                    hashMap["n_usuario"] = h_nombre_usuario
                    hashMap["email"] = h_email
                    hashMap["imagen"] = ""
                    hashMap["buscar"] = h_nombre_usuario.lowercase()

                    /*Nuevos datos de usuario*/
                    hashMap["nombres"] = ""
                    hashMap["apellidos"] = ""
                    hashMap["edad"] = ""
                    hashMap["profesion"] = ""
                    hashMap["domicilio"] = ""
                    hashMap["estado"] = "offline"

                    reference.updateChildren(hashMap).addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            val intent = Intent(this@RegistroActivity, MainActivity::class.java)
                            Toast.makeText(
                                applicationContext,
                                "¡Registrado correctamente!",
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(intent)
                        }
                    }.addOnFailureListener { e ->
                        Toast.makeText(
                            applicationContext,
                            "${e.message}",
                            Toast.LENGTH_SHORT
                        ).show()

                    }
                } else {
                    Toast.makeText(
                        applicationContext,
                        "Ha ocurrido un error",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    applicationContext,
                    "${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

    }

}