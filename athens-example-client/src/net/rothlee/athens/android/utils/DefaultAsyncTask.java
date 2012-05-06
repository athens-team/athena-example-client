package net.rothlee.athens.android.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public abstract class DefaultAsyncTask<Result> extends
		AsyncTask<Object, Object, Result> {

	private final ProgressDialog dialog;

	public DefaultAsyncTask(ProgressDialog dialog) {
		this.dialog = dialog;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		try {
			dialog.show();
		} catch (Exception e) {
		}
	}

	@Override
	protected abstract Result doInBackground(Object... params);

	protected void onPostExecute(Result result) {
		try {
			dialog.dismiss();
		} catch (Exception e) {
		}

	};

}
