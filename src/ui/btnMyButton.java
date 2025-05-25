package ui;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class btnMyButton extends JButton implements MouseListener {
	
	private static final long serialVersionUID = 2799320686797730968L;
	private String Label;
	private Color colorShadow, colorFont;
	private Image icon;
	private boolean over = false;
	private int xLabel, yLabel, xImage, yImage;
	private Graphics2D g2;
	private int x, y, w, h;
	private GradientPaint gra;
	private Boolean enabled = true;
	private Paint graEnabled = new GradientPaint(0, h, new Color(255, 255, 255, 200), w, 0,
			new Color(255, 255, 255, 200));

	private Font font = new Font("Dialog", Font.BOLD, 14);

	private Color colorFontHover = new Color(252, 40, 205);
	private Color colorFontExit = Color.decode("#673ab7");
	private Color colorShadowPress = new Color(0, 0, 0, 0);
	private Color colorShadowExit = new Color(215, 254, 251, 100);
	
	public btnMyButton(int w, int h, String label, Dimension sizeLabel, GradientPaint gra) {
		super();
		x = 0;
		y = 0;

		this.w = w;
		this.h = h;
		this.Label = label;
		this.gra = gra;
		this.xLabel = (int) (w - 6 - (int) sizeLabel.getWidth()) * 2 + 1;
		this.yLabel = (int) (h - 6 - (int) sizeLabel.getHeight()) * 2 + 1;
		colorFont = colorFontExit;

		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorder(null);
		colorShadow = colorShadowExit;
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(this);
	}
	
	
	public btnMyButton(int w, int h, String label, Dimension sizeLabel, Image icon, Dimension sizeIcon,
			GradientPaint gra) {
		super();
		x = 0;
		y = 0;

		this.w = w;
		this.h = h;
		this.Label = label;
		this.gra = gra;
		this.icon = icon;
		this.xImage = (int) (w - 3 - (sizeIcon.getWidth() + 5 + sizeLabel.getWidth())) / 2;
		this.yImage = (int) (h - 6 - sizeIcon.getHeight()) / 2;
		this.xLabel = (int) (this.xImage + sizeIcon.getWidth() + 5);
		this.yLabel = (int) (h - 6 - (int) sizeLabel.getHeight()) * 2 + 1;
		colorFont = colorFontExit;

		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorder(null);
		colorShadow = colorShadowExit;
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(this);
	}

	
	public btnMyButton(int w, int h, String label, GradientPaint gra, Image icon, int xLabel, int yLabel) {
		super();
		x = 0;
		y = 0;

		this.w = w;
		this.h = h;
		this.Label = label;
		this.gra = gra;
		this.icon = icon;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.xImage = xLabel - 21;
		this.yImage = yLabel - 13;
		colorFont = colorFontExit;

		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorder(null);
		colorShadow = colorShadowExit;
		setCursor(new Cursor(Cursor.HAND_CURSOR));
		addMouseListener(this);
	}

	
	public btnMyButton(int w, int h, String Label, GradientPaint gra, Image image, int xLabel, int yLabel, int xImage,
			int yImage) {
		super();
		x = 0;
		y = 0;

		this.w = w;
		this.h = h;
		this.Label = Label;
		this.gra = gra;
		this.icon = image;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.xImage = xImage;
		this.yImage = yImage;
		colorFont = colorFontExit;
		colorShadow = colorShadowExit;

		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorder(null);
		addMouseListener(this);
	}

	
	public btnMyButton(int w, int h, String Label, GradientPaint gra, Image image, int xLabel, int yLabel, int xImage,
			int yImage, Color colorShadowDefault, Color colorFontDefault) {
		super();
		x = 0;
		y = 0;

		this.w = w;
		this.h = h;
		this.Label = Label;
		this.gra = gra;
		this.icon = image;
		this.xLabel = xLabel;
		this.yLabel = yLabel;
		this.xImage = xImage;
		this.yImage = yImage;
		this.colorFont = colorFontDefault;
		this.colorShadow = colorShadowDefault;
		this.colorFontExit = colorFontDefault;
		this.colorShadowExit = colorShadowDefault;

		setCursor(new Cursor(Cursor.HAND_CURSOR));
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorder(null);
		addMouseListener(this);
	}

	public void paintComponent(Graphics g) {
		g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(colorShadow);
		g2.fillRoundRect(x + 1, y + 2, w - 2, h - 3, 30, 30);
		if (enabled)
			g2.setPaint(gra);
		else {
			g2.setPaint(graEnabled);
		}
		g2.fillRoundRect(x, y, w - 3, h - 6, 30, 30);
		g2.drawImage(icon, xImage, yImage, null);
		setFont(font);
		g2.setColor(colorFont);
		g2.drawString(Label, xLabel, yLabel);
		super.paintComponent(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (enabled) {
			x = 2;
			y = 3;
			btnMyButton.this.xLabel = xLabel + 2;
			btnMyButton.this.yLabel = yLabel + 3;
			btnMyButton.this.xImage = xImage + 2;
			btnMyButton.this.yImage = yImage + 3;
			colorShadow = colorShadowPress;
			over = true;
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (over) {
			x = 0;
			y = 0;
			btnMyButton.this.yImage = yImage - 3;
			btnMyButton.this.xLabel = xLabel - 2;
			btnMyButton.this.yLabel = yLabel - 3;
			btnMyButton.this.xImage = xImage - 2;
			colorShadow = colorShadowExit;
			over = false;
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if (enabled) {
			colorFont = colorFontHover;
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		colorFont = colorFontExit;
	}

	
	public void setColorShadowPress(Color color) {
		this.colorShadowPress = color;
	}

	
	public void setColorShadowExit(Color color) {
		this.colorShadowExit = color;
	}

	
	public void setColorHover(Color color) {
		this.colorFontHover = color;
	}


	public void setColorExit(Color color) {
		this.colorFontExit = color;
	}

	
	public void setFontCustom(Font font) {
		this.font = font;
	}

	
	public void setEnabledCustom(boolean enabled) {
		this.enabled = enabled;
		setEnabled(enabled);
	}

	
	public void setTextCustom(String text) {
		this.Label = text;
	}

	
	public void setLocationName(int x, int y) {
		this.xLabel = x;
		this.yLabel = y;
	}
}
