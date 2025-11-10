public class MemoryPlacementbestworst {

    // ===== Predefined Input =====
    static int[] blocksOriginal = {100, 50, 200, 75};
    static int[] processes = {212, 60, 50, 90, 40};

    public static void main(String[] args) {
        System.out.println("--- Best Fit ---");
        runAndPrint("Best Fit", Strategy.BEST_FIT);

        System.out.println("\n--- Worst Fit ---");
        runAndPrint("Worst Fit", Strategy.WORST_FIT);
    }

    enum Strategy { BEST_FIT, WORST_FIT }

    static void runAndPrint(String title, Strategy strategy) {
        int[] blocks = blocksOriginal.clone();
        int p = processes.length;
        int b = blocks.length;

        int[] procAllocatedBlock = new int[p]; // -1 if not allocated
        for (int i = 0; i < p; i++) procAllocatedBlock[i] = -1;
        boolean[] blockAllocated = new boolean[b];
        int[] blockAllocatedToProc = new int[b];
        for (int i = 0; i < b; i++) blockAllocatedToProc[i] = -1;

        // ===== Allocation Logic =====
        for (int i = 0; i < p; i++) {
            int chosen = -1;
            int procSize = processes[i];

            if (strategy == Strategy.BEST_FIT) {
                int bestSize = Integer.MAX_VALUE;
                for (int j = 0; j < b; j++) {
                    if (blocks[j] >= procSize && blocks[j] < bestSize) {
                        bestSize = blocks[j];
                        chosen = j;
                    }
                }
            } else if (strategy == Strategy.WORST_FIT) {
                int worstSize = -1;
                for (int j = 0; j < b; j++) {
                    if (blocks[j] >= procSize && blocks[j] > worstSize) {
                        worstSize = blocks[j];
                        chosen = j;
                    }
                }
            }

            if (chosen != -1) {
                procAllocatedBlock[i] = chosen;
                if (!blockAllocated[chosen]) {
                    blockAllocated[chosen] = true;
                    blockAllocatedToProc[chosen] = i;
                }
                blocks[chosen] -= procSize;
            }
        }

        // ===== Output Table (Process Allocation) =====
        System.out.printf("%-8s| %-4s | %-26s | %-12s\n", "Process", "Size", "Block Allocated", "Internal Frag");
        System.out.println("--------+------+----------------------------+--------------");
        int totalInternalFrag = 0;
        int unallocatedCount = 0;

        for (int i = 0; i < p; i++) {
            String procName = "P" + (i + 1);
            int size = processes[i];

            if (procAllocatedBlock[i] == -1) {
                System.out.printf("  %-5s | %-4d | %-26s | %-12s\n", procName, size, "Not Allocated", "-");
                unallocatedCount++;
            } else {
                int blkIdx = procAllocatedBlock[i];
                int originalBlockSize = blocksOriginal[blkIdx];
                int internal = originalBlockSize - size;
                totalInternalFrag += internal;
                String blkLabel = "Block " + (blkIdx + 1) + " (size " + originalBlockSize + ")";
                System.out.printf("  %-5s | %-4d | %-26s | %-12d\n", procName, size, blkLabel, internal);
            }
        }

        // ===== Blocks Summary =====
        System.out.println("\nBlocks:");
        System.out.printf("%-6s| %-4s | %-9s | %-7s\n", "Block", "Size", "Allocated", "Process");
        System.out.println("------+------|-----------|--------");

        for (int j = 0; j < b; j++) {
            String blkName = "B" + (j + 1);
            int size = blocksOriginal[j];
            if (blockAllocated[j]) {
                String procName = "P" + (blockAllocatedToProc[j] + 1);
                System.out.printf("  %-4s | %-4d | %-9s | %-7s\n", blkName, size, "Yes", procName);
            } else {
                System.out.printf("  %-4s | %-4d | %-9s | %-7s\n", blkName, size, "No", "-");
            }
        }

        System.out.println("\nTotals: Internal Frag = " + totalInternalFrag +
                ", Unallocated Processes = " + unallocatedCount);
    }
}
