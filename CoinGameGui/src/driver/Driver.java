package driver;

import javax.swing.SwingUtilities;

import model.GameEngineImpl;
import model.interfaces.GameEngine;
import view.GameEngineCallbackImpl;
import view.MainFrame;
import view.interfaces.GameEngineCallback;

public class Driver {

	public static void main(String[] args) {
		GameEngine gameEngine = new GameEngineImpl();
		GameEngineCallback gec = new GameEngineCallbackImpl();
		gameEngine.addGameEngineCallback(gec);
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MainFrame mainFrame = new MainFrame(gameEngine);
				mainFrame.run();
			}
			
		});

	}

}
