import java.util.*;

public class FIFOPageReplacement {
    public static void main(String[] args) {
        int[] refString = {2, 3, 2, 1, 5, 2, 4, 5, 3, 2, 5, 2};
        int frames = 3;

        Queue<Integer> fifoQueue = new LinkedList<>(); // order of pages in frames (FIFO)
        Set<Integer> frameSet = new HashSet<>();       // quick membership check
        int pageFaults = 0;

        System.out.println("FIFO Page Replacement Simulation");
        System.out.println("Reference String: " + Arrays.toString(refString));
        System.out.println("Number of frames: " + frames);
        System.out.println();
        System.out.printf("%-5s %-6s %-12s %-20s\n", "Step", "Page", "Result", "Frames (oldest->newest)");
        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < refString.length; i++) {
            int page = refString[i];
            String result;

            if (frameSet.contains(page)) {
                result = "Hit";
            } else {
                pageFaults++;
                result = "Page Fault";
                if (frameSet.size() < frames) {
                    // empty slot available
                    fifoQueue.add(page);
                    frameSet.add(page);
                } else {
                    // replace the oldest page
                    int removed = fifoQueue.poll();
                    frameSet.remove(removed);
                    fifoQueue.add(page);
                    frameSet.add(page);
                }
            }

            // build printable frame list in FIFO order
            List<Integer> frameList = new ArrayList<>(fifoQueue);
            System.out.printf("%-5d %-6d %-12s %-20s\n", (i + 1), page, result, frameList);
        }

        System.out.println("----------------------------------------------------------");
        System.out.println("Total Page Faults = " + pageFaults);
    }
}
