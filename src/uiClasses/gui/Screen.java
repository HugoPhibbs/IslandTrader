package uiClasses.gui;

import javax.swing.*;

import coreClasses.GameEnvironment;


public abstract class Screen {
	
	private final JFrame frame;
	
	private final GameEnvironment game;
	
	protected Screen(String title, GameEnvironment gameEvironment) {
		this.game = gameEvironment;
		iniitalise(title);
	}
	
	
	}

}
