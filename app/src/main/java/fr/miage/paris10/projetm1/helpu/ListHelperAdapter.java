package fr.miage.paris10.projetm1.helpu;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class ListHelperAdapter extends ArrayAdapter<String> {

    private Activity context;
    private ArrayList users_name ;

    private int icon = R.drawable.user;
    public ListHelperAdapter(Activity context, ArrayList users_name) {
        super(context, R.layout.row_list_helpeur, users_name);
        this.context=context;
        this.users_name = users_name;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.row_list_helpeur, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.helpeur_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.helpeur_picture);
        txtTitle.setText(users_name.get(position).toString());
        //int []img = {R.drawable.avocado,R.drawable.icecream,R.drawable.chili};
        imageView.setImageResource(icon);
        return rowView;
    }
}
