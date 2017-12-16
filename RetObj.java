import java.util.*;

public class RetObj{

    private Map<State, State> aMap;
    private State finalS;

    public RetObj(Map<State, State> myMap, State s){
        this.aMap = myMap;
        this.finalS = s;
    }

    public Map<State, State> get_Map(){
        return this.aMap;
    }

    public State get_final(){
        return this.finalS;
    }

}
