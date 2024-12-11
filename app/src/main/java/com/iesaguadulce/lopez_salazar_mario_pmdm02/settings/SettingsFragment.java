package com.iesaguadulce.lopez_salazar_mario_pmdm02.settings;

import android.content.res.Configuration;
import android.os.Bundle;
import androidx.appcompat.app.*;
import androidx.preference.*;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.MainActivity;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.R;

import java.util.Locale;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

        //Inflado
        setPreferencesFromResource(R.xml.preferences, rootKey);

        SwitchPreferenceCompat languageSwitch = findPreference("languageEnglish");
        if(languageSwitch!= null){
            languageSwitch.setOnPreferenceChangeListener( (preference, newValue) -> {
                if( (boolean)newValue )
                    return changeLanguage((MainActivity) requireActivity(),"en");
                else
                    return changeLanguage((MainActivity) requireActivity(),"es");
            });
        }
    }

    public static boolean changeLanguage(MainActivity activity, String language) {
        //Cambiar el idioma de la app utilizando la configuración del sistema
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        //Configuración de la nueva Locale
        Configuration configuration = new Configuration();
        configuration.setLocale(locale);
        activity.getResources().updateConfiguration(configuration, activity.getResources().getDisplayMetrics());

        //Refrescamos la activity
        activity.refreshMenuLanguage();

        return true;
    }
    @Override
    public void onStart() {
        super.onStart();
        // Cambia el título del ActionBar
        AppCompatActivity activity;
        ActionBar actionBar;
        if( (activity = (AppCompatActivity) getActivity()) != null && (actionBar = activity.getSupportActionBar()) != null)
            actionBar.setTitle(R.string.settingsTitle);
    }


}