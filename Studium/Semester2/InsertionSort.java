package algo.übungen1;

public class InsertionSort{
	
	public static void main(String[] args) {
		int key,j;
		int[] array = {1,8,4,5,9,67,1,4};
		for(int i=1;i<array.length;i++) {
			key = array[i];
			j = i-1;
			while(j >0 && array[j] > key) {
				array[j+1] = array[j];
				j -= 1;
			}
			array[j+1] = key;	
		}
		System.out.println("Array ist sortiert ");
		for(int i = 0; i < array.length; i++) {
			System.out.print(array[i] + " ");
		}
	}
}
	

