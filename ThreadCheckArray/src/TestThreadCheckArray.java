import java.util.ArrayList;
import java.util.Scanner;

/**
 * This class serves as the entry point to test the functionality of the `ThreadCheckArray` class. 
 * It creates a multi-threaded environment to check whether a subset of an array can sum up to a 
 * given target value
 */
public class TestThreadCheckArray {

    /**
     * The main method initializes the array, the target sum, and shared data, and then creates 
     * and starts threads to perform the subset sum check. The results are displayed at the end.
     *
     * @param args Command-line arguments (not used in this program).
     */
    public static void main(String[] args) {
        try (Scanner input = new Scanner(System.in)) {
            Thread thread1, thread2;

            // Input array size
            System.out.println("Enter array size");
            int num = input.nextInt();

            ArrayList<Integer> array = new ArrayList<>();
            System.out.println("Enter numbers for array");

            // Populate the array with user input
            for (int index = 0; index < num; index++) 
                array.add(index, input.nextInt());

            // Input the target sum
            System.out.println("Enter number");
            num = input.nextInt();

            // Create shared data
            SharedData sd = new SharedData(array, num);

            // Create and start threads
            thread1 = new Thread(new ThreadCheckArray(sd), "thread1");
            thread2 = new Thread(new ThreadCheckArray(sd), "thread2");
            thread1.start();
            thread2.start();

            // Wait for threads to complete
            try {
                thread1.join();
                thread2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Check the result and display output
            if (!sd.getFlag()) {
                System.out.println("Sorry");
                return;
            }

            System.out.println("Solution for b : " + sd.getB() + ", n = " + sd.getArray().size());
            
            // Print indices
            System.out.print("I:    ");
            for (int index = 0; index < sd.getArray().size(); index++)
                System.out.print(index + "    ");
            System.out.println();

            // Print array values
            System.out.print("A:    ");
            for (int index : sd.getArray()) {
                System.out.print(index);
                int counter = 5;
                while (true) {
                    index = index / 10;
                    counter--;
                    if (index == 0)
                        break;
                }
                for (int i = 0; i < counter; i++)
                    System.out.print(" ");
            }
            System.out.println();

            // Print solution flags
            System.out.print("C:    ");
            for (boolean index : sd.getWinArray()) {
                if (index)
                    System.out.print("1    ");
                else
                    System.out.print("0    ");
            }
        }
    }
}
