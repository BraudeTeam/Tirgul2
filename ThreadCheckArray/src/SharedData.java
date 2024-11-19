import java.util.ArrayList;

/**
 * This class encapsulates shared data between threads. It stores the array of integers, the target sum, 
 * a flag indicating whether a solution has been found, and an array of boolean values marking the subset 
 * contributing to the solution
 */
public class SharedData {
    private ArrayList<Integer> array;
    private boolean[] winArray;
    private boolean flag;
    private final int b;

    /**
     * Constructs a `SharedData` object with the given array and target sum.
     *
     * @param array The list of integers to be checked
     * @param b     The target sum to achieve
     */
    public SharedData(ArrayList<Integer> array, int b) {
        this.array = array;
        this.b = b;
    }

    /**
     * Retrieves the array of boolean values marking the solution subset.
     *
     * @return A boolean array where each element indicates if the corresponding index 
     *         in the array is part of the solution
     */
    public boolean[] getWinArray() {
        return winArray;
    }

    /**
     * Sets the boolean array marking the solution subset.
     *
     * @param winArray A boolean array indicating the solution subset
     */
    public void setWinArray(boolean[] winArray) {
        this.winArray = winArray;
    }

    /**
     * Retrieves the array of integers to be checked.
     *
     * @return The list of integers
     */
    public ArrayList<Integer> getArray() {
        return array;
    }

    /**
     * Retrieves the target sum.
     *
     * @return The target sum to achieve
     */
    public int getB() {
        return b;
    }

    /**
     * Checks if a solution has been found.
     *
     * @return `true` if a solution exists, `false` otherwise
     */
    public boolean getFlag() {
        return flag;
    }

    /**
     * Sets the flag indicating whether a solution has been found.
     *
     * @param flag `true` if a solution exists, `false` otherwise
     */
    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
