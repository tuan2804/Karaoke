package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.GradientPaint;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicButtonUI;

import connectDB.ConnectDB;
import dao.NhanVien_Dao;
import dao.TaiKhoan_Dao;
import entity.NhanVien;
import entity.TaiKhoan;


public class fDangNhap extends JFrame implements ActionListener{

	private JPanel pnlMain;
	ImageIcon backgroundImage = new ImageIcon(fDangNhap.class.getResource("/image/Happy.png"));
	
	private JTextField txtTaiKhoan;
	private JPasswordField txtMatKhau;
	private JPanel pnlMatKhau;
	private JToggleButton tglMatKhau;
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	private GradientPaint gra1 = new GradientPaint(0, 0, Color.decode("#DCDCDC"), getWidth(), 0, Color.decode("#F1F1F2"));
	private JButton btnDangNhap, btnXemPhong;
	
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private TaiKhoan_Dao daoTaiKhoan;
	private NhanVien_Dao daoNhanVien;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					
					fDangNhap frame = new fDangNhap();
					frame.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public fDangNhap() {
	    try {
	        ConnectDB.getInstance().connect();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    setTitle("Đăng nhập");
	    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	    setSize(905, 615);
	    setResizable(false);
	    setLocationRelativeTo(null);
	    URL urlIcon = fDangNhap.class.getResource("/image/iconSystem.png");
	    Image img = Toolkit.getDefaultToolkit().createImage(urlIcon);
	    this.setIconImage(img);
	    createDangNhap();
	    daoNhanVien = new NhanVien_Dao();
	    daoTaiKhoan = new TaiKhoan_Dao();
	}

	
	public void createDangNhap() {
		pnlMain = new JPanel();
		pnlMain.setSize(905, 615);
		pnlMain.setLayout(null);
		
		JLabel lblTaiKhoan = new JLabel("Tài khoản:");
		lblTaiKhoan.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTaiKhoan.setBounds(33, 183, 119, 25);
		pnlMain.add(lblTaiKhoan);
		
		txtTaiKhoan = new JTextField();
		txtTaiKhoan.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtTaiKhoan.setBounds(33, 219, 259, 30);
		txtTaiKhoan.setOpaque(false);
		txtTaiKhoan.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		txtTaiKhoan.setText("NV001");
		pnlMain.add(txtTaiKhoan);
		txtTaiKhoan.setColumns(10);
		
		JLabel lblMatKhau = new JLabel("Mật khẩu:");
		lblMatKhau.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblMatKhau.setBounds(33, 271, 119, 25);
		pnlMain.add(lblMatKhau);
		
		pnlMatKhau = new JPanel();
        pnlMatKhau.setBounds(33, 307, 259, 30);
        pnlMatKhau.setLayout(null);
        pnlMatKhau.setOpaque(false);

        txtMatKhau = new JPasswordField();
        txtMatKhau.setFont(new Font("Tahoma", Font.BOLD, 15));
        txtMatKhau.setBounds(0, 0, 229, 30);
        txtMatKhau.setOpaque(false);
        txtMatKhau.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        txtMatKhau.setText("Tuan2804");
        pnlMatKhau.add(txtMatKhau);
        txtMatKhau.setColumns(10);

        tglMatKhau = new JToggleButton("");
        tglMatKhau.setIcon(new ImageIcon(fDangNhap.class.getResource("/image/eye1.png")));
        tglMatKhau.setBounds(229, 0, 30, 30);
        tglMatKhau.setOpaque(false);
        tglMatKhau.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        tglMatKhau.setBorderPainted(false); // Loại bỏ viền
        tglMatKhau.setFocusPainted(false); // Loại bỏ viền khi nhấn vào
        tglMatKhau.setContentAreaFilled(false); // Loại bỏ vùng nền
        tglMatKhau.setLayout(null);
        
        tglMatKhau.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tglMatKhau.isSelected()) {
                    txtMatKhau.setEchoChar((char) 0); // Hiển thị mật khẩu thực
                } else {
                    txtMatKhau.setEchoChar('\u2022'); // Hiển thị "****"
                }
            }
        });
        pnlMatKhau.add(tglMatKhau);

        pnlMain.add(pnlMatKhau);
		
		btnDangNhap = new btnMyButton(259, 40, "Đăng nhập", new Dimension(210, 23), gra);
		btnDangNhap.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnDangNhap.setBounds(33, 370, 259, 40);
		pnlMain.add(btnDangNhap);
		
		btnXemPhong = new btnMyButton(259, 40, "Dành cho khách hàng", new Dimension(227, 23), gra1);
		btnXemPhong.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fXemPhong xemPhong = new fXemPhong();
				xemPhong.setVisible(true);
				dispose();
			}
		});
		btnXemPhong.setBounds(33, 524, 259, 41);
		pnlMain.add(btnXemPhong);
		
//		JLabel clQuenMK = new JLabel("Quên mật khẩu?");
//		clQuenMK.setForeground(Color.BLUE);
//		clQuenMK.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
//		clQuenMK.addMouseListener(new MouseAdapter() {
//			@Override
//		    public void mouseClicked(MouseEvent e) {
//		        if (e.getButton() == MouseEvent.BUTTON1) { 
//		            ((JFrame) SwingUtilities.getRoot(clQuenMK)).dispose();
//
//		            DialogQuenMatKhau dialog = new DialogQuenMatKhau();
//		            dialog.setVisible(true);
//		        }
//		    }
//			@Override
//		    public void mouseEntered(MouseEvent e) {
//		        clQuenMK.setForeground(Color.decode("#BCBABE")); 
//		    }
//		    
//		    @Override
//		    public void mouseExited(MouseEvent e) {
//		        clQuenMK.setForeground(Color.BLUE);
//		    }
//		});
//		
//		clQuenMK.setBounds(114, 421, 93, 40);
//		pnlMain.add(clQuenMK);
		
		JLabel backgroundLabel = new JLabel();
		backgroundLabel.setIcon(backgroundImage);
		backgroundLabel.setBounds(0, 0, 889, 576);
		pnlMain.add(backgroundLabel);
		
		Image originalImage = backgroundImage.getImage();
		int desiredWidth = 905; 
		int desiredHeight = 615; 
		Dimension newSize = new Dimension(desiredWidth, desiredHeight);
		Image resizedImage = originalImage.getScaledInstance(newSize.width, newSize.height, Image.SCALE_SMOOTH);
		ImageIcon resizedBackgroundImage = new ImageIcon(resizedImage);
		backgroundLabel.setIcon(resizedBackgroundImage);
		backgroundLabel.setBounds(0, 0, desiredWidth, desiredHeight);
		getContentPane().add(pnlMain);
		
	
		// Thêm KeyListener vào btnDangNhap
		btnDangNhap.setToolTipText("Đăng nhập");
		
		
		AbstractAction actionDangNhap = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnDangNhap.doClick(); 
            }
        };
		KeyStroke keyStrokeDangNhap = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		btnDangNhap.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeDangNhap, "shortcut");
		btnDangNhap.getActionMap().put("shortcut", actionDangNhap);
		btnDangNhap.addActionListener(this);
		
		
		
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnDangNhap)) {
			String taiKhoan = txtTaiKhoan.getText().trim();
			String matKhau = txtMatKhau.getText().trim();
			TaiKhoan tk = daoTaiKhoan.getTaiKhoanTheoMa(taiKhoan);
			
			if(tk.getTaiKhoan() == null) {
				JOptionPane.showMessageDialog(this, "Tài khoản không đúng!\nVui lòng kiểm tra lại.", "Thông báo", JOptionPane.ERROR_MESSAGE);
			}
			else if(!tk.getMatKhau().equalsIgnoreCase(matKhau)){
				JOptionPane.showMessageDialog(this, "Mật khẩu không đúng!\nVui lòng kiểm tra lại.", "Thông báo", JOptionPane.ERROR_MESSAGE);
			}
			else {
				NhanVien nv = daoNhanVien.getNVTheoTK(tk.getTaiKhoan());
				if (nv != null) {
					JOptionPane.showMessageDialog(this, "Đăng nhập thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				    fQuanTriHeThong fQL = new fQuanTriHeThong(nv);
				    fQL.setVisible(true);
				    this.setVisible(false);
				    
				} else {
					JOptionPane.showMessageDialog(this, "Đăng nhập thất bại", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
		}
	}
}
