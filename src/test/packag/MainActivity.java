package test.packag;


import android.os.Bundle;
import android.view.Window;
import android.app.Activity;

public class MainActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		new GetJSONData(this, this.findViewById(android.R.id.content)).execute(); 
	}
}

