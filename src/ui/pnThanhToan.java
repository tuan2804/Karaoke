package ui;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.SpinnerNumberModel;

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
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Time;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.codec.PngImage;
import com.toedter.components.JSpinField;

import connectDB.ConnectDB;
import dao.CT_HoaDon_Dao;
import dao.DichVu_Dao;
import dao.HoaDon_Dao;
import dao.KhachHang_Dao;
import dao.LoaiDichVu_Dao;
import dao.Phong_Dao;
import entity.CT_HoaDon;
import entity.DichVu;
import entity.HoaDon;
import entity.KhachHang;
import entity.LoaiDichVu;
import entity.NhanVien;
import entity.Phong;
import entity.TaiKhoan;
import javax.swing.JCheckBox;

public class pnThanhToan extends JPanel implements ActionListener, MouseListener {
	private Image imgBG = new ImageIcon(pnThanhToan.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAddDV = new ImageIcon(pnThanhToan.class.getResource("/image/addDV.png"));
	private ImageIcon iconSuaDV = new ImageIcon(pnThanhToan.class.getResource("/image/suaDV.png"));
	private ImageIcon iconHuyDV = new ImageIcon(pnThanhToan.class.getResource("/image/huy1.png"));
	private ImageIcon iconThanhToan = new ImageIcon(pnThanhToan.class.getResource("/image/iconTT.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	private DefaultTableModel model;
	private DefaultTableModel model1;
	private ListSelectionModel modelSelect;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private JTextField tfTenKH, tfSDT, tfTienNhan, tfTienThoi, tfThanhTien ;
	private JButton btnLuu, btnLamMoi, btnThemDV, btnXacNhanTraPhong, btnThanhToan, btnCapNhap, btnXoa;
	private JTable tblDanhSachHD, tblThongTinHD;
	private JTextField txtSoPhong,txtTrangThai;
	private JComboBox cbGioRa, cbPhutRa, cbLDV, cbTDV;
	private JSpinner spnSoLuong;
	private HoaDon_Dao daoHoaDon;
	private KhachHang_Dao daoKhachHang;
	private Phong_Dao daoPhong;
	private LoaiDichVu_Dao daoLoaiDV;
	private DichVu_Dao daoDichVu;
	private CT_HoaDon_Dao daoCTHoaDon;
	private ArrayList<LoaiDichVu> loaiDV;
	
	/**
	 * Create the panel.
	 */
	public pnThanhToan() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		daoKhachHang = new KhachHang_Dao();
		daoHoaDon = new HoaDon_Dao();
		daoPhong = new Phong_Dao();
		daoLoaiDV = new LoaiDichVu_Dao();
		daoDichVu = new DichVu_Dao();
		daoCTHoaDon = new CT_HoaDon_Dao();
		setLayout(null);
		JPanel pnMain = new JPanel();
		pnMain.setBounds(0, 0, 1559, 1040);
		add(pnMain);
		pnMain.setLayout(null);
	
	    JPanel pnlNhanVien = new JPanel();
	    pnlNhanVien.setBounds(31, 92, 678, 71);
	    pnMain.add(pnlNhanVien);
	    setBorderTitle(pnlNhanVien, "Nhân viên");
	    pnlNhanVien.setLayout(null);
	    
	    JLabel lblStaff = new JLabel(fQuanTriHeThong.getChucVu());
	    lblStaff.setForeground(Color.BLUE);
	    lblStaff.setFont(new Font("Tahoma", Font.BOLD, 17));
	    lblStaff.setBounds(156, 25, 93, 25);
	    pnlNhanVien.add(lblStaff);
	    
	    JLabel lblNewLabel = new JLabel(fQuanTriHeThong.getTenNV());
	    lblNewLabel.setForeground(Color.BLUE);
	    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
	    lblNewLabel.setBounds(331, 25, 226, 25);
	    pnlNhanVien.add(lblNewLabel);
		
	    JPanel pnlThongTinHoaDon = new JPanel();
		pnlThongTinHoaDon.setBounds(752, 92, 761, 596);
		pnlThongTinHoaDon.setOpaque(false);
	    setBorderTitle(pnlThongTinHoaDon, "Chi Tiết Hóa Đơn");
	    pnMain.add(pnlThongTinHoaDon);
	    pnlThongTinHoaDon.setLayout(null);
	    pnlThongTinHoaDon.setFont(new Font("Tahoma", Font.BOLD, 17));
	    
	    JLabel lblTenKH = new JLabel("Tên khách hàng:");
	    lblTenKH.setBounds(20, 30, 120, 25);
	    pnlThongTinHoaDon.add(lblTenKH);
	    lblTenKH.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    tfTenKH = new JTextField();
	    tfTenKH.setOpaque(false);
	    tfTenKH.setEnabled(false);
	    tfTenKH.setDisabledTextColor(Color.BLACK);
	    tfTenKH.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
	    tfTenKH.setFont(new Font("Tahoma", Font.BOLD, 15));
	    tfTenKH.setBounds(150, 30, 207, 25);
	    pnlThongTinHoaDon.add(tfTenKH);
	    tfTenKH.setColumns(10);
	    
	    JLabel lblSDT = new JLabel("SĐT:");
	    lblSDT.setBounds(450, 30, 36, 25);
	    pnlThongTinHoaDon.add(lblSDT);
	    lblSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    tfSDT = new JTextField();
	    tfSDT.setOpaque(false);
	    tfSDT.setEnabled(false);
	    tfSDT.setDisabledTextColor(Color.BLACK);
	    tfSDT.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
	    tfSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
	    tfSDT.setBounds(496, 30, 207, 25);
	    pnlThongTinHoaDon.add(tfSDT);
	    tfSDT.setColumns(10);
	    
	    JPanel pnlDSThongTinHD = new JPanel();
	    pnlDSThongTinHD.setLayout(null);
	    setBorderTitle(pnlDSThongTinHD, "Danh sách chi tiết hoá đơn");
	    pnlDSThongTinHD.setBounds(10, 66, 741, 519);
	    pnlDSThongTinHD.setOpaque(false);
	    String[] cols = { "STT", "Dịch vụ", "Số lượng", "Đơn giá", "Thành tiền(VNĐ)", "Đơn vị tính" };
		model = new DefaultTableModel(cols, 0);
		
		tblThongTinHD = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		setCustomTable(tblThongTinHD);
		tblThongTinHD.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblThongTinHD);
		scrTable.setBounds(10, 23, 720, 485);
		pnlDSThongTinHD.add(scrTable);
		pnlThongTinHoaDon.add(pnlDSThongTinHD);
		
		
		JPanel pnlDanhSachHD = new JPanel();
		pnlDanhSachHD.setLayout(null);
		pnlDanhSachHD.setBounds(31, 174, 678, 514);
		pnlDanhSachHD.setOpaque(false);
		pnlDanhSachHD.setFont(new Font("Tahoma", Font.BOLD, 15));

		String[] cols1 = { "Hóa đơn	", "Tên Khách Hàng", "Tên Phòng", "Giờ Vào", "Tình Trạng" };
		model1 = new DefaultTableModel(cols1, 0);

		tblDanhSachHD = new JTable(model1) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		        return false;
		    }
		};
		JScrollPane scrTable1 = new JScrollPane(tblDanhSachHD, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrTable1.setBounds(0, 0, 678, 514);
		scrTable1.setOpaque(false);// Đặt màu nền cho phần viewport của JScrollPane
		scrTable1.getViewport().setOpaque(false);
		scrTable1.getViewport().setBackground(Color.WHITE);

		JScrollBar verticalScrollBar = scrTable1.getVerticalScrollBar();
		verticalScrollBar.setPreferredSize(new Dimension(20, 0)); // Đặt kích thước cho thanh cuộn dọc

		JTableHeader tableHeader = tblDanhSachHD.getTableHeader();
		tableHeader.setPreferredSize(new Dimension(0, 30)); // Đặt chiều cao cho phần header của bảng

		tblDanhSachHD.setRowHeight(30); // Đặt chiều cao cho các hàng trong bảng

		// Tuỳ chỉnh màu cho header của bảng
		tableHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
		tableHeader.setForeground(Color.decode("#000000"));
		tableHeader.setBackground(Color.decode("#1995AD"));

		// Tuỳ chỉnh màu nền và font chữ cho bảng
		tblDanhSachHD.setBackground(Color.white);
		tblDanhSachHD.setFont(new Font("Tahoma", Font.PLAIN, 15));

		// Cài đặt kiểu chọn một hàng duy nhất trong bảng
		tblDanhSachHD.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Đặt kích thước cột
		tblDanhSachHD.getColumnModel().getColumn(0).setPreferredWidth(50);
		tblDanhSachHD.getColumnModel().getColumn(1).setPreferredWidth(150);
		tblDanhSachHD.getColumnModel().getColumn(2).setPreferredWidth(50);
		tblDanhSachHD.getColumnModel().getColumn(3).setPreferredWidth(50);
		tblDanhSachHD.getColumnModel().getColumn(4).setPreferredWidth(80);

		// Thêm scrTable1 vào pnlDanhSachHD
		pnlDanhSachHD.add(scrTable1);
		setBorderTitle(scrTable1, "Danh sách hóa đơn");
		pnMain.add(pnlDanhSachHD);
		
		
		JPanel pnlChiTietHD = new JPanel() {
	    	@Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            // Không vẽ gì trong phương thức paintComponent của panel con
	        }
		};
		pnlChiTietHD.setBounds(31, 699, 1482, 330);
		pnlChiTietHD.setOpaque(false);
	    setBorderTitle(pnlChiTietHD, "Thông Tin Chi Tiết");
	    pnMain.add(pnlChiTietHD);
	    pnlChiTietHD.setLayout(null);
	    pnlChiTietHD.setFont(new Font("Tahoma", Font.BOLD, 17));
	    
	    JLabel lblSL = new JLabel("Số Lượng:");
	    lblSL.setBounds(30, 180, 145, 30);
	    pnlChiTietHD.add(lblSL);
	    lblSL.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    JPanel pnlDichVu = new JPanel();
	    pnlDichVu.setBounds(10, 19, 410, 298);
	    setBorderTitle(pnlDichVu, "Chọn dịch vụ");
	    pnlChiTietHD.add(pnlDichVu);
	    pnlDichVu.setLayout(null);
	    
	    SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, null, null, 1);
        spnSoLuong = new JSpinner(spinnerModel);
        spnSoLuong.setFont(new Font("Tahoma", Font.BOLD, 15));
        spnSoLuong.setBounds(187, 173, 200, 30);
        pnlDichVu.add(spnSoLuong);
	    
	    cbLDV = new JComboBox();
	    cbLDV.addItem("Loại dịch vụ");
	    cbLDV.setBounds(187, 41, 200, 30);
	    pnlDichVu.add(cbLDV);
	    cbLDV.setFont(new Font("Tahoma", Font.BOLD, 15));
	    cbLDV.addItemListener(new ItemListener() {
	        @Override
	        public void itemStateChanged(ItemEvent e) {
	            if (e.getStateChange() == ItemEvent.SELECTED) {
	                String maLoaiDV = daoLoaiDV.getMaLoaiDVTheoTen(cbLDV.getSelectedItem().toString());
	                ArrayList<DichVu> dsDichVu = daoDichVu.getDichVuTheoLoai(maLoaiDV);

	                // Xóa các item cũ trong cbTDV
	                cbTDV.removeAllItems();

	                // Thêm dịch vụ vào cbTDV
	                for (DichVu dv : dsDichVu) {
	                    cbTDV.addItem(dv.getTenDichVu());
	                }
	            }
	        }
	    });
	    
	    JLabel lblThemDV = new JLabel("Chọn Loại Dịch Vụ:");
	    lblThemDV.setBounds(20, 41, 145, 30);
	    pnlDichVu.add(lblThemDV);
	    lblThemDV.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    JLabel lblTenDV = new JLabel("Tên Dịch Vụ:");
	    lblTenDV.setBounds(20, 101, 145, 30);
	    pnlDichVu.add(lblTenDV);
	    lblTenDV.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    cbTDV = new JComboBox();
	    cbTDV.addItem("Tên dịch vụ");
	    cbTDV.setBounds(187, 101, 200, 30);
	    pnlDichVu.add(cbTDV);
	    cbTDV.setFont(new Font("Tahoma", Font.BOLD, 15));
	    

	    
	    btnThemDV = new btnMyButton(100, 40, "Thêm", new Dimension(45, 23), iconAddDV.getImage(), new Dimension(20,25), gra);
	    btnThemDV.setBounds(20, 234, 100, 40);
	    pnlDichVu.add(btnThemDV);
	    btnThemDV.setFocusPainted(false);
	    btnThemDV.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    btnCapNhap = new btnMyButton(120, 40, "Cập nhập", new Dimension(60, 23), iconSuaDV.getImage(), new Dimension(20,25), gra);
	    btnCapNhap.setBounds(150, 234, 120, 40);
	    pnlDichVu.add(btnCapNhap);
	    
	    btnXoa = new btnMyButton(100, 40, "Xóa", new Dimension(40, 23), iconHuyDV.getImage(), new Dimension(20,25), gra);
	    btnXoa.setBounds(298, 234, 100, 40);
	    pnlDichVu.add(btnXoa);
	    
	    JPanel pnlThanhToan = new JPanel();
	    pnlThanhToan.setBounds(701, 19, 771, 298);
	    setBorderTitle(pnlThanhToan, "Thanh toán");
	    pnlChiTietHD.add(pnlThanhToan);
	    pnlThanhToan.setLayout(null);
	    
	    tfThanhTien = new JTextField();
	    tfThanhTien.setOpaque(false);
	    tfThanhTien.setEnabled(false);
	    tfThanhTien.setDisabledTextColor(Color.BLACK);
	    tfThanhTien.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
	    tfThanhTien.setBounds(259, 52, 200, 30);
	    pnlThanhToan.add(tfThanhTien);
	    tfThanhTien.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    
	    JLabel lblThanhTien = new JLabel("Tổng tiền (VNĐ):");
	    lblThanhTien.setBounds(80, 52, 169, 30);
	    pnlThanhToan.add(lblThanhTien);
	    lblThanhTien.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    JLabel lblTienNhan = new JLabel("Tiền Nhận (VNĐ):");
	    lblTienNhan.setBounds(80, 112, 169, 30);
	    pnlThanhToan.add(lblTienNhan);
	    lblTienNhan.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    tfTienNhan = new JTextField();
	    tfTienNhan.setBounds(259, 112, 200, 30);
	    pnlThanhToan.add(tfTienNhan);
	    tfTienNhan.setFont(new Font("Tahoma", Font.BOLD, 15));
	    tfTienNhan.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				Object obj = e.getSource();
				if (obj.equals(tfTienNhan)) {
					if (tfTienNhan.getText().equals("")) {
						tfTienThoi.setText("");
					}
					try {
						double tienNhan = Double.parseDouble(tfTienNhan.getText());
						double tongTien = Double.parseDouble(tfThanhTien.getText());
						if (tienNhan < tongTien) {
							tfTienThoi.setText("Chưa đủ tiền");
						} else {
							double tienThua = tienNhan - tongTien;
							tfTienThoi.setText(String.valueOf(Math.round(tienThua)));
						}
					} catch (Exception e2) {
					}
				}
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});

	    JLabel lblTienThoi = new JLabel("Tiền Thối (VNĐ):");
	    lblTienThoi.setBounds(80, 171, 152, 30);
	    pnlThanhToan.add(lblTienThoi);
	    lblTienThoi.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    
	    
	    tfTienThoi = new JTextField();
	    tfTienThoi.setBorder(null);
	    tfTienThoi.setOpaque(false);
	    tfTienThoi.setEnabled(false);
	    tfTienThoi.setDisabledTextColor(Color.BLACK);
	    tfTienThoi.setBounds(259, 171, 200, 30);
	    pnlThanhToan.add(tfTienThoi);
	    tfTienThoi.setFont(new Font("Tahoma", Font.BOLD, 15));
	  
	    
	    btnThanhToan = new btnMyButton(200, 40, "Thanh toán", new Dimension(105, 23), iconThanhToan.getImage(), new Dimension(25,25), gra);
	    btnThanhToan.setBounds(508, 51, 200, 40);
	    pnlThanhToan.add(btnThanhToan);
	    btnThanhToan.setFocusPainted(false);
	    btnThanhToan.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    
	    JPanel pnlTraPhong = new JPanel();
	    setBorderTitle(pnlTraPhong, "Trà phòng");
	    pnlTraPhong.setBounds(430, 19, 261, 298);
	    pnlChiTietHD.add(pnlTraPhong);
	    pnlTraPhong.setLayout(null);
	    
	    JLabel lblGioRa = new JLabel("Giờ Ra:");
	    lblGioRa.setBounds(20, 40, 54, 30);
	    pnlTraPhong.add(lblGioRa);
	    lblGioRa.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    cbGioRa = new JComboBox<String>();
		DefaultComboBoxModel<Integer> gioModel = new DefaultComboBoxModel<>();
        for (int i = 0; i <= 23; i++) {
            gioModel.addElement(i);
        }
        cbGioRa.setModel(gioModel);
	    cbGioRa.setBounds(97, 40, 50, 30);
	    pnlTraPhong.add(cbGioRa);
	    cbGioRa.setFont(new Font("Tahoma", Font.BOLD, 15));
	    cbGioRa.setBackground(Color.WHITE);
	    cbGioRa.setForeground(Color.BLACK);
	    
	    
	    JLabel lblNgan1 = new JLabel(":");
	    lblNgan1.setBounds(165, 40, 18, 30);
	    pnlTraPhong.add(lblNgan1);
	    lblNgan1.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    cbPhutRa = new JComboBox();
	    DefaultComboBoxModel<String> phutModel = new DefaultComboBoxModel<>();

        for (int i = 0; i <= 60; i++) {
            String phut = String.format("%02d", i);
            phutModel.addElement(phut);
        }
        
        // Thiết lập mô hình cho JComboBox
        cbPhutRa.setModel(phutModel);
	    cbPhutRa.setBounds(190, 40, 50, 30);
	    pnlTraPhong.add(cbPhutRa);
	    cbPhutRa.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    btnXacNhanTraPhong = new btnMyButton(220, 40, "Xác nhận trả phòng", new Dimension(195, 23), gra);
	    btnXacNhanTraPhong.setBounds(20, 98, 220, 40);
	    pnlTraPhong.add(btnXacNhanTraPhong);
	    btnXacNhanTraPhong.setFocusPainted(false);
	    btnXacNhanTraPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    
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
	    
	    JLabel lblTitle = new JLabel("LẬP HÓA ĐƠN");
	    lblTitle.setBounds(588, 20, 294, 36);
	    pnlTitle.add(lblTitle);
	    lblTitle.setForeground(Color.BLACK);
	    lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
	    lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
	    tblDanhSachHD.addMouseListener(this);
	    
	  
	    for(LoaiDichVu ldv : daoLoaiDV.getAllLoaiDichVu()) {
	    	cbLDV.addItem(ldv.getTenLoaiDV());
	    }
	    
	    for(DichVu dv : daoDichVu.loadDSDichVuFromDatabase()) {
	    	cbTDV.addItem(dv.getTenDichVu());
	    }
	    tblThongTinHD.addMouseListener(this);
	    btnThemDV.addActionListener(this);
	    btnCapNhap.addActionListener(this);
	    btnXoa.addActionListener(this);
	    btnXacNhanTraPhong.addActionListener(this);
	    btnThanhToan.addActionListener(this);
	    DocDuLieuDatabaseVaoTable();
	    
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
	
	
	public void DocDuLieuDatabaseVaoTable() {
		model1.setRowCount(0);
		List<HoaDon> list = daoHoaDon.getAllHoaDon();
				for (HoaDon hd : list) {	
					String tt = "Đã Thanh Toán";
					if(hd.isTinhTrangHD()==false)
						tt="Chưa Thanh Toán";
					if(hd.isTinhTrangHD()==false) {
						KhachHang khachHang = daoKhachHang.getMaKhachHangTheoMa(hd.getKhachHang().getMaKhachHang());
						Phong phong = daoPhong.getPhongTheoMa(hd.getPhong().getMaPhong());
						Object [] row = {hd.getMaHoaDon(), khachHang.getHoTen(), phong.getMaPhong() ,hd.getGioVao(),tt};
						model1.addRow(row);
					}
				}
	}

	public void DocDuLieuCTHoaDonDataBaseVaoTable() {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		List<CT_HoaDon> list = daoCTHoaDon.loadDSCTHoaDonTheoMa(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString());
		model.setRowCount(0);
		for(CT_HoaDon ct : list) {
			DichVu dv = daoDichVu.getDichVuTheoMa(ct.getDichVu().getMaDichVu());
			Object [] row = {model.getRowCount()+1, dv.getTenDichVu(), ct.getSoLuongDat(), Math.round(dv.getGiaBan()),Math.round(dv.getGiaBan()*ct.getSoLuongDat()), dv.getDonViTinh()};
			model.addRow(row);
		}
	}
	
	public void btnThemDichVu() {
		if(tblDanhSachHD.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		} else if (cbTDV.getSelectedItem().toString().equals("Dịch vụ")) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		} else if(Integer.parseInt(spnSoLuong.getValue().toString())<=0) {
			JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    	} else if(Integer.parseInt(spnSoLuong.getValue().toString()) > daoDichVu.getSLTheoTen(cbTDV.getSelectedItem().toString())) {
			JOptionPane.showMessageDialog(this, "Số lượng trong kho không đủ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    	} else {
    		int dem = 0;
        	for(int i = 0; i<model.getRowCount(); i++) {
    	    	if(model.getValueAt(i, 1).toString().equals(cbTDV.getSelectedItem().toString())) {
    	    		dem++;
    	    	}
    		}
        	
        	if (dem == 0) {
				double tongTien = 0;
				CT_HoaDon ct = new CT_HoaDon(new HoaDon(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString()), new DichVu(daoDichVu.getMaTheoTen(cbTDV.getSelectedItem().toString())), 
						Integer.parseInt(spnSoLuong.getValue().toString()), daoDichVu.getGiaTheoTen(cbTDV.getSelectedItem().toString()), daoDichVu.getDVTTheoTen(cbTDV.getSelectedItem().toString()));
				daoCTHoaDon.addCTHoaDon(ct);
				if(!tfThanhTien.getText().equals("")) {
                	tongTien = Double.parseDouble(tfThanhTien.getText());
        		}
				Object [] row = {model.getRowCount()+1,cbTDV.getSelectedItem().toString(),spnSoLuong.getValue(), Math.round(daoDichVu.getGiaTheoTen(cbTDV.getSelectedItem().toString())), 
						Math.round(daoDichVu.getGiaTheoTen(cbTDV.getSelectedItem().toString())*Float.parseFloat(spnSoLuong.getValue().toString())), daoDichVu.getDVTTheoTen(cbTDV.getSelectedItem().toString())};
        		model.addRow(row);
        		JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        		tongTien = tongTien + Math.round(daoDichVu.getGiaTheoTen(cbTDV.getSelectedItem().toString())*Integer.parseInt(spnSoLuong.getValue().toString()));
        		tfThanhTien.setText(String.valueOf(Math.round(tongTien)));
        		cbLDV.setSelectedIndex(0);
        		cbTDV.setSelectedItem(0);
        		spnSoLuong.setValue(0);
			}
        	
    	}
	}
	
	public void btnCapNhapDichVu() {
		if(tblThongTinHD.getSelectedRow()==-1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần cập nhật", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		} else if(model.getValueAt(tblThongTinHD.getSelectedRow(), 4).toString().equals("Giờ")) {
			JOptionPane.showMessageDialog(this, "Không được cập nhật hóa đơn phòng", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    	} else if(!cbTDV.getSelectedItem().toString().equals(model.getValueAt(tblThongTinHD.getSelectedRow(), 1).toString())) {
			JOptionPane.showMessageDialog(this, "Không được cập nhật tên dịch vụ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			cbTDV.setSelectedItem(model.getValueAt(tblThongTinHD.getSelectedRow(), 1).toString());
    	} else if(Integer.parseInt(spnSoLuong.getValue().toString())<=0) {
			JOptionPane.showMessageDialog(this, "Số lượng phải lớn hơn 0", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    	} else if(Integer.parseInt(spnSoLuong.getValue().toString()) > daoDichVu.getSLTheoTen(cbTDV.getSelectedItem().toString())) {
			JOptionPane.showMessageDialog(this, "Số lượng trong kho không đủ", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    	} else {
			double tongTien = 0;
			CT_HoaDon ct = new CT_HoaDon(new HoaDon(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString()), new DichVu(daoDichVu.getMaTheoTen(cbTDV.getSelectedItem().toString())), 
					Integer.parseInt(spnSoLuong.getValue().toString()), daoDichVu.getGiaTheoTen(cbTDV.getSelectedItem().toString()), daoDichVu.getDVTTheoTen(cbTDV.getSelectedItem().toString()));
			boolean capNhatThanhCong = false;
			daoCTHoaDon.updateCTHoaDon(ct);
			capNhatThanhCong = true;

			if (capNhatThanhCong) {
			    JOptionPane.showMessageDialog(null, "Cập nhật dịch vụ thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			} else {
			    JOptionPane.showMessageDialog(null, "Cập nhật dịch vụ thất bại", "Thông báo", JOptionPane.ERROR_MESSAGE);
			}

			// Cập nhật thành công: cập nhật dữ liệu khác và làm các thao tác khác
			if (capNhatThanhCong) {
			    Object[] row = {model.getRowCount()+1, cbTDV.getSelectedItem().toString(), spnSoLuong.getValue(),
			                    Math.round(daoDichVu.getGiaTheoTen(cbTDV.getSelectedItem().toString())),
			                    Math.round(daoDichVu.getGiaTheoTen(cbTDV.getSelectedItem().toString()) * Float.parseFloat(spnSoLuong.getValue().toString())), 
			                    		daoDichVu.getDVTTheoTen(cbTDV.getSelectedItem().toString())};

			    for (int i = 0; i < model.getRowCount(); i++) {
			        tongTien = tongTien + Double.parseDouble(model.getValueAt(i, 4).toString());
			    }

			    tfThanhTien.setText(String.valueOf(Math.round(tongTien)));
			    DocDuLieuCTHoaDonDataBaseVaoTable();
			    cbLDV.setSelectedIndex(0);
			    spnSoLuong.setValue(0);
			}
		}
	}
	
	public void btnXoaDichVu() {
		if(tblThongTinHD.getSelectedRow() == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn dịch vụ cần xóa", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		} else if (model.getValueAt(tblThongTinHD.getSelectedRow(), 4).toString().equals("Giờ")) {
			JOptionPane.showMessageDialog(this, "Không được xóa phòng ra khỏi hóa đơn", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		} else {
			int hoi = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa dịch vụ này khỏi hóa đơn không?", "Thông báo", JOptionPane.YES_NO_OPTION);
			if(hoi == JOptionPane.YES_OPTION) {
				double tongTien = 0;
				daoCTHoaDon.deleteCTHD(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString(), 
						daoDichVu.getMaTheoTen(model.getValueAt(tblThongTinHD.getSelectedRow(), 1).toString()));
				for(int i = tblThongTinHD.getSelectedRow(); i < model.getRowCount(); i++) {
					model.setValueAt(Integer.parseInt(model.getValueAt(i, 0).toString()) - 1, i, 0);
				}
				model.removeRow(tblThongTinHD.getSelectedRow());
				for(int i = 0; i < model.getRowCount(); i++ ) {
					tongTien = tongTien + Double.parseDouble(model.getValueAt(i, 4).toString());
				}
				tfThanhTien.setText(String.valueOf(Math.round(tongTien)));
			    DocDuLieuCTHoaDonDataBaseVaoTable();
			    cbLDV.setSelectedIndex(0);
			    spnSoLuong.setValue(0);
			    JOptionPane.showMessageDialog(this, "Xóa dịch vụ thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}
	
	public void btnTraPhong() {
		int gioVao = Integer.parseInt(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(0, 2));
	    int gioRa = Integer.parseInt(cbGioRa.getSelectedItem().toString());
		int dem = 0;
		if (gioRa <= gioVao) {
	        JOptionPane.showMessageDialog(this, "Giờ ra phải lớn hơn giờ vào", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }
		for(int i = 0; i < model.getRowCount(); i++) {
	    	if(model.getValueAt(i, 5).toString().equals("Giờ")) {
	    		dem++;
	    	}
		}
		
		if(dem == 0) {
			if(tblDanhSachHD.getSelectedRow()==-1) {
    			JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn muốn trả", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    			return;
    		} else {
    			float soPhut = 0;
    			if(Float.parseFloat(cbGioRa.getSelectedItem().toString()) >= Float.parseFloat(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(0,2))) {
    				if(Float.parseFloat(cbGioRa.getSelectedItem().toString()) > Float.parseFloat(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(0,2))) {
        				soPhut = (Float.parseFloat(cbGioRa.getSelectedItem().toString())-Float.parseFloat(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(0,2)))*60 
        						+ (Float.parseFloat(cbPhutRa.getSelectedItem().toString())-Float.parseFloat(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(3,5)));
    				}
    				else if(Float.parseFloat(cbGioRa.getSelectedItem().toString()) == Float.parseFloat(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(0,2)) && 
    						Float.parseFloat(cbPhutRa.getSelectedItem().toString()) >= Float.parseFloat(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(3,5))) {
        				soPhut = (Float.parseFloat(cbGioRa.getSelectedItem().toString())-Float.parseFloat(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(0,2)))*60 
        						+ (Float.parseFloat(cbPhutRa.getSelectedItem().toString())-Float.parseFloat(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(3,5)));
    				}
    				else {
        				soPhut = (24-Float.parseFloat(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(0,2)) 
        						+ Float.parseFloat(cbGioRa.getSelectedItem().toString()))*60 + (Float.parseFloat(cbPhutRa.getSelectedItem().toString())
        								- Float.parseFloat(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(3,5)));
    				}
    			}
    			else {
    				soPhut = (24-Float.parseFloat(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(0,2)) 
    						+ Float.parseFloat(cbGioRa.getSelectedItem().toString()))*60 + (Float.parseFloat(cbPhutRa.getSelectedItem().toString())
    								- Float.parseFloat(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 3).toString().substring(3,5)));
    			}
    			
    			float soGio = soPhut/60;

    			Object [] row = {model.getRowCount()+1, model1.getValueAt(tblDanhSachHD.getSelectedRow(), 2).toString(),(float) Math.round(soGio * 100) / 100, 
    					Math.round(daoPhong.getGiaTheoTenP(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 2).toString())), 
    					Math.round(daoPhong.getGiaTheoTenP(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 2).toString())* (float) Math.round(soGio * 100) / 100), "Giờ"};
    			model.addRow(row);
    			if(tfThanhTien.getText().equals("")) {
    				tfThanhTien.setText(String.valueOf(Math.round(daoPhong.getGiaTheoTenP(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 2).toString())* (float) Math.round(soGio * 100) / 100)));
    			}
    			else {
    				tfThanhTien.setText(String.valueOf(Math.round(daoPhong.getGiaTheoTenP(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 2).toString())* (float) Math.round(soGio * 100) / 100)+Math.round(Double.parseDouble(tfThanhTien.getText()))));
    			}
    			daoHoaDon.updateGioRa(new Time(Integer.parseInt(cbGioRa.getSelectedItem().toString()), Integer.parseInt(cbPhutRa.getSelectedItem().toString()), 0), 
    					Math.round(daoPhong.getGiaTheoTenP(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 2).toString())* (float) Math.round(soGio * 100) / 100), 
    					model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString());
    			JOptionPane.showMessageDialog(this, "Trả phòng thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    		}
			
		}
		else {
			JOptionPane.showMessageDialog(this, "Phòng này đã được trả", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	public void btnThanhToan() {
		int dem = 0;
	    for (int i = 0; i < model.getRowCount(); i++) {
	        if (model.getValueAt(i, 5).toString().equals("Giờ")) {
	            dem++;
	        }
	    }
	    if (dem == 0) {
	        JOptionPane.showMessageDialog(this, "Vui lòng trả phòng trước khi thanh toán", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    } else if (tfTienNhan.getText().equals("")) {
	    	JOptionPane.showConfirmDialog(this, "Bạn chưa nhập số tiền nhận.\nBạn có muốn tiếp tục thanh toán?", "Xác nhận", JOptionPane.YES_NO_OPTION);
	        return;
	    } else if (Double.parseDouble(tfTienNhan.getText()) < Double.parseDouble(tfThanhTien.getText())) {
	        JOptionPane.showMessageDialog(this, "Số tiền nhận phải lớn hơn tổng tiền", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	        return;
	    }
	    String path = System.getProperty("user.dir") + "\\exportHoaDon\\" + model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString() + ".pdf";
		com.itextpdf.text.Font font10 = FontFactory.getFont("font/SVN-Times New Roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 10);
		com.itextpdf.text.Font font13 = FontFactory.getFont("font/SVN-Times New Roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 13);
		com.itextpdf.text.Font font17 = FontFactory.getFont("font/SVN-Times New Roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 17);
		com.itextpdf.text.Font font20 = FontFactory.getFont("font/SVN-Times New Roman.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED, 25);
		Document doc = new Document();
		try {
			PdfWriter.getInstance(doc, new FileOutputStream(path));
			doc.open();
			
			Paragraph karaokeName = new Paragraph("KARAOKE NICE\n", font20);
			karaokeName.setAlignment(Element.ALIGN_CENTER);
            doc.add(karaokeName);
            
            Paragraph karaokeNameDiaChi = new Paragraph(" Địa chỉ: 14 Nguyễn Văn Bảo, Phường 4, Quận Gò Vấp, Thành phố Hồ Chí Minh\n", font10);
            karaokeNameDiaChi.setAlignment(Element.ALIGN_CENTER);
            doc.add(karaokeNameDiaChi);
            
            Paragraph karaokeNameSDT = new Paragraph("Số điện thoại: 0366403157\n", font10);
            karaokeNameSDT.setAlignment(Element.ALIGN_CENTER);
            
            doc.add(karaokeNameSDT);
            Paragraph starLine = new Paragraph("________________________________________________________________________________________________________", font10);
            Paragraph khoangTrang = new Paragraph("\n");
            doc.add(khoangTrang);

            
            Paragraph paragrapTitle = new Paragraph("HÓA ĐƠN TÍNH TIỀN\n", font20);
            paragrapTitle.setAlignment(Element.ALIGN_CENTER);
            doc.add(paragrapTitle);
            doc.add(khoangTrang);
            
            Paragraph paragraphNgayLapHDLine1_1 = new Paragraph("Ngày lập hóa đơn: " + daoHoaDon.getNgayLapHD(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString()).toString(), font10);
            Paragraph paragraphMaHoaDonLine1_2 = new Paragraph("Mã hóa đơn: " + model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString(), font10);
            paragraphNgayLapHDLine1_1.setAlignment(Element.ALIGN_LEFT);
            paragraphMaHoaDonLine1_2.setAlignment(Element.ALIGN_RIGHT);
            PdfPTable tableLine1 = new PdfPTable(2);
            tableLine1.setWidthPercentage(100);
            PdfPCell cellLine1_1 = new PdfPCell(paragraphNgayLapHDLine1_1);
            PdfPCell cellLine1_2 = new PdfPCell(paragraphMaHoaDonLine1_2);
            cellLine1_2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cellLine1_1.setBorder(PdfPCell.NO_BORDER);
            cellLine1_2.setBorder(PdfPCell.NO_BORDER);
            tableLine1.addCell(cellLine1_1);
            tableLine1.addCell(cellLine1_2);
            doc.add(tableLine1);
            
            Paragraph paragraphTenNhanVienLine2_1 = new Paragraph("Thu ngân: " + fQuanTriHeThong.getTenNV(), font10);
            Paragraph paragraphPhongLine2_2 = new Paragraph("Phòng: " + model1.getValueAt(tblDanhSachHD.getSelectedRow(), 2).toString(), font10);
            paragraphTenNhanVienLine2_1.setAlignment(Element.ALIGN_LEFT);
            paragraphPhongLine2_2.setAlignment(Element.ALIGN_RIGHT);
            PdfPTable tableLine2 = new PdfPTable(2);
            tableLine2.setWidthPercentage(100);
            PdfPCell cellLine2_1 = new PdfPCell(paragraphTenNhanVienLine2_1);
            PdfPCell cellLine2_2 = new PdfPCell(paragraphPhongLine2_2);
            cellLine2_2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cellLine2_1.setBorder(PdfPCell.NO_BORDER);
            cellLine2_2.setBorder(PdfPCell.NO_BORDER);
            tableLine2.addCell(cellLine2_1);
            tableLine2.addCell(cellLine2_2);
            doc.add(tableLine2);
            
            doc.add(starLine);

            Paragraph paragraphKhachHangLine3_1 = new Paragraph("Khách hàng:  " + tfTenKH.getText(), font10);
            Paragraph paragraphSoDienThoaiLine3_2 = new Paragraph("Số điện thoại: " + tfSDT.getText(), font10);
            paragraphKhachHangLine3_1.setAlignment(Element.ALIGN_LEFT);
            paragraphSoDienThoaiLine3_2.setAlignment(Element.ALIGN_RIGHT);
            PdfPTable tableLine3 = new PdfPTable(2);
            tableLine3.setWidthPercentage(100);
            PdfPCell cellLine3_1 = new PdfPCell(paragraphKhachHangLine3_1);
            PdfPCell cellLine3_2 = new PdfPCell(paragraphSoDienThoaiLine3_2);
            cellLine3_2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cellLine3_1.setBorder(PdfPCell.NO_BORDER);
            cellLine3_2.setBorder(PdfPCell.NO_BORDER);
            tableLine3.addCell(cellLine3_1);
            tableLine3.addCell(cellLine3_2);
            doc.add(tableLine3);
            
            
        	Paragraph paragraphGioVaoPhongLine4_1 = new Paragraph("Giờ vào phòng: "   + daoHoaDon.getGioVao(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString()).toString(), font10);
        	Paragraph paragrapGioRaPhongLine4_2 = new Paragraph("Giờ ra phòng: " + daoHoaDon.getGioRa(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString()).toString(), font10);
        	paragraphGioVaoPhongLine4_1.setAlignment(Element.ALIGN_LEFT);
        	paragrapGioRaPhongLine4_2.setAlignment(Element.ALIGN_RIGHT);
        	PdfPTable tableLine4 = new PdfPTable(2);
            tableLine4.setWidthPercentage(100);
            PdfPCell cellLine4_1 = new PdfPCell(paragraphGioVaoPhongLine4_1);
            PdfPCell cellLine4_2 = new PdfPCell(paragrapGioRaPhongLine4_2);
            cellLine4_2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cellLine4_1.setBorder(PdfPCell.NO_BORDER);
            cellLine4_2.setBorder(PdfPCell.NO_BORDER);
            tableLine4.addCell(cellLine4_1);
            tableLine4.addCell(cellLine4_2);
            doc.add(tableLine4);
           
           
            
            doc.add(khoangTrang);
            Paragraph paragraph4 = new Paragraph("Dịch vụ:", font13);
            paragraph4.setAlignment(Element.ALIGN_LEFT);
            doc.add(paragraph4);
            Paragraph paragraph5 = new Paragraph("\n", font10);
            doc.add(paragraph5);
            PdfPTable tbl = new PdfPTable(5);
            tbl.addCell(new Phrase("        STT", font10));
            tbl.addCell(new Phrase("  Tên dịch vụ", font10));
            tbl.addCell(new Phrase("     Số lượng", font10));
            tbl.addCell(new Phrase("         Giá", font10));
            tbl.addCell(new Phrase("   Thành tiền", font10));
        	int stt=0;
    		DecimalFormat formatter = new DecimalFormat("###,###,###");
            for (int i = 0; i < tblThongTinHD.getRowCount(); i++) {
            	if(!model.getValueAt(i, 5).toString().equals("Giờ")) {
            		stt++;
	                String ten = tblThongTinHD.getValueAt(i, 1).toString();
	                double gia = Double.parseDouble(tblThongTinHD.getValueAt(i, 3).toString());
	                String sl = tblThongTinHD.getValueAt(i, 2).toString();
	                double thanhTien = Double.parseDouble(tblThongTinHD.getValueAt(i, 4).toString());
	                tbl.addCell(new Phrase(String.valueOf(stt), font10));
	                tbl.addCell(new Phrase(ten, font10));
	                tbl.addCell(new Phrase(sl, font10));
	                tbl.addCell(new Phrase(formatter.format(gia)+" VNĐ", font10));
	                tbl.addCell(new Phrase(formatter.format(thanhTien)+" VNĐ", font10));
            	}
            }
            doc.add(tbl);
            doc.add(starLine);
            
            
         
            double tienPhong = daoHoaDon.getTienPhong(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString()); 
            double tienDV = Double.parseDouble(tfThanhTien.getText())-tienPhong;
            
            Paragraph paragraphTongTienLine1_1 = new Paragraph("Tổng tiền dịch vụ: " + formatter.format(tienDV) + " VNĐ", font10);
            Paragraph paragraphTongTienLine1_2 = new Paragraph("Tiền khách đưa: " + formatter.format(Double.parseDouble(tfTienNhan.getText())) + " VNĐ", font10);
            paragraphTongTienLine1_1.setAlignment(Element.ALIGN_LEFT);
            paragraphTongTienLine1_2.setAlignment(Element.ALIGN_RIGHT);
            PdfPTable tableLine5 = new PdfPTable(2);
            tableLine5.setWidthPercentage(100);
            PdfPCell cellLine5_1 = new PdfPCell(paragraphTongTienLine1_1);
            PdfPCell cellLine5_2 = new PdfPCell(paragraphTongTienLine1_2);
            cellLine5_2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cellLine5_1.setBorder(PdfPCell.NO_BORDER);
            cellLine5_2.setBorder(PdfPCell.NO_BORDER);
            tableLine5.addCell(cellLine5_1);
            tableLine5.addCell(cellLine5_2);
            doc.add(tableLine5);
            
            Paragraph paragraphTongTienLine2_1 = new Paragraph("Tổng tiền phòng: " + formatter.format(tienPhong) + " VNĐ", font10);
            Paragraph paragraphTongTienLine2_2 = new Paragraph("Tiền trả khách: " + formatter.format(Double.parseDouble(tfTienThoi.getText())) + " VNĐ", font10);
            paragraphTongTienLine2_1.setAlignment(Element.ALIGN_LEFT);
            paragraphTongTienLine2_2.setAlignment(Element.ALIGN_RIGHT);
            PdfPTable tableLine6 = new PdfPTable(2);
            tableLine6.setWidthPercentage(100);
            PdfPCell cellLine6_1 = new PdfPCell(paragraphTongTienLine2_1);
            PdfPCell cellLine6_2 = new PdfPCell(paragraphTongTienLine2_2);
            cellLine6_2.setHorizontalAlignment(PdfPCell.ALIGN_RIGHT);
            cellLine6_1.setBorder(PdfPCell.NO_BORDER);
            cellLine6_2.setBorder(PdfPCell.NO_BORDER);
            tableLine6.addCell(cellLine6_1);
            tableLine6.addCell(cellLine6_2);
            doc.add(tableLine6);
            
            doc.add(starLine);
            Paragraph paragraphTongTienLine5 = new Paragraph("Tổng tiền: " + formatter.format(Double.parseDouble(tfThanhTien.getText())) + " VNĐ", font17);
            paragraphTongTienLine5.setAlignment(Element.ALIGN_RIGHT);
            doc.add(paragraphTongTienLine5);

            doc.close();
            
            openBill(model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, e);
		}
	    
		List<DichVu> list = daoDichVu.loadDSDichVuFromDatabase();
		for (DichVu dv : list) {	
			for (int i=0; i<model.getRowCount();i++) {
				if(dv.getTenDichVu().equals(model.getValueAt(i, 1).toString())) {
					
					daoDichVu.updateSoLuong(dv.getSoLuongTon()-Integer.parseInt(model.getValueAt(i, 2).toString()),dv.getMaDichVu());
				}
			}
		}
		JOptionPane.showMessageDialog(this, "Thanh toán hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
		daoHoaDon.updateTTHD(true, model1.getValueAt(tblDanhSachHD.getSelectedRow(), 0).toString());
		String maphong = model1.getValueAt(tblDanhSachHD.getSelectedRow(), 2).toString();
		Phong p = daoPhong.getPhongTheoMa(maphong);
		p.setTinhTrangPhong("Trống");
        daoPhong.updatePhong(p);
 		DocDuLieuDatabaseVaoTable();
 		tfThanhTien.setText("");
 		tfTienNhan.setText("");
 		tfTienThoi.setText("");
 		tfTenKH.setText("");
 		tfSDT.setText("");
 		model.setRowCount(0);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object source = e.getSource();
	    
	    if (source.equals(tblDanhSachHD)) {
	        int selectedRow = tblDanhSachHD.getSelectedRow();
	        tfSDT.setText(daoKhachHang.getSDTTheoMaHD(model1.getValueAt(selectedRow, 0).toString()));

	        // Lấy giá trị của cột từ dòng đã chọn
	        Object tenKhachHang = model1.getValueAt(selectedRow, 1);
	        tfTenKH.setText(tenKhachHang.toString());
	        DocDuLieuCTHoaDonDataBaseVaoTable();
	        if(model.getRowCount()!=0) {
				double tongTien = 0;
		    	for(int i = 0; i< model.getRowCount(); i++) {
			    	tongTien = tongTien + Double.parseDouble(model.getValueAt(i, 4).toString());
			    		
				}
				tfThanhTien.setText(String.valueOf(Math.round(tongTien)));
			}
			else {
				tfThanhTien.setText("");
			}
	       
	    }
	    
	    else if (source.equals(tblThongTinHD)) {
	        if (!model.getValueAt(tblThongTinHD.getSelectedRow(), 5).toString().equals("Giờ")) {
	            int row = tblThongTinHD.getSelectedRow();
	            cbTDV.setSelectedItem(model.getValueAt(row, 1).toString());
				spnSoLuong.setValue(Integer.parseInt(model.getValueAt(row, 2).toString()));
	        }
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
		if(o.equals(btnThemDV)) {
			btnThemDichVu();
		} else if (o.equals(btnCapNhap)) {
			btnCapNhapDichVu();
		} else if (o.equals(btnXoa)) {
			btnXoaDichVu();
		} else if (o.equals(btnXacNhanTraPhong)) {
			btnTraPhong();
		} else if (o.equals(btnThanhToan)) {
			btnThanhToan();
		}
	}
	
	public static void openBill(String path) {
        try {
            if((new File(System.getProperty("user.dir") + "\\exportHoaDon\\" + path + ".pdf")).exists()){
                Process p = Runtime
                        .getRuntime()
                        .exec("rundll32 url.dll, FileProtocolHandler " + System.getProperty("user.dir") + "\\exportHoaDon\\" + path + ".pdf");
            } else {
                JOptionPane.showMessageDialog(null, "Không tìm thấy hóa đơn");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
}
