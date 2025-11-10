import java.util.*;

public class LRUPageReplacement {
    public static void main(String[] args) {
        int[] refString = {2, 3, 2, 1, 5, 2, 4, 5, 3, 2, 5, 2};
        int frames = 3;

        // frameList holds pages currently in frames (size <= frames)
        List<Integer> frameList = new ArrayList<>();

        // lastUsed maps a page -> last time index it was used (higher => more recent)
        Map<Integer, Integer> lastUsed = new HashMap<>();

        int pageFaults = 0;

        System.out.println("LRU Page Replacement Simulation");
        System.out.println("Reference String: " + Arrays.toString(refString));
        System.out.println("Number of frames: " + frames);
        System.out.println();
        System.out.printf("%-5s %-6s %-12s %-20s\n", "Step", "Page", "Result", "Frames");
        System.out.println("----------------------------------------------------------");

        for (int i = 0; i < refString.length; i++) {
            int page = refString[i];
            String result;

            if (frameList.contains(page)) {
                // Hit: update last used time
                result = "Hit";
                lastUsed.put(page, i);
            } else {
                // Page fault
                pageFaults++;
                result = "Page Fault";

                if (frameList.size() < frames) {
                    // empty slot available: just load page
                    frameList.add(page);
                    lastUsed.put(page, i);
                } else {
                    // Need to replace the least recently used page
                    int lruPage = -1;
                    int lruTime = Integer.MAX_VALUE;
                    for (int p : frameList) {
                        int t = lastUsed.getOrDefault(p, -1);
                        if (t < lruTime) {
                            lruTime = t;
                            lruPage = p;
                        }
                    }
                    // replace lruPage with current page
                    int idx = frameList.indexOf(lruPage);
                    frameList.set(idx, page);

                    // remove old page's lastUsed entry (optional)
                    lastUsed.remove(lruPage);

                    // set last used time for new page
                    lastUsed.put(page, i);
                }
            }

            // print current frame contents
            System.out.printf("%-5d %-6d %-12s %-20s\n", (i + 1), page, result, frameList);
        }

        System.out.println("----------------------------------------------------------");
        System.out.println("Total Page Faults = " + pageFaults);
    }
}
