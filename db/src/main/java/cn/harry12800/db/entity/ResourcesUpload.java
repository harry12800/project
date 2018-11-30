package cn.harry12800.db.entity;

/**
 * 
 * @author LL159127
 *
 */
public class ResourcesUpload {
	private Long id;
	private String fileName;
	private String uploadName;
	private String fileUrl;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUploadName() {
		return uploadName;
	}

	public void setUploadName(String uploadName) {
		this.uploadName = uploadName;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	@Override
	public String toString() {
		return "ResourcesUpload [id=" + id + ", fileName=" + fileName + ", uploadName=" + uploadName + ", fileUrl="
				+ fileUrl + "]";
	}

}
