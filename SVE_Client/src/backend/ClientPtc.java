package backend;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;
import java.util.UUID;

import javax.net.ssl.SSLSocket;

public class ClientPtc {
	
	public String[] getMessage(SSLSocket socket){
		String[] messageArray = null;
		try {
			BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			String str = in.readLine();
			StringTokenizer st = new StringTokenizer( str );
			int i = 0;
			messageArray = new String[st.countTokens()];
			while( st.hasMoreTokens() ) {
				messageArray[i] = new String( st.nextToken() );
				i++;
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(messageArray == null){
			System.out.println("CtfPtc getMessage() return null");
		}
		return messageArray;
	}
	
	public void sendMessage(SSLSocket socket, String msg ){
		
		try {
			PrintWriter socketOut = new PrintWriter( socket.getOutputStream(), true );
			socketOut.println(msg);
			socketOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public UUID getVerificationNr(SSLSocket socket){
		UUID verifyNr = null;
		try {
			BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
			verifyNr = UUID.fromString(in.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return verifyNr;
	}

}
