package com.afendi.widgetbmkg;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    EditText ed1;
    TextView ed2;
    TextView ed3;
    TextView ed4;
    TextView ed5, ed6, ed7, ed8, ed9,ed11, ed12, ed10,ed16,ed13,ed14,ed15;
    TabHost hos;
    TextView dd2;
    TextView dd3;
    TextView dd4;
    TextView dd5,dd6,warn,dd11,dd22,dd33,dd44;
    Spinner skot, swit;
    private HandleXMLWisata objw;
    Button b2;
    private String awar = "Peringatan Cuaca";
    private String warwis = "";
    private HandleXML obj;
    Button b1;
    ImageView be,bb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b1 = (Button) findViewById(R.id.button);
        be = (ImageView) findViewById(R.id.gambarCuaca);
        skot = (Spinner) findViewById(R.id.kota);//location
        ed2 = (TextView) findViewById(R.id.Kota);//curent
        ed3 = (TextView) findViewById(R.id.SuhuMin);
        ed4 = (TextView) findViewById(R.id.SuhuMax);
        ed5 = (TextView) findViewById(R.id.KelemMin);
        ed6 = (TextView) findViewById(R.id.KelemMax);
        ed7 = (TextView) findViewById(R.id.Kecepatan);//tempt
        ed8 = (TextView) findViewById(R.id.Cuaca);//humadity
        ed9 = (TextView) findViewById(R.id.ArahAngin);//preasure
        ed12=(TextView)findViewById(R.id.Sampai);
        ed14=(TextView)findViewById(R.id.jamsampai);
        ed13 = (TextView)findViewById(R.id.mulai);
        ed15 = (TextView)findViewById(R.id.jammulai);
        dd11 = (TextView)findViewById(R.id.mulaiwisata);
        dd22 = (TextView)findViewById(R.id.jammulaiwisata);
        dd33 = (TextView)findViewById(R.id.Sampaiwisata);
        dd44 = (TextView)findViewById(R.id.jamsampaiwisata);

        warn = (TextView)findViewById(R.id.warningtab);
        ed11 = (TextView) findViewById(R.id.warningtabwisata);
        warwis= ed11.getText().toString().trim();

        b2=(Button)findViewById(R.id.buttonwisata);
        bb=(ImageView)findViewById(R.id.gmbrWisata);
        swit=(Spinner)findViewById(R.id.kotawisata);//location
        dd2= (TextView)findViewById(R.id.Area);//curent
        dd3=(TextView)findViewById(R.id.Cuacawisata);
        dd4=(TextView)findViewById(R.id.SuhuMinWisata);
        dd5=(TextView)findViewById(R.id.KelemMinWisata);
        dd6=(TextView)findViewById(R.id.TinggiGel);


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
                new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,item);
        //untuk menentukan model adapter nya
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //menerapkan adapter pada spinner sp
        swit.setAdapter(adapter);

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
                new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_spinner_dropdown_item,itemkota);
        //untuk menentukan model adapter nya
        adapterkota.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //menerapkan adapter pada spinner sp
        skot.setAdapter(adapterkota);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = skot.getSelectedItem().toString();
                String finalUrl = "http://balai3.denpasar.bmkg.go.id/bbmkg3_xml_files/propinsi_17_2.xml";
                ed2.setText(finalUrl);

                obj = new HandleXML(finalUrl, url);
                obj.fetchXML();

                while (obj.parsingComplete) ;
                ed2.setText(obj.getCountry());
                if(obj.getSuhuMin().equals("") && obj.getSuhuMax().equals("") && obj.getKelembapanMin().equals("")
                        && obj.getKelembapanMin().equals("") && obj.getKecepatan().equals("") && obj.getTanggalMulai().equals("")
                        && obj.getTanggalSampai().equals("") && obj.getCuaca().equals("")) {
                    ed3.setText("");
                    ed4.setText("");
                    ed5.setText("");
                    ed6.setText("");
                    ed7.setText("");
                    ed8.setText("");
                    ed12.setText("");
                    ed13.setText("");
                }else{
                    ed3.setText(obj.getSuhuMin() + " - ");
                    ed4.setText(obj.getSuhuMax() + " 'C");
                    ed5.setText(obj.getKelembapanMin() + " - ");
                    ed6.setText(obj.getKelembapanMax() + " %");
                    ed7.setText(obj.getKecepatan() + " km/jam");
                    ed8.setText(obj.getCuaca());
                    ed12.setText(obj.getTanggalSampai() + " / ");
                    ed13.setText(obj.getTanggalMulai() + " / ");
                }
                ed9.setText(obj.getArahAngin());
                ed14.setText(obj.getJamSampai()+" WIB");
                ed15.setText(obj.getJamMulai()+" WIB");
                be.setImageResource(obj.getGambar());

                String urlwarning = "http://balai3.denpasar.bmkg.go.id/bbmkg3_xml_files/cuaca_harian_id.xml";

                HandleXMLWarning war = new HandleXMLWarning(urlwarning);
                war.fetchXML();

                while (war.parsingComplete);

                if(war.getWarning().equals("NIL")){
                    warn.setText("Tidak Ada Peringatan");
                    ed11.setText("Tidak Ada Peringatan");
                }else{
                    NotificationManager notif = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                    Notification notify = new Notification(R.drawable.logoweb, awar, System.currentTimeMillis());

                    PendingIntent pending = PendingIntent.getActivity(getApplicationContext(), 0, new Intent(), 0);
                    notify.defaults |= notify.DEFAULT_ALL;
                    notify.flags |= notify.FLAG_AUTO_CANCEL;
                    String subject ="Peringatan Cuaca BMKG ";
                    String body = (war.getWarning());
//                    notify.setLatestEventInfo(getApplicationContext(), subject, body, pending);
                    notif.notify(0, notify);
                    warn.setText(war.getWarning());
                    ed11.setText(war.getWarning());

                }
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String url = swit.getSelectedItem().toString();
                String finalUrl = "http://balai3.denpasar.bmkg.go.id/bbmkg3_xml_files/cuaca_wisata.xml";
                dd2.setText(finalUrl);

                objw = new HandleXMLWisata(finalUrl, url);
                objw.fetchXML();

                while(objw.parsingComplete);
                dd2.setText(objw.getArea());
                if(objw.getWeather().equals("Generally Cloudy")){
                    dd3.setText("Berawan");
                }else if(objw.getWeather().equals("Sunny")){
                    dd3.setText("Cerah");
                }else if(objw.getWeather().equals("Generally Sunny to Cloudy")){
                    dd3.setText("Cerah Berawan");
                }else if(objw.getWeather().equals("Possibility of Heavy Rain")){
                    dd3.setText("Hujan Lebat");
                }else if(objw.getWeather().equals("Possibility of Light Rain")){
                    dd3.setText("Hujan Ringan");
                }else if(objw.getWeather().equals("Possibility of Moderate Rain")){
                    dd3.setText("Hujan Sedang");
                }
                if(objw.getTemperature().equals("") && objw.getHumidity().equals("") && objw.getTinggiGel().equals("")){
                    dd4.setText("");
                    dd5.setText("");
                    dd6.setText("");
                }else {
                    dd4.setText(objw.getTemperature() + " 'C");
                    dd5.setText(objw.getHumidity() + " %");
                    dd6.setText(objw.getTinggiGel() + " m");
                    bb.setImageResource(objw.getGambarWisata());
                    dd11.setText(objw.getTanggalMulaiWisata()+" / ");
                    dd22.setText(objw.getJammulaiWisata()+" WIB");
                    dd33.setText(objw.getTanggalSampaiWisata()+" / ");
                    dd44.setText(objw.getJamSampaiWisata()+" WIB");

                }

            }
        });

        hos = (TabHost) findViewById(R.id.tabHost);

        hos.setup();
        TabHost.TabSpec spec = hos.newTabSpec("Tab1");
        spec.setIndicator("KOTA");
        spec.setContent(R.id.KOTA);

        hos.addTab(spec);

        hos.setup();
        TabHost.TabSpec spec2 = hos.newTabSpec("Tab2");
        spec2.setIndicator("Wisata");
        spec2.setContent(R.id.WISATA);

        hos.addTab(spec2);

        hos.setup();
        TabHost.TabSpec spec3 = hos.newTabSpec("Tab3");
        spec3.setIndicator("HELP");
        spec3.setContent(R.id.HELP);

        hos.addTab(spec3);

        hos.setup();
        TabHost.TabSpec spec4 = hos.newTabSpec("Tab4");
        spec4.setIndicator("ABOUT");
        spec4.setContent(R.id.Credit);

        hos.addTab(spec4);



    }



}
