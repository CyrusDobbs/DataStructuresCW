/* Put your student number and name here
 *
 * Optionally, if you have any comments regarding your submission, put them here. 
 * For instance, specify here if your program does not generate the proper output or does not do it in the correct manner.
 */

import java.util.*;
import java.io.*;

// A class that represents a dense vector and allows you to read/write its elements
class DenseVector
{
	private int[] elements;
	
	public DenseVector(int n)
	{
		elements = new int[n];
	}
	
	public DenseVector(String filename)
	{
        File file = new File(filename);
        ArrayList<Integer> values = new ArrayList<Integer>();

        try {
            Scanner sc = new Scanner(file);
        
            while(sc.hasNextInt()){
            		values.add(sc.nextInt());
            }
            
            sc.close();
            
            elements = new int[ values.size() ];
            for(int i = 0; i < values.size(); ++ i)
            {
            		elements[i] = values.get(i);
            }
        } 
        catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	// Read an element of the vector
	public int getElement(int idx) {
		return elements[idx];
	}
	
	// Modify an element of the vector
	public void setElement(int idx, int value)
	{
		elements[idx] = value;
	}
	
	// Return the number of elements
	public int size()
	{
		return (elements == null) ? 0 : (elements.length);
	}
	
	// Print all the elements 
	public void print()
	{
		if(elements == null) {
			return;
		}
		
		for(int i = 0; i < elements.length; ++ i)
		{
			System.out.println(elements[i]);
		}
	}
}


// A class that represents a sparse matrix
public class SparseMatrix 
{
	// Auxiliary function that prints out the command syntax
	public static void printCommandError()
	{
        System.err.println("ERROR: use one of the following commands");
        System.err.println(" - Read a matrix and print information: java SparseMatrix -i <MatrixFile>");
        System.err.println(" - Read a matrix and print elements: java SparseMatrix -r <MatrixFile>");
        System.err.println(" - Transpose a matrix: java SparseMatrix -t <MatrixFile>");
        System.err.println(" - Add two matrices: java SparseMatrix -a <MatrixFile1> <MatrixFile2>");
        System.err.println(" - Matrix-vector multiplication: java SparseMatrix -v <MatrixFile> <VectorFile>");
	}
	
	
    public static void main(String [] args) throws Exception
    {
        if (args.length < 2) 
        {
        		printCommandError();
        		System.exit(-1);
        }
        
        if(args[0].equals("-i")) 
        {
        		if(args.length != 2) {
            		printCommandError();
            		System.exit(-1);
        		}
        		
        		SparseMatrix mat = new SparseMatrix();
        		mat.loadEntries(args[1]);
        		System.out.println("Read matrix from " + args[1]);
        		System.out.println("The matrix has " + mat.numRows() + " rows and " + mat.numColumns() + " columns");
        		System.out.println("It has " + mat.numNonZeros() + " non-zeros");
        }
        else if(args[0].equals("-r")) 
        {
        		if(args.length != 2) {
            		printCommandError();
            		System.exit(-1);
        		}
        		
        		SparseMatrix mat = new SparseMatrix();
        		mat.loadEntries(args[1]);
        		System.out.println("Read matrix from " + args[1] + ":");
        		mat.print();
        }
        else if(args[0].equals("-t"))
        {
        		if(args.length != 2) {
            		printCommandError();
            		System.exit(-1);
        		}
        		
        		SparseMatrix mat = new SparseMatrix();
        		mat.loadEntries(args[1]);
        		System.out.println("Read matrix from " + args[1]);        		
        		SparseMatrix transpose_mat = mat.transpose();
        		System.out.println();
        		System.out.println("Matrix elements:");
        		mat.print();
        		System.out.println();
        		System.out.println("Transposed matrix elements:");
        		transpose_mat.print();
        }
        else if(args[0].equals("-a"))
        {
        		if(args.length != 3) {
            		printCommandError();
            		System.exit(-1);
        		}
        		
        		SparseMatrix mat1 = new SparseMatrix();
        		mat1.loadEntries(args[1]);
        		System.out.println("Read matrix 1 from " + args[1]);
        		System.out.println("Matrix elements:");
        		mat1.print();
        		
        		System.out.println();
        		SparseMatrix mat2 = new SparseMatrix();
        		mat2.loadEntries(args[2]);
        		System.out.println("Read matrix 2 from " + args[2]);
        		System.out.println("Matrix elements:");
        		mat2.print();
        		SparseMatrix mat_sum1 = mat1.add(mat2);
        		
//        		System.out.println();
//        		mat1.multiplyBy(2);
//        		SparseMatrix mat_sum2 = mat1.add(mat2);
//
//        		mat1.multiplyBy(5);
//        		SparseMatrix mat_sum3 = mat1.add(mat2);
        		
        		System.out.println("Matrix1 + Matrix2 =");
        		mat_sum1.print();
        		System.out.println();
        		
//        		System.out.println("Matrix1 * 2 + Matrix2 =");
//        		mat_sum2.print();
//        		System.out.println();
//
//        		System.out.println("Matrix1 * 10 + Matrix2 =");
//        		mat_sum3.print();
        }
        else if(args[0].equals("-v"))
        {
        		if(args.length != 3) {
            		printCommandError();
            		System.exit(-1);
        		}
        		
        		SparseMatrix mat = new SparseMatrix();
        		mat.loadEntries(args[1]);
        		DenseVector vec = new DenseVector(args[2]);
        		DenseVector mv = mat.multiply(vec);
        		
        		System.out.println("Read matrix from " + args[1] + ":");
        		mat.print();
        		System.out.println();
        		
        		System.out.println("Read vector from " + args[2] + ":");
        		vec.print();
        		System.out.println();
        		
        		System.out.println("Matrix-vector multiplication:");
        		mv.print();
        }
    }

    
    // Loading matrix entries from a text file
    // You need to complete this implementation
    public void loadEntries(String filename)
    {
        File file = new File(filename);

        try
        {
	        Scanner sc = new Scanner(file);
	        int numRows = sc.nextInt();
	        numCols = sc.nextInt();
	        entries = new ArrayList< ArrayList<Entry> >();
	        
	        for(int i = 0; i < numRows; ++ i) {
	        		entries.add(null);
	        }
	        
	        while(sc.hasNextInt())
	        {
	        		// Read the row index, column index, and value of an element
	        		int row = sc.nextInt();
	        		int col = sc.nextInt();
	        		int val = sc.nextInt();
	        		
	        		// If row of entry is null then create row array
                    if (entries.get(row) == null){
                        entries.set(row, new ArrayList<Entry>());
                    }

                    // Add entry to row array
					Entry newEntry = new Entry(col, val);
					entries.get(row).add(newEntry);
	        }
	        
	        // Order entries
            for (ArrayList<Entry> row : entries) {
	            if (row != null){
                    Collections.sort(row, new entryColumnComparator());
                }
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            numCols = 0;
            entries = null;
        }
        print();
    }
    
    // Default constructor
    public SparseMatrix()
    {
    		numCols = 0;
    		entries = null;
    }
    
    
    // A class representing a pair of column index and elements
    private class Entry
    {
    		private int column;	// Column index
    		private int value;	// Element value
    		
    		// Constructor using the column index and the element value
    		public Entry(int col, int val)
    		{
    			this.column = col;
    			this.value = val;
    		}
    		
    		// Copy constructor
    		public Entry(Entry entry)
    		{
    			this(entry.column, entry.value); 
    		}
    		
    		// Read column index
    		int getColumn() 
    		{
    			return column;
    		}
    		
    		// Set column index
    		void setColumn(int col)
    		{
    			this.column = col;
    		}
    		
    		// Read element value
    		int getValue()
    		{
    			return value;
    		}
    		
    		// Set element value
    		void setValue(int val)
    		{
    			this.value = val;
    		}
    }
    
    // Adding two matrices  
    public SparseMatrix add(SparseMatrix M)
    {
        // Create new matrix from currentMatrix
        SparseMatrix newMatrix = new SparseMatrix();
        newMatrix.entries = entries;
        newMatrix.numCols = numCols;

        // Create temporary M matrix
        SparseMatrix tempM = M;

        // Iterate through rows
        int rowCount = 0;
        for (ArrayList<Entry> row : newMatrix.entries) {
            // Iterate through ENTRIES of specified ROW if row not null
            if (row != null) {
                // Iterate through entries
                for (Entry currentEntry: row) {
                    // Search through M to see if it contains entry in the same location as currentEntry
                    int col = currentEntry.getColumn();
                    Entry entryInM = searchArrayForEntryInColumn(tempM.entries.get(rowCount), col);
                    if (entryInM != null){
                        // Add value of entries if they share location
                        currentEntry.setValue(currentEntry.getValue() + entryInM.getValue());
                        // Remove entry from tempM
                        tempM.entries.get(rowCount).remove(entryInM);
                    }
                }
                if (tempM.entries.get(rowCount).size() == 0){
                    tempM.entries.set(rowCount, null);
                    System.out.println("ArrayList exhausted and changed to null");
                }
            }
            rowCount++;
        }

        rowCount = 0;
        for (int i = 0; i < tempM.entries.size(); i++) {
            System.out.println(tempM.entries.get(i));
        }

        for (ArrayList<Entry> row : tempM.entries) {
            // Iterate through entries that aren't null
            if (row != null) {
                for (Entry currentEntry : row) {
                    // If row we are adding entry too is null then we change it to ArrayList
                    if (newMatrix.entries.get(rowCount) == null) {
                        newMatrix.entries.set(rowCount, new ArrayList<Entry>());
                        System.out.println("Changed null to array");
                    }
                    // Add every entry left over to newMatrix
                    newMatrix.entries.get(rowCount).add(currentEntry);
                    System.out.println("ADDED NUMBER LEFT IN MATRIX B");
                }
            }
            rowCount++;
        }
        sortMatrix(newMatrix);
        return newMatrix;
    }

    public void sortMatrix(SparseMatrix matrix2Sort){
        for (ArrayList<Entry> row : matrix2Sort.entries) {
            if (row != null){
                Collections.sort(row, new entryColumnComparator());
            }
        }
    }

    public Entry searchArrayForEntryInColumn(ArrayList<Entry> searchArray, int column ){
	    // https://stackoverflow.com/questions/17526608/how-to-find-an-object-in-an-arraylist-by-property
        return searchArray.stream().filter(c -> c.getColumn() == column).findFirst().orElse(null);
    }
    
    // Transposing a matrix
    public SparseMatrix transpose()
    {
		// Create new matrix object
        SparseMatrix transposedMatrix = new SparseMatrix();

        // Number of cols/rows swap as do each of the index's of values.
        // Create new cols from old rows
		int newNumCols = entries.size();
		int newNumRows = numCols;

		// Initialise new entries
        ArrayList< ArrayList<Entry> > newEntries = new ArrayList< ArrayList<Entry> >();

        // Create a new ArrayList for each row in new entries
        for (int eachRow = 0; eachRow < newNumRows; eachRow++) {
            newEntries.add(null);
        }

        int rowCount = 0;
        // Iterate through old ROWS
        for (ArrayList<Entry> oldRows: entries){

            // Iterate through old ENTRIES of specified ROW if row not null
            if (oldRows != null) {
                for (Entry oldEntry: oldRows) {
                    // new entry values
                    int newRow = oldEntry.getColumn();
                    int newCol = rowCount;
                    int val = oldEntry.getValue();

                    // Check if row of newEntry is null, if so, create row array
                    if (newEntries.get(newRow) == null){
                        newEntries.set(newRow, new ArrayList<Entry>());
                    }

                    // add new row to new entries
                    Entry newEntry = new Entry(newCol, val);
                    newEntries.get(newRow).add(newEntry);
                }
            }

            rowCount++;
        }

        transposedMatrix.entries = newEntries;
        transposedMatrix.numCols = newNumCols;

        return transposedMatrix;
    }
    
    // Matrix-vector multiplication
    public DenseVector multiply(DenseVector v)
    {
    		// Add your code here
        return v;
    }
    
    // Count the number of non-zeros
    public int numNonZeros()
    {
    		// Add your code here
        return 1;
    }
    
    // Multiply the matrix by a scalar, and update the matrix elements
    public void multiplyBy(int scalar)
    {
    		// Add your code here
    }
    
    // Number of rows of the matrix
    public int numRows()
    {
    		if(this.entries != null) {
    			return this.entries.size();
    		}
    		else {
    			return 0;
    		}
    }
    
    // Number of columns of the matrix
    public int numColumns()
    {
    		return this.numCols;
    }
    
    // Output the elements of the matrix, including the zeros
    // Do not modify this method
	public void print()
	{
		int numRows = entries.size();
		for(int i = 0; i < numRows; ++ i)
		{
			ArrayList<Entry> currentRow = entries.get(i);
			int currentCol = -1, entryIdx = -1;
			if(currentRow != null && (!currentRow.isEmpty())) {
				entryIdx = 0;
				currentCol = currentRow.get(entryIdx).getColumn();
			}
			
			for(int j = 0;  j < numCols; ++ j) {
				if(j == currentCol) {
					System.out.print(currentRow.get(entryIdx).getValue());
					entryIdx++;
					currentCol = (entryIdx < currentRow.size()) ? currentRow.get(entryIdx).getColumn() : (-1); 
				}
				else {
					System.out.print(0);
				}
				
				System.out.print(" ");
			}
			
			System.out.println();
		}
	}

    class entryColumnComparator implements Comparator<Entry> {
        public int compare(Entry entry1, Entry entry2) {
            return entry1.getColumn() - entry2.getColumn();
        }
    }
    
    private int numCols;		// Number of columns
    private ArrayList< ArrayList<Entry> > entries;	// Entries for each row
}


