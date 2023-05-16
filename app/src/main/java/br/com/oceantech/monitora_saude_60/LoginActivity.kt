package br.com.oceantech.monitora_saude_60

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.oceantech.monitora_saude_60.databinding.ActivityLoginBinding
import br.com.oceantech.monitora_saude_60.viewModel.UserViewModel
import com.google.android.material.appbar.MaterialToolbar
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura a Toolbar
        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // use the factory to create a UserViewModel instance
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding.loginButton.setOnClickListener {
            val login = binding.nomeLoginInputEditText.text.toString().trim()
            val password = binding.senhaInputEditText.text.toString().trim()

            if (login.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch {
                        val user = userViewModel.getUserByLoginAndPassword(login, password)
                        if (user != null) {
                            // usuário encontrado
                            Toast.makeText(this@LoginActivity, "Bem-vindo, ${user.name}", Toast.LENGTH_SHORT).show()
                        } else {
                            // usuário não encontrado
                            Toast.makeText(this@LoginActivity, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
                        }
                    }

                }
            }
        }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Tratar evento de clique no botão de voltar
                onBackPressedDispatcher.onBackPressed()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}
