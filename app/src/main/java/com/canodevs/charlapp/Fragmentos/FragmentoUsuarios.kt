package com.canodevs.charlapp.Fragmentos

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.canodevs.charlapp.Adaptador.AdapadorUsuario
import com.canodevs.charlapp.Modelo.Usuario
import com.canodevs.charlapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.getValue


class FragmentoUsuarios : Fragment() {

    private var usuarioAdaptador: AdapadorUsuario? = null
    private var usuarioLista: List<Usuario>? = null
    private var rvUsuarios: RecyclerView? = null
    private lateinit var Et_buscar_usuario: EditText

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_fragmento_usuarios, container, false)

        rvUsuarios = view.findViewById(R.id.RV_usuarios)
        rvUsuarios!!.setHasFixedSize(true)
        rvUsuarios!!.layoutManager = LinearLayoutManager(context)
        Et_buscar_usuario = view.findViewById(R.id.Et_buscar_usuario)

        usuarioLista = ArrayList()
        ObtenerUsuariosBD()

        Et_buscar_usuario.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(
                b_usuario: CharSequence?,
                start: Int,
                before: Int,
                count: Int
            ) {
                BuscarUsuario(b_usuario.toString().lowercase())
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })

        return view
    }

    private fun ObtenerUsuariosBD() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
        val reference =
            FirebaseDatabase.getInstance().reference.child("Usuarios").orderByChild("n_usuario")
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (usuarioLista as ArrayList<Usuario>).clear()
                if (Et_buscar_usuario.text.toString().isEmpty()) {
                    for (sh in snapshot.children) {
                        val usuario: Usuario? = sh.getValue(Usuario::class.java)
                        if (!(usuario!!.getUid()).equals(firebaseUser)) {
                            (usuarioLista as ArrayList<Usuario>).add(usuario)
                        }
                    }
                    usuarioAdaptador = AdapadorUsuario(context!!, usuarioLista!!)
                    rvUsuarios!!.adapter = usuarioAdaptador
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun BuscarUsuario(buscarUsuario: String) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser!!.uid
        val consulta =
            FirebaseDatabase.getInstance().reference.child("Usuarios").orderByChild("buscar")
                .startAt(buscarUsuario).endAt(buscarUsuario + "\uf8ff")
        consulta.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                (usuarioLista as ArrayList<Usuario>).clear()
                for (sh in snapshot.children) {
                    val usuario: Usuario? = sh.getValue(Usuario::class.java)
                    if (!(usuario!!.getUid()).equals(firebaseUser)) {
                        (usuarioLista as ArrayList<Usuario>).add(usuario)
                    }
                }
                usuarioAdaptador = AdapadorUsuario(context!!, usuarioLista!!)
                rvUsuarios!!.adapter = usuarioAdaptador
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

}