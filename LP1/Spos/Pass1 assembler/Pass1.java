import java.io.*;
import java.util.*;

public class Pass1 {
    static String[] prog = {
            "START 100",
            "MOVR AX 05",
            "MOVER BX 10",
            "UP: ADD AX BX",
            "MOVEM A ='5'",
            "ORIGIN UP",
            "LTORG",
            "MOVEM B ='7'",
            "DS A 02",
            "DC B 10",
            "END"
    };

    public static void main(String[] args) throws Exception {
        Tables T = new Tables();
        List<String> ic = new ArrayList<>();
        int LC = 0;

        // literal pools (1-based start index)
        int litPoolStart = 0;
        T.pool.add(new Pool(1));

        for (String raw : prog) {
            String line = raw.trim().replaceAll("\\s+", " ");
            if (line.isEmpty()) continue;

            // label like "UP: ..."
            if (line.contains(":")) {
                String[] sp = line.split(":");
                String label = sp[0].trim().toUpperCase();
                line = sp[1].trim();
                T.sym.putIfAbsent(label, new Symbol(label));
                Symbol s = T.sym.get(label);
                s.address = LC;
                s.defined = true;
                s.length = 1;
            }

            String[] tok = line.split(" ");
            String op = tok[0].toUpperCase();
            if (op.equals("MOVR")) op = "MOVER"; // normalize

            // --- Assembler directives ---
            if (op.equals("START")) {
                LC = Integer.parseInt(tok[1]);
                ic.add("(AD,01) (C," + tok[1] + ")");
                continue;
            }
            if (op.equals("END")) {
                int placed = allocateLiterals(T, litPoolStart, LC);
                LC += placed;
                ic.add("(AD,02)");
                break;
            }
            if (op.equals("ORIGIN")) {
                String sym = tok[1].toUpperCase();
                Symbol s = T.sym.get(sym);
                if (s == null || !s.defined) throw new RuntimeException("Undefined symbol in ORIGIN: " + sym);
                LC = s.address;
                ic.add("(AD,03) (S," + sym + ")");
                continue;
            }
            if (op.equals("LTORG")) {
                int placed = allocateLiterals(T, litPoolStart, LC);
                LC += placed;
                litPoolStart = T.lit.size();
                T.pool.add(new Pool(T.lit.size() + 1));
                ic.add("(AD,05)");
                continue;
            }

            // --- Declarations (DL) — DL,01=DC, DL,02=DS ---
            if (op.equals("DS") || op.equals("DC")) {
                String sym = tok[1].toUpperCase();
                int val = Integer.parseInt(tok[2]);
                T.sym.putIfAbsent(sym, new Symbol(sym));
                Symbol s = T.sym.get(sym);
                s.address = LC;
                s.defined = true;

                if (op.equals("DS")) {
                    s.length = val;
                    ic.add("(DL,02) (S," + sym + ") (C," + val + ")");
                    LC += val;
                } else { // DC
                    s.length = 1;
                    ic.add("(DL,01) (S," + sym + ") (C," + val + ")");
                    LC += 1;
                }
                continue;
            }

            // --- Imperative statements (IS) ---
            OpInfo oi = T.optab.get(op);
            if (oi == null) throw new RuntimeException("Unknown op: " + op);

            String opnd1 = tok.length > 1 ? tok[1].toUpperCase() : null;
            String opnd2 = tok.length > 2 ? String.join(" ", Arrays.copyOfRange(tok, 2, tok.length)).toUpperCase() : null;

            // Expand "MOVEM <SYM> ='<lit>'" into two instructions:
            //   MOVER <RG>, <lit>  ; MOVEM <RG>, <SYM>
            if (op.equals("MOVEM")
                    && opnd1 != null
                    && opnd2 != null
                    && opnd1.matches("[A-Z][A-Z0-9]*")
                    && (opnd2.matches("=\\'.*\\'") || opnd2.matches("=\\d+"))) {

                String symName = opnd1;
                int regId = guessRegisterForSymbol(T, symName);

                // 1) MOVER <reg>, <literal>
                OpInfo moverInfo = T.optab.get("MOVER");
                if (moverInfo == null) throw new RuntimeException("MOVER not in optab");
                String litNorm = normalizeLit(opnd2);
                int litIdx = ensureLiteral(T, litNorm);
                ic.add("(IS," + String.format("%02d", moverInfo.code) + ") (RG," + regId + ") (L," + (litIdx + 1) + ")");
                LC += 1;

                // 2) MOVEM <reg>, <symbol>
                T.sym.putIfAbsent(symName, new Symbol(symName));
                ic.add("(IS," + String.format("%02d", oi.code) + ") (RG," + regId + ") (S," + symName + ")");
                LC += 1;
                continue;
            }

            // General case
            StringBuilder row = new StringBuilder();
            row.append("(IS,").append(String.format("%02d", oi.code)).append(") ");
            if (opnd1 != null) appendOperandIC(row, T, opnd1);
            if (opnd2 != null) appendOperandIC(row, T, opnd2);
            ic.add(row.toString().trim());
            LC += 1;
        }

        // ---- Output ----
        try (PrintWriter out = new PrintWriter(new FileWriter("Output.txt"))) {
            printAndWrite(out, "Intermediate Code:");
            for (String s : ic) printAndWrite(out, s);

            // stable, sorted Symbol Table: by address then name
            List<Symbol> syms = new ArrayList<>(T.sym.values());
            syms.sort(Comparator
                    .comparingInt((Symbol s) -> s.address)
                    .thenComparing(s -> s.name));

            printAndWrite(out, "\nSymbol Table:");
            int i = 1;
            for (Symbol s : syms)
                printAndWrite(out, i++ + "\t" + s.name + "\t" + s.address + "\t" + s.length);

            printAndWrite(out, "\nLiteral Table:");
            for (int j = 0; j < T.lit.size(); j++)
                printAndWrite(out, (j + 1) + "\t" + T.lit.get(j).lit + "\t" + T.lit.get(j).address);

            printAndWrite(out, "\nPool Table:");
            for (Pool p : T.pool)
                printAndWrite(out, String.valueOf(p.startIndex));
        }
    }

    // ---------- helpers ----------

    static void printAndWrite(PrintWriter out, String text) {
        System.out.println(text);
        out.println(text);
    }

    static int ensureLiteral(Tables T, String lit) {
        for (int i = 0; i < T.lit.size(); i++)
            if (T.lit.get(i).lit.equals(lit)) return i;
        T.lit.add(new Literal(lit));
        return T.lit.size() - 1;
    }

    static String normalizeLit(String s) {
        if (s.startsWith("='") && s.endsWith("'")) return s;
        if (s.startsWith("=")) return "='" + s.substring(1) + "'";
        return s;
    }

    // Allocate literals from index 'from'; return how many were placed
    static int allocateLiterals(Tables T, int from, int LC) {
        int placed = 0;
        for (int i = from; i < T.lit.size(); i++) {
            if (T.lit.get(i).address == -1) {
                T.lit.get(i).address = LC;
                LC++;
                placed++;
            }
        }
        return placed;
    }

    static int guessRegisterForSymbol(Tables T, String symName) {
        if (symName.equals("A") && T.regtab.containsKey("AX")) return T.regtab.get("AX");
        if (symName.equals("B") && T.regtab.containsKey("BX")) return T.regtab.get("BX");
        if (T.regtab.containsKey("AX")) return T.regtab.get("AX");
        return 1; // fallback
        }
    static void appendOperandIC(StringBuilder row, Tables T, String opnd) {
        if (T.regtab.containsKey(opnd)) {
            row.append("(RG,").append(T.regtab.get(opnd)).append(") ");
        } else if (opnd.matches("[A-Z][A-Z0-9]*")) {
            T.sym.putIfAbsent(opnd, new Symbol(opnd));
            row.append("(S,").append(opnd).append(") ");
        } else if (opnd.matches("=\\'.*\\'") || opnd.matches("=\\d+")) {
            int idx = ensureLiteral(T, normalizeLit(opnd));
            row.append("(L,").append(idx + 1).append(") ");
        } else {
            row.append("(C,").append(opnd).append(") ");
        }
    }
}
