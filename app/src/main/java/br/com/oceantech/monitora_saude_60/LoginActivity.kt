package br.com.oceantech.monitora_saude_60

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.oceantech.monitora_saude_60.databinding.ActivityLoginBinding
import br.com.oceantech.monitora_saude_60.viewModel.UserViewModel
import com.google.android.material.appbar.MaterialToolbar

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
                if (login == "admin" && password == "1234"){
                    Toast.makeText(this, "Bem-vindo, ${login}", Toast.LENGTH_SHORT).show()
                } else{
                    Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
                }

              /*  userViewModel.getUserByLoginAndPassword(login, password)?.let { user ->
                    // usuário encontrado
                    Toast.makeText(this, "Bem-vindo, ${user.name}", Toast.LENGTH_SHORT).show()
                } ?: run {
                    // usuário não encontrado
                    Toast.makeText(this, "Usuário ou senha incorretos", Toast.LENGTH_SHORT).show()
                }*/
            }
        }

        /*binding.loginButton.setOnClickListener {
            startActivity(Intent(this, ListaMedicamentoActivity::class.java))
        }*/
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
