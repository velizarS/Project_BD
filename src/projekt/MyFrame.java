package projekt;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class MyFrame extends JFrame {
	
	Connection conn = null;
	PreparedStatement state = null;
	ResultSet result1 = null;
	ResultSet result2 = null;
	
	JTable table1 = new JTable();
	JScrollPane myScroll1 = new JScrollPane(table1);
	JTable table2 = new JTable();
	JScrollPane myScroll2 = new JScrollPane(table2);
	
	JPanel upPanel1 = new JPanel();
	JPanel upPanel2 = new JPanel();
	JPanel midPanel1 = new JPanel();
	JPanel midPanel2 = new JPanel();
	JPanel downPanel1 = new JPanel();
	JPanel downPanel2 = new JPanel();
	
	
	JLabel fnLabel = new JLabel("First Name");
	JLabel lnLabel = new JLabel("Last Name");
	JLabel pLabel = new JLabel("pin");
	JLabel salaryLabel = new JLabel("Salary");
	JLabel diLabel = new JLabel("departmenid");
	 JLabel dLabel = new JLabel("department");
		JLabel lLabel = new JLabel("leader");

	JTextField fnField = new JTextField(20);
	JTextField lnField = new JTextField(20);
	JTextField pField = new JTextField(20);
	JTextField salaryField = new JTextField(20);
	JTextField diField = new JTextField(20);

	JTextField dField = new JTextField(20);
	JTextField lField = new JTextField(20);	
	
	JButton addButton = new JButton("Add");
	JButton delButton = new JButton("Del");
	JButton searchButton = new JButton("Search");
	JButton addButton1 = new JButton("Add.");
	JButton delButton1 = new JButton("Del.");
	JButton searchButton1 = new JButton("Search.");	
	
	public MyFrame(){
		this.setVisible(true);
		this.setSize(500, 500);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.setLayout(new GridLayout(4,2));
		this.add(upPanel1);
		this.add(upPanel2);
		this.add(midPanel1);
        this.add(midPanel2);
		this.add(downPanel1);
		this.add(downPanel2);

		
		//upPanel
	
		upPanel1.setLayout(new GridLayout(5,1));
        upPanel1.add(fnLabel);
		upPanel1.add(fnField);
		upPanel1.add(lnLabel);
		upPanel1.add(lnField);
		upPanel1.add(pLabel);
		upPanel1.add(pField);
		upPanel1.add(salaryLabel);
		upPanel1.add(salaryField);
		upPanel1.add(diLabel);
		upPanel1.add(diField);
		upPanel2.add(dLabel);
		upPanel2.add(dField);
		upPanel2.add(lLabel);
		upPanel2.add(lField);	
		
		//midPanel
		midPanel1.add(addButton);
		midPanel1.add(delButton);
		midPanel1.add(searchButton);
		addButton.addActionListener(new AddAction());
		searchButton.addActionListener(new SearchAction());
		delButton.addActionListener(new delAction());

		midPanel2.add(addButton1);
		midPanel2.add(delButton1);
		midPanel2.add(searchButton1);
		addButton1.addActionListener(new AddAction1());
		searchButton1.addActionListener(new SearchAction1());
		delButton1.addActionListener(new delAction1());
	 //downPanel
		myScroll1.setSize(500, 250);
		downPanel1.add(myScroll1);
		refreshTable1();
		myScroll2.setSize(500, 200);
		downPanel2.add(myScroll2);
		refreshTable2();
	}// end constructor()
	

	public void clear(){
		
		fnField.setText("");
		lnField.setText("");
		pField.setText("");
		salaryField.setText("");
		diField.setText("");
		dField.setText("");
		lField.setText("");
	}// clear()
	
	public ResultSet getAll2(){
		
		conn = DBconnection.getConnection();
		try {
			state = conn.prepareStatement("select department_id,departmen,leader from departments");
			result2 = state.executeQuery();
			return result2;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			return result2;
		}	
	}// end getAll
	
	public void refreshTable2(){
		try {
			table2.setModel(new MyModel(getAll2()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// end refreshTable

	public ResultSet getAll1(){
		
		conn = DBconnection.getConnection();
		try {
			state = conn.prepareStatement("select employees.firstname,employees.lastname,employees.pin,employees.salary,departments.departmen from employees join departments on employees.department_id=departments.department_id");
			result1 = state.executeQuery();
			return result1;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			return result1;
		}	
	}// end getAll
	
	public void refreshTable1(){
		try {
			table1.setModel(new MyModel(getAll1()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// end refreshTable
	
	class SearchAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			conn = DBconnection.getConnection();
			try {
				state = conn.prepareStatement("select e.firstname,e.lastname,e.pin,e.salary,d.departmen from employees e join departments d on d.department_id=e.department_id and e.firstname=?");
				state.setString(1, fnField.getText());
				result1 = state.executeQuery();
				clear();
				table1.setModel(new MyModel(result1));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end actionPerformed()
		
	}// end SearchAction
	
	class AddAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			conn = DBconnection.getConnection();
			String sql1 = "insert into employees(firstname,lastname,pin,salary,department_id) values(?,?,?,?,?)";
			try {
				state = conn.prepareStatement(sql1);
				state.setString(1, fnField.getText());
				state.setString(2, lnField.getText());
				state.setInt(3, Integer.parseInt(pField.getText()));
				state.setDouble(4, Double.parseDouble(salaryField.getText()));
				state.setInt(5, Integer.parseInt(diField.getText()));

				state.execute();
				clear();
				refreshTable1();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end actionPerformed()
		
	}// end AddAction
	class delAction implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			conn = DBconnection.getConnection();
			String sql1 = "delete from  employees where pin=? ";
			try {
				state = conn.prepareStatement(sql1);
				
				state.setInt(1, Integer.parseInt(pField.getText()));
				
				state.execute();
				clear();
				refreshTable1();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end actionPerformed()
		
	}// end AddActionclass SearchAction implements ActionListener{
	class SearchAction1 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			conn = DBconnection.getConnection();
			try {
				state = conn.prepareStatement("select departments.departmen,departments.leader from departments where departmen=?");
				state.setString(1, dField.getText());
				result2 = state.executeQuery();
				clear();
				table2.setModel(new MyModel(result2));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end actionPerformed()
		
	}// end SearchAction
	
	class AddAction1 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			conn = DBconnection.getConnection();
			String sql2 = "insert into departments values(null,?,?)";
			try {
				state = conn.prepareStatement(sql2);
				state.setString(1, dField.getText());
				state.setString(2, lField.getText());
				//state.setInt(5, Integer.parseInt(diField.getText()));

				state.execute();
				clear();
				refreshTable2();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end actionPerformed()
		
	}// end AddAction
	class delAction1 implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			conn = DBconnection.getConnection();
			String sql2 = "delete from  departments where departments.departmen=? ";
			try {
				state = conn.prepareStatement(sql2);
				
				state.setString(1, dField.getText());
				
				state.execute();
				clear();
				refreshTable2();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}// end actionPerformed()
		
	}// end AddActionclass SearchAction implements ActionListener{

	

}// end class


