package Server;

import javax.net.ssl.SSLSocket;

public class SocketHandeler implements Runnable{

	private SSLSocket socketToClient;
	private SSLSocket socketToCtf;
	private ClaPtc protocol;
	private User user = null;
	
	
	public SocketHandeler(SSLSocket socketClient, SSLSocket socketCtf){
		this.socketToClient = socketClient;
		this.socketToCtf = socketCtf;
	}
	
	public void run(){
		System.out.println("CLA SocketHandeler approve this message");
		
		protocol = new ClaPtc();
		
		while(true){
			if(verifyUser()){
				protocol.sendMessage(socketToClient, "Accepted");
				protocol.sendVailidationNr(socketToClient, user.getValidationNr());
				protocol.sendVailidationNr(socketToCtf, user.getValidationNr());
			}else{
				protocol.sendMessage(socketToClient, "Fail");
			}
				
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}

	private boolean verifyUser(){
		String[] userInfo = protocol.getMessage(socketToClient);

		user = UserContainer.instance().verifyUser(userInfo);
		
		if(user == null){
			System.out.println("No user found");
			return false;
		}else{
			System.out.println(user.getUserName() + user.getPassword() + user.getValidationNr());
		}
		return true;
	}
	
	
	
}
