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
import java.awt.Image;
import java.awt.LayoutManager;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Date;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;


import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;

import connectDB.ConnectDB;
import dao.KhachHang_Dao;
import dao.LoaiPhong_Dao;
import dao.PhieuDatPhong_Dao;
import dao.Phong_Dao;
import entity.KhachHang;
import entity.LoaiPhong;
import entity.PhieuDatPhong;
import entity.Phong;

public class pnDatPhong extends JPanel implements ActionListener,MouseListener{
	private Image imgBG = new ImageIcon(pnDatPhong.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconPick = new ImageIcon(pnDatPhong.class.getResource("/image/iconPick.png"));
	private ImageIcon iconThue = new ImageIcon(pnDatPhong.class.getResource("/image/iconThue.png"));
	private ImageIcon iconSave = new ImageIcon(pnDatPhong.class.getResource("/image/save.png"));
	private ImageIcon iconDelete = new ImageIcon(pnDatPhong.class.getResource("/image/huy1.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnDatPhong.class.getResource("/image/update.png"));
	private ImageIcon iconPhong1 = new ImageIcon(pnDatPhong.class.getResource("/image/phong1.png"));
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0, Color.decode("#FAFFD1"));
	private DefaultTableModel model;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private JTextField txtTenKH, txtSDT;
	private JButton btnChonKH, btnLuu;
	private JTable tblPhong;
	private JTextField tfSoPhong,tfTrangThai,tfPhong, tfLoaiPhong, tfGia;
	private LoaiPhong_Dao daoLoaiPhong = new LoaiPhong_Dao();
	private Phong_Dao daoPhong = new Phong_Dao();
	private JLabel lblGia;
	private JDateChooser dateNgayDat;
	private JButton btnThemDonDP, btnXoaDonDP,btnSuaDonDP;
	private JComboBox cmbGio, cmbPhut;
	private String maPhong;
	private PhieuDatPhong_Dao daoPhieuDatPhong = new PhieuDatPhong_Dao();
	private KhachHang_Dao daoKhachHang = new KhachHang_Dao();
	private ArrayList<String> arrMaPDPTable;
	private int countMaTable ;
	private ArrayList<String> arrMaPDPSQL;
	private int countMaSQL ;
	private ArrayList<Integer> arrViTriChuaLuu = new ArrayList<>();
	private ArrayList<String> arrSdtDoiLuu = new ArrayList<>();
	private int arrSdtDoiLuuSize = 0;
	private DefaultComboBoxModel<Integer> gioModel;
	private DefaultComboBoxModel<String> phutModel;
	private int kiemTraChonThem = 0;
	private JPanel pnlTable;
	private ArrayList<JPanel> pnlPhongListTrong, pnlPhongListDangSuDung;
	
	public void setTenKH(String tenKH) {
	    txtTenKH.setText(tenKH);
	}
	
	public void setSDT(String soDT) {
		txtSDT.setText(soDT);
	}
	/**
	 * Create the panel.
	 */
	public pnDatPhong() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		setLayout(null);
		JPanel pnMain = new JPanel();
		pnMain.setBounds(0, 0, 1599, 1040);
		add(pnMain);
		pnMain.setLayout(null);
		
		JLabel lblTitle = new JLabel("ĐẶT PHÒNG");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(640, 20, 294, 36);
		pnMain.add(lblTitle);
	
	    
	    JPanel pnlTTPhong = new JPanel() {
	    	@Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            // Không vẽ gì trong phương thức paintComponent của panel con
	        }
		};
	    pnlTTPhong.setBounds(31, 191, 615, 838);
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
		sbrDanhSachPhong.setBounds(15, 250, 590, 577);
		sbrDanhSachPhong.revalidate();
		sbrDanhSachPhong.repaint();
		pnlTTPhong.add(sbrDanhSachPhong);
		setBorderTitle(sbrDanhSachPhong, "Danh sách phòng");

		
		
		JLabel lblPhong = new JLabel("Phòng:");
		lblPhong.setBounds(15, 40, 102, 25);
		pnlTTPhong.add(lblPhong);
		lblPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel lblLoaiPhong = new JLabel("Loại phòng:");
		lblLoaiPhong.setBounds(15, 95, 102, 25);
		pnlTTPhong.add(lblLoaiPhong);
		lblLoaiPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		JLabel lblNgayDat = new JLabel("Ngày đặt:");
		lblNgayDat.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNgayDat.setBounds(15, 205, 102, 25);
		pnlTTPhong.add(lblNgayDat);
		
		dateNgayDat = new JDateChooser();
		dateNgayDat.setFont(new Font("Tahoma", Font.BOLD, 15));
		dateNgayDat.setBounds(125, 205, 190, 25);
		pnlTTPhong.add(dateNgayDat);
		dateNgayDat.setLocale(new Locale("vi", "VN"));
		JTextFieldDateEditor editor = (JTextFieldDateEditor) dateNgayDat.getDateEditor();
		editor.setEditable(false);
		
		JLabel lblGioDat = new JLabel("Giờ đặt:");
		lblGioDat.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGioDat.setBounds(351, 205, 102, 25);
		pnlTTPhong.add(lblGioDat);
		
		cmbGio = new JComboBox<>();
		gioModel = new DefaultComboBoxModel<>();

        // Thêm các giá trị từ 9 đến 21 vào mô hình
        for (int i = 9; i <= 21; i++) {
            gioModel.addElement(i);
        }

        // Thiết lập mô hình cho JComboBox
        cmbGio.setModel(gioModel);
		cmbGio.setFont(new Font("Tahoma", Font.BOLD, 15));
		cmbGio.setBounds(458, 205, 51, 25);
		pnlTTPhong.add(cmbGio);
		
		JLabel lblNewLabel_1 = new JLabel(":");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1.setBounds(519, 205, 14, 25);
		pnlTTPhong.add(lblNewLabel_1);
		
		cmbPhut = new JComboBox<>();
		phutModel = new DefaultComboBoxModel<>();

        // Thêm các giá trị từ "00" đến "60" vào mô hình
        for (int i = 0; i <= 60; i++) {
            String phut = String.format("%02d", i);
            phutModel.addElement(phut);
        }

        // Thiết lập mô hình cho JComboBox
        cmbPhut.setModel(phutModel);
		cmbPhut.setFont(new Font("Tahoma", Font.BOLD, 15));
		cmbPhut.setBounds(536, 205, 51, 25);
		pnlTTPhong.add(cmbPhut);
		
		btnThemDonDP = new btnMyButton(130, 40, "Thêm", new Dimension(50, 23), iconThue.getImage(), new Dimension(25,20), gra);
		btnThemDonDP.setBounds(351, 141, 137, 40);
		pnlTTPhong.add(btnThemDonDP);
		
		tfPhong = new JTextField();
		tfPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfPhong.setEditable(false);
		tfPhong.setColumns(10);
		tfPhong.setBounds(125, 40, 190, 25);
		pnlTTPhong.add(tfPhong);
		
		tfLoaiPhong = new JTextField();
		tfLoaiPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfLoaiPhong.setEditable(false);
		tfLoaiPhong.setColumns(10);
		tfLoaiPhong.setBounds(125, 95, 190, 25);
		pnlTTPhong.add(tfLoaiPhong);
		
		lblGia = new JLabel("Giá:");
		lblGia.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGia.setBounds(15, 150, 102, 25);
		pnlTTPhong.add(lblGia);
		
		tfGia = new JTextField();
		tfGia.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfGia.setEditable(false);
		tfGia.setColumns(10);
		tfGia.setBounds(125, 150, 190, 25);
		pnlTTPhong.add(tfGia);
		
	    
	    JPanel pnlDonDatP = new JPanel() {
	    	@Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            // Không vẽ gì trong phương thức paintComponent của panel con
	        }
		};
	    pnlDonDatP.setBounds(685, 92, 826, 937);
	    pnlDonDatP.setOpaque(false);
	    setBorderTitle(pnlDonDatP, "");
	    pnMain.add(pnlDonDatP);
	    pnlDonDatP.setLayout(null);
	    
	    JPanel pnlDSDonDP = new JPanel();
	    pnlDSDonDP.setLayout(null);
	    setBorderTitle(pnlDSDonDP, "Danh sách đơn đặt phòng");
	    pnlDSDonDP.setBounds(10, 159, 807, 720);
	    pnlDSDonDP.setOpaque(false);
	    String[] cols = { "STT", "Mã đơn", "Khách hàng", "Số đt", "Mã phòng", "Ngày đặt", "Giờ đặt" };
		model = new DefaultTableModel(cols, 0);
		tblPhong = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		int[] columnWidths = {50, 50, 200, 80, 80, 100, 80};
        for (int i = 0; i < cols.length; i++) {
            TableColumn column = tblPhong.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }
		TableColumnModel columnModel = tblPhong.getColumnModel();

		// Đặt độ rộng cho cột "STT" (ví dụ: 50 pixel)
		int sttColumnWidth = 10;
		TableColumn sttColumn = columnModel.getColumn(0);
		sttColumn.setPreferredWidth(sttColumnWidth);
		setCustomTable(tblPhong);
		tblPhong.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblPhong);
		scrTable.setBounds(10, 25, 787, 684);
		pnlDSDonDP.add(scrTable);
		
	    pnlDonDatP.add(pnlDSDonDP);
	    
	    JLabel lblTenKH = new JLabel("Tên khách hàng:");
	    lblTenKH.setBounds(26, 81, 143, 25);
	    pnlDonDatP.add(lblTenKH);
	    lblTenKH.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    txtTenKH = new JTextField();
	    txtTenKH.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
	    txtTenKH.setFont(new Font("Tahoma", Font.BOLD, 15));
	    txtTenKH.setEditable(false);
	    txtTenKH.setBounds(180, 81, 190, 25);
	    pnlDonDatP.add(txtTenKH);
	    txtTenKH.setColumns(10);
	    
	    btnChonKH = new btnMyButton(137, 40, "Chọn KH", new Dimension(70, 23), iconPick.getImage(), new Dimension(25,20), gra);
	    btnChonKH.setBounds(680, 70, 137, 36);
	    pnlDonDatP.add(btnChonKH);
	    btnChonKH.addActionListener(new ActionListener() {
	        public void actionPerformed(ActionEvent e) {
	        	fChonKH fchonKH = new fChonKH(pnDatPhong.this);
	        	fchonKH.setVisible(true);
	        }
	    });
	    btnChonKH.setFont(new Font("Tahoma", Font.BOLD, 11));
	    
	    btnLuu = new btnMyButton(200, 40, "Lưu đơn đặt phòng", new Dimension(135, 23), iconSave.getImage(), new Dimension(25,20), gra);
	    btnLuu.setBounds(307, 890, 200, 36);
	    pnlDonDatP.add(btnLuu);
	    btnLuu.setFont(new Font("Tahoma", Font.BOLD, 11));
	    
	    JLabel lblDonDatP = new JLabel("Đơn đặt phòng");
	    lblDonDatP.setForeground(Color.BLUE);
	    lblDonDatP.setFont(new Font("Tahoma", Font.BOLD, 23));
	    lblDonDatP.setBounds(328, 21, 179, 25);
	    pnlDonDatP.add(lblDonDatP);
	    
	    JLabel lblSDT = new JLabel("SDT:");
	    lblSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
	    lblSDT.setBounds(380, 81, 46, 25);
	    pnlDonDatP.add(lblSDT);
	    
	    txtSDT = new JTextField();
	    txtSDT.setOpaque(false);
	    txtSDT.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
	    txtSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
	    txtSDT.setBounds(436, 81, 150, 25);
	    pnlDonDatP.add(txtSDT);
	    txtSDT.setColumns(10);
	    
	    btnXoaDonDP = new btnMyButton(130, 40, "Xóa", new Dimension(50, 23), iconDelete.getImage(), new Dimension(25, 20), gra);
	    btnXoaDonDP.setBounds(88, 890, 137, 40);
	    pnlDonDatP.add(btnXoaDonDP);
	    
	    btnSuaDonDP = new btnMyButton(130, 40, "Sửa", new Dimension(50, 23), iconUpdate.getImage(), new Dimension(25, 20), gra);
	    btnSuaDonDP.setBounds(597, 890, 137, 40);
	    pnlDonDatP.add(btnSuaDonDP);
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
	    lblStaff.setBounds(100, 22, 93, 25);
	    pnlNhanVien.add(lblStaff);
	    
	    JLabel lblNewLabel = new JLabel(fQuanTriHeThong.getTenNV());
	    lblNewLabel.setForeground(Color.BLUE);
	    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
	    lblNewLabel.setBounds(250, 22, 226, 25);
	    pnlNhanVien.add(lblNewLabel);
	    
	    btnThemDonDP.addActionListener(this);
	    btnLuu.addActionListener(this);
	    btnXoaDonDP.addActionListener(this);
	    btnSuaDonDP.addActionListener(this);
	    tblPhong.addMouseListener(this);
	    DocDuLieuDataBaseVaoTable();
	    
	    arrMaPDPTable = new ArrayList<String>();
	    for (int i = 0; i < model.getRowCount(); i++) { 
	        String myString = String.valueOf(model.getValueAt(i, 1));
	        arrMaPDPTable.add(myString);
	    }
	    countMaTable = model.getRowCount() + 1;
	    arrMaPDPSQL = new ArrayList<String>();
	    for (int j = 0; j < model.getRowCount(); j++) { 
	        String myString1 = String.valueOf(model.getValueAt(j, 1));
	        arrMaPDPSQL.add(myString1);
	    }
	    countMaSQL = model.getRowCount();
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
	
	public void setBorderTitle(JScrollPane sbrDanhSachPhong, String title) {
		Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#1995AD"), 2),
				title, TitledBorder.LEADING, TitledBorder.TOP, fontBold, Color.black);
		sbrDanhSachPhong.setBorder(border);
}
	public void taoDanhSachPhong(JPanel dsPhong) {
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
		pnlPhongListTrong = new ArrayList<>();
		pnlPhongListDangSuDung = new ArrayList<>();
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
				pnlPhong1.addMouseListener(new MouseAdapter() {
				    public void mouseClicked(MouseEvent e) {
				    	if (e.getButton() == MouseEvent.BUTTON1) {
				    		JTextField tfMaPhong = (JTextField) finalPnlPhong1.getComponent(1);
					    	maPhong = tfMaPhong.getText();
					    	for (JPanel panel : pnlPhongListTrong) {
				                panel.setBackground(Color.decode("#1995AD"));
				                
				            }
					    	for (JPanel panel : pnlPhongListDangSuDung) {
				                panel.setBackground(Color.decode("#00FF00"));
				                
				            }
					        finalPnlPhong1.setBackground(Color.red);
					        tfPhong.setText(daoPhong.getRoomList().get(currentViTri).getTenPhong().toString());
					        LoaiPhong loaiP = daoLoaiPhong.getLoaiPhongTheoMa(daoPhong.getRoomList().get(currentViTri).getLoaiPhong().getMaLoaiP());
					        tfLoaiPhong.setText(loaiP.getTenLoaiP());
					        DecimalFormat decimalFormat = new DecimalFormat("#,###");
					        String giaPhongFormatted = decimalFormat.format(daoPhong.getRoomList().get(currentViTri).getGiaPhong());
					        String giaPhongFormattedWithUnit = giaPhongFormatted + " VND";
					        tfGia.setText(giaPhongFormattedWithUnit);

					        
					        kiemTraChonThem = 1;
				    	}
				    	else {
				    		if(model.getRowCount() == daoPhieuDatPhong.getDsPhieuDatPhongSize()){
				    			JTextField tfMaPhong = (JTextField) finalPnlPhong1.getComponent(1);
					    		JFrame frame = new JFrame();
					            frame.setSize(1020, 600);
					            frame.setLayout(null);
					            frame.setTitle("Các lịch đặt của phòng " + tfMaPhong.getText());
					            frame.setResizable(false);
					            frame.setLocationRelativeTo(null);
					            frame.setVisible(true);
					            
					         // Tạo DefaultTableModel với tên cột là "Ngày đã đặt" và "Họ tên"
					            DefaultTableModel model = new DefaultTableModel();
					            model.addColumn("Ngày đã đặt");
					            model.addColumn("Họ tên");

					            List<Object[]> dataFromDatabase = daoPhieuDatPhong.getChiTietDanhSachCacNgayDaDuocDat(tfMaPhong.getText());
					            for (Object[] data : dataFromDatabase) {
					                model.addRow(data);
					            }



					            // Tạo JTable từ DefaultTableModel
					            JTable table = new JTable(model);

					            // Đặt thuộc tính cho JTable (độ rộng cột, chiều cao dòng, v.v.)
					            table.getColumnModel().getColumn(0).setPreferredWidth(150); // Độ rộng cột "Ngày đã đặt"
					            table.getColumnModel().getColumn(1).setPreferredWidth(200); // Độ rộng cột "Họ tên"

					            // Thêm JTable vào một JScrollPane để có thanh cuộn nếu cần
					            JScrollPane scrollPane = new JScrollPane(table);
					            scrollPane.setBounds(10, 10, 980, 550); // Điều chỉnh vị trí và kích thước cho phù hợp với JFrame của bạn
					            frame.add(scrollPane); // Thêm JScrollPane chứa JTable vào JFrame
				    		}
				    		else {
				    			int result = JOptionPane.showConfirmDialog(null,
						                "Bạn phải lưu đơn đặt phòng trước khi xem chi tiết",
						                "Xác nhận lưu",
						                JOptionPane.YES_NO_OPTION);

						        if (result == JOptionPane.YES_OPTION) {
						        	luuDonDatPhong();
						        	JTextField tfMaPhong = (JTextField) finalPnlPhong1.getComponent(1);
						    		JFrame frame = new JFrame();
						            frame.setSize(1020, 600);
						            frame.setLayout(null);
						            frame.setTitle("Các lịch đặt của phòng " + tfMaPhong.getText());
						            frame.setResizable(false);
						            frame.setLocationRelativeTo(null);
						            frame.setVisible(true);
						            
						         // Tạo DefaultTableModel với tên cột là "Ngày đã đặt" và "Họ tên"
						            DefaultTableModel model = new DefaultTableModel();
						            model.addColumn("Ngày đã đặt");
						            model.addColumn("Họ tên");

						            List<Object[]> dataFromDatabase = daoPhieuDatPhong.getChiTietDanhSachCacNgayDaDuocDat(tfMaPhong.getText());
						            for (Object[] data : dataFromDatabase) {
						                model.addRow(data);
						            }



						            // Tạo JTable từ DefaultTableModel
						            JTable table = new JTable(model);

						            // Đặt thuộc tính cho JTable (độ rộng cột, chiều cao dòng, v.v.)
						            table.getColumnModel().getColumn(0).setPreferredWidth(150); // Độ rộng cột "Ngày đã đặt"
						            table.getColumnModel().getColumn(1).setPreferredWidth(200); // Độ rộng cột "Họ tên"

						            // Thêm JTable vào một JScrollPane để có thanh cuộn nếu cần
						            JScrollPane scrollPane = new JScrollPane(table);
						            scrollPane.setBounds(10, 10, 980, 550); // Điều chỉnh vị trí và kích thước cho phù hợp với JFrame của bạn
						            frame.add(scrollPane); // Thêm JScrollPane chứa JTable vào JFrame
						        }
						        else {
						        	//Không làm 
						        }
				    		}
				    		

				    	}
				    }
				});
				viTri++;
			}

			boxCol.setPreferredSize(new Dimension(585, chieuCaoDanhSachPhong));
			boxCol.add(panel);
			dsPhong.add(boxCol);
		}
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		Date ngayHienTai = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	    try {
			ngayHienTai = sdf.parse(sdf.format(ngayHienTai));
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		Date ngayTuJDateChooser = (Date) dateNgayDat.getDate();
		Calendar cal = Calendar.getInstance();
		Object selectedHour = cmbGio.getSelectedItem();
		int gioDatInt = Integer.parseInt(selectedHour.toString());
		Object selectedPhut = cmbPhut.getSelectedItem();
		int phutDatInt = Integer.parseInt(selectedPhut.toString());
        int gioHienTai = cal.get(Calendar.HOUR_OF_DAY);
        int phutHienTai = cal.get(Calendar.MINUTE);
		if (o.equals(btnThemDonDP)) {
			if (txtTenKH.getText().isEmpty()) {
	            JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng chọn khách hàng trước khi thêm đơn đặt phòng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
	        }
			else if(kiemTraChonThem == 0) {
				JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng chọn phòng trước khi thêm đơn đặt phòng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
			else if(dateNgayDat.getDate() == null ) {
				JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng chọn lịch đặt phòng trước khi thêm đơn đặt phòng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
			else if(ngayTuJDateChooser.before(ngayHienTai)) {
				JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng chọn lịch sau thời gian hiện tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
			else if(ngayTuJDateChooser.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(ngayHienTai.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
				if(gioDatInt < gioHienTai) {
					JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng không chọn giờ trước thời điểm hiện tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
				else if(gioDatInt > gioHienTai) {
					themDonDatPhong();
				}
				else{
					if(phutDatInt <= phutHienTai) {
						JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng chọn phút sau thời điểm hiện tại", "Lỗi", JOptionPane.ERROR_MESSAGE);
					}
					else {
						themDonDatPhong();
					}
				}
			}
			else {
				themDonDatPhong();
	        }
		}
		if(o.equals(btnXoaDonDP)) {
			xoaDonDatPhong();
		}
		else if (o.equals(btnLuu)) {
		    luuDonDatPhong();
		}else if(o.equals(btnSuaDonDP)) {
			int selectedRow = tblPhong.getSelectedRow();
			if(model.getRowCount() == daoPhieuDatPhong.getDsPhieuDatPhongSize()) {	
				if(selectedRow == -1){
					JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng chọn đơn đặt phòng trước khi sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}else {
					suaDonDatPhong();
				}
			}
			else {
				JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng lưu đơn đặt phòng trước khi thực hiện chức năng sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int selectedRow = tblPhong.getSelectedRow();
		kiemTraChonThem = 0;

		String maPhong = String.valueOf(model.getValueAt(selectedRow, 4));
		tfPhong.setText(daoPhong.getPhongTheoMa(maPhong).getTenPhong());
		
		tfLoaiPhong.setText(String.valueOf(daoLoaiPhong.getLoaiPhongTheoMa(daoPhong.getPhongTheoMa(maPhong).getLoaiPhong().getMaLoaiP()).getTenLoaiP()));
		tfGia.setText(daoPhong.getPhongTheoMa(maPhong).getGiaPhong().toString());
		
		txtTenKH.setText(String.valueOf(model.getValueAt(selectedRow, 2)));
		txtSDT.setText(String.valueOf(model.getValueAt(selectedRow, 3)));
		
		String ngayDatStr = String.valueOf(model.getValueAt(selectedRow, 5));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
			Date date = sdf.parse(ngayDatStr);
			dateNgayDat.setDate(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
								
		; 
        String gioDatStr = String.valueOf(model.getValueAt(selectedRow, 6));
        LocalTime localTime = LocalTime.parse(gioDatStr, DateTimeFormatter.ofPattern("HH:mm:ss"));
        int selectedHour = localTime.getHour(); // Lấy giờ dưới dạng int
	    String selectedMinute = String.format("%02d", localTime.getMinute()); // Định dạng phút thành chuỗi hai chữ số
        gioModel.setSelectedItem(selectedHour);
        cmbPhut.setSelectedItem(selectedMinute);
        for (JPanel panel : pnlPhongListTrong) {
            panel.setBackground(Color.decode("#1995AD"));
            
        }
    	for (JPanel panel : pnlPhongListDangSuDung) {
            panel.setBackground(Color.decode("#00FF00"));
            
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
	public void DocDuLieuDataBaseVaoTable() {
		List<PhieuDatPhong> list = daoPhieuDatPhong.loadDSPhieuDatPhongFromDatabase();
		model.setRowCount(0);
		for(PhieuDatPhong pdp : list) {
			int stt = model.getRowCount() + 1;
			KhachHang khachHang = daoKhachHang.getMaKhachHangTheoMa(pdp.getKhachhang().getMaKhachHang());
			model.addRow(new Object[] { stt, pdp.getMaPhieuDatPhong(), khachHang.getHoTen(), khachHang.getSoDT(), pdp.getPhong().getMaPhong(), pdp.getNgayDatPhong(), pdp.getGioDatPhong()});
		}
	}
	public void capNhapMangMaPDPTable() {
		arrMaPDPTable.clear();
		for (int i = 0; i < countMaTable ; i++) { 
	        String myString = String.valueOf(model.getValueAt(i, 1));
	        arrMaPDPTable.add(myString);
	    }
	}
    public boolean kiemTraTrungMaTable(String maPDP) {
    	for (String element : arrMaPDPTable) {
            if(element.equals(maPDP)) {
            	return false;
            }
        }
    	return true;
    }
    public void capNhapMangMaPDPSQL() {
		arrMaPDPSQL.clear();
		countMaSQL++;
		for (int i = 0; i < countMaSQL; i++) { 
	        String myString1 = String.valueOf(model.getValueAt(i, 1));
	        arrMaPDPSQL.add(myString1);
	    }
	}
    public boolean kiemTraTrungMaSQL(String maPDP) {
    	for (String element : arrMaPDPSQL) {
            if(element.equals(maPDP)) {
            	return false;
            }
        }
    	return true;
    }
    public boolean kiemTraTrungNgayTable(String ngay, String maPhong) {
    	int countDateTable = model.getRowCount();
    	for(int i = 0; i < countDateTable ; i++) {
    		if(maPhong.equals(String.valueOf(model.getValueAt(i, 4)))){
    			if(ngay.equals(String.valueOf(model.getValueAt(i, 5)))){		
        			return false;
        		}
    		}  		
    	}
    	return true;
    }
    public boolean kiemTraTrungNgay(String ngay, String maPhong, int viTri) {
    	System.out.println(ngay);
    	System.out.println(maPhong);
    	System.out.println(viTri);
    	int countDateTable = model.getRowCount();
    	for(int i = 0; i < countDateTable ; i++) {
    		if(viTri == i) {
    			continue;
    		}else {
    			if(maPhong.equals(String.valueOf(model.getValueAt(i, 4)))){
        			if(ngay.equals(String.valueOf(model.getValueAt(i, 5)))){
        				System.out.println("Trùng rồi");
            			return false;
            		}
        		} 
    		}
    		 		
    	}
    	return true;
    }
    public void lamMoi() {
		tfGia.setText("");
		tfLoaiPhong.setText("");
		tfPhong.setText("");
		dateNgayDat.setDate(null);
		txtTenKH.setText("");
		txtSDT.setText("");
    }
    public void themDonDatPhong() {
    	int kiemTraThem = 0;
    	int duoiMaPDP = 0;
    	for (int i = 0; i < model.getRowCount() + 1; i++) {  
    		duoiMaPDP++;
	        DecimalFormat dcm = new DecimalFormat("000");
	        String maPDP = "PDP" + String.valueOf(dcm.format(duoiMaPDP));
	        java.util.Date selectedDate = dateNgayDat.getDate();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = dateFormat.format(selectedDate);
        	if(kiemTraTrungMaTable(maPDP)  && kiemTraTrungNgayTable(dateString, maPhong)) {
        		int stt = model.getRowCount() + 1;
	            String tenKhachHang = txtTenKH.getText();
	            String sdt = txtSDT.getText();
	            String[] rowData1 = {String.valueOf(stt), maPDP, tenKhachHang, sdt, maPhong, dateString, String.valueOf(cmbGio.getSelectedItem() + ":" + String.valueOf(cmbPhut.getSelectedItem()))};	            
	            model.addRow(rowData1);
	            countMaTable = model.getRowCount();
	        	capNhapMangMaPDPTable();
	        	kiemTraThem = 1;
	        	kiemTraChonThem = 0;
	    		tfGia.setText("");
	    		tfLoaiPhong.setText("");
	    		tfPhong.setText("");
	    		dateNgayDat.setDate(null);
	        	for (JPanel panel : pnlPhongListTrong) {
	                panel.setBackground(Color.decode("#1995AD"));
	                
	            }
	        	for (JPanel panel : pnlPhongListDangSuDung) {
	                panel.setBackground(Color.decode("#00FF00"));
	                
	            }
	            break;
        	}
		}
    	if(kiemTraThem == 1) {
    	    int columnIndexToColor = model.getRowCount() - 1;
    	    arrViTriChuaLuu.add(columnIndexToColor);
        	tblPhong.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
                @Override
                public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                    if (arrViTriChuaLuu.contains(row)) {
                        c.setBackground(Color.decode("#1995AD"));
                    } else {
                        c.setBackground(table.getBackground());
                    }
                    return c;
                }
            });
        	JOptionPane.showMessageDialog(
                    null,
                    "Thêm đơn thành công",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
        	arrSdtDoiLuu.add(txtSDT.getText());
        }else {
        	JOptionPane.showMessageDialog(pnDatPhong.this, "Phòng bạn chọn đã tồn tại lịch đặt", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void xoaDonDatPhong() {
    	if(model.getRowCount() == daoPhieuDatPhong.getDsPhieuDatPhongSize()) {
			int selectedRow = tblPhong.getSelectedRow();
			if (selectedRow != -1) {
				int result = JOptionPane.showConfirmDialog(null,
		                "Bạn có chắc chắn muốn xóa?",
		                "Xác nhận xóa",
		                JOptionPane.YES_NO_OPTION);

		        if (result == JOptionPane.YES_OPTION) {
		        	String ma = (String) model.getValueAt(selectedRow, 1);
			        daoPhieuDatPhong.deletePhieuDatPhong(ma);
			        DocDuLieuDataBaseVaoTable();
			        countMaTable = model.getRowCount();
			        capNhapMangMaPDPTable();
			        countMaSQL = model.getRowCount() - 1;
			        capNhapMangMaPDPSQL();
			        kiemTraChonThem = 0;
			        lamMoi();
		        } else {
		        	JOptionPane.showMessageDialog(
		                    null,
		                    "Bạn đã hủy xóa.",
		                    "Hủy xóa",
		                    JOptionPane.INFORMATION_MESSAGE
		            );
		        }	        
		    }else {
		    	JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng chọn đơn đặt phòng trước khi xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
		    }
		}else {
			JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng lưu đơn đặt phòng trước khi thực hiện chức năng xóa", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
    }
	public void luuDonDatPhong() {
		int kiemTraLuu = 0;
		for (int i = 0; i < model.getRowCount(); i++) {
		    String maPDP = String.valueOf(model.getValueAt(i, 1));
		    String ngayDatStr = String.valueOf(model.getValueAt(i, 5));
		    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		    String gioDatStr = String.valueOf(model.getValueAt(i, 6));
		    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
		    try {
		        java.sql.Date ngayDat = new java.sql.Date(dateFormat.parse(ngayDatStr).getTime());
		        java.sql.Time gioDat = new java.sql.Time(timeFormat.parse(gioDatStr).getTime());
		        
		        if(kiemTraTrungMaSQL(maPDP)) {
		        	KhachHang maKH = new KhachHang(daoKhachHang.getMaKhachHangTheoSDT(arrSdtDoiLuu.get(arrSdtDoiLuuSize++)));
			        Phong maP = new Phong(String.valueOf(model.getValueAt(i, 4)));
		        	PhieuDatPhong pdp = new PhieuDatPhong(maPDP, maKH, maP, ngayDat, gioDat);
		        	daoPhieuDatPhong.addPhieuDatPhong(pdp);
		        	capNhapMangMaPDPSQL();
		        	kiemTraLuu = 1;
		        }
	
		    } catch (ParseException e1) {
		        // Xử lý ngoại lệ nếu có lỗi khi phân tích chuỗi
		    }
		}
		if(kiemTraLuu == 0) {
			JOptionPane.showMessageDialog(
		            null,
		            "Không có đơn đặt phòng mới cần lưu",
		            "Thông báo",
		            JOptionPane.INFORMATION_MESSAGE
		    );
		}else {
			for (JPanel panel : pnlPhongListTrong) {
	            panel.setBackground(Color.decode("#1995AD"));
	            
	        }
	    	for (JPanel panel : pnlPhongListDangSuDung) {
	            panel.setBackground(Color.decode("#00FF00"));
	            
	        }
			JOptionPane.showMessageDialog(
		            null,
		            "Lưu thành công!",
		            "Thông báo",
		            JOptionPane.INFORMATION_MESSAGE
		    );
			DocDuLieuDataBaseVaoTable();
			arrViTriChuaLuu.clear();
			arrSdtDoiLuu.clear();
			arrSdtDoiLuuSize = 0;
			lamMoi();
			kiemTraChonThem = 0;
		}
	}
	public void suaDonDatPhong() {
		int kiemTraSua = 0;
		int selectedRow = tblPhong.getSelectedRow();
		String maPDP = String.valueOf(model.getValueAt(selectedRow, 1));

		java.util.Date selectedDate = dateNgayDat.getDate();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String ngayString = dateFormat.format(selectedDate);
		
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String gioDatStr = String.valueOf(cmbGio.getSelectedItem() + ":" + String.valueOf(cmbPhut.getSelectedItem()));
        String maPh = String.valueOf(model.getValueAt(selectedRow, 4));
        String dateString = dateFormat.format(selectedDate);
		try {
			java.sql.Date ngayDat = new java.sql.Date(dateFormat.parse(ngayString).getTime());
            java.sql.Time gioDat = new java.sql.Time(timeFormat.parse(gioDatStr).getTime());
            if(kiemTraTrungNgay(dateString, maPh, selectedRow) == true) {
        		Date ngayHienTai = new Date();
        		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        	    try {
        			ngayHienTai = sdf.parse(sdf.format(ngayHienTai));
        		} catch (ParseException e1) {
        			e1.printStackTrace();
        		}
        		Date ngayTuJDateChooser = (Date) dateNgayDat.getDate();
        		Calendar cal = Calendar.getInstance();
        		Object selectedHour = cmbGio.getSelectedItem();
        		int gioDatInt = Integer.parseInt(selectedHour.toString());
        		Object selectedPhut = cmbPhut.getSelectedItem();
        		int phutDatInt = Integer.parseInt(selectedPhut.toString());
                int gioHienTai = cal.get(Calendar.HOUR_OF_DAY);
                int phutHienTai = cal.get(Calendar.MINUTE);
            	if(dateNgayDat.getDate() == null ) {
					JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng chọn lịch đặt phòng trước khi sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
				else if(ngayTuJDateChooser.before(ngayHienTai)) {
					JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng chọn lịch sau thời gian hiện tại trước khi sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
				}
				else if(ngayTuJDateChooser.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(ngayHienTai.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())) {
					if(gioDatInt < gioHienTai) {
						JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng không chọn giờ trước thời điểm hiện tại trước khi sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
					}
					else if(gioDatInt > gioHienTai) {
						daoPhieuDatPhong.updatePhieuDatPhong(ngayDat, gioDat, maPDP);
						DocDuLieuDataBaseVaoTable();
						kiemTraSua = 1;
						kiemTraChonThem = 0;
						lamMoi();
					}
					else{
						if(phutDatInt <= phutHienTai) {
							JOptionPane.showMessageDialog(pnDatPhong.this, "Vui lòng chọn phút sau thời điểm hiện tại trước khi sửa", "Lỗi", JOptionPane.ERROR_MESSAGE);
						}
						else {
							daoPhieuDatPhong.updatePhieuDatPhong(ngayDat, gioDat, maPDP);
							DocDuLieuDataBaseVaoTable();
							kiemTraSua = 1;
							kiemTraChonThem = 0;
							lamMoi();
						}
					}
				}else {
					daoPhieuDatPhong.updatePhieuDatPhong(ngayDat, gioDat, maPDP);
					DocDuLieuDataBaseVaoTable();
					kiemTraSua = 1;
					kiemTraChonThem = 0;
					lamMoi();
				}
            }else {
            	JOptionPane.showMessageDialog(pnDatPhong.this, "Phòng bạn chọn thời gian này đã có người đặt", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if(kiemTraSua == 1) {
			JOptionPane.showMessageDialog(
                    null,
                    "Sửa thành công",
                    "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE
            );
		}
	}
}
	
