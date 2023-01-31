package com.oscarsainz.agenda

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.google.firebase.auth.FirebaseAuth
import com.oscarsainz.agenda.databinding.ActivityAsignaturasBinding
import com.oscarsainz.agenda.databinding.ActivityLoginBinding

private lateinit var binding: ActivityAsignaturasBinding

enum class ProviderType{
    BASIC,
    GOOGLE
}

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsignaturasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Recuperación de parametros (extras) del loginActivity
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        //Guardado de sesion
        val prefs = getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE).edit()
        prefs.putString("email",email)
        prefs.putString("provider",provider)
        prefs.apply()


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