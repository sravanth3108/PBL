
package com.project.Model;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "postProjects")
public class PostProjectsModel {
	@Id
	private String projName;
	private String projDesc;
	private String projDescfull;
	private String projReq;
	private String email;
	private String phone;
	private String projType;
	private int projExp;
	private int flags;
	private String expiryDate;
	private String publishedDate;
	private List<Requests> requests;
	private int requestCount;
	private String keywords;
	private String userMail;
	private char approved;
	private String usrName;
	private String likes;
	private String dislikes;
	
		
	
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public int getRequestCount() {
		return requestCount;
	}
	public void setRequestCount(int requestCount) {
		this.requestCount = requestCount;
	}
	public List<Requests> getRequests() {
		return requests;
	}
	public void setRequests(List<Requests> requests) {
		this.requests = requests;
	}
	public String getPublishedDate() {
		return publishedDate;
	}
	public void setPublishedDate(String publishedDate) {
		this.publishedDate = publishedDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public int getFlags() {
		return flags;
	}
	public void setFlags(int flags) {
		this.flags = flags;
	}
	public int getProjExp() {
		return projExp;
	}
	public void setProjExp(int projExp) {
		this.projExp = projExp;
	}
	public String getProjType() {
		return projType;
	}
	public void setProjType(String projType) {
		this.projType = projType;
	}
	public String getProjName() {
		return projName;
	}
	public String getProjDescfull() {
		return projDescfull;
	}
	public void setProjDescfull(String projDescfull) {
		this.projDescfull = projDescfull;
	}
	public void setProjName(String projName) {
		this.projName = projName;
	}
	public String getProjDesc() {
		return projDesc;
	}
	public void setProjDesc(String projDesc) {
		this.projDesc = projDesc;
	}
	public String getProjReq() {
		return projReq;
	}
	public void setProjReq(String projReq) {
		this.projReq = projReq;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}

   	public  PostProjectsModel(String projName, String projDesc, String projDescfull, String projReq, String email, String phone, String projType, int projExp, List<Requests> requests, String keywords) {
       super();
   		this.projName = projName;
        this.projDesc = projDesc;
        this.projDescfull = projDescfull;
        this.projReq = projReq;
        this.email = email;
        this.phone = phone;
        this.projType = projType;
        this.projExp = projExp;
        this.requests = requests;
        this.keywords = keywords;
    }

	@Override
	public String toString() 
	{
		
		return "PostProjects [projName=" + projName + "; projDesc=" + projDesc +"; projDescfull=" + projDescfull+ "; projReq=" + projReq + "; email="
				+ email + "; phone=" + phone + "]";
	}
	public char getApproved() {
		return approved;
	}
	public void setApproved(char approved) {
		this.approved = approved;
	}
	
	public String getUserMail() {
		return userMail;
	}
	public void setUserMail(String userMail) {
		this.userMail = userMail;
	}
	public String getUsrName() {
		return usrName;
	}
	public void setUsrName(String usrName) {
		this.usrName = usrName;
	}
	public String getLikes() {
		return likes;
	}
	public void setLikes(String likes) {
		this.likes = likes;
	}
	public String getDislikes() {
		return dislikes;
	}
	public void setDislikes(String dislikes) {
		this.dislikes = dislikes;
	}
	
	

}