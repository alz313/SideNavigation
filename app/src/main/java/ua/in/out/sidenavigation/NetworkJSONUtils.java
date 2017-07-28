package ua.in.out.sidenavigation;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class NetworkJSONUtils {

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }


    /**
     * This method parses JSON from a web response and returns an array of MenuItems
     * <p/>
     *
     * @param menuJsonStr JSON response from server
     * @return Array of MenuItems
     * @throws JSONException If JSON data cannot be properly parsed
     */
    public static ArrayList<HashMap<String, String>> getMenuItemsFromJson(Context context, String menuJsonStr) throws JSONException {

        /* MenuItems array to hold each menu item */
        ArrayList<HashMap<String, String>> parsedMenuData = new ArrayList<>();

        JSONObject menuJson = new JSONObject(menuJsonStr);

        JSONArray menuArray = menuJson.getJSONArray(context.getString(R.string.menu));


        for (int i = 0; i < menuArray.length(); i++) {
            HashMap<String, String> menuItemMap = new HashMap<>();

            /* Get the JSON object representing the MenuItem */
            JSONObject menuItemJSON = menuArray.getJSONObject(i);

            menuItemMap.put(context.getString(R.string.name), menuItemJSON.getString(context.getString(R.string.name)));
            menuItemMap.put(context.getString(R.string.function), menuItemJSON.getString(context.getString(R.string.function)));
            menuItemMap.put(context.getString(R.string.param), menuItemJSON.getString(context.getString(R.string.param)));

            parsedMenuData.add(menuItemMap);
        }

        return parsedMenuData;
    }

}
