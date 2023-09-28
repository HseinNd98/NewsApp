package hussein.nasereddine.areeba_challenge.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.navigation.NavigationView;

import hussein.nasereddine.areeba_challenge.R;
import hussein.nasereddine.areeba_challenge.enums.ArticleLanguage;
import hussein.nasereddine.areeba_challenge.fragments.FragNewsList;

@SuppressWarnings("FieldCanBeLocal")
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    // Views
    private MaterialButton mBtnMenu;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;

    // Fragments
    private final FragNewsList mFragNewsEn = FragNewsList.newInstance(ArticleLanguage.ENGLISH);
    private final FragNewsList mFragNewsAr = FragNewsList.newInstance(ArticleLanguage.ARABIC);

    // Configs
    private Fragment mCurrentFragment = mFragNewsEn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initFragments();

        mNavigationView.setNavigationItemSelectedListener(this);
        mBtnMenu.setOnClickListener(v -> mDrawerLayout.openDrawer(GravityCompat.START));
    }

    private void showFragment(@NonNull Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .hide(mCurrentFragment)
                .show(fragment)
                .commit();

        mCurrentFragment = fragment;
    }

    private void initFragments(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.activity_main_frame_layout, mFragNewsEn, String.format("%s%s", FragNewsList.TAG, "_EN"))
                .add(R.id.activity_main_frame_layout, mFragNewsAr, String.format("%s%s", FragNewsList.TAG, "_AR"))
                .show(mFragNewsEn)
                .hide(mFragNewsAr)
                .commit();
    }

    private void initViews(){
        mBtnMenu = findViewById(R.id.activity_main_btn_menu);
        mNavigationView = findViewById(R.id.activity_main_navigation_view);
        mDrawerLayout = findViewById(R.id.activity_main_drawer_layout);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final int id = item.getItemId();
        if(id == R.id.nav_lang_en){
            showFragment(mFragNewsEn);
        }else if(id == R.id.nav_lang_ar){
            showFragment(mFragNewsAr);
        }else if(id == R.id.nav_albums){
            startActivity(new Intent(this, AlbumActivity.class));
        }else if(id == R.id.nav_contact){
            startActivity(new Intent(this, ContactActivity.class));
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}