package com.iesaguadulce.lopez_salazar_mario_pmdm02;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.*;
import android.view.*;
import androidx.fragment.app.Fragment;
import androidx.navigation.*;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.*;
import androidx.preference.PreferenceManager;

import com.iesaguadulce.lopez_salazar_mario_pmdm02.databinding.ActivityMainBinding;
import java.util.ArrayList;

/**
 * MainActivity is the entry point of the application and is responsible for inflating
 * the main view and configuring the navigation components.
 * This activity inflates the layout that serves as the main user interface and sets up
 * the navigation elements, including the ToolBar and navigation controller, allowing users
 * to navigate through different fragments and destinations within the app.
 */
public class MainActivity extends AppCompatActivity {

    /** NavController object for managing navigation within the application. */
    private NavController navController;

    private ActivityMainBinding binding;

    private ActionBarDrawerToggle toggle;
    AppBarConfiguration configuration;

    /**
     * Initializes the activity. This method executes several actions:
     *  - Inflates the ActivityMain layout.
     *  - Sets the ToolBar as the application's ActionBar.
     *  - Links the ToolBar to the navigation controller.
     *
     * @param savedInstanceState If the activity is being re-initialized
     * after previously being shut down then this Bundle contains the data
     * it most recently supplied in onSaveInstanceState(Bundle).
     * Otherwise it is null.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // Calling the superclass' method with the same name:
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Loading and establishing language:
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean english = preferences.getBoolean("languageEnglish", false);
        if(english)
            SettingsFragment.changeLanguage(this,"en");
        else
            SettingsFragment.changeLanguage(this,"es");

        //Inflating the main view:
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Establishing the ToolBar as the ActionBar of the app:
        setSupportActionBar(binding.toolbar);


        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(binding.fragmentsContainer.getId());
        if(navHostFragment != null){
            //Getting the NavController from the FragmentContainer:
            navController = NavHostFragment.findNavController(navHostFragment);

            // Setting up the Toolbar with the NavController (configuration avoids placing the home fragment in the stack):
            configuration = new AppBarConfiguration
                    .Builder(R.id.characterList)
                    .setOpenableLayout(binding.drawerLayout)
                    .build();
            NavigationUI.setupActionBarWithNavController(this, navController, configuration);
        }

        configureToggle();
        navController.addOnDestinationChangedListener(this::onChangeFragment);

        configureNavigationMenu();
        // Configurar el icono del menú en la ActionBar (en este caso la toolbar)
        // Redundante, entra en conflicto con lo anterior, por eso pongo setOpenableLayout.
        /*if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
    }

    private void configureNavigationMenu() {
        binding.navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    int itemId = menuItem.getItemId();
                    if (itemId == R.id.drawer_home)
                        navController.navigate(R.id.characterList);
                    else if (itemId == R.id.drawer_settings)
                        navController.navigate(R.id.settingsFragment);

                    //Close the Drawer:
                    binding.drawerLayout.closeDrawers();

                    return true;
        });
    }


    /**
     * Configures the "back" button of the Toolbar with the navigation stack.
     * @return True if navigation back was successful; False otherwise.
     */
    @Override
    public boolean onSupportNavigateUp() {
        /* Calling the navigateUp() method of the NavController.
           When not successful, call the superclass' method with the same name:  */
        return
                NavigationUI.navigateUp(navController, configuration)
                        || super.onSupportNavigateUp();
    }

    void configureToggle() {
        // Configurar el Toggle
        toggle = new ActionBarDrawerToggle(this, binding.drawerLayout,
                R.string.open_navigation_drawer, R.string.close_navigation_drawer);

        //Vincular el DrawerLayout con el toogle
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();  //Sincroniza el estado inicial
    }

    void onChangeFragment(NavController navController, NavDestination navDestination, Bundle bundle) {
        if(toggle == null)
            return;

        toggle.setDrawerIndicatorEnabled( navDestination.getId() == R.id.characterList );
    }

    /**
     * Loads the characters information from the data source.
     * @return A collection of Character objects (may be empty but not null).
     */
    @NonNull
    ArrayList<Character> loadCharacters(){
        return Database.load(this);
    }


    /**
     * Launches the character detail screen by passing the character's information.
     * This method bundles the character's details into a `Bundle` and navigates to the
     * CharacterDetailFragment.
     *
     * @param character The character whose details are to be displayed in the character detail screen.
     */
    void launchCharacterDetail(Character character) {

        // Encapsulating the character's data in a Bundle object:
        Bundle bundle = new Bundle();
        bundle.putString("name", character.getName());
        bundle.putString("picture", character.getPicture());
        bundle.putString("description", character.getDescription());
        bundle.putString("detail", character.getDetail());
        bundle.putString("skills", character.getSkills());

        navController.navigate(R.id.characterDetail, bundle);
    }


    /**
     * Inflates the menu in the ToolBar.
     * This method is responsible for inflating the menu layout into the ToolBar
     * so that it can display the menu options.
     *
     * @param menu The menu that is being inflated. This is the menu object in which the items are added.
     * @return True.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    /**
     * Called when an item in the options menu is selected.
     * This method handles item clicks from the "about" option, showing an AlertDialog.
     *
     * @param item The menu item that was selected.
     * @return True if the menu item click was handled, false otherwise.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){

        // Managing when Toggle button pressed:
        if (toggle != null && toggle.onOptionsItemSelected(item))
            return true;

        // Getting the selected item ID:
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

        // Perform the default menu handling:
        return super.onOptionsItemSelected(item);
    }

    public void refreshMenuLanguage() {

        if(binding == null)
            return;

        //Menú por defecto:
        invalidateOptionsMenu();

        //Menú del navigationDrawer:
        binding.navigationView.getMenu().clear();
        getMenuInflater().inflate(R.menu.drawer_menu, binding.navigationView.getMenu());

        //Texto de la Toolbar:
        getSupportActionBar().setTitle(R.string.settingsTitle);

        navController.navigateUp();
        navController.navigate(R.id.settingsFragment);


    }
}