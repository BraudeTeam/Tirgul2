import java.util.ArrayList;

/**
 * This class implements the Runnable interface and is responsible for checking if a target sum 
 * can be achieved by selecting a subset of numbers from the array. The check is performed recursively 
 * in a multi-threaded environment.
 */
public class ThreadCheckArray implements Runnable {

    private boolean flag;
    private boolean[] winArray;
    private SharedData sd;
    private ArrayList<Integer> array;
     private int b;

    /**
     * Constructs a ThreadCheckArray with shared data.
     *
     * @param sd The SharedData object containing the array and the target sum
     */
    public ThreadCheckArray(SharedData sd) {
        this.sd = sd;
        synchronized (sd) {
            array = sd.getArray();
            b = sd.getB();
        }
        winArray = new boolean[array.size()];
    }

    /**
     * Recursively checks if a subset of the array can sum to the target value.
     * Marks elements in winArray as part of the solution if they contribute to the sum.
     * 
     * @param n The index of the current element in the array being considered
     * @param b The remaining sum to check
     */
    void rec(int n, int b) {
        synchronized (sd) {
            if (sd.getFlag()) return;  // Exit if solution already found
        }

        if (n == 1) {
            // Base case: check if the current element equals the target sum
            if (b == 0 || b == array.get(n - 1)) {
                flag = true;
                synchronized (sd) {
                    sd.setFlag(true);  // Set flag in shared data to indicate solution found
                }
            }
            if (b == array.get(n - 1)) winArray[n - 1] = true;  // Mark current element as part of solution
            return;
        }

        // Recurse with or without the current element
        rec(n - 1, b - array.get(n - 1));  
        if (flag) winArray[n - 1] = true;  // Mark current element if part of the solution
        synchronized (sd) {
            if (sd.getFlag()) return;  // Exit if another thread has found a solution
        }
        rec(n - 1, b);
    }

    /**
     * Starts the thread execution. This method determines which logic to apply based on the thread's name.
     * It invokes the recursive method to check for a valid subset sum and updates the shared data with the result.
     */
    @Override
    public void run() {
        if (array.size() != 1) {
            // Thread-specific logic: thread1 checks last element, others check without it
            if (Thread.currentThread().getName().equals("thread1")) {
                rec(array.size() - 1, b - array.get(array.size() - 1));
            } else {
                rec(array.size() - 1, b);
            }
        }

        if (array.size() == 1 && b == array.get(0) && !flag) {
            // Special case: single element array, check if it matches the target
            winArray[0] = true;
            flag = true;
            synchronized (sd) {
                sd.setFlag(true);  // Set flag in shared data to indicate solution found
            }
        }

        if (flag) {
            // If a solution is found, update the shared data with the result
            if (Thread.currentThread().getName().equals("thread1")) {
                winArray[array.size() - 1] = true;  // Mark last element if part of the solution
            }
            synchronized (sd) {
                sd.setWinArray(winArray);  // Store the result in shared data
            }
        }
    }
}
