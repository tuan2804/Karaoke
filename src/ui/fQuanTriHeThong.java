package ui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Insets;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import entity.Category;
import entity.NhanVien;
import entity.TaiKhoan;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;

import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.awt.event.ActionEvent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.SwingUtilities;

public class fQuanTriHeThong extends JFrame {

	private JPanel PanelALL;
	private Image img_logo = new ImageIcon(fQuanTriHeThong.class.getResource("/image/account1.png")).getImage().getScaledInstance(180, 135, Image.SCALE_SMOOTH);
	private Image imgBG = new ImageIcon(fQuanTriHeThong.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private Image img_logout = new ImageIcon(fQuanTriHeThong.class.getResource("/image/quaylai.png")).getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
	private ImageIcon imgHome = new ImageIcon(fQuanTriHeThong.class.getResource("/image/home1.png"));
	private ImageIcon iconNV = new ImageIcon(fQuanTriHeThong.class.getResource("/image/nhanvien.png"));
	private ImageIcon iconDV = new ImageIcon(fQuanTriHeThong.class.getResource("/image/dichvu.png"));
	private ImageIcon iconKH = new ImageIcon(fQuanTriHeThong.class.getResource("/image/khachhang.png"));
	private ImageIcon iconHD = new ImageIcon(fQuanTriHeThong.class.getResource("/image/hoadon1.png"));
	private ImageIcon iconTK = new ImageIcon(fQuanTriHeThong.class.getResource("/image/thongke1.png"));
	private ImageIcon subMenu = new ImageIcon(fQuanTriHeThong.class.getResource("/image/next.png"));
	private ImageIcon iconDatPhong = new ImageIcon(fQuanTriHeThong.class.getResource("/image/iconThue.png"));
	private ImageIcon iconPhong = new ImageIcon(fQuanTriHeThong.class.getResource("/image/phong.png"));
	
	private JPanel pnlMenuSwap, pnlMenu, pnlBody, pnlNgan;
	private JScrollPane scrollPane;
	private JLabel pnlLogo;
	private Font font = new Font("Dialog", Font.BOLD, 14);
	private pnMenuItem menuTC, menuNV1, menuNV2, menuNV3, menuNV, menuKH, menuKH1, menuKH2, menuDV, menuPhong1, menuPhong2, menuPhong3, menuPhong, menuDV1, menuDV2, menuDV3, 
	menuHoaDon, menuHoaDon1, menuHoaDon2, menuHoaDon3, menuHoaDon4, menuTK, menuTK1, menuTK2, menuTK3, menuLabel, menuQLDatPhong, menuQLDatPhong1, menuQLDatPhong2, menuQLDatPhong3, menuHuongDan;
	private JButton btnSignOut, btnHeaderInfo;
	Insets insets = new Insets(20, 20, 20, 20);

	private NhanVien headerNV;
	private JLabel lblHeaderMaNV, lblHeaderTen, lblChucVu;
	private static String maNV, tenNV, chucVu;
	
	public static String getTenNV() {
		return tenNV;
	}
	
	public static String getMaNV() {
		return maNV;
	}
	
	public static String getChucVu() {
		return chucVu;
	}
	
	public fQuanTriHeThong(NhanVien nv) {
		this.headerNV = nv;
		URL urlIcon = fQuanTriHeThong.class.getResource("/image/iconSystem.png");
		Image img = Toolkit.getDefaultToolkit().createImage(urlIcon);
		this.setIconImage(img);
		gui(nv);
		
		tenNV = lblHeaderTen.getText();
		maNV = lblHeaderMaNV.getText();
		chucVu = lblChucVu.getText();
		btnSignOut = new JButton("Đăng xuất", new ImageIcon(img_logout));
		btnSignOut.setBounds(0, 999, 350, 41);
		PanelALL.add(btnSignOut);
		btnSignOut.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        int choice = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn đăng xuất?", "Xác nhận đăng xuất", JOptionPane.YES_NO_OPTION);
		        if (choice == JOptionPane.YES_OPTION) {
		            fDangNhap dangNhap = new fDangNhap();
		            dispose();
		            dangNhap.setVisible(true);
		        }
		    }
		});
		btnSignOut.setFont(new Font("Arial", Font.BOLD, 18));		
		btnSignOut.setBackground(Color.decode("#1995AD"));
		btnSignOut.setForeground(Color.BLACK);
		btnSignOut.setHorizontalAlignment(SwingConstants.LEFT);
		btnSignOut.setBorderPainted(false);
		btnSignOut.setFocusPainted(false);
		btnSignOut.setToolTipText("Đăng xuất Ctrl + O");
		AbstractAction actionDangXuat = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnSignOut.doClick(); 
            }
        };
		KeyStroke keyStrokeDangXuat = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		btnSignOut.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeDangXuat, "shortcut");
		btnSignOut.getActionMap().put("shortcut", actionDangXuat);
		
		
		pnlNgan = new JPanel();
		pnlNgan.setBackground(Color.decode("#BCBABE"));
		pnlNgan.setBounds(349, 0, 16, 1040);
		PanelALL.add(pnlNgan);
		ConvertMenu controller = new ConvertMenu(pnlBody);
		controller.setView(null);
		List<Category> listItem = new ArrayList<>();
		if(btnHeaderInfo.getText().contains("QL")) {
			listItem.add(new Category("TrangChu", menuTC));
			listItem.add(new Category("datPhong", menuQLDatPhong1));
			listItem.add(new Category("Phong", menuPhong1));
			listItem.add(new Category("loaiPhong", menuPhong2));
			listItem.add(new Category("timKiemPhong", menuPhong3));
			listItem.add(new Category("nhanVien", menuNV1));
			listItem.add(new Category("timKiemNhanVien", menuNV3));
			listItem.add(new Category("dichVu", menuDV1));
			listItem.add(new Category("loaiDV", menuDV2));
			listItem.add(new Category("timKiemDV", menuDV3));
			listItem.add(new Category("khachHang", menuKH1));
			listItem.add(new Category("timKiemKH", menuKH2));
			listItem.add(new Category("lapHoaDon", menuHoaDon3));
			listItem.add(new Category("thanhToan", menuHoaDon4));
			listItem.add(new Category("cTHoaDon", menuHoaDon2));
			listItem.add(new Category("tKDoanhThu", menuTK1));
			listItem.add(new Category("tKKHGheQuan", menuTK2));
			
		}
		else if (btnHeaderInfo.getText().contains("NV")){
			listItem.add(new Category("TrangChu", menuTC));
			listItem.add(new Category("datPhong", menuQLDatPhong1));
			listItem.add(new Category("timKiemPhong", menuPhong3));
			listItem.add(new Category("timKiemDV", menuDV3));
			listItem.add(new Category("khachHang", menuKH1));
			listItem.add(new Category("timKiemKH", menuKH2));
			listItem.add(new Category("lapHoaDon", menuHoaDon3));
			listItem.add(new Category("thanhToan", menuHoaDon4));
			listItem.add(new Category("cTHoaDon", menuHoaDon2));
			listItem.add(new Category("tKDoanhThu", menuTK1));
			listItem.add(new Category("tKKHGheQuan", menuTK2));
			
		}
		controller.setEvent(listItem);
		
		
		setTitle("Quản Trị Hệ Thống");
		
    	setSize(1920,1080);
		setUndecorated(true);
	    setResizable(false);
	    setVisible(true);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setLocationRelativeTo(null);
	

	    setExtendedState(JFrame.MAXIMIZED_BOTH);
	    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    setSize(screenSize.width, screenSize.height - 40);
	}
	
	public void gui(NhanVien nv) {
		setBounds(100, 100, 1600, 763);
		PanelALL = new JPanel();
		PanelALL.setBorder(new EmptyBorder(5, 5, 5, 5));
		 
		setContentPane(PanelALL);
		PanelALL.setLayout(null);

		
		pnlMenu = new JPanel() {
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gra = new GradientPaint(179, 0, Color.decode("#A1D6E2"), 180, getHeight(), Color.decode("#F1F1F2"));
                g2.setPaint(gra);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
		};
		pnlMenu.setBounds(0, 0, 350, 1001);
		pnlMenu.setLayout(null);
		pnlLogo = new JLabel("");
		pnlLogo.setToolTipText("Quản lý tài khoản cá nhân Ctrl + T");
		pnlLogo.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        // Sự kiện khi nhấp chuột vào JLabel
		        fTaiKhoan taiKhoan = new fTaiKhoan(nv);
		        taiKhoan.setVisible(true);
		        
		        // Đóng frame hiện tại
		        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(pnlLogo);
		        currentFrame.dispose();
		    }
		});
		
		
		AbstractAction showTaiKhoanAction = new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        
		        fTaiKhoan taiKhoan = new fTaiKhoan(nv);
		        taiKhoan.setVisible(true);

		        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(pnlLogo);
		        currentFrame.dispose();
		    }
		};

		// Gắn phím tắt (Ctrl+T) cho panel pnlLogo
		InputMap inputMap = pnlLogo.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_T, InputEvent.CTRL_DOWN_MASK), "showTaiKhoan");
		ActionMap actionMap = pnlLogo.getActionMap();
		actionMap.put("showTaiKhoan", showTaiKhoanAction);
		pnlLogo.setBounds(87, 11, 194, 168);
		pnlLogo.setIcon(new ImageIcon(img_logo));
		pnlMenu.add(pnlLogo);
		
		PanelALL.add(pnlMenu);
	
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 237, 350, 753);
		scrollPane.setBorder(null);
		pnlMenu.add(scrollPane);
		
		pnlMenuSwap = new JPanel(){
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gra = new GradientPaint(179, 0, Color.decode("#A1D6E2"), 180, getHeight(), Color.decode("#F1F1F2"));
                g2.setPaint(gra);
                g2.fillRect(2, 2, getWidth(), getHeight());
            }
		};
	
		scrollPane.setViewportView(pnlMenuSwap);
		pnlMenuSwap.setLayout(new BoxLayout(pnlMenuSwap, BoxLayout.Y_AXIS));
		
		lblHeaderMaNV = new JLabel(headerNV.getMaNhanVien());
		lblHeaderMaNV.setVisible(false);
		lblHeaderMaNV.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblHeaderMaNV.setBounds(10, 174, 108, 25);
		pnlMenu.add(lblHeaderMaNV);
		
		lblChucVu = new JLabel(headerNV.getChucVu() + ":");
		lblChucVu.setHorizontalAlignment(SwingConstants.CENTER);
		lblChucVu.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblChucVu.setBounds(0, 174, 108, 25);
		pnlMenu.add(lblChucVu);
		
		lblHeaderTen = new JLabel(headerNV.getTenNhanVien());
		lblHeaderTen.setHorizontalAlignment(SwingConstants.CENTER);
		lblHeaderTen.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblHeaderTen.setBounds(127, 174, 213, 25);
		pnlMenu.add(lblHeaderTen);
		
		btnHeaderInfo = new JButton("New button");
		btnHeaderInfo.setVisible(false);
		if(nv.getChucVu().equalsIgnoreCase("Quản lý"))
			btnHeaderInfo.setText("QL");
		else {
			btnHeaderInfo.setText("NV");
		}
		btnHeaderInfo.setBounds(251, 157, 89, 23);
		pnlMenu.add(btnHeaderInfo);
		
		JLabel lblTroGiup = new JLabel("Trợ giúp?");
		lblTroGiup.setFont(new Font("Tahoma", Font.ITALIC, 15));
		lblTroGiup.setBounds(122, 210, 70, 25);
		pnlMenu.add(lblTroGiup);
		lblTroGiup.setForeground(Color.BLUE);
		lblTroGiup.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		lblTroGiup.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        try {
		            String filePath = System.getProperty("user.dir") + "\\data\\2023_7_ApplicationDevelopment_UserManual.pdf";
		            if (new File(filePath).exists()) {
		                Process p = Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + filePath);
		            } else {
		                JOptionPane.showMessageDialog(null, "Không tìm thấy file hướng dẫn");
		            }
		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		    }
		});

		
		if(btnHeaderInfo.getText().contains("QL")) {
			phanQuyenQL();
		}
		
		else if (btnHeaderInfo.getText().contains("NV")) {
			phanQuyenNV();
		}
		
		pnlBody = new JPanel();
		pnlBody.setBounds(365, 0, 1559, 1040);
		PanelALL.add(pnlBody);
		
		
	}
	
	public void phanQuyenQL() {
		menuTC = new pnMenuItem(imgHome, "Trang Chủ");
		menuTC.setBackground(Color.decode("#1995AD"));
		
		
		menuQLDatPhong1 = new pnMenuItem(subMenu, "Thêm Đơn Đặt Phòng");
		menuPhong1 = new pnMenuItem(subMenu, "Quản Lý Phòng");
		menuPhong2 = new pnMenuItem(subMenu, "Quản Lý Loại Phòng");
		menuPhong3 = new pnMenuItem(subMenu, "Tìm Kiếm Phòng");
		menuPhong = new pnMenuItem(iconPhong, "Phòng Hát", menuQLDatPhong1, menuPhong1, menuPhong2, menuPhong3);
		menuPhong.setBackground(Color.decode("#1995AD"));
		menuQLDatPhong1.setBackground(Color.decode("#1995AD"));
		menuPhong1.setBackground(Color.decode("#1995AD"));
		menuPhong2.setBackground(Color.decode("#1995AD"));
		menuPhong3.setBackground(Color.decode("#1995AD"));
		
		menuNV1 = new pnMenuItem(subMenu, "Quản Lý Nhân Viên");
		menuNV3 = new pnMenuItem(subMenu, "Tìm Kiếm Nhân Viên");
		menuNV = new pnMenuItem(iconNV, "Nhân Viên", menuNV1, menuNV3);
		menuNV.setBackground(Color.decode("#1995AD"));
		menuNV1.setBackground(Color.decode("#1995AD"));
		menuNV3.setBackground(Color.decode("#1995AD"));
		
		
		menuDV1 = new pnMenuItem(subMenu, "Quản Lý Dịch Vụ");
		menuDV2 = new pnMenuItem(subMenu, "Quản Lý Loại Dịch Vụ");
		menuDV3 = new pnMenuItem(subMenu, "Tìm Kiếm Dịch Vụ");
		menuDV = new pnMenuItem(iconDV, "Dịch Vụ",menuDV1, menuDV2, menuDV3);
		menuDV.setBackground(Color.decode("#1995AD"));
		menuDV1.setBackground(Color.decode("#1995AD"));
		menuDV2.setBackground(Color.decode("#1995AD"));
		menuDV3.setBackground(Color.decode("#1995AD"));
		
		menuKH1 = new pnMenuItem(subMenu, "Quản Lý Khách Hàng");
		menuKH2 = new pnMenuItem(subMenu, "Tìm Kiếm Khách Hàng");
		menuKH = new pnMenuItem(iconKH, "Khách Hàng", menuKH1, menuKH2);
		menuKH.setBackground(Color.decode("#1995AD"));
		menuKH1.setBackground(Color.decode("#1995AD"));
		menuKH2.setBackground(Color.decode("#1995AD"));
		
		menuHoaDon3 = new pnMenuItem(subMenu, "Nhận Phòng");
		menuHoaDon4 = new pnMenuItem(subMenu, "Lập hóa đơn");
		menuHoaDon2 = new pnMenuItem(subMenu, "Quản Lý Hóa Đơn");
		menuHoaDon = new pnMenuItem(iconHD, "Hóa Đơn", menuHoaDon3, menuHoaDon4, menuHoaDon2);
		menuHoaDon.setBackground(Color.decode("#1995AD"));
		menuHoaDon2.setBackground(Color.decode("#1995AD"));
		menuHoaDon3.setBackground(Color.decode("#1995AD"));
		menuHoaDon4.setBackground(Color.decode("#1995AD"));
		
		menuTK1 = new pnMenuItem(subMenu, "Thống Kê Doanh Thu");
		menuTK2 = new pnMenuItem(subMenu, "Thống Kê Khách Hàng Ghé Quán");
		menuTK = new pnMenuItem(iconTK, "Thống Kê", menuTK1, menuTK2);
		menuTK.setBackground(Color.decode("#1995AD"));
		menuTK1.setBackground(Color.decode("#1995AD"));
		menuTK2.setBackground(Color.decode("#1995AD"));
		
		
		
		addMenu(menuTC, menuPhong, menuNV, menuDV, menuKH, menuHoaDon, menuTK);
	}
	
	public void phanQuyenNV() {
		menuTC = new pnMenuItem(imgHome, "Trang Chủ");
		menuTC.setBackground(Color.decode("#1995AD"));
		
		
		menuQLDatPhong1 = new pnMenuItem(subMenu, "Thêm Đơn Đặt Phòng");
		menuPhong3 = new pnMenuItem(subMenu, "Tìm Kiếm Phòng");
		menuPhong = new pnMenuItem(iconPhong, "Phòng Hát", menuQLDatPhong1, menuPhong3);
		menuPhong.setBackground(Color.decode("#1995AD"));
		menuQLDatPhong1.setBackground(Color.decode("#1995AD"));
		menuPhong3.setBackground(Color.decode("#1995AD"));
		

		 
		menuDV3 = new pnMenuItem(subMenu, "Tìm Kiếm Dịch Vụ");
		menuDV = new pnMenuItem(iconDV, "Dịch Vụ", menuDV3);
		menuDV.setBackground(Color.decode("#1995AD"));
		menuDV3.setBackground(Color.decode("#1995AD"));
		
		menuKH1 = new pnMenuItem(subMenu, "Quản Lý Khách Hàng");
		menuKH2 = new pnMenuItem(subMenu, "Tìm Kiếm Khách Hàng");
		menuKH = new pnMenuItem(iconKH, "Khách Hàng", menuKH1, menuKH2);
		menuKH.setBackground(Color.decode("#1995AD"));
		menuKH1.setBackground(Color.decode("#1995AD"));
		menuKH2.setBackground(Color.decode("#1995AD"));
		
		menuHoaDon3 = new pnMenuItem(subMenu, "Lập Hóa Đơn");
		menuHoaDon4 = new pnMenuItem(subMenu, "Thanh Toán");
		menuHoaDon2 = new pnMenuItem(subMenu, "Quản Lý Hóa Đơn");
		menuHoaDon = new pnMenuItem(iconHD, "Hóa Đơn", menuHoaDon3, menuHoaDon4, menuHoaDon2);
		menuHoaDon.setBackground(Color.decode("#1995AD"));
		menuHoaDon2.setBackground(Color.decode("#1995AD"));
		menuHoaDon3.setBackground(Color.decode("#1995AD"));
		menuHoaDon4.setBackground(Color.decode("#1995AD"));
		
		menuTK1 = new pnMenuItem(subMenu, "Thống Kê Doanh Thu");
		menuTK2 = new pnMenuItem(subMenu, "Thống Kê Khách Hàng Ghé Quán");
		menuTK = new pnMenuItem(iconTK, "Thống Kê", menuTK1, menuTK2);
		menuTK.setBackground(Color.decode("#1995AD"));
		menuTK1.setBackground(Color.decode("#1995AD"));
		menuTK2.setBackground(Color.decode("#1995AD"));
		
		
		addMenu(menuTC, menuPhong, menuDV, menuKH, menuHoaDon, menuTK);
	}
	
	private void addMenu(pnMenuItem... menu) {
        for (int i = 0; i < menu.length; i++) {
        	pnlMenuSwap.add(menu[i]);
            ArrayList<pnMenuItem> subMenu = menu[i].getSubMenu();
            for (pnMenuItem m : subMenu) {
                addMenu(m);
            }
        }
        pnlMenuSwap.revalidate();
    }
	
	public void setBorderTitlePanel(JPanel pnl, String title) {
		Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#000000"), 2),
				title, TitledBorder.LEADING, TitledBorder.TOP, font, Color.black);
		pnl.setBorder(border);
	}
}
