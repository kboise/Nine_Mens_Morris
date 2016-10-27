package engine;

public class Debug {
    static Engine game;
    
    public static void main(String[] args) {
        game = new Engine();
        
        game.place("d2");
        
        // attempt to exhaust player's play turns
        for (int i = 1; i <= 6; i++) { game.place("b" + i); }
        
        game.place("c5");
        game.place("c5");    // play disabled, already owned
        
        // attempt to exhaust both player's play turns
        for (int i = 5; i <= 7; i++) { game.place("G" + i); }
        
        game.place("c2");
        
        for (int i = 4; i <= 7; i++) { game.place("d" + i); }

        game.place("g7");
        game.place("g4");
        game.place("g1");
        
        // attempt to exhaust both player's play turns
        for (int i = 2; i <= 6; i = i+2) { game.place("F" + i); }
        
        // attempt to exhaust both player's play turns
        for (int i = 5; i >= 1; i = i-1) { game.place("E" + i); }
        
        // attempt to exhaust both player's play turns
        for (int i = 7; i >= 1; i = i-1) { game.place("A" + i); }
        
        game.move("g1",  "d1");
        game.move("e3",  "d3");
        game.move("a7",  "a4");
        game.move("d5",  "d6");
        game.move("e3",  "d3");
    }
}
