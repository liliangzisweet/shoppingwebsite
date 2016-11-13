package com.llz.project.meta;

public class Content {

	private int id;
	private int price; //当前价格
	private String title; //标题
	private byte[] icon; //图片
	private String summary; //摘要
	private byte[] text; //正文
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public byte[] getIcon() {
		return icon;
	}
	public void setIcon(byte[] icon) {
		this.icon = icon;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public byte[] getText() {
		return text;
	}
	public void setText(byte[] text) {
		this.text = text;
	}
	
	
}
