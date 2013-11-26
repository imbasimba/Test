package test.packag;

import java.io.*;
import java.util.ArrayList;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.*;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class GetJSONData extends AsyncTask<String, Void, JSONObject> {
	private Context context;
	private View view;
	private ProgressDialog progressDialog;
	private final String URL = "http://www.tackthis.com/json/products.php?categoryId=5718&page=1&widgetId=1857";

	public GetJSONData(Context context, View view){
		this.context = context;
		this.view= view;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context, "Please wait...", "Retrieving data ...", true);

	}
	@Override
	protected JSONObject doInBackground(String... param) {
		InputStream is = null;
		String result = "";
		JSONObject jsonObject = null;

		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(URL);
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection "+e.toString());
		}

		//convert response to string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
		}catch(Exception e){
			Log.e("log_tag", "Error converting result "+e.toString());
		}

		//try parse the string to a JSON object
		try{
			jsonObject = new JSONObject(result);
		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return jsonObject;

	} 
	protected void onPostExecute(JSONObject jsonObject){
		JSONArray results;
		try {
			results = jsonObject.getJSONArray("results");
			ArrayList<String> nameList = new ArrayList<String>();
			ArrayList<String> priceList = new ArrayList<String>();
			ArrayList<String> imageURLs = new ArrayList<String>();

			for(int i=0;i < results.length();i++){						
				JSONObject product = results.getJSONObject(i);
				nameList.add(product.getString("productName"));
				priceList.add(product.getString("productPrice") + "$");
				JSONArray images = product.getJSONArray("productImages");
				JSONObject image = images.getJSONObject(0);
				imageURLs.add(image.getString("url_thumb"));
			}

			new DownloadImageTask(context, view, nameList, priceList, imageURLs).execute();
		} 
		catch (JSONException e1) {
			e1.printStackTrace();
		}
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}

	}
}
