package net.rothlee.athens.android;

public class WritingItem {

	String imageID;
	String name;
	String content;
	String date;
	String ID;
	int number;
	
	WritingItem(String imageID, String name, String content, String date, String ID, int no) {
//		WritingItem(String name, String content, String date) {
		this.imageID = imageID;
		this.name = name;
		this.content = content;
		this.date = date;
		this.ID = ID;
		this.number = no;
	}
	
	WritingItem(String imageID, String name, String content, String date, int no) {
//		WritingItem(String name, String content, String date) {
		this.imageID = imageID;
		this.name = name;
		this.content = content;
		this.date = date;
		this.ID = null;
		this.number = no;
	}

	WritingItem(String imageID, String name, String content, int no) {
//		WritingItem(String name, String content, String date) {
		this.imageID = imageID;
		this.name = name;
		this.content = content;
		this.date = null;
		this.ID = null;
		this.number = no;
	}
	
	WritingItem(String imageID, String name, String content) {
//		WritingItem(String name, String content, String date) {
		this.imageID = imageID;
		this.name = name;
		this.content = content;
		this.date = null;
		this.ID = null;
		this.number = 0;
	}


}
