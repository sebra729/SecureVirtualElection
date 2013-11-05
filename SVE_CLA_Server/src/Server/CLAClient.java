package Server;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class CLAClient{
	
	private InetAddress host;
	private int port;
	private SSLSocket socketToCla = null;
	// This is not a reserved port number 
	
	static final String KEYSTORE = "ClaKeyStore.ks";
	static final String TRUSTSTORE = "ClaTrustStore.ks";
	static final String STOREPASSWD = "123456";
	static final String TRUSTSTOREPASSWD = "123456";
	static final String ALIASPASSWD = "123456";
	static final String KEYALIAS = "ClaAlias";
	
	// Constructor @param host Internet address of the host where the server is located
	// @param port Port number on the host where the server is listening
	public CLAClient( InetAddress host, int port ) {
		this.host = host;
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
			SSLSocketFactory sslFact = sslContext.getSocketFactory();      	
			SSLSocket client =  (SSLSocket)sslFact.createSocket(host, port);
			client.setEnabledCipherSuites( client.getSupportedCipherSuites() );
			System.out.println("\n>>>>CLA SSL/TLS handshake completed " + port);
			
			socketToCla = client;
//			connectionHandeler(client);
//			client.close();
		}
		catch( Exception x ) {
			System.out.println( x );
			x.printStackTrace();
		}
//		System.out.println("CLA handshake faild on port: " + port);
	}
	
	public SSLSocket getSocket(){
		if(socketToCla != null){
			return socketToCla;
		}
		else{
			System.out.println("CLAClient getSocket = null");
			return null;
		}
		
	}
	

}
