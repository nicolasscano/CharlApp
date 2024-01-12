package com.canodevs.charlapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.canodevs.charlapp.Fragmentos.FragmentoUsuarios
import com.canodevs.charlapp.Modelo.Usuario
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue

class MainActivity : AppCompatActivity() {

    var reference: DatabaseReference?= null
    var firebaseUser: FirebaseUser?= null
    private lateinit var nombre_usuario: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicializarComponentes()
        ObtenerDato()
    }

    fun inicializarComponentes() {
        val appBarLayout: AppBarLayout = findViewById(R.id.toolbarMain)
        val toolbar: Toolbar = appBarLayout.findViewById(R.id.toolbarInsideAppBar)

        if (supportActionBar == null) {
            setSupportActionBar(toolbar)
        }

        supportActionBar!!.title = ""


        firebaseUser = FirebaseAuth.getInstance().currentUser
        reference = FirebaseDatabase.getInstance().reference.child("Usuarios").child(firebaseUser!!.uid)
        nombre_usuario = findViewById(R.id.Nombre_usuario)

        val tabLayout: TabLayout = findViewById(R.id.TabLayoutMain)
        val viewPager: ViewPager = findViewById(R.id.ViewPagerMain)

        val viewpagerAdapter = ViewPagerAdapter(supportFragmentManager)

        viewpagerAdapter.addItem(FragmentoUsuarios(), "Usuarios")
        viewpagerAdapter.addItem(FragmentoUsuarios(), "Chats")

        viewPager.adapter = viewpagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }


    fun ObtenerDato() {
        reference!!.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val usuario: Usuario?= snapshot.getValue(Usuario::class.java)
                    nombre_usuario.text = usuario!!.getN_Usuario()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager): FragmentPagerAdapter(fragmentManager) {

        private val listaFragmentos : MutableList<Fragment> = ArrayList()
        private val listaTitulos : MutableList<String> = ArrayList()


        override fun getCount(): Int {
            return listaFragmentos.size
        }

        override fun getItem(position: Int): Fragment {
            return listaFragmentos[position]
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return listaTitulos[position]
        }

        fun addItem(fragment: Fragment, titulo: String) {
            listaFragmentos.add(fragment)
            listaTitulos.add(titulo)
        }
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
