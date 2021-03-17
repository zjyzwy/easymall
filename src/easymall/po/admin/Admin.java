package easymall.po.admin;

public class Admin {
	private String username;
	private String password;
	private int authority;
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAuthority() {
		return authority;
	}
	public void setAuthority(int authority) {
		this.authority = authority;
	}

	@Override
	public String toString() {
		return "Admin [username=" + username + ", password=" + password + " ,authority="+authority+"]";
	}
	
}
