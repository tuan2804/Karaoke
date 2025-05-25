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
import dao.Regex;
import entity.DichVu;
import entity.LoaiDichVu;
import entity.LoaiPhong;
import entity.Phong;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class pnDichVu extends JPanel implements ActionListener, MouseListener{
	
	
	private Image imgBG = new ImageIcon(pnDichVu.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAdd = new ImageIcon(pnDichVu.class.getResource("/image/add1.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnDichVu.class.getResource("/image/update.png"));
	private ImageIcon iconReload = new ImageIcon(pnDichVu.class.getResource("/image/reload.png"));
	private ImageIcon iconFind = new ImageIcon(pnDichVu.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(pnDichVu.class.getResource("/image/Rewind.png"));
	private ImageIcon iconLast = new ImageIcon(pnDichVu.class.getResource("/image/Forward.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	
	
	private JTextField txtMaDV, txtGiaBan, txtTenDV, txtSoLuongTon, txtDonViTinh;
	private JButton btnThem, btnSua, btnLamMoi;
	private JComboBox<String> cbLoaiDV;
	private JTable tblDichVu;
	private DefaultTableModel model;

	private ArrayList<LoaiDichVu> loaiDV;
	private LoaiDichVu_Dao daoLoaiDichVu;
	private DichVu_Dao daoDichVu;
	private Regex regex;
	/**
	 * Create the panel.
	 */
	public pnDichVu() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		daoLoaiDichVu = new LoaiDichVu_Dao();
		daoDichVu = new DichVu_Dao();
		regex = new Regex();
		setLayout(null);
		JPanel pnMain = new JPanel();
		pnMain.setBounds(0, 0, 1559, 1040);
		add(pnMain);
		pnMain.setLayout(null);
		
		JLabel lblTitle = new JLabel("QUẢN LÝ DỊCH VỤ");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(615, 20, 294, 36);
		pnMain.add(lblTitle);
		
		JLabel lblMaDV = new JLabel("Mã dịch vụ:");
		lblMaDV.setForeground(Color.BLACK);
		lblMaDV.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMaDV.setBounds(299, 110, 109, 30);
		pnMain.add(lblMaDV);
		
		txtMaDV = new JTextField();
		txtMaDV.setText(daoDichVu.getMaDichVu());
		txtMaDV.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtMaDV.setBounds(418, 110, 175, 30);
		pnMain.add(txtMaDV);
		txtMaDV.setColumns(10);
		
		JLabel lblGiaBan = new JLabel("Giá bán:");
		lblGiaBan.setForeground(Color.BLACK);
		lblGiaBan.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGiaBan.setBounds(299, 170, 80, 30);
		pnMain.add(lblGiaBan);
		
		txtGiaBan = new JTextField();
		txtGiaBan.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtGiaBan.setBounds(418, 170, 175, 30);
		pnMain.add(txtGiaBan);
		txtGiaBan.setColumns(10);
		
		JLabel lblSoLT = new JLabel("Số lượng tồn:");
		lblSoLT.setForeground(Color.BLACK);
		lblSoLT.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSoLT.setBounds(299, 230, 109, 30);
		pnMain.add(lblSoLT);
		
		txtSoLuongTon = new JTextField();
		txtSoLuongTon.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtSoLuongTon.setBounds(418, 230, 175, 30);
		pnMain.add(txtSoLuongTon);
		txtSoLuongTon.setColumns(10);
		
		JLabel lblTenDV = new JLabel("Tên dịch vụ:");
		lblTenDV.setForeground(Color.BLACK);
		lblTenDV.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTenDV.setBounds(899, 110, 92, 30);
		pnMain.add(lblTenDV);
		
		txtTenDV = new JTextField();
		txtTenDV.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtTenDV.setBounds(1015, 110, 175, 30);
		pnMain.add(txtTenDV);
		txtTenDV.setColumns(10);
		
		JLabel lblLoaiDV = new JLabel("Loại dịch vụ:");
		lblLoaiDV.setForeground(Color.BLACK);
		lblLoaiDV.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLoaiDV.setBounds(899, 230, 106, 30);
		pnMain.add(lblLoaiDV);
		
		cbLoaiDV = new JComboBox();
		cbLoaiDV.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbLoaiDV.setBounds(1015, 230, 175, 30);
		pnMain.add(cbLoaiDV);
		
		btnThem = new btnMyButton(105, 40, "Thêm", new Dimension(50, 23), iconAdd.getImage(), new Dimension(25,25), gra);
		btnThem.setToolTipText("Thêm dịch vụ");
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnThem.setBounds(400, 300, 137, 36);
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
		btnSua.setToolTipText("Cập nhập dịch vụ");
		btnSua.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSua.setBounds(700, 300, 137, 36);
		pnMain.add(btnSua);
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
		btnLamMoi.setToolTipText("Làm mới ");
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLamMoi.setBounds(1000, 300, 137, 36);
		pnMain.add(btnLamMoi);
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
		pnlTable.setBounds(98, 390, 1350, 588);
		setBorderTitle(pnlTable, "Danh sách dịch vụ");
		pnlTable.setOpaque(false);
		String[] cols = { "STT", "Mã dịch vụ", "Tên dịch vụ", "Giá bán", "Đơn vị tính", "Loại dịch vụ", "Số lượng tồn"};
		model = new DefaultTableModel(cols, 0);
		tblDichVu = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		setCustomTable(tblDichVu);
		tblDichVu.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblDichVu);
		scrTable.setBounds(10, 25, 1330, 552);
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
	    
	    JLabel lblDonViTinh = new JLabel("Đơn vị tính:");
	    lblDonViTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
	    lblDonViTinh.setBounds(899, 170, 92, 30);
	    pnMain.add(lblDonViTinh);
	    
	    txtDonViTinh = new JTextField();
	    txtDonViTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
	    txtDonViTinh.setBounds(1015, 170, 175, 30);
	    pnMain.add(txtDonViTinh);
	    txtDonViTinh.setColumns(10);
	    
	    
		btnLamMoi.addActionListener(this);
		btnSua.addActionListener(this);
		btnThem.addActionListener(this);
		tblDichVu.addMouseListener(this);
	    loaiDV = daoLoaiDichVu.getAllLoaiDichVu();
	    for(LoaiDichVu ldv : loaiDV) {
			cbLoaiDV.addItem(ldv.getTenLoaiDV());
		}
	    DocDuLieuDataBaseVaoTable();
	    
	}
	
	/*
	 * tạo giao diện table
	 */
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
		Object o = e.getSource();
		if(o.equals(tblDichVu)) {
			int row = tblDichVu.getSelectedRow();
			txtMaDV.setText(model.getValueAt(row, 1).toString());
			txtTenDV.setText(model.getValueAt(row, 2).toString());
			txtGiaBan.setText(model.getValueAt(row, 3).toString());
			txtDonViTinh.setText(model.getValueAt(row, 4).toString());
			cbLoaiDV.setSelectedItem(model.getValueAt(row, 5));
			txtSoLuongTon.setText(model.getValueAt(row, 6).toString());
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
		if(o.equals(btnThem)) {
			btnThemDichVu();
		}
		
		else if (o.equals(btnLamMoi)) {
			btnLamMoi();
		}
		
		else if (o.equals(btnSua)) {
			btnUpdateDichVu();
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
	
	public void btnThemDichVu() {
	    if (txtMaDV.getText().isEmpty() || txtTenDV.getText().isEmpty() || txtGiaBan.getText().isEmpty() || txtDonViTinh.getText().isEmpty() || txtSoLuongTon.getText().isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	        return;
	    }

	    if (regex.regexDonViTinh(txtDonViTinh)) {
	        String maDichVu = txtMaDV.getText();
	        String tenDichVu = txtTenDV.getText();
	        Double giaBan = Double.parseDouble(txtGiaBan.getText());
	        String donViTinh = txtDonViTinh.getText();
	        int soLuongTon = Integer.parseInt(txtSoLuongTon.getText());
	        LoaiDichVu lDichVu = new LoaiDichVu(daoLoaiDichVu.getMaLoaiDVTheoTen(cbLoaiDV.getSelectedItem().toString()));

	        DichVu dv = new DichVu(maDichVu, tenDichVu, giaBan, donViTinh, soLuongTon, lDichVu);
	        try {
	            if (daoDichVu.addDichVu(dv)) {
	                int stt = model.getRowCount() + 1;
	                LoaiDichVu loaiDV = daoLoaiDichVu.getLoaiDichVuTheoMa(dv.getLoaiDV().getMaLoaiDV());
	                model.addRow(new Object[] { stt, dv.getMaDichVu(), dv.getTenDichVu(), dv.getGiaBan()+"", dv.getDonViTinh(), loaiDV.getTenLoaiDV(), dv.getSoLuongTon()});
	                btnLamMoi();

	                JOptionPane.showMessageDialog(this, "Thêm dịch vụ thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	            } else {
	                JOptionPane.showMessageDialog(this, "Trùng mã dịch vụ!", "Thông báo", JOptionPane.WARNING_MESSAGE);
	                return;
	            }
	        } catch (Exception e2) {
	            JOptionPane.showMessageDialog(this, "Error!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	            return;
	        }
	    }
	}
	
	public void btnLamMoi() {
		txtMaDV.setText(daoDichVu.getMaDichVu());
		txtTenDV.setText("");
		txtGiaBan.setText("");
		txtSoLuongTon.setText("");
		txtDonViTinh.setText("");
		cbLoaiDV.setSelectedIndex(0);
		DocDuLieuDataBaseVaoTable();
		txtMaDV.requestFocus();
		btnThem.setEnabled(true);
	}
	
	public void btnUpdateDichVu() {
	    int r = tblDichVu.getSelectedRow();

	    if (r >= 0) {
	        try {
	            int cn = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin này?", "Thông báo", JOptionPane.YES_NO_OPTION);
	            if (cn == JOptionPane.YES_OPTION) {
	                String maDichVu = (String) model.getValueAt(r, 1);
	                DichVu dv = new DichVu(maDichVu);
	                
	                dv.setTenDichVu(txtTenDV.getText());
	                dv.setGiaBan(Double.parseDouble(txtGiaBan.getText()));
	                dv.setDonViTinh(txtDonViTinh.getText());
	                dv.setSoLuongTon(Integer.parseInt(txtSoLuongTon.getText()));
	                LoaiDichVu loaiDV = new LoaiDichVu(daoLoaiDichVu.getMaLoaiDVTheoTen(cbLoaiDV.getSelectedItem().toString()));  
	                dv.setLoaiDV(loaiDV);

	                daoDichVu.updateDichVu(dv, maDichVu);
	                JOptionPane.showMessageDialog(this, "Cập nhập thành công!");
	                btnLamMoi();
	                DocDuLieuDataBaseVaoTable();
	            }
	        } catch (Exception e2) {
	            e2.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Cập nhật không thành công!");
	        }
	    }
	}
}
