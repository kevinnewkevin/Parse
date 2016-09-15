package mikhail.akushko.test.balinasoft.test;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Lenovo on 09.09.2016.
 */
public class DashesListAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<Dashes> objects;
    ProgressBar progressBar;

    DashesListAdapter(Context context, ArrayList<Dashes> dashes) {
        ctx = context;
        objects = dashes;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // кол-во элементов
    @Override
    public int getCount() {
        return objects.size();
    }

    // элемент по позиции
    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    // id по позиции
    @Override
    public long getItemId(int position) {
        return position;
    }

    // пункт списка
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // используем созданные, но не используемые view
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.fragment_item, parent, false);
        }

        Dashes p = getProduct(position);

        // заполняем View в пункте списка данными из товаров: наименование, цена
        // и картинка
        ((TextView) view.findViewById(R.id.NameDashes)).setText(p.name);
        ((TextView) view.findViewById(R.id.VesDashes)).setText(p.ves + "");
        ((TextView) view.findViewById(R.id.PriceDashes)).setText(p.price + "");
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarForImage);
        progressBar.setVisibility(View.VISIBLE);

        Picasso.with(ctx).load(p.image).into((ImageView) view.findViewById(R.id.imageDashes), new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {

            }
        });

        return view;
    }

    // товар по позиции
    Dashes getProduct(int position) {
        return ((Dashes) getItem(position));
    }


}