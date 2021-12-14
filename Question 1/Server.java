import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 
 * @author Malachi Gage Sanderson
 * @since 12-13-21
 */
public class Server implements Runnable
{
	private ServerSocket ss;
	private Socket s;
	private DataInputStream inputStream;
	//private DataOutputStream outputStream;



	public static void main(String[] args) 
	{
		System.out.println("[SERVER]: RUNNING SERVER MAIN!");
		Server server = new Server(Client.getPortID());
		new Thread(server).start();

	}



	@Override
	public void run() 
	{
		try
		{
			ss = new ServerSocket(Client.getPortID());
			System.out.println("[SERVER]:.....Trying to connect....");
			
			

			

			while (true) 
			{
				final Socket socket = ss.accept();
				//Tries to connect...
				System.out.println("\n");
				System.out.println("[SERVER]:.....Client connected!....");
				new Thread() 
				{
					public void run() 
					{
						try
						{
							DataInputStream inpStream  = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
							DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
							serverGetClientInputtedStringTest(inpStream,outStream);
							socket.close();
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}

					}
				}
				.start();
			}
		}
		catch(UnknownHostException a)
		{
			System.out.println("[SERVER]: [ERROR...Host Connection FAILED...(Unknown Host)]");
			a.printStackTrace();
			return;
		}
		catch(IOException b)
		{
			System.out.println("[SERVER]: Host Connection FAILED...(IO Exception)");
			return;        
		}
		catch(Exception c)
		{
			System.out.println("[SERVER]: [UNEXPECTED ERROR!!! Host Connection FAILED...UNPREDICTED FAILURE]");
			c.printStackTrace();
			return;  
		}

	}







	public Server(int portID)
	{
		System.out.println("[SERVER]: NEW SERVER CREATED!");
		/*
		try
		{
			ss = new ServerSocket(portID);
			System.out.println("[SERVER]:.....Trying to connect....");
			s = ss.accept();
			

			//Tries to connect...
			System.out.println("\n");
			System.out.println("[SERVER]:.....Client connected!....");
		}
		catch(UnknownHostException a)
		{
			System.out.println("[SERVER]: [ERROR...Host Connection FAILED...(Unknown Host)]");
			a.printStackTrace();
			return;
		}
		catch(IOException b)
		{
			System.out.println("[SERVER]: Host Connection FAILED...(IO Exception)");
			return;        
		}
		catch(Exception c)
		{
			System.out.println("[SERVER]: [UNEXPECTED ERROR!!! Host Connection FAILED...UNPREDICTED FAILURE]");
			c.printStackTrace();
			return;  
		}
		
		*/
	}


	/**
	 * [TODO]
	 * @throws IOException 
	 */
	public void serverGetClientInputtedStringTest(DataInputStream input, DataOutputStream out)
	{
		//takes input from client socket...

		//String to read message the client will input.
		String line = "";
		//Keep reading input until we see the word "Over"
		while(!line.equals("Over"))
		{
			try 
			{
				
				//iStream = s.getInputStream();
				line = input.readUTF();
				System.out.println("[SERVER]: Successfully recieved line:\n\t "+line);

				System.out.println("[SERVER]: Attempting to Parse Body Data Given User Input... ");
				ArrayList<Double> bodyData = convertLineToDoubleArr(line);

				System.out.println("[SERVER]: Attempting to Calculate BMI Given Parsed Data of:\n\tHeight =  "+bodyData.get(0)+"\n\tWeight = "+bodyData.get(1));
				System.out.println("[SERVER]: Successfully Calculated BMI...\n\tBMI =  "+calculateBMI(bodyData.get(0), bodyData.get(1)));
				out.writeUTF(Double.toString(calculateBMI(bodyData.get(0), bodyData.get(1))));
				out.close();
				input.close();
				line = "Over";
			} 
			catch (IOException e) 
			{
				// TODO: handle exception
				System.out.printf("\n[SERVER]: Failed To Get Line %s...(IO Exception)\n", line );
				e.printStackTrace();
				return;
			}
			catch(Exception c)
			{
				System.out.println("[SERVER]: [UNEXPECTED ERROR!!! Get Line FAILED...UNPREDICTED FAILURE]");
				c.printStackTrace();
				return;  
			}
		}
		//closeAllConnections();
	}


	public void closeAllConnections()
	{
		System.out.println("[SERVER]:.....Closing Connection with Client!....");
		//Close the client-server connection...
		try 
		{
			inputStream.close();
			ss.close();
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



	private ArrayList<Double> convertLineToDoubleArr(String line) throws Exception
	{
		ArrayList<String> sData_temp = new ArrayList<String>(Arrays.asList(line.split(","))); 
		ArrayList<Double> bodyData = new ArrayList<Double>();
		if(sData_temp.size() > 2) throw new Exception("[!!!ERROR: BAD INPUTS!!!!]");
		for(String s : sData_temp)
		{
			bodyData.add(Double.parseDouble(s));
		}
		bodyData.trimToSize();
		return bodyData;
	}


	/**
	 * 
	 * @param weight required to be in kilograms
	 * @param height required to be in meters
	 * @return
	 */
	private double calculateBMI(double height,double weight)
	{
		return (weight/(height*height));
	}



}
