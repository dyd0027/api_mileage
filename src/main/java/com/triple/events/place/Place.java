package com.triple.events.place;

public class Place {
	private String placeID;
	private String placeName;
	
	@Override
	public String toString() {
		return "Place [placeID=" + placeID + ", placeName=" + placeName + "]";
	}
	public String getPlaceID() {
		return placeID;
	}
	public void setPlaceID(String placeID) {
		this.placeID = placeID;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	
	
}
