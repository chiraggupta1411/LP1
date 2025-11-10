import java.util.*;

class Process {
    String pid;
    int at; // arrival time
    int bt; // burst time
    int ct; // completion time
    int tat; // turnaround time
    int wt; // waiting time

    Process(String pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
    }
}

public class FCFS {
    public static void main(String[] args) {
        // Input processes (from user)
        List<Process> procs = new ArrayList<>();
        procs.add(new Process("P1", 10, 2));
        procs.add(new Process("P2", 0, 10));
        procs.add(new Process("P3", 8, 4));
        procs.add(new Process("P4", 5, 5));

        // Sort by arrival time (stable sort keeps original order for equal AT)
        procs.sort(Comparator.comparingInt(p -> p.at));

        int time = 0;
        StringBuilder gantt = new StringBuilder();
        gantt.append("Gantt: |");

        double totalWT = 0;
        double totalTAT = 0;

        for (Process p : procs) {
            // If CPU is idle until process arrives, advance time
            if (time < p.at) {
                // represent idle visually in Gantt
                gantt.append(" idle(").append(p.at - time).append(") |");
                time = p.at;
            }

            int start = time;
            time += p.bt; // process runs
            p.ct = time;
            p.tat = p.ct - p.at;
            p.wt = p.tat - p.bt;

            // append to Gantt
            gantt.append(" ").append(p.pid).append("(").append(start).append("->").append(p.ct).append(") |");

            totalWT += p.wt;
            totalTAT += p.tat;
        }

        // Print results
        System.out.println("FCFS Scheduling Simulation\n");
        System.out.printf("%-6s %-6s %-6s %-6s %-6s %-6s\n",
                "PID", "AT", "BT", "CT", "TAT", "WT");
        for (Process p : procs) {
            System.out.printf("%-6s %-6d %-6d %-6d %-6d %-6d\n",
                    p.pid, p.at, p.bt, p.ct, p.tat, p.wt);
        }

        int n = procs.size();
        System.out.println();
        System.out.println(gantt.toString());
        System.out.printf("\nAverage Turnaround Time = %.2f\n", totalTAT / n);
        System.out.printf("Average Waiting Time    = %.2f\n", totalWT / n);
    }
}
