package es.upm.dit.adsw.ej7;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private List<String> optionList;
    private List<String> urlList;
    private Spinner feedSpinner;
    private ProgressBar barra;
    private EditText palabras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        feedSpinner = (Spinner) findViewById(R.id.spinner);
        barra = (ProgressBar) findViewById(R.id.progressBar);

        optionList = new ArrayList<>();
        urlList = new ArrayList<>();
        add("El Norte de Castilla", "http://www.elnortedecastilla.es/rss/2.0/portada");
        add("El Día de Valladolid", "http://www.eldiadevalladolid.com/rss/DVPortada.xml");
        add("Universidad de Valladolid", "http://comunicacion.uva.es/opencms/rss/lauvainforma.html");
        add("El País",
                "http://ep00.epimg.net/rss/elpais/portada.xml");
        add("SlashDat",
                "http://dat.etsit.upm.es/?q=/rss/all");
        add("NY Times - Technology",
                "http://rss.nytimes.com/services/xml/rss/nyt/Technology.xml"
        );
        add("Xataka Android", "http://feeds.weblogssl.com/xatakandroid");


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_selectable_list_item, optionList);
        feedSpinner.setAdapter(adapter);
    }

    public void go(View view) {
        try {
            palabras = (EditText) findViewById(R.id.editText);
            String words = palabras.getText().toString();
            int position = feedSpinner.getSelectedItemPosition();
            String url = urlList.get(position);
            String[] parameters = new String[]{url, words};
            AsyncTask<String, Void, Void> task = new RssRetrieveTask();
            task.execute(parameters);
        } catch (Exception e) {
            Toast.makeText(MainActivity.this.getBaseContext(), "Error intentar cargar las noticias:", Toast.LENGTH_SHORT).show();
        }
    }

    private void add(String site, String url) {
        optionList.add(site);
        urlList.add(url);
    }

    private class RssRetrieveTask extends AsyncTask<String, Void, Void> {

        public RssRetrieveTask() {
        }

        @Override
        protected void onPreExecute() {
            barra.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... data) {
            try {
                String site = data[0];
                String words = data[1];
                List<RssItem> noticias = new RssFeedDownloader().loadXmlFromNetwork(site);
                FilteredRssFeed.reset(words);
                for (RssItem item : noticias) {
                    FilteredRssFeed.add(item);
                }
            } catch (Exception e) {
                Toast.makeText(MainActivity.this.getBaseContext(), "Error en RssRetrieveTask", Toast.LENGTH_SHORT).show();
            }

            return null;
        }

        /*  No se usa aquí
        @Override
        protected void onProgressUpdate(String... values){

        }*/

        protected void onPostExecute(Void v) {
            super.onPostExecute(v);
            barra.setVisibility(View.INVISIBLE);
            Intent intent = new Intent(MainActivity.this, RssListActivity.class);
            MainActivity.this.startActivity(intent);
        }
    }
}
