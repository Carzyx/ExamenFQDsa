package marchilla.fqandroid.Service;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import marchilla.fqandroid.Activity.Display;
import marchilla.fqandroid.Model.Follower;
import marchilla.fqandroid.R;

import static marchilla.fqandroid.R.id.followerImage;

/**
 * Created by Miguel Angel on 31/05/2017.
 */

public class FollowerAdapter extends ArrayAdapter<Follower> {
    private final Context _context;
    public FollowerAdapter(Context context, ArrayList<Follower> users) {
        super(context, R.layout.follower, users);
        _context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Follower user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.follower, parent, false);
        }

        // Lookup view for data population
        TextView followerId = (TextView) convertView.findViewById(R.id.followerId);
        TextView followerName = (TextView) convertView.findViewById(R.id.followerName);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.followerImage);

        // Populate the data into the template view using the data object
        followerId.setText(user.getId());
        followerName.setText(user.getLogin());
        Picasso.with(_context).load(user.getAvatarUrl()).into(imageView);


        // Return the completed view to render on screen
        return convertView;
    }

}
