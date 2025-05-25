package ui;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.Panel;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import connectDB.ConnectDB;
import dao.HoaDon_Dao;
import dao.KhachHang_Dao;
import dao.LoaiPhong_Dao;
import dao.NhanVien_Dao;
import dao.PhieuDatPhong_Dao;
import dao.Phong_Dao;
import entity.HoaDon;
import entity.KhachHang;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.PhieuDatPhong;
import entity.Phong;
import entity.TaiKhoan;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class pnNhanPhong extends JPanel implements ActionListener, MouseListener {
	private static final long serialVersionUID = 7935621774047432226L;
	private Image imgBG = new ImageIcon(pnNhanPhong.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconPick = new ImageIcon(pnNhanPhong.class.getResource("/image/iconPick.png"));
	private ImageIcon iconThue = new ImageIcon(pnNhanPhong.class.getResource("/image/iconThue.png"));
	private ImageIcon iconTraPhong = new ImageIcon(pnNhanPhong.class.getResource("/image/iconTT.png"));
	private ImageIcon iconSwap = new ImageIcon(pnNhanPhong.class.getResource("/image/iconDoiPhong.png"));
	private ImageIcon iconSave = new ImageIcon(pnNhanPhong.class.getResource("/image/save.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnNhanPhong.class.getResource("/image/update.png"));
	private ImageIcon iconReload = new ImageIcon(pnNhanPhong.class.getResource("/image/reload.png"));
	private ImageIcon iconFind = new ImageIcon(pnNhanPhong.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(pnNhanPhong.class.getResource("/image/Rewind.png"));
	private ImageIcon iconLast = new ImageIcon(pnNhanPhong.class.getResource("/image/Forward.png"));
	private ImageIcon iconPhong = new ImageIcon(pnNhanPhong.class.getResource("/image/phong.png"));
	private ImageIcon iconPhong1 = new ImageIcon(pnNhanPhong.class.getResource("/image/phong1.png"));
	private ImageIcon iconHome = new ImageIcon(pnNhanPhong.class.getResource("/image/house.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	private DefaultTableModel model;
	private DefaultTableModel model1;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private JTextField tfTenKH;
	private JButton btnNhanPhong, btnLuu, btnLamMoi, btnDoiPhong;
	private JLabel lblTenNV;
	private JTable tblDichVu;
	private JTable tblPhong;
	private JPanel pnlTable;
	private String maPhong;
	private JTextField tfSoPhong, tfTrangThai, tfSDT, tfPhong, tfLoaiPhong, tfGia;
	private Phong_Dao daoPhong;
	private LoaiPhong_Dao daoLoaiPhong;
	private PhieuDatPhong_Dao daoPhieuDatPhong;
	private KhachHang_Dao daoKhachHang;
	private HoaDon_Dao daoHoaDon;
	private NhanVien_Dao daoNhanVien;
	
	
	private NhanVien headerNV = null;

	public void setTenKH(String tenKH) {
	    tfTenKH.setText(tenKH);
	}
	public void setSoDT(String soDT) {
		tfSDT.setText(soDT);
	}
	/**
	 * Create the panel.
	 */
	public pnNhanPhong() {
		
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		daoPhieuDatPhong = new PhieuDatPhong_Dao();
		daoKhachHang = new KhachHang_Dao();
		daoHoaDon = new HoaDon_Dao();
		daoNhanVien = new NhanVien_Dao();
		setLayout(null);
		JPanel pnMain = new JPanel();
		pnMain.setOpaque(false);
		pnMain.setBounds(0, 0, 1559, 1040);
		add(pnMain);
		pnMain.setLayout(null);
		
		JLabel lblTitle = new JLabel("NHẬN PHÒNG");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(517, 20, 294, 36);
		pnMain.add(lblTitle);
	
	    
	    JPanel pnlTTPhong = new JPanel() {
	    	@Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	        }
		};
	    pnlTTPhong.setBounds(31, 180, 615, 849);
	    pnlTTPhong.setOpaque(false);
	    pnMain.add(pnlTTPhong);
	    pnlTTPhong.setLayout(null);
	    setBorderTitle(pnlTTPhong, "Phòng");
		
	    pnlTable = new JPanel();
		pnlTable.setLayout(null);
		pnlTable.setBounds(15, 250, 595, 485);
		pnlTable.setOpaque(false);
		taoDanhSachPhong(pnlTable);
		JScrollPane sbrDanhSachPhong = new JScrollPane(pnlTable);
		sbrDanhSachPhong.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS); // Đặt thanh cuộn dọc luôn hiển thị.
		sbrDanhSachPhong.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER); // Vô hiệu hóa thanh cuộn ngang.
		JScrollBar horizontalScrollBar = sbrDanhSachPhong.getHorizontalScrollBar();
	    horizontalScrollBar.setValue(20); // Điều chỉnh giá trị này để scr dịch sang phải

		sbrDanhSachPhong.setBorder(null);
		sbrDanhSachPhong.setBounds(15, 20, 590, 818);
		sbrDanhSachPhong.revalidate();
		sbrDanhSachPhong.repaint();
		pnlTTPhong.add(sbrDanhSachPhong);
		setBorderTitle(sbrDanhSachPhong, "Danh sách phòng");

	    JPanel pnlDonDatP = new JPanel() {
	    	@Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            // Không vẽ gì trong phương thức paintComponent của panel con
	        }
		};
	    pnlDonDatP.setBounds(685, 92, 844, 937);
	    pnlDonDatP.setOpaque(false);
	    setBorderTitle(pnlDonDatP, "Dịch vụ");
	    pnMain.add(pnlDonDatP);
	    pnlDonDatP.setLayout(null);
	    
	    JPanel pnlDSDonDP = new JPanel();
	    pnlDSDonDP.setLayout(null);
	    setBorderTitle(pnlDSDonDP, "Danh sách đơn đặt phòng");
	    pnlDSDonDP.setBounds(15, 159, 819, 767);
	    pnlDSDonDP.setOpaque(false);
	    String[] cols = { "STT", "Mã đơn", "Khách hàng", "Số điện thoại", "Mã phòng", "Ngày đặt", "Giờ đặt" };
		model = new DefaultTableModel(cols, 0);
		tblPhong = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		TableColumnModel columnModel = tblPhong.getColumnModel();

		int sttColumnWidth = 10;
		TableColumn sttColumn = columnModel.getColumn(0);
		sttColumn.setPreferredWidth(sttColumnWidth);
		
		int maPhieuDP = 30;
		TableColumn maPhieuColumn = columnModel.getColumn(1);
		maPhieuColumn.setPreferredWidth(maPhieuDP);
		
		int tenKhachHang = 170;
		TableColumn tenKhachHangColumn = columnModel.getColumn(2);
		tenKhachHangColumn.setPreferredWidth(tenKhachHang);
		
		int maPhong = 35;
		TableColumn maPhongColumn = columnModel.getColumn(4);
		maPhongColumn.setPreferredWidth(maPhong);
		
		int gioDat = 40;
		TableColumn gioDatColumn = columnModel.getColumn(6);
		gioDatColumn.setPreferredWidth(gioDat);
		
		setCustomTable(tblPhong);
		tblPhong.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblPhong);
		scrTable.setBounds(10, 25, 799, 731);
		pnlDSDonDP.add(scrTable);
		
	    pnlDonDatP.add(pnlDSDonDP);
	    
	    JLabel lblTenKH = new JLabel("Tên khách hàng:");
	    lblTenKH.setBounds(20, 81, 120, 25);
	    pnlDonDatP.add(lblTenKH);
	    lblTenKH.setFont(new Font("Tahoma", Font.BOLD, 13));
	    
	    tfTenKH = new JTextField();
	    tfTenKH.setOpaque(false);
	    tfTenKH.setEnabled(false);
	    tfTenKH.setDisabledTextColor(Color.BLACK);
	    tfTenKH.setFont(new Font("Tahoma", Font.BOLD, 15));
	    tfTenKH.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
	    tfTenKH.setBounds(148, 81, 206, 25);
	    pnlDonDatP.add(tfTenKH);
	    tfTenKH.setColumns(10);
	    
	    btnNhanPhong = new btnMyButton(137, 40, "Nhận Phòng", new Dimension(90, 23), iconHome.getImage(), new Dimension(25,25), gra);
	    btnNhanPhong.setBounds(684, 72, 150, 36);
	    pnlDonDatP.add(btnNhanPhong);
	    btnNhanPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    JLabel lblDonDatP = new JLabel("Nhận phòng");
	    lblDonDatP.setForeground(Color.BLUE);
	    lblDonDatP.setFont(new Font("Tahoma", Font.BOLD, 17));
	    lblDonDatP.setBounds(360, 21, 120, 25);
	    pnlDonDatP.add(lblDonDatP);
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
	    
	    JPanel pnlNhanVien = new JPanel();
	    pnlNhanVien.setBounds(31, 92, 615, 71);
	    pnMain.add(pnlNhanVien);
	    setBorderTitle(pnlNhanVien, "Nhân viên");
	    pnlNhanVien.setLayout(null);
	    
	    JLabel lblStaff = new JLabel(fQuanTriHeThong.getChucVu());
	    lblStaff.setForeground(Color.BLUE);
	    lblStaff.setFont(new Font("Tahoma", Font.BOLD, 17));
	    lblStaff.setBounds(139, 22, 93, 25);
	    pnlNhanVien.add(lblStaff);
	    
	    lblTenNV = new JLabel(fQuanTriHeThong.getTenNV());
        lblTenNV.setForeground(Color.BLUE);
        lblTenNV.setFont(new Font("Tahoma", Font.BOLD, 17));
        lblTenNV.setBounds(285, 22, 226, 25);
        pnlNhanVien.add(lblTenNV);
	    
	    JLabel lblSDT = new JLabel("SDT:");
	    lblSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
	    lblSDT.setBounds(380, 81, 46, 25);
	    pnlDonDatP.add(lblSDT);
	    
	    
	    tfSDT = new JTextField();
	    tfSDT.setOpaque(false);
	    tfSDT.setEnabled(false);
	    tfSDT.setDisabledTextColor(Color.BLACK);
	    tfSDT.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
	    tfSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
	    tfSDT.setBounds(436, 81, 199, 25);
	    pnlDonDatP.add(tfSDT);
	    
	    btnNhanPhong.addActionListener(this);
	    tblPhong.addMouseListener(this);
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
	
	public void setBorderTitle(JScrollPane sbrDanhSachPhong, String title) {
		Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#1995AD"), 2),
				title, TitledBorder.LEADING, TitledBorder.TOP, fontBold, Color.black);
		sbrDanhSachPhong.setBorder(border);
	}
	
	public void taoDanhSachPhong(JPanel dsPhong) {
		daoPhong = new Phong_Dao();
		daoLoaiPhong = new LoaiPhong_Dao();		
		dsPhong.setLayout(new BoxLayout(dsPhong, BoxLayout.Y_AXIS));
		JPanel pnlPhong1;
		Box boxCol = Box.createVerticalBox();
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(585, 485));
		int doDai1 = daoPhong.getRoomList().size(),doDai2 = 0, doDai3 = 0, viTri = 0;
		int chieuCaoDanhSachPhong = 0;
		if(doDai1%4 == 0) {
			chieuCaoDanhSachPhong = (doDai1/4)*135;
		}
		else {
			chieuCaoDanhSachPhong = ((doDai1/4)+1)*135;
		}
		ArrayList<JPanel> pnlPhongListTrong = new ArrayList<>();
		ArrayList<JPanel> pnlPhongListDangSuDung = new ArrayList<>();
		for(; doDai1 > 0; doDai1 -=4) {
			doDai2 = doDai1 / 4;
			if(doDai2 >= 1) {
				doDai3 = 4;
			}
			else {
				doDai3 = doDai1;
			}
			for(int i = 0; i < doDai3; i++) {
				
				pnlPhong1 = new JPanel();	
				tfSoPhong = new JTextField();
				tfSoPhong.setBackground(Color.decode("#1995AD"));
				tfSoPhong.setHorizontalAlignment(JTextField.CENTER);
				tfSoPhong.setFont(new Font("Arial", Font.BOLD, 20));
				tfSoPhong.setOpaque(false);
				tfSoPhong.setBorder(null);
				tfSoPhong.setForeground(Color.WHITE);
				tfSoPhong.setEditable(false);
				tfSoPhong.setText(daoPhong.getRoomList().get(viTri).getMaPhong().toString());

				
				tfTrangThai = new JTextField();
				tfTrangThai.setBackground(Color.decode("#1995AD"));
				tfTrangThai.setHorizontalAlignment(JTextField.CENTER);
				tfTrangThai.setFont(new Font("Arial", Font.BOLD, 15));
				tfTrangThai.setBorder(null);
				tfTrangThai.setOpaque(false);
				tfTrangThai.setForeground(Color.WHITE);
				tfTrangThai.setEditable(false);
				tfTrangThai.setText(daoPhong.getRoomList().get(viTri).getTinhTrangPhong().toString());
			  
				JPanel boxRow = new JPanel((LayoutManager)new FlowLayout(FlowLayout.LEFT));
				boxRow.setPreferredSize(new Dimension(130, 130));
				pnlPhong1.setPreferredSize(new Dimension(130, 130));
				JLabel lblPhong = new JLabel(iconPhong1);
				lblPhong.setMaximumSize(new Dimension(50, 50));
				lblPhong.setBounds(40, 5, 50, 50);
				pnlPhong1.add(lblPhong);
				tfSoPhong.setBounds(10, 55, 110, 40);
				pnlPhong1.add(tfSoPhong);
				tfTrangThai.setBounds(10, 90, 110, 30);
				pnlPhong1.add(tfTrangThai);
				pnlPhong1.setLayout(null);
				boxRow.add(pnlPhong1);
				boxRow.add(Box.createHorizontalStrut(50));
				panel.add(boxRow);
			    if (daoPhong.getRoomList().get(viTri).getTinhTrangPhong().equals("Đang sử dụng")) {
			        pnlPhong1.setBackground(Color.decode("#00FF00")); 
			        pnlPhongListDangSuDung.add(pnlPhong1);
			    } else {
			        pnlPhong1.setBackground(Color.decode("#1995AD"));
			        pnlPhongListTrong.add(pnlPhong1);
			    }
			    
			    
			    
				final JPanel finalPnlPhong1 = pnlPhong1;
				final int currentViTri = viTri;
				viTri++;
			}

			boxCol.setPreferredSize(new Dimension(585, chieuCaoDanhSachPhong));
			boxCol.add(panel);
			dsPhong.add(boxCol);
		}
	}
	
	public void DocDuLieuDataBaseVaoTable() {
		List<PhieuDatPhong> list = daoPhieuDatPhong.loadDSPhieuDatPhongFromDatabase();
		model.setRowCount(0);
		for(PhieuDatPhong pdp : list) {
			int stt = model.getRowCount() + 1;
			KhachHang khachHang = daoKhachHang.getMaKhachHangTheoMa(pdp.getKhachhang().getMaKhachHang());
			model.addRow(new Object[] { stt, pdp.getMaPhieuDatPhong(), khachHang.getHoTen(), khachHang.getSoDT(), pdp.getPhong().getMaPhong(), pdp.getNgayDatPhong(), pdp.getGioDatPhong()});
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(tblPhong)) {
			int row = tblPhong.getSelectedRow();
			tfTenKH.setText(model.getValueAt(row, 2).toString());
			tfSDT.setText(model.getValueAt(row, 3).toString());
			
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
	    Object o = e.getSource();
	    if (o.equals(btnNhanPhong)) {
	        btnNhanPhong();
	    }
	}
	
	public void btnNhanPhong() {
	    int row = tblPhong.getSelectedRow();
	    if (row != -1) {
	        String maPhong = model.getValueAt(row, 4).toString();
	        Phong p = daoPhong.getPhongTheoMa(maPhong);

	        if (p.getTinhTrangPhong().equals("Đang sử dụng")) {
	            JOptionPane.showMessageDialog(this, "Phòng đang sử dụng, không thể nhận!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	        } else {
	            int ask = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn nhận phòng này không?", "Cảnh Báo!", JOptionPane.YES_NO_OPTION);
	            if (ask == JOptionPane.YES_OPTION) {
	                LocalDate ngayDat = LocalDate.parse(model.getValueAt(row, 5).toString()); // Lấy giá trị ngày đặt từ bảng
	                LocalDate ngayHienTai = LocalDate.now();

	                if (ngayHienTai.isEqual(ngayDat)) { // Kiểm tra ngày hiện tại bằng ngày đặt
	                    HoaDon hd = new HoaDon(daoHoaDon.getMaHoaDon(), java.sql.Date.valueOf(ngayHienTai), new java.sql.Time(System.currentTimeMillis()), false, new NhanVien(daoNhanVien.getNVtheoMa(fQuanTriHeThong.getMaNV()).getMaNhanVien()), new Phong(maPhong), new KhachHang(daoKhachHang.getMaKhachHangTheoSDT(tfSDT.getText())));
	                    daoHoaDon.addHoaDonKCoNVVaGR(hd);

	                    p.setTinhTrangPhong("Đang sử dụng");
	                    daoPhong.updatePhong(p);
	                    daoPhieuDatPhong.deletePhieuDatPhong(model.getValueAt(row, 1).toString());
	                    loadDanhSachPhong();
	                    DocDuLieuDataBaseVaoTable();

	                    JOptionPane.showMessageDialog(this, "Đã nhận phòng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                } else {
	                    JOptionPane.showMessageDialog(this, "Không thể nhận phòng! Chưa đến ngày nhận phòng.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
	                }
	            }
	        }
	    }
	}
	
	public void loadDanhSachPhong() {
	    pnlTable.removeAll();
	    taoDanhSachPhong(pnlTable);
	    pnlTable.revalidate();
	    pnlTable.repaint();
	}
	
}
