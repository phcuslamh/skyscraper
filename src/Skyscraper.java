import java.util.Scanner;

public class Skyscraper {
	private int DIM;
	private int[][] arrConfig;
	private int[][] arrVisibility;
	
	// Check if a value is in the array
	private static boolean checkBelongArray(int[] arrInput, int x) {
		boolean outIn = false;
		for (int i = 0; i < arrInput.length; ++i) {
			if (x == arrInput[i]) {
				outIn = true;
				break;
			}
		}
		return outIn;
	}
	
	// Return a 2-dimensional array transposed 
	private static int[][] transposeArray(int[][] arrInput) {
		int len1 = arrInput.length;
		int len2 = arrInput[0].length;
		int[][] outArray = new int[len2][len1];
		
		for (int i = 0; i < len2; ++i) {
			for (int j = 0; j < len1; ++j) {
				outArray[i][j] = arrInput[j][i];
			}
		}
		return outArray;
	}
	
	// Return an array reversed
	private static int[] reverseArray(int[] arrInput) {
		int[] outArr = new int[arrInput.length];
		for (int i = 0; i < arrInput.length; ++i) {
			outArr[i] = arrInput[arrInput.length - 1 - i];
		}
		return outArr;
	}
	
	// Find visibility of an array
	private static int ArrVisibility(int[] arrInput) {
		int visi = 1;
		int max = arrInput[0];
		for (int i = 1; i < arrInput.length; ++i) {
			if (arrInput[i] > max) {
				max = arrInput[i];
				visi += 1;
			}
		}
		return visi;
	}

	// Store visibility in arrVisibility
	private void storeVisibility() {
		for (int i = 0; i < DIM; ++i) {
			arrVisibility[0][i] = ArrVisibility(arrConfig[i]);
			arrVisibility[1][i] = ArrVisibility(reverseArray(arrConfig[i]));
		}
		int[][] TarrConfig = transposeArray(arrConfig);
		for (int i = 0; i < DIM; ++i) {
			arrVisibility[2][i] = ArrVisibility(TarrConfig[i]);
			arrVisibility[3][i] = ArrVisibility(reverseArray(TarrConfig[i]));
		}
	}

	// Print a formatted array
	// Each elements in the array is printed so that each of them take up exactly m spaces, and between the n spaces is a single space
	private static void printFormatted(int[] arrInput, int m) {
		for (int i = 0; i < arrInput.length - 1; ++i) {
			System.out.printf("%" + m + "d ", arrInput[i]);
		}
		System.out.printf("%" + m + "d", arrInput[arrInput.length - 1]);
	}

	// Print the formatted second and next-to-last-second lines
	private static void printHeading(int digi, int dim) {
		for (int i = 0; i < digi + 1; ++i) {System.out.print(" ");}
		for (int i = 0; i < (digi + 1)*dim - 1; ++i) {System.out.print("-");} 
	}

	/* verifyPlacement
	 * Check if the puzzle is valid
	 */
	public boolean verifyPlacement() {
		boolean x = true;
		for (int i = 0; i < DIM; ++i) {
			for (int j = 1; j < DIM + 1; ++j) {
				if (!checkBelongArray(arrConfig[i], j)) {
					x = false;
					return x; 
				}
			}
		}
		
		int[][] TarrConfig = transposeArray(arrConfig);
		for (int i = 0; i < DIM; ++i) {
			for (int j = 1; j < DIM + 1; ++j) {
				if (!checkBelongArray(TarrConfig[i], j)) {
					x = false;
					return x; 
				}
			}
		}
		
		return x;
	}
	
	/* loadPuzzle
	 * Update puzzle for arrConfig
	 */
	public void loadPuzzle(Scanner scnr) {
		Scanner eachLine = null;
		String Line; 
		DIM = scnr.nextInt();
		arrConfig = new int[DIM][DIM];
		arrVisibility = new int[4][DIM];
		scnr.nextLine();
		for (int i = 0; i < DIM; ++i) {
			Line = scnr.nextLine();
			eachLine = new Scanner(Line);
			for (int j = 0; j < DIM; ++j) {
				arrConfig[i][j] = eachLine.nextInt();
			}
		}
		storeVisibility();
	}
	
	/* print
	 * print out the standard configuration
	 */
	public void print( ) {
		System.out.println(DIM);
		for (int i = 0; i < DIM; ++i) {
			for (int j = 0; j < DIM - 1; ++j) {
				System.out.print(arrConfig[i][j] + " ");
			}
			System.out.print(arrConfig[i][DIM - 1]);
			System.out.println();
		}
	}
	
	/* printWithVisibility
	 */
	public void printWithVisibility() {
		int m = (int)(Math.log10(DIM)) + 1;
		System.out.println();
		
		for (int i = 0; i < m + 1; ++i) {System.out.print(" ");}
		printFormatted(arrVisibility[2], m);
		System.out.println(); //Print first line, formatted

		printHeading(m, DIM);
		System.out.println(); //Print second line, formatted
		
		for (int i = 0; i < DIM; ++i) {
			System.out.printf("%" + m + "d", arrVisibility[0][i]);
			System.out.print("|");
			printFormatted(arrConfig[i], m);
			System.out.print("|");
			System.out.print(arrVisibility[1][i]);
			System.out.println();
		} //Print the next big whole thing
		
		printHeading(m, DIM);
		System.out.println(); //Print second-to-last line, formatted
		
		for (int i = 0; i < m + 1; ++i) {System.out.print(" ");}
		printFormatted(arrVisibility[3], m); //Print last line, formatted
	}
	
	public static void main(String[] args) {
		Scanner scnr = new Scanner(System.in);
		Skyscraper Bitexco = new Skyscraper();
		Bitexco.loadPuzzle(scnr);
		//Bitexco.print();
		//System.out.println();
		if (Bitexco.verifyPlacement()) {
			System.out.println("The puzzle above is valid.");
			Bitexco.printWithVisibility();
			}
		else {
			System.out.println("The puzzle above is invalid.");
			}
		scnr.close();
		}
}
