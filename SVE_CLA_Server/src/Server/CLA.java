package Server;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManagerFactory;

public class CLA{
	
	private int port;
	private static SSLServerSocket sss;
	// This is not a reserved port number
	static final String KEYSTORE = "ClaKeyStore.ks";
	static final String TRUSTSTORE = "ClaTrustStore.ks";
	static final String STOREPASSWD = "123456";
	static final String TRUSTSTOREPASSWD = "123456";
	static final String ALIASPASSWD = "123456";
	
	/** Constructor
	 * @param port The port where the server
	 *    will listen for requests
	 * @return 
	 */
	CLA( int port ) {
		this.port = port;
	}

	public void run() {
		try {
			KeyStore ks = KeyStore.getInstance( "JCEKS" );
			ks.load( new FileInputStream( KEYSTORE ), STOREPASSWD.toCharArray() );
			
			KeyStore ts = KeyStore.getInstance( "JCEKS" );
			ts.load( new FileInputStream( TRUSTSTORE ), TRUSTSTOREPASSWD.toCharArray() );
			
			KeyManagerFactory kmf = KeyManagerFactory.getInstance( KeyManagerFactory.getDefaultAlgorithm() );
			kmf.init( ks, ALIASPASSWD.toCharArray() );
			
			TrustManagerFactory tmf = TrustManagerFactory.getInstance( KeyManagerFactory.getDefaultAlgorithm() );
			tmf.init( ts );
			
			SSLContext sslContext = SSLContext.getInstance( "TLS" );
			sslContext.init( kmf.getKeyManagers(), tmf.getTrustManagers(), null );
			SSLServerSocketFactory sslServerFactory = sslContext.getServerSocketFactory();
			sss = (SSLServerSocket) sslServerFactory.createServerSocket( port );
			sss.setEnabledCipherSuites( sss.getSupportedCipherSuites() );	
			System.out.println("\n>>>> CLA Server: active on port: " + port );
//			SSLSocket incoming = (SSLSocket)sss.accept();

//			connectionHandeler(incoming);	
//			incoming.close();
		}
		catch( EOFException x ) {
			System.out.println( x );
			
		}
		catch( Exception x ) {
			System.out.println( x );
			x.printStackTrace();
		}
//		System.out.println("CLA server unable to start on port " + port);
	}
	
	static final int DEFAULT_CLIENT_PORT = 8189; // CLA to Client 8189
	static final int DEFAULT_CTF_PORT = 8191;	//CLA to CTF 8191
	
	public static void main( String[] args ) {
		int portClient = DEFAULT_CLIENT_PORT;
		int portCTF = DEFAULT_CTF_PORT;
		InetAddress host;
		CLAClient claToCtf = null;
		
		UserContainer uc = UserContainer.instance();
		
		// Creates and initialize socket to CTF
		try {
			host = InetAddress.getLocalHost();
			claToCtf = new CLAClient(host, portCTF);
			claToCtf.run();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		// Creates and initialize CLA server. 
		CLA claToClient = new CLA(portClient);
		claToClient.run();
		
		// Wait for new clients to connect. Ads new connection to new tread,
		while(true){
			try {
				SSLSocket socketToClient = (SSLSocket)sss.accept();
				sss.setNeedClientAuth(true);
				(new Thread(new SocketHandeler(socketToClient, claToCtf.getSocket()))).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("CLA failed to accept client");
				e.printStackTrace();
			} 
		}

		
		
	}
	
}
