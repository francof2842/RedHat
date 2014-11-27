package Juego;
/*
 * Programa creado para resolver 
 * el juego 2048 para el Desafio presentado por RedHat
 */


import java.io.IOException;
import Juego.Json.Json;
import Juego.Board.Board;
import Juego.Movimientos.Direction;
import Juego.Ai.Ai;
import org.json.JSONException;


public class Game {


    public static void main(String[] args) {
        int x=1;
        System.out.println("Desafio Red Hat 2048:");
        pressAnyKeyToContinue();
        System.out.println("======================");
        System.out.println("=======Running========");
        System.out.println("======================");
        System.out.println();
        while (x==1){
            try {
                redHat();
                x=0;
            } catch (Exception e) {
                System.out.println("Algo no funciono bien");
                System.out.println("Intentando de Nuevo");
            }
        System.out.println("======================");
        
    }
    }
    
    
    private static void pressAnyKeyToContinue()
    { 
        System.out.println("Press any key to Start!");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}  
    }
    
    
    
    
    public static void redHat() throws CloneNotSupportedException, IOException, JSONException{
        
        int hintDepth = 5;
        
        System.out.println("Running Red Hat Game: ");
        
        Json json = new Json();
        
        
        Board game = new Board(json.getGrid());
        game.setSession(json.getSessionId());
        System.out.println("Session: " + game.getSession());
        
        
        setBoardStatus(game, json);

        
        Direction hint = Ai.findBestMove(game, hintDepth);
        
        move(json, game, hint);
        setBoardStatus(game, json);
        printFullBoard(game, hint);
        
        while (game.getWon() == false && game.getOver() == false){
            hint = Ai.findBestMove(game, hintDepth);
            move(json, game, hint);
            
        }
        
        System.out.println("Finish Red Hat! ");
        System.out.println("Won: " + game.getWon() );
        System.out.println("Over: " + game.getOver());
        System.out.println("Score: " + game.getScore());
        System.out.println("Total Number of Movements: " + game.getMoves());
        System.out.println("Session Id: " + game.getSession());
    }
    
    
    public static void setBoardStatus (Board game,Json json) throws IOException{
        game.setScore(json.getScore());
        game.setWon(json.getWon());
        game.setOver(json.getOver());
        game.setMoves(json.getMoves());
    }
    
    
     public static void move(Json json, Board game, Direction hint) throws IOException, JSONException {
       
        json.movementJson(hint.getCode());
        game.setBoard(json.getGrid());
        setBoardStatus(game, json);
        printFullBoard(game, hint);
    }
     
    public static void printFullBoard(Board game, Direction hint){
        printBoard(game.getBoardArray(), game.getScore(), hint);
        System.out.println("Movimiento N: " + game.getMoves());
        System.out.println("Session Id " + game.getSession());
    }
    
    public static void printBoard(int[][] boardArray, int score, Direction hint) {
        System.out.println("-------------------------");
        System.out.println("Score:\t" + String.valueOf(score));
        System.out.println();
        System.out.println("Hint:\t" + hint);
        System.out.println();
        
        for(int i=0;i<boardArray.length;++i) {
            for(int j=0;j<boardArray[i].length;++j) {
                System.out.print(boardArray[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.println("-------------------------");
    }
    
    
}
