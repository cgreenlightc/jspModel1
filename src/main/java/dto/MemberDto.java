package dto;

import java.io.Serializable;
									// 직렬화
public class MemberDto implements Serializable{
	// Dto 가 한두개가 아니다, 게시판...등등 번호를 배겨주는게 아랫것 ↓ 추가 안해도 사용하는데 지장없음
//	private static final long serialVersionUID = 1L;
	
	private String id;
	private String pwd;
	private String name;
	private String email;
	private int auth;		// 사용자:3 관리자:1 가릴수 있는 부분
	
	public MemberDto() {
	}
	
	public MemberDto(String id, String pwd, String name, String email, int auth) {
		super();
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
		this.auth = auth;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAuth() {
		return auth;
	}

	public void setAuth(int auth) {
		this.auth = auth;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", pwd=" + pwd + ", name=" + name + ", email=" + email + ", auth=" + auth + "]";
	}
	
}
