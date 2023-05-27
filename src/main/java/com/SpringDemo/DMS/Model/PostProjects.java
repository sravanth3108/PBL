
package com.SpringDemo.DMS.Model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "postProjects")
public class PostProjects {
	@Id
	private String projName;
	private String projDesc;
	private String projDescfull;
	private String projReq;
	private String email;
	private String phone;
	
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
	public PostProjects() {}
	public PostProjects(String projName, String projDesc,String projDescfull, String projReq, String email, String phone) {
		super();
		this.projName = projName;
		this.projDesc = projDesc;
		this.projReq = projReq;
		this.email = email;
		this.phone = phone;
		this.projDescfull = projDescfull;
	}
	@Override
	public String toString() {
		return "PostProjects [projName=" + projName + ", projDesc=" + projDesc +"projDescfull=" + projDescfull+ ", projReq=" + projReq + ", email="
				+ email + ", phone=" + phone + "]";
	}
	
	

}
