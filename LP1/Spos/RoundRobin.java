import java.util.*;

class Process {
    String pid;
    int at;    // Arrival Time
    int bt;    // Burst Time
    int rem;   // Remaining Time
    int ct;    // Completion Time
    int tat;   // Turnaround Time
    int wt;    // Waiting Time

    Process(String pid, int at, int bt) {
        this.pid = pid;
        this.at = at;
        this.bt = bt;
        this.rem = bt; // initially, remaining = burst time
    }
}

public class RoundRobin {
    public static void main(String[] args) {
        int tq = 1; // Time Quantum = 1 sec

        // Step 1: Define processes
        List<Process> procs = new ArrayList<>();
        procs.add(new Process("P1", 10, 2));
        procs.add(new Process("P2", 0, 10));
        procs.add(new Process("P3", 8, 4));
        procs.add(new Process("P4", 5, 5));

        // Step 2: Sort by arrival time
        procs.sort(Comparator.comparingInt(p -> p.at));

        Queue<Process> readyQueue = new LinkedList<>();
        int time = 0;
        int completed = 0;

        StringBuilder gantt = new StringBuilder();
        gantt.append("Gantt: |");

        // Step 3: Loop until all processes complete
        while (completed < procs.size()) {

            // Add newly arrived processes to ready queue
            for (Process p : procs) {
                if (p.at == time) {
                    readyQueue.add(p);
                }
            }

            // Step 4: If CPU is idle
            if (readyQueue.isEmpty()) {
                time++;
                continue;
            }

            // Step 5: Pick process from front of queue
            Process current = readyQueue.poll();

            // Execute for 1 time quantum (1 sec)
            gantt.append(" ").append(current.pid).append("(").append(time).append("->").append(time + tq).append(") |");

            current.rem -= tq;
            time += tq;

            // Add any process that arrived during this 1 sec
            for (Process p : procs) {
                if (p.at > (time - tq) && p.at <= time && !readyQueue.contains(p) && p.rem > 0 && !p.equals(current)) {
                    readyQueue.add(p);
                }
            }

            // If the process is finished
            if (current.rem == 0) {
                current.ct = time;
                current.tat = current.ct - current.at;
                current.wt = current.tat - current.bt;
                completed++;
            } else {
                // Process not finished, re-add to queue
                readyQueue.add(current);
            }
        }

        // Step 6: Display results
        System.out.println("Round Robin Scheduling Simulation (TQ = " + tq + " sec)\n");
        System.out.printf("%-6s %-6s %-6s %-6s %-6s %-6s\n", "PID", "AT", "BT", "CT", "TAT", "WT");

        double totalTAT = 0, totalWT = 0;
        for (Process p : procs) {
            System.out.printf("%-6s %-6d %-6d %-6d %-6d %-6d\n",
                    p.pid, p.at, p.bt, p.ct, p.tat, p.wt);
            totalTAT += p.tat;
            totalWT += p.wt;
        }

        System.out.println();
        System.out.println(gantt.toString());
        System.out.printf("\nAverage Turnaround Time = %.2f\n", totalTAT / procs.size());
        System.out.printf("Average Waiting Time    = %.2f\n", totalWT / procs.size());
    }
}
