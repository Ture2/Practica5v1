package es.ucm.fdi.tp.was;




import org.junit.Test;

import es.ucm.fdi.tp.base.model.GameError;
import es.ucm.fdi.tp.launcher.MainLauncher;

public class MainLauncherTest {

	@Test
	public void testJuegoErroneo() {
		try{
			MainLauncher.createInitialState("UnDosTresRespondaOtraVez");
			
		}catch(GameError e){
			System.out.println(e);
		}
	}
}
