package es.upm.dit.adsw.ej7;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Adapta una List de java a una ListView de android.
 * Usa res/layout/rss_item_row.xml.
 *
 * @author Javier de la Fuente Casal
 * @version 21.5.2016
 */
public class RssArrayAdapter extends ArrayAdapter<RssItem> {

    /**
     * Constructor.
     *
     * @param context     The current context.
     * @param RssItemList The objects to represent in the ListView.
     */
    public RssArrayAdapter(Context context, List<RssItem> RssItemList) {
        super(context, 0, RssItemList);
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link android.view.LayoutInflater#inflate(int, android.view.ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        RssItem item = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.rss_item_row, parent, false);

        // Lookup view for data population
        TextView nameTV = (TextView) convertView.findViewById(R.id.titular);
        TextView dateTV = (TextView) convertView.findViewById(R.id.fecha);
        TextView descriptionTV = (TextView) convertView.findViewById(R.id.descripcion);

        // Populate the data into the template view using the data object
        nameTV.setText(item.title);
        dateTV.setText(item.date);
        descriptionTV.setText(Html.fromHtml(item.description));

        // Return the completed view to render on screen
        return convertView;
    }
}