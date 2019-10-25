package com.afendi.widgetbmkg;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandleXML {
    private String Kota = "";
    private String tampKota = "";
    private String SuhuMin = "";
    private String SuhuMax = "";
    private String KelembapanMin = "";
    private String KelembapanMax = "";
    private String Cuaca = "";
    private String ArahAngin = "";
    private String KecepatanAngin = "";
    private String kota = "";
    private String sampai = "";
    private String sampaijam = "";
    private String mulai = "";
    private String mulaijam = "";
    private int ih ;
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public HandleXML(String url, String kota) {

        this.urlString = url;
        this.kota = kota;
    }

    public String getCountry() {
        return tampKota;
    }

    public String getSuhuMin() {
        return SuhuMin;
    }

    public String getSuhuMax() {
        return SuhuMax;
    }

    public String getKelembapanMin() {
        return KelembapanMin;
    }

    public String getKelembapanMax() {
        return KelembapanMax;
    }

    public String getKecepatan() {
        return KecepatanAngin;
    }

    public String getCuaca() {
        return Cuaca;
    }

    public String getTanggalSampai(){
        return sampai;
    }
    public String getTanggalMulai(){
        return mulai;
    } public String getJamMulai(){
        return mulaijam;
    } public String getJamSampai(){
        return sampaijam;
    }
    public String getArahAngin() {
        return ArahAngin;
    }
    public int getGambar(){
        return ih;
    }
    public void parseXMLAndStoreIt(XmlPullParser myParser) {
        int event;
        String text = null;

        try {
            event = myParser.getEventType();

            boolean status = false;
            while (event != XmlPullParser.END_DOCUMENT) {
                String name = myParser.getName();

                switch (event) {
                    case XmlPullParser.START_TAG:
                        break;

                    case XmlPullParser.TEXT:
                        text = myParser.getText();
                        break;

                    case XmlPullParser.END_TAG:
//                        if(name.equalsIgnoreCase("Row")){


                        if (name.equalsIgnoreCase("Kota")) {
                            Kota = text;
                            Log.i("parserxml", "kota: " + Kota);

                            if (kota.equals(Kota)) {
                                tampKota = text;
                                Log.i("parserxml", "kota berulang: " + tampKota);
                                status = true;
                            }
                        }
//                        }
                        else if (name.equalsIgnoreCase("Cuaca") && status) {
                            Cuaca = text;
                            Log.i("parserxml", "Cuacanya adalah: " + Cuaca);
                            if(Cuaca.equals("Berawan")){
                                ih =R.drawable.bberawan;
                            }else if(Cuaca.equals("Cerah")){
                                ih=R.drawable.ccerah;
                            }else if(Cuaca.equals("Cerah Berawan")){
                                ih=R.drawable.ccerahberawan;
                            }
                            else if(Cuaca.equals("Hujan Lebat")){
                                ih=R.drawable.hhujanlebat;
                            }else if(Cuaca.equals("Hujan Ringan")){
                                ih=R.drawable.hhujanringan;
                            }else if(Cuaca.equals("Hujan Sedang")){
                                ih=R.drawable.hhujansedang;
                            }

                        } else if (name.equalsIgnoreCase("SuhuMin") && status) {
                            SuhuMin = text;
                            Log.i("parserxml", "suhumix berulang: " + SuhuMin);
                        } else if (name.equalsIgnoreCase("SuhuMax") && status) {
                            SuhuMax = text;
                            Log.i("parserxml", "suhumax berulang: " + SuhuMax);
                        } else if (name.equalsIgnoreCase("KelembapanMin") && status) {
                            KelembapanMin = text;
                            Log.i("parserxml", "kelembapanMin berulang: " + KelembapanMin);
                        } else if (name.equalsIgnoreCase("KelembapanMax") && status) {
                            KelembapanMax = text;
                            Log.i("parserxml", "kelembapanmax berulang: " + KelembapanMax);
                        }
                        else if (name.equalsIgnoreCase("KecepatanAngin") && status) {
                            KecepatanAngin = text;
                            Log.i("parserxml", "kecepatan berulang: " + KecepatanAngin);
                        }
                        else if (name.equalsIgnoreCase("ArahAngin") && status) {
                            ArahAngin = text;
                            Log.i("parserxml", "ArahAngin berulang: " + ArahAngin);
                            status = false;
                        } else if (name.equalsIgnoreCase("Mulai")) {
                            mulai = text;
                            Log.i("parserxml", "Tanggal Mulai: " + mulai);
                        }else if (name.equalsIgnoreCase("MulaiPukul")) {
                            mulaijam = text;
                            Log.i("parserxml", "Jam Mulai: " + mulaijam);
                        }else if (name.equalsIgnoreCase("Sampai")) {
                            sampai = text;
                            Log.i("parserxml", "Tanggal Sampai: " + sampai);
                        }else if (name.equalsIgnoreCase("SampaiPukul")) {
                            sampaijam = text;
                            Log.i("parserxml", "Jam Sampai: " + sampaijam);
                        }
                        else
                            break;
                }
                event = myParser.next();
            }
            parsingComplete = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fetchXML() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlString);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setReadTimeout(10000 /* milliseconds */);
                    conn.setConnectTimeout(15000 /* milliseconds */);
                    conn.setRequestMethod("GET");
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream stream = conn.getInputStream();
                    xmlFactoryObject = XmlPullParserFactory.newInstance();
                    XmlPullParser myparser = xmlFactoryObject.newPullParser();

                    myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                    myparser.setInput(stream, null);

                    parseXMLAndStoreIt(myparser);
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
