import java.util.ArrayList;
public class Subject{
    private double average = 100.0;
    private String name = "Untitled";
    private ArrayList<Subcategory> collection = new ArrayList<>();
    
    public Subject(String n){
	name=n;
    }

    public String toString(){
	return "Subject Name: "+name;
    }
    public void setName(String n){
	name=n;
    }
    public String getName(){
	return name;
    }
   
    public double getAverage(){
	return average;
    }

    public static void main(String[] args){
	Subject n = new Subject("foo");
	System.out.println(n);
	n.setName("APCS");
	System.out.println(n.getName());
    }			    
}