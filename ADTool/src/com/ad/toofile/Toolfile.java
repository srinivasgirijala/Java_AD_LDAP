package com.ad.toofile;
import com.ad.allfiles.*;

import java.util.*;

import javax.naming.NamingException;
public class Toolfile {

	public static void main(String[] args) throws NamingException {
		AllFiles object=new AllFiles();
		object.newconnection();
		
		Scanner sc=new Scanner(System.in);
		System.out.println("1.DeleteUsers");
		System.out.println("2.AddGroup");
		System.out.println("3.DeleteGroup");
		System.out.println("4.AddMemberShipToGroup");
		System.out.println("5.UpdateUserPassword");
		System.out.println("6.AddUsers");
		System.out.println("7.UpdateUsers");
        System.out.println("8.User Authentication");
        System.out.println("9.UserDetailsFetch");
		System.out.println("Choose One Option:");
		int input=sc.nextInt();
		switch(input){
		case 1:
			object.DeleteUsers();
			break;
		case 2:
			object.AddGroup();
			break;
		case 3:
			object.DeleteGroup();
			break;
		case 4:
			object.AddMemberShipToGroup();
			break;
		case 5:
			object.updateUserPassword();
			break;
		case 6:
			object.adduser();
			break;
		case 7:
			object.updateEntry();
			break;
		case 8:
			object.userauthentication();
			break;
		case 9:
			object.GetAllUsers();
			break;
		}
     
	}

}

