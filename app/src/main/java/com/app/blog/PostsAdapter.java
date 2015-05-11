package com.app.blog;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.blog.database.DataContract;
import com.app.blog.utils.ImageLoader;
import com.kiory.blog.R;

/**
 * Created by darknoe on 19/4/15.
 */
public class PostsAdapter extends CursorAdapter {

    public static class ViewHolder {
        public final ImageView icon;
        public final TextView title;
        public final TextView description;

        public ViewHolder(View view) {
            icon = (ImageView) view.findViewById(R.id.list_item_icon);
            title = (TextView) view.findViewById(R.id.list_item_title);
            description= (TextView) view.findViewById(R.id.list_item_description);
        }
    }

    public PostsAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        // Choose the layout type
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_post, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder viewHolder = (ViewHolder) view.getTag();

        // Read weather forecast from cursor
        String title = cursor.getString(cursor.getColumnIndex(DataContract.PostEntry.COLUMN_TITLE_PLAIN));
        viewHolder.title.setText(Html.fromHtml(title));

        String description = cursor.getString(cursor.getColumnIndex(DataContract.PostEntry.COLUMN_EXCERPT));
        viewHolder.description.setText(Html.fromHtml(description));

        // For accessibility, add a content description to the icon field
        viewHolder.icon.setContentDescription(title);

        ImageLoader image = new ImageLoader(context);
        image.DisplayImage(cursor.getString(cursor.getColumnIndex(DataContract.PostEntry.COLUMN_ICON)), viewHolder.icon);
    }

}
