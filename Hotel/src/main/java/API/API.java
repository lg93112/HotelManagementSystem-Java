package API;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class API {
    private final String YELP_USER_AGENT = "Bearer _BaNlKEH-hPFgh57xEqG6wD9Y7PXw0kkb8fsZrmmR-QRRA" +
            "KcejYX6UKqw5zfOcEUn4TVj36eMqK4g1uX6dQaBPKL8vf9A_b-ZaweGdTTwbRKfGSg3CSQQbHuuOHtW3Yx";
    public int responseCode;
    public List<String[]> Get_yelp(String term, String qlocation, String categories, int limit) throws Exception {

        String url = "";
        if(categories == null){
            url = "https://api.yelp.com/v3/businesses/search?term="+term+"&location="+qlocation
                    +"&limit="+Integer.toString(limit)+"&sort_by=rating";
        }
        else{
            url = "https://api.yelp.com/v3/businesses/search?term="+term+"&location="+qlocation
                    +"&categories="+categories+"&limit="+Integer.toString(limit)+"&sort_by=rating";
        }
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("Authorization", YELP_USER_AGENT);

        responseCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        JSONObject myResponse = new JSONObject(response.toString());
        //int num = myResponse.getInt("total");
        JSONArray arrayofbus = myResponse.getJSONArray("businesses");
        //print result
        String[] img_url = new String[limit];
        String[] address = new String[limit];
        String[] name = new String[limit];
        String[] rating = new String[limit];
        for(int i = 0; i < limit; i++){
            JSONObject curr = arrayofbus.getJSONObject(i);
            img_url[i] = curr.getString("image_url");
            name[i] = curr.getString("name");
            rating[i] = Double.toString(curr.getDouble("rating"));
            JSONObject location = curr.getJSONObject("location");
            address[i] = location.getString("address1") + ", "+
                    location.getString("city") + ", "+ location.getString("state") + ", "+ location.get("zip_code");
        }
        List<String[]> res = new LinkedList<>();
        res.add(img_url);
        res.add(name);
        res.add(address);
        res.add(rating);
        return res;
    }

}
