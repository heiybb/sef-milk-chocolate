package com.sefChocolate.monster;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


public class Game extends Application{


    public static final int TILE_SIZE = 50;
    public static final int WIDTH = 9;
    public static final int HEIGHT = 9;

    private Tile[][] board = new Tile[WIDTH][HEIGHT];

    private Group tileGroup = new Group();
    private Group pieceGroup = new Group();
    
    private static Piece piece = new Piece(PieceType.RED, 0, 0);

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);
        root.getChildren().addAll(tileGroup, pieceGroup);

        Tile tile = null;
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
            		
            		if(y==0||y==4||y==8) {
            			tile = new Tile(true, x, y);
            		}
            		else if(x==0||x==4||x==8) {
            			tile = new Tile(true, x, y);
            		}
            		else {
            			tile = new Tile(false, x, y);
            		}

                board[x][y] = tile;

                tileGroup.getChildren().add(tile);

            }
        }

        tile.setPiece(piece);
        pieceGroup.getChildren().add(piece);
        
        
        return root;
    }


    private int toBoard(double pixel) {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        
               
        scene.setOnKeyPressed(event -> {
	        		        
	        if (event.getCode() == KeyCode.W || event.getCode() == KeyCode.UP) {
	        		move(0, -1);
	            System.out.println("up");
	        } else if (event.getCode() == KeyCode.S || event.getCode() == KeyCode.DOWN) {
	        		move(0, 1);
	            System.out.println("down");
	        } else if (event.getCode() == KeyCode.A || event.getCode() == KeyCode.LEFT) {
	        		move(-1, 0);
	        		
	            System.out.println("left");
	        } else if (event.getCode() == KeyCode.D || event.getCode() == KeyCode.RIGHT) {
	        		
	        		move(1, 0);
	            System.out.println("right");
	        }            
	    });
        
      
        primaryStage.setTitle("CheckersApp");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    public void move(int x, int y) {
    		int x0 = toBoard(piece.getOldX());
        int y0 = toBoard(piece.getOldY());

        int newX = x0 + x;
        int newY = y0 + y;
        
        MoveResult result;
        
        // 越出围墙，则不可以移动，不然，就可以移动
      if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) {
          result = new MoveResult(MoveType.NONE) ;
      } 
      else if(newX==0||newX==4||newX==8||newY==0||newY==4||newY==8) {
    	  	result = new MoveResult(MoveType.NORMAL);
      }
      else {
    	  	result = new MoveResult(MoveType.NONE) ;
      }
        
      
        switch (result.getType()) {
	      case NONE:
	          piece.abortMove();
	          break;
	      case NORMAL:
	          piece.move(newX, newY);
	          board[x0][y0].setPiece(null);
	          board[newX][newY].setPiece(piece);
	          break;
	      case KILL:
	          piece.move(newX, newY);
	          board[x0][y0].setPiece(null);
	          board[newX][newY].setPiece(piece);
	
	          Piece otherPiece = result.getPiece();
	          board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null);
	          pieceGroup.getChildren().remove(otherPiece);
	          break;
        }
        
        
        
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
