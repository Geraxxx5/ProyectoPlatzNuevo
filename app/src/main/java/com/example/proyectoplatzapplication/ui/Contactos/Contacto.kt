package com.example.proyectoplatzapplication.ui.Contactos

import android.os.Parcelable
import com.example.proyectoplatzapplication.R
import kotlinx.parcelize.Parcelize


data class Contacto(val id: Int,val nombre: String, val imagenResId: Int, val numero:String, val sede1:String, val ubicacion1:String, val sede2: String, val ubicacion2:String, val sede3:String, val ubicacion3:String)

val listaDeContactos = listOf(
    Contacto(0,"Banco Azteca", R.drawable.bancoaztecaaa,"+502 2306 8000","Cdad.Guatemala", "7A Avenida 19-28", "Mixco", "6A Calle 4-36","Huehuetenango", "Calz Kaibil Balam" ),
    Contacto(1,"Banco Banrural", R.drawable.bancobanrural,"+502 2339 8888","Cdad.Guatemala", "Mercado La Villa de Guadalupe", "Guatemala", "7A Calle 6-23","Mixco", "11 calle 4-13"),
    Contacto(2,"Banco Industrial", R.drawable.bancobi,"+502 2411 6000","Cdad.Guatemala","Vista Hermosa, Carril Auxiliar","Guatemala", "9A Calle 2-42","Zacapa", "13 calle 5-12"),
    Contacto(3,"Banco G&T", R.drawable.bancog_t,"1718", "Cdad.Guatemala", "20 Cakke, 25-85 Zona 10 C.C La Pradera","Guatemala", "4A Calle 7-36","Antigua Guatemala", "12 calle 13 -55")
)

