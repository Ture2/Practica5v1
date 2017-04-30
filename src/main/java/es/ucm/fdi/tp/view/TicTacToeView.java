package es.ucm.fdi.tp.view;


import java.awt.Color;

import es.ucm.fdi.tp.base.model.GamePlayer.PlayerMode;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;
import es.ucm.fdi.tp.view.PlayersInfoViewer.PlayersInfoObserver;
import es.ucm.fdi.tp.was.WolfAndSheepAction;

public class TicTacToeView extends GUIView<TttState, TttAction> implements PlayersInfoObserver {
	private JBoard boardComp;
	private Integer[][] board;
	
	private int numOfRows, numOfCols;
	private Color colorP1, colorP2;
	
	GameController<TttState, TttAction> gameCntrl;
	TttState state = new TttState(3);

	public TicTacToeView() {
		this.colorP1 = Color.RED;
		this.colorP2 = Color.BLUE;
		
		initGUI();
	}

	private void initGUI() {
		createBoardData(3, 3);

		boardComp = new JBoard() {
			
			@Override
			protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
				if((gameCntrl.getPlayerMode().equals(PlayerMode.Manual)) && this.isEnabled()){
					if(clickCount == 1){
						System.out.println("Mouse: " + clickCount + "clicks at position (" + row + "," + col + ") with Button "
						+ mouseButton + "where do you want to go?");
				}
					if(clickCount == 2){
						if(gameCntrl.getPlayerId() == state.getTurn()){
							TttAction action = new TttAction(state.getTurn(), row, col);
						if(state.isValid(action))
							gameCntrl.makeManualMove(action); //Realizar movimiento
						}
					}
				}
			}

			@Override
			protected void keyTyped(int keyCode) {
				System.out.println("Key " + keyCode + " pressed ..");
			}

			@Override
			protected Shape getShape(int player) {
				return Shape.CIRCLE;
			}

			@Override
			protected Integer getPosition(int row, int col) {
				return TicTacToeView.this.getPosition(row, col);
			}

			@Override
			protected int getNumRows() {
				return numOfRows;
			}

			@Override
			protected int getNumCols() {
				return numOfCols;
			}

			@Override
			protected Color getColor(int player) {
				return TicTacToeView.this.getColor(player);
			}

			@Override
			protected Color getBackground(int row, int col) {
				//return Color.LIGHT_GRAY;

				// use this for 2 chess like board
				 return (row+col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
			}

			@Override
			protected int getSepPixels() {
				return 1; // put to 0 if you don't want a separator between
							// cells
			}
		};
	}


	private void createBoardData(int numOfRows, int numOfCols) {
		this.numOfRows = numOfRows;
		this.numOfCols = numOfCols;
		
		board = new Integer[numOfRows][numOfCols];
		
		int[][] boardTtt = state.getBoard();
		
		for (int i = 0; i < numOfRows; i++)
			for (int j = 0; j < numOfCols; j++) {
				if(boardTtt[i][j] == -1) board[i][j] = null;
				else board[i][j] = boardTtt[i][j];
			}
			
			
	}

	protected Integer getPosition(int row, int col) {
		return board[row][col];
	}

	protected Color getColor(int player) {
		return player == 0 ?  colorP1 : colorP2;
	}

	@Override
	public void ColorChanged(int p, Color color) {
		if(p == 0) colorP1 = color;
		else colorP2 = color;
	}

	@Override
	public void enable() {
		boardComp.setEnabled(true);
	}

	@Override
	public void disable() {
		boardComp.setEnabled(false);
	}

	@Override
	public void update(TttState state) {
		this.state = state;		
	}

	@Override
	public void setMessageViewer(MessageViewer<TttState, TttAction> infoViewer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPlayersInfoViewer(
			PlayersInfoViewer<TttState, TttAction> playersInfoViewer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setGameController(GameController<TttState, TttAction> gameCntrl) {
		this.gameCntrl = gameCntrl;		
	}

	
}
