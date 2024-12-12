package com.iesaguadulce.lopez_salazar_mario_pmdm02.model;

import android.content.Context;
import android.util.Xml;
import android.widget.Toast;
import androidx.annotation.NonNull;
import org.xmlpull.v1.XmlPullParser;
import java.io.InputStream;
import java.util.ArrayList;
import com.iesaguadulce.lopez_salazar_mario_pmdm02.R;

/**
 * Class responsible for reading and interpreting character information
 * from an XML file and generating a collection of Character objects.
 *
 * @author Mario López Salazar
 * @version 1.1
 */
public class Database {


    /**
     * Identifier for the XML file containing character information. Default: res/raw/characters.xml
     */
    public static final int CHARACTERS_DATABASE_FILE = R.raw.characters;


    /**
     * Loads character information from an XML file, interprets it,
     * and generates a collection of Character objects.
     *
     * @param context The application context used to access the XML file.
     * @return A collection of Character objects.
     */
    @NonNull
    public static ArrayList<Character> load(Context context) {

        ArrayList<Character> characters = new ArrayList<>();

        try {
            // Opening XML file from res/raw:
            InputStream inputStream = context.getResources().openRawResource(CHARACTERS_DATABASE_FILE);

            // Configuring the XML text parser:
            XmlPullParser parser = Xml.newPullParser();
            parser.setInput(inputStream, null);

            // Variables for storing character data:
            String name = null, picture = null, description = null, detail = null, skills = null;

            // First event:
            int eventType = parser.getEventType();

            // Processing the XML file:
            while (eventType != XmlPullParser.END_DOCUMENT) {

                // Tag name:
                String tagName = parser.getName();

                switch (eventType) {

                    // Starts processing a character:
                    case XmlPullParser.START_TAG:
                        switch (tagName) {
                            case "name":
                                name = parser.nextText();
                                break;
                            case "picture":
                                picture = parser.nextText();
                                break;
                            case "description":
                                description = parser.nextText();
                                break;
                            case "detail":
                                detail = parser.nextText();
                                break;
                            case "skills":
                                skills = parser.nextText();
                                break;
                        }
                        break;

                    // Ends processing a character:
                    case XmlPullParser.END_TAG:
                        if ("character".equals(tagName))
                            characters.add(new Character(name, picture, description, detail, skills));
                        break;
                }

                // Next event:
                eventType = parser.next();
            }

            // Closing XML file:
            inputStream.close();

        } catch (Exception e) {
            Toast.makeText(context, R.string.load_error, Toast.LENGTH_SHORT).show();
        }

        return characters;
    }
}
