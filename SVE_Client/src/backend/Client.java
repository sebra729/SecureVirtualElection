package backend;

import java.io.FileInputStream;
import java.net.InetAddress;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

public class Client {

	private InetAddress host;
	private int port;
	// This is not a reserved port number 
	
	static final String KEYSTORE = "ClientKeyStore.ks";
	static final String TRUSTSTORE = "ClientTrustStore.ks";
	static final String STOREPASSWD = "123456";
	static final String TRUSTSTOREPASSWD = "123456";
	static final String ALIASPASSWD = "123456";
	static final String KEYALIAS = "clientAlias";
	
	// Constructor @param host Internet address of the host where the server is located
	// @param port Port number on the host where the server is listening
	public Client( InetAddress host, int port ) {
		this.host = host;
		this.port = port;
	}
	
	public SSLSocket runCLA() {
		
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
			System.out.println("\n>>>> SSL/TLS handshake completed " + port);
			
			return client;
//			connectionHandeler(client);
//			client.close();
		}
		catch( Exception x ) {
			System.out.println( x );
			x.printStackTrace();
		}
		System.out.println("Client socket connetion faild on port: " + port);
		return null;
	}
	
	
	
}
