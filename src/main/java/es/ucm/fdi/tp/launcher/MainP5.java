package es.ucm.fdi.tp.launcher;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.base.model.GamePlayer;
import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.base.player.RandomPlayer;
import es.ucm.fdi.tp.base.player.SmartPlayer;
import es.ucm.fdi.tp.mvc.GameTable;
import es.ucm.fdi.tp.view.ConsoleController;
import es.ucm.fdi.tp.view.ConsoleView;
import es.ucm.fdi.tp.view.GUIController;
import es.ucm.fdi.tp.view.GUIView;
import es.ucm.fdi.tp.view.GameController;

import javax.swing.SwingUtilities;

public class MainP5 {
	private static GameTable<?,?> createGame(String gType){
		
		
	}
	private static <S extends GameState<S,A>, A extends GameAction<S, A>> void startConsoleMode(
			String gType, GameTable<S,A> game, String playerModes[]){
		//Crear una lista de jugadores y asignar
		new ConsoleView<S,A>(game);
		//new ConsoleController <S,A>(players,game).run;
	}
	
	
	private static <S extends GameState<S,A>, A extends GameAction<S, A>> void startGUIMode(
			String gType, GameTable<S,A> game, String playerModes[]){
		for (int i = 0; i < game.getState().getPlayerCount(); i++) {
			 GamePlayer p1;
			 GamePlayer p2;
			 p1 = new RandomPlayer("");
			 p2 = new SmartPlayer("", 2);
			 p1.join(0);
			 p2.join(1);
			 try {
			 SwingUtilities.invokeAndWait(new Runnable() { public void run() {
			 GameController<S, A> gameCtrl = new GUIController(i, p1, p2, game);
			 GUIView<S,A> guiView = (GUIView<S, A>) createGameView(gType);
			 guiView.setGameController(gameCtrl);
			 GUIView<S,A> container = new GameContainer(guiView, gameCtrl, /*falta*/);
			 container.enableWindowMode();
			 }
			 });
			 } catch (InvocationTargetException | InterruptedException e) {
			 System.err.println("Some error occurred while creating the view ...");
			 System.exit(1);
			}
		}
		SwingUtilities.invokeLater(new Runnable() { public void run() { game.start(); } });
	}
	
	private Game createGameView(String gType){ //[1]
		// Crear un game view dependiendo de que sea WAS o TTT
		if(gType.equalsIgnoreCase("WAS")) return new GameTable(new WolfAndSheepState(8));
	}
	
	private static void usage(){}
	
	public static void main(String[] args){
		if(args.length <2){
			usage();
			System.exit(1);
		}
		GameTable<?,?> game = createGame(args[0]);
		if(game == null){
		System.err.println("Invalid game");
		usage();
		System.exit(1);
		}
		String[] otherArgs = Arrays.copyOfRange(args,2,args.length);
		switch(args[1]){
		case "console":
			startConsoleMode(game, otherArgs);
		case "gui":
			startGUIMode(args[0], game, otherArgs);
			break;
		default: 
			System.err.println("Invalid view mode: "+ args[1]);
			usage();
			System.exit(1);
		}
	
		
		
	
}
	private static void startConsoleMode(GameTable<?, ?> game,
			String[] otherArgs) {
		// TODO Auto-generated method stub
		
	}
}
