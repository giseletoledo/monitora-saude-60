package br.com.oceantech.monitora_saude_60

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.com.oceantech.monitora_saude_60.databinding.ActivityConfiguracaoBinding
import com.google.android.material.appbar.MaterialToolbar

class ConfiguracaoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityConfiguracaoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityConfiguracaoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura a Toolbar
        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnResponsavel.setOnClickListener {
            val intent = Intent(this, ResponsavelActivity::class.java)
            startActivity(intent)
        }
    }
}