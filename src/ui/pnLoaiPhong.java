package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.RowFilter;

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
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import connectDB.ConnectDB;
import dao.LoaiPhong_Dao;
import entity.LoaiDichVu;
import entity.LoaiPhong;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class pnLoaiPhong extends JPanel implements ActionListener, MouseListener {
	private JTextField tfMaLP, tfTenLP, tfTuKhoa;
	private JButton btnThem, btnSua, btnLamMoi, btnTimKiem, btnXoa, btnNext, btnLast;
	private JComboBox<String> cbSucChua, cbTimTheo;
	
	private Image imgBG = new ImageIcon(pnLoaiPhong.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAdd = new ImageIcon(pnLoaiPhong.class.getResource("/image/add1.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnLoaiPhong.class.getResource("/image/update.png"));
	private ImageIcon iconDelete = new ImageIcon(pnLoaiPhong.class.getResource("/image/huy1.png"));
	private ImageIcon iconReload = new ImageIcon(pnLoaiPhong.class.getResource("/image/reload.png"));
	private ImageIcon iconFind = new ImageIcon(pnLoaiPhong.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(pnLoaiPhong.class.getResource("/image/Rewind.png"));
	private ImageIcon iconLast = new ImageIcon(pnLoaiPhong.class.getResource("/image/Forward.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	private JPanel pnMain;
	private JTable tblLoaiPhong;
	private DefaultTableModel model;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private LoaiPhong_Dao daoLoaiPhong;
	
	
	

	/**
	 * Create the panel.
	 */
	public pnLoaiPhong() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		daoLoaiPhong = new LoaiPhong_Dao();
		
		setLayout(null);
		pnMain = new JPanel();
		pnMain.setBounds(0, 0, 1599, 1040);
		add(pnMain);
		pnMain.setLayout(null);
		createLoaiPhong();
		DocDuLieuDatabaseVaoTable();
	}
	
	public void createLoaiPhong() {
		JLabel lblTitle = new JLabel("QUẢN LÝ LOẠI PHÒNG");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(615, 20, 294, 36);
		pnMain.add(lblTitle);
		
		JLabel lblMaLP = new JLabel("Mã loại phòng:");
		lblMaLP.setForeground(Color.BLACK);
		lblMaLP.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMaLP.setBounds(282, 110, 109, 30);
		pnMain.add(lblMaLP);
		
		tfMaLP = new JTextField(daoLoaiPhong.getMaLoaiPhong());
		tfMaLP.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfMaLP.setBounds(418, 110, 175, 30);
		pnMain.add(tfMaLP);
		tfMaLP.setColumns(10);
		
		JLabel lblTenLP = new JLabel("Tên loại phòng:");
		lblTenLP.setForeground(Color.BLACK);
		lblTenLP.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTenLP.setBounds(282, 170, 126, 30);
		pnMain.add(lblTenLP);
		
		tfTenLP = new JTextField();
		tfTenLP.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfTenLP.setBounds(418, 170, 175, 30);
		pnMain.add(tfTenLP);
		tfTenLP.setColumns(10);
		
		JLabel lblSucChua = new JLabel("Sức chứa:");
		lblSucChua.setForeground(Color.BLACK);
		lblSucChua.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSucChua.setBounds(896, 110, 92, 30);
		pnMain.add(lblSucChua);
		
		cbSucChua = new JComboBox<String>();
		cbSucChua.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbSucChua.addItem("5");
		cbSucChua.addItem("10");
		cbSucChua.addItem("15");
		cbSucChua.addItem("20");
		cbSucChua.setBounds(1015, 110, 175, 30);
		pnMain.add(cbSucChua);
		
		btnThem = new btnMyButton(105, 40, "Thêm", new Dimension(50, 23), iconAdd.getImage(), new Dimension(25,25), gra);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnThem.setBounds(300, 230, 137, 36);
		pnMain.add(btnThem);
		btnThem.setToolTipText("Thêm loại phòng");

		AbstractAction actionThem = new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        btnThem.doClick();
		    }
		};

		KeyStroke keyStrokeThem = KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.ALT_DOWN_MASK);
		btnThem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeThem, "shortcut");
		btnThem.getActionMap().put("shortcut", actionThem);
		
		
		btnXoa = new btnMyButton(105, 40, "Xóa", new Dimension(50, 23), iconDelete.getImage(), new Dimension(25,25), gra);
		btnXoa.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnXoa.setBounds(500, 230, 137, 36);
		pnMain.add(btnXoa);
		btnXoa.setToolTipText("Xóa loại phòng");
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
		btnSua.setBounds(700, 230, 137, 36);
		pnMain.add(btnSua);
		btnSua.setToolTipText("Cập nhập loại phòng");
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
		btnLamMoi.setBounds(920, 230, 137, 36);
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
		setBorderTitle(pnlTable, "Danh sách loại phòng");
		pnlTable.setBounds(98, 380, 1350, 598);
		String[] cols = { "STT", "Mã loại phòng", "Tên loại phòng", "Sức chứa"};
		model = new DefaultTableModel(cols, 0);
		tblLoaiPhong = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		setCustomTable(tblLoaiPhong);
		tblLoaiPhong.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblLoaiPhong);
		scrTable.setBounds(10, 25, 1330, 562);
		pnlTable.add(scrTable);
		pnMain.add(pnlTable);
			
	    JPanel pnlTimKiem = new JPanel() {
	    	 @Override
	    	    protected void paintComponent(Graphics g) {
	    	        super.paintComponent(g);
	    	        // Không vẽ gì trong phương thức paintComponent của panel con
	    	    }
	    };
	    pnlTimKiem.setOpaque(false);
		pnlTimKiem.setLayout(null);
		pnlTimKiem.setBounds(266, 308, 947, 69);
		setBorderTitle(pnlTimKiem, "Tìm kiếm");
		pnMain.add(pnlTimKiem);
		
		JLabel lblLocTheo = new JLabel("Lọc theo sức chứa:");
		lblLocTheo.setBounds(20, 22, 145, 25);
		pnlTimKiem.add(lblLocTheo);
		lblLocTheo.setForeground(Color.BLACK);
		lblLocTheo.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		cbTimTheo = new JComboBox<String>();
		cbTimTheo.setBounds(175, 22, 175, 25);
		pnlTimKiem.add(cbTimTheo);
		
		btnTimKiem = new btnMyButton(130, 40, "Tìm kiếm", new Dimension(60, 23), iconFind.getImage(), new Dimension(25,25), gra);
		btnTimKiem.setBounds(786, 16, 137, 36);
		pnlTimKiem.add(btnTimKiem);
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 11));
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
		
		tfTuKhoa = new JTextField();
		tfTuKhoa.setToolTipText("Tìm theo tên phòng");
		tfTuKhoa.setBounds(583, 22, 183, 25);
		pnlTimKiem.add(tfTuKhoa);
		tfTuKhoa.setColumns(10);
		
		JLabel lblTuKhoa = new JLabel("Tìm theo tên phòng:");
		lblTuKhoa.setBounds(422, 22, 152, 25);
		pnlTimKiem.add(lblTuKhoa);
		lblTuKhoa.setForeground(Color.BLACK);
		lblTuKhoa.setFont(new Font("Tahoma", Font.BOLD, 15));
		btnTimKiem.addActionListener(this);
		cbTimTheo.addItem("");
		cbTimTheo.addItem("5");
		cbTimTheo.addItem("10");
		cbTimTheo.addItem("15");
		cbTimTheo.addItem("20");
		cbTimTheo.setToolTipText("Tìm theo sức chứa");
		
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
	    btnXoa.addActionListener(this);
	    btnLamMoi.addActionListener(this);
	    btnSua.addActionListener(this);
	    tblLoaiPhong.addMouseListener(this);
	    tfMaLP.requestFocus();
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
		int row = tblLoaiPhong.getSelectedRow();
		tfMaLP.setText(model.getValueAt(row, 1).toString());
		tfTenLP.setText(model.getValueAt(row, 2).toString());
		cbSucChua.setSelectedItem(model.getValueAt(row, 3).toString());
		btnThem.setEnabled(false);
	}


	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if (o.equals(btnThem)) {
		    btnThemLoaiPhong();
		}
		
		else if (o.equals(btnXoa)) {
			btnXoaLoaiPhong();
		}
		
		else if (o.equals(btnLamMoi)) {
			btnLamMoi();
		}
		
		else if (o.equals(btnSua)) {
			btnUpdateLoaiPhong();
		}
	
		else if (o.equals(btnTimKiem)) {
			String selectedValue = cbTimTheo.getSelectedItem().toString();
			if (selectedValue.isEmpty()) {
	            JOptionPane.showMessageDialog(
	                    this, "Vui lòng chọn một tiêu chí tìm kiếm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	        	if (!selectedValue.isEmpty()) {
			        // Lọc theo sức chứa
	        		TimTheoSucChua();
			    } else {
			        // Lọc theo tên loại phòng
			        TimTheoTenLoaiPhong();
			    }
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
	
	public void btnThemLoaiPhong() {
		String maLP = tfMaLP.getText().trim();
	    String tenLP = tfTenLP.getText().trim();
	    int sucChua = Integer.parseInt(cbSucChua.getSelectedItem().toString());

	    // Kiểm tra ràng buộc
	    if (maLP.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Mã loại phòng không được để trống!");
	        return;
	    }

	    if (tenLP.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "Tên loại phòng không được để trống!");
	        return;
	    }

	    if (sucChua <= 0) {
	        JOptionPane.showMessageDialog(this, "Sức chứa phải là một số nguyên dương!");
	        return;
	    }

	    try {
	        boolean result = daoLoaiPhong.addLoaiPhong(maLP, tenLP, sucChua);
	        if (result) {
	            LoaiPhong loaiP = new LoaiPhong(maLP, tenLP, sucChua);
	            int stt = model.getRowCount() + 1;
	            model.addRow(new Object[]{stt, loaiP.getMaLoaiP(), loaiP.getTenLoaiP(), loaiP.getSucChua()});
	            btnLamMoi();
	            JOptionPane.showMessageDialog(this, "Thêm loại phòng thành công!");
	        } else {
	            JOptionPane.showMessageDialog(this, "Lỗi khi thêm loại phòng!");
	        }
	    } catch (Exception e2) {
	        e2.printStackTrace();
	    }
	}
	
	public void btnXoaLoaiPhong() {
		int r = tblLoaiPhong.getSelectedRow();
		if (r != -1) {
		    String maLoaiP = (String) tblLoaiPhong.getModel().getValueAt(r, 1);
		    int hoi = JOptionPane.showConfirmDialog(this, "Bạn có muốn xóa không?", "Chú ý!", JOptionPane.YES_NO_OPTION);
		    if (hoi == JOptionPane.YES_OPTION) {
		        try {
		            boolean result = daoLoaiPhong.deleteLoaiPhong(maLoaiP);
		            if (result) {
		                model.removeRow(r);
		                tfMaLP.setText("");
		                btnLamMoi();
		                JOptionPane.showMessageDialog(this, "Xóa loại phòng thành công!");
		            } else {
		                JOptionPane.showMessageDialog(this, "Lỗi khi xóa loại phòng!");
		            }
		        } catch (SQLException e1) {
		            e1.printStackTrace();
		        }
		    }
		} else {
		    JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!");
		}
	}
	
	public void btnUpdateLoaiPhong() {
		 int r = tblLoaiPhong.getSelectedRow();

		    if (r >= 0) {
		        try {
		            int cn = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin này?", "Thông báo", JOptionPane.YES_NO_OPTION);
		            if (cn == JOptionPane.YES_OPTION) {
		                String maLoaiPhong = (String) model.getValueAt(r, 1);
		                LoaiPhong lp = new LoaiPhong(maLoaiPhong);
		                lp.setTenLoaiP(tfTenLP.getText());
		                lp.setSucChua(Integer.parseInt(cbSucChua.getSelectedItem().toString()));

		                daoLoaiPhong.updateLoaiPhong(lp, maLoaiPhong);
		                // Đổ dữ liệu vào bảng sau khi cập nhật thành công
	                    JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
	                    btnLamMoi();
	                    
		            }
		        } catch (Exception e2) {
		            e2.printStackTrace();
		            JOptionPane.showMessageDialog(null, "Cập nhật không thành công!");
		        }
		    }
	}
	
	public void DocDuLieuDatabaseVaoTable() {
	    List<LoaiPhong> list = daoLoaiPhong.loadLoaiPhongFromDatabase();
	    model.setRowCount(0); // Xóa dữ liệu cũ trên bảng
	    model.setColumnCount(4); // Số lượng cột trong bảng là 5
	    for (int i = 0; i < list.size(); i++) {
	        LoaiPhong nt = list.get(i);
	        model.addRow(new Object[]{i + 1, nt.getMaLoaiP(), nt.getTenLoaiP(), nt.getSucChua()});
	    }
	    model.fireTableDataChanged();
	}
	
	
	public void btnLamMoi() {
		tfMaLP.setText(daoLoaiPhong.getMaLoaiPhong());
		tfTenLP.setText("");
		tfTuKhoa.setText("");
		cbSucChua.setSelectedIndex(0);
		tfMaLP.requestFocus();
		tfTuKhoa.setText("");
		cbTimTheo.setSelectedIndex(0);
		tfMaLP.requestFocus();
		DocDuLieuDatabaseVaoTable();
		btnThem.setEnabled(true);
	}


	
	
	public void TimTheoSucChua() {
	    int sucChua = Integer.parseInt(cbTimTheo.getSelectedItem().toString());
	    List<LoaiPhong> list = daoLoaiPhong.getLoc(sucChua);
	    model.setRowCount(0);
	    for (int i = 0; i < list.size(); i++) {
	        LoaiPhong nt = list.get(i);
	        model.addRow(new Object[]{i + 1, nt.getMaLoaiP(), nt.getTenLoaiP(), nt.getSucChua()});
	    }
	}
	
	
	public void TimTheoTenLoaiPhong() {
	    String tenLP = tfTuKhoa.getText();
	    List<LoaiPhong> list = daoLoaiPhong.getLoc(tenLP);
	    model.setRowCount(0);
	    for (int i = 0; i < list.size(); i++) {
	        LoaiPhong nt = list.get(i);
	        model.addRow(new Object[]{i + 1, nt.getMaLoaiP(), nt.getTenLoaiP(), nt.getSucChua()});
	    }
	}
	

}
