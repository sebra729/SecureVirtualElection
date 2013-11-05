package Server;

import java.util.UUID;

public class User {

	private String userName, pwd;
	private UUID validNr;
	
	public User(String un, String pw, UUID vdn){
		this.userName = un;
		this.pwd = pw;
		if(vdn == null){
			this.validNr = getValidationNr();
		}else{
			this.validNr = vdn;
		}
	}
	
	public UUID getValidationNr(){
		if(validNr == null){
			this.validNr = UUID.randomUUID();
		}
		return validNr;
	}
	
	public String getUserName(){
		return userName;
	}
	
	public String getPassword(){
		return pwd;
	}
	
}
