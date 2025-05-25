package ui;

import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class pnTrangChu extends JPanel {
	private JTextField txtQunLNh;
	private JLabel lblTime;

	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private Image imgBG = new ImageIcon(pnLoaiPhong.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private Image img_logo = new ImageIcon(fQuanTriHeThong.class.getResource("/image/logo1.png")).getImage().getScaledInstance(1242, 625, Image.SCALE_SMOOTH);

	String imageBackGround = "img/bg_index_test 1.png";
	String imageBanner = "img/banner.png";
	
	Timer t;
	SimpleDateFormat st;
	/**
	 * Create the panel.
	 */
	public pnTrangChu() {
		setLayout(null);
		JPanel pnMain = new JPanel();
	
		pnMain.setBounds(0, 0, 1559, 1040);
		add(pnMain);
		pnMain.setLayout(null);
		
		
		
		 JPanel pnlTitle = new JPanel(){
			    @Override
			    protected void paintComponent(Graphics g) {
			        super.paintComponent(g);
			        // Vẽ hình ảnh làm background
			        g.drawImage(imgBG, 0, 0, getWidth(), getHeight(), this);
			    }
		};
		pnlTitle.setLayout(null);
		setBorderTitle(pnlTitle, "");
		pnlTitle.setOpaque(false);
	    pnlTitle.setBounds(0, 0, 1554, 81);
	    pnMain.add(pnlTitle);
	    
	    JLabel lblTitle = new JLabel("QUẢN LÝ KARAOKE NICE");
	    lblTitle.setBounds(584, 19, 338, 36);
	    pnlTitle.add(lblTitle);
	    lblTitle.setForeground(Color.BLACK);
	    lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
	    
	    time();
	    
	    JPanel pnlBanner = new JPanel(){
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        // Vẽ hình ảnh làm background
		        g.drawImage(img_logo, 0, 0, getWidth(), getHeight(), this);
		    }
	};
	    pnlBanner.setLayout(null);
		setBorderTitle(pnlBanner, "");
		pnlBanner.setOpaque(false);
	    pnlBanner.setBounds(83, 282, 1385, 700);
	    pnMain.add(pnlBanner);
	    
	    JPanel panel = new JPanel();
	    panel.setBounds(495, 148, 527, 81);
	    pnMain.add(panel);
	    panel.setLayout(null);
	    lblTime = new JLabel(" ");
	    lblTime.setForeground(Color.RED);
	    lblTime.setBounds(10, 11, 507, 59);
	    panel.add(lblTime);
	    lblTime.setHorizontalAlignment(SwingConstants.CENTER);
	    lblTime.setFont(new Font("Tahoma", Font.BOLD, 30));

	}
	
	public void setBorderTitle(JPanel pnl, String title) {
		Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#1995AD"), 2),
				title, TitledBorder.LEADING, TitledBorder.TOP, fontBold, Color.black);
		pnl.setBorder(border);
	}
	
	public void time() {
		
		t = new Timer(0,new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
	            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	        
	        Date dt  =new Date();
	        st = new SimpleDateFormat("hh:mm:ss a");	
	        String tt = st.format(dt);
	        lblTime.setText(tt);
	        
	        }
	    });
	  
	    t.start();
	  		
	}
}
