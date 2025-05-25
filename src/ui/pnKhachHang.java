package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import connectDB.ConnectDB;
import dao.KhachHang_Dao;
import dao.Regex;
import entity.KhachHang;
import entity.LoaiPhong;
import entity.Phong;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

public class pnKhachHang extends JPanel implements ActionListener, MouseListener{
	
	
	private Image imgBG = new ImageIcon(pnKhachHang.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAdd = new ImageIcon(pnKhachHang.class.getResource("/image/add1.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnKhachHang.class.getResource("/image/update.png"));
	private ImageIcon iconReload = new ImageIcon(pnKhachHang.class.getResource("/image/reload.png"));
	private ImageIcon iconFind = new ImageIcon(pnKhachHang.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(pnKhachHang.class.getResource("/image/Rewind.png"));
	private ImageIcon iconNext1 = new ImageIcon(pnKhachHang.class.getResource("/image/First.png"));
	private ImageIcon iconlast1 = new ImageIcon(pnKhachHang.class.getResource("/image/Last.png"));
	private ImageIcon iconLast = new ImageIcon(pnKhachHang.class.getResource("/image/Forward.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	
	private JTextField txtMaKH, txtTenKH, txtSDT, txtCCCD;
	private JButton btnThem, btnSua, btnLamMoi;
	private JComboBox<String> cmbGioiTinh;
	private JDateChooser dateChooserNgaySinh;
	private JTable tblKhachHang;
	private DefaultTableModel model;

	private KhachHang_Dao daoKhachHang;
	private Date dNow;
	private String sHeaderMaNV;
	@SuppressWarnings("unused")
	private String sHeaderTenNV;
	@SuppressWarnings("unused")
	private Date dNgayHienTai;
	private Regex regex;
	
	/**
	 * Create the panel.
	 */
	public pnKhachHang() {
		this.sHeaderMaNV = sHeaderMaNV;
		this.sHeaderTenNV = sHeaderTenNV;
		this.dNgayHienTai = dNgayHienTai;
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		daoKhachHang = new KhachHang_Dao();
		regex = new Regex();
		setLayout(null);
		JPanel pnMain = new JPanel();
		pnMain.setBounds(0, 0, 1559, 1040);
		add(pnMain);
		pnMain.setLayout(null);
		
		JLabel lblTitle = new JLabel("QUẢN LÝ KHÁCH HÀNG");
		lblTitle.setBackground(Color.WHITE);
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(565, 20, 400, 36);
		pnMain.add(lblTitle);
		
		JLabel lblMaKH = new JLabel("Mã khách hàng:");
		lblMaKH.setForeground(Color.BLACK);
		lblMaKH.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMaKH.setBounds(299, 110, 130, 25);
		pnMain.add(lblMaKH);
		
		txtMaKH = new JTextField(daoKhachHang.getMaKhachHang());
		txtMaKH.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtMaKH.setBounds(425, 110, 175, 30);
		pnMain.add(txtMaKH);
		txtMaKH.setColumns(10);
		
		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setForeground(Color.BLACK);
		lblGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGioiTinh.setBounds(299, 165, 130, 25);
		pnMain.add(lblGioiTinh);
		
		cmbGioiTinh = new JComboBox<String>();
		cmbGioiTinh.addItem("Nam");
		cmbGioiTinh.addItem("Nữ");
		cmbGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		cmbGioiTinh.setToolTipText("Chọn giới tính");
		cmbGioiTinh.setBounds(425, 165, 175, 30);
		pnMain.add(cmbGioiTinh);
		
		JLabel lblTenKH = new JLabel("Tên khách hàng:");
		lblTenKH.setForeground(Color.BLACK);
		lblTenKH.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTenKH.setBounds(860, 110, 130, 25);
		pnMain.add(lblTenKH);
		
		txtTenKH = new JTextField();
		txtTenKH.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtTenKH.setBounds(1015, 110, 175, 30);
		pnMain.add(txtTenKH);
		
		JLabel lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setForeground(Color.BLACK);
		lblSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSDT.setBounds(860, 165, 130, 25);
		pnMain.add(lblSDT);
		
		txtSDT = new JTextField();
		txtSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtSDT.setBounds(1015, 165, 175, 30);
		pnMain.add(txtSDT);
		
		JLabel lblCCCD = new JLabel("CCCD:");
		lblCCCD.setForeground(Color.BLACK);
		lblCCCD.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCCCD.setBounds(299, 220, 130, 25);
		pnMain.add(lblCCCD);
		
		txtCCCD = new JTextField();
		txtCCCD.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtCCCD.setBounds(425, 220, 175, 30);
		pnMain.add(txtCCCD);
		
		btnThem = new btnMyButton(105, 40, "Thêm", new Dimension(50, 23), iconAdd.getImage(), new Dimension(25,25), gra);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnThem.setBounds(400, 300, 137, 36);
		btnThem.setToolTipText("Thêm khách hàng");
		pnMain.add(btnThem);
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
		btnSua.setBounds(700, 300, 137, 36);
		pnMain.add(btnSua);
		btnSua.setToolTipText("Cập nhập thông tin khách hàng");
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
		btnLamMoi.setBounds(1000, 300, 137, 36);
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
		pnlTable.setBounds(98, 380, 1350, 613);
		setBorderTitle(pnlTable, "Danh sách khách hàng");
		pnlTable.setOpaque(false);
		String[] cols = { "STT", "Mã khách hàng", "Tên khách hàng", "Giới tính", "Số điện thoại", "CCCD", "Ngày sinh" };
		model = new DefaultTableModel(cols, 0);
		tblKhachHang = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		
		
		TableColumnModel columnModel = tblKhachHang.getColumnModel();
		
		int sttColumnWidth = 10;
		TableColumn sttColumn = columnModel.getColumn(0);
		sttColumn.setPreferredWidth(sttColumnWidth);

		
		int maKHColumnWidth = 10;
		TableColumn maLoaiDVColumn = columnModel.getColumn(1);
		maLoaiDVColumn.setPreferredWidth(maKHColumnWidth);

		
		int tenKHColumnWidth = 200;
		TableColumn tenLoaiDVColumn = columnModel.getColumn(2);
		tenLoaiDVColumn.setPreferredWidth(tenKHColumnWidth);
		
		
		setCustomTable(tblKhachHang);
		tblKhachHang.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblKhachHang);
		scrTable.setBounds(10, 25, 1330, 577);
		pnlTable.add(scrTable);
		pnMain.add(pnlTable);
		

		
	    
	    
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
	    
	    JLabel lblNgaySinh = new JLabel("Ngày sinh:");
	    lblNgaySinh.setFont(new Font("Tahoma", Font.BOLD, 15));
	    lblNgaySinh.setBounds(860, 220, 105, 25);
	    pnMain.add(lblNgaySinh);
	    
	    dateChooserNgaySinh = new JDateChooser();
	    dateChooserNgaySinh.setLocale(new Locale("vi", "VN"));
	    dateChooserNgaySinh.setFont(new Font("Tahoma", Font.BOLD, 15));
	    dateChooserNgaySinh.setBackground(Color.WHITE);
	    dateChooserNgaySinh.setForeground(Color.BLACK);
	    // Vô hiệu hóa chỉnh sửa trực tiếp trên ô văn bản
	    JTextFieldDateEditor editor = (JTextFieldDateEditor) dateChooserNgaySinh.getDateEditor();
	    editor.setEditable(false);

	    dateChooserNgaySinh.setBounds(1015, 220, 175, 30);
	    pnMain.add(dateChooserNgaySinh);
	    btnLamMoi.addActionListener(this);
	    btnSua.addActionListener(this);
	    btnThem.addActionListener(this);
	    tblKhachHang.addMouseListener(this);
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
		JScrollPane src = new JScrollPane(tbl, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
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
	
	public void DocDuLieuDataBaseVaoTable() {
		List<KhachHang> list = daoKhachHang.loadKhachHangFromDatabase();
		model.setRowCount(0);
		for(KhachHang kh : list) {
			int stt = model.getRowCount() + 1;
			model.addRow(new Object[] { stt, kh.getMaKhachHang(), kh.getHoTen(), kh.getGioiTinh(), kh.getSoDT(), kh.getcCCD(), kh.getNgaySinh()});
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
	    Object source = e.getSource();
	    
	    if (source.equals(tblKhachHang)) {
	        int selectedRow = tblKhachHang.getSelectedRow();

	        // Lấy giá trị của cột từ dòng đã chọn
	        Object maKH = model.getValueAt(selectedRow, 1);
	        Object tenKH = model.getValueAt(selectedRow, 2);
	        Object gioiTinh = model.getValueAt(selectedRow, 3);
	        Object sdt = model.getValueAt(selectedRow, 4);
	        Object cccd = model.getValueAt(selectedRow, 5);
	        Object ngaySinh = model.getValueAt(selectedRow, 6); // Lấy giá trị cột thứ 6

	        // Hiển thị giá trị lấy được lên các thành phần UI tương ứng
	        txtMaKH.setText(maKH.toString());
	        txtTenKH.setText(tenKH.toString());
	        cmbGioiTinh.setSelectedItem(gioiTinh.toString());
	        txtSDT.setText(sdt.toString());
	        txtCCCD.setText(cccd.toString());

	        // Kiểm tra nếu giá trị cột thứ 6 là ngày sinh hợp lệ
	        if (ngaySinh != null && ngaySinh instanceof Date) {
	            Date date = (Date) ngaySinh;
	            dateChooserNgaySinh.setDate(date);

	           
	            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	            String ngaySinhFormatted = dateFormat.format(date);
	            System.out.println("Ngày sinh: " + ngaySinhFormatted);

	         
	        }
	        
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
		    if (txtTenKH.getText().isEmpty() || txtCCCD.getText().isEmpty() || txtSDT.getText().isEmpty()) {
		        JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thông báo", JOptionPane.ERROR_MESSAGE);
		        return;
		    }

		    if (regex.regexTen(txtTenKH) && regex.regexCCCD(txtCCCD) && regex.regexSDT(txtSDT)) {
		        try {
		            String maKH = txtMaKH.getText();
		            String hoTen = txtTenKH.getText();
		            String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
		            String sdt = txtSDT.getText();
		            String cCCD = txtCCCD.getText();

		            int ngaySinh = dateChooserNgaySinh.getDate().getDate();
		            int thangSinh = dateChooserNgaySinh.getDate().getMonth();
		            int namSinh = dateChooserNgaySinh.getDate().getYear();

		            LocalDate ngaySinhLocalDate = LocalDate.of(namSinh + 1900, thangSinh + 1, ngaySinh);
		            LocalDate ngayHienTai = LocalDate.now();
		            int age = Period.between(ngaySinhLocalDate, ngayHienTai).getYears();

		            if (age > 17) {
		                KhachHang kh = new KhachHang(maKH, hoTen, gioiTinh, sdt, cCCD, new Date(namSinh, thangSinh, ngaySinh));
		                daoKhachHang.addKhachHang(kh);
		                int stt = model.getRowCount() + 1;
		                model.addRow(new Object[]{stt, kh.getMaKhachHang(), kh.getHoTen(), kh.getGioiTinh(), kh.getSoDT(), kh.getcCCD(), kh.getNgaySinh()});
		                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
		                lamMoi();
		            } else {
		                JOptionPane.showMessageDialog(this, "Tuổi phải lớn hơn 17!", "Thông báo", JOptionPane.ERROR_MESSAGE);
		            }
		        } catch (Exception e2) {
		            JOptionPane.showMessageDialog(this, "Lỗi trùng mã!");
		        }
		    }
		}
		
		else if (o.equals(btnLamMoi)) {
			lamMoi();
			DocDuLieuDataBaseVaoTable();
		}
		
		else if (o.equals(btnSua)) {
			update();
			
		}
	}
	
	
	public void lamMoi() {
		txtMaKH.setText(daoKhachHang.getMaKhachHang());
		txtTenKH.setText("");
		txtCCCD.setText("");
		txtSDT.setText("");
		dateChooserNgaySinh.setDate(dNgayHienTai);
		txtMaKH.requestFocus();
		btnThem.setEnabled(true);
	}
	
	public void update() {
	    int r = tblKhachHang.getSelectedRow();

	    if (r >= 0) {
	        try {
	            int cn = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin này?", "Thông báo", JOptionPane.YES_NO_OPTION);
	            if (cn == JOptionPane.YES_OPTION) {
	                String maKH = (String) model.getValueAt(r, 1);
	                KhachHang kh = new KhachHang(maKH);
	                kh.setHoTen(txtTenKH.getText());
	                kh.setGioiTinh(cmbGioiTinh.getSelectedItem().toString());
	                kh.setSoDT(txtSDT.getText());
	                kh.setcCCD(txtCCCD.getText());
	                java.util.Date date = dateChooserNgaySinh.getDate();
	                Date ngaySinh = new Date(date.getTime());
	                kh.setNgaySinh(ngaySinh);

	                daoKhachHang.updateKH(kh, maKH);
	                JOptionPane.showMessageDialog(this, "Cập nhập thành công!");
	                lamMoi();
	                DocDuLieuDataBaseVaoTable();
	            }
	        } catch (Exception e2) {
	            e2.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Cập nhật không thành công!");
	        }
	    }
	}
	
	
	
}
