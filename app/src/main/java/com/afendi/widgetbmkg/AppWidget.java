package com.afendi.widgetbmkg;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 * App Widget Configuration implemented in {@link AppWidgetConfigureActivity AppWidgetConfigureActivity}
 */
public class AppWidget extends AppWidgetProvider {

    private static int gmb,gmw;
    ImageView bew;
    public static String ACTION_WIDGET_CONFIGURE = "ConfigureWidget";

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        // When the user deletes the widget, delete the preference associated with it.
        final int N = appWidgetIds.length;
        for (int i = 0; i < N; i++) {
            AppWidgetConfigureActivity.deleteTitlePref(context, appWidgetIds[i]);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetCuaca = AppWidgetConfigureActivity.loadTitlePref(context, appWidgetId, "weather");
        CharSequence widgetSuhu = AppWidgetConfigureActivity.loadTitlePref(context, appWidgetId, "temp");
        CharSequence widgetwisata = AppWidgetConfigureActivity.loadTitlePref(context, appWidgetId, "wisata");

        CharSequence widgettanggal = AppWidgetConfigureActivity.loadTitlePref(context, appWidgetId, "tanggal");
        CharSequence widgetperingatan = AppWidgetConfigureActivity.loadTitlePref(context,appWidgetId, "warnin");
        CharSequence widgetCuacakota = AppWidgetConfigureActivity.loadTitlePref(context, appWidgetId, "cuaca");
        CharSequence widgetSuhukota = AppWidgetConfigureActivity.loadTitlePref(context, appWidgetId, "suhu");
        CharSequence widgetSuhuMax = AppWidgetConfigureActivity.loadTitlePref(context,appWidgetId, "suhumax");
        CharSequence widgetkota = AppWidgetConfigureActivity.loadTitlePref(context, appWidgetId, "kota");
        // Construct the RemoteViews object

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget);
        if(widgetCuaca.equals("Berawan")){
            gmw=R.drawable.bberawan;
            views.setImageViewResource(R.id.imagewisata, gmw);
        }else if(widgetCuaca.equals("Cerah")){
            gmw=R.drawable.ccerah;
            views.setImageViewResource(R.id.imagewisata, gmw);
        }else if(widgetCuaca.equals("Cerah Berawan")){
            gmw=R.drawable.ccerahberawan;
            views.setImageViewResource(R.id.imagewisata, gmw);
        }else if(widgetCuaca.equals("Hujan Lebat")){
            gmw=R.drawable.hhujanlebat;
            views.setImageViewResource(R.id.imagewisata, gmw);
        }else if(widgetCuaca.equals("Hujan Ringan")){
            gmw=R.drawable.hhujanringan;
            views.setImageViewResource(R.id.imagewisata, gmw);
        }else if(widgetCuaca.equals("Hujan Sedang")){
            gmw=R.drawable.hhujansedang;
            views.setImageViewResource(R.id.imagewisata, gmw);
        }else{
            views.setImageViewResource(R.id.imagewisata, R.drawable.cuacanullnull);
        }
        views.setTextViewText(R.id.wisatawidget, widgetwisata);
        views.setTextViewText(R.id.cuacawisataWidget, widgetCuaca);
        views.setTextViewText(R.id.suhuwisataWidget, widgetSuhu);

        views.setTextViewText(R.id.kotawidget, widgetkota);
        if(widgetCuacakota.equals("Berawan")){
            gmb=R.drawable.bberawan;
            views.setImageViewResource(R.id.imagekota, gmb);
        }else if(widgetCuacakota.equals("Cerah")){
            gmb=R.drawable.ccerah;
            views.setImageViewResource(R.id.imagekota, gmb);
        }else if(widgetCuacakota.equals("Cerah Berawan")){
            gmb=R.drawable.ccerahberawan;
            views.setImageViewResource(R.id.imagekota, gmb);
        }else if(widgetCuacakota.equals("Hujan Lebat")){
            gmb=R.drawable.hhujanlebat;
            views.setImageViewResource(R.id.imagekota, gmb);
        }else if(widgetCuacakota.equals("Hujan Ringan")){
            gmb=R.drawable.hhujanringan;
            views.setImageViewResource(R.id.imagekota, gmb);
        }else if(widgetCuacakota.equals("Hujan Sedang")){
            gmb=R.drawable.hhujansedang;
            views.setImageViewResource(R.id.imagekota, gmb);
        }else{
            views.setImageViewResource(R.id.imagekota, R.drawable.cuacanullnull);
        }
        views.setTextViewText(R.id.cuacakotaWidget, widgetCuacakota);
        views.setTextViewText(R.id.suhukotaWidget, widgetSuhukota);
        views.setTextViewText(R.id.suhumaxkotaWidget, widgetSuhuMax);
        views.setTextViewText(R.id.tanggalwidget, widgettanggal);
        views.setTextViewText(R.id.warningwidget, widgetperingatan);


        Intent intent = new Intent(context,AppWidgetConfigureActivity.class);
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
        PendingIntent pending = PendingIntent.getActivity(context, appWidgetId, intent, 0);
        views.setOnClickPendingIntent(R.id.buttonPindahWisata , pending);
        intent.setAction(ACTION_WIDGET_CONFIGURE + Integer.toString(appWidgetId));

        Intent intent2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://balai3.denpasar.bmkg.go.id"));
        PendingIntent pendin2 = PendingIntent.getActivity(context, 1, intent2, 0);

        views.setOnClickPendingIntent(R.id.buttonPindahWeb, pendin2);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}


