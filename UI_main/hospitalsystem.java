package Ui;

import java.util.Scanner;

import db.DBmanager;
import dsa.patientQueue;
import model.Patient;

public class hospitalsystem {
	
	public static void main(String args[])
	{
		
		patientQueue queue=new patientQueue();	
		Scanner s=new Scanner(System.in);
		

		DBmanager db;	//DBmanager reference variable to call the methods in DBmanager class

		try {
								
		 db=new DBmanager();			//calls the DBmanager class's constructor to initialize the connection

		while(true)
		{
			System.out.println("----Welcome to XYZ Hospital----");
			System.out.println("  Enter the choices  ");
			System.out.println("1. Create PatientQueue");			//create table
			System.out.println("2. Admit patient");					//Enqueue patient
			System.out.println("3. Retrive History");				//Queue data of all patients
			System.out.println("4. Discharge patient");				//Dequeue the served patient
			System.out.println("5. Searchpatient by Token");		//search patient by patient details
			System.out.println("6. Deletepatient by TokenName");	//delete patient by name
			System.out.println("7. Display Queue");					//display from Queue class
			System.out.println("8. exit");						//disconnects the connection
			int choice=s.nextInt();
			s.nextLine();


			switch(choice)
			{
			case 1:
				db.createtable();
				break;
				
			case 2:
				System.out.println("Admission num: ");
				int Admission=s.nextInt();
				s.nextLine();
				System.out.println("Name: ");
				String name=s.nextLine();
				System.out.println("Age: ");
				int age=s.nextInt();
				s.nextLine();
				System.out.println("healthissue: ");
				String healthissue=s.nextLine();
				
	                switch (healthissue) {				//nested switch to recommend doctors
	                    case "fever":
	                    case "cold":
	                        System.out.println("General Physician.");
	                        break;
	                    case "toothache":
	                        System.out.println("Dentist.");
	                        break;
	                    case "bone fracture":
	                        System.out.println("Orthopedic Surgeon.");
	                        break;	               
	                    case "diabetes":
	                        System.out.println("Endocrinologist.");
	                        break;
	                    case "heart pain":
	                        System.out.println("Cardiologist.");
	                        break;
	                    case "eye pain":
	                        System.out.println("Ophthalmologist.");
	                        break;
	                    case "skin allergy":
	                        System.out.println("Dermatologist.");
	                        break;
	                    default:
	                        System.out.println("No specific recommendation");
	                }
	                
				System.out.println("Priority (High/Medium/Low): ");
				String priority=s.nextLine();

				
				Patient newpatient=new Patient(Admission,name,age,healthissue,priority);		
				queue.enque(newpatient);
				db.inserttable(newpatient);						
				
				break;

				
			
			case 3:		
				db.retrivetable();
				break;

				
			case 4:
				Patient serve=queue.deque();
				if(serve!=null)
				{
					db.deletePatient(serve.Admission);
					System.out.println("Enter the number: ");
					Admission=s.nextInt();
					System.out.println("Patient get severed by the Appropriate Doctor." + serve.name);
				}
				else 
				{
					System.out.println("Queue is empty.");
				}
				break;

				
			case 5:
				System.out.println("Enter patient (Adnumber,name,age,healthissue,priority): ");
				String searchtoken=s.nextLine();
				db.searchpatientbyToken(searchtoken);
				break;
				
			case 6:
				System.out.print("Enter patient name to delete: ");
			    String deleteToken = s.nextLine();
			    db.deleteByToken(deleteToken);
			    break;
			
			case 7:
				queue.display();
				break;

			
			case 8:
				db.close();						
				break;
				
			}

		}
		}

		
        catch(Exception e)
		{
        	System.out.println(e);
		}
		
		s.close();
	}

}
