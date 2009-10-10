package core.utility;

import java.util.Scanner;

import org.jivesoftware.smack.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Registration {
	public static void main(String []args)
	{
		Scanner input = new Scanner(System.in);
		System.out.print("Enter username: ");
		String username = input.nextLine();
		System.out.print("Enter password: ");
		String password = input.nextLine();
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			XMPPConnection conn = new XMPPConnection("127.0.0.1");
			conn.connect();
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("Algorithm does not exist: " + e.toString());
		}
		catch (XMPPException e) {
			System.out.println("Threw an XMPP error: " + e.toString());
		}
		catch(Exception e)
		{
			System.out.println("Generic exception: " + e.toString());
		}
	}

}