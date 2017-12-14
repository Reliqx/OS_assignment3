/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package os.assignment3;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.*;
import java.net.*;

public class EchoClient {
    public static void main(String[] args) {
	
	String hostname = args[0];
	int port = 6789;

        Socket clientSocket = null;  
        DataOutputStream os = null;
        BufferedReader is = null;
        
        try {
            clientSocket = new Socket(hostname, port);
            os = new DataOutputStream(clientSocket.getOutputStream());
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
	
	// If everything has been initialized then we want to write some data
	// to the socket we have opened a connection to on the given port

	try {
	    while ( true ) {
		System.out.print( "Enter Something otherwise hit . to exit:" );
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String keyboardInput = br.readLine();
		os.writeBytes( keyboardInput + "\n" );
                if (keyboardInput.equals(".")){
                    break;
                }
		String responseLine = is.readLine();
		System.out.println("Server: " + responseLine);
	    }
	    
	    // clean up:
	    // close the output stream
	    // close the input stream
	    // close the socket
	    
	    os.close();
	    is.close();
	    clientSocket.close();   
	} catch (UnknownHostException e) {
	    System.err.println("Trying to connect to unknown host: " + e);
	} catch (IOException e) {
	    System.err.println("IOException:  " + e);
	}
    }           
}

