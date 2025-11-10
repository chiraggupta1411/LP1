import java.util.*;

public class MemoryPlacementfirstbest {

    static class Block {
        int size, id;
        boolean allocated;
        int procId; // which process is placed here (0 if none)
        Block(int id, int size) { this.id = id; this.size = size; }
    }

    static class Proc {
        int size, id;
        int blockId; // 0 if not allocated
        Proc(int id, int size) { this.id = id; this.size = size; }
    }

    // ---------- Strategies ----------
    static List<Block> firstFit(List<Block> blocksIn, List<Proc> procsIn) {
        List<Block> blocks = cloneBlocks(blocksIn);
        List<Proc> procs  = cloneProcs(procsIn);
        for (Proc p : procs) {
            for (Block b : blocks) {
                if (!b.allocated && b.size >= p.size) {
                    b.allocated = true; b.procId = p.id; p.blockId = b.id; break;
                }
            }
        }
        printResult("First Fit", blocks, procs);
        return blocks;
    }

    static List<Block> bestFit(List<Block> blocksIn, List<Proc> procsIn) {
        List<Block> blocks = cloneBlocks(blocksIn);
        List<Proc> procs  = cloneProcs(procsIn);
        for (Proc p : procs) {
            int bestIdx = -1, bestSize = Integer.MAX_VALUE;
            for (int i = 0; i < blocks.size(); i++) {
                Block b = blocks.get(i);
                if (!b.allocated && b.size >= p.size && b.size < bestSize) {
                    bestSize = b.size; bestIdx = i;
                }
            }
            if (bestIdx != -1) {
                Block b = blocks.get(bestIdx);
                b.allocated = true; b.procId = p.id; p.blockId = b.id;
            }
        }
        printResult("Best Fit", blocks, procs);
        return blocks;
    }

    // ---------- Utilities ----------
    static List<Block> cloneBlocks(List<Block> in) {
        List<Block> out = new ArrayList<>();
        for (Block b : in) out.add(new Block(b.id, b.size));
        return out;
    }
    static List<Proc> cloneProcs(List<Proc> in) {
        List<Proc> out = new ArrayList<>();
        for (Proc p : in) out.add(new Proc(p.id, p.size));
        return out;
    }

    static void printResult(String title, List<Block> blocks, List<Proc> procs) {
        System.out.println("\n--- " + title + " ---");
        System.out.println("Process | Size | Block Allocated            | Internal Frag");
        System.out.println("--------+------+----------------------------+--------------");
        int totalIF = 0, notAlloc = 0;
        for (Proc p : procs) {
            Block used = null;
            for (Block b : blocks) if (b.procId == p.id) { used = b; break; }
            if (used == null) {
                System.out.printf("  P%-5d| %-4d | %-26s | %-12s\n", p.id, p.size, "Not Allocated", "-");
                notAlloc++;
            } else {
                int frag = used.size - p.size;
                totalIF += frag;
                System.out.printf("  P%-5d| %-4d | Block %d (size %d)         | %-12d\n",
                        p.id, p.size, used.id, used.size, frag);
            }
        }

        System.out.println("\nBlocks:");
        System.out.println("Block | Size | Allocated | Process");
        System.out.println("------+------|-----------|--------");
        for (Block b : blocks) {
            System.out.printf("  B%-4d| %-4d | %-9s | %s\n",
                    b.id, b.size, (b.allocated ? "Yes" : "No"), (b.allocated ? ("P" + b.procId) : "-"));
        }
        System.out.println("\nTotals: Internal Frag = " + totalIF + ", Unallocated Processes = " + notAlloc);
    }

    public static void main(String[] args) {
        // EDIT THESE ARRAYS to try different inputs
        int[] blockSizes = {100, 50, 200, 75};
        int[] processSizes = {212, 60, 50, 90, 40};

        List<Block> blocks = new ArrayList<>();
        for (int i = 0; i < blockSizes.length; i++) blocks.add(new Block(i + 1, blockSizes[i]));

        List<Proc> procs = new ArrayList<>();
        for (int i = 0; i < processSizes.length; i++) procs.add(new Proc(i + 1, processSizes[i]));

        // Run both strategies
        firstFit(blocks, procs);
        bestFit(blocks, procs);
    }
}
