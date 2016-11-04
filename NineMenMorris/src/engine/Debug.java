package engine;

import java.util.Scanner;

public class Debug {
    static Engine game;
    static Scanner scanner;;
    
    public static void main(String[] args) {
        //autoRun_v1();
        //autoRun_v2();
        autoRun_v3();
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
    
    public static void autoRun_v3() {
        game = new Engine();
        String[] plays = "a1,b2,d1,d2,a4,f2,g5".split(",");
        for (int i = 0; i < plays.length; i++) {
            game.place(plays[i]);
        }
        
        game.remove("a4");
        
        plays = "g1".split(",");
        for (int i = 0; i < plays.length; i++) {
            game.place(plays[i]);
        }
        
        game.remove("d2");
        
        /*
        plays = "d3,d2,c3,e3,c4,c5,b4,a4,a7,d7,f4".split(",");
        for (int i = 0; i < plays.length; i++) {
            game.place(plays[i]);
        }
        
        game.move("g1", "g4");
        game.move("f4", "f6");
        game.move("g4", "g1");
        game.remove("f6");
        */
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
        plays = "a1,g1,g4,g7,d7,a7,b6,b4,b2,c3".split(",");
        for (int i = 0; i < plays.length; i++) {
            game.place(plays[i]);
        }
        
        plays = "f2".split(",");
        for (int i = 0; i < plays.length; i++) {
            game.place(plays[i]);
        }

        game.move("g4","f4");
        
        game.cBoard.getVacantNeighbors("a1");
        game.cBoard.getVacantCells();
        game.cBoard.getOwnedCells(game.p1, true);
        game.cBoard.getOwnedCells(game.p2, true);
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
