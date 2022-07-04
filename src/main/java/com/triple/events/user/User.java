package com.triple.events.user;

public class User {
	private String userID;
	private int totalCount;
	
	
	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", totalCount=" + totalCount + "]";
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	
		
}
