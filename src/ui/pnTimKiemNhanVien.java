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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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

import connectDB.ConnectDB;
import dao.NhanVien_Dao;
import dao.Regex;
import dao.TaiKhoan_Dao;
import entity.NhanVien;
import entity.TaiKhoan;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import com.toedter.calendar.JDateChooser;

public class pnTimKiemNhanVien extends JPanel implements ActionListener, MouseListener {
	private JTextField tfMa, tfTen;
	private JButton btnTimKiem, btnLamMoi;
	
	private Image imgBG = new ImageIcon(pnTimKiemNhanVien.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAdd = new ImageIcon(pnTimKiemNhanVien.class.getResource("/image/add1.png"));
	private ImageIcon iconDelete = new ImageIcon(pnTimKiemNhanVien.class.getResource("/image/huy1.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnTimKiemNhanVien.class.getResource("/image/update.png"));
	private ImageIcon iconReload = new ImageIcon(pnTimKiemNhanVien.class.getResource("/image/reload.png"));
	private ImageIcon iconReloadTable = new ImageIcon(pnTimKiemNhanVien.class.getResource("/image/reloadtable.png"));
	private ImageIcon iconFind = new ImageIcon(pnTimKiemNhanVien.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(pnTimKiemNhanVien.class.getResource("/image/Rewind.png"));
	private ImageIcon iconLast = new ImageIcon(pnTimKiemNhanVien.class.getResource("/image/Forward.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	
	private JTable tblNhanVien;
	private DefaultTableModel model;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private JTextField tfSDT;
	private JTextField tfCCCD;
	private JComboBox<String> cbChucVu, cbGioiTinh, cbTinhTrang, cbCaLamViec;
	
	private NhanVien_Dao daoNhanVien;
	private TaiKhoan_Dao daoTaiKhoan;
	private Regex regex;
	/**
	 * Create the panel.
	 */
	public pnTimKiemNhanVien() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		daoNhanVien = new NhanVien_Dao();
		daoTaiKhoan = new TaiKhoan_Dao();
		regex = new Regex();
		setLayout(null);
		JPanel pnMain = new JPanel();
		pnMain.setBounds(0, 0, 1599, 1040);
		add(pnMain);
		pnMain.setLayout(null);
		
		JLabel lblTitle = new JLabel("TÌM KIẾM NHÂN VIÊN");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(615, 20, 276, 36);
		pnMain.add(lblTitle);
		
		btnTimKiem = new btnMyButton(130, 40, "Tìm kiếm", new Dimension(60, 23), iconFind.getImage(), new Dimension(25,20), gra);
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTimKiem.setBounds(220, 315, 137, 36);
		pnMain.add(btnTimKiem);
		btnTimKiem.setToolTipText("Tìm kiếm");
		AbstractAction actionTimKiem = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnTimKiem.doClick(); 
            }
        };
		KeyStroke keyStrokeTimKiem = KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.ALT_DOWN_MASK);
		btnTimKiem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeTimKiem, "shortcut");
		btnTimKiem.getActionMap().put("shortcut", actionTimKiem);
		
		btnLamMoi = new btnMyButton(130, 40, "Làm mới", new Dimension(60, 23), iconReload.getImage(), new Dimension(25,20), gra);
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLamMoi.setBounds(466, 315, 137, 36);
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
		pnlTable.setLayout(null);
		pnlTable.setBounds(98, 380, 1350, 598);
		setBorderTitle(pnlTable, "Danh sách nhân viên");
		pnlTable.setOpaque(false);
		
		String[] cols = { "STT", "Mã nhân viên", "Tên nhân viên", "Giới tính", "Ngày sinh", "CCCD", "Số điện thoại", "Chức vụ", "Lương",
				"Ca làm việc", "Tình trạng", "Mật khẩu" };
		model = new DefaultTableModel(cols, 0);
		tblNhanVien = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		
		// Lấy đối tượng TableColumnModel từ JTable
		TableColumnModel columnModel = tblNhanVien.getColumnModel();

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
		setCustomTable(tblNhanVien);
		tblNhanVien.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblNhanVien);
		scrTable.setBounds(10, 25, 1330, 562);
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
	    
	    JPanel pnlTimKiem = new JPanel();
	    pnlTimKiem.setBounds(98, 111, 1350, 175);
	    pnlTimKiem.setLayout(null);
	    setBorderTitle(pnlTimKiem, "Tìm kiếm theo tiêu chí");
	    pnMain.add(pnlTimKiem);
	    
	    JLabel lbTimTheoMa = new JLabel("Mã nhân viên:");
	    lbTimTheoMa.setBounds(75, 20, 125, 30);
	    pnlTimKiem.add(lbTimTheoMa);
	    lbTimTheoMa.setForeground(Color.BLACK);
	    lbTimTheoMa.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    tfMa = new JTextField();
	    tfMa.setToolTipText("Tìm kiếm theo mã nhân viên");
	    tfMa.setFont(new Font("Tahoma", Font.BOLD, 15));
	    tfMa.setBounds(200, 20, 175, 30);
	    pnlTimKiem.add(tfMa);
	    tfMa.setColumns(10);
	    
	    JLabel lblTimTheoTen = new JLabel("Tên nhân viên:");
	    lblTimTheoTen.setBounds(75, 72, 125, 30);
	    pnlTimKiem.add(lblTimTheoTen);
	    lblTimTheoTen.setForeground(Color.BLACK);
	    lblTimTheoTen.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
		tfTen = new JTextField();
		tfTen.setToolTipText("Tìm kiếm theo tên nhân viên");
		tfTen.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfTen.setBounds(200, 72, 175, 30);
		pnlTimKiem.add(tfTen);
		tfTen.setColumns(10);
		
		JLabel lblCCCD = new JLabel("CCCD:");
		lblCCCD.setBounds(532, 72, 125, 30);
		pnlTimKiem.add(lblCCCD);
		lblCCCD.setForeground(Color.BLACK);
		lblCCCD.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setBounds(532, 20, 125, 30);
		pnlTimKiem.add(lblSDT);
		lblSDT.setForeground(Color.BLACK);
		lblSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel lblCaLamViec = new JLabel("Ca làm việc:");
		lblCaLamViec.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCaLamViec.setBounds(75, 125, 125, 30);
		pnlTimKiem.add(lblCaLamViec);
		
		cbCaLamViec = new JComboBox();
		cbCaLamViec.setToolTipText("Tìm theo ca làm việc");
		cbCaLamViec.addItem("");
		cbCaLamViec.addItem("Ca 1");
		cbCaLamViec.addItem("Ca 2");
		cbCaLamViec.addItem("Ca 3");
		cbCaLamViec.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbCaLamViec.setBounds(200, 125, 175, 30);
		pnlTimKiem.add(cbCaLamViec);
		
		tfSDT = new JTextField();
		tfSDT.setToolTipText("Tìm theo số điện thoại của nhân viên");
		tfSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfSDT.setBounds(667, 20, 175, 30);
		pnlTimKiem.add(tfSDT);
		tfSDT.setColumns(10);
		
		tfCCCD = new JTextField();
		tfCCCD.setToolTipText("Tìm theo căn cước công dân của nhân viên");
		tfCCCD.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfCCCD.setBounds(667, 72, 175, 30);
		pnlTimKiem.add(tfCCCD);
		tfCCCD.setColumns(10);
		
		JLabel lblChucVu = new JLabel("Chức vụ:");
		lblChucVu.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblChucVu.setBounds(532, 125, 125, 30);
		pnlTimKiem.add(lblChucVu);
		
		cbChucVu = new JComboBox();
		cbChucVu.setToolTipText("Tìm theo chức vụ nhân viên");
		cbChucVu.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbChucVu.addItem("");
		cbChucVu.addItem("Quản lý");
		cbChucVu.addItem("Nhân viên");
		cbChucVu.setBounds(667, 125, 175, 30);
		pnlTimKiem.add(cbChucVu);
		
		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGioiTinh.setBounds(977, 20, 85, 30);
		pnlTimKiem.add(lblGioiTinh);
		
		JLabel lblTinhTrang = new JLabel("Tình trạng:");
		lblTinhTrang.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTinhTrang.setBounds(977, 72, 85, 30);
		pnlTimKiem.add(lblTinhTrang);
		
		cbGioiTinh = new JComboBox();
		cbGioiTinh.setToolTipText("Tìm theo giới tính nhân viên");
		cbGioiTinh.addItem("");
		cbGioiTinh.addItem("Nam");
		cbGioiTinh.addItem("Nữ");
		cbGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbGioiTinh.setBounds(1072, 20, 175, 30);
		pnlTimKiem.add(cbGioiTinh);
		
		cbTinhTrang = new JComboBox();
		cbTinhTrang.setToolTipText("Tìm theo tình trạng làm việc");
		cbTinhTrang.addItem("");
		cbTinhTrang.addItem("Đang làm");
		cbTinhTrang.addItem("Nghỉ việc");
		cbTinhTrang.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbTinhTrang.setBounds(1072, 72, 175, 30);
		pnlTimKiem.add(cbTinhTrang);
		
		btnTimKiem.addActionListener(this);
		btnLamMoi.addActionListener(this);
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
		if(o.equals(btnTimKiem)) {
			btnTimKiemNhanVien();
		}
		
		else if (o.equals(btnLamMoi)) {
			btnLamMoi();
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
	
	public void btnTimKiemNhanVien() {
	    String maNV = tfMa.getText().trim();
	    String tenNV = tfTen.getText().trim();
	    String sdt = tfSDT.getText().trim();
	    String cccd = tfCCCD.getText().trim();
	    String chucVu = cbChucVu.getSelectedItem().toString();
	    String caLamViec = cbCaLamViec.getSelectedItem().toString();
	    String tinhTrang = cbTinhTrang.getSelectedItem().toString();
	    String gioiTinh = cbGioiTinh.getSelectedItem().toString();
	    List<NhanVien> list = new ArrayList<>();

	    if (maNV.isEmpty() && tenNV.isEmpty() && sdt.isEmpty() && cccd.isEmpty() && chucVu.isEmpty() && caLamViec.isEmpty() && tinhTrang.isEmpty()
	            && gioiTinh.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập ít nhất một trường thông tin để tìm kiếm!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (!maNV.isEmpty()) {
	        if (!regex.regexTimKiemMaNV(tfMa)) {
	            return;
	        }
	        list = daoNhanVien.getLocMaNhanVien(maNV);
	    } else if (!tenNV.isEmpty()) {
	        if (!regex.regexTimNV(tfTen)) {
	            return;
	        }
	        list = daoNhanVien.getLocTenNhanvien(tenNV);
	    } else if (!sdt.isEmpty()) {
	        if (!regex.regexSDT(tfSDT)) {
	            return;
	        }
	        list = daoNhanVien.getLocSoDienThoai(sdt);
	    } else if (!cccd.isEmpty()) {
	        if (!regex.regexCCCD(tfCCCD)) {
	            return;
	        }
	        list = daoNhanVien.getLocCCCD(cccd);
	    } else if (!chucVu.isEmpty()) {
	        list = daoNhanVien.getLocChucVu(chucVu);
	    } else if (!caLamViec.isEmpty()) {
	        list = daoNhanVien.getLocCaLamViec(caLamViec);
	    } else if (!tinhTrang.isEmpty()) {
	        list = daoNhanVien.getLocTinhTrang(tinhTrang);
	    } else if (!gioiTinh.isEmpty()) {
	        list = daoNhanVien.getLocGioiTinh(gioiTinh);
	    }

	    if (!list.isEmpty()) {
	        model.setRowCount(0);
	        DecimalFormat decimalFormat = new DecimalFormat("#,###");
	        for (NhanVien nv : list) {
	            int stt = model.getRowCount() + 1;
	            TaiKhoan tk = daoTaiKhoan.getTaiKhoanTheoMa(nv.getTaiKhoan().getTaiKhoan());
	            model.addRow(new Object[]{stt, nv.getMaNhanVien(), nv.getTenNhanVien(), nv.getGioiTinh(), nv.getNgaySinh(), nv.getcCCD(), nv.getSoDT(),
	                    nv.getChucVu(), decimalFormat.format(nv.getMucLuong()), nv.getCaLamViec(), nv.getTinhTrang(), tk.getMatKhau()});
	        }
	        JOptionPane.showMessageDialog(this, "Đã tìm thấy " + list.size() + " kết quả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    }
	}
	
	
	
	public void btnLamMoi() {
		tfMa.setText("");
		tfTen.setText("");
		tfCCCD.setText("");
		tfSDT.setText("");
		cbGioiTinh.setSelectedIndex(0);
		cbTinhTrang.setSelectedIndex(0);
		cbChucVu.setSelectedItem("");
		cbCaLamViec.setSelectedItem("");
		DocDuLieuDataBaseVaoTable();
		tfMa.requestFocus();
	}
	
	
}
