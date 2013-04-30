package ContestRounds;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import Helpers.*;
import Core.*;

enum PieceStatus {
	CLEAR,
	TEAM_A,
	TEAM_B,
	TEAM_C
};

public class ToOng implements Runnable, KeyListener {
	private Main mainframe;
	
	private final int boardsize = 10;
	private ArrayList<ArrayList<Piece>> board;
	
	//this is not the current piece. It's just the array's coordinate of the piece. Ex: a[x][y]
	private Point currentpiece;
	private Point position_being_selected = null;
	
	private Image pieces_sprite;
	
	//currentplayer determines what kind of piece can be selected during a team's turn
	private PieceStatus currentplayer;
	
	//Game statistics
	private Statistics statistics;
	
	private int turns = 1;
	private int moves = 0;
	
	private boolean showNumbers = false;
	private boolean hasNoMoreMoves = false;
	private boolean isExtraTurn = false;
	
	public ToOng(Main mainframe) {
		this.mainframe = mainframe;
		Toolkit tk = Toolkit.getDefaultToolkit();
		pieces_sprite = tk.getImage("./!RES/pieces_sprite.png");
		currentplayer = PieceStatus.TEAM_A;
		
		currentpiece = new Point(0, 0);
		board = new ArrayList<ArrayList<Piece>>();
		
		//init the first row piece
		ArrayList<Piece> temp_piecelist_first = new ArrayList<Piece>();
		int[] temp_xpoints_first = {0, 30, 30, 0, -30, -30};
		int[] temp_ypoints_first = {-30, -15, 15, 30, 15, -15};
		Point temp_point = new Point(0, 0);
		Piece temp_piece_first = new Piece(this, temp_xpoints_first, temp_ypoints_first, temp_point);
		temp_piecelist_first.add(temp_piece_first);
		board.add(temp_piecelist_first);
		
		//init the rest pieces
		for(int i = 1; i < boardsize; i++) {
			ArrayList<Piece> temp_piecelist = new ArrayList<Piece>();
			for(int j = 0; j < board.get(i - 1).size(); j++) {
				if(j == 0)
					initPiece(i, j, temp_piecelist, 4);
				initPiece(i, j, temp_piecelist, 2);
			}
			board.add(temp_piecelist);
		}
		
		//start keep track of the game
		statistics = new Statistics();
		new Thread(this).start();
	}
	
	public void run() {
		while(true) {
			//update scores, pieces
			statistics.clear();
			for(int i = 0; i < board.size(); i++)
				for(int j = 0; j < board.get(i).size(); j++) {
					switch(board.get(i).get(j).getStatus()) {
					case TEAM_A:
						statistics.team_a_pieces++;
						break;
					case TEAM_B:
						statistics.team_b_pieces++;
						break;
					case TEAM_C:
						statistics.team_c_pieces++;
						break;
					}
				}
			
			//check for no more moves, got no time for this now, gonna come back later
			/*
			synchronized(currentplayer) {
				stop1:
				for(int i = 0; i < board.size(); i++)
					for(int j = 0; j < board.get(i).size(); j++) {
						if(board.get(i).get(j).getStatus().equals(currentplayer)) {
							ArrayList<Point> temp_neighbors = getNeighbors(new Point(i, j));
							for(int k = 0; k < temp_neighbors.size(); k++)
								if(board.get(temp_neighbors.get(k).x).get(temp_neighbors.get(k).y).getStatus() == PieceStatus.CLEAR) {
									System.out.println("Broke at " + temp_neighbors.get(k).x + ", " + temp_neighbors.get(k).y);
									break stop1;
								}
						}
						
						if(i == board.size() && j == i)
							hasNoMoreMoves = true;
					}
			}
			*/
			try {
				Thread.sleep(100);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void drawBoard(Graphics2D g2d) {
		if(isExtraTurn) {
			g2d.setFont(new Font("Arial", Font.BOLD, 36));
			
			g2d.setColor(Color.BLACK);
			g2d.drawString("Extra moves chance!", 203, 93);
			
			g2d.setColor(Color.YELLOW);
			g2d.drawString("Extra moves chance!", 200, 90);
		}
		
		int counter = 1;
		g2d.translate(1024/2 - 50, 768/8 + 80);
		for(int i = 0; i < board.size(); i++)
			for(int j = 0; j < board.get(i).size(); j++) {
				board.get(i).get(j).drawPiece(g2d);
				
				if(showNumbers) {
					int temp_width = board.get(i).get(j).xpoints[2] - board.get(i).get(j).xpoints[5];
					int temp_height = board.get(i).get(j).ypoints[2] - board.get(i).get(j).ypoints[1];
					Rectangle temp_rect = new Rectangle(board.get(i).get(j).xpoints[5] + temp_width/8 - 7, board.get(i).get(j).ypoints[5] - temp_height/2 + 7, temp_width, temp_height);
					g2d.setFont(new Font("Arial", Font.BOLD, 30));
					
					g2d.setColor(Color.BLACK);
					temp_rect.x += 3;
					temp_rect.y += 3;
					GraphicsUtil.drawString(g2d, "" + counter, temp_rect, GraphicsUtil.Align.North);
					
					g2d.setColor(Color.WHITE);
					temp_rect.x -= 3;
					temp_rect.y -= 3;
					GraphicsUtil.drawString(g2d, "" + counter, temp_rect, GraphicsUtil.Align.North);
					
					counter++;
				}
			}
		
		g2d.setTransform(new AffineTransform());
		
		//drawLogState(g2d);
		
		
		//g2d.drawString("HasNoMoreMoves: " + hasNoMoreMoves, 100, 620);
		g2d.translate(mainframe.getWidth()/2 + 100, 0);
		
		Rectangle temp_rect = new Rectangle(70, 130, 300, 250);
		g2d.setStroke(new BasicStroke(5));
		g2d.setColor(Color.YELLOW);
		g2d.draw(temp_rect);
		g2d.setColor(new Color(1, 0, 1, 0.3f));
		g2d.fill(temp_rect);
		
		g2d.drawImage(pieces_sprite, 150, 200, 200, 250, 0, 0, 50, 50, null);
		g2d.drawImage(pieces_sprite, 150, 260, 200, 310, 50, 0, 100, 50, null);
		g2d.drawImage(pieces_sprite, 150, 320, 200, 370, 100, 0, 150, 50, null);
		
		g2d.setFont(new Font("Arial", Font.BOLD, 60));
		g2d.setColor(Color.BLACK);
		g2d.drawString("" + statistics.team_a_pieces, 280 + 3, 243 + 3);
		g2d.drawString("" + statistics.team_b_pieces, 280 + 3, 303 + 3);
		g2d.drawString("" + statistics.team_c_pieces, 280 + 3, 363 + 3);
		
		g2d.drawString("Turns", 100 + 3, 183 + 3);
		g2d.drawString("" + turns, 280 + 3, 183 + 3);
		
		g2d.setColor(Color.WHITE);
		g2d.drawString("" + statistics.team_a_pieces, 280, 243);
		g2d.drawString("" + statistics.team_b_pieces, 280, 303);
		g2d.drawString("" + statistics.team_c_pieces, 280, 363);
		
		g2d.setColor(Color.YELLOW);
		g2d.drawString("Turns", 100, 183);
		g2d.drawString("" + turns, 280, 183);
		
		g2d.setTransform(new AffineTransform());
		
		if(hasNoMoreMoves)
			g2d.drawString("NO MORE MOVES!", 100, 640);
		//printed = true;
	}
	
	private ArrayList<Point> getNeighbors(Point piece) {
		//get neighbors manually. Gonna optimize this later on
		ArrayList<Point> neighbors = new ArrayList<Point>();
		Point[] p = new Point[6];
		
		p[0] = new Point(piece.x, piece.y - 1);
		p[1] = new Point(piece.x, piece.y + 1);
		p[2] = new Point(piece.x - 1, piece.y - 1);
		p[3] = new Point(piece.x - 1, piece.y);
		p[4] = new Point(piece.x + 1, piece.y);
		p[5] = new Point(piece.x + 1, piece.y + 1);
		
		for(int i = 0; i < 6; i++)
			if(p[i].x >= 0 && p[i].y >= 0 && p[i].x < 10 && p[i].y < board.get(p[i].x).size())
				neighbors.add(p[i]);
		return neighbors;
	}
	
	private void initPlayerPieces(Point init, PieceStatus team) {
		//System.out.println("Initializing " + team + " at " + init.x + "," + init.y);
		int noOfPiecesInRow = 1;
		for(int i = init.x; i < init.x + Helpers.pascalTriangleRow(boardsize); i++) {
			for(int j = init.y; j < init.y + noOfPiecesInRow; j++)
				board.get(i).get(j).setStatus(team);
			noOfPiecesInRow++;
		}
	}
	
	private void clearBoard() {
		for(int i = 0; i < board.size(); i++)
			for(int j = 0; j < board.get(i).size(); j++)
				if(board.get(i).get(j).getStatus() != PieceStatus.CLEAR)
					board.get(i).get(j).setStatus(PieceStatus.CLEAR);
	}
	
	private void initPiece(int row, int col, ArrayList<Piece> temp_piecelist, int firstvertexposition) {
		int[] temp_xpoints = new int[6];
		int[] temp_ypoints = new int[6];
		
		temp_xpoints[0] = board.get(row - 1).get(col).getXpoints()[firstvertexposition];
		temp_xpoints[1] = temp_xpoints[0] + 30;
		temp_xpoints[2] = temp_xpoints[1];
		temp_xpoints[3] = temp_xpoints[0];
		temp_xpoints[4] = temp_xpoints[0] - 30;
		temp_xpoints[5] = temp_xpoints[4];
			
		temp_ypoints[0] = board.get(row - 1).get(col).getYpoints()[firstvertexposition];
		temp_ypoints[1] = temp_ypoints[0] + 15;
		temp_ypoints[2] = temp_ypoints[1] + 30;
		temp_ypoints[3] = temp_ypoints[2] + 15;
		temp_ypoints[4] = temp_ypoints[2];
		temp_ypoints[5] = temp_ypoints[1];
		
		Point temp_point = new Point(row, col);
		if(firstvertexposition == 2)
			temp_point.y++;
		Piece temp_piece = new Piece(this, temp_xpoints, temp_ypoints, temp_point);
		temp_piecelist.add(temp_piece);
	}
	
	private void drawLogState(Graphics2D g2d) {
		g2d.setColor(Color.WHITE);
		g2d.setFont(new Font("Arial", Font.PLAIN, 12));
		g2d.drawString("Current position: (" + getCurrentpiece().x + ", " + getCurrentpiece().y + ")", 100, 500);
		g2d.drawString("Current turn: " + currentplayer, 100, 530);
		g2d.drawString("Number of moves: " + moves, 100, 560);
	}
	
	public Point getCurrentpiece() {
		return currentpiece;
	}

	public void setCurrentpiece(Point currentpiece) {
		this.currentpiece = currentpiece;
	}

	public Image getPieces_sprite() {
		return pieces_sprite;
	}

	public void setPieces_sprite(Image pieces_sprite) {
		this.pieces_sprite = pieces_sprite;
	}

	public PieceStatus getCurrentplayer() {
		return currentplayer;
	}

	public void setCurrentplayer(PieceStatus currentplayer) {
		this.currentplayer = currentplayer;
	}

	public Point getPosition_being_selected() {
		return position_being_selected;
	}

	public void setPosition_being_selected(Point position_being_selected) {
		this.position_being_selected = position_being_selected;
	}

	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()) {
		/*case KeyEvent.VK_Z:
			if(e.isControlDown())
				board = (ArrayList<ArrayList<Piece>>) backup.backup_board.clone();
			break;*/
		case KeyEvent.VK_UP:
			currentpiece.x -= 1;
			if(currentpiece.x < 0)
				currentpiece.x = 0;
			if(currentpiece.x < currentpiece.y)
				currentpiece.y = currentpiece.x;
			break;
		case KeyEvent.VK_DOWN:
			currentpiece.x += 1;
			if(currentpiece.x >= boardsize)
				currentpiece.x = boardsize - 1;
			break;
		case KeyEvent.VK_RIGHT:
			currentpiece.y += 1;
			if(currentpiece.y > currentpiece.x)
				currentpiece.y = currentpiece.x;
			break;
		case KeyEvent.VK_LEFT:
			currentpiece.y -= 1;
			if(currentpiece.y < 0)
				currentpiece.y = 0;
			break;
		case KeyEvent.VK_ENTER:
			if(position_being_selected != null) {
				//check for deselection
				if(position_being_selected.equals(currentpiece)) {
					position_being_selected = null;
					break;
				}
				
				//move piece hereeeeeeeee
				if(board.get(currentpiece.x).get(currentpiece.y).getStatus() == PieceStatus.CLEAR) {
					ArrayList<Point> neighbors = getNeighbors(position_being_selected);
					
					for(int i = 0; i < neighbors.size(); i++) {
						if(currentpiece.equals(neighbors.get(i))) {
							board.get(currentpiece.x).get(currentpiece.y).setStatus(board.get(position_being_selected.x).get(position_being_selected.y).getStatus());
							board.get(position_being_selected.x).get(position_being_selected.y).setStatus(PieceStatus.CLEAR);
							position_being_selected = null;
							
							//update board if a team has captured others' piece(s)
							ArrayList<Point> neighbors_currentpiece = getNeighbors(currentpiece);
							for(int j = 0; j < neighbors_currentpiece.size(); j++)
								if(board.get(neighbors_currentpiece.get(j).x).get(neighbors_currentpiece.get(j).y).getStatus() != PieceStatus.CLEAR)
									board.get(neighbors_currentpiece.get(j).x).get(neighbors_currentpiece.get(j).y).setStatus(currentplayer);

							Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(new KeyEvent(mainframe, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, KeyEvent.CHAR_UNDEFINED));
							
							//update game's number of moves and turns
							updateMovesAndTurns();
							break;
						}
					}
					position_being_selected = null;
					break;
				}
			}
			
			//illegal move. Select another piece!!!
			if(currentplayer == board.get(currentpiece.x).get(currentpiece.y).getStatus())
				position_being_selected = new Point(currentpiece.x, currentpiece.y);
			else
				position_being_selected = null;
			
			break;
		case KeyEvent.VK_N:
			clearBoard();
			initPlayerPieces(new Point(0, 0), PieceStatus.TEAM_A);
			initPlayerPieces(new Point(6, 0), PieceStatus.TEAM_B);
			initPlayerPieces(new Point(6, 6), PieceStatus.TEAM_C);
			moves = 0;
			turns = 1;
			break;
		case KeyEvent.VK_1:
			board.get(currentpiece.x).get(currentpiece.y).setStatus(PieceStatus.TEAM_A);
			break;
		case KeyEvent.VK_2:
			board.get(currentpiece.x).get(currentpiece.y).setStatus(PieceStatus.TEAM_B);
			break;
		case KeyEvent.VK_3:
			board.get(currentpiece.x).get(currentpiece.y).setStatus(PieceStatus.TEAM_C);
			break;
		case KeyEvent.VK_4:
			board.get(currentpiece.x).get(currentpiece.y).setStatus(PieceStatus.CLEAR);
			break;
		case KeyEvent.VK_C:
			clearBoard();
			break;
		case KeyEvent.VK_O:
			switch(currentplayer) {
			case TEAM_A:
				currentplayer = PieceStatus.TEAM_C;
				break;
			case TEAM_B:
				currentplayer = PieceStatus.TEAM_A;
				break;
			case TEAM_C:
				currentplayer = PieceStatus.TEAM_B;
				break;
			}
			
			position_being_selected = null;
			hasNoMoreMoves = false;
			break;
		case KeyEvent.VK_P:
			switch(currentplayer) {
			case TEAM_A:
				currentplayer = PieceStatus.TEAM_B;
				break;
			case TEAM_B:
				currentplayer = PieceStatus.TEAM_C;
				break;
			case TEAM_C:
				currentplayer = PieceStatus.TEAM_A;
				break;
			}
			
			position_being_selected = null;
			hasNoMoreMoves = false;
			break;
		case KeyEvent.VK_I:
			showNumbers = !showNumbers;
			break;
		case KeyEvent.VK_M:
			if(isExtraTurn) {
				isExtraTurn = false;
				incrementCurrentQuestion();
			} //new update
			updateMovesAndTurns();
			break;
		}
	}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
	
	private void incrementCurrentQuestion() {
		MainRound.current_question++;
		if(MainRound.current_question > MainRound.current_list.size())
			MainRound.current_question = MainRound.current_list.size();
		QuestionSet.setQuestionvisible(false);
		QuestionSet.setSolutionvisible(false);
	}
	
	private void updateMovesAndTurns() {
		if(!isExtraTurn) {
			moves++;
			if(moves % 4 == 0) {
				//backupState();
				turns++;
				currentplayer = PieceStatus.TEAM_A;
			}
			if(moves % 4 == 3)
				isExtraTurn = true;
		}
	}
	
	/*private void backupState() {
		backup.backup_board = (ArrayList<ArrayList<Piece>>) board.clone(); //save state for undo
		backup.turns = turns;
		backup.moves = moves;
	}*/
}

class Piece {
	ToOng toong;
	Polygon shape;
	int[] xpoints, ypoints;
	Point coordinate;
	PieceStatus status;
	
	//rect for texture paint
	Rectangle rect;
	
	public Piece(ToOng toong, int[] xpoints, int[] ypoints, Point coordinate) {
		rect = new Rectangle(0, 0, 50, 50);
		this.toong = toong;
		this.xpoints = xpoints;
		this.ypoints = ypoints;
		this.coordinate = coordinate;
		shape = new Polygon(xpoints, ypoints, xpoints.length);
		status = PieceStatus.CLEAR;
	}
	
	public void drawPiece(Graphics2D g2d) {
		//draw the border
		g2d.setColor(Color.WHITE);
		g2d.setStroke(new BasicStroke(3f));
		g2d.draw(shape);
		
		//draw current selection
		if(toong.getCurrentpiece().x == coordinate.x && toong.getCurrentpiece().y == coordinate.y) {
			switch(toong.getCurrentplayer()) {
			case TEAM_A:
				g2d.setPaint(new Color(1.0f, 1.0f, 0, 0.3f));
				break;
			case TEAM_B:
				g2d.setPaint(new Color(0, 1.0f, 0, 0.3f));
				break;
			case TEAM_C:
				g2d.setPaint(new Color(0, 0, 1.0f, 0.3f));
				break;
			}
			g2d.fill(shape);
		}
		
		//draw current selected piece
		if(toong.getPosition_being_selected() != null && toong.getPosition_being_selected().equals(coordinate)) {
			g2d.setPaint(new Color(1.0f, 1.0f, 1.0f, 0.7f));
			g2d.fill(shape);
		}
		
		//draw piece
		switch(status) {
		case TEAM_A:
			g2d.drawImage(toong.getPieces_sprite(), xpoints[0] - 24, ypoints[0] + 5, xpoints[0] + 26, ypoints[0] + 55, 0, 0, 50, 50, null);
			break;
		case TEAM_B:
			g2d.drawImage(toong.getPieces_sprite(), xpoints[0] - 24, ypoints[0] + 5, xpoints[0] + 26, ypoints[0] + 55, 50, 0, 100, 50, null);
			break;
		case TEAM_C:
			g2d.drawImage(toong.getPieces_sprite(), xpoints[0] - 24, ypoints[0] + 5, xpoints[0] + 26, ypoints[0] + 55, 100, 0, 150, 50, null);
			break;
		}
	}
	
	public void printCoordinate() {
		for(int i = 0; i < xpoints.length; i++)
			System.out.println("Vertex " + (i + 1) + ": (" + xpoints[i] + ", " + ypoints[i] + ")");
		
		System.out.println("");
	}
	
	public Polygon getShape() {
		return shape;
	}
	public void setShape(Polygon shape) {
		this.shape = shape;
	}
	
	public int[] getXpoints() {
		return xpoints;
	}

	public void setXpoints(int[] xpoints) {
		this.xpoints = xpoints;
	}

	public int[] getYpoints() {
		return ypoints;
	}

	public void setYpoints(int[] ypoints) {
		this.ypoints = ypoints;
	}

	public PieceStatus getStatus() {
		return status;
	}
	public void setStatus(PieceStatus status) {
		this.status = status;
	}
}

class Statistics {
	int team_a_pieces = 0;
	int team_b_pieces = 0;
	int team_c_pieces = 0;
	
	public void clear() {
		team_a_pieces = 0;
		team_b_pieces = 0;
		team_c_pieces = 0;
	}
	
	public int getTeam_a_pieces() {
		return team_a_pieces;
	}
	public void setTeam_a_pieces(int team_a_pieces) {
		this.team_a_pieces = team_a_pieces;
	}
	public int getTeam_b_pieces() {
		return team_b_pieces;
	}
	public void setTeam_b_pieces(int team_b_pieces) {
		this.team_b_pieces = team_b_pieces;
	}
	public int getTeam_c_pieces() {
		return team_c_pieces;
	}
	public void setTeam_c_pieces(int team_c_pieces) {
		this.team_c_pieces = team_c_pieces;
	}
}