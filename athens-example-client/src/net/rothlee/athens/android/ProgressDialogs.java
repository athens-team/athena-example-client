package net.rothlee.athens.android;

import android.app.ProgressDialog;
import android.content.Context;

public class ProgressDialogs {

	public static ProgressDialog createDialog(Context context) {
		ProgressDialog result = new ProgressDialog(context);
		result.setMessage("Please Wait...");
		result.show();
		return result;
	}
}
