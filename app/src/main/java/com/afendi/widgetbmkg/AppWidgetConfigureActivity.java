package com.afendi.widgetbmkg;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class AppWidgetConfigureActivity extends Activity {

    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    EditText dd1,ed2;
    private static final String PREFS_NAME = "com.example.yoko.wisataparsing.WidgetWisata";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    TextView dd2,dd3,dd4,ee2,ee3,ee4,ee5,warn,etang;
    String awr="Peringatan Cuaca";

    private HandleXMLWisata obj;
    private HandleXML bjw ;
    Button b1;
    ImageView aa,ab;
    Spinner sw,sk;

    public AppWidgetConfigureActivity() {
        super();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        setContentView(R.layout.app_widget_configure);

        dd2 = (TextView)findViewById(R.id.hasilwisata);
        dd3 = (TextView)findViewById(R.id.hasilcuaca);
        dd4 = (TextView)findViewById(R.id.suhuUdra);
        sw = (Spinner)findViewById(R.id.parsewisata);
        sk = (Spinner)findViewById(R.id.parsekota);
        ee2 = (TextView)findViewById(R.id.hasilkota);
        ee3 = (TextView)findViewById(R.id.hasilcuacakota);
        ee4 = (TextView)findViewById(R.id.suhuUdrakota);
        ee5 = (TextView)findViewById(R.id.suhuMaxkota);
        aa = (ImageView)findViewById(R.id.imageCuacaWisata);
        ab = (ImageView)findViewById(R.id.imageCuacaKota);
        warn = (TextView)findViewById(R.id.warningconfig);
        etang = (TextView)findViewById(R.id.tglwidget);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);
        b1=(Button)findViewById(R.id.buttonparse);

        //untuk membuat list kota, atau bisa menggunaan String[]
        List<String> item = new ArrayList<String>();
        item.add("Pilih Wisata");
        item.add("Kuta");
        item.add("Ubud");
        item.add("Tanah Lot");
        item.add("Nusa Dua");
        item.add("Sanur");
        item.add("Bedugul");
        item.add("Kintamani");
        item.add("Besakih");
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(AppWidgetConfigureActivity.this,android.R.layout.simple_spinner_dropdown_item,item);
        //untuk menentukan model adapter nya
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //menerapkan adapter pada spinner sp
        sw.setAdapter(adapter);

        //untuk membuat list kota, atau bisa menggunaan String[]
        List<String> itemkota = new ArrayList<String>();
        itemkota.add("Pilih Kota");
        itemkota.add("Negara");
        itemkota.add("Tabanan");
        itemkota.add("Mangupura");
        itemkota.add("Gianyar");
        itemkota.add("Semarapura");
        itemkota.add("Bangli");
        itemkota.add("Amlapura");
        itemkota.add("Singaraja");
        itemkota.add("Denpasar");
        ArrayAdapter<String> adapterkota =
                new ArrayAdapter<String>(AppWidgetConfigureActivity.this,android.R.layout.simple_spinner_dropdown_item,itemkota);
        //untuk menentukan model adapter nya
        adapterkota.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //menerapkan adapter pada spinner sp
        sk.setAdapter(adapterkota);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlkota = sk.getSelectedItem().toString();
                String finalUrlKota = "http://balai3.denpasar.bmkg.go.id/bbmkg3_xml_files/propinsi_17_2.xml";
                ee2.setText(finalUrlKota);

                String url = sw.getSelectedItem().toString();
                String finalUrl = "http://balai3.denpasar.bmkg.go.id/bbmkg3_xml_files/cuaca_wisata.xml";
                dd2.setText(finalUrl);

                String urlwarning = "http://balai3.denpasar.bmkg.go.id/bbmkg3_xml_files/cuaca_harian_id.xml";




                obj = new HandleXMLWisata(finalUrl, url);
                obj.fetchXML();

                while (obj.parsingComplete);
                dd2.setText(obj.getArea());
                if(obj.getTemperature().equals("")){
                    dd4.setText("");
                }else {
                    dd4.setText(obj.getTemperature() + " 'C");
                }
                aa.setImageResource(obj.getGambarWisata());
                if(obj.getWeather().equals("Generally Cloudy")){
                    dd3.setText("Berawan");
                }else if(obj.getWeather().equals("Sunny")){
                    dd3.setText("Cerah");
                }else if(obj.getWeather().equals("Generally Sunny to Cloudy")){
                    dd3.setText("Cerah Berawan");
                }else if(obj.getWeather().equals("Possibility of Heavy Rain")){
                    dd3.setText("Hujan Lebat");
                }else if(obj.getWeather().equals("Possibility of Light Rain")){
                    dd3.setText("Hujan Ringan");
                }else if(obj.getWeather().equals("Possibility of Moderate Rain")){
                    dd3.setText("Hujan Sedang");
                }

                bjw = new HandleXML(finalUrlKota, urlkota);
                bjw.fetchXML();

                while(bjw.parsingComplete);
                ee2.setText(bjw.getCountry());
                ee3.setText(bjw.getCuaca());
                if(bjw.getSuhuMin().equals("")&&bjw.getSuhuMax().equals("")){
                    ee4.setText("");
                    ee5.setText("");
                }else{
                    ee4.setText(bjw.getSuhuMin()+" - ");
                    ee5.setText(bjw.getSuhuMax()+" 'C");
                }

                etang.setText(bjw.getTanggalMulai());
                ab.setImageResource(bjw.getGambar());

                HandleXMLWarning war = new HandleXMLWarning(urlwarning);
                war.fetchXML();
                while (war.parsingComplete);
//
                if(war.getWarning().equals("")){
                    warn.setText("Tidak Ada Peringatan");
                }else{
//                    Intent inten = new Intent(this, MainActivity.)
                    NotificationManager notif = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notify = new Notification(R.drawable.logoweb, awr, System.currentTimeMillis());

                    PendingIntent pending = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);
                    notify.defaults |= notify.DEFAULT_ALL;
                    notify.flags |= notify.FLAG_AUTO_CANCEL;
                    String subject ="Peringatan Cuaca Dari BMKG ";
                    String body = (war.getWarning());
//                    notify.setLatestEventInfo(getApplicationContext(), subject, body, pending);
                    notif.notify(0, notify);
                    warn.setText(war.getWarning());

                }

            }
        });

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        dd2.setText(loadTitlePref(AppWidgetConfigureActivity.this, mAppWidgetId));
    }

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = AppWidgetConfigureActivity.this;
//            final Context context1 = WidgetWisataConfigureActivity.this;

            // When the button is clicked, store the string locally
            String widgetwisata = dd2.getText().toString();
            String widgetWeather = dd3.getText().toString();
            String widgetTemp = dd4.getText().toString();
            String widgetkota = ee2.getText().toString();
            String widgetcuaca = ee3.getText().toString();
            String widgetsuhu = ee4.getText().toString();
            String widgetsuhumax = ee5.getText().toString();
            String widgetwarn = warn.getText().toString();
            String widgettang = etang.getText().toString();
            saveTitlePref(context, mAppWidgetId, widgetwisata, "wisata");
            saveTitlePref(context, mAppWidgetId, widgetWeather, "weather");
            saveTitlePref(context, mAppWidgetId, widgetTemp, "temp");
            saveTitlePref(context, mAppWidgetId, widgetwarn, "warnin");

            saveTitlePref(context, mAppWidgetId, widgetkota, "kota");
            saveTitlePref(context, mAppWidgetId, widgetcuaca, "cuaca");
            saveTitlePref(context, mAppWidgetId, widgetsuhu, "suhu");
            saveTitlePref(context, mAppWidgetId, widgetsuhumax, "suhumax");
            saveTitlePref(context, mAppWidgetId, widgettang, "tanggal");

              // It is the responsibility of the configuration activity to update the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            AppWidget.updateAppWidget(context, appWidgetManager, mAppWidgetId);
//
            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
//
//
        }
    };


    // Write the prefix to the SharedPreferences object for this widget
    static void saveTitlePref(Context context, int appWidgetId, String text,String data) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putString(PREF_PREFIX_KEY + appWidgetId+data, text);
        prefs.commit();
    }
    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static String loadTitlePref(Context context, int appWidgetId) {
        return loadTitlePref(context,appWidgetId,"");
    }
    static String loadTitlePref(Context context, int appWidgetId,String data) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        String titleValue = prefs.getString(PREF_PREFIX_KEY + appWidgetId + data, null);
        if (titleValue != null) {
            return titleValue;
        } else {
            return "";
        }
    }
    static void deleteTitlePref(Context context, int appWidgetId) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId);
        prefs.commit();
    }
}



