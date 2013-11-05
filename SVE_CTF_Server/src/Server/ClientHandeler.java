package Server;

import java.util.UUID;

import javax.net.ssl.SSLSocket;


public class ClientHandeler implements Runnable{
	private SSLSocket socketToClient;
	private CtfPtc protocol = new CtfPtc();
	
	public ClientHandeler(SSLSocket socketClient){
		this.socketToClient = socketClient;
	}
	
	private enum Action {
		vote, validation, print; 
	}
	
	public void run(){
		
		while(true){
			String[] msg = protocol.getMessage(socketToClient);
			Action action = Action.valueOf(msg[0]);
			
			switch(action){
			case vote:
				UUID validNr = UUID.fromString(msg[1]);
				UUID idNr = UUID.fromString(msg[2]);
				String vote = msg[3];
//				System.out.println(validNr + " " + idNr+ " " + vote);
				
				if(ValidationNrContainer.instance().verifyNr(validNr)){
					ValidationNrContainer.instance().setVoted(validNr);
					Tabulation.instance().addVote(idNr, vote);
					protocol.sendMessage(socketToClient, "Your vote has been accounted");
				}else{
					protocol.sendMessage(socketToClient, "You have allready voted!");
				}
				break;
			case validation:
				UUID validNr2 = UUID.fromString(msg[1]);
				UUID idNr2 = UUID.fromString(msg[2]);
				if(ValidationNrContainer.instance().verifyNr(validNr2)){
					protocol.sendMessage(socketToClient, "You have not voted");
				}else if(Tabulation.instance().verifyVote(idNr2)){
					protocol.sendMessage(socketToClient, "Your vote is in the tabulation");
				}else{
					protocol.sendMessage(socketToClient, "Your identification is not in the tabulation, but your validation number is used");
				}
				break;
			case print:
				Tabulation.instance().printResult();
				break;
			}
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		

	}

}
