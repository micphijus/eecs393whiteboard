package core.utility;

import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;

import org.jivesoftware.smack.*;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Registration;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class testMessage {
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
			XMPPConnection conn = new XMPPConnection(new ConnectionConfiguration("talk.google.com", 5222, "gmail.com"));
			conn.connect();
			md.update(password.getBytes());
			//password = md.digest().toString();
			//SASLAuthentication.supportSASLMechanism("PLAIN", 0);
			conn.login(username, password);
			Roster roster = conn.getRoster();
			Collection<RosterEntry> entries = roster.getEntries();
			Iterator<RosterEntry> iter = entries.iterator();
			while(iter.hasNext())
			{
				RosterEntry entry = iter.next();
				System.out.println(entry.getName() + "("+entry.getUser()+ ")");
			}
			Chat chat = conn.getChatManager().createChat("jpm45@case.edu", new MessageListener() {
				
				@Override
				public void processMessage(Chat arg0, Message arg1) {
					// TODO Auto-generated method stub
					System.out.println("Received Message: " + arg1);
				}
			});
			chat.sendMessage("This is Felix.");
			
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("Algorithm does not exist: " + e.toString());
		}
		catch (XMPPException e) {
			System.out.println("Threw an XMPP error: " + e.toString());
			e.printStackTrace();
		}
		catch(Exception e)
		{
			System.out.println("Generic exception: " + e.toString());
		}
	}

}
