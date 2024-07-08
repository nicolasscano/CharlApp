package com.canodevs.charlapp.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.canodevs.charlapp.Adaptador.AdapadorUsuario
import com.canodevs.charlapp.Modelo.ListaChats
import com.canodevs.charlapp.Modelo.Usuario
import com.canodevs.charlapp.R
import com.google.firebase.database.FirebaseDatabase


class FragmentoChats : Fragment() {

    private var usuarioAdaptador: AdapadorUsuario? = null
    private var usuarioLista: List<Usuario>? = null
    private var usuarioListaChats: List<ListaChats>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_fragmento_chats, container, false)
        return view
    }

    private fun RecuperarListaChats() {
        usuarioLista = ArrayList()
        val reference = FirebaseDatabase.getInstance().reference.child("Usuarios")
    }

}