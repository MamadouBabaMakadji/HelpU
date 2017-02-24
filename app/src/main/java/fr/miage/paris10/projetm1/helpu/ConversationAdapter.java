package fr.miage.paris10.projetm1.helpu;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MBM on 21/02/2017.
 */

public class ConversationAdapter extends ArrayAdapter {
    private List list = new ArrayList();
    public ConversationAdapter(Context context, int resource) {
        super(context, resource);
    }
    @Override
    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public void remove(Object object) {
        super.remove(object);
    }
}
