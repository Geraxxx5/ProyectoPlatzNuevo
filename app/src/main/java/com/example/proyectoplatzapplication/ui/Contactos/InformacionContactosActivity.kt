package com.example.proyectoplatzapplication.ui.Contactos

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.proyectoplatzapplication.R
import com.example.proyectoplatzapplication.ui.theme.ProyectoPlatzApplicationTheme

var contacto:Contacto? = null
//var Sede:Contacto? = null

class InformacionContactosActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contacto = intent.getParcelableExtra<Contacto>("Contacto")
        setContent {
            ProyectoPlatzApplicationTheme {
                // A surface container using the 'background' color from the theme
                Column() {
                    TopContactInfo()
                    Spacer(modifier = Modifier.width(16.dp))
                    ContentContactInfo()
                }
            }
        }
    }
}

@Composable
fun ContactInformation() {
    Column {
        TopContactInfo()
        Spacer(modifier = Modifier.width(16.dp))
        ContentContactInfo()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopContactInfo(){
    val context = LocalContext.current
    TopAppBar(title = { Text(text = "Informacion") }, colors = topAppBarColors(
        containerColor = Color(0xFFB6FC65)),
        navigationIcon = {
            IconButton(onClick = {
                context.startActivity(Intent(context,Contactos::class.java))
            }) {
                Icon(painter = painterResource(id = R.drawable.ic_arrow_back), contentDescription = "")
            }
        }
    )
}

@Composable
fun ContentContactInfo(){
    Spacer(modifier = Modifier.padding(10.dp))
    Card {
        Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
            Image(
                painter = painterResource(id = contacto!!.imagenResId),
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFE66FB5),
                                Color(0xFF6F85E6)
                            )
                        )
                    ),
                contentScale = ContentScale.Crop
            )
            Text(text = contacto!!.nombre)
            Text(text = contacto!!.numero)
            Row {
                IconButton(onClick = { /*TODO*/ }, Modifier.padding(horizontal = 10.dp)) {
                    Icon(painter = painterResource(id = R.drawable.ic_phone), contentDescription = "")
                }
                IconButton(onClick = { /*TODO*/ }, Modifier.padding(horizontal = 10.dp)) {
                    Icon(painter = painterResource(id = R.drawable.ic_message), contentDescription = "")
                }
            }

        }
    }
    Spacer(modifier = Modifier.padding(10.dp))
    Card {
        Box(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.align(Alignment.CenterStart)) {
                Text(text = "Movil", fontSize = 10.sp)
                Text(text = contacto!!.numero)
            }
            Row(Modifier.align(Alignment.CenterEnd)) {
                IconButton(onClick = { /*TODO*/ }, Modifier.padding(horizontal = 10.dp)) {
                    Icon(painter = painterResource(id = R.drawable.ic_phone), contentDescription = "")
                }
                IconButton(onClick = { /*TODO*/ }, Modifier.padding(horizontal = 10.dp)) {
                    Icon(painter = painterResource(id = R.drawable.ic_message), contentDescription = "")
                }
            }
        }
    }
    Spacer(modifier = Modifier.padding(10.dp))
    Text(text = "Sedes de interes",Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
    Spacer(modifier = Modifier.padding(5.dp))
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth()) {
            Text(text = "Sede", fontSize = 10.sp)
            Text(text = contacto!!. sede1)
            Text(text = "Ubicación", fontSize = 10.sp)
            Text(text = contacto!!.ubicacion1)
        }
    }
    Spacer(modifier = Modifier.padding(1.dp))
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth()) {
            Text(text = "Sede", fontSize = 10.sp)
            Text(text = contacto!!. sede2)
            Text(text = "Ubicación", fontSize = 10.sp)
            Text(text = contacto!!. ubicacion2)
        }
    }
    Spacer(modifier = Modifier.padding(1.dp))
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.fillMaxWidth()) {
            Text(text = "Sede", fontSize = 10.sp)
            Text(text = contacto!!. sede3)
            Text(text = "Ubicación", fontSize = 10.sp)
            Text(text = contacto!!. ubicacion3)
        }
    }



}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ContactInformationPreview() {
    ProyectoPlatzApplicationTheme {
        ContactInformation()
    }
}