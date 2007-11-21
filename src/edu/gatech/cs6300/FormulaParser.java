package edu.gatech.cs6300;

import java.text.DecimalFormat;

public class FormulaParser
{
	private static final char[] validOperators = {'/','*','+','-'};
	static DecimalFormat formatter = new DecimalFormat("#0.00#");

	private FormulaParser()
	{
		
	}

	private static double evaluate(String leftSide, char oper, String rightSide, int[] parLoc)
	throws IllegalArgumentException
	{
		double total = 0;
		double leftResult = 0;
		double rightResult = 0;
		
		if ((parLoc[0] ==0)&&(parLoc[1] >0)) {
			String totalString = leftSide + oper + rightSide;
			leftSide = totalString.substring(1, parLoc[1]);
			oper = totalString.charAt(parLoc[1]+1);
			rightSide = totalString.substring(parLoc[1]+2, totalString.length());
		}
		
		int operatorLoc = findOperatorLocation(leftSide);
		if( operatorLoc > 0 && operatorLoc < leftSide.length()-1 )
		{
			leftResult = evaluate(leftSide.substring(0,operatorLoc),
					leftSide.charAt(operatorLoc),
					leftSide.substring(operatorLoc+1,leftSide.length()), findHigherPriorityOperatorLocation(leftSide));
		}
		else
		{
			try
			{	
				leftResult = Double.parseDouble(formatter.format(Double.parseDouble(leftSide)));
			}
			catch(Exception e)
			{
				//throw new IllegalArgumentException("Invalid value found in portion of equation: "
				//		+ leftSide);
			}
		}

		operatorLoc = findOperatorLocation(rightSide);
		if( operatorLoc > 0 && operatorLoc < rightSide.length()-1 )
		{
			rightResult = evaluate(rightSide.substring(0,operatorLoc),
					rightSide.charAt(operatorLoc),
					rightSide.substring(operatorLoc+1,rightSide.length()),findHigherPriorityOperatorLocation(rightSide));
		}
		else
		{
			try
			{
				rightResult = Double.parseDouble(rightSide);
			}
			catch(Exception e)
			{
				throw new IllegalArgumentException("Invalid value found in portion of equation: "
						+ rightSide);
			}
		}

		switch(oper)
		{
		case '/':
			total = leftResult / rightResult; break;
		case '*':
			total = leftResult * rightResult; break;
		case '+':
			total = leftResult + rightResult; break;
		case '-':
			total = leftResult - rightResult; break;
		default:
			throw new IllegalArgumentException("Unknown operator.");
		}
		return total;
	}

	private static int[] findHigherPriorityOperatorLocation(String string){
		int[] index = new int[2];
		index[0] = -1;
		index[1] = -1;

		if (string.startsWith("(")){
			index[0] = string.indexOf("(");
			index[1] = string.lastIndexOf(")");
			if(index[1] > 0){
				return index;
			}
		}
		return index;
	}
	
	private static int findOperatorLocation(String string)
	{
		int index = -1;
		for(int i = validOperators.length-1; i >= 0; i--)
		{
			index = string.indexOf(validOperators[i]);
			if(index >= 0)
				return index;
		}
		return index;
	}

	public static double processEquation(String equation)
	throws IllegalArgumentException
	{
		equation = equation.replaceAll(" ", "");
		return evaluate(equation,'+',"0", findHigherPriorityOperatorLocation(equation));
	}
}


