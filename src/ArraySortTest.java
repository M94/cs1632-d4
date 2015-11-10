/**
 * Austin Choi
 * CS1632 Deliverable 4
 * Java Array Sort Test [PROPERTY-BASED TESTING]
 */

import static org.junit.Assert.*;
import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import net.java.quickcheck.Generator;
import net.java.quickcheck.generator.PrimitiveGenerators;

public class ArraySortTest {
	
	int intArrays[][];
	char charArrays[][];
	String strArrays[][];
	
	/**
	 * Generate arrays of different types and sizes
	 */
	public void generateArrays (int sizes[]) {
		// Allocate arrays
		int nSizes = sizes.length;
		intArrays = new int[nSizes][];
		charArrays = new char[nSizes][];	
		strArrays = new String[nSizes][];	
		for (int i = 0; i < nSizes; i++) {
			int size = sizes[i];
			charArrays[i] = new char[size];
			intArrays[i] = new int[size];
			strArrays[i] = new String[size];			
		}
		// Fill with random data
		Generator<Character> charGen = PrimitiveGenerators.characters();
		Generator<Integer> intGen = PrimitiveGenerators.integers();
		Generator<String> strGen = PrimitiveGenerators.strings();
		for (int i = 0; i < nSizes; i++) {
			int size = sizes[i];
			for (int s = 0; s < size; s++) {
				charArrays[i][s] = charGen.next();
				intArrays[i][s] = intGen.next();
				strArrays[i][s] = strGen.next();
			}
		}
	}
	
	public void sortArrays(char[][] charArr, int[][] intArr, String[][] strArr) {
		for (char[] a: charArr) {
			Arrays.sort(a);
		}
		for (int[] a: intArr) {
			Arrays.sort(a);
		}
		for (String[] a: strArr) {
			Arrays.sort(a);
		}	
	}
	
	public void sortArrays() {
		sortArrays(charArrays, intArrays, strArrays);
	}

	@Before
	public void setUp() throws Exception {
		int[] sizes = {1, 10,  25, 50, 69, 100, 1000, 10000, 20000, 300000};
		generateArrays(sizes);
	}

	/**
	 * The output array should have the same number of elements
	 * as the input array.
	 */
	@Test
	public void sameTest() {
		// Save input sizes
		int inputSizes[] = new int[charArrays.length];
		for (int i = 0; i < charArrays.length; i++) {
			inputSizes[i] = charArrays[i].length;
		}
		// Sort
		sortArrays();
		// Compare sizes
		for (int i = 0; i < inputSizes.length; i++) {
			int inputSize = inputSizes[i];
			int charOutputSize = charArrays[i].length;
			int intOutputSize = intArrays[i].length;
			int strOutputSize = strArrays[i].length;
			assertEquals(inputSize, charOutputSize);
			assertEquals(inputSize, intOutputSize);
			assertEquals(inputSize, strOutputSize);
		}
	}

	/**
	 * The sort function is pure; running it two times on the same input
	 * array should always produce the same output array.
	 */
	@Test
	public void pureTest() {
		char[][] sameCharArrays= charArrays.clone();
		int[][] sameIntArrays = intArrays.clone();
		String[][] sameStrArrays = strArrays.clone();
		// Sort originals
		sortArrays(); 	
		// Sort copies
		sortArrays(sameCharArrays, sameIntArrays, sameStrArrays);
		// Originals == copies
		assertArrayEquals(charArrays, sameCharArrays);
		assertArrayEquals(intArrays, sameIntArrays);
		assertArrayEquals(strArrays, sameStrArrays);
	}
	
	/**
	 * The sort function is idempotent. Running the sort method twice
	 * on the same array should produce the same output array as just 
	 * running it once.
	 */
	@Test
	public void idempotentTest() {
		// Sort once
		sortArrays();
		// Store sorted arrays
		char[][] sortedCharArrays= charArrays.clone();
		int[][] sortedIntArrays = intArrays.clone();
		String[][] sortedStrArrays = strArrays.clone();		
		// Sort again
		sortArrays();
		// Compare
		assertArrayEquals(charArrays, sortedCharArrays);
		assertArrayEquals(intArrays, sortedIntArrays);
		assertArrayEquals(strArrays, sortedStrArrays);		
	}
	
	/**
	 * The value of each successive element in the output array is 
	 * greater than or equal to the previous value.
	 */
	@Test
	public void orderTest() {
		sortArrays();
		for (int i = 0; i < charArrays.length; i++) {
			for (int j = 0; j < charArrays[i].length - 1; j++) {
				// Current
				char prevChar = charArrays[i][j];
				int prevInt = intArrays[i][j];
				String prevString = strArrays[i][j];
				// Successive 
				char nextChar = charArrays[i][j + 1];
				int nextInt = intArrays[i][j + 1];
				String nextString = strArrays[i][j + 1];
				// Current <= Successive
				assertTrue(prevChar <= nextChar);
				assertTrue(prevInt <= nextInt);
				assertTrue(prevString.compareTo(nextString) <= 0);
			}
		}
	}

}
