package cluedo.board;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import cluedo.control.Player;
import cluedo.control.gameOfCluedo;

public class Board {
	//private BoardObject[][] board;
	private char[][] board;
	private gameOfCluedo game;
	private final int boardLength = 25;
	
	/**
	 * Construct the initial board
	 */
	public Board(String filename, gameOfCluedo game) throws IOException {
		//this.board = new BoardObject[25][25]; // first row/col are numbers
		this.game = game;
		this.board = new char[boardLength][boardLength];
		createBoard(filename);
	}
	
	/**
	 * Reads text file containing the view of the board
	 * @param filename
	 * @throws IOException
	 */
	private void createBoard(String filename) throws IOException{
		FileReader fr = null;		
		BufferedReader br = null;
		try {
			fr = new FileReader(filename);		
			br = new BufferedReader(fr);
		}catch(IOException e){
			System.out.println("\n");
			System.out.println("Move cluedoBoard.txt to the dir(out of the src folder)");
		}
		
		ArrayList<String> lines = new ArrayList<String>();
		//int width = -1;
		String line;		
		while((line = br.readLine()) != null) {
			lines.add(line);
		}
		
		for(int i = 0; i < boardLength; i++){
			line  = lines.get(i);
			for(int j = 0; j < boardLength; j++){
				board[i][j] = line.charAt(j);
			}
		}
		
		
		/*	// now sanity check
			
			if(width == -1) {				
				width = line.length();
			} else if(width != line.length()) {				
				throw new IllegalArgumentException("Input file \"" + filename + "\" is malformed; line " + lines.size() + " incorrect width.");
			}			
		}*/
		
	}
	public void drawTop(){
		
	}

	/**
	 * Draw the board
	 */
	public void drawBoard(){
		//draw top coords
		/*for(int i = 0; i <= boardLength; i++){
			System.out.print(i+"");
		}*/
        //String topCoords =  "                      |11 |13 |15 |17 |19 |21 |23 |25";
		//String topCoords2 = " 0|1|2|3|4|5|6|7|8|9|10 |12 |14 |16 |18 |20 |22 |24";
		String topCoords = "   A|B|C|D|E|F|G|H|I|J|K|L|M|N|O|P|Q|R|S|T|U|V|W|X|Y|";
		System.out.println(topCoords);
		//System.out.println(topCoords2);
		
		
		
		for(int x = 0; x < boardLength; x++){
			//draw left coords
			if(x < 9){
				System.out.print(" "+(x+1)+"|");
			} else {
				System.out.print(x+1+"|");
			}
			
			for(int y = 0; y < boardLength; y++){
				//draw board
				
				boolean isPlayerThere = false;
				ArrayList<Player> players = game.getPlayerArray();
				
				for(int player = 0; player < players.size(); player++){
					isPlayerThere = players.get(player).checkPlayer(x,y);
					if(isPlayerThere){
						System.out.print(players.get(player).getCharacterID());
						break;
					}
						
				}
				if(!isPlayerThere){
					System.out.print(board[x][y]);
				}
				System.out.print("|");
			}
			
			System.out.println();
			
		}
		System.out.println(topCoords);
	}
	
	public char getCharAt(int x, int y){
		return board[x][y];
	}
}

