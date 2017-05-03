/**
 * Created by theasianpianist on 5/1/17.
 */

import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import org.json.*;
import java.io.*;
import org.apache.commons.io.FileUtils;


public class Crawler {

    public static void getData() {
        for (int i = 1; i <= 381; i++){
            try {
                URL url = new URL("http://www.iwf.net/wp-content/themes/iwf/results_export.php?event=" + i);
                File file = new File("data/result" + i + ".csv");
                URLConnection connection = url.openConnection();
                connection.connect();
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                String html = "";
                while ((inputLine = in.readLine()) != null) {
                    html += inputLine;
                }
                Document doc = Jsoup.parse(html);
                String rawJson = "{\"data\": ";
                rawJson += doc.select("#txt").text();
                rawJson += "}";
                try {
                    JSONObject output = new JSONObject(rawJson);
                    JSONArray array = output.getJSONArray("data");
                    String csv = CDL.toString(array);
                    FileUtils.writeStringToFile(file, csv);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
