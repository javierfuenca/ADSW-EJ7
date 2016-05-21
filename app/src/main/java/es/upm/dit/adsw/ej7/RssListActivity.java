package es.upm.dit.adsw.ej7;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class RssListActivity extends AppCompatActivity {
    private RssArrayAdapter itemsAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_list);

        listView = (ListView) findViewById(R.id.newsListView);

        List<RssItem> itemList;
        itemList = FilteredRssFeed.getEntries();

        itemsAdapter = new RssArrayAdapter(this, itemList);
        listView.setAdapter(itemsAdapter);

        // Si hacemos click en un RssItem de la pantalla
        listView.setOnItemClickListener(new OnItemClickListenerListViewItem());
    }

    private class OnItemClickListenerListViewItem implements android.widget.AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            RssArrayAdapter adapter = (RssArrayAdapter) listView.getAdapter();
            RssItem item = adapter.getItem(position);
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.link));
            RssListActivity.this.startActivity(intent);
        }
    }
}
