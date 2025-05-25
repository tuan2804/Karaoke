package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.TaiKhoan_Dao;
import entity.NhanVien;
import entity.TaiKhoan;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.util.Locale;
import java.awt.event.ActionEvent;

public class fTaiKhoan extends JFrame implements ActionListener{

	ImageIcon iconQuayLai = new ImageIcon(fTaiKhoan.class.getResource("/image/quaylai1.png"));
	ImageIcon iconDoiMK = new ImageIcon(fTaiKhoan.class.getResource("/image/key.png"));
	
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0, Color.decode("#FAFFD1"));
	private JPanel contentPane, pnlMain;
	private JButton btnQuayLai, btnSua;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private JDateChooser dateChooserNgaySinh;
	private JTextField txtTaiKhoan, txtMatKhau, txtMKNew, txtXacNhan, txtMaNV, txtTenNV, txtCCCD, txtSDT, txtLuong, txtGioiTinh, txtChucVu;
	private JCheckBox chkDoiMatKhau;
	
	private NhanVien headerNV;
	private TaiKhoan_Dao daoTaiKhoan;
	private TaiKhoan headerTK;
	private static String tenCV, tenNV, maNV;

	
	 public static String getCV() {
	    	return tenCV;
	 }
	    
    public static String getNV() {
    	return maNV;
    }
    
    public static String getTenNV() {
    	return tenNV;
    }
	
	/**
	 * Create the frame.
	 */
	public fTaiKhoan(NhanVien nv) {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		daoTaiKhoan = new TaiKhoan_Dao();
		this.headerNV = nv;
		
	
		setTitle("Quản lý tài khoản");
		setResizable(false);
		
		setBounds(100, 100, 922, 584);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		setLocationRelativeTo(null);
		// Ẩn thanh tiêu đề
		setUndecorated(true);

		// Không cho phép tắt JFrame
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		pnlMain = new JPanel(){
			@Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gra = new GradientPaint(179, 0, Color.decode("#A1D6E2"), 180, getHeight(), Color.decode("#F1F1F2"));
                g2.setPaint(gra);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
		};
		pnlMain.setBounds(0, 0, 922, 584);
		contentPane.add(pnlMain);
		pnlMain.setLayout(null);
		
		JLabel lblTitle = new JLabel("QUẢN LÝ THÔNG TIN CÁ NHÂN");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(303, 32, 329, 34);
		pnlMain.add(lblTitle);
		
		JPanel pnlThongTin = new JPanel();
		pnlThongTin.setOpaque(false);
		setBorderTitle(pnlThongTin, "Thông tin tài khoản");
		pnlThongTin.setBounds(10, 120, 422, 141);
		pnlMain.add(pnlThongTin);
		pnlThongTin.setLayout(null);
		
		JLabel lblTaiKhoan = new JLabel("Tên đăng nhập:");
		lblTaiKhoan.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTaiKhoan.setBounds(25, 33, 124, 25);
		pnlThongTin.add(lblTaiKhoan);
		
		txtTaiKhoan = new JTextField(headerNV.getTaiKhoan().getTaiKhoan());
		txtTaiKhoan.setToolTipText("Tên đăn nhập");
		txtTaiKhoan.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtTaiKhoan.setBounds(159, 33, 235, 25);
		pnlThongTin.add(txtTaiKhoan);
		txtTaiKhoan.setColumns(10);
		
		JLabel lblMatKhau = new JLabel("Mật khẩu:");
		lblMatKhau.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMatKhau.setBounds(25, 86, 124, 25);
		pnlThongTin.add(lblMatKhau);
		
		txtMatKhau = new JTextField();
		txtMatKhau.setToolTipText("Mật khẩu");
		txtMatKhau.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtMatKhau.setBounds(159, 86, 235, 25);
		pnlThongTin.add(txtMatKhau);
		txtMatKhau.setColumns(10);
		
		JPanel pnlDoiMK = new JPanel();
		pnlDoiMK.setOpaque(false);
		setBorderTitle(pnlDoiMK, "Đổi mật khẩu");
		pnlDoiMK.setBounds(10, 284, 422, 235);
		pnlMain.add(pnlDoiMK);
		pnlDoiMK.setLayout(null);
		
		chkDoiMatKhau = new JCheckBox("Đổi mật khẩu");
		chkDoiMatKhau.setOpaque(false);
		chkDoiMatKhau.setFont(new Font("Tahoma", Font.BOLD, 11));
		chkDoiMatKhau.setBounds(25, 45, 133, 23);
		chkDoiMatKhau.setBorderPainted(false); // Tắt viền
		pnlDoiMK.add(chkDoiMatKhau);

		
		chkDoiMatKhau.addItemListener(new ItemListener() {
		    public void itemStateChanged(ItemEvent e) {
		        if (e.getStateChange() == ItemEvent.SELECTED) {
		            txtMKNew.setEnabled(true);
		            txtXacNhan.setEnabled(true);
		        } else {
		            txtMKNew.setEnabled(false);
		            txtXacNhan.setEnabled(false);
		        }
		    }
		});
		
		JLabel lblNewMatKhau = new JLabel("Mật khẩu mới:");
		lblNewMatKhau.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewMatKhau.setBounds(25, 100, 118, 25);
		pnlDoiMK.add(lblNewMatKhau);
		
		txtMKNew = new JTextField();
		txtMKNew.setToolTipText("Mật khẩu mới");
		txtMKNew.setEnabled(false);
		txtMKNew.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtMKNew.setBounds(159, 100, 235, 25);
		pnlDoiMK.add(txtMKNew);
		txtMKNew.setColumns(10);
		
		JLabel lblNhapMK = new JLabel("Xác nhận lại:");
		lblNhapMK.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNhapMK.setBounds(25, 168, 118, 25);
		pnlDoiMK.add(lblNhapMK);
		
		txtXacNhan = new JTextField();
		txtXacNhan.setToolTipText("Nhập lại mật khẩu mới");
	    txtXacNhan.setEnabled(false);
		txtXacNhan.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtXacNhan.setBounds(159, 168, 235, 25);
		pnlDoiMK.add(txtXacNhan);
		txtXacNhan.setColumns(10);
		
		JPanel pnlTTCaNhan = new JPanel();
		pnlTTCaNhan.setOpaque(false);
		setBorderTitle(pnlTTCaNhan, "Thông tin nhân viên");
		pnlTTCaNhan.setBounds(471, 120, 441, 399);
		pnlMain.add(pnlTTCaNhan);
		pnlTTCaNhan.setLayout(null);
		
		JLabel lblMaNV = new JLabel("Mã nhân viên:");
		lblMaNV.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMaNV.setBounds(40, 24, 112, 25);
		pnlTTCaNhan.add(lblMaNV);
		
		txtMaNV = new JTextField(headerNV.getMaNhanVien());
		txtMaNV.setEnabled(false);
		txtMaNV.setDisabledTextColor(Color.BLACK);
	
		txtMaNV.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtMaNV.setBounds(160, 24, 201, 25);
		pnlTTCaNhan.add(txtMaNV);
		txtMaNV.setColumns(10);
		
		JLabel lblTen = new JLabel("Họ tên:");
		lblTen.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTen.setBounds(40, 71, 112, 25);
		pnlTTCaNhan.add(lblTen);
		
		txtTenNV = new JTextField(headerNV.getTenNhanVien());
		txtTenNV.setDisabledTextColor(Color.BLACK);
		txtTenNV.setEnabled(false);
		txtTenNV.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtTenNV.setBounds(160, 71, 201, 25);
		pnlTTCaNhan.add(txtTenNV);
		txtTenNV.setColumns(10);
		
		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGioiTinh.setBounds(40, 120, 112, 25);
		pnlTTCaNhan.add(lblGioiTinh);
		
		JLabel lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNgaySinh.setBounds(40, 170, 112, 25);
		pnlTTCaNhan.add(lblNgaySinh);
		
		JDateChooser dateChooserNgaySinh = new JDateChooser(headerNV.getNgaySinh());
		dateChooserNgaySinh.setLocale(new Locale("vi", "VN"));
		dateChooserNgaySinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		dateChooserNgaySinh.setEnabled(false);

		// Truy cập JTextField bên trong JDateChooser
		JTextField textField = ((JTextField) dateChooserNgaySinh.getDateEditor().getUiComponent());
		textField.setDisabledTextColor(Color.BLACK);

		dateChooserNgaySinh.setBounds(160, 170, 201, 25);
		pnlTTCaNhan.add(dateChooserNgaySinh);
		
		JLabel lblCCCD = new JLabel("CCCD:");
		lblCCCD.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCCCD.setBounds(40, 220, 46, 25);
		pnlTTCaNhan.add(lblCCCD);
		
		txtCCCD = new JTextField(headerNV.getcCCD());
		txtCCCD.setEnabled(false);
		txtCCCD.setDisabledTextColor(Color.BLACK);
		txtCCCD.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtCCCD.setBounds(160, 220, 201, 25);
		pnlTTCaNhan.add(txtCCCD);
		txtCCCD.setColumns(10);
		
		JLabel lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSDT.setBounds(40, 267, 112, 25);
		pnlTTCaNhan.add(lblSDT);
		
		txtSDT = new JTextField(headerNV.getSoDT());
		txtSDT.setEnabled(false);
		txtSDT.setDisabledTextColor(Color.BLACK);
		txtSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtSDT.setBounds(160, 267, 201, 25);
		pnlTTCaNhan.add(txtSDT);
		txtSDT.setColumns(10);
		
		JLabel lblChucVu = new JLabel("Chức Vụ:");
		lblChucVu.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblChucVu.setBounds(40, 313, 112, 25);
		pnlTTCaNhan.add(lblChucVu);
		
		JLabel lblLuong = new JLabel("Lương:");
		lblLuong.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLuong.setBounds(40, 358, 112, 25);
		pnlTTCaNhan.add(lblLuong);
		
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		double mucLuong = Double.parseDouble(String.valueOf(headerNV.getMucLuong()));
		txtLuong = new JTextField(String.valueOf(decimalFormat.format(mucLuong)));
		txtLuong.setEnabled(false);
		txtLuong.setDisabledTextColor(Color.BLACK);
		txtLuong.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtLuong.setBounds(160, 358, 201, 25);
		pnlTTCaNhan.add(txtLuong);
		txtLuong.setColumns(10);
		
		txtGioiTinh = new JTextField(headerNV.getGioiTinh());
		txtGioiTinh.setEnabled(false);
		txtGioiTinh.setDisabledTextColor(Color.BLACK);
		txtGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtGioiTinh.setBounds(160, 120, 201, 25);
		pnlTTCaNhan.add(txtGioiTinh);
		txtGioiTinh.setColumns(10);
		
		txtChucVu = new JTextField(headerNV.getChucVu());
		txtChucVu.setEnabled(false);
		txtChucVu.setDisabledTextColor(Color.BLACK);
		txtChucVu.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtChucVu.setBounds(160, 313, 201, 25);
		pnlTTCaNhan.add(txtChucVu);
		txtChucVu.setColumns(10);
		
		btnSua = new btnMyButton(150, 40, "Đổi mật khẩu", new Dimension(95, 23), iconDoiMK.getImage(), new Dimension(20,20), gra);
		btnSua.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSua.setBounds(762, 530, 150, 40);
		pnlMain.add(btnSua);
		
		btnQuayLai = new btnMyButton(130, 40, "Quay lại", new Dimension(70, 23), iconQuayLai.getImage(), new Dimension(40,20), gra);
		btnQuayLai.setToolTipText("Quay lại");
		btnQuayLai.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnQuayLai.setBounds(782, 69, 130, 40);
		pnlMain.add(btnQuayLai);
		AbstractAction actionQuayLai = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnQuayLai.doClick(); 
            }
        };
		KeyStroke keyStrokeQuayLai = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
		btnQuayLai.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeQuayLai, "shortcut");
		btnQuayLai.getActionMap().put("shortcut", actionQuayLai);
		
		btnQuayLai.addActionListener(this);
		btnSua.addActionListener(this);
	}
	
	public void setBorderTitle(JPanel pnl, String title) {
		Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#1995AD"), 2),
				title, TitledBorder.LEADING, TitledBorder.TOP, fontBold, Color.black);
		pnl.setBorder(border);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(btnQuayLai)) {
			fQuanTriHeThong quanTri = new fQuanTriHeThong(headerNV);
			quanTri.setVisible(true);
			
			 // Đóng frame hiện tại
	        JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(pnlMain);
	        currentFrame.dispose();
		}
		
		else if (o.equals(btnSua)) {
		    try {
		        int cn = JOptionPane.showConfirmDialog(this, "Bạn muốn đổi mật khẩu?", "Thông báo", JOptionPane.YES_NO_OPTION);
		        if (cn == JOptionPane.YES_OPTION) {
		            String taiKhoan = txtTaiKhoan.getText();
		            String matKhauCu = txtMatKhau.getText();
		            String matKhauMoi = txtMKNew.getText();
		            String xacNhanMatKhau = txtXacNhan.getText();

		            // Kiểm tra mật khẩu cũ
		            if (kiemTraMatKhau(taiKhoan, matKhauCu)) {
		                if (matKhauMoi.equals(xacNhanMatKhau)) {
		                    TaiKhoan tk = new TaiKhoan(taiKhoan);
		                    tk.setMatKhau(matKhauMoi);
		                    daoTaiKhoan.suaTK(tk);
		                    lamMoi();
		                    JOptionPane.showMessageDialog(this, "Đổi mật khẩu thành công!");
		                } else {
		                    JOptionPane.showMessageDialog(this, "Mật khẩu mới không khớp. Vui lòng nhập lại!");
		                }
		            } else {
		                JOptionPane.showMessageDialog(this, "Mật khẩu cũ không chính xác. Vui lòng nhập lại!");
		            }
		        }
		    } catch (Exception e2) {
		        JOptionPane.showMessageDialog(this, "Lỗi không thể đổi mật khẩu!");
		    }
		}
	}
	
	// Phương thức kiểm tra mật khẩu cũ
	private boolean kiemTraMatKhau(String taiKhoan, String matKhauCu) {
	    TaiKhoan tk = daoTaiKhoan.getTaiKhoanTheoMa(taiKhoan);
	    if (tk != null) {
	        return tk.getMatKhau().equals(matKhauCu);
	    }
	    return false;
	}
	
	public void lamMoi() {
		txtMKNew.setText("");
		txtXacNhan.setText("");
		txtMatKhau.setText("");
	}
}
