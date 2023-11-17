package com.project.Model;

import org.springframework.data.annotation.Id;


public class Subcomments {
	
	@Id
	private String id;
	private String parentCommentId;
    private String text;
    private String usrName;
    private int flags;
    private String profilePicture;

    // getters and setters

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParentCommentId() {
		return parentCommentId;
	}

	

	public void setParentCommentId(String id2) {
		// TODO Auto-generated method stub
		this.parentCommentId = id2;
	}

	public String getUsrName() {
		return usrName;
	}

	public void setUsrName(String userName) {
		this.usrName = userName;
	}

	public int getFlags() {
		return flags;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

}
