package com.project.Model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "comments")
public class Comments {
    @Id
    private String id;
    private String projectId;
    private String text;
    private List<Subcomments> replies;
    private String usrName;
    private int flags;
    
    private String profilePicture;

    // getters and setters

    public String getProfilePicture() {
        return profilePicture;
    }

    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProjectId() {
		return projectId;
	}
	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Subcomments> getReplies() {
		return replies;
	}
	public void setReplies(List<Subcomments> replies) {
		this.replies = replies;
	}
	public String getUsrName() {
		return usrName;
	}
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}

	

	public void setProfilePicture(String encodeToString) {
		// TODO Auto-generated method stub
		this.profilePicture = encodeToString;
		
	}



    
}
