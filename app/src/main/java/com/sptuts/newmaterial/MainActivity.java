package com.sptuts.newmaterial;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sptuts.newmaterial.adapters.PagerAdapter;
import com.sptuts.newmaterial.frags.Firstfragment;

//This is just a sample comment
public class MainActivity extends AppCompatActivity implements Firstfragment.OnFragmentInteractionListener {

    FloatingActionButton fab;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupToolbar();
        setupTabLayout();
        setupFAB();

    }

    private void setupTabLayout() {
        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_data));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_up));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_down));


        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition(),true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setupFAB() {
        fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(fabLstnr);
    }

    View.OnClickListener fabLstnr = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Snackbar.make(findViewById(R.id.rootLayout), "Snackbar called by Fab", Snackbar.LENGTH_LONG)
                    .setAction("UNDO",this)
                    .show();
        }
    };

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
