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
package com.eincs.athens.android.utils;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * @author roth2520@gmail.com
 */
public class ProgressDialogs {

	public static ProgressDialog createDialog(Context context) {
		ProgressDialog result = new ProgressDialog(context);
		result.setMessage("Please Wait...");
		result.show();
		return result;
	}
}
