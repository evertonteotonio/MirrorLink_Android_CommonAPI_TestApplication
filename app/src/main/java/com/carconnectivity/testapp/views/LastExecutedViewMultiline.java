/*
 * Copyright Car Connectivity Consortium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * You may decide to give the Car Connectivity Consortium input, suggestions
 * or feedback of a technical nature which may be implemented on the
 * Car Connectivity Consortium products (“Feedback”).
 *
 * You agrees that any such Feedback is given on non-confidential
 * basis and Licensee hereby waives any confidentiality restrictions
 * for such Feedback. In addition, Licensee grants to the Car Connectivity Consortium
 * and its affiliates a worldwide, non-exclusive, perpetual, irrevocable,
 * sub-licensable, royalty-free right and license under Licensee’s copyrights to copy,
 * reproduce, modify, create derivative works and directly or indirectly
 * distribute, make available and communicate to public the Feedback
 * in or in connection to any CCC products, software and/or services.
 */
package com.carconnectivity.testapp.views;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.carconnectivity.testapp.R;

public class LastExecutedViewMultiline  extends LinearLayout{
	TextView tTimeStamp = null;
	List<Date> updateList;

	
	public LastExecutedViewMultiline(Context context) {
		super(context);
		View.inflate(context, R.layout.view_last_executed_multiline, this);
		
		tTimeStamp = (TextView) findViewById(R.id.tTiemStamp);
		tTimeStamp.setText("N/A");
		updateList = new ArrayList<Date>();
	}
	public void clear()
	{
		updateList.clear();
		tTimeStamp.setText("N/A");
	}
	
	public void setNow()
	{
		Date d = new Date();
		updateList.add(d);
		if (updateList.size() >5)
		{
			updateList.remove(0);
		}
		tTimeStamp.setText("");
		for (Date date : updateList) 
		{
			tTimeStamp.setText(tTimeStamp.getText() + date.toString()+"\n");
		}		
	}
	
	public String getValue()
	{
		return tTimeStamp.getText().toString();
	}
	
	public long[] getRawValues()
	{
		long[]  list = new long[updateList.size()]; 
		for (int i=0; i<updateList.size();i++)
		{
			list[i] = updateList.get(i).getTime();
		}	
		
		return list;
	}
	public void setRawValue(long[]  newValue)
	{	
		for (int i=0; i<newValue.length; i++)
		{
			updateList.add(new Date(newValue[i]));
		}	
	}
}


