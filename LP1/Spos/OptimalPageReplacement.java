import java.util.*;

public class OptimalPageReplacement {
    public static void main(String[] args) {
        int[] refString = {2, 3, 2, 1, 5, 2, 4, 5, 3, 2, 5, 2};
        int frames = 3;

        List<Integer> frameList = new ArrayList<>(); // holds current pages in frames
        int pageFaults = 0;

        System.out.println("Optimal Page Replacement Simulation");
        System.out.println("Reference String: " + Arrays.toString(refString));
        System.out.println("Number of frames: " + frames);
        System.out.println();
        System.out.printf("%-5s %-6s %-12s %-20s\n", "Step", "Page", "Result", "Frames (any order)");
        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < refString.length; i++) {
            int page = refString[i];
            String result;

            if (frameList.contains(page)) {
                result = "Hit";
            } else {
                pageFaults++;
                result = "Page Fault";

                if (frameList.size() < frames) {
                    frameList.add(page);
                } else {
                    // Choose a page to replace: the one whose next use is farthest in future
                    int replaceIndex = -1;
                    int farthestNextUse = -1;

                    for (int j = 0; j < frameList.size(); j++) {
                        int p = frameList.get(j);
                        int nextUse = Integer.MAX_VALUE;
                        for (int k = i + 1; k < refString.length; k++) {
                            if (refString[k] == p) {
                                nextUse = k;
                                break;
                            }
                        }
                        if (nextUse == Integer.MAX_VALUE) {
                            // this page is never used again — best candidate
                            replaceIndex = j;
                            break;
                        }
                        if (nextUse > farthestNextUse) {
                            farthestNextUse = nextUse;
                            replaceIndex = j;
                        }
                    }

                    // replace the chosen page
                    frameList.set(replaceIndex, page);
                }
            }

            // Print current state (frames shown as list)
            System.out.printf("%-5d %-6d %-12s %-20s\n", (i + 1), page, result, frameList);
        }

        System.out.println("----------------------------------------------------------");
        System.out.println("Total Page Faults = " + pageFaults);
    }
}
