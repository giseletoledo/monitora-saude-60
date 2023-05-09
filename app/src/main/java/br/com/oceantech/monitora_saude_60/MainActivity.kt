package br.com.oceantech.monitora_saude_60

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import br.com.oceantech.monitora_saude_60.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura a Toolbar
        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.buttonMenuMedicamentos.setOnClickListener {
            val intent = Intent(this, ListaMedicamentoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Tratar evento de clique no botão de voltar
                onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}