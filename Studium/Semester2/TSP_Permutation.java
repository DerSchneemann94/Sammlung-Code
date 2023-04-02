package algo.übungen3;

public class TSP_Permutation {
	public  static void permute(int start, int[] input ) 
	{
		if (start == input.length) 
        {
            // Hier können Sie die aktuelle Permutation überprüfen. Zum Test wird sie hier ausgedruckt

            for (int x: input)
            {
            	System.out.print(x);
        	}
            System.out.println("");
        
        	return;
    	}

        for (int i = start; i < input.length; i++) 
        {
	        // swapping
	        int temp = input[i];
	        input[i] = input[start];
	        input[start] = temp;
	        // swap(input[i], input[start]);
	
	        permute(start + 1, input);
	        // swap(input[i],input[start]);
	
	        int temp2 = input[i];
	        input[i] = input[start];
	        input[start] = temp2;
        }
	}
	
	public static void main(String[] args) 
	{
		int[] a={0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
		permute(0, a);    
	}
}
