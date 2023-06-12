package br.com.oceantech.monitora_saude_60

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat

class LembreteReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // Verificar se a ação recebida é a ação personalizada "LEMBRETE_ACTION"
        if (intent?.action == "LEMBRETE_ACTION") {
            // Obter a mensagem do intent
            val mensagem = intent.getStringExtra("mensagem")

            // Obter o ID de notificação do intent
            val notificationId = intent.getIntExtra("notificationId", 0) ?: 0

            // Exibir a notificação
            if (mensagem != null) {
                exibirNotificacao(context, mensagem, notificationId)
            }
        }
    }

    private fun exibirNotificacao(context: Context?, mensagem: String, notificationId: Int) {
        // Configurar o canal de notificação (necessário para o Android 8.0 e superior)
        val channelId = "lembrete_channel_id"
        val channelName = "Lembrete Channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            val notificationManager = context?.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }

        val smallIcon = R.drawable.ic_notification // Substitua "ic_notification" pelo ID do seu ícone

        // Criar a notificação
        val notification = NotificationCompat.Builder(context!!, channelId)
            .setSmallIcon(smallIcon)
            .setContentTitle("Lembrete")
            .setContentText(mensagem)
            .setAutoCancel(true)
            .build()

        // Exibir a notificação usando o ID de notificação exclusivo
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(notificationId, notification)
    }
}