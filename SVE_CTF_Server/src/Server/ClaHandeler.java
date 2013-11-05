package Server;

import java.util.UUID;

import javax.net.ssl.SSLSocket;

public class ClaHandeler implements Runnable{
	private SSLSocket socketToCla;
	private CtfPtc protocol = new CtfPtc();
	
	public ClaHandeler(SSLSocket socketCla){
		this.socketToCla = socketCla;
	}
	
	public void run(){
		while(true){
			try {
				UUID verifyNr = protocol.getVerificationNr(socketToCla);
				if(ValidationNrContainer.instance().verifyNewNr(verifyNr)){
					ValidationNrContainer.instance().addNr(verifyNr);
					System.out.println("CTL recived validationnumber " + verifyNr+  " from CLA");
				}
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
