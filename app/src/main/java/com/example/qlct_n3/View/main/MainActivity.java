package com.example.qlct_n3.View.main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.qlct_n3.R;
import com.example.qlct_n3.base.DataBaseManager;
import com.example.qlct_n3.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (binding == null) {
            binding = ActivityMainBinding.inflate(getLayoutInflater());
        }
        setContentView(binding.getRoot());

        DataBaseManager database = DataBaseManager.getInstance(getApplicationContext());

        // Thiết lập ViewPager và BottomNavigationView
        setupViewPager();
        setupBottomNavigation();
    }

    private void setupViewPager() {
        ViewPagerAdapter adapter = new ViewPagerAdapter(this);
        binding.viewpager.setAdapter(adapter);
        binding.viewpager.setUserInputEnabled(false); // Tắt vuốt để chuyển tab

        // Xử lý sự kiện khi ViewPager2 chuyển trang
        binding.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position) {
                    case 0:
                        binding.bottomNavigation.setSelectedItemId(R.id.navigation_home);
                        break;
                    case 1:
                        binding.bottomNavigation.setSelectedItemId(R.id.navigation_calendar);
                        break;
                    case 2:
                        binding.bottomNavigation.setSelectedItemId(R.id.navigation_chart);
                        break;
                    case 3:
                        binding.bottomNavigation.setSelectedItemId(R.id.navigation_menu);
                        break;
                }
            }
        });
    }

    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_home) {
                    binding.viewpager.setCurrentItem(0, false);
                    return true;
                } else if (itemId == R.id.navigation_calendar) {
                    binding.viewpager.setCurrentItem(1, false);
                    return true;
                } else if (itemId == R.id.navigation_chart) {
                    binding.viewpager.setCurrentItem(2, false);
                    return true;
                } else if (itemId == R.id.navigation_menu) {
                    binding.viewpager.setCurrentItem(3, false);
                    return true;
                }
                return false;
            }
        });
    }
}