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

public class CTF{
	
	private int port;
	// This is not a reserved port number
	static final String KEYSTORE = "CtfKeyStore.ks";
	static final String TRUSTSTORE = "CtfTrustStore.ks";
	static final String STOREPASSWD = "123456";
	static final String TRUSTSTOREPASSWD = "123456";
	static final String ALIASPASSWD = "123456";
	
	private static SSLServerSocket sss;
	/** Constructor
	 * @param port The port where the server
	 *    will listen for requests
	 * @return 
	 */
	CTF( int port ) {
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

			System.out.println("\n>>>> CTF Server: active on port: " + port );
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
//		System.out.println("CTF server unable to start om port " + port);
	}

	
	static final int DEFAULT_CLIENT_PORT = 8190; // CLA to Client 8190
	static final int DEFAULT_CLA_PORT = 8191;	//CLA to CTF 8191
	
	public static void main( String[] args ) {
		int portClient = DEFAULT_CLIENT_PORT;
		int portCLA = DEFAULT_CLA_PORT;
		SSLSocket socketToCla = null;
		
		ValidationNrContainer vc = ValidationNrContainer.instance();
		Tabulation tb = Tabulation.instance();
		
		CTF ctfToCla= new CTF(portCLA);
		ctfToCla.run();
		try {
			socketToCla = (SSLSocket)sss.accept();
			sss.setNeedClientAuth(true);
			System.out.println("CTF server socket to CLA establiched");
			(new Thread(new ClaHandeler(socketToCla))).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		CTF ctfToClient = new CTF(portClient);
		ctfToClient.run();
		
		while(true){
			try {
				SSLSocket socketToClient = (SSLSocket)sss.accept();
				sss.setNeedClientAuth(true);
				System.out.println("CTF server socket to Client establiched");
				(new Thread(new ClientHandeler(socketToClient))).start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		
	}
}

