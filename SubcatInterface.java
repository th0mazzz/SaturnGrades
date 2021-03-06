import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SubcatInterface extends JPanel implements ActionListener{
    private SaturnGradesGUI frame;
    private SubjectInterface pane;
    private Subcategory data;
    private ArrayList<Assignment> arr;
    private JScrollPane midpane;
    private JPanel top;
    ArrayList<JButton> AButtons = new ArrayList<>();

    public SubcatInterface(SaturnGradesGUI frame, SubjectInterface pane, Subcategory data){
	setLayout(new BorderLayout());
	this.frame=frame;
	this.pane=pane;
	this.data=data;
	top = new JPanel(new FlowLayout());
	JLabel title = new JLabel(data.getName());
	top.add(title);
	this.add(top, BorderLayout.NORTH);
	setUpMidPane();
	addBackButton();
	
    }

    public void setUpMidPane(){
	JPanel pane = new JPanel();
	pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
	midpane = new JScrollPane(pane, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

	//adding to the top panel an add button
	JLabel add = new JLabel("Add an assignment (name, percent grade earned, date): ");
	top.add(add);
	JTextField name = new JTextField(10);
	JTextField grade = new JTextField(5);
	JTextField date = new JTextField(10);
	JButton addit = new JButton("add");
	addit.addActionListener(new ActionListener(){
		@Override public void actionPerformed(ActionEvent e){
		    Assignment a = new Assignment(name.getText(),Double.parseDouble(grade.getText()),date.getText());
		    pane.add(createAssignmentButton(a));
		    name.setText("");
		    grade.setText("");
		    date.setText("");
		    revalidate();
		    repaint();
		}
	    });
	top.add(name);
	top.add(grade);
	top.add(date);
	top.add(addit);

	//adding to top panel a remove button
	JLabel remove = new JLabel("remove");
	top.add(remove);
	JTextField rm = new JTextField(10);
	JButton rmstuff = new JButton("remove");
	rmstuff.addActionListener(new ActionListener(){
		@Override public void actionPerformed(ActionEvent e){
		    arr.remove(rm.getText());
		    for(int i=0;i<AButtons.size();i++){
			if(AButtons.get(i).getText().equals(rm.getText())){
			    pane.remove(i);
			    AButtons.remove(i);
			}
		    }
		    data.removeAssignment(rm.getText());
		    frame.getProgram().writeFile();
		    rm.setText("");
		    revalidate();
		    repaint();
		}
	    });
	top.add(rm);
	top.add(rmstuff);

	//initialize array of assignments
	arr = data.getCollection();
	
	//create a new button for each assignment
	for(int i=0;i<arr.size();i++){
	    pane.add(createAssignmentButton(arr.get(i)));
	}
	this.add(midpane, BorderLayout.CENTER);
    }

    public JButton createAssignmentButton(Assignment thing){
	JButton newthang = new JButton(thing.getName());
	AssignmentInterface interact = makeNewAssignmentInterface(thing);
	newthang.addActionListener(new ActionListener(){
		@Override public void actionPerformed(ActionEvent e){
		    mvToAssignmentPanel(interact);
		    revalidate();
		    repaint();
		}
	    });
	newthang.setAlignmentX(Component.CENTER_ALIGNMENT);
	AButtons.add(newthang);
	return newthang;
    }

    public AssignmentInterface makeNewAssignmentInterface(Assignment thing){
	AssignmentInterface pane = new AssignmentInterface(frame, this, thing);
	return pane;
    }

    public void mvToAssignmentPanel(AssignmentInterface pane){
	frame.setContentPane(pane);
	revalidate();
	repaint();
    }

    public SaturnGradesGUI getFrame(){
	return frame;
    }
    public SubjectInterface getPane(){
	return pane;
    }
    public Subcategory getData(){
	return data;
    }

    public void setFrame(SaturnGradesGUI newFrame){
	frame=newFrame;
    }
    public void setPane(SubjectInterface newPane){
	pane=newPane;
    }
    public void setData(Subcategory newData){
	data=newData;
    }

    public void addBackButton(){
	JButton back = new JButton("Go Back");
	back.setActionCommand("go_back");
	back.addActionListener(this);
	this.add(back, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e){
	String command = e.getActionCommand();
	if("go_back".equals(command)){
	    frame.setContentPane(pane);
	    revalidate();
	    repaint();
	}
    }
}