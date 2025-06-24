package Project;

public class patientQueue {
	Node front;
	Node rear;

	class Node{

		Patient patient;
		Node next;

		Node(Patient patient)
		{
			this.patient=patient;

		}

	}

	
	patientQueue()			//constructor
	{
		front=null;
		rear=null;

	}


	public void enque(Patient patient)
	{
		Node newnode=new Node(patient);
		if(front==null)
		{
			front=newnode;
			rear=newnode;
		}

		else
		{
			rear.next=newnode;
			rear=newnode;

		}

	}

	
	
	
	public Patient deque()
	{
		if(front==null)
		{
			System.out.println("queue empty");		//checks whether the queue is empty
			return null;
		}
		else {
			Patient patient=front.patient;		//if queue isn't empty it points the first patient as front.
		front=front.next;						//to remove first patient, front points to the next patient.
		
		
		if(front==null)				//if there was only one patient that was also got removed, front will now become null.
		{
			rear=null;		//after removal, if front is null, then the queue has become completely empty.
							//so we also update rear to null.
		}
		
		return patient;		//return the discharged patient.
		
		
		}
	}


	public void display()
	{
		Node temp=front;
		while(temp!=null)
		{
			System.out.println(temp.patient.name + " - " + temp.patient.healthissue);
			temp=temp.next;
		}

	}
}
