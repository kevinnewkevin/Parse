package mikhail.akushko.test.balinasoft.test;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;


import android.widget.GridView;


/**
 * Fragment категории.
 * отображает все катериии еды.
 * Картинки и текс статичены
 */
public class CategoryFragment extends Fragment {

    GridView gridMainCategory;
    DashesFragment dashesFragment = new DashesFragment();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.category, container, false);
        // находим Элементы
        gridMainCategory = (GridView) v.findViewById(R.id.gridViewMainCategory);

        //применяем adaper для GridView
        gridMainCategory.setAdapter(new ImageTextAdapter(getActivity()));
        gridMainCategory.setNumColumns(gridMainCategory.AUTO_FIT);

        // указываем вертикальный и горизотальный отступ
        gridMainCategory.setVerticalSpacing(40);
        gridMainCategory.setHorizontalSpacing(10);

        gridMainCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                // присваиваем кажому элементу в списке номер по позиции в массиве
                int idCategoty[] = {24, 3, 1, 18, 2, 5, 6, 7, 23, 8, 20, 10, 9};

                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                Bundle bundle = new Bundle();



                // передаем параметры в другой фрагмент
                bundle.putInt("number", idCategoty[pos]);
                DashesFragment dashesFragment = new DashesFragment();


                // боримся с ошибкой fragment already active
                // передаем аргументы другому фрагменту
                if (dashesFragment.getArguments() != null) {

                    dashesFragment.getArguments().clear();
                    bundle.putInt("number", idCategoty[pos]);
                    bundle.putString("titels", new ImageTextAdapter(getActivity()).categoryname[pos].toString());
                } else {
                    bundle.putInt("number", idCategoty[pos]);
                    bundle.putString("titels", new ImageTextAdapter(getActivity()).categoryname[pos].toString());
                }

                dashesFragment.setArguments(bundle);


                fragmentTransaction.replace(R.id.ContainerFragment, dashesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        return v;
    }


}
