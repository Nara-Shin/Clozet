package com.hojung.nfc;

import java.nio.charset.Charset;
import java.util.ArrayList;

import com.hojung.nfc.interfaces.OnHojungNFCListener;
import com.hojung.nfc.model.NfcModel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.FeatureInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.Parcelable;
import android.util.Log;

public class HojungNFCCheckingLibrary {
	static final String TAG = "HojungNFCLibrary";
	private Context context;
	private OnHojungNFCListener listener;
	Activity activity;
	private NfcAdapter nfcAdapter;
	NfcModel model;

	public HojungNFCCheckingLibrary(Context context) {
		this(context, null);
	}

	public HojungNFCCheckingLibrary(Context context, String packageName) {
		this.context = context;
		this.activity = activity;
		nfcAdapter = NfcAdapter.getDefaultAdapter(context);
		checkNFCAvailable(packageName, context);

	}

	public NfcModel getNFCData(Intent intent) {
		Tag myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
		Ndef ndefTag = Ndef.get(myTag);
		model = new NfcModel();
		if (ndefTag != null) {
			int size = ndefTag.getMaxSize();
			model.setSize(size);
			boolean writable = ndefTag.isWritable();
			model.setWritable(writable);
			String type = ndefTag.getType();
			model.setTagType(type);
			String id = byteArrayToHexString(myTag.getId());
			model.setId(id);
		}

		Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
		if (messages == null) {
			String errorMessage = "nfc message is empty";
			if (listener != null)
				listener.onError(errorMessage);
			return null;
		} else {
			for (int i = 0; i < messages.length; i++) {
				setReadTagData((NdefMessage) messages[i]);
			}
		}
		return model;

	}

	
	
	
	public void checkNFCAvailable(String packageName, Context context) {

		try {
			if (!packageName.equals(null) && !context.equals(null)) {
				if (!isContainNFCPermission(packageName, context)) {
					Log.d(TAG, "개발자는 퍼미션을 잊어버린것 같다.");
					return;
				}
				if (!NfcAdapter.getDefaultAdapter(context).isEnabled()) {
					Log.d(TAG, "NFC가 꺼져있다.");
					if (listener != null) {
						listener.onDeviceTurnOffNFC();
					}
				}
				if (!isFeatureAvailable(context, PackageManager.FEATURE_NFC)) {
					Log.d(TAG, "NFC가 지원되지 않는 단말기 인것 같다.");
					if (listener != null) {
						listener.onDeviceNotSupportNFC();
					}
				}

			}

		} catch (NameNotFoundException e) {
			Log.d(TAG, "개발자는 팩키지 이름을 잘못 입력한것 같다.");
		} catch (Exception e) {
			Log.d(TAG, "알 수 없는 오류 : " + e.getMessage());
		}

	}

	public void setOnHojungNFCListener(OnHojungNFCListener listener) {
		this.listener = listener;
	}

	private boolean isContainNFCPermission(String packageName, Context context) throws NameNotFoundException {

		PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName,
				PackageManager.GET_PERMISSIONS);
		String[] permissions = packageInfo.requestedPermissions;
		ArrayList<String> array = new ArrayList<String>();
		for (String permission : permissions) {
			array.add(permission);
		}
		if (!array.contains(HojungConstants.NFC_PERMISSION)) {
			return false;
		}
		if (!array.contains(HojungConstants.BIND_NFC_SERVICE_PERMISSION)) {
			return false;
		}
		return true;

	}

	private boolean isFeatureAvailable(Context context, String feature) {
		PackageManager packageManager = context.getPackageManager();
		FeatureInfo[] featuresList = packageManager.getSystemAvailableFeatures();
		for (FeatureInfo f : featuresList) {
			if (f.name != null && f.name.equals(feature)) {
				return true;
			}
		}
		return false;
	}

	private String byteArrayToHexString(byte[] byteArray) {
		int len = byteArray.length;
		String data = new String();

		for (int i = 0; i < len; i++) {
			data += Integer.toHexString((byteArray[i] >> 4) & 0xf);
			data += Integer.toHexString(byteArray[i] & 0xf);
		}
		return data;
	}

	private void setReadTagData(NdefMessage ndefmsg) {
		if (ndefmsg == null) {
			String errorMessage = "nfc message is empty";
			if (listener != null)
				listener.onError(errorMessage);
			return;
		}
		String msgs = "";
		msgs += ndefmsg.toString() + "\n";
		NdefRecord[] records = ndefmsg.getRecords();
		for (NdefRecord rec : records) {
			byte[] payload = rec.getPayload();
			String textEncoding = "UTF-8";
			if (payload.length > 0)
				textEncoding = (payload[0] & 0200) == 0 ? "UTF-8" : "UTF-16";

			Short tnf = rec.getTnf();
			model.setTnf(tnf);
			String type = String.valueOf(rec.getType());
			model.setRecordType(type);
			String payloadStr = new String(rec.getPayload(), Charset.forName(textEncoding));
			model.setPayloadStr(payloadStr);
		}
	}

}
