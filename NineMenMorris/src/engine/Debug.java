package engine;

import java.util.Scanner;

public class Debug {
    static Engine game;
    static Scanner scanner;;
    
    public static void main(String[] args) {
        //autoRun_v1();
        autoRun_v2();
        //interractive();
    }
    
    public static void interractive() {
        game = new Engine();
        scanner = new Scanner(System.in);
        
        while (game.inPlaceMode()) { place(); }
        System.out.println("Done with PLACE mode");
        while (game.inMoveMode()) { move(); }
    }
    
    public static void place() {
        System.out.print("PLACE[P" + game.activePlayer.getName() + "], e.g. a1, b2, e4: ");
        game.place(scanner.nextLine());
    }
    
    public static void move() {
        System.out.print("MOVE [P" + game.activePlayer.getName() + "], e.g. a1-d1, g4-f4: ");
        String input = scanner.nextLine();
        input = input.replaceAll(" ", "").trim();
        String[] addr = input.split("-");
        game.move(addr[0],  addr[1]);
    }

    public static void autoRun_v2() {
        game = new Engine();
        String[] plays = "d2,c3,d3,c5,d1".split(",");
        for (int i = 0; i < plays.length; i++) {
            game.place(plays[i]);
        }
        
        game.remove("c3");
        
        plays = "d5,b6,e5,c5,d1".split(",");
        for (int i = 0; i < plays.length; i++) {
            game.place(plays[i]);
        }
        
        game.remove("d3");
        
        plays = "a1,g1".split(",");
        for (int i = 0; i < plays.length; i++) {
            game.place(plays[i]);
        }
        game.remove("b6");
        plays = "a1,g1".split(",");
        for (int i = 0; i < plays.length; i++) {
            game.place(plays[i]);
        }
    }
    
    
    public static void autoRun_v1() {
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
