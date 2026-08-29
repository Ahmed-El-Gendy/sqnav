package com.sagendy.sqnav.sample;

import android.os.Bundle;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.sagendy.sqnav.SqNav;
import com.sagendy.sqnav.SqNavItem;

public class MainActivity extends AppCompatActivity {

    private static final int TAB_HOME = 1;
    private static final int TAB_SEARCH = 2;
    private static final int TAB_DOWNLOADS = 3;
    private static final int TAB_PROFILE = 4;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tvSelected = findViewById(R.id.tv_selected_tab);
        SqNav sqNav = findViewById(R.id.sqnav);

        sqNav.addItem(new SqNavItem(TAB_HOME, "Home", R.drawable.ic_home));
        sqNav.addItem(new SqNavItem(TAB_SEARCH, "Search", R.drawable.ic_search));
        sqNav.addItem(new SqNavItem(TAB_DOWNLOADS, "Downloads", R.drawable.ic_downloads));
        sqNav.addItem(new SqNavItem(TAB_PROFILE, "Profile", R.drawable.ic_profile));

        sqNav.setOnItemSelectedListener(itemId -> {
            if (itemId == TAB_HOME) tvSelected.setText("Active Tab: Home");
            else if (itemId == TAB_SEARCH) tvSelected.setText("Active Tab: Search");
            else if (itemId == TAB_DOWNLOADS) tvSelected.setText("Active Tab: Downloads");
            else if (itemId == TAB_PROFILE) tvSelected.setText("Active Tab: Profile");
        });
    }
}
