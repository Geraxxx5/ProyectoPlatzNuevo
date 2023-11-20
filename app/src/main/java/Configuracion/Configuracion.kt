package Configuracion


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.R
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectoplatzapplication.ui.theme.ProyectoPlatzApplicationTheme
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import java.net.Inet4Address
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


data class MyMessages(val id: Int, val title: String, val body: String, val address: String)

private val message: List<MyMessages> = listOf(
    MyMessages(1, "Battery Health", "","Bateria Maxima = 74%\", \"Con Este Porcentaje de Bateria de la Applicación, rendira a toda su capacidad.\", \"El Minimo de bateria para que esta aplicación funcione en su maxima capacidad es de 22%, si no esta podria representar errores para el usuario.\""),
    MyMessages(2, "lenguaje & Region", "","Actualmente Tú Aplicación se encuentra en 'Español.'\", \"\", \"Tú ubicación actual es en Guatemala.\","),
    MyMessages(3, "Información Bancaria", "","Actualmente Tienes Inscrito a Dos diferentes cuentas bancarias.\",\"Para más información contacta a tus bancos respectivos.\","),
    MyMessages(4, "Tutorial", "","- Recordatorios: apunta tus pendientes en el espacio de recordatorios especificando cuando deseas ser notificado y nosotros lo haremos\",\"- Calculadora: Ingresa al espacio de calculadora y realiza tus cuentas sin necesidad de salir de la app. \",\"- Ahorros: Ingresa tu plan de ahorros actual para así brindararte información del mismo. Si deseas cambiar o modificar has click en la opción que desees.\", \"- Registro de gastos: Registra de manera específica tu gastos para llevar un control de tu cuenta.\",\"- Reporte de gastos: Con la misma información que obtengamos de tu registro de gastos te estaremos brindando un análisis estadístico semanal para que puedas ver el movimiento de tu cuenta bancaria.\", \"- Notificaciones: Recibe alertas, notificaciones y promociones de parte del banco."),
    MyMessages(5, "Version", "","Actualmente Cuentas con la utima actualización de esta aplicación.\",\"La cual es la 1.0\",\"Para proximás actualizaciones porfavor consulte el PlayStore \","),
    MyMessages(6, "Terminos Y Condiciones\n", ""
        ,"Fecha de vigencia: 20 de Noviembre de 2024\nBienvenido a nuestra aplicación de finanzas. Antes de comenzar a utilizar nuestros servicios, te pedimos que leas detenidamente estos Términos y Condiciones. Al acceder o utilizar nuestra aplicación, aceptas estar sujeto a los términos descritos a continuación. Si no estás de acuerdo con estos términos, te recomendamos que no utilices nuestra aplicación.\",\"1. Uso Adecuado:\n" +
                "a. La aplicación está diseñada para uso personal y no comercial. No puedes utilizarla con fines ilegales o no autorizados.\n" +
                "b. Te comprometes a proporcionar información precisa y completa al registrarte en la aplicación.\n" +
                "\n\",\"2. Privacidad:\n" +
                "a. Respetamos tu privacidad. Consulta nuestra Política de Privacidad para entender cómo recopilamos, usamos y protegemos tu información personal.\n" +
                "b. Al utilizar la aplicación, aceptas las prácticas de privacidad descritas en nuestra Política de Privacidad.\", \"\n" +
                "Términos y Condiciones de Uso para la Aplicación de Finanzas\n" +
                "\n" +
                "Fecha de vigencia: [Fecha de entrada en vigencia]\n" +
                "\n" +
                "Bienvenido a nuestra aplicación de finanzas. Antes de comenzar a utilizar nuestros servicios, te pedimos que leas detenidamente estos Términos y Condiciones. Al acceder o utilizar nuestra aplicación, aceptas estar sujeto a los términos descritos a continuación. Si no estás de acuerdo con estos términos, te recomendamos que no utilices nuestra aplicación.\n" +
                "\n" +
                "1. Uso Adecuado:\n" +
                "a. La aplicación está diseñada para uso personal y no comercial. No puedes utilizarla con fines ilegales o no autorizados.\n" +
                "b. Te comprometes a proporcionar información precisa y completa al registrarte en la aplicación.\n" +
                "\n" +
                "2. Privacidad:\n" +
                "a. Respetamos tu privacidad. Consulta nuestra Política de Privacidad para entender cómo recopilamos, usamos y protegemos tu información personal.\n" +
                "b. Al utilizar la aplicación, aceptas las prácticas de privacidad descritas en nuestra Política de Privacidad.\n" +
                "\n" +
                "3. Seguridad:\n" +
                "a. Eres responsable de mantener la confidencialidad de tu información de inicio de sesión y de cualquier actividad que ocurra en tu cuenta.\n" +
                "b. Debes notificarnos de inmediato si sospechas de cualquier acceso no autorizado a tu cuenta.\",\"Ley Aplicable:\n" +
                "a. Estos términos están sujetos a las leyes vigentes en [jurisdicción]. Cualquier disputa estará sujeta a la jurisdicción exclusiva de los tribunales en [ciudad, país].\n" +
                "\n" +
                "4.Al utilizar nuestra aplicación, reconoces que has leído, comprendido y aceptado estos Términos y Condiciones. Si tienes alguna pregunta, por favor contáctanos en [correo electrónico de soporte].\", \"¡Gracias por utilizar nuestra aplicación de finanzas!"),
    MyMessages(7, "Sobre Nosotros", "","Somos una Aplicación nueva la cual buca ayudarte a poder tener un manejo financiero, más efectivo, de igual formas busamos que tus cuentas sean ordeandas y optimizadas para que puedas tener una vida más tranquila cuando de dinero se habla.\","),
    MyMessages(8, "Contactanos", "","Puedes contactarnos a nuestro correo: dudasfinance@gmail.com\",\"Contactanos a nuestro número 2240-1523\\\",\" Vistinaos en sucursal, 20 calle 13-55, zona 10, calles las aves. \", \"\",\"¡Cualquier duda Estamos para servirte!\", \"")
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoPlatzApplicationTheme {
                val navController = rememberNavController()
                NavHost(navController, startDestination = "contact_list") {
                    composable("contact_list") {
                        MyMessages(navController, message)
                    }
                    composable("contact_detail/{id}") { backStackEntry ->
                        val contactId = backStackEntry.arguments?.getInt("id")
                        if (contactId != null) {
                            val contact = message.find { it.id == contactId }
                            if (contact != null) {
                                ContactDetail(contact = contact)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MyComponent(messages: MyMessages, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        //MyImage()
        MyTexts(messages)
    }
}


@Composable
fun MyTexts(messages: MyMessages) {
    Column(modifier = Modifier.padding(start = 8.dp)) {
        MyText(messages.title, MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.size(4.dp))
        MyText(messages.body, MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
fun MyText(text: String, color: Color) {
    Text(text, color = color)
}

@Composable
fun MyMessagesItem(navController: NavController, message: MyMessages) {
    MyComponent(message) {
        navController.navigate("contact_detail/${message.id}")
    }
}

@Composable
fun MyMessages(navController: NavController, messages: List<MyMessages>) {
    LazyColumn {
        items(messages) { message ->
            MyMessagesItem(navController, message)
        }
    }
}

@Composable
fun ContactDetail(contact: MyMessages) {
    Column {
        Text(text = "Nombre: ${contact.title}")
        Text(text = "Teléfono: ${contact.body}")
        Text(text = "Dirección: ${contact.address}")

    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewComponent(){
    ProyectoPlatzApplicationTheme {
        MyMessages(rememberNavController(), message)
    }
}
