import java.util.*;

class Process {
    String pid;
    int at;     // arrival time
    int bt;     // burst time
    int pr;     // priority (lower value => higher priority)
    int ct;     // completion time
    int tat;    // turnaround time
    int wt;     // waiting time
    boolean done;

    Process(String pid, int at, int bt, int pr) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.pr = pr;
        this.done = false;
    }
}

public class PriorityNonPreemptive {
    public static void main(String[] args) {
        List<Process> procs = new ArrayList<>();

        // Given processes + default priorities (change these as needed)
        // Convention: lower priority number => higher priority
        procs.add(new Process("P1", 10, 2, 2)); // priority 2
        procs.add(new Process("P2", 0, 10, 1)); // priority 1 (highest)
        procs.add(new Process("P3", 8, 4, 3));  // priority 3
        procs.add(new Process("P4", 5, 5, 4));  // priority 4

        // Optional: sort by arrival time to make tie-breaking consistent
        procs.sort(Comparator.comparingInt(p -> p.at));

        int n = procs.size();
        int completed = 0;
        int time = 0;

        StringBuilder gantt = new StringBuilder();
        gantt.append("Gantt: |");

        while (completed < n) {
            Process next = null;
            for (Process p : procs) {
                if (!p.done && p.at <= time) {
                    if (next == null
                        || p.pr < next.pr                                // higher priority (lower number)
                        || (p.pr == next.pr && p.at < next.at)           // tie-breaker: earlier arrival
                        || (p.pr == next.pr && p.at == next.at && p.pid.compareTo(next.pid) < 0)) {
                        next = p;
                    }
                }
            }

            if (next == null) {
                // no process has arrived yet -> advance time to next arrival
                int nextArrival = Integer.MAX_VALUE;
                for (Process p : procs) {
                    if (!p.done && p.at < nextArrival) nextArrival = p.at;
                }
                if (time < nextArrival) {
                    gantt.append(" idle(").append(time).append("->").append(nextArrival).append(") |");
                    time = nextArrival;
                }
                continue;
            }

            // Run selected process to completion (non-preemptive)
            int start = time;
            time += next.bt;
            next.ct = time;
            next.tat = next.ct - next.at;
            next.wt = next.tat - next.bt;
            next.done = true;
            completed++;

            gantt.append(" ").append(next.pid).append("(").append(start).append("->").append(next.ct).append(") |");
        }

        System.out.println("Priority Scheduling (Non-Preemptive) Simulation\n");
        System.out.printf("%-6s %-6s %-6s %-8s %-6s %-6s %-6s\n", "PID", "AT", "BT", "PRIORITY", "CT", "TAT", "WT");

        double totalTAT = 0, totalWT = 0;
        for (Process p : procs) {
            System.out.printf("%-6s %-6d %-6d %-8d %-6d %-6d %-6d\n",
                    p.pid, p.at, p.bt, p.pr, p.ct, p.tat, p.wt);
            totalTAT += p.tat;
            totalWT += p.wt;
        }

        System.out.println();
        System.out.println(gantt.toString());
        System.out.printf("\nAverage Turnaround Time = %.2f\n", totalTAT / n);
        System.out.printf("Average Waiting Time    = %.2f\n", totalWT / n);
    }
}
