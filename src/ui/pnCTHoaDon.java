package ui;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;


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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.Date;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import com.toedter.calendar.JDateChooser;

import connectDB.ConnectDB;
import dao.CT_HoaDon_Dao;
import dao.DichVu_Dao;
import dao.HoaDon_Dao;
import dao.KhachHang_Dao;
import dao.NhanVien_Dao;
import dao.Phong_Dao;
import entity.CT_HoaDon;
import entity.DichVu;
import entity.HoaDon;
import entity.KhachHang;
import entity.LoaiPhong;
import entity.NhanVien;
import entity.Phong;

public class pnCTHoaDon extends JPanel implements ActionListener, MouseListener {
	
	private Image imgBG = new ImageIcon(pnCTHoaDon.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAdd = new ImageIcon(pnCTHoaDon.class.getResource("/image/add1.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnCTHoaDon.class.getResource("/image/update.png"));
	private ImageIcon iconReload = new ImageIcon(pnCTHoaDon.class.getResource("/image/reload.png"));
	private ImageIcon iconFind = new ImageIcon(pnCTHoaDon.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(pnCTHoaDon.class.getResource("/image/Rewind.png"));
	private ImageIcon iconLast = new ImageIcon(pnCTHoaDon.class.getResource("/image/Forward.png"));
	private ImageIcon iconPDF = new ImageIcon(pnCTHoaDon.class.getResource("/image/pdf.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	
	private JButton btnTimKiem, btnLamMoi;
	
	private JTable tblHoaDon, tblCTHD;
	private DefaultTableModel model, model1;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private JDateChooser dcTuNgay, dcDenNgay;
	private JComboBox cbmTimTheo;
	
	private HoaDon_Dao daoHoaDon;
	private CT_HoaDon_Dao daoCTHoaDon;
	private Phong_Dao daoPhong;
	private DichVu_Dao daoDichVu;
	private KhachHang_Dao daoKhachHang;
	private NhanVien_Dao daoNhanVien;
	/**
	 * Create the panel.
	 */
	public pnCTHoaDon() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		daoHoaDon = new HoaDon_Dao();
		daoCTHoaDon = new CT_HoaDon_Dao();
		daoDichVu = new DichVu_Dao();
		daoKhachHang = new KhachHang_Dao();
		daoNhanVien = new NhanVien_Dao();
		daoPhong = new Phong_Dao();
		setLayout(null);
		JPanel pnMain = new JPanel();
		pnMain.setBounds(0, 0, 1559, 1040);
		add(pnMain);
		pnMain.setLayout(null);
		
		JLabel lblTitle = new JLabel("QUẢN LÝ CHI TIẾT HÓA ĐƠN");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(543, 20, 376, 36);
		pnMain.add(lblTitle);
		
		JPanel pnlTimKiem = new JPanel();
		pnlTimKiem.setBounds(70, 123, 1402, 114);
		pnlTimKiem.setOpaque(false);
		setBorderTitle(pnlTimKiem, "Tìm kiếm");
		pnMain.add(pnlTimKiem);
		pnlTimKiem.setLayout(null);
		
		btnTimKiem = new btnMyButton(130, 40, "Tìm kiếm", new Dimension(60, 23), iconFind.getImage(), new Dimension(25,25), gra);
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTimKiem.setBounds(1064, 28, 130, 40);
		pnlTimKiem.add(btnTimKiem);
		
//		JLabel lblTimTheo = new JLabel("Tình trạng:");
//		lblTimTheo.setBounds(179, 66, 95, 25);
//		pnlTimKiem.add(lblTimTheo);
//		lblTimTheo.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel lblTuNgay = new JLabel("Từ ngày:");
		lblTuNgay.setBounds(179, 28, 95, 25);
		pnlTimKiem.add(lblTuNgay);
		lblTuNgay.setFont(new Font("Tahoma", Font.BOLD, 15));
		
//		cbmTimTheo = new JComboBox();
//		cbmTimTheo.setFont(new Font("Tahoma", Font.BOLD, 15));
//		cbmTimTheo.addItem("Chưa thanh toán");
//		cbmTimTheo.addItem("Đã thanh toán");
//		cbmTimTheo.setBounds(284, 68, 190, 25);
//		pnlTimKiem.add(cbmTimTheo);
		
		JLabel lblDenNgay = new JLabel("Đến ngày:");
		lblDenNgay.setBounds(725, 28, 76, 25);
		pnlTimKiem.add(lblDenNgay);
		lblDenNgay.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		dcTuNgay = new JDateChooser();
		dcTuNgay.setLocale(new Locale("vi", "VN"));
		dcTuNgay.setToolTipText("Tìm theo từ ngày");
		dcTuNgay.setFont(new Font("Tahoma", Font.BOLD, 15));
		dcTuNgay.setBounds(284, 28, 190, 25);
		pnlTimKiem.add(dcTuNgay);
		
		dcDenNgay = new JDateChooser();
		dcDenNgay.setLocale(new Locale("vi", "VN"));
		dcDenNgay.setToolTipText("Tìm theo đến ngày");
		dcDenNgay.setFont(new Font("Tahoma", Font.BOLD, 15));
		dcDenNgay.setBounds(811, 28, 190, 25);
		pnlTimKiem.add(dcDenNgay);
		
		btnLamMoi = new btnMyButton(130, 40, "Làm mới", new Dimension(60, 23), iconReload.getImage(), new Dimension(25,25), gra);
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLamMoi.setBounds(1220, 28, 130, 40);
		pnlTimKiem.add(btnLamMoi);
		
		JPanel pnlHoaDon = new JPanel();
		pnlHoaDon.setLayout(null);
		pnlHoaDon.setBounds(70, 280, 1402, 340);
		setBorderTitle(pnlHoaDon, "Danh sách hóa đơn");
		pnlHoaDon.setOpaque(false);
		pnMain.add(pnlHoaDon);
		
		String[] cols = {"Mã hóa đơn", "Ngày lập hóa đơn", "Giờ vào", "Giờ ra", "Tình trạng" , "Tổng tiền phòng", "Nhân viên", "Phòng", "Khách hàng" };
		model = new DefaultTableModel(cols, 0);
		tblHoaDon = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		
		setCustomTable(tblHoaDon);
		tblHoaDon.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblHoaDon);
		scrTable.setBounds(10, 19, 1382, 300);
		pnlHoaDon.add(scrTable);
		
		JPanel pnlCTHD = new JPanel();
		pnlCTHD.setLayout(null);
		pnlCTHD.setBounds(70, 618, 1402, 323);
		pnlCTHD.setOpaque(false);
		pnlCTHD.setFont(new Font("Tahoma", Font.BOLD, 15));
	
		

		String[] cols1 = { "STT", "Dịch vụ", "Số lượng đặt", "Giá tiền", "Thành tiền", "Đơn vị tính" };
		model1 = new DefaultTableModel(cols1, 0);
		tblCTHD = new JTable(model1) {
			  @Override
			    public boolean isCellEditable(int row, int column) {
			        return false;
			    }
		};
		JScrollPane scrTable1 = new JScrollPane(tblCTHD, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrTable1.setBounds(0, 0, 1402, 323);
		scrTable1.setOpaque(false);
		scrTable1.getViewport().setOpaque(false);
		scrTable1.getViewport().setBackground(Color.WHITE);
		JScrollBar verticalScrollBar = scrTable1.getVerticalScrollBar();
		verticalScrollBar.setPreferredSize(new Dimension(20, 0));

		JTableHeader tableHeader = tblCTHD.getTableHeader();
		tableHeader.setPreferredSize(new Dimension(0, 30));

		tblCTHD.setRowHeight(30);


		tableHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
		tableHeader.setForeground(Color.decode("#000000"));
		tableHeader.setBackground(Color.decode("#1995AD"));

	
		tblCTHD.setBackground(Color.white);
		tblCTHD.setFont(new Font("Tahoma", Font.PLAIN, 15));


		tblCTHD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		tblCTHD.getColumnModel().getColumn(0).setPreferredWidth(50);
		tblCTHD.getColumnModel().getColumn(1).setPreferredWidth(150);
		tblCTHD.getColumnModel().getColumn(2).setPreferredWidth(50);
		tblCTHD.getColumnModel().getColumn(3).setPreferredWidth(50);
		tblCTHD.getColumnModel().getColumn(4).setPreferredWidth(80);

		
		pnlCTHD.add(scrTable1);
		setBorderTitle(scrTable1, "Danh sách chi tiết hóa đơn");
		pnMain.add(pnlCTHD);
		
		
		JPanel pnlTitle = new JPanel(){
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawImage(imgBG, 0, 0, getWidth(), getHeight(), this);
		    }
		};
		pnlTitle.setLayout(null);
		setBorderTitle(pnlTitle, "");
		pnlTitle.setOpaque(false);
	    pnlTitle.setBounds(0, 0, 1554, 81);
	    pnMain.add(pnlTitle);
	    tblHoaDon.addMouseListener(this);
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
	
	public void DocDuLieuDataBaseVaoTable() {
	    List<HoaDon> list = daoHoaDon.getAllHoaDon();
	    model.setRowCount(0);
	    for (HoaDon hd : list) {
	        Phong p = daoPhong.getPhongTheoMa(hd.getPhong().getMaPhong());
	        NhanVien nv = daoNhanVien.getNVtheoMa(hd.getNhanVien().getMaNhanVien());
	        KhachHang kh = daoKhachHang.getMaKhachHangTheoMa(hd.getKhachHang().getMaKhachHang());

	        String paymentStatus = hd.isTinhTrangHD() ? "Đã thanh toán" : "Chưa thanh toán";

	        model.addRow(new Object[] { hd.getMaHoaDon(), hd.getNgayLapHD(), hd.getGioVao(), hd.getGioRa(), paymentStatus, hd.getGiaPhong(), nv.getTenNhanVien(), p.getTenPhong(), kh.getHoTen() });
	    }
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
	    
	    if (source.equals(tblHoaDon)) {
	        int selectedRow = tblHoaDon.getSelectedRow();
	        DocDuLieuCTHoaDonDataBaseVaoTable();
	
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
		if(o.equals(btnTimKiem)) {
			btnTimKiemHoaDon();
		} else if (o.equals(btnLamMoi)) {
			btnLamMoi();
		}
	}
	
	public void DocDuLieuCTHoaDonDataBaseVaoTable() {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		List<CT_HoaDon> list = daoCTHoaDon.loadDSCTHoaDonTheoMa(model.getValueAt(tblHoaDon.getSelectedRow(), 0).toString());
		model1.setRowCount(0);
		for(CT_HoaDon ct : list) {
			DichVu dv = daoDichVu.getDichVuTheoMa(ct.getDichVu().getMaDichVu());
			Object [] row = {model1.getRowCount()+1, dv.getTenDichVu(), ct.getSoLuongDat(), Math.round(dv.getGiaBan()),Math.round(dv.getGiaBan()*ct.getSoLuongDat()), dv.getDonViTinh()};
			model1.addRow(row);
		}
	}
	
	public void btnTimKiemHoaDon() {
	    java.util.Date tuNgayUtil = dcTuNgay.getDate();
	    java.util.Date denNgayUtil = dcDenNgay.getDate();
	    java.util.Date ngayHienTai = new java.util.Date();
	    if (tuNgayUtil == null || denNgayUtil == null) {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	        return;
	    }
	    java.sql.Date tuNgay = new java.sql.Date(tuNgayUtil.getTime());
	    java.sql.Date denNgay = new java.sql.Date(denNgayUtil.getTime());
	    if (tuNgay.after(ngayHienTai) || denNgay.after(ngayHienTai)) {
	        JOptionPane.showMessageDialog(this, "Ngày không hợp lệ!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (tuNgay.after(denNgay)) {
	        JOptionPane.showMessageDialog(this, "Ngày không hợp lệ! Ngày kết thúc phải lớn hơn hoặc bằng ngày bắt đầu.", "Thông báo", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    List<HoaDon> hoaDonList = daoHoaDon.getLocNgayLapHD(tuNgay, denNgay);

	    DefaultTableModel model = (DefaultTableModel) tblHoaDon.getModel();
	    model.setRowCount(0);

	    if (!hoaDonList.isEmpty()) {
	        for (HoaDon hd : hoaDonList) {
	            Phong p = daoPhong.getPhongTheoMa(hd.getPhong().getMaPhong());
	            NhanVien nv = daoNhanVien.getNVtheoMa(hd.getNhanVien().getMaNhanVien());
	            KhachHang kh = daoKhachHang.getMaKhachHangTheoMa(hd.getKhachHang().getMaKhachHang());
	            String paymentStatus = hd.isTinhTrangHD() ? "Đã thanh toán" : "Chưa thanh toán";
	            model.addRow(new Object[] { hd.getMaHoaDon(), hd.getNgayLapHD(), hd.getGioVao(), hd.getGioRa(), paymentStatus, hd.getGiaPhong(), nv.getTenNhanVien(), p.getTenPhong(), kh.getHoTen() });
	            dcTuNgay.setDate(null);
	            dcDenNgay.setDate(null);
	        }

	        JOptionPane.showMessageDialog(this, "Đã tìm thấy " + hoaDonList.size() + " kết quả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    }
	}
	
	public void btnLamMoi() {
	    dcTuNgay.setDate(null);
	    dcDenNgay.setDate(null);
	    DocDuLieuDataBaseVaoTable();
	}
}
