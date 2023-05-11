package br.com.oceantech.monitora_saude_60

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import br.com.oceantech.monitora_saude_60.databinding.ActivityRegisterBinding
import br.com.oceantech.monitora_saude_60.model.User
import br.com.oceantech.monitora_saude_60.utils.formatDate
import br.com.oceantech.monitora_saude_60.utils.toLocalDateOrNull
import br.com.oceantech.monitora_saude_60.viewModel.UserViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import java.time.DateTimeException
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
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
            val birthday = userViewModel.dataNascimento.value
            val login = binding.loginInputEditText.text.toString().trim()
            val password = binding.senhaInputEditText.text.toString().trim()
            val phone = binding.telInputEditText.text.toString().trim()

            // Verifica se todos os campos foram preenchidos
            if (name.isEmpty() || birthday == null || password.isEmpty() || phone.isEmpty()) {
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


           Log.d("Usuário cadastrado", "user")

         // Exibe o objeto User no Toast
         Toast.makeText(this, user.toString(), Toast.LENGTH_SHORT).show()


         // Exibe uma mensagem de sucesso
            Toast.makeText(this, "Usuário criado com sucesso.", Toast.LENGTH_SHORT).show()

            // Redireciona o usuário para a tela de login
            //finish()
        }

        insereData()
    }

    // Verifica se o número de telefone é válido
    private fun isValidPhone(phone: String): Boolean {
        val regex = Regex("^\\([1-9]{2}\\) (?:[2-8]|9[1-9])[0-9]{3}\\-[0-9]{4}\$")
        return regex.matches(phone)
    }

    private fun insereData(){
        binding.dataNascEditText.editText?.setOnClickListener {
            val today = LocalDate.now()
            val minDate = today.minusYears(90)
            val maxDate = today.plusYears(1)

            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Selecione a data de nascimento")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.from(minDate.toEpochDay()))
                        .build()
                )
                .build()

            datePicker.addOnPositiveButtonClickListener { timestamp ->
                try {
                    val dataNascimento = Instant.ofEpochMilli(timestamp).atZone(ZoneOffset.UTC).toLocalDate()
                    userViewModel.onDataNascSelecionada(dataNascimento)

                    validarDataNascimento()
                    binding.dataNascEditText.editText?.setText(dataNascimento.formatDate())
                } catch (e: DateTimeException) {
                    Toast.makeText(this, "Data inválida", Toast.LENGTH_LONG).show()
                }
            }

            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG")
        }
    }

    private fun validarDataNascimento(): LocalDate?  {
        val dataNascStr = binding.dataNascEditText.editText?.text?.toString()

        // Converte a string em um objeto LocalDate usando a função de extensão
        val dataNascimento = dataNascStr?.toLocalDateOrNull()

        // Verifica se a data inicial foi preenchida corretamente
        if (dataNascimento == null) {
            // Exibe uma mensagem de erro ao usuário
            Toast.makeText(this, "Preencha a data de nascimento", Toast.LENGTH_LONG).show()
            return null
        }

        // Calcula a diferença em anos entre a data atual e a data de nascimento
        val diffYears = ChronoUnit.YEARS.between(dataNascimento, LocalDate.now())

        // Verifica se a idade é maior ou igual a 18 anos
        if (diffYears < 18) {
            // Exibe uma mensagem de erro ao usuário
            Toast.makeText(this, "A idade deve ser maior ou igual a 18 anos", Toast.LENGTH_LONG).show()
            return null
        }

        return dataNascimento
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
