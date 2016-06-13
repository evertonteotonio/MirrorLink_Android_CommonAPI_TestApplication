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
package com.carconnectivity.testapp;


import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.carconnectivity.testapp.views.BooloeanInputView;
import com.carconnectivity.testapp.views.HeaderView;
import com.carconnectivity.testapp.views.LastExecutedViewMultiline;
import com.carconnectivity.testapp.views.SuccesView;
import com.mirrorlink.android.commonapi.IDeviceStatusListener;
import com.mirrorlink.android.commonapi.IDeviceStatusManager;

public class MirrorLinkMicrophoneStatus extends BaseActivity {
	SuccesView microphoneEnabled= null;
	SuccesView callbackFired= null;
	LastExecutedViewMultiline callbackFiredList = null;
	
	Button clearCallbackList = null;
	
	
	BooloeanInputView enableMicrophone = null;
	Button sendMicrophoneStatus = null;

	IDeviceStatusManager deviceStatusManager = null;
			
	@Override
	protected void onResume()
	{
		deviceStatusManager = getMirrorLinkApplicationContext().registerDeviceStatusManager(this, mDeviceStatusListener);

		if (deviceStatusManager == null)
		{
			finish();
		}
		super.onResume();
	}
	@Override
	protected void onPause()
	{
		getMirrorLinkApplicationContext().unregisterDeviceStatusManager(this, mDeviceStatusListener);
		super.onPause();
	}
	

	IDeviceStatusListener mDeviceStatusListener = new IDeviceStatusListener.Stub() {
		@Override
		public void onNightModeChanged(final boolean nightMode) throws RemoteException {
		}
		
		@Override
		public void onMicrophoneStatusChanged(final boolean micInput) throws RemoteException {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					callbackFiredList.setNow();
					microphoneEnabled.setValue(micInput);
					callbackFired.setValue(true);
				}
			});
		}
		
		@Override
		public void onDriveModeChange(final boolean driveMode) throws RemoteException {
		}
	};
	

	ScrollView buildUI()
	{
		ScrollView scrollView = new ScrollView(this);
		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		
		enableMicrophone = new BooloeanInputView(this, " Mic input enabled", false);
		layout.addView(enableMicrophone);
		
		sendMicrophoneStatus = new Button(this);
		sendMicrophoneStatus.setText("Send Set Open Microphone command");
		sendMicrophoneStatus.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
					try
					{
						deviceStatusManager.setMicrophoneOpen(enableMicrophone.getValue(), enableMicrophone.getValue());
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
			}
		});
		
		layout.addView(sendMicrophoneStatus);
		
		microphoneEnabled= new SuccesView(this,"Microphone enabled",false);
		layout.addView(microphoneEnabled);
		

		layout.addView(new HeaderView(this,"callbacks"));
		callbackFired= new SuccesView(this,"Callback fired",false);
		layout.addView(callbackFired);
		callbackFiredList = new LastExecutedViewMultiline(this);
		layout.addView(callbackFiredList);
		clearCallbackList = new Button(this);
		layout.addView(clearCallbackList);
		clearCallbackList.setText("Clear callback logs");
		clearCallbackList.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				callbackFiredList.clear();
				callbackFired.setValue(false);
			}
		});
		
		
		

		scrollView.addView(layout);
		return scrollView;
	}

}
