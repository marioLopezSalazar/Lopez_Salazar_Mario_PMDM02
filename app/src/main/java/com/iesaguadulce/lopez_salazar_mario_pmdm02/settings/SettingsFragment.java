package com.iesaguadulce.lopez_salazar_mario_pmdm02.settings;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.MainActivity;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.R;
import java.util.Locale;

/**
 * Fragment to handle the application's custom configuration by the user.
 * Utilizes SharedPreferences to store and retrieve user preferences.
 *
 * @author Mario López Salazar
 * @version 1.1
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    /**
     * Called during onCreate to supply the preferences.
     * Inflates the preferences view and sets listeners for the controls.
     *
     * @param savedInstanceState If the fragment is being re-created from a previous saved state, this is the state.
     * @param rootKey            If non-null, this preference fragment should be rooted at the PreferenceScreen with this key.
     */
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        // Inflating:
        setPreferencesFromResource(R.xml.preferences, rootKey);

        // 'SWITCH TO SPANISH / CAMBIAR A INGLÉS':
        SwitchPreferenceCompat languageSwitch = findPreference("languageEnglish");
        if (languageSwitch != null) {
            // Setting a listener that calls a static method to change the application's language:
            languageSwitch.setOnPreferenceChangeListener(
                    (preference, newValue) -> {
                        if ((boolean) newValue)
                            return changeLanguage((MainActivity) requireActivity(), "en");
                        else
                            return changeLanguage((MainActivity) requireActivity(), "es");
                    });
        }
    }


    /**
     * Configures the language of the app.
     *
     * @param activity Running activity.
     * @param language Language code.
     * @return True.
     */
    public static boolean changeLanguage(MainActivity activity, String language) {
        // Setting the language for this instance of the Java VM:
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        // Setting the language int the activity:
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        activity.getResources().updateConfiguration(configuration, activity.getResources().getDisplayMetrics());

        // Refreshing specific user interface which have not been affected for the previous updateConfiguration:
        activity.syncUILanguage();

        return true;
    }


    /**
     * Called when the Fragment is visible to the user.
     * Used to arrange the ToolBar text.
     */
    @Override
    public void onStart() {
        super.onStart();
        AppCompatActivity activity;
        ActionBar actionBar;
        if ((activity = (AppCompatActivity) getActivity()) != null && (actionBar = activity.getSupportActionBar()) != null)
            actionBar.setTitle(R.string.settingsTitle);
    }

}