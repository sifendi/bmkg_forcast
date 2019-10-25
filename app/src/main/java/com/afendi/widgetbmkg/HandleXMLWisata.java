package com.afendi.widgetbmkg;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HandleXMLWisata {
    private String Area = "";
    private String tampArea = "";
    private String Temperature = "";
    private String Humidity = "";
    private String TinggiGel = "";
    private String Weather = "";
    private String kota = "";
    private String tanggalmulaiwis = "";
    private String jammulaiwis = "";
    private String tanggalsampaiwis = "";
    private String jamsampaiwis = "";
    private int ih;
    private String urlString = null;
    private XmlPullParserFactory xmlFactoryObject;
    public volatile boolean parsingComplete = true;

    public HandleXMLWisata(String url, String area) {

        this.urlString = url;
        this.kota = area;
    }

    public String getArea() {
        return tampArea;
    }

    public String getTemperature() {
        return Temperature;
    }

    public String getHumidity() {
        return Humidity;
    }

    public String getTinggiGel() {
        return TinggiGel;
    }

    public String getWeather() {
        return Weather;
    }


    public String getTanggalMulaiWisata() {
        return tanggalmulaiwis;
    }

    public String getJammulaiWisata() {
        return jammulaiwis;
    }
    public String getTanggalSampaiWisata() {
        return tanggalsampaiwis;
    }
    public String getJamSampaiWisata() {
        return jamsampaiwis;
    }
    public int getGambarWisata(){
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


                        if (name.equalsIgnoreCase("Area")) {
                            Area = text;
                            Log.i("parserxml", "Area: " + Area);

                            if (kota.equals(Area)) {
                                tampArea = text;
                                Log.i("parserxml", "Area berulang: " + tampArea);
                                status = true;
                            }
                        }
//                        }
                        else if (name.equalsIgnoreCase("Weather") && status) {
                            Weather = text;
                            Log.i("parserxml", "Cuacanya adalah: " + Weather);
                            if(Weather.equals("Generally Cloudy")){
                                ih =R.drawable.bberawan;
                            }else if(Weather.equals("Sunny")){
                                ih=R.drawable.ccerah;
                            }else if(Weather.equals("Generally Sunny to Cloudy")){
                                ih=R.drawable.ccerahberawan;
                            }else if(Weather.equals("Possibility of Heavy Rain")){
                                ih=R.drawable.hhujanlebat;
                            }else if(Weather.equals("Possibility of Light Rain")){
                                ih=R.drawable.hhujanringan;
                            }else if(Weather.equals("Possibility of Moderate Rain")){
                                ih=R.drawable.hhujansedang;
                            }
                        } else if (name.equalsIgnoreCase("Temperature") && status) {
                            Temperature = text;
                            Log.i("parserxml", "suhu: " + Temperature);
                        } else if (name.equalsIgnoreCase("Humidity") && status) {
                            Humidity = text;
                            Log.i("parserxml", "Kelembapan: " + Humidity);
                        }
                        else if (name.equalsIgnoreCase("WaveHeight") && status ) {
                            TinggiGel = text;
                            Log.i("parserxml", "Tinggi Gelombang: " + TinggiGel);
                            status = false;
                        }

                        else if (name.equalsIgnoreCase("ValidStart")) {
                            tanggalmulaiwis = text;
                            Log.i("parserxml", "Tanggal mulai wisata: " + tanggalmulaiwis);
                        }
                        else if (name.equalsIgnoreCase("ValidTimeStart")) {
                            jammulaiwis = text;
                            Log.i("parserxml", "jam mulai: " + jammulaiwis);

                        }
                        else if (name.equalsIgnoreCase("ValidEnd")) {
                            tanggalsampaiwis = text;
                            Log.i("parserxml", "Tanggal sampai Wisata: " + tanggalsampaiwis);
                        }
                        else if (name.equalsIgnoreCase("ValidTimeEnd")) {
                            jamsampaiwis = text;
                            Log.i("parserxml", "jam Sampai: " + jamsampaiwis);
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
