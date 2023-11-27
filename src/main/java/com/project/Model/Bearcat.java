package com.project.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "bearcats")
public class Bearcat {
	
	@Id	 
	private String mail;
    private String usrName;
    private String fname;
    private String lname;
    private String password;
    private String profilePictureURL;
    private String profilePictureFileName;
    private int flags;
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public String getUsrName() {
		return usrName;
	}
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getProfilePictureURL() {
		return profilePictureURL;
	}
	public void setProfilePictureURL(String profilePictureURL) {
		this.profilePictureURL = profilePictureURL;
	}
	public String getProfilePictureFileName() {
		return profilePictureFileName;
	}
	public void setProfilePictureFileName(String profilePictureFileName) {
		this.profilePictureFileName = profilePictureFileName;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}
    
    
  

}
