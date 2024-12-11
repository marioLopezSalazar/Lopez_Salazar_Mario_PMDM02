package com.iesaguadulce.lopez_salazar_mario_pmdm02.model;

import android.content.Context;
import java.util.*;
import android.util.Xml;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.R;
import org.xmlpull.v1.XmlPullParser;
import java.io.*;

public class Database {

    @NonNull
    public static ArrayList<Character> load(Context context){

        ArrayList<Character> characters = new ArrayList<>();

        try {
            // Abrir el archivo XML desde res/raw
            InputStream inputStream = context.getResources().openRawResource(R.raw.characters);
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, null);

            // Variables para los datos del personaje
            String name = null, picture = null, description = null, detail = null, skills = null;

            // Procesar el XML
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                String tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("character".equals(tagName)) {
                            // Resetear variables
                            name = picture = description = detail = skills = null;
                        } else if ("name".equals(tagName)) {
                            name = parser.nextText();
                        } else if ("picture".equals(tagName)) {
                            picture = parser.nextText();
                        } else if ("description".equals(tagName)) {
                            description = parser.nextText();
                        } else if ("detail".equals(tagName)) {
                            detail = parser.nextText();
                        } else if ("skills".equals(tagName)) {
                            skills = parser.nextText();
                        }
                        break;

                    case XmlPullParser.END_TAG:
                        if ("character".equals(tagName)) {
                            // Crear un nuevo Character y agregarlo a la lista
                            characters.add(new Character(name, picture, description, detail, skills));
                        }
                        break;
                }

                // Siguiente evento
                eventType = parser.next();
            }

            inputStream.close();
        } catch (Exception e) {
            Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
        }
        return characters;
    }
}
