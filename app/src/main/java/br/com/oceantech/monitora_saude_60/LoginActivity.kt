package br.com.oceantech.monitora_saude_60

import android.content.Intent
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.oceantech.monitora_saude_60.databinding.ActivityLoginBinding
import br.com.oceantech.monitora_saude_60.viewModel.UserViewModel
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
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
            val phone = binding.phoneLoginInputEditText.text.toString().trim()
            val password = binding.senhaInputEditText.text.toString().trim()

            if (phone.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
                } else {
                    lifecycleScope.launch {
                        val user = userViewModel.getUserByPhoneAndPassword(phone, password)
                        if (user != null) {
                            // usuário encontrado
                            showBottomSheetMessage("Bem-vindo, ${user.name}")
                        } else {
                            // usuário não encontrado
                            showBottomSheetMessage("Usuário ou senha incorretos")
                        }
                    }

                }
            }
        }

    private fun showBottomSheetMessage(message: String) {
        val bottomSheetDialog = BottomSheetDialog(this)
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_login_message, null)
        val messageTextView = bottomSheetView.findViewById<TextView>(R.id.messageTextView)
        messageTextView.text = message
        bottomSheetDialog.setContentView(bottomSheetView)

        // Ajuste o deslocamento vertical do Bottom Sheet
        val behavior = BottomSheetBehavior.from(bottomSheetView.parent as View)
        behavior.peekHeight = Resources.getSystem().displayMetrics.heightPixels / 2

        bottomSheetDialog.setOnDismissListener {
            // Bottom Sheet foi fechado, iniciar a MainActivity aqui
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        bottomSheetDialog.show()
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
