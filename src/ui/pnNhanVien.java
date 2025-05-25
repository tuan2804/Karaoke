package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.geom.RoundRectangle2D;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.NhanVien_Dao;
import dao.Regex;
import dao.TaiKhoan_Dao;
import entity.KhachHang;
import entity.NhanVien;
import entity.TaiKhoan;

public class pnNhanVien extends JPanel implements ActionListener, MouseListener{
	private JTextField tfMaNhanVien, tfTenNhanVien, tfCCCD, tfSDT;
	private JButton btnThem, btnSua, btnLamMoi;
	private JComboBox<String> cbGioiTinh;
	private JLabel lblGioiTinh, lblNgaySinh, lblCCCD, lblSDT, lblChucVu, lblCaLamViec, lblTinhTrang;
	private JComboBox<String> cbCaLamViec, cbChucVu, cbTinhTrang;
	private JDateChooser dateChooserNgaySinh;
	
	private Image imgBG = new ImageIcon(fQuanTriHeThong.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAdd = new ImageIcon(pnNhanVien.class.getResource("/image/add1.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnNhanVien.class.getResource("/image/update.png"));
	private ImageIcon iconReload = new ImageIcon(pnNhanVien.class.getResource("/image/reload.png"));
	private ImageIcon iconFind = new ImageIcon(pnNhanVien.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(pnNhanVien.class.getResource("/image/Rewind.png"));
	private ImageIcon iconLast = new ImageIcon(pnNhanVien.class.getResource("/image/Forward.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	
	private int nam, thang, ngay;
	private JTable tblQuanLyNhanVien;
	private DefaultTableModel model;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private NhanVien_Dao daoNhanVien;
	private Regex regex;
	private JTextField tfMucLuong;
	private TaiKhoan_Dao daoTaiKhoan;
	/**
	 * Create the panel.
	 */
	
	
	public pnNhanVien() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		regex = new Regex();
		daoNhanVien = new NhanVien_Dao();
		daoTaiKhoan = new TaiKhoan_Dao();
		setLayout(null);
		JPanel pnMain = new JPanel();
		pnMain.setBounds(0, 0, 1599, 1040);
		add(pnMain);
		pnMain.setLayout(null);
		
		JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(640, 20, 276, 36);
		pnMain.add(lblTitle);
		
		JLabel lblMaNhanVien = new JLabel("Mã nhân viên:");
		lblMaNhanVien.setForeground(Color.BLACK);
		lblMaNhanVien.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMaNhanVien.setBounds(98, 105, 114, 25);
		pnMain.add(lblMaNhanVien);
		
		tfMaNhanVien = new JTextField();
		tfMaNhanVien.setText(daoNhanVien.getMaNV());
		tfMaNhanVien.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfMaNhanVien.setBounds(222, 105, 175, 25);
		pnMain.add(tfMaNhanVien);
		tfMaNhanVien.setColumns(10);
		
		JLabel lblTenNhanVien = new JLabel("Tên nhân viên:");
		lblTenNhanVien.setForeground(Color.BLACK);
		lblTenNhanVien.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTenNhanVien.setBounds(630, 105, 114, 25);
		pnMain.add(lblTenNhanVien);
		
		cbGioiTinh = new JComboBox();
		cbGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbGioiTinh.addItem("Nam");
		cbGioiTinh.addItem("Nữ");
		cbGioiTinh.setBounds(1273, 105, 175, 25);
		pnMain.add(cbGioiTinh);
		
		btnThem = new btnMyButton(105, 40, "Thêm", new Dimension(50, 23), iconAdd.getImage(), new Dimension(25,25), gra);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnThem.setBounds(222, 320, 137, 36);
		pnMain.add(btnThem);
		btnThem.setToolTipText("Thêm nhân viên");
		AbstractAction actionThem = new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        btnThem.doClick();
		    }
		};

		KeyStroke keyStrokeThem = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.ALT_DOWN_MASK);
		btnThem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeThem, "shortcut");
		btnThem.getActionMap().put("shortcut", actionThem);
		
		btnSua = new btnMyButton(130, 40, "Cập nhập", new Dimension(60, 23), iconUpdate.getImage(), new Dimension(25,25), gra);
		btnSua.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSua.setBounds(456, 320, 137, 36);
		pnMain.add(btnSua);
		btnSua.setToolTipText("Cập nhập nhân viên");
		AbstractAction actionSua = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnSua.doClick(); 
            }
        };
		KeyStroke keyStrokeSua = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.ALT_DOWN_MASK);
		btnSua.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeSua, "shortcut");
		btnSua.getActionMap().put("shortcut", actionSua);

		
		btnLamMoi = new btnMyButton(130, 40, "Làm mới", new Dimension(60, 23), iconReload.getImage(), new Dimension(25,25), gra);
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLamMoi.setBounds(714, 320, 137, 36);
		pnMain.add(btnLamMoi);
		btnLamMoi.setToolTipText("Làm mới");
		AbstractAction actionLamMoi = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnLamMoi.doClick(); 
            }
        };
		KeyStroke keyStrokeLamMoi = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.ALT_DOWN_MASK);
		btnLamMoi.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeLamMoi, "shortcut");
		btnLamMoi.getActionMap().put("shortcut", actionLamMoi);
		
		JPanel pnlTable = new JPanel();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		setBorderTitle(pnlTable, "Danh sách nhân viên");
		pnlTable.setBounds(98, 390, 1350, 588);
		pnlTable.setOpaque(false);
		String[] cols = { "STT", "Mã nhân viên", "Tên nhân viên", "Giới tính", "Ngày sinh", "CCCD", "Số điện thoại", "Chức vụ", "Lương",
				"Ca làm việc", "Tình trạng", "Mật khẩu" };
		model = new DefaultTableModel(cols, 0);
		tblQuanLyNhanVien = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		// Lấy đối tượng TableColumnModel từ JTable
		TableColumnModel columnModel = tblQuanLyNhanVien.getColumnModel();

		// Đặt độ rộng cho cột "STT" (ví dụ: 50 pixel)
		int sttColumnWidth = 10;
		TableColumn sttColumn = columnModel.getColumn(0);
		sttColumn.setPreferredWidth(sttColumnWidth);

		// Đặt độ rộng cho cột "Mã Loại DV" (ví dụ: 200 pixel)
		int maColumnWidth = 80;
		TableColumn maLoaiDVColumn = columnModel.getColumn(1);
		maLoaiDVColumn.setPreferredWidth(maColumnWidth);

		// Đặt độ rộng cho cột "Tên Loại DV" (ví dụ: 300 pixel)
		int tenColumnWidth = 200;
		TableColumn tenLoaiDVColumn = columnModel.getColumn(2);
		tenLoaiDVColumn.setPreferredWidth(tenColumnWidth);
		
		int gioiTinhColumnWidth = 50;
		TableColumn gioiTinhColumn = columnModel.getColumn(3);
		gioiTinhColumn.setPreferredWidth(gioiTinhColumnWidth);

		int ngaySinhColumnWidth = 80;
		TableColumn ngaySinhColumn = columnModel.getColumn(4);
		ngaySinhColumn.setPreferredWidth(ngaySinhColumnWidth);
		
		int cccdColumnWidth = 100;
		TableColumn cccdColumn = columnModel.getColumn(5);
		cccdColumn.setPreferredWidth(cccdColumnWidth);
		
		setCustomTable(tblQuanLyNhanVien);
		tblQuanLyNhanVien.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblQuanLyNhanVien);
		scrTable.setBounds(10, 25, 1330, 552);
		pnlTable.add(scrTable);
		pnMain.add(pnlTable);
		
		tfTenNhanVien = new JTextField();
		tfTenNhanVien.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfTenNhanVien.setColumns(10);
		tfTenNhanVien.setBounds(754, 105, 175, 25);
		pnMain.add(tfTenNhanVien);
		
		lblGioiTinh = new JLabel("Giới Tính:");
		lblGioiTinh.setForeground(Color.BLACK);
		lblGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGioiTinh.setBounds(1149, 105, 114, 25);
		pnMain.add(lblGioiTinh);
		
		lblNgaySinh = new JLabel("Ngày sinh:");
		lblNgaySinh.setForeground(Color.BLACK);
		lblNgaySinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNgaySinh.setBounds(98, 150, 114, 25);
		pnMain.add(lblNgaySinh);
		
		dateChooserNgaySinh = new JDateChooser();
	    dateChooserNgaySinh.setBounds(222, 150, 175, 25);
	    pnMain.add(dateChooserNgaySinh);
	    dateChooserNgaySinh.setLocale(new Locale("vi", "VN"));
	    dateChooserNgaySinh.setBackground(Color.WHITE);
	    dateChooserNgaySinh.setForeground(Color.BLACK);
	    dateChooserNgaySinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		lblCCCD = new JLabel("CCCD:");
		lblCCCD.setForeground(Color.BLACK);
		lblCCCD.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCCCD.setBounds(630, 150, 114, 25);
		pnMain.add(lblCCCD);
		
		lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setForeground(Color.BLACK);
		lblSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSDT.setBounds(1149, 150, 114, 25);
		pnMain.add(lblSDT);
		
		lblChucVu = new JLabel("Chức vụ:");
		lblChucVu.setForeground(Color.BLACK);
		lblChucVu.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblChucVu.setBounds(630, 200, 114, 25);
		pnMain.add(lblChucVu);
		
		lblCaLamViec = new JLabel("Ca làm việc:");
		lblCaLamViec.setForeground(Color.BLACK);
		lblCaLamViec.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCaLamViec.setBounds(1149, 200, 114, 25);
		pnMain.add(lblCaLamViec);
		
		lblTinhTrang = new JLabel("Tình trạng:");
		lblTinhTrang.setForeground(Color.BLACK);
		lblTinhTrang.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTinhTrang.setBounds(98, 252, 114, 25);
		pnMain.add(lblTinhTrang);
		
		tfCCCD = new JTextField();
		tfCCCD.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfCCCD.setColumns(10);
		tfCCCD.setBounds(754, 150, 175, 25);
		pnMain.add(tfCCCD);
		
		tfSDT = new JTextField();
		tfSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfSDT.setColumns(10);
		tfSDT.setBounds(1273, 150, 175, 25);
		pnMain.add(tfSDT);
		
		JLabel lblMucLuong = new JLabel("Lương:");
	    lblMucLuong.setFont(new Font("Tahoma", Font.BOLD, 15));
	    lblMucLuong.setBounds(98, 200, 114, 25);
	    pnMain.add(lblMucLuong);
	    
	    tfMucLuong = new JTextField();
	    tfMucLuong.setFont(new Font("Tahoma", Font.BOLD, 15));
	    tfMucLuong.setBounds(222, 200, 175, 25);
	    pnMain.add(tfMucLuong);
		
		cbCaLamViec = new JComboBox();
		cbCaLamViec.addItem("Ca 1");
		cbCaLamViec.addItem("Ca 2");
		cbCaLamViec.addItem("Ca 3");
		cbCaLamViec.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbCaLamViec.setBounds(1273, 200, 175, 25);
		pnMain.add(cbCaLamViec);
		
		cbChucVu = new JComboBox();
		cbChucVu.addItem("Nhân viên");
		cbChucVu.addItem("Quản lý");
		cbChucVu.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbChucVu.setBounds(754, 200, 175, 25);
		pnMain.add(cbChucVu);
		
		cbTinhTrang = new JComboBox();
		cbTinhTrang.addItem("Đang làm");
		cbTinhTrang.addItem("Nghỉ việc");
		cbTinhTrang.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbTinhTrang.setBounds(222, 252, 175, 25);
		pnMain.add(cbTinhTrang);
		

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
	    
	   
	    tfMucLuong.setColumns(10);
	    btnThem.addActionListener(this);
	    btnSua.addActionListener(this);
	    btnLamMoi.addActionListener(this);
	    tblQuanLyNhanVien.addMouseListener(this);
	    DocDuLieuDataBaseVaoTable();
	}
	
	public void setCustomTable(JTable tbl) {
		tbl.setFont(fontNormal);
		tbl.getTableHeader().setFont(fontBold);
		tbl.getTableHeader().setForeground(Color.decode("#000000"));
		tbl.getTableHeader().setBackground(Color.decode("#1995AD"));
	}
	
	/*
	 * tạo giao diện scrollpane
	 */
	public JScrollPane setCustomScrollPaneNotScroll(JTable tbl) {
		JScrollPane src = new JScrollPane(tbl, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		src.setOpaque(false);
		src.getViewport().setOpaque(false);
		src.getViewport().setBackground(Color.WHITE);
		return src;
	}
	
	public void setBorderTitle(JPanel pnl, String title) {
		Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#1995AD"), 2),
				title, TitledBorder.LEADING, TitledBorder.TOP, fontBold, Color.black);
		pnl.setBorder(border);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
	    
	    if (source.equals(tblQuanLyNhanVien)) {
	        int selectedRow = tblQuanLyNhanVien.getSelectedRow();

	        // Lấy giá trị của cột từ dòng đã chọn
	        Object maNV = model.getValueAt(selectedRow, 1);
	        Object tenNV = model.getValueAt(selectedRow, 2);
	        Object gioiTinh = model.getValueAt(selectedRow, 3);
	        Object ngaySinh = model.getValueAt(selectedRow, 4); 
	        Object cccd = model.getValueAt(selectedRow, 5);
	        Object sdt = model.getValueAt(selectedRow, 6);
	        Object chucVu = model.getValueAt(selectedRow, 7);
	        Object luong = model.getValueAt(selectedRow, 8);
	        Object caLamViec = model.getValueAt(selectedRow, 9);
	        Object tinhTrang = model.getValueAt(selectedRow, 10);
	       
	        
	        tfMaNhanVien.setText(maNV.toString());
	        tfTenNhanVien.setText(tenNV.toString());
	        cbGioiTinh.setSelectedItem(gioiTinh.toString());
	        tfSDT.setText(sdt.toString());
	        tfCCCD.setText(cccd.toString());
	        tfMucLuong.setText(luong.toString());
	        if (ngaySinh != null && ngaySinh instanceof Date) {
	            Date date = (Date) ngaySinh;
	            dateChooserNgaySinh.setDate(date);

	           
	            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	            String ngaySinhFormatted = dateFormat.format(date);
	            System.out.println("Ngày sinh: " + ngaySinhFormatted);

	         
	        }
	        cbChucVu.setSelectedItem(chucVu.toString());
	        cbCaLamViec.setSelectedItem(caLamViec.toString());
	        cbTinhTrang.setSelectedItem(tinhTrang.toString());
	        btnThem.setEnabled(false);
	    }
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			btnThemNhanVien();
		}
		
		else if (o.equals(btnLamMoi)) {
			btnLamMoi();
		}
		
		else if (o.equals(btnSua)) {
			btnUpdateNhanVien();
		}
	}
	
	public void DocDuLieuDataBaseVaoTable() {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		List<NhanVien> list = daoNhanVien.loadNhanVienFromDatabase();
		model.setRowCount(0);
		for(NhanVien nv : list) {
			int stt = model.getRowCount() + 1;
			TaiKhoan tk = daoTaiKhoan.getTaiKhoanTheoMa(nv.getTaiKhoan().getTaiKhoan());
			model.addRow(new Object[] { stt, nv.getMaNhanVien(), nv.getTenNhanVien(), nv.getGioiTinh(), nv.getNgaySinh(), nv.getcCCD(), nv.getSoDT(),
					nv.getChucVu(), decimalFormat.format(nv.getMucLuong()), nv.getCaLamViec(), nv.getTinhTrang(), tk.getMatKhau()});
		}
	}
	
	public void btnThemNhanVien() {
	    if (regex.regexTen(tfTenNhanVien) && regex.regexSDT(tfSDT) && regex.regexCCCD(tfCCCD)) {
	        String maNV = tfMaNhanVien.getText();
	        String hoTen = tfTenNhanVien.getText();
	        String gioiTinh = cbGioiTinh.getSelectedItem().toString();
	        java.util.Date date = dateChooserNgaySinh.getDate();

	        // Kiểm tra giá trị ngày tháng được chọn
	        if (date == null) {
	            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày sinh!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        // Kiểm tra ngày sinh phải nhỏ hơn ngày hiện tại trừ 18 năm
	        java.util.Date currentDate = new java.util.Date();
	        Calendar cal = Calendar.getInstance();
	        cal.setTime(currentDate);
	        cal.add(Calendar.YEAR, -18);
	        java.util.Date eighteenYearsAgo = cal.getTime();
	        if (date.after(eighteenYearsAgo)) {
	            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ! Nhân viên phải từ 18 tuổi trở lên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
	            return;
	        }

	        // Thêm tài khoản
	        TaiKhoan tk = new TaiKhoan(maNV);
	        String matKhau = maNV;
	        TaiKhoan tk1 = new TaiKhoan();
	        tk1.setTaiKhoan(maNV);
	        tk1.setMatKhau(matKhau);
	        try {
	            boolean taiKhoanResult = new TaiKhoan_Dao().addTaiKhoan(tk1);
	            if (!taiKhoanResult) {
	                JOptionPane.showMessageDialog(this, "Lỗi khi thêm tài khoản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	            return;
	        }

	        // Thêm nhân viên
	        NhanVien nv = new NhanVien();
	        nv.setMaNhanVien(maNV);
	        nv.setTenNhanVien(hoTen);
	        nv.setGioiTinh(gioiTinh);
	        nv.setNgaySinh(new java.sql.Date(date.getTime()));
	        nv.setcCCD(tfCCCD.getText());
	        nv.setSoDT(tfSDT.getText());
	        nv.setChucVu(cbChucVu.getSelectedItem().toString());
	        nv.setMucLuong(Double.parseDouble(tfMucLuong.getText()));
	        nv.setCaLamViec(cbCaLamViec.getSelectedItem().toString());
	        nv.setTinhTrang(cbTinhTrang.getSelectedItem().toString());
	        nv.setTaiKhoan(tk);
	        try {
	            boolean nhanVienResult = new NhanVien_Dao().addNhanVien(nv);
	            if (!nhanVienResult) {
	                JOptionPane.showMessageDialog(this, "Lỗi khi thêm nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
	                return;
	            }
	        } catch (SQLException e1) {
	            e1.printStackTrace();
	            return;
	        }

	        // Cập nhật giao diện
	        int stt = model.getRowCount() + 1;
	        model.addRow(new Object[]{stt, maNV, hoTen, gioiTinh, nv.getNgaySinh(), nv.getcCCD(), nv.getSoDT(), nv.getChucVu(), nv.getMucLuong()+"", nv.getCaLamViec(), nv.getTinhTrang(), matKhau});
	        String mkTK = "\nMật khẩu: " + maNV;
	        JOptionPane.showMessageDialog(this, "Thêm thành công!\nMã tài khoản: " + maNV + mkTK, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	        btnLamMoi();
	    }
	}
	
	public void btnUpdateNhanVien() {
	    int r = tblQuanLyNhanVien.getSelectedRow();

	    if (r >= 0) {
	        try {
	            int cn = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin này?", "Thông báo", JOptionPane.YES_NO_OPTION);
	            if (cn == JOptionPane.YES_OPTION) {
	                String maNV = (String) model.getValueAt(r, 1);
	                NhanVien nv = new NhanVien(maNV);
	                nv.setTenNhanVien(tfTenNhanVien.getText());
	                nv.setGioiTinh(cbGioiTinh.getSelectedItem().toString());
	                java.util.Date date = dateChooserNgaySinh.getDate();
	                Date ngaySinh = new Date(date.getTime());
	                nv.setNgaySinh(ngaySinh);
	                nv.setcCCD(tfCCCD.getText());
	                nv.setSoDT(tfSDT.getText());
	                nv.setChucVu(cbChucVu.getSelectedItem().toString());
	                nv.setMucLuong(Double.parseDouble(tfMucLuong.getText()));
	                nv.setCaLamViec(cbCaLamViec.getSelectedItem().toString());
	                nv.setTinhTrang(cbTinhTrang.getSelectedItem().toString());

	                daoNhanVien.updateNV(nv, maNV);
	                JOptionPane.showMessageDialog(this, "Cập nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                btnLamMoi();
	                
	            }
	        } catch (Exception e2) {
	            e2.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Cập nhật không thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	        }
	    }
	}
	
	public void btnLamMoi() {
		tfMaNhanVien.setText(daoNhanVien.getMaNV());
		tfTenNhanVien.setText("");
		tfCCCD.setText("");
		tfSDT.setText("");
		tfMucLuong.setText("");
		Calendar currentDate = Calendar.getInstance();
		cbGioiTinh.setSelectedItem(0);
		cbCaLamViec.setSelectedItem(0);
		cbTinhTrang.setSelectedItem(0);
		cbChucVu.setSelectedItem(0);
		dateChooserNgaySinh.setDate(currentDate.getTime());
		DocDuLieuDataBaseVaoTable();
		tfMaNhanVien.requestFocus();
		btnThem.setEnabled(true);
	}
	
	

	
}
