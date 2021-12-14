
import java.net.*;
import java.util.Scanner;
import java.io.*;



/**
 * 
 * @author Malachi Gage Sanderson
 * @since 12-13-21
 * 
 * 
 * @see 
 * <p><a href="https://www.youtube.com/watch?v=BqBKEXLqdvI">Edureka! Youtube Client Server Architecture tutorial</a> </p> 
 * <p> <a href="https://www.youtube.com/watch?v=-xKgxqG411c">ThenisH Socket Programming Simple tutorial</a></p>
 */
public class Client 
{
	private Socket s;
	private DataInputStream inputStream;
	private DataOutputStream outputStream;


	private final static int portID = 1210;
	private final static String address = "localhost";


	public static void main(String[] args) throws IOException
	{
		System.out.println("[CLIENT]: RUNNING CLIENT MAIN!");
		Client client = new Client(getPortID(),getAddressName());
		client.clientInputStringTest();
		

	}

	public Client(int port, String addresssName)
	{
		System.out.println("[CLIENT]: NEW CLIENT CREATED!");
		try
		{
			System.out.println("[CLIENT]:.....Trying to connect to server....");

			s = new Socket(addresssName, port);
			//inputStream  = new DataInputStream(System.in);
			inputStream  = new DataInputStream(s.getInputStream());
			outputStream = new DataOutputStream(s.getOutputStream());


			//Tries to connect...
			System.out.println("\n");
			System.out.println("[CLIENT]:.....Connected to Server!....");

		}
		catch(UnknownHostException a)
		{
			System.out.println("[CLIENT]: Host Connection Failed...(Unknown Host)");
			a.printStackTrace();
			return;
		}
		catch(IOException b)
		{
			System.out.println("[CLIENT]: Host Connection FAILED...(IO Exception)");
			return;        
		}
		catch(Exception c)
		{
			System.out.println("[CLIENT]: [UNEXPECTED ERROR!!! Host Connection FAILED...UNPREDICTED FAILURE]");
			c.printStackTrace();
			return;  
		}


		


	}


	/**
	 * Lets client input text and upon detecting the word "Over" it will send the line of text and
	 * stop accepting inputs.
	 */
	public void clientInputStringTest()
	{
		System.out.println("[CLIENT]:.....Input two positive number values separated by commas as height(m) then weight(kg)....");
		Scanner scan = new Scanner(System.in);
		//String to read message the client will input.
		String line = "";
		//Keep reading input until we see the word "OVER"
		while(!line.equals("Over"))
		{
			try 
			{
				//line = inputStream.readLine();
				//outputStream.writeUTF(line);
				line = scan.nextLine();
				System.out.println(line + "\n\n\n");
				
				outputStream.writeUTF(line);
				//System.out.println(inputStream.readUTF() + "\n\n\n");
				
				
				line = inputStream.readUTF();
				System.out.println("[CLIENT]: Successfully Successfully Calculated BMI from server:\n\tBMI = "+line);
				closeAllConnections();
			} 
			catch (IOException e) 
			{
				// TODO: handle exception
				System.out.printf("\n[CLIENT]: Failed To Send Line %s...(IO Exception)\n[CLIENT]: ENDING DUE TO ERROR...", line );
				break;
			}
			catch(Exception c)
			{
				System.out.println("[CLIENT]: [UNEXPECTED ERROR!!! Send Line FAILED...UNPREDICTED FAILURE]");
				c.printStackTrace();
				return;  
			}
		}
		//scan.close();
		closeAllConnections();
	}




	public void closeAllConnections()
	{
		//FINALLY...
		//Close the client-server connection...
		try 
		{
			inputStream.close();
			outputStream.close();
			s.close();
			System.exit(0);
		}
		catch (IOException a) 
		{
			System.out.println("[CLIENT]: Failed To Close Connection...(IO Exception)");
		}
		catch (Exception e) 
		{
			System.out.println("[CLIENT]: [UNEXPECTED ERROR!!! Close Connection FAILED...UNPREDICTED FAILURE]");
			e.printStackTrace();
			return;  
		}
	}

	/**
	 * Since I'm just using one connection with one port number I made it a static
	 * final value but I don't want it to ever be changed so I made it private and gave it
	 * a getter.
	 * @return {@link #portID}
	 */
	public final static int getPortID()
	{
		return portID;
	}

	public final static String getAddressName()
	{
		return address;
	}

}
