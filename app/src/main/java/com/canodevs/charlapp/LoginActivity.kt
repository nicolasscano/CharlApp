package com.canodevs.charlapp

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var L_Et_email: EditText
    private lateinit var L_Et_password: EditText
    private lateinit var Btn_login: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var TXT_ir_registro : MaterialButton


    private lateinit var progressDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //supportActionBar!!.title = "Login"
        inicializarVariables()

        Btn_login.setOnClickListener {
            ValidarDatos()
        }

        TXT_ir_registro.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistroActivity::class.java)
            startActivity(intent)
        }
    }

    private fun inicializarVariables() {
        L_Et_email = findViewById(R.id.L_Et_email)
        L_Et_password = findViewById(R.id.L_Et_password)
        Btn_login = findViewById(R.id.Btn_Login)
        auth = FirebaseAuth.getInstance()
        TXT_ir_registro = findViewById(R.id.TXT_ir_registro)

        // Config del progress dialog del login
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Iniciando sesión...")
        progressDialog.setCanceledOnTouchOutside(false)
    }

    private fun ValidarDatos() {
        val email: String = L_Et_email.text.toString()
        val password: String = L_Et_password.text.toString()

        if (email.isEmpty()) {
            Toast.makeText(applicationContext, "Escribe tu correo electrónico", Toast.LENGTH_SHORT)
                .show()
        }
        if (password.isEmpty()) {
            Toast.makeText(applicationContext, "Escribe tu contraseña", Toast.LENGTH_SHORT).show()
        } else {
            LoginUsuario(email, password)
        }


    }

    private fun LoginUsuario(email: String, password: String) {
        progressDialog.setMessage("Espere por favor")
        progressDialog.show()
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    progressDialog.dismiss()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    Toast.makeText(
                        applicationContext,
                        "Inicio de sesión exitoso",
                        Toast.LENGTH_SHORT
                    ).show()
                    startActivity(intent)
                    finish()
                } else {
                    progressDialog.dismiss()
                    Toast.makeText(
                        applicationContext,
                        "Ha ocurrido un error",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }.addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    applicationContext,
                    "{${e.message}}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}