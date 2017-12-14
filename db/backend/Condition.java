package eg.edu.alexu.csd.oop.db.backend;

public class Condition {
	
	private String firstOperand;
	private String secondOperand;
	private char operator;
	
	public String getFirstOperand() {
		return firstOperand;
	}
	public void setFirstOperand(String firstOperand) {
		this.firstOperand = firstOperand;
	}
	public String getSecondOperand() {
		return secondOperand;
	}
	public void setSecondOperand(String secondOperand) {
		this.secondOperand = secondOperand;
	}
	public char getOperator() {
		return operator;
	}
	public void setOperator(char c) {
		this.operator = c;
	}
	

}
