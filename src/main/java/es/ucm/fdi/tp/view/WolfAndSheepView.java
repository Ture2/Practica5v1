package es.ucm.fdi.tp.view;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JComponent;
import javax.swing.JPanel;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.model.GamePlayer.PlayerMode;
import es.ucm.fdi.tp.extra.jboard.JBoard;
import es.ucm.fdi.tp.extra.jboard.JBoard.Shape;
import es.ucm.fdi.tp.view.PlayersInfoViewer.PlayersInfoObserver;
import es.ucm.fdi.tp.was.WolfAndSheepAction;
import es.ucm.fdi.tp.was.WolfAndSheepState;

public class WolfAndSheepView extends GUIView<WolfAndSheepState, WolfAndSheepAction>
			implements PlayersInfoObserver{
	
	private Integer[][] board;
	private int numOfRows;
	private int numOfCols;
	private GameController<?,?> gameCtrl;
	private MessageViewer<?,?> infoViewer; 
	private PlayersInfoViewer<?,?> playersInfoViewer;
	private WolfAndSheepState state;
	private JComponent jboard;
	boolean viewEnable;

	public WolfAndSheepView(GameController<WolfAndSheepState,WolfAndSheepAction> gameCtrl, WolfAndSheepState state, MessageViewer<WolfAndSheepState,WolfAndSheepAction> infoViewer,
			PlayersInfoViewer<WolfAndSheepState,WolfAndSheepAction> playersInfoViewer, int rows, int cols) {
		this.gameCtrl = gameCtrl;
		this.state = state;
		this.infoViewer = infoViewer;
		this.playersInfoViewer = playersInfoViewer;
		this.numOfRows = rows;
		this.numOfCols = cols;
		initGUI();
	}
	private void initGUI() {
		createBoardData(this.numOfCols,this.numOfRows);
		this.setLayout(new BorderLayout());
		jboard = new JBoard() {
			@Override
			protected void mouseClicked(int row, int col, int clickCount, int mouseButton) {
				if((gameCtrl.getPlayerMode().equals(PlayerMode.Manual)) && this.isEnabled()){
					if(clickCount == 1){
						System.out.println("Mouse: " + clickCount + "clicks at position (" + row + "," + col + ") with Button "
						+ mouseButton + "where do you want to go?");
				}
					if(clickCount == 2){
						//Crear evento
						//Realizar movimiento
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
				return board[row][col];
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
				return board.getColor(player);
			}
			@Override
			protected Color getBackground(int row, int col) {
				return Color.LIGHT_GRAY;

				// use this for 2 chess like board
				// return (row+col) % 2 == 0 ? Color.LIGHT_GRAY : Color.BLACK;
			}
			@Override
			protected int getSepPixels() {
				return 1; // put to 0 if you don't want a separator between
							// cells
			}
		};
		this.add(jboard, BorderLayout.CENTER);
		JPanel sizePabel = new JPanel();
		this.add(sizePabel, BorderLayout.PAGE_START);
		setOpaque(true);
	}
	
	private void createBoardData(int numOfRows, int numOfCols) {
		this.numOfRows = numOfRows;
		this.numOfCols = numOfCols;
		board = new Integer[numOfRows][numOfCols];
		for (int i = 0; i < numOfRows; i++)
			for (int j = 0; j < numOfCols; j++) {
				double d = Math.random();
				if (d < 0.6) {
					board[i][j] = null;
				} else if (d < 0.8) {
					board[i][j] = 0;
				} else {
					board[i][j] = 1;
				}
			}
	}
	protected Color getColor(int player) {
		return player == 0 ? Color.BLUE : Color.RED;
	}
	
	@Override
	public void enable() {
	}
	@Override
	public void disable() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(WolfAndSheepState state) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setMessageViewer(MessageViewer<WolfAndSheepState, WolfAndSheepAction> infoViewer) {
		this.infoViewer = infoViewer;
	}
	@Override
	public void setPlayersInfoViewer(PlayersInfoViewer<WolfAndSheepState, WolfAndSheepAction> playersInfoViewer) {
		this.playersInfoViewer = playersInfoViewer;
		this.playersInfoViewer.addObserver(this); // No se si esta bien
		
	}
	@Override
	public void setGameController(GameController<WolfAndSheepState, WolfAndSheepAction> gameCntrl) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void ColorChanged(int p, Color color) {
		// TODO Auto-generated method stub
		
	}


}
