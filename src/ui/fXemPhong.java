package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.LoaiPhong_Dao;
import dao.Phong_Dao;
import dao.Regex;
import entity.LoaiPhong;
import entity.Phong;

public class fXemPhong extends JFrame implements ActionListener, MouseListener {
	private JTextField tfMaPhong, tfTenPhong;
	private JButton btnTimKiem, btnLamMoi;
	private JComboBox<String> cbTrangThai, cbLoaiPhong;
	
	private Image imgBG = new ImageIcon(pnTimKiemPhong.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAdd = new ImageIcon(pnTimKiemPhong.class.getResource("/image/add1.png"));
	private ImageIcon iconDelete = new ImageIcon(pnTimKiemPhong.class.getResource("/image/huy1.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnTimKiemPhong.class.getResource("/image/update.png"));
	private ImageIcon iconReload = new ImageIcon(pnTimKiemPhong.class.getResource("/image/reload.png"));
	private ImageIcon iconReloadTable = new ImageIcon(pnTimKiemPhong.class.getResource("/image/reloadtable.png"));
	private ImageIcon iconFind = new ImageIcon(pnTimKiemPhong.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(pnTimKiemPhong.class.getResource("/image/Rewind.png"));
	private ImageIcon iconLast = new ImageIcon(pnTimKiemPhong.class.getResource("/image/Forward.png"));
	private ImageIcon iconBack = new ImageIcon(pnTimKiemPhong.class.getResource("/image/back1.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	
	private JTable tblPhong;
	private DefaultTableModel model;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	
	
	private Phong_Dao daoPhong;
	private LoaiPhong_Dao daoLoaiPhong;
	private ArrayList<Phong> phong;
	private ArrayList<LoaiPhong> loaiPhong;
	private Regex regex;

	/**
	 * Create the frame.
	 */
	public fXemPhong() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		setTitle("Xem phòng");
		setResizable(false);
		
		setBounds(160, 0, 1559, 1040);
		daoPhong = new Phong_Dao();
		daoLoaiPhong = new LoaiPhong_Dao();
		regex = new Regex();
		getContentPane().setLayout(null);
		JPanel pnMain = new JPanel();
		pnMain.setBounds(0, 0, 1599, 1040);
		getContentPane().add(pnMain);
		pnMain.setLayout(null);
		
		JLabel lblTitle = new JLabel("TÌM KIẾM PHÒNG");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(615, 20, 276, 36);
		pnMain.add(lblTitle);
		
		JLabel lbTimTheoMa = new JLabel("Tìm theo mã phòng:");
		lbTimTheoMa.setForeground(Color.BLACK);
		lbTimTheoMa.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbTimTheoMa.setBounds(220, 130, 158, 30);
		pnMain.add(lbTimTheoMa);
		
		tfMaPhong = new JTextField();
		tfMaPhong.setToolTipText("Tìm kiếm theo mã phòng");
		tfMaPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfMaPhong.setBounds(418, 130, 175, 30);
		pnMain.add(tfMaPhong);
		tfMaPhong.setColumns(10);
		
		JLabel lblTimTheoTen = new JLabel("Tìm theo tên phòng:");
		lblTimTheoTen.setForeground(Color.BLACK);
		lblTimTheoTen.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTimTheoTen.setBounds(220, 185, 158, 30);
		pnMain.add(lblTimTheoTen);

		tfTenPhong = new JTextField();
		tfTenPhong.setToolTipText("Tìm kiếm theo tên phòng");
		tfTenPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfTenPhong.setBounds(418, 185, 175, 30);
		pnMain.add(tfTenPhong);
		tfTenPhong.setColumns(10);
		
		JLabel lblTimTheoTT = new JLabel("Tìm theo trạng thái:");
		lblTimTheoTT.setForeground(Color.BLACK);
		lblTimTheoTT.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTimTheoTT.setBounds(900, 130, 158, 30);
		pnMain.add(lblTimTheoTT);
		
		cbTrangThai = new JComboBox();
		cbTrangThai.setToolTipText("Tìm kiếm theo trạng thái phòng");
		cbTrangThai.addItem("");
		cbTrangThai.addItem("Trống");
		cbTrangThai.addItem("Đang sử dụng");
		cbTrangThai.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbTrangThai.setBounds(1100, 130, 193, 30);
		pnMain.add(cbTrangThai);
		
		JLabel lblLoaiPhong = new JLabel("Tìm theo loại phòng:");
		lblLoaiPhong.setForeground(Color.BLACK);
		lblLoaiPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLoaiPhong.setBounds(900, 185, 158, 30);
		pnMain.add(lblLoaiPhong);
		
		cbLoaiPhong = new JComboBox();
		cbLoaiPhong.setToolTipText("Tìm kiếm theo loại phòng");
		cbLoaiPhong.addItem("");
		cbLoaiPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbLoaiPhong.setBounds(1100, 185, 193, 30);
		pnMain.add(cbLoaiPhong);
		
		btnTimKiem = new btnMyButton(130, 40, "Tìm kiếm", new Dimension(60, 23), iconFind.getImage(), new Dimension(25,25), gra);
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTimKiem.setBounds(220, 289, 137, 36);
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
		
		btnLamMoi = new btnMyButton(130, 40, "Làm mới", new Dimension(60, 23), iconReload.getImage(), new Dimension(25,25), gra);
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLamMoi.setBounds(466, 289, 137, 36);
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
		setBorderTitle(pnlTable, "Danh sách phòng");
		pnlTable.setOpaque(false);
		
		String[] cols = {"STT", "Mã phòng", "Tên phòng", "Tình trạng", "Giá phòng", "Loại phòng"};
		model = new DefaultTableModel(cols, 0);
		tblPhong = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		setCustomTable(tblPhong);
		tblPhong.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblPhong);
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
	    
	    JButton btnQuayLai = new btnMyButton(130, 40, "Quay lại", new Dimension(60, 23), iconBack.getImage(), new Dimension(25,25), gra);
	    btnQuayLai.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    		fDangNhap dangNhap = new fDangNhap();
	    		dangNhap.setVisible(true);
	    		dispose();
	    	}
	    });
	    btnQuayLai.setBounds(1366, 30, 130, 40);
	    pnlTitle.add(btnQuayLai);
	    
	    JPanel pnlTimKiem = new JPanel();
	    pnlTimKiem.setBounds(98, 111, 1350, 132);
	    pnlTimKiem.setLayout(null);
	    setBorderTitle(pnlTimKiem, "Tìm kiếm theo tiêu chí");
	    pnMain.add(pnlTimKiem);
	    
	    loaiPhong = daoLoaiPhong.getAllLoaiPhong();
	    for(LoaiPhong lp : loaiPhong) {
			cbLoaiPhong.addItem(lp.getTenLoaiP());
		}
	    
	    btnTimKiem.addActionListener(this);
	    btnLamMoi.addActionListener(this);
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
	
	public void DocDuLieuDataBaseVaoTable() {
		List<Phong> list = daoPhong.loadDSPhongFromDatabase();
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		model.setRowCount(0);
		for(Phong p : list) {
			int stt = model.getRowCount() + 1;
			LoaiPhong loaiP = daoLoaiPhong.getLoaiPhongTheoMa(p.getLoaiPhong().getMaLoaiP());
			model.addRow(new Object[] { stt, p.getMaPhong(), p.getTenPhong(), p.getTinhTrangPhong(), decimalFormat.format(p.getGiaPhong()), loaiP.getTenLoaiP()});
		}
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
		if (o.equals(btnTimKiem)) {
		    btnTimKiem();
		}
		
		else if (o.equals(btnLamMoi)) {
			btnLamMoi();
		}
	}
	
	
	public void btnLamMoi() {
		tfMaPhong.setText("");
		tfTenPhong.setText("");
		cbTrangThai.setSelectedIndex(0);
		cbLoaiPhong.setSelectedIndex(0);
		DocDuLieuDataBaseVaoTable();
	}
	
	public void btnTimKiem() {
	    String maPhong = tfMaPhong.getText().trim();
	    String tenPhong = tfTenPhong.getText().trim();
	    String tinhTrang = cbTrangThai.getSelectedItem().toString();
	    String maLoaiPhong = daoLoaiPhong.getMaLoaiPTheoTen(cbLoaiPhong.getSelectedItem().toString());
	    List<Phong> list = new ArrayList<>();

	    if (maPhong.isEmpty() && tenPhong.isEmpty() && maLoaiPhong.isEmpty() && tinhTrang.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập ít nhất một trường thông tin để tìm kiếm!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (!maPhong.isEmpty()) {
	        if (!regex.regexTimKiemMaPhong(tfMaPhong)) {
	            return;
	        }
	        list = daoPhong.getLocMaPhong(maPhong);
	    } else if (!tenPhong.isEmpty()) {
	        if (!regex.regexTimTenPhong(tfTenPhong)) {
	            return;
	        }
	        list = daoPhong.getLocTenPhong(tenPhong);
	    } else if (!maLoaiPhong.isEmpty()) {
	        list = daoPhong.getPhongTheoLoai(maLoaiPhong);
	    } else if (!tinhTrang.isEmpty()) {
	        list = daoPhong.getLocTinhTrang(tinhTrang);
	    }

	    if (!list.isEmpty()) {
	        model.setRowCount(0);
	        for (Phong p : list) {
	            int stt = model.getRowCount() + 1;
	            LoaiPhong loaiP = daoLoaiPhong.getLoaiPhongTheoMa(p.getLoaiPhong().getMaLoaiP());
	            model.addRow(new Object[]{stt, p.getMaPhong(), p.getTenPhong(), p.getTinhTrangPhong(), p.getGiaPhong() + "", loaiP.getTenLoaiP()});
	        }
	        JOptionPane.showMessageDialog(this, "Đã tìm thấy " + list.size() + " kết quả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả!");
	    }
	}
}
