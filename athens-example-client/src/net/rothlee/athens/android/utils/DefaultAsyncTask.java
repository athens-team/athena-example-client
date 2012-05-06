/*
* Copyright 2012 Athens Team
 *
 * This file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package net.rothlee.athens.android.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;

/**
 * @author roth2520@gmail.com
 * @param <Result>
 */
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
