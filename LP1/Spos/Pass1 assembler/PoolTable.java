import java.util.*;
import java.io.*;


public class PoolTable {
    private final List<Integer> poolStart;

    public PoolTable() {
        poolStart = new ArrayList<>();
        poolStart.add(1); // first pool always starts at literal 1
    }

    public void addPool(int startIndex) {
        poolStart.add(startIndex);
    }

    public List<Integer> getPoolStart() {
        return poolStart;
    }

    public void printPoolTable() {
        System.out.println("\nPOOL TABLE:");
        for (int i = 0; i < poolStart.size(); i++) {
            System.out.println((i + 1) + "\t" + poolStart.get(i));
        }
    }

    public void writeToFile(PrintWriter out) {
        out.println("Pool Table:");
        for (int start : poolStart) {
            out.println(start);
        }
    }
}
