package br.com.oceantech.monitora_saude_60

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.oceantech.monitora_saude_60.databinding.ActivityRegisterBinding
import br.com.oceantech.monitora_saude_60.model.User
import br.com.oceantech.monitora_saude_60.viewModel.UserViewModel
import com.google.android.material.appbar.MaterialToolbar

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura a Toolbar
        val toolbar: MaterialToolbar = binding.toolbar
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Inicializa o ViewModel
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // Define o comportamento do botão de registro
     binding.cadastrarButton.setOnClickListener {
            val name = binding.nomeInputEditText.text.toString().trim()
            val birthday = binding.daNascInputEditText.text.toString().trim()
            val login = binding.loginInputEditText.text.toString().trim()
            val password = binding.senhaInputEditText.text.toString().trim()
            val phone = binding.telInputEditText.text.toString().trim()

            // Verifica se todos os campos foram preenchidos
            if (name.isEmpty() || birthday.isEmpty() || password.isEmpty() || phone.isEmpty() || sex.isEmpty()) {
                Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Verifica se o número de telefone é válido
            if (!isValidPhone(phone)) {
                Toast.makeText(this, "Por favor, insira um número de telefone válido.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Cria um objeto User a partir dos campos preenchidos
            val user = User(
                name = name,
                birthday = birthday,
                login = login,
                password = password,
                phone = phone
            )

            // Insere o novo usuário no banco de dados
            //userViewModel.insert(user)

            // Exibe uma mensagem de sucesso
            Toast.makeText(this, "Usuário criado com sucesso.", Toast.LENGTH_SHORT).show()

            // Redireciona o usuário para a tela de login
            finish()
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

    // Verifica se o número de telefone é válido
    private fun isValidPhone(phone: String): Boolean {
        val regex = Regex("^\\+?[1-9]\\d{1,14}\$")
        return regex.matches(phone)
    }
}
