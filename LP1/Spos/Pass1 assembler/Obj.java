import java.util.*;

class Symbol {
    String name;
    int address;
    int length;
    boolean defined;
    Symbol(String n){ name=n; address=-1; length=1; defined=false; }
}

class Literal {
    String lit;
    int address;
    Literal(String l){ lit=l; address=-1; }
}

class Pool {
    int startIndex;
    Pool(int s){ startIndex=s; }
}

class OpInfo {
    String type;
    int code;
    OpInfo(String t,int c){ type=t; code=c; }
}

class Tables {
    Map<String,Symbol> sym=new LinkedHashMap<>();
    List<Literal> lit=new ArrayList<>();
    List<Pool> pool=new ArrayList<>();
    Map<String,OpInfo> optab=new HashMap<>();
    Map<String,Integer> regtab=new HashMap<>();
    Tables(){
        optab.put("START",new OpInfo("AD",1));
        optab.put("END",new OpInfo("AD",2));
        optab.put("ORIGIN",new OpInfo("AD",3));
        optab.put("LTORG",new OpInfo("AD",5));
        optab.put("DS",new OpInfo("DL",1));
        optab.put("DC",new OpInfo("DL",2));
        optab.put("MOVR",new OpInfo("IS",1));
        optab.put("MOVER",new OpInfo("IS",2));
        optab.put("ADD",new OpInfo("IS",3));
        optab.put("MOVEM",new OpInfo("IS",4));
        regtab.put("AX",1);
        regtab.put("BX",2);
        regtab.put("CX",3);
        regtab.put("DX",4);
    }
}
