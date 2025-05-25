package entity;

import javax.swing.JLabel;
import javax.swing.JPanel;

import ui.pnMenuItem;

public class Category {

	private String kind;
	private pnMenuItem menu;
	public Category() {
		super();
	}
	public Category(String kind, pnMenuItem menu) {
		super();
		this.kind = kind;
		this.menu = menu;
	}
	public String getKind() {
		return kind;
	}
	public void setKind(String kind) {
		this.kind = kind;
	}
	public pnMenuItem getMenu() {
		return menu;
	}
	public void setMenu(pnMenuItem menu) {
		this.menu = menu;
	}
}
