package mikhail.akushko.test.balinasoft.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Lenovo on 09.09.2016.
 */
class ImageTextAdapter extends BaseAdapter {
    private Context mContext;
    public String[] categoryname ={"шашлыки", "лапша", "пицца", "роллы", "сеты", "суши", "супы", "салаты", "добавки", "теплое", "закуски", "десерты", "напитки"};

    public ImageTextAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return imageCategoryId.length;
    }

    public Object getItem(int position) {
        return imageCategoryId[position];
    }

    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        View grid;

        if (convertView == null) {
            grid = new View(mContext);

            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            grid = inflater.inflate(R.layout.main_item_category, parent, false);
        } else {
            grid = (View) convertView;
        }

        ImageView imageView = (ImageView) grid.findViewById(R.id.imageViewItemCategiry);
        TextView textView = (TextView) grid.findViewById(R.id.textViewItemCategiry);
        imageView.setImageResource(imageCategoryId[position]);
        textView.setText(categoryname[position]);

        return grid;
    }




    public Integer[] imageCategoryId = {R.drawable.shashlik, R.drawable.lapsha, R.drawable.pizza, R.drawable.rolls, R.drawable.setti,
            R.drawable.sussi, R.drawable.sup, R.drawable.salate, R.drawable.dobav, R.drawable.hot, R.drawable.zakusk, R.drawable.desert, R.drawable.napitki};
}