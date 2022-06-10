package com.ad.allfiles;



import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.AuthenticationException;
import javax.naming.AuthenticationNotSupportedException;
import javax.naming.directory.*;





		
public class AllFiles {
	DirContext connection;

	public void newconnection(){
			
	
				// TODO Auto-generated method stub
		    System.out.println("Hello World");
		    Properties env=new Properties();
		    env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		    env.put(Context.PROVIDER_URL,"ldap://192.168.56.102:389");
		 //   env.put(Context.SECURITY_PROTOCOL,"ssl");

		    env.put(Context.SECURITY_PRINCIPAL,"Administrator@fortunaidentity.com");
		    
		    env.put(Context.SECURITY_CREDENTIALS,"Vasu234@#");
		   
		    try {
				connection = new InitialDirContext(env);
				System.out.println("Hello World!" + connection);
			} catch (AuthenticationException ex) {
				System.out.println(ex.getMessage());
				System.out.println("Not Connected");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("Not Connected");

			}
		    
		    
	}
	
	public void adduser()
	 {
		 Attributes attributes=new BasicAttributes();
		 Attribute attribute=new BasicAttribute("objectClass");
		 attribute.add("User");
		 attributes.put(attribute);
		 Scanner sc=new Scanner(System.in);
		 System.out.println("Enter FirstName: ");
		 String firstname=sc.nextLine();
		 attributes.put("givenName",firstname);
		 System.out.println("Enter LaststName: ");
		 String lastname=sc.nextLine();
		 attributes.put("sn",lastname);
		 System.out.println("Enter UserName: ");
		 String username=sc.nextLine();
		 attributes.put("cn",username);
		 try {
			connection.createSubcontext("CN="+username+",OU=People,DC=fortunaidentity,DC=com",attributes);
			System.out.println("success");
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 
	 
	public static boolean userauthentication()
	{
		try
		{
			 Scanner sc=new Scanner(System.in);
			 System.out.println("Enter UserName: ");
			 String username=sc.nextLine();
			 System.out.println("Enter Password: ");
			 String password=sc.nextLine();
			Properties env =new Properties();
			env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		    env.put(Context.PROVIDER_URL,"ldap://192.168.56.102:389");
		  //  env.put(Context.SECURITY_AUTHENTICATION, "simple");
		    env.put(Context.SECURITY_PRINCIPAL,"CN="+username+",OU=People,DC=fortunaidentity,DC=com");
		    env.put(Context.SECURITY_CREDENTIALS,password);
			DirContext con=new InitialDirContext(env);
			con.close();
			return true;
			
		}
		catch (Exception e){
			System.out.println(e.getMessage());
			return false;
			
		}
	}
	
	
	
	
	 public void GetAllUsers() throws NamingException {
			String searchfilter="(objectClass=organizationalPerson)";
		
					String[] reqatt={"cn","sn","memberof","telephoneNumber","userPassword"};
		
			SearchControls controls=new SearchControls();
			controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			controls.setReturningAttributes(reqatt);
			NamingEnumeration users=connection.search("ou=People,dc=fortunaidentity,dc=com",searchfilter,controls);
			SearchResult result=null;
			System.out.println("-------------------");
			while(users.hasMore())
			{
				
			
				result=(SearchResult)users.next();
				
				Attributes attr=result.getAttributes();
				
				System.out.println("user");
	            System.out.println(attr.get("cn"));
	            System.out.println(attr.get("sn"));
			    System.out.println(attr.get("memberof"));
			    System.out.println(attr.get("telephoneNumber"));
			    System.out.println(attr.get("userPassword"));
			}
			
		}
	
	
	
	
	
	
	public void updateEntry() {
		DirContext connection ;
	Properties properties = new Properties();
	properties.put(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.ldap.LdapCtxFactory");
	properties.put(Context.PROVIDER_URL, "ldap://192.168.56.102:389");
	properties.put(Context.SECURITY_PRINCIPAL,"Administrator@fortunaidentity.com");
	properties.put(Context.SECURITY_CREDENTIALS,"Vasu234@#");
	try {
	 connection = new InitialDirContext(properties);
	 Scanner sc=new Scanner(System.in);
	 System.out.println("Enter UserName: ");
	 String username=sc.nextLine();
	Attributes attrs = connection.getAttributes("cn="+username+",ou=People,dc=fortunaidentity,dc=com");
	System.out.println("Before update..");
	displayAttributes(attrs);
	System.out.println("Updating..");
	 System.out.println("Enter LastName: ");
	 String lastname=sc.nextLine();

	 System.out.println("Enter TelephoneNumber: ");
	 String telephonenumber=sc.nextLine();

	Attribute attribute = new BasicAttribute("sn",lastname);
	Attribute attribute1 = new BasicAttribute("telephoneNumber",telephonenumber);
	ModificationItem[] items = new ModificationItem[2];
	items[0] = new ModificationItem(connection.REPLACE_ATTRIBUTE,attribute);
	items[1] = new ModificationItem(connection.REPLACE_ATTRIBUTE,attribute1);
	connection.modifyAttributes("cn=vasu1,ou=People,dc=fortunaidentity,dc=com",items);

	//ModificationItem[] item1 = new ModificationItem[2];
	//item[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE,attribute1);
	   
	System.out.println("After update ..");
	Attributes attrs1 = connection
	.getAttributes("cn=vasu1,ou=People,dc=fortunaidentity,dc=com");
	displayAttributes(attrs1);
	} catch (NamingException e) {
	e.printStackTrace();
	}
	}
	public void displayAttributes(Attributes attributes) {
	try {

	System.out.println("Common name : " + attributes.get("cn").get());
	System.out.println("Last name : " + attributes.get("sn").get());
	System.out.println("Telephone Number : " + attributes.get("telephoneNumber").get());

	} catch (NamingException e) {
		
		e.printStackTrace();
		}
		}
		

	
	
	
	
	
	
	 public void AddGroup()
	 {	 Scanner sc=new Scanner(System.in);
	 System.out.println("Enter Value For Group Scope& Type: ");
	 String groupinput=sc.nextLine();
	 System.out.println("Enter GroupName To Add :");
	 String username=sc.nextLine();
	 
		 Attributes attributes=new BasicAttributes();
		 Attribute attribute=new BasicAttribute("objectClass");	
		 attribute.add("group");
		 attributes.put(attribute);	
		 attributes.put("groupType",groupinput);
	
		 try {
			connection.createSubcontext("CN="+username+",OU=Groups,DC=fortunaidentity,DC=com",attributes);
			System.out.println("success");
			
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	 }
	 
	 
	 
	 
	 
	 
	 public void AddMemberShipToGroup(){
			
		    try {
		        
		        Scanner sc=new Scanner(System.in);
		        System.out.println("Enter GroupName: ");
		        String groupname=sc.nextLine();
		        System.out.println("Enter AddMemberShip: ");
		        String addmembership=sc.nextLine();

		        System.out.println(connection.getEnvironment());
		        ModificationItem[] mods = new ModificationItem[1];
				Attribute attribute = new BasicAttribute("member","CN="+groupname+",OU=Groups,DC=fortunaidentity,DC=com");
				mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attribute);
				
					connection.modifyAttributes("CN="+addmembership+",OU=Groups,DC=fortunaidentity,DC=com", mods);
					System.out.println("success");
				} catch (NamingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
		    }
	 
	 
	 
	
	public void DeleteUsers() {
		
    try {        
       Scanner sc=new Scanner(System.in);
       System.out.println("enter UserName To Delete: ");
       String input=sc.nextLine();
        connection.unbind("CN="+input+",OU=People,DC=fortunaidentity,DC=com");
    	 Object obj = null;
   
   	try{
   		obj = connection.lookup("CN="+input+",OU=People,DC=fortunaidentity,DC=com");
   	
   }catch (NameNotFoundException ne) {
       System.out.println("unbind successful");
       return;
   }
   	System.out.println("unbind failed; object still there: " + obj);

       // Close the context when we're done
       connection.close();
     } catch (NamingException e) {
       System.out.println("Operation failed: " + e);
     }
   }
        
	
	
	
	
	public void DeleteGroup(){
		    try {
		        Scanner sc=new Scanner(System.in);
		        System.out.println("Enter GroupName To Delete: ");
		        String input=sc.nextLine();
		        connection.unbind("CN="+input+",OU=Groups,DC=fortunaidentity,DC=com");
		   	 Object obj = null;
		   
		   	try{
		   		obj = connection.lookup("CN="+input+",OU=Groups,DC=fortunaidentity,DC=com");
		   	
		   }catch (NameNotFoundException ne) {
		       System.out.println("unbind successful");
		       return;
		   }
		   	System.out.println("unbind failed; object still there: " + obj);

		       // Close the context when we're done
		       connection.close();
		     } catch (NamingException e) {
		       System.out.println("Operation failed: " + e);
		     }
	}
	
	
	
	public void updateUserPassword() {
		try {
			 Scanner sc=new Scanner(System.in);
		        System.out.println("Enter Username: ");
		        String username=sc.nextLine();
		        System.out.println("Enter Password: ");
		       String password=sc.nextLine();
			String dnBase=",OU=People,DC=fortunaidentity,DC=com";
			ModificationItem[] mods= new ModificationItem[1];
			mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("userPassword", password));// if you want, then you can delete the old password and after that you can replace with new password 
			connection.modifyAttributes("cn="+username +dnBase, mods);//try to form DN dynamically
			System.out.println("success");
		}catch (Exception e) {
			System.out.println("failed: "+e.getMessage());
		}
	
	
	
}
	
}