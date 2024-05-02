package com.example.mywidgets

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class RandomNumberWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (intent?.action == WIDGET_CLICK){
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val widgetText = "Random : " + NumberGenerator.generate(100)
            val appWidgetId = intent.getIntExtra(WIDGET_ID_EXTRA, 0)
            // Construct the RemoteViews object
            val views = RemoteViews(context?.packageName, R.layout.random_number_widget)
            views.setTextViewText(R.id.appwidget_text, widgetText)

            // Instruct the widget manager to update the widget
            appWidgetManager.updateAppWidget(appWidgetId, views)

        }
    }

    companion object {
        const val WIDGET_CLICK = "android.appwidget.action.APPWIDGET_UPDATE"
        const val WIDGET_ID_EXTRA = "widget_id_extra"
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int,
) {
    //konfigurasi aksi ketika button di klik
    val intent = Intent(context, RandomNumberWidget::class.java)
    intent.action = RandomNumberWidget.WIDGET_CLICK
    intent.putExtra(RandomNumberWidget.WIDGET_ID_EXTRA, appWidgetId)

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        appWidgetId,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
    )

    val widgetText = "Random : " + NumberGenerator.generate(100)
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.random_number_widget)
    views.setTextViewText(R.id.appwidget_text, widgetText)

    views.setOnClickPendingIntent(R.id.btn_click,pendingIntent) // implementasi ke button

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}