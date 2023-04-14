package io.getarrays.fileuploadanddownload.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="files")
public class File {
	private String filename;
    private String contentType;
    private byte[] data;
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
}
