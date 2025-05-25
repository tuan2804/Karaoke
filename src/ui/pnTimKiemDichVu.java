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
import dao.DichVu_Dao;
import dao.LoaiDichVu_Dao;
import dao.LoaiPhong_Dao;
import dao.Phong_Dao;
import dao.Regex;
import entity.DichVu;
import entity.LoaiDichVu;
import entity.LoaiPhong;
import entity.Phong;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class pnTimKiemDichVu extends JPanel implements ActionListener, MouseListener{
	private JTextField txtMaDichVu, txtTenDichVu;
	private JButton btnTim, btnLamMoi, btnTaiLai;
	private JComboBox<String> cmbTimTheoLoai;
	
	private Image imgBG = new ImageIcon(pnTimKiemDichVu.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAdd = new ImageIcon(pnTimKiemDichVu.class.getResource("/image/add1.png"));
	private ImageIcon iconDelete = new ImageIcon(pnTimKiemDichVu.class.getResource("/image/huy1.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnTimKiemDichVu.class.getResource("/image/update.png"));
	private ImageIcon iconReload = new ImageIcon(pnTimKiemDichVu.class.getResource("/image/reload.png"));
	private ImageIcon iconReloadTable = new ImageIcon(pnTimKiemDichVu.class.getResource("/image/reloadtable.png"));
	private ImageIcon iconFind = new ImageIcon(pnTimKiemDichVu.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(pnTimKiemDichVu.class.getResource("/image/Rewind.png"));
	private ImageIcon iconLast = new ImageIcon(pnTimKiemDichVu.class.getResource("/image/Forward.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	
	private JTable tblPhong;
	private DefaultTableModel model;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	
	
	private ArrayList<LoaiDichVu> loaiDV;
	private LoaiDichVu_Dao daoLoaiDichVu;
	private DichVu_Dao daoDichVu;
	private Regex regex;
	/**
	 * Create the panel.
	 */
	public pnTimKiemDichVu() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		daoDichVu = new DichVu_Dao();
		daoLoaiDichVu = new LoaiDichVu_Dao();
		regex = new Regex();
		setLayout(null);
		JPanel pnMain = new JPanel();
		pnMain.setBounds(0, 0, 1559, 1040);
		add(pnMain);
		pnMain.setLayout(null);
		
		JLabel lblTitle = new JLabel("TÌM KIẾM DỊCH VỤ");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(615, 20, 276, 36);
		pnMain.add(lblTitle);
		
		JLabel lbTimTheoMa = new JLabel("Tìm theo mã dịch vụ:");
		lbTimTheoMa.setForeground(Color.BLACK);
		lbTimTheoMa.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbTimTheoMa.setBounds(220, 130, 158, 30);
		pnMain.add(lbTimTheoMa);
		
		txtMaDichVu = new JTextField();
		txtMaDichVu.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtMaDichVu.setBounds(418, 130, 175, 30);
		pnMain.add(txtMaDichVu);
		txtMaDichVu.setColumns(10);
		
		JLabel lblTimTheoTen = new JLabel("Tìm theo tên dịch vụ:");
		lblTimTheoTen.setForeground(Color.BLACK);
		lblTimTheoTen.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTimTheoTen.setBounds(220, 185, 158, 30);
		pnMain.add(lblTimTheoTen);

		txtTenDichVu = new JTextField();
		txtTenDichVu.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtTenDichVu.setBounds(418, 185, 175, 30);
		pnMain.add(txtTenDichVu);
		txtTenDichVu.setColumns(10);
		
		btnTim = new btnMyButton(130, 40, "Tìm kiếm", new Dimension(60, 23), iconFind.getImage(), new Dimension(25,25), gra);
		btnTim.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTim.setToolTipText("Tìm kiếm dịch vụ");
		btnTim.setBounds(220, 289, 137, 36);
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
		pnlTable.setBounds(98, 380, 1350, 607);
		setBorderTitle(pnlTable, "Danh sách dịch vụ");
		pnlTable.setOpaque(false);
		
		String[] cols = {"STT", "Mã dịch vụ", "Tên dịch vụ", "Giá bán", "Đơn vị tính", "Loại dịch vụ", "Số lượng tồn"};
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
		scrTable.setBounds(10, 25, 1330, 571);
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
	    pnlTimKiem.setBounds(98, 111, 1350, 132);
	    pnlTimKiem.setLayout(null);
	    setBorderTitle(pnlTimKiem, "Tìm kiếm theo tiêu chí");
	    pnMain.add(pnlTimKiem);
	    
	    cmbTimTheoLoai = new JComboBox();
	    cmbTimTheoLoai.setBounds(1069, 20, 193, 30);
	    pnlTimKiem.add(cmbTimTheoLoai);
	    cmbTimTheoLoai.setFont(new Font("Tahoma", Font.BOLD, 15));
	    
	    JLabel lblTimTheoLoai = new JLabel("Tìm theo loại dịch vụ:");
	    lblTimTheoLoai.setBounds(881, 20, 158, 30);
	    pnlTimKiem.add(lblTimTheoLoai);
	    lblTimTheoLoai.setForeground(Color.BLACK);
	    lblTimTheoLoai.setFont(new Font("Tahoma", Font.BOLD, 15));
	    loaiDV = daoLoaiDichVu.getAllLoaiDichVu();
	    for(LoaiDichVu ldv : loaiDV) {
			cmbTimTheoLoai.addItem(ldv.getTenLoaiDV());
		}
	   
	    btnLamMoi.addActionListener(this);
	    btnTim.addActionListener(this);
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
			btnTimKiem();
		    
		}
		else if (o.equals(btnLamMoi)) {
			lamMoi();
		}
		
	}
	
	public void DocDuLieuDataBaseVaoTable() {
		List<DichVu> list = daoDichVu.loadDSDichVuFromDatabase();
		model.setRowCount(0);
		for(DichVu dv : list) {
			int stt = model.getRowCount() + 1;
			LoaiDichVu loaiDV = daoLoaiDichVu.getLoaiDichVuTheoMa(dv.getLoaiDV().getMaLoaiDV());
			model.addRow(new Object[] { stt, dv.getMaDichVu(), dv.getTenDichVu(), dv.getGiaBan()+"", dv.getDonViTinh(), loaiDV.getTenLoaiDV(), dv.getSoLuongTon()});
		}
	}
	
	public void lamMoi() {
		txtMaDichVu.setText("");
		txtTenDichVu.setText("");
		DocDuLieuDataBaseVaoTable();
	}
	
	public void btnTimKiem() {
	    String maDichVu = txtMaDichVu.getText().trim();
	    String tenDichVu = txtTenDichVu.getText().trim();
	    String maLoaiDichVu = daoLoaiDichVu.getMaLoaiDVTheoTen(cmbTimTheoLoai.getSelectedItem().toString());
	    List<DichVu> list = new ArrayList<>();

	    if (maDichVu.isEmpty() && tenDichVu.isEmpty() && maLoaiDichVu.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Vui lòng nhập ít nhất một trường thông tin để tìm kiếm!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (!maDichVu.isEmpty()) {
	        if (!regex.regexTimMaDichVu(txtMaDichVu)) {
	            return;
	        }
	        list = daoDichVu.getLocMaDichVu(maDichVu);
	    } else if (!tenDichVu.isEmpty()) {
	        list = daoDichVu.getLocTenDichVu(tenDichVu);
	    } else if (!maLoaiDichVu.isEmpty()) {
	        list = daoDichVu.getDichVuTheoLoai(maLoaiDichVu);
	    }

	    if (!list.isEmpty()) {
	        model.setRowCount(0);
	        for (DichVu dv : list) {
	            int stt = model.getRowCount() + 1;
	            LoaiDichVu loaiDV = daoLoaiDichVu.getLoaiDichVuTheoMa(dv.getLoaiDV().getMaLoaiDV());
	            model.addRow(new Object[] { stt, dv.getMaDichVu(), dv.getTenDichVu(), dv.getGiaBan()+"", dv.getDonViTinh(), loaiDV.getTenLoaiDV(), dv.getSoLuongTon()});
	        }
	        JOptionPane.showMessageDialog(this, "Đã tìm thấy " + list.size() + " kết quả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    } else {
	        JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả!");
	    }
	}
	
}
