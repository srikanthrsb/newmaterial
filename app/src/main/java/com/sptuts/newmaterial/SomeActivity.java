package com.sptuts.newmaterial;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.lang.reflect.Field;

public class SomeActivity extends AppCompatActivity {

    FloatingActionButton fab;
    TabLayout tabLayout;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    EditText toolbarSearchView;
    LinearLayout searchContainer;
    ImageView searchClearButton;
    ActionBarDrawerToggle actionBarDrawerToggle;
    boolean toolbarHomeButtonAnimating;
    Menu mnu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);
        setupDrawerLayout();
        setupToolbar();
        setupSearchView();
    }

    private void setupSearchView() {
        searchContainer = (LinearLayout) findViewById(R.id.search_container);
        toolbarSearchView = (EditText) findViewById(R.id.search_view);
        searchClearButton = (ImageView) findViewById(R.id.search_clear);


        // Setup search container view
        try {
            // Set cursor colour to white
            // http://stackoverflow.com/a/26544231/1692770
            // https://github.com/android/platform_frameworks_base/blob/kitkat-release/core/java/android/widget/TextView.java#L562-564
            Field f = TextView.class.getDeclaredField("mCursorDrawableRes");
            f.setAccessible(true);
            f.set(toolbarSearchView, R.drawable.cursor);
        } catch (Exception ignored) {
        }

        // Search text changed listener
        toolbarSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               /* Fragment mainFragment = getFragmentManager().findFragmentById(R.id.container);
                if (mainFragment != null && mainFragment instanceof MainListFragment) {
                    ((MainListFragment) mainFragment).search(s.toString());
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        // Clear search text when clear button is tapped
        searchClearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toolbarSearchView.setText("");
            }
        });

        // Hide the search view
        searchContainer.setVisibility(View.GONE);
    }


    public void displaySearchView(boolean visible) {
        if (visible) {
            // Stops user from being able to open drawer while searching
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

            // Hide search button, display EditText
            mnu.findItem(R.id.action_search).setVisible(false);
            searchContainer.setVisibility(View.VISIBLE);

            // Animate the home icon to the back arrow
            toggleActionBarIcon(ActionDrawableState.ARROW, actionBarDrawerToggle, true);

            // Shift focus to the search EditText
            toolbarSearchView.requestFocus();

            // Pop up the soft keyboard
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    toolbarSearchView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0, 0, 0));
                    toolbarSearchView.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0, 0, 0));
                }
            }, 200);
        } else {
            // Allows user to open drawer again
            drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);

            // Hide the EditText and put the search button back on the Toolbar.
            // This sometimes fails when it isn't postDelayed(), don't know why.
            toolbarSearchView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    toolbarSearchView.setText("");
                    searchContainer.setVisibility(View.GONE);
                    mnu.findItem(R.id.action_search).setVisible(true);
                }
            }, 200);

            // Turn the home button back into a drawer icon
            toggleActionBarIcon(ActionDrawableState.BURGER, actionBarDrawerToggle, true);

            // Hide the keyboard because the search box has been hidden
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(toolbarSearchView.getWindowToken(), 0);
        }
    }


    private enum ActionDrawableState {
        BURGER, ARROW
    }

    private void toggleActionBarIcon(final ActionDrawableState state, final ActionBarDrawerToggle toggle, boolean animate) {
        if (animate) {
            float start = state == ActionDrawableState.BURGER ? 1.0f : 0f;
            float end = Math.abs(start - 1);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                ValueAnimator offsetAnimator = ValueAnimator.ofFloat(start, end);
                offsetAnimator.setDuration(300);
                offsetAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
                offsetAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        float offset = (Float) animation.getAnimatedValue();
                        toggle.onDrawerSlide(null, offset);
                    }
                });
                offsetAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        toolbarHomeButtonAnimating = false;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
                toolbarHomeButtonAnimating = true;
                offsetAnimator.start();
            }
        } else {
            if (state == ActionDrawableState.BURGER) {
                toggle.onDrawerClosed(null);
            } else {
                toggle.onDrawerOpened(null);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_some, menu);

        mnu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerLayout() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.openDrawer, R.string.closeDrawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        setupNavigation();
    }

    private void setupNavigation() {
        NavigationView navView = (NavigationView) findViewById(R.id.navigation_view);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                Snackbar.make(findViewById(R.id.coordLayout), menuItem.getTitle() + " is pressed", Snackbar.LENGTH_LONG).show();
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // toolbarHomeButtonAnimating is a boolean that is initialized as false. It's used to stop the user pressing the home button while it is animating and breaking things.
                if (!toolbarHomeButtonAnimating) {
                    // Here you'll want to check if you have a search query set, if you don't then hide the search box.
                    // My main fragment handles this stuff, so I call its methods.
                    if (searchContainer.getVisibility() == View.VISIBLE) {
                        displaySearchView(false);
                        return;
                    }
                }

                if (drawerLayout.isDrawerOpen(findViewById(R.id.navigation_view)))
                    drawerLayout.closeDrawer(findViewById(R.id.navigation_view));
                else
                    drawerLayout.openDrawer(findViewById(R.id.navigation_view));
            }
        });
    }

}
