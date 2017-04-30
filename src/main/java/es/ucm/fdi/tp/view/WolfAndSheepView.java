package es.ucm.fdi.tp.view;

import java.awt.Color;
import java.util.List;

import es.ucm.fdi.tp.base.model.GamePlayer.PlayerMode;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.view.PlayersInfoViewer.PlayersInfoObserver;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;

public class WolfAndSheepView extends GUIView<WolfAndSheepState, WolfAndSheepAction>
			implements PlayersInfoObserver{
	
	private JBoard boardComp;
	private Integer[][] board;
	
	private int numOfRows, numOfCols;
	private Color colorP1, colorP2;
	
	GameController<WolfAndSheepState, WolfAndSheepAction> gameCntrl;
	WolfAndSheepState state = new WolfAndSheepState(8);

	public WolfAndSheepView() {
		this.colorP1 = Color.RED;
		this.colorP2 = Color.BLUE;
		
		initGUI();
	}

	private void initGUI() {
		createBoardData(8, 8);

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
							WolfAndSheepAction action = new WolfAndSheepAction(state.getTurn(), row, col, state.lastRow(), state.lastCol());
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
				return WolfAndSheepView.this.getPosition(row, col);
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
				return WolfAndSheepView.this.getColor(player);
			}

			@Override
			protected Color getBackground(int row, int col) {
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
		
		int[][] boardWas = state.getBoard();
		
		for (int i = 0; i < numOfRows; i++)
			for (int j = 0; j < numOfCols; j++) {
				if(boardWas[i][j] == -1) board[i][j] = null;
				else board[i][j] = boardWas[i][j];
			}	
	}

	protected Integer getPosition(int row, int col) {
		return board[row][col];
	}

	protected Color getColor(int player) {
		return player == 0 ? colorP1 : colorP2;
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
	public void update(WolfAndSheepState state) {
		this.state = state;		
	}

	@Override
	public void setMessageViewer(
			MessageViewer<WolfAndSheepState, WolfAndSheepAction> infoViewer) {
	
	}

	@Override
	public void setPlayersInfoViewer(
			PlayersInfoViewer<WolfAndSheepState, WolfAndSheepAction> playersInfoViewer) {}

	@Override
	public void setGameController(
			GameController<WolfAndSheepState, WolfAndSheepAction> gameCntrl) {
		this.gameCntrl = gameCntrl;	
	}
	
}
