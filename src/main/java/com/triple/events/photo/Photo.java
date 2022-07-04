package com.triple.events.photo;

public class Photo {
	private String reviewID;
	private String photoID;
	
	@Override
	public String toString() {
		return "Photo [reviewID=" + reviewID + ", photoID=" + photoID + "]";
	}
	public String getReviewID() {
		return reviewID;
	}
	public void setReviewID(String reviewID) {
		this.reviewID = reviewID;
	}
	public String getPhotoID() {
		return photoID;
	}
	public void setPhotoID(String photoID) {
		this.photoID = photoID;
	}
	
	
}
