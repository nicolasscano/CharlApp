package com.canodevs.charlapp

import android.app.ProgressDialog
import android.content.ContentProviderClient
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.FirebaseDatabase

class Inicio : AppCompatActivity() {

    // Instanciamos 2 botones, uno para registro y otro para logeo
    private lateinit var Btn_ir_logeo: MaterialButton
    private lateinit var Btn_login_google : MaterialButton

    var firebaseUser: FirebaseUser? = null
    private lateinit var auth : FirebaseAuth

    private lateinit var progressDialog: ProgressDialog
    private lateinit var mGoogleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)

        // Asociamos cada botón por ID a su elemento del layout
        Btn_ir_logeo = findViewById(R.id.Btn_ir_logeo)
        Btn_login_google = findViewById(R.id.Btn_login_google)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Por favor, espere")
        progressDialog.setCanceledOnTouchOutside(false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)


        // Establecemos un Listener para que nos lleve a la pantalla de login
        Btn_ir_logeo.setOnClickListener() {
            val intent = Intent(this@Inicio, LoginActivity::class.java)
            Toast.makeText(applicationContext, "Inicio de sesión", Toast.LENGTH_SHORT).show()
            startActivity(intent)
        }

        Btn_login_google.setOnClickListener {
            empezarInicioSesionGoogle()
        }
    }

    private fun empezarInicioSesionGoogle() {
        val googleSignIntent = mGoogleSignInClient.signInIntent
        googleSignInARL.launch(googleSignIntent)
    }

    private val googleSignInARL = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()){resultado ->
        if (resultado.resultCode == RESULT_OK) {
            val data = resultado.data
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                autenticarGoogleFirebase(account.idToken)
            } catch (e: Exception) {
                Toast.makeText(applicationContext, "Ha ocurrido la siguiente excepción: ${e.message}", Toast.LENGTH_SHORT).show()
            }

        } else {
            Toast.makeText(applicationContext, "Cancelado", Toast.LENGTH_SHORT).show()
        }

    }

    private fun autenticarGoogleFirebase(idToken: String?) {
        val credencial = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credencial)
            .addOnSuccessListener {authResult ->
                // Si el usuario es nuevo
                if (authResult.additionalUserInfo!!.isNewUser) {
                    guardarInfoBBDD()

                // Si el usuario ya existe
                } else {
                    startActivity(Intent(this, MainActivity::class.java))
                    finishAffinity()
                }


            }.addOnFailureListener {e ->
                Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun guardarInfoBBDD() {
        progressDialog.setMessage("Guardando su información...")
        progressDialog.show()
        // Obtener información de una cuenta de Google
        val uidGoogle = auth.uid
        val correoGoogle = auth.currentUser?.email
        val n_Google = auth.currentUser?.displayName
        val nombre_usuario_G : String = n_Google.toString()

        val hashMap = HashMap<String, Any?>()

        hashMap["uid"] = uidGoogle
        hashMap["n_usuario"] = nombre_usuario_G
        hashMap["email"] = correoGoogle
        hashMap["imagen"] = ""
        hashMap["buscar"] = nombre_usuario_G.lowercase()

        /*Nuevos datos de usuario*/
        hashMap["nombres"] = ""
        hashMap["apellidos"] = ""
        hashMap["edad"] = ""
        hashMap["profesion"] = ""
        hashMap["domicilio"] = ""
        hashMap["telefono"] = ""
        hashMap["estado"] = "offline"
        hashMap["proveedor"] = "Google"

        // Referencia a la BBDD
        val reference = FirebaseDatabase.getInstance().getReference("Usuarios")
        reference.child(uidGoogle!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                Toast.makeText(applicationContext, "¡Registro exitoso!", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(applicationContext, "${e.message}", Toast.LENGTH_SHORT).show()
            }

    }

    private fun comprobarSesion() {
        firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            val intent = Intent(this@Inicio, MainActivity::class.java)
            Toast.makeText(applicationContext, "Sesión de usuario activa", Toast.LENGTH_SHORT).show()
            startActivity(intent)
            finish()

        }
    }

    override fun onStart() {
        comprobarSesion()
        super.onStart()
    }
}