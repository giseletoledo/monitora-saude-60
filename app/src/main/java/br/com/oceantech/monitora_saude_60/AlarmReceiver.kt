package br.com.oceantech.monitora_saude_60

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, "Alarme disparado!", Toast.LENGTH_SHORT).show()
    }
}
