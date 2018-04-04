package xyz.quinntian.po;

public class News {
	private String Title;
	private String description;
	private String PicUrl;	//图片链接（由系统生成）
	private String Url;
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	@Override
	public String toString() {
		return "News [Title=" + Title + ", description=" + description + ", PicUrl=" + PicUrl + ", Url=" + Url + "]";
	}
	
}
