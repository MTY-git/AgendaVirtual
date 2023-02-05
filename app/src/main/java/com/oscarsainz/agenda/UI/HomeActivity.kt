package com.oscarsainz.agenda.UI

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.oscarsainz.agenda.R
import com.oscarsainz.agenda.model.bd.DbFirestore
import com.oscarsainz.agenda.model.components.AsignaturaDialog
import com.oscarsainz.agenda.model.Asignatura
import com.oscarsainz.agenda.model.Usuario
import kotlinx.coroutines.launch


enum class ProviderType{
    BASIC,
    GOOGLE
}

class HomeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Recuperación de parametros (extras) del loginActivity
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        //Guardado de sesion
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()

        //Guardando usuario en base de datos
        val user = Usuario(email!!,provider!!)
        DbFirestore.añadirUsuario(user)


    }


    private fun logOut() {
        val prefs = getSharedPreferences(getString(R.string.prefs_file) , Context.MODE_PRIVATE).edit()
        prefs.clear()
        prefs.apply()
        FirebaseAuth.getInstance().signOut()
        onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_asignaturas,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.cerrarSesion -> logOut()
        }
        return super.onOptionsItemSelected(item)
    }

}