package com.hojung.nfc.model;

public class NfcModel {
	private int size;
	private boolean writable;
	private String tagType;
	private String id;
	private short tnf;
	private String RecordType;
	private String payloadStr;
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public boolean isWritable() {
		return writable;
	}
	public void setWritable(boolean writable) {
		this.writable = writable;
	}
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public short getTnf() {
		return tnf;
	}
	public void setTnf(short tnf) {
		this.tnf = tnf;
	}
	
	public String getRecordType() {
		return RecordType;
	}
	public void setRecordType(String recordType) {
		RecordType = recordType;
	}
	public String getPayloadStr() {
		return payloadStr;
	}
	public void setPayloadStr(String payloadStr) {
		this.payloadStr = payloadStr;
	}
	
	
	

}
