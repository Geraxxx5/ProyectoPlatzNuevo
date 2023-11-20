package com.example.proyectoplatzapplication.ui.Contactos

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Contacto(val nombre: String, val imagenResId: Int, val numero:String, val sede1:String, val ubicacion1:String, val sede2: String, val ubicacion2:String, val sede3:String, val ubicacion3:String) : Parcelable
