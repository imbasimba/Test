package test.packag;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomList extends ArrayAdapter<String>{

	private Context context;
	private ArrayList<String> productNameList;
	private ArrayList<String> priceList;
	private ArrayList<Bitmap> imageList;
	public CustomList(Context context, ArrayList<String> productNameList, ArrayList<String> priceList, ArrayList<Bitmap> imageList) {
		super(context, R.layout.list_single, productNameList);
		this.context = context;
		this.productNameList = productNameList;
		this.priceList = priceList;
		this.imageList = imageList;

	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View rowView= inflater.inflate(R.layout.list_single, null, true);
		TextView productName = (TextView) rowView.findViewById(R.id.name);
		TextView productPrice = (TextView) rowView.findViewById(R.id.price);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		productName.setText(productNameList.get(position));
		productPrice.setText(priceList.get(position));
		imageView.setImageBitmap(imageList.get(position));

		return rowView;
	}
	

}