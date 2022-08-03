package com.dicoding.courseschedule.notification

import android.annotation.SuppressLint
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.data.Course
import com.dicoding.courseschedule.data.DataRepository
import com.dicoding.courseschedule.ui.home.HomeActivity
import com.dicoding.courseschedule.util.*
import java.util.*

class DailyReminder : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        executeThread {
            val repository = DataRepository.getInstance(context)
            val courses = repository?.getTodaySchedule()

            courses?.let {
                if (it.isNotEmpty()) showNotification(context, it)
            }
        }
    }

    //TODO 12 : Implement daily reminder for every 06.00 a.m using AlarmManager
    @SuppressLint("UnspecifiedImmutableFlag")
    fun setDailyReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context,DailyReminder::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY,6)
        calendar.set(Calendar.MINUTE,0)
        calendar.set(Calendar.SECOND,0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING,intent,0)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.timeInMillis,AlarmManager.INTERVAL_DAY,pendingIntent)

        Toast.makeText(context, "Alarm 06:00",Toast.LENGTH_LONG).show()
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminder::class.java)

        val  pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING,intent,0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)

        Toast.makeText(context,"cancel alarm",Toast.LENGTH_LONG).show()
    }

    private fun showNotification(context: Context, content: List<Course>) {
        //TODO 13 : Show today schedules in inbox style notification & open HomeActivity when notification tapped
        val notificationStyle = NotificationCompat.InboxStyle()
        val timeString = context.resources.getString(R.string.notification_message_format)
        content.forEach {
            val courseData = String.format(timeString, it.startTime, it.endTime, it.courseName)
            notificationStyle.addLine(courseData)
        }
        val channelId = NOTIFICATION_CHANNEL_ID
        val channelName = NOTIFICATION_CHANNEL_NAME
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val intent = Intent(context,HomeActivity::class.java)
        val pendingIntent = TaskStackBuilder.create(context)
            .addParentStack(HomeActivity::class.java)
            .addNextIntent(intent)
            .getPendingIntent(ID_REPEATING, PendingIntent.FLAG_CANCEL_CURRENT)
        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, channelId)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications)
            .setStyle(notificationStyle)
            .setContentTitle(context.resources.getString(R.string.today_schedule))
            .setContentText(context.resources.getString(R.string.notification_message_format))
            .setSound(alarmSound)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(NOTIFICATION_ID, notification)
    }

}