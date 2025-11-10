import java.util.*;

class Process {
    String pid; // Process ID
    int at;     // Arrival Time
    int bt;     // Burst Time
    int rem;    // Remaining Time
    int ct;     // Completion Time
    int tat;    // Turnaround Time
    int wt;     // Waiting Time

    Process(String pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.rem = bt; // Initially, remaining time = burst time
    }
}

public class SJFPreemptive {
    public static void main(String[] args) {
        // Step 1: Create process list (Given data)
        List<Process> procs = new ArrayList<>();
        procs.add(new Process("P1", 10, 2));
        procs.add(new Process("P2", 0, 10));
        procs.add(new Process("P3", 8, 4));
        procs.add(new Process("P4", 5, 5));

        int n = procs.size();
        int time = 0;       // Current CPU time
        int completed = 0;  // Count of completed processes

        // To build the Gantt chart
        List<String> ganttLabels = new ArrayList<>();
        List<Integer> ganttStart = new ArrayList<>();
        List<Integer> ganttEnd = new ArrayList<>();

        // Step 2: Loop until all processes are completed
        while (completed < n) {
            Process cur = null;

            // Step 3: Find process with the shortest remaining time among arrived processes
            for (Process p : procs) {
                if (p.at <= time && p.rem > 0) {
                    if (cur == null || p.rem < cur.rem || (p.rem == cur.rem && p.at < cur.at)) {
                        cur = p;
                    }
                }
            }

            // Step 4: If no process has arrived yet, CPU remains idle
            if (cur == null) {
                // Merge consecutive idle time intervals
                if (ganttLabels.isEmpty() || !ganttLabels.get(ganttLabels.size()-1).equals("idle")) {
                    ganttLabels.add("idle");
                    ganttStart.add(time);
                    ganttEnd.add(time + 1);
                } else {
                    ganttEnd.set(ganttEnd.size()-1, ganttEnd.get(ganttEnd.size()-1) + 1);
                }
                time++;
                continue;
            }

            // Step 5: Execute the current process for 1 unit of time
            if (ganttLabels.isEmpty() || !ganttLabels.get(ganttLabels.size()-1).equals(cur.pid)) {
                ganttLabels.add(cur.pid);
                ganttStart.add(time);
                ganttEnd.add(time + 1);
            } else {
                ganttEnd.set(ganttEnd.size()-1, ganttEnd.get(ganttEnd.size()-1) + 1);
            }

            cur.rem--; // Decrease remaining time by 1
            time++;    // Move clock forward by 1 unit

            // Step 6: If the process finishes, calculate its times
            if (cur.rem == 0) {
                cur.ct = time;               // Completion Time
                cur.tat = cur.ct - cur.at;   // Turnaround Time = CT - AT
                cur.wt = cur.tat - cur.bt;   // Waiting Time = TAT - BT
                completed++;
            }
        }

        // Step 7: Display results
        System.out.println("SJF (Preemptive) Scheduling Simulation\n");
        System.out.printf("%-6s %-6s %-6s %-6s %-6s %-6s\n",
                          "PID", "AT", "BT", "CT", "TAT", "WT");

        double totalTAT = 0, totalWT = 0;
        for (Process p : procs) {
            System.out.printf("%-6s %-6d %-6d %-6d %-6d %-6d\n",
                              p.pid, p.at, p.bt, p.ct, p.tat, p.wt);
            totalTAT += p.tat;
            totalWT += p.wt;
        }

        // Step 8: Print Gantt Chart
        System.out.println();
        System.out.print("Gantt Chart: |");
        for (int i = 0; i < ganttLabels.size(); i++) {
            System.out.print(" " + ganttLabels.get(i) +
                             "(" + ganttStart.get(i) + "->" + ganttEnd.get(i) + ") |");
        }

        // Step 9: Display Averages
        System.out.printf("\n\nAverage Turnaround Time = %.2f\n", totalTAT / n);
        System.out.printf("Average Waiting Time    = %.2f\n", totalWT / n);
    }
}
