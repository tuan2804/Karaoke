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
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import connectDB.ConnectDB;
import dao.LoaiPhong_Dao;
import dao.Phong_Dao;
import dao.Regex;
import entity.KhachHang;
import entity.LoaiPhong;
import entity.Phong;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;

public class pnPhong extends JPanel implements ActionListener, MouseListener {
	private JTextField tfMaPhong, tfTenPhong;
	private JButton btnThem, btnSua, btnLamMoi, btnXoa;
	private JComboBox<String> cbTrangThai, cbLoaiPhong;
	
	private Image imgBG = new ImageIcon(pnPhong.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAdd = new ImageIcon(pnPhong.class.getResource("/image/add1.png"));
	private ImageIcon iconDelete = new ImageIcon(pnPhong.class.getResource("/image/huy1.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnPhong.class.getResource("/image/update.png"));
	private ImageIcon iconReload = new ImageIcon(pnPhong.class.getResource("/image/reload.png"));
	private ImageIcon iconFind = new ImageIcon(pnPhong.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(pnPhong.class.getResource("/image/Rewind.png"));
	private ImageIcon iconLast = new ImageIcon(pnPhong.class.getResource("/image/Forward.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	
	private JTable tblPhong;
	private DefaultTableModel model;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private JTextField tfGiaPhong;
	
	private LoaiPhong_Dao daoLoaiPhong;
	private Phong_Dao daoPhong;
	private ArrayList<LoaiPhong> loaiP;
	private Regex regex;
	
	/**
	 * Create the panel.
	 */
	public pnPhong() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		daoLoaiPhong = new LoaiPhong_Dao();
		daoPhong = new Phong_Dao();
		regex = new Regex();
		setLayout(null);
		JPanel pnMain = new JPanel();
	
		pnMain.setBounds(0, 0, 1559, 1040);
		add(pnMain);
		pnMain.setLayout(null);
		
		JLabel lblTitle = new JLabel("QUẢN LÝ PHÒNG");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(615, 20, 276, 36);
		pnMain.add(lblTitle);
		
		JLabel lblMaPhong = new JLabel("Mã phòng:");
		lblMaPhong.setForeground(Color.BLACK);
		lblMaPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMaPhong.setBounds(299, 130, 92, 30);
		pnMain.add(lblMaPhong);
		
		tfMaPhong = new JTextField(daoPhong.getMaPhong());
		tfMaPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfMaPhong.setBounds(418, 130, 175, 30);
		pnMain.add(tfMaPhong);
		tfMaPhong.setColumns(10);
		
		JLabel lblTenPhong = new JLabel("Tên phòng:");
		lblTenPhong.setForeground(Color.BLACK);
		lblTenPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTenPhong.setBounds(299, 185, 92, 30);
		pnMain.add(lblTenPhong);
		
		JLabel lblGiaPhong = new JLabel("Giá phòng:");
	    lblGiaPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
	    lblGiaPhong.setBounds(299, 243, 92, 30);
	    pnMain.add(lblGiaPhong);
	    
	    tfGiaPhong = new JTextField();
	    tfGiaPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
	
	    tfGiaPhong.setBounds(418, 243, 175, 30);
	    pnMain.add(tfGiaPhong);
	    tfGiaPhong.setColumns(10);

		tfTenPhong = new JTextField();
		tfTenPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfTenPhong.setBounds(418, 185, 175, 30);
		pnMain.add(tfTenPhong);
		tfTenPhong.setColumns(10);
		
		JLabel lblTrangThai = new JLabel("Trạng thái:");
		lblTrangThai.setForeground(Color.BLACK);
		lblTrangThai.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTrangThai.setBounds(899, 130, 92, 30);
		pnMain.add(lblTrangThai);
		
		cbTrangThai = new JComboBox();
		cbTrangThai.setEnabled(false);
		cbTrangThai.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbTrangThai.setBounds(1015, 130, 175, 30);
		cbTrangThai.addItem("Trống");
		cbTrangThai.addItem("Đang sử dụng");
		UIManager.put("ComboBox.disabledForeground", Color.BLACK);
		pnMain.add(cbTrangThai);
		
		JLabel lblLoaiPhong = new JLabel("Loại phòng:");
		lblLoaiPhong.setForeground(Color.BLACK);
		lblLoaiPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLoaiPhong.setBounds(899, 185, 92, 30);
		pnMain.add(lblLoaiPhong);
		
		cbLoaiPhong = new JComboBox();
		cbLoaiPhong.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbLoaiPhong.setBounds(1015, 185, 175, 30);

		pnMain.add(cbLoaiPhong);
		
		btnThem = new btnMyButton(105, 40, "Thêm", new Dimension(45, 23), iconAdd.getImage(), new Dimension(25,25), gra);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnThem.setBounds(300, 300, 137, 40);
		pnMain.add(btnThem);
		btnThem.setToolTipText("Thêm phòng");

		AbstractAction actionThem = new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        btnThem.doClick();
		    }
		};

		KeyStroke keyStrokeThem = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.ALT_DOWN_MASK);
		btnThem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeThem, "shortcut");
		btnThem.getActionMap().put("shortcut", actionThem);
		
		btnXoa = new btnMyButton(105, 40, "Xóa", new Dimension(40, 23), iconDelete.getImage(), new Dimension(25,25), gra);
		btnXoa.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnXoa.setBounds(550, 300, 137, 36);
		pnMain.add(btnXoa);
		btnXoa.setToolTipText("Xóa phòng");
		AbstractAction actionXoa = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnXoa.doClick(); 
            }
        };
		KeyStroke keyStrokeXoa = KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.ALT_DOWN_MASK);
		btnXoa.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeXoa, "shortcut");
		btnXoa.getActionMap().put("shortcut", actionXoa);
		
		
		btnSua = new btnMyButton(130, 40, "Cập nhập", new Dimension(60, 23), iconUpdate.getImage(), new Dimension(25,25), gra);
		btnSua.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSua.setBounds(800, 300, 137, 36);
		pnMain.add(btnSua);
		btnSua.setToolTipText("Cập nhập phòng");
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
		btnLamMoi.setBounds(1100, 300, 137, 36);
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
		pnlTable.setForeground(Color.BLACK);
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		pnlTable.setOpaque(false);
		pnlTable.setBounds(98, 380, 1350, 575);
		setBorderTitle(pnlTable, "Danh sách phòng");
		
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
		scrTable.setBounds(10, 25, 1330, 539);
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
	    
	    btnThem.addActionListener(this);
	    btnSua.addActionListener(this);
	    btnLamMoi.addActionListener(this);
	    btnXoa.addActionListener(this);
	    tblPhong.addMouseListener(this);
	    loaiP = daoLoaiPhong.getAllLoaiPhong();
	    for(LoaiPhong lp : loaiP) {
			cbLoaiPhong.addItem(lp.getTenLoaiP());
		}
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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThem)) {
			btnThemPhong();
		}
		
		else if (o.equals(btnXoa)) {
			btnXoaPhong();
		}
		
		else if (o.equals(btnSua)) {
			btnUpdatePhong();
		}
		
		else if (o.equals(btnLamMoi)) {
			btnLamMoi();
		}
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
	
	public void btnLamMoi() {
		tfMaPhong.setText(daoPhong.getMaPhong());
		tfTenPhong.setText("");
		tfGiaPhong.setText("");
		cbTrangThai.setSelectedIndex(0);
		cbLoaiPhong.setSelectedIndex(0);
		tfMaPhong.requestFocus();
		DocDuLieuDataBaseVaoTable();
		btnThem.setEnabled(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(tblPhong)) {
			int row = tblPhong.getSelectedRow();
			tfMaPhong.setText(model.getValueAt(row, 1).toString());
			tfTenPhong.setText(model.getValueAt(row, 2).toString());
			cbTrangThai.setSelectedItem(model.getValueAt(row, 3).toString());
			tfGiaPhong.setText(model.getValueAt(row, 4).toString());
			cbLoaiPhong.setSelectedItem(model.getValueAt(row, 5));
			btnThem.setEnabled(false);
		}
		
	}
	
	public void btnThemPhong() {
		DecimalFormat decimalFormat = new DecimalFormat("#,###");
		if (validateFields() && regex.regexTimTenPhong(tfTenPhong)) {
	        String maPhong = tfMaPhong.getText();
	        String tenPhong = tfTenPhong.getText();
	        String tinhTrang = cbTrangThai.getSelectedItem().toString();
	        Double giaPhong = Double.parseDouble(tfGiaPhong.getText());
	        LoaiPhong lPhong = new LoaiPhong(daoLoaiPhong.getMaLoaiPTheoTen(cbLoaiPhong.getSelectedItem().toString()));
	        Phong p = new Phong(maPhong, tenPhong, tinhTrang, giaPhong, lPhong);
	        try {
	            if(daoPhong.addPhong(p)) {
	                int stt = model.getRowCount() + 1;
	                LoaiPhong loaiP = daoLoaiPhong.getLoaiPhongTheoMa(p.getLoaiPhong().getMaLoaiP());
	                model.addRow(new Object[] {stt, p.getMaPhong(), p.getTenPhong(), p.getTinhTrangPhong(), decimalFormat.format(p.getGiaPhong()), loaiP.getTenLoaiP()});
	                btnLamMoi();
	                
	                JOptionPane.showMessageDialog(this, "Thêm phòng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	            }
	            else {
	                JOptionPane.showMessageDialog(this, "Trùng mã phòng!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	            }
	        } catch (Exception e2) {
	            JOptionPane.showMessageDialog(this, "Lỗi khi thêm phòng!", "Thông báo", JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}
	
	public void btnXoaPhong() {
		int row = tblPhong.getSelectedRow();
        if (row != -1) {
            String maPhong = (String) tblPhong.getModel().getValueAt(row, 1);
            int hoi = JOptionPane.showConfirmDialog(
                    this, "Bạn có muốn xóa không?", "Chú ý!", JOptionPane.YES_NO_OPTION);
            if (hoi == JOptionPane.YES_OPTION) {
                try {
                    daoPhong.deletePhong(maPhong);
                    model.removeRow(row);
                    tfMaPhong.setText("");
                    btnLamMoi();
                    JOptionPane.showMessageDialog(
                            this, "Xóa phòng thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(
                            this, "Lỗi khi xóa phòng!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(
                    this, "Vui lòng chọn dòng cần xóa!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	
	public void btnUpdatePhong() {
	    int r = tblPhong.getSelectedRow();

	    if (r >= 0) {
	        try {
	            int cn = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin này?", "Thông báo", JOptionPane.YES_NO_OPTION);
	            if (cn == JOptionPane.YES_OPTION) {
	                String maPhong = (String) model.getValueAt(r, 1);
	                Phong phong = new Phong(maPhong);
	                phong.setTenPhong(tfTenPhong.getText());
	                phong.setTinhTrangPhong(cbTrangThai.getSelectedItem().toString());
	                phong.setGiaPhong(Double.parseDouble(tfGiaPhong.getText()));
	                
	                LoaiPhong loaiPhong = new LoaiPhong(daoLoaiPhong.getMaLoaiPTheoTen(cbLoaiPhong.getSelectedItem().toString()));
	                
	                phong.setLoaiPhong(loaiPhong);

	                daoPhong.updatePhong(phong, maPhong);
	                JOptionPane.showMessageDialog(this, "Cập nhập thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	                btnLamMoi();
	            }
	        } catch (Exception e2) {
	            e2.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Cập nhật không thành công!", "Thông báo", JOptionPane.ERROR_MESSAGE);
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
	
	private boolean validateFields() {
	    String maPhong = tfMaPhong.getText();
	    String tenPhong = tfTenPhong.getText();
	    String giaPhongText = tfGiaPhong.getText();

	    // Kiểm tra các trường thông tin
	    if (maPhong.isEmpty() || tenPhong.isEmpty() || giaPhongText.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin phòng!");
	        return false;
	    }
	    
	    // Kiểm tra định dạng giá phòng
	    try {
	        double giaPhong = Double.parseDouble(giaPhongText);
	        if (giaPhong <= 0) {
	            JOptionPane.showMessageDialog(this, "Giá phòng phải lớn hơn 0!");
	            return false;
	        }
	    } catch (NumberFormatException e) {
	        JOptionPane.showMessageDialog(this, "Giá phòng không hợp lệ!");
	        return false;
	    }

	    return true;
	}
}
