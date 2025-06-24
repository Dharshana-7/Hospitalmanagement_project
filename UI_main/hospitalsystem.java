package Project;

import java.util.Scanner;

public class hospitalsystem {
	
	public static void main(String args[])
	{
		
		patientQueue queue=new patientQueue();	
		Scanner s=new Scanner(System.in);

		//DBmanager db;

		try {
			new DBmanager();					//calls the DBmanager class's constructor to initialize the connection
		//db=new DBmanager();

		while(true)
		{
			System.out.println(" Enter the choices");
			System.out.println("1. Create PatientQueue");			//create table
			System.out.println("2. Admit patient");					//Enqueue patient
			System.out.println("3. Retrive History");				//Queue data of all patients
			System.out.println("4. Discharge patient");				//Dequeue patient
			System.out.println("5. Display Queue");					
			System.out.println("6. exit");
			int choice=s.nextInt();
			s.nextLine();


			switch(choice)
			{
			case 1:
				DBmanager.createtable();
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
				DBmanager.inserttable(newpatient);						//static method call
				System.out.println("Patient added to queue and database");
				break;

			
			case 3:
				DBmanager.retrivetable();
				break;

				
			case 4:
				Patient removed=queue.deque();
				if(removed!=null)
				{
					System.out.println("Dischared :"+removed.name);
					System.out.println("Patient get severed by the Appropriate Doctor.");
				}
				else 
				{
					System.out.println("Queue is empty.");
				}
				break;

			
			case 5:
				queue.display();
				break;

			
			case 6:
			DBmanager.close();						//static method call
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
