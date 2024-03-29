package Helpers;

public class Helpers {
	public static int pascalTriangleRow(int n) {
		int row = 0;
		while(n > 0) {
			row++;
			n -= row;
		}
		return row;
	}
	
	public static int maxOfThree(int a, int b, int c) {
		int result = a;
		if(result < b)
			result = b;
		if(result < c)
			result = c;
		return result;
	}
}
