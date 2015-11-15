package com.hojung.nfc.interfaces;

public interface OnHojungNFCListener {
	public void onSend();
	public void onReceive();
	public void onDeviceNotSupportNFC();
	public void onDeviceTurnOffNFC();
	public void onError(String errorMessage);

}
