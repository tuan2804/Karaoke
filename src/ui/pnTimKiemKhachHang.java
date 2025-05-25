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

import connectDB.ConnectDB;
import dao.KhachHang_Dao;
import dao.LoaiPhong_Dao;
import dao.Phong_Dao;
import dao.Regex;
import entity.KhachHang;
import entity.LoaiPhong;
import entity.Phong;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class pnTimKiemKhachHang extends JPanel implements ActionListener, MouseListener{
	private JButton btnTim, btnLamMoi, btnTaiLai;
	
	private Image imgBG = new ImageIcon(pnTimKiemKhachHang.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAdd = new ImageIcon(pnTimKiemKhachHang.class.getResource("/image/add1.png"));
	private ImageIcon iconDelete = new ImageIcon(pnTimKiemKhachHang.class.getResource("/image/huy1.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnTimKiemKhachHang.class.getResource("/image/update.png"));
	private ImageIcon iconReload = new ImageIcon(pnTimKiemKhachHang.class.getResource("/image/reload.png"));
	private ImageIcon iconReloadTable = new ImageIcon(pnTimKiemKhachHang.class.getResource("/image/reloadtable.png"));
	private ImageIcon iconFind = new ImageIcon(pnTimKiemKhachHang.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(pnTimKiemKhachHang.class.getResource("/image/Rewind.png"));
	private ImageIcon iconLast = new ImageIcon(pnTimKiemKhachHang.class.getResource("/image/Forward.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	
	private JTable tblKhachHang;
	private DefaultTableModel model;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	
	private JTextField txtMaKH, txtCCCD, txtTenKH, txtSDT;
	private JComboBox cmbGioiTinh;
	
	private KhachHang_Dao daoKhachHang;
	private Regex regex;
	
	/**
	 * Create the panel.
	 */
	public pnTimKiemKhachHang() {
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
		
		JLabel lblTitle = new JLabel("TÌM KIẾM KHÁCH HÀNG");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(600, 20, 323, 36);
		pnMain.add(lblTitle);
		
		btnTim = new btnMyButton(130, 40, "Tìm kiếm", new Dimension(60, 23), iconFind.getImage(), new Dimension(25,25), gra);
		btnTim.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTim.setBounds(220, 320, 137, 36);
		btnTim.setToolTipText("Tìm kiếm khách hàng");
		pnMain.add(btnTim);
		AbstractAction actionTimKiem = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnTim.doClick(); 
            }
        };
		KeyStroke keyStrokeTimKiem = KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.ALT_DOWN_MASK);
		btnTim.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeTimKiem, "shortcut");
		
		btnLamMoi = new btnMyButton(130, 40, "Làm mới", new Dimension(60, 23), iconReload.getImage(), new Dimension(25,25), gra);
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLamMoi.setBounds(466, 320, 137, 36);
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
		pnlTable.setBounds(98, 380, 1350, 612);
		setBorderTitle(pnlTable, "Danh sách khách hàng");
		pnlTable.setOpaque(false);
		
		String[] cols = {"STT", "Mã khách hàng", "Tên khách hàng", "Giới tính", "Số điện thoại", "CCCD", "Ngày sinh"};
		model = new DefaultTableModel(cols, 0);
		tblKhachHang = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		setCustomTable(tblKhachHang);
		tblKhachHang.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblKhachHang);
		scrTable.setBounds(10, 25, 1330, 576);
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
	    pnlTimKiem.setBounds(98, 113, 1350, 185);
	    pnlTimKiem.setLayout(null);
	    setBorderTitle(pnlTimKiem, "Tìm kiếm theo tiêu chí");
	    pnMain.add(pnlTimKiem);
	    
	    JLabel lblMaKH = new JLabel("Mã khách hàng:");
	    lblMaKH.setFont(new Font("Tahoma", Font.BOLD, 15));
	    lblMaKH.setBounds(200, 30, 124, 25);
	    pnlTimKiem.add(lblMaKH);
	    
	    txtMaKH = new JTextField();
	    txtMaKH.setFont(new Font("Tahoma", Font.BOLD, 15));
	    txtMaKH.setBounds(341, 30, 177, 25);
	    pnlTimKiem.add(txtMaKH);
	    txtMaKH.setColumns(10);
	    
	    JLabel lblGioiTinh = new JLabel("Giới tính:");
	    lblGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
	    lblGioiTinh.setBounds(200, 81, 124, 25);
	    pnlTimKiem.add(lblGioiTinh);
	    
	    cmbGioiTinh = new JComboBox<>();
	    cmbGioiTinh.addItem("Nam");
	    cmbGioiTinh.addItem("Nữ");
	    cmbGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
	    cmbGioiTinh.setBounds(341, 81, 177, 25);
	    pnlTimKiem.add(cmbGioiTinh);
	    
	    JLabel lblNewLabel = new JLabel("CCCD:");
	    lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
	    lblNewLabel.setBounds(200, 130, 124, 25);
	    pnlTimKiem.add(lblNewLabel);
	    
	    txtCCCD = new JTextField();
	    txtCCCD.setFont(new Font("Tahoma", Font.BOLD, 15));
	    txtCCCD.setBounds(341, 130, 177, 25);
	    pnlTimKiem.add(txtCCCD);
	    txtCCCD.setColumns(10);
	    
	    JLabel lblTenKH = new JLabel("Tên khách hàng:");
	    lblTenKH.setFont(new Font("Tahoma", Font.BOLD, 15));
	    lblTenKH.setBounds(800, 30, 140, 25);
	    pnlTimKiem.add(lblTenKH);
	    
	    txtTenKH = new JTextField();
	    txtTenKH.setFont(new Font("Tahoma", Font.BOLD, 15));
	    txtTenKH.setBounds(960, 30, 177, 25);
	    pnlTimKiem.add(txtTenKH);
	    txtTenKH.setColumns(10);
	    
	    txtSDT = new JTextField();
	    txtSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
	    txtSDT.setBounds(960, 81, 177, 25);
	    pnlTimKiem.add(txtSDT);
	    txtSDT.setColumns(10);
	    
	    JLabel lblSDT = new JLabel("Số điện thoại:");
	    lblSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
	    lblSDT.setBounds(800, 81, 140, 25);
	    pnlTimKiem.add(lblSDT);
	    
	   
	    
	    btnTim.addActionListener(this);
	    btnLamMoi.addActionListener(this);
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
		
		if (o.equals(btnTim)) {
			btnTimKhachHang();
		}
		
		else if (o.equals(btnLamMoi)) {
			lamMoi();
			DocDuLieuDataBaseVaoTable();
		}
		
		
	}
	
	public void lamMoi() {
		txtMaKH.setText("");
		txtTenKH.setText("");
		txtSDT.setText("");
		txtCCCD.setText("");
		txtMaKH.requestDefaultFocus();
	}
	
	public void btnTimKhachHang() {
	    String maKhachHang = txtMaKH.getText().trim();
	    String tenKhachHang = txtTenKH.getText().trim();
	    String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
	    String sdt = txtSDT.getText().trim();
	    String cCCD = txtCCCD.getText().trim();
	    List<KhachHang> list = new ArrayList<>();

	    if (maKhachHang.isEmpty() && tenKhachHang.isEmpty() && gioiTinh.isEmpty() && sdt.isEmpty() && cCCD.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập ít nhất một trường thông tin để tìm kiếm!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (!maKhachHang.isEmpty()) {
	        if (!regex.regexTimKiemMaKH(txtMaKH)) {
	            return;
	        }
	        list = daoKhachHang.getLocMaKhachHang(maKhachHang);
	    } else if (!tenKhachHang.isEmpty()) {
	        if (!regex.regexTimNV(txtTenKH)) {
	            return;
	        }
	        list = daoKhachHang.getLocTenKhachHang(tenKhachHang);
	    } else if (!sdt.isEmpty()) {
	        if (!regex.regexSDT(txtSDT)) {
	            return;
	        }
	        list = daoKhachHang.getLocSDT(sdt);
	    } else if (!cCCD.isEmpty()) {
	        if (!regex.regexCCCD(txtCCCD)) {
	            return;
	        }
	        list = daoKhachHang.getLocCCCD(cCCD);
	    } else if (!gioiTinh.isEmpty()) {
	        list = daoKhachHang.getLocGioiTinh(gioiTinh);
	    }

	    if (!list.isEmpty()) {
	        model.setRowCount(0);
	        for (KhachHang kh : list) {
	            int stt = model.getRowCount() + 1;
	            model.addRow(new Object[]{stt, kh.getMaKhachHang(), kh.getHoTen(), kh.getGioiTinh(), kh.getSoDT(), kh.getcCCD(), kh.getNgaySinh()});
	        }
	        JOptionPane.showMessageDialog(this, "Đã tìm thấy " + list.size() + " kết quả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    }
	}
	
	public void DocDuLieuDataBaseVaoTable() {
		List<KhachHang> list = daoKhachHang.loadKhachHangFromDatabase();
		model.setRowCount(0);
		for(KhachHang kh : list) {
			int stt = model.getRowCount() + 1;
			model.addRow(new Object[] { stt, kh.getMaKhachHang(), kh.getHoTen(), kh.getGioiTinh(), kh.getSoDT(), kh.getcCCD(), kh.getNgaySinh()});
		}
	}
}
