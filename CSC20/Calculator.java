import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator implements ActionListener {

	JFrame frame;
	JTextField textfield;
	// JButton array for holding the numbers 0-9
	JButton[] numberButtons = new JButton[10];	
	// JButton array for holding +, -, *, /, ., =, del, clr, and neg
	JButton[] functionButtons = new JButton[9];
	// List of names for the JButton
	JButton addButton, subButton, mulButton, divButton;
	JButton decButton, equButton, delButton, clrButton, negButton;
	JPanel panel;
	
	// Creating the size of the numbers and symbols
	Font myFont = new Font("Ink Free", Font.BOLD,30);
	
	double num1 = 0, num2 = 0, result = 0;
	// holds the add, sub, mul, and div symbol
	char operator;
	
	Calculator() {
		
		// name for the frame
		frame = new JFrame("Calculator");						
		// allow us to close out the program
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// size of the frame
		frame.setSize(420, 550);
		// display the numbers and symbols
		frame.setLayout(null);
		
		// creating a textfield for typing the numbers
		textfield = new JTextField();
		textfield.setBounds(50, 25, 300, 50);
		textfield.setFont(myFont);
		// doesn't allow to type in the textfield
		textfield.setEditable(false);
		
		// the buttons for the symbols
		addButton = new JButton("+");
		subButton = new JButton("-");
		mulButton = new JButton("*");
		divButton = new JButton("/");
		decButton = new JButton(".");
		equButton = new JButton("=");
		delButton = new JButton("Delete");
		clrButton = new JButton("Clear");
		negButton = new JButton("(-)");
		
		// add all of the above buttons into our functionButtons array
		functionButtons[0] = addButton;
		functionButtons[1] = subButton;
		functionButtons[2] = mulButton;
		functionButtons[3] = divButton;
		functionButtons[4] = decButton;
		functionButtons[5] = equButton;
		functionButtons[6] = delButton;
		functionButtons[7] = clrButton;
		functionButtons[8] = negButton;
		
		for(int i=0; i<9; i++) {
			functionButtons[i].addActionListener(this);
			functionButtons[i].setFont(myFont);
			functionButtons[i].setFocusable(false);
		}
		
		for(int i=0; i<10; i++) {
			numberButtons[i] = new JButton(String.valueOf(i));
			numberButtons[i].addActionListener(this);
			numberButtons[i].setFont(myFont);
			numberButtons[i].setFocusable(false);
		}
		
		// displaying the neg, del, and clr button into the calculator
		negButton.setBounds(50, 430, 100, 50);
		delButton.setBounds(150, 430, 100, 50);
		clrButton.setBounds(250, 430, 100, 50);
		
		panel = new JPanel();
		panel.setBounds(50, 100, 300, 300);
		panel.setLayout(new GridLayout(4, 4, 10, 10));
		
		// adding the numbers into the panel
		panel.add(numberButtons[1]);
		panel.add(numberButtons[2]);
		panel.add(numberButtons[3]);
		panel.add(numberButtons[4]);
		panel.add(numberButtons[5]);
		panel.add(numberButtons[6]);
		panel.add(numberButtons[7]);
		panel.add(numberButtons[8]);
		panel.add(numberButtons[9]);
		
		// adding the calculating symbols into the panel
		panel.add(addButton);
		panel.add(subButton);
		panel.add(mulButton);
		panel.add(decButton);
		panel.add(numberButtons[0]);
		panel.add(equButton);
		panel.add(divButton);
		
		// adding the other symbols into the frame
		frame.add(panel);
		frame.add(negButton);
		frame.add(delButton);
		frame.add(clrButton);
		frame.add(textfield);
		frame.setVisible(true);	
	}
	
	public static void main(String[] args) {
		Calculator calc = new Calculator();
	}
	
	public void actionPerformed(ActionEvent e) {
		for(int i=0; i<10; i++) {
			if(e.getSource() == numberButtons[i]) {
				textfield.setText(textfield.getText().concat(String.valueOf(i)));
			}  
		} 
		
		// this if statement is for providing the decimal point
		if(e.getSource() == decButton) {
			textfield.setText(textfield.getText().concat("."));
		}
		
		// this if statement is for providing the add symbol
		if(e.getSource() == addButton) {
			num1 = Double.parseDouble(textfield.getText());
			operator = '+';
			textfield.setText("");
		}
		
		// this if statement is for providing the subtract symbol
		if(e.getSource() == subButton) {
			num1 = Double.parseDouble(textfield.getText());
			operator = '-';
			textfield.setText("");
		}
		
		// this if statement is for providing the multiplication symbol
		if(e.getSource() == mulButton) {
			num1 = Double.parseDouble(textfield.getText());
			operator = '*';
			textfield.setText("");
		}
		
		// this if statement is for providing the division symbol
		if(e.getSource() == divButton) {
			num1 = Double.parseDouble(textfield.getText());
			operator = '/';
			textfield.setText("");
		}
		
		// this is for comparing the symbol with what the user has select
		if(e.getSource() == equButton) {
			num2 = Double.parseDouble(textfield.getText());
			
			switch(operator) {
			case '+':
				result = num1+num2;
				break;
			case '-':
				result = num1-num2;
				break;
			case '*':
				result = num1*num2;
				break;
			case '/':
				result = num1/num2;
				break;
			}
			textfield.setText(String.valueOf(result));
			num1 = result;
		}
		
		// this allow the user to clear 
		if(e.getSource() == clrButton) {
			textfield.setText("");
		}
		
		// this allow the user to delete
		if(e.getSource() == delButton) {
			String string = textfield.getText();
			textfield.setText("");
			
			// a for loop to iterate the length of the string - 1
			for(int i=0; i<string.length()-1; i++) {
				textfield.setText(textfield.getText()+string.charAt(i));
			}
		}
		
		// this will allow me to get a positive number from multiplying a negative number with a negative number
		if(e.getSource() == negButton) {
			double temp = Double.parseDouble(textfield.getText());
			temp *= -1;
			textfield.setText(String.valueOf(temp));
		}
	}
}
