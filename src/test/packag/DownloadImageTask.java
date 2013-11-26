package test.packag;

import java.io.InputStream;
import java.util.ArrayList;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class DownloadImageTask extends AsyncTask<String, Void, ArrayList<Bitmap>> {
	private Context context;
	private View view;
	private ProgressDialog progressDialog;
	private ArrayList<String> nameList;
	private ArrayList<String> priceList;
	private ArrayList<String> imageURLs;

	public DownloadImageTask(Context context, View view, ArrayList<String> nameList, ArrayList<String> priceList, ArrayList<String> imageURLs) {
		this.context = context;
		this.view= view;
		this.nameList = nameList;
		this.priceList = priceList;
		this.imageURLs = imageURLs;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		progressDialog = ProgressDialog.show(context, "Please wait...", "Downloading images...", true);
	}
	
	protected ArrayList<Bitmap> doInBackground(String... urls) {
		ArrayList<Bitmap> images = new ArrayList<Bitmap>();
		for(int i = 0; i < imageURLs.size(); i++){
			String url = "http:"+imageURLs.get(i);
			Bitmap image = null;
			try {
				InputStream in = new java.net.URL(url).openStream();
				image = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			images.add(image);
		}
		return images;
	}

	protected void onPostExecute(ArrayList<Bitmap> images) {
		ListView listView = (ListView)view.findViewById(R.id.listViewDresses);
		CustomList arrayAdapter = new CustomList(context, nameList, priceList, images);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast toast = Toast.makeText(context, "I just took my driver's licence! WOHOO!", Toast.LENGTH_SHORT);
				toast.show();
				
			}
		});
		listView.setAdapter(arrayAdapter);
		if (progressDialog.isShowing()) {
			progressDialog.dismiss();
		}
	}
}