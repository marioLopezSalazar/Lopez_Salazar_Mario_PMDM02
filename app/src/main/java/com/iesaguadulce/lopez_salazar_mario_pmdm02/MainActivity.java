package com.iesaguadulce.lopez_salazar_mario_pmdm02;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.iesaguadulce.lopez_salazar_mario_pmdm02.databinding.ActivityMainBinding;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.model.Character;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.model.Database;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.settings.SettingsFragment;

import java.util.ArrayList;


/**
 * Main Activity of the application.
 * Inflates the layout that serves as the main user interface and sets up the navigation elements,
 * including the ToolBar and navigation controller, allowing users to navigate through
 * different fragments and destinations within the app.
 */
public class MainActivity extends AppCompatActivity {


    /**
     * NavController object for managing navigation within the application.
     */
    private NavController navController;

    /**
     * ViewBinding to handle the view and access its elements.
     */
    private ActivityMainBinding binding;

    /**
     * Provides a button (hamburger icon) on the ToolBar to facilitate interaction with the NavigationDrawer.
     */
    private ActionBarDrawerToggle toggle;

    /**
     * Encapsulates the configuration of the ToolBar.
     */
    AppBarConfiguration configuration;


    /**
     * Initializes the activity. This method executes several actions:
     * - Loads the preferred language (default ES).
     * - Inflates the ActivityMain layout.
     * - Sets the ToolBar as the application's ActionBar.
     * - Links the button of the ToolBar (burger//back) with the NavigationDrawer and the navigation controller.
     * - Launches the configuration of the ToggleButton and the NavigationDrawer.
     *
     * @param savedInstanceState If the activity is being re-initialized after previously being
     *                           shut down then this Bundle contains the data it most recently
     *                           supplied in onSaveInstanceState(Bundle). Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Loading and preferred language (default ES):
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean english = preferences.getBoolean("languageEnglish", false);
        if (english)
            SettingsFragment.changeLanguage(this, "en");
        else
            SettingsFragment.changeLanguage(this, "es");

        //Inflating the main view:
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Establishing the ToolBar as the ActionBar of the app:
        setSupportActionBar(binding.toolbar);


        // Linking the button of the ToolBar (burger//back) with the NavigationDrawer and the NavController:
        // STEP 1: Getting the NavController from the FragmentContainer:
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(binding.fragmentsContainer.getId());
        if (navHostFragment != null) {
            navController = NavHostFragment.findNavController(navHostFragment);

            //STEP 2: Indicating the top-level fragment and the NavigationDrawer opened-closed status:
            configuration = new AppBarConfiguration
                    .Builder(R.id.characterList)
                    .setOpenableLayout(binding.drawerLayout)
                    .build();

            // STEP 3: Applying above configuration and linking with the NavController:
            NavigationUI.setupActionBarWithNavController(this, navController, configuration);
        }

        // Launching the configuration of the ToggleButton:
        configureToggle();

        // Launching the configuration of the NavigationDrawer:
        configureNavigationMenu();
    }


    /**
     * Creates and configures the ActionBarDrawerToggle for opening-closing the DrawerLayout.
     * Additionally, a listener is added to establish the desired icon after fragment navigation.
     */
    void configureToggle() {
        // Creating the Toggle:
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                R.string.open_navigation_drawer, R.string.close_navigation_drawer);

        // Registering the Toggle as a listener of opened-closed changes in the DrawerLayout:
        binding.drawerLayout.addDrawerListener(toggle);

        // Synchronizing the Toggle with the current status of the DrawerLayout:
        toggle.syncState();

        // Only showing the burger icon in the Character list fragment:
        navController.addOnDestinationChangedListener(this::burgerOrBack);
    }


    /**
     * Called when the NavController has changed the destination.
     * Controls when the 'burger icon' or the 'back icon' are shown.
     *
     * @param navController  The NavController object.
     * @param navDestination The destination node in the navigation graph.
     * @param bundle         If non-null, the previous status of the re-constructed fragment or
     *                       arguments passed to the destination fragment.
     */
    void burgerOrBack(NavController navController, NavDestination navDestination, Bundle bundle) {
        if (toggle == null)
            return;

        // Burger icon only shown when Character list is displayed:
        toggle.setDrawerIndicatorEnabled(navDestination.getId() == R.id.characterList);
    }


    /**
     * Called when the user chooses to navigate up.
     * This method delegates the user's "Up requirement" to the NavController.
     *
     * @return True if navigation back was successful; False otherwise.
     */
    @Override
    public boolean onSupportNavigateUp() {
        return
                NavigationUI.navigateUp(navController, configuration)
                        || super.onSupportNavigateUp();
    }


    /**
     * Configures the response to a user's click on a menu option in the NavigationLayout.
     */
    void configureNavigationMenu() {

        binding.navigationView.setNavigationItemSelectedListener(
                menuItem -> {

                    // Identifying the clicked MenuItem:
                    int itemId = menuItem.getItemId();

                    // Navigating to the corresponding fragment:
                    if (itemId == R.id.drawer_home)
                        navController.navigate(R.id.characterList);
                    else if (itemId == R.id.drawer_settings)
                        navController.navigate(R.id.settingsFragment);

                    // Closing the DrawerLayout:
                    binding.drawerLayout.closeDrawers();

                    return true;
                });
    }


    /**
     * Inflates the default menu in the ToolBar.
     *
     * @param menu The menu that is being inflated (usually menu.xlm).
     * @return True.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    /**
     * Called when an item in the options menu is selected, or the ToggleButton is selected.
     *
     * @param item The menu item that was selected.
     * @return True if the menu item click was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        // Managing when ToggleButton pressed:
        if (toggle != null && toggle.onOptionsItemSelected(item))
            return true;

        // Otherwise, some MenuItem has been selected. Getting the selected item ID:
        int id = item.getItemId();

        // Managing the selected item (in this case, "About" option only):
        if (id == R.id.action_about) {

            // Finding the version name of the app:
            String versionName;
            try {
                versionName = getPackageManager()
                        .getPackageInfo(getPackageName(), 0)
                        .versionName;
            } catch (PackageManager.NameNotFoundException e) {
                versionName = getString(R.string.unknown);
            }

            // Building the message text:
            String message = String.format("%s %s. %s %s.",
                    getString(R.string.developedBy),
                    getString(R.string.developer_name),
                    getString(R.string.version),
                    versionName);

            // Showing the AlertDialog:
            new AlertDialog.Builder(this)
                    .setTitle(R.string.about)
                    .setIcon(R.drawable.supermario_icon)
                    .setMessage(message)
                    .setPositiveButton(R.string.ok, null)
                    .show();

        }

        // Performing the default menu handling:
        return super.onOptionsItemSelected(item);
    }


    /**
     * Loads the characters list from the data source.
     *
     * @return A collection of Character objects (may be empty but not null).
     */
    @NonNull
    public ArrayList<Character> loadCharacters() {
        return Database.load(this);
    }


    /**
     * Launches the character detail screen by passing the character's information.
     * This method bundles the character's details into a 'Bundle' and navigates to the
     * CharacterDetailFragment.
     *
     * @param character The character whose details are to be displayed in the character detail screen.
     */
    public void launchCharacterDetail(Character character) {

        // Encapsulating the character's data in a Bundle object:
        Bundle bundle = new Bundle();
        bundle.putString("name", character.getName());
        bundle.putString("picture", character.getPicture());
        bundle.putString("description", character.getDescription());
        bundle.putString("detail", character.getDetail());
        bundle.putString("skills", character.getSkills());

        // Navigating onto the CharacterDetailFragment:
        navController.navigate(R.id.characterDetail, bundle);
    }


    /**
     * Refreshes specific UI elements which aren't affected for activity.getResources().updateConfiguration:
     */
    public void syncUILanguage() {

        // Done when UI is not inflated yet:
        if (binding == null)
            return;

        // Refreshing default menu:
        invalidateOptionsMenu();

        // Refreshing the NavigationLayout menu (flushing + reinflating):
        binding.navigationView.getMenu().clear();
        getMenuInflater().inflate(R.menu.drawer_menu, binding.navigationView.getMenu());

        // Refreshing the ToolBar text:
        ActionBar actionBar;
        if ((actionBar = getSupportActionBar()) != null)
            actionBar.setTitle(R.string.settingsTitle);

        // Refreshing the SettingsFragment layout (going up and reentering):
        navController.navigateUp();
        navController.navigate(R.id.settingsFragment);
    }


}