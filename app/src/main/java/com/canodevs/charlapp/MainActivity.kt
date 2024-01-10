package com.canodevs.charlapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Define menu resource file (menu/menu_principal.xml)
        // val menuResId = R.menu.menu_principal

        // Inflate menu resource into the Toolbar
        // val toolbar: Toolbar = findViewById(R.id.toolbar)
        // toolbar.inflateMenu(menuResId)

        // Set support action bar
        // setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater : MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_principal, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_salir -> {
                // Sign out of Firebase
                FirebaseAuth.getInstance().signOut()

                // Navigate to the login activity
                val intent = Intent(this@MainActivity, Inicio::class.java)
                Toast.makeText(applicationContext,"Has cerrado sesión", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                return true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
