package br.com.oceantech.monitora_saude_60

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.AlarmClock
import android.widget.Button

class AlarmActivity : AppCompatActivity() {
    lateinit var alarmManager: AlarmManager
    lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val alarmButton = findViewById<Button>(R.id.alarm_button)
        alarmButton.setOnClickListener {
            setAlarm()
        }
    }

    private fun setAlarm() {
        val intent = Intent(AlarmClock.ACTION_SET_ALARM).apply {
            putExtra(AlarmClock.EXTRA_MESSAGE, "Hora de tomar o remédio")
            putExtra(AlarmClock.EXTRA_HOUR, 10)
            putExtra(AlarmClock.EXTRA_MINUTES, 0)
            putExtra(AlarmClock.EXTRA_SKIP_UI, true)
        }
//        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
//        }
    }


    /*  private fun setAlarm() {
          val intent = Intent(this, AlarmReceiver::class.java)
          pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

          // Definir o alarme para 5 segundos a partir de agora
          val triggerAtMillis = System.currentTimeMillis() + 10000

          alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
      }*/
}
