package uiClasses.gui;

import java.awt.Container;

import javax.swing.*;

import coreClasses.GameEnvironment;


public abstract class Screen {
	
	private final GameEnvironment game;
	private final Screen parent;
	
	protected Screen(String title, GameEnvironment gameEvironment, Screen parent) {
		this.game = gameEvironment;
		this.parent = parent;
		initialize(title);
	}
	
	abstract protected void initialize(String title);
	
	
	protected GameEnvironment getGame() {
		return game;
	}
	
	protected void show() {
		
	}
	
	protected boolean confirmQuit() {
		return true;
	}
	
	public void quit() {
		
	}
	
	public void goBack() {
		
	}
	
	public void showError() {
		
	}
	
	public Screen getParent() {
		return parent;
	}
}
