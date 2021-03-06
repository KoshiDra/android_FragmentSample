package com.example.fragmentsample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import javax.xml.namespace.QName;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MenuThanksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MenuThanksFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MenuThanksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MenuThanksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MenuThanksFragment newInstance(String param1, String param2) {
        MenuThanksFragment fragment = new MenuThanksFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private boolean isLayoutXLarge = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // 親クラスのメソッド呼び出し
        super.onCreate(savedInstanceState);

        // フラグメントマネージャー取得
        FragmentManager fragmentManager = getFragmentManager();

        // フラグメントマネージャーからメニューリストフラグメント取得
        MenuListFragment menuListFragment = (MenuListFragment) fragmentManager.findFragmentById(R.id.fragmentMenuList);

        // メニューリストフラグメントが存在しない（ = 画面サイズはスマホサイズ）
        isLayoutXLarge = menuListFragment != null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // このフラグメントが所属するアクティビティを取得
        Activity parentActivity = getActivity();

        // フラグメントで表示する画面（XML）をインフレートする
        View view = inflater.inflate(R.layout.fragment_menu_thanks, container, false);

        Bundle extras;
        if (isLayoutXLarge) {
            // このフラグメントに埋め込まれた引継ぎデータを取得
            extras = getArguments();
        } else {
            // 所属アクティビティからインテント取得
            Intent intent = parentActivity.getIntent();

            // インテントから引継ぎデータの集約オブジェクト（Bundle）を取得
            extras = intent.getExtras();
        }

        String menu = "";
        String price = "";

        // 引継ぎデータの集約オブジェクト（Bundle）存在時に値を取得
        if (extras != null) {
            menu = extras.getString("menu");
            price = extras.getString("price");
        }

        // TextViewに値を設定
        TextView tvMenu = view.findViewById(R.id.tvMenuName);
        TextView tvPrice = view.findViewById(R.id.tvMenuPrice);

        tvMenu.setText(menu);
        tvPrice.setText(price);

        // 戻るボタンにリスナ設定
        Button button = view.findViewById(R.id.btBackButton);
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isLayoutXLarge) {
                    // フラグメントマネージャーの取得
                    FragmentManager fragmentManager = getFragmentManager();

                    // フラグメントトランザクションの開始
                    FragmentTransaction fragmentTransaction= fragmentManager.beginTransaction();

                    // 自分自身を削除
                    fragmentTransaction.remove(MenuThanksFragment.this);

                    // フラグメントトランザクションをコミット
                    fragmentTransaction.commit();
                } else {
                    Activity parentActivity = getActivity();
                    parentActivity.finish();
                }
            }
        });

        // インフレートされた画面を戻り値として返す
        return view;
    }
}