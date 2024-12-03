package com.delivery_clientes.utils;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper {

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = "example_channel";
            String channelName = "Example Channel";
            String channelDescription = "Canal para notificaciones locales";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;

            NotificationChannel channel = new NotificationChannel(channelId, channelName, importance);
            channel.setDescription(channelDescription);

            // Registrar el canal en el sistema
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    public static void sendLocalNotification(Context context, String mensaje) {
        String channelId = "example_channel"; // ID del canal configurado previamente

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(android.R.drawable.ic_dialog_info) // Icono de la notificación
                .setContentTitle("Notificación local")
                .setContentText(mensaje)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT); // Prioridad de la notificación

        // Mostrar la notificación
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.notify(1, builder.build()); // El ID 1 identifica esta notificación
        }
    }
}

