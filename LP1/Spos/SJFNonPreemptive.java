import java.util.*;

class Process {
    String pid;
    int at;   // arrival time
    int bt;   // burst time
    int ct;   // completion time
    int tat;  // turnaround time
    int wt;   // waiting time
    boolean done;

    Process(String pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.done = false;
    }
}

public class SJFNonPreemptive {
    public static void main(String[] args) {
        // Given processes
        List<Process> procs = new ArrayList<>();
        procs.add(new Process("P1", 10, 2));
        procs.add(new Process("P2", 0, 10));
        procs.add(new Process("P3", 8, 4));
        procs.add(new Process("P4", 5, 5));

        // Sort by arrival time primarily (helps tie-breaking)
        procs.sort(Comparator.comparingInt(p -> p.at));

        int n = procs.size();
        int completed = 0;
        int time = 0;

        StringBuilder gantt = new StringBuilder();
        gantt.append("Gantt: |");

        while (completed < n) {
            // Find all arrived and not-done processes; pick one with smallest BT
            Process next = null;
            for (Process p : procs) {
                if (!p.done && p.at <= time) {
                    if (next == null || p.bt < next.bt ||
                        (p.bt == next.bt && p.at < next.at)) {
                        next = p;
                    }
                }
            }

            // If no process has arrived yet, advance time to the next arrival
            if (next == null) {
                // find the earliest arrival among remaining
                int nextArrival = Integer.MAX_VALUE;
                for (Process p : procs) {
                    if (!p.done && p.at < nextArrival) nextArrival = p.at;
                }
                // represent idle time (optional)
                if (time < nextArrival) {
                    gantt.append(" idle(").append(time).append("->").append(nextArrival).append(") |");
                    time = nextArrival;
                }
                continue;
            }

            // Run 'next' to completion (non-preemptive)
            int start = time;
            time += next.bt;
            next.ct = time;
            next.tat = next.ct - next.at;
            next.wt = next.tat - next.bt;
            next.done = true;
            completed++;

            // append to Gantt
            gantt.append(" ").append(next.pid).append("(").append(start).append("->").append(next.ct).append(") |");
        }

        // Print results
        System.out.println("SJF (Non-Preemptive) Scheduling Simulation\n");
        System.out.printf("%-6s %-6s %-6s %-6s %-6s %-6s\n", "PID", "AT", "BT", "CT", "TAT", "WT");

        double totalTAT = 0, totalWT = 0;
        // Print in original PID order if you prefer; here printing in arrival-sorted order
        for (Process p : procs) {
            System.out.printf("%-6s %-6d %-6d %-6d %-6d %-6d\n",
                    p.pid, p.at, p.bt, p.ct, p.tat, p.wt);
            totalTAT += p.tat;
            totalWT += p.wt;
        }

        System.out.println();
        System.out.println(gantt.toString());
        System.out.printf("\nAverage Turnaround Time = %.2f\n", totalTAT / n);
        System.out.printf("Average Waiting Time    = %.2f\n", totalWT / n);
    }
}
