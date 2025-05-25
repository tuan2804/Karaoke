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
import java.sql.Date;
import java.text.SimpleDateFormat;
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
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import connectDB.ConnectDB;
import dao.LoaiDichVu_Dao;
import entity.KhachHang;
import entity.LoaiDichVu;
import entity.NhanVien;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

public class pnLoaiDichVu extends JPanel implements ActionListener, MouseListener{
	private JTextField txtMaLDV, txtTuKhoa, txtTenLDV, txtTimTheoMa;
	private JButton btnThem, btnSua, btnLamMoi, btnTimKiem;
	
	private Image imgBG = new ImageIcon(pnLoaiDichVu.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAdd = new ImageIcon(pnLoaiDichVu.class.getResource("/image/add1.png"));
	private ImageIcon iconUpdate = new ImageIcon(pnLoaiDichVu.class.getResource("/image/update.png"));
	private ImageIcon iconReload = new ImageIcon(pnLoaiDichVu.class.getResource("/image/reload.png"));
	private ImageIcon iconFind = new ImageIcon(pnLoaiDichVu.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(pnLoaiDichVu.class.getResource("/image/Rewind.png"));
	private ImageIcon iconLast = new ImageIcon(pnLoaiDichVu.class.getResource("/image/Forward.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	
	private JTable tblLoaiDV;
	private DefaultTableModel model;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private LoaiDichVu_Dao daoLoaiDichVu;
	/**
	 * Create the panel.
	 */
	public pnLoaiDichVu() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		daoLoaiDichVu = new LoaiDichVu_Dao();
		setLayout(null); 
		JPanel pnMain = new JPanel();
		pnMain.setForeground(Color.BLACK);
		pnMain.setBounds(0, 0, 1559, 1040);
		add(pnMain);
		pnMain.setLayout(null);
		
		JLabel lblTitle = new JLabel("QUẢN LÝ LOẠI DỊCH VỤ");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(594, 20, 321, 36);
		pnMain.add(lblTitle);
		
		JLabel lblMaLDV = new JLabel("Mã loại DV:");
		lblMaLDV.setForeground(Color.BLACK);
		lblMaLDV.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMaLDV.setBounds(299, 130, 92, 30);
		pnMain.add(lblMaLDV);
		
		txtMaLDV = new JTextField(daoLoaiDichVu.getMaLoaiDichVu());
		txtMaLDV.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtMaLDV.setBounds(418, 130, 175, 30);
		pnMain.add(txtMaLDV);
		txtMaLDV.setColumns(10);
		
		JLabel lblTenLDV = new JLabel("Tên loại DV:");
		lblTenLDV.setBackground(Color.WHITE);
		lblTenLDV.setForeground(Color.BLACK);
		lblTenLDV.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTenLDV.setBounds(899, 130, 92, 30);
		pnMain.add(lblTenLDV);
		
		txtTenLDV = new JTextField();
		txtTenLDV.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtTenLDV.setBounds(1015, 130, 175, 30);
		pnMain.add(txtTenLDV);
		txtTenLDV.setColumns(10);
		
		btnThem = new btnMyButton(105, 40, "Thêm", new Dimension(50, 23), iconAdd.getImage(), new Dimension(25,25), gra);
		btnThem.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnThem.setBounds(400, 200, 137, 36);
		pnMain.add(btnThem);
		btnThem.setToolTipText("Thêm loại dịch vụ");

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
		btnSua.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSua.setToolTipText("Cập nhập thông tin loại dịch vụ");
		btnSua.setBounds(700, 200, 137, 36);
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
		btnLamMoi.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnLamMoi.setToolTipText("Làm mới");
		btnLamMoi.setBounds(1000, 200, 137, 36);
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
		
		JLabel lblTuKhoa = new JLabel("Tìm theo tên:");
		lblTuKhoa.setForeground(Color.BLACK);
		lblTuKhoa.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTuKhoa.setBounds(720, 331, 117, 25);
		pnMain.add(lblTuKhoa);
		
		txtTuKhoa = new JTextField();
		txtTuKhoa.setToolTipText("Nhập tìm kiếm theo tên loại dịch vụ");
		txtTuKhoa.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtTuKhoa.setBounds(844, 331, 183, 25);
		pnMain.add(txtTuKhoa);
		txtTuKhoa.setColumns(10);
		
		btnTimKiem = new btnMyButton(130, 40, "Tìm kiếm", new Dimension(60, 23), iconFind.getImage(), new Dimension(25,25), gra);
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTimKiem.setToolTipText("Tìm kiếm theo tiêu chí");
		btnTimKiem.setBounds(1055, 325, 137, 36);
		pnMain.add(btnTimKiem);
		AbstractAction actionTimKiem = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnTimKiem.doClick(); 
            }
        };
		KeyStroke keyStrokeTimKiem = KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.ALT_DOWN_MASK);
		btnTimKiem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeTimKiem, "shortcut");
		
		JPanel pnlTable = new JPanel();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		pnlTable.setBounds(98, 390, 1350, 603);
		setBorderTitle(pnlTable, "Danh sách loại dịch vụ");
		pnlTable.setOpaque(false);
		String[] cols = { "STT", "Mã Loại DV", "Tên Loại DV"};
		model = new DefaultTableModel(cols, 0);
		tblLoaiDV = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		setCustomTable(tblLoaiDV);
		tblLoaiDV.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblLoaiDV);
		scrTable.setBounds(10, 25, 1330, 567);
		pnlTable.add(scrTable);
		pnMain.add(pnlTable);
		

		
	    JPanel pnlTimKiem = new JPanel();
	    pnlTimKiem.setOpaque(false);
		pnlTimKiem.setLayout(null);
		pnlTimKiem.setBounds(266, 308, 947, 69);
		setBorderTitle(pnlTimKiem, "Tìm kiếm");
		pnMain.add(pnlTimKiem);
		
		txtTimTheoMa = new JTextField();
		txtTimTheoMa.setToolTipText("Tìm theo mã loại dịch vụ");
		txtTimTheoMa.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtTimTheoMa.setBounds(179, 23, 183, 25);
		pnlTimKiem.add(txtTimTheoMa);
		txtTimTheoMa.setColumns(10);
		
		JLabel lblTimTheo = new JLabel("Tìm theo mã:");
		lblTimTheo.setBounds(51, 23, 118, 25);
		pnlTimKiem.add(lblTimTheo);
		lblTimTheo.setForeground(Color.BLACK);
		lblTimTheo.setFont(new Font("Tahoma", Font.BOLD, 15));
		
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
	    tblLoaiDV.addMouseListener(this);
	    btnLamMoi.addActionListener(this);
	    btnSua.addActionListener(this);
	    btnThem.addActionListener(this);
	    btnTimKiem.addActionListener(this);
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
		Object source = e.getSource();
	    
	    if (source.equals(tblLoaiDV)) {
	        int selectedRow = tblLoaiDV.getSelectedRow();

	        // Lấy giá trị của cột từ dòng đã chọn
	        Object maLoaiDV = model.getValueAt(selectedRow, 1);
	        Object tenLoaiDV = model.getValueAt(selectedRow, 2);
	    
	       
	        
	        txtMaLDV.setText(maLoaiDV.toString());
	        txtTenLDV.setText(tenLoaiDV.toString());
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
			try {
				String maLoaiDV = txtMaLDV.getText();
			    String tenLoaiDV = txtTenLDV.getText();
			    
				
				LoaiDichVu loaiDV = new LoaiDichVu(maLoaiDV, tenLoaiDV);
				daoLoaiDichVu.addLoaiDV(loaiDV);
				int stt = model.getRowCount() + 1;
				model.addRow(new Object[] { stt, loaiDV.getMaLoaiDV(), loaiDV.getTenLoaiDV()});
		        JOptionPane.showMessageDialog(this, "Thêm loại dịch vụ thành công!");
		        lamMoi();
			} catch (Exception e2) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(this, "Lỗi trùng mã!");
			}
		}
		
		else if (o.equals(btnLamMoi)) {
			lamMoi();
		}
		
		else if (o.equals(btnTimKiem)) {
			String maLoaiDV = txtTimTheoMa.getText();
			String tenLoaiDV = txtTuKhoa.getText();
			List<LoaiDichVu> list = new ArrayList<>();
			if(!maLoaiDV.isEmpty()) {
				list = daoLoaiDichVu.getLocTheoMa(maLoaiDV);
			}
			else if (!tenLoaiDV.isEmpty()) {
				list = daoLoaiDichVu.getLocTheoTen(tenLoaiDV);
			}
			model.setRowCount(0);
			for(LoaiDichVu loaiDV : list) {
				int stt = model.getRowCount() + 1;
				model.addRow(new Object[] { stt, loaiDV.getMaLoaiDV(), loaiDV.getTenLoaiDV()});
			}
		}
		
		else if (o.equals(btnSua)) {
			update();
		}
	}
	
	public void DocDuLieuDataBaseVaoTable() {
		List<LoaiDichVu> list = daoLoaiDichVu.loadLoaiDichVuFromDatabase();
		model.setRowCount(0);
		for(LoaiDichVu loaiDV : list) {
			int stt = model.getRowCount() + 1;
			model.addRow(new Object[] { stt, loaiDV.getMaLoaiDV(), loaiDV.getTenLoaiDV()});
		}
	}
	
	public void lamMoi() {
		txtMaLDV.setText(daoLoaiDichVu.getMaLoaiDichVu());
		txtTenLDV.setText("");
		txtMaLDV.requestFocus();
		DocDuLieuDataBaseVaoTable();
		btnThem.setEnabled(true);
	}
	
	public void update() {
	    int r = tblLoaiDV.getSelectedRow();

	    if (r >= 0) {
	        try {
	            int cn = JOptionPane.showConfirmDialog(this, "Bạn muốn sửa thông tin này?", "Thông báo", JOptionPane.YES_NO_OPTION);
	            if (cn == JOptionPane.YES_OPTION) {
	                String maLoaiDV = (String) model.getValueAt(r, 1);
	                LoaiDichVu loaiDV = new LoaiDichVu(maLoaiDV);
	                loaiDV.setTenLoaiDV(txtTenLDV.getText());
	                

	                daoLoaiDichVu.updateLoaiDV(loaiDV, maLoaiDV);
	                JOptionPane.showMessageDialog(this, "Cập nhập thành công!");
	                lamMoi();
	                
	            }
	        } catch (Exception e2) {
	            e2.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Cập nhật không thành công!");
	        }
	    }
	}
}
