package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import connectDB.ConnectDB;
import dao.KhachHang_Dao;
import entity.KhachHang;
import entity.NhanVien;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class fChonKH extends JFrame implements ActionListener, MouseListener{

	private Image imgBG = new ImageIcon(fChonKH.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconAdd = new ImageIcon(fChonKH.class.getResource("/image/add1.png"));
	private ImageIcon iconUpdate = new ImageIcon(fChonKH.class.getResource("/image/update.png"));
	private ImageIcon iconReload = new ImageIcon(fChonKH.class.getResource("/image/reload.png"));
	private ImageIcon iconFind = new ImageIcon(fChonKH.class.getResource("/image/find.png"));
	private ImageIcon iconNext = new ImageIcon(fChonKH.class.getResource("/image/Rewind.png"));
	private ImageIcon iconPick = new ImageIcon(fChonKH.class.getResource("/image/iconPick.png"));
	private ImageIcon iconLast = new ImageIcon(fChonKH.class.getResource("/image/Forward.png"));
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	
	private JTable tblKhachHang;
	private DefaultTableModel model;
	private JPanel pnlMain;
	private JTextField txtMaKH, txtTenKH, txtSDT, txtCCCD;
	private JComboBox cmbGioiTinh;
	private JButton btnTimKiem, btnLamMoi, btnChonKH;
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	
	private pnDatPhong pnDatPhong;
	private KhachHang_Dao daoKhachHang;
	private pnDatPhong pnlDatPhong;
	
	
	
	public fChonKH(JTextField txtTenKH, JTextField txtSDT) {
        this.txtTenKH = txtTenKH;
        this.txtSDT = txtSDT;
    }

    public void settxtTenKH(JTextField txtTenKH, JTextField txtSDT) {
        this.txtTenKH = txtTenKH;
        this.txtSDT = txtSDT;
    }
	
   
    
	/**
	 * Create the frame.
	 */
	public fChonKH(pnDatPhong pnlDatPhong) {
		this.pnlDatPhong = pnlDatPhong;
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		daoKhachHang = new KhachHang_Dao();
		pnDatPhong = new pnDatPhong();
		pnDatPhong.setVisible(false);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1179, 749);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		JPanel pnlTitle = new JPanel(){
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        // Vẽ hình ảnh làm background
		        g.drawImage(imgBG, 0, 0, getWidth(), getHeight(), this);
		    }
		};
		pnlTitle.setBounds(0, 0, 1163, 73);
		getContentPane().add(pnlTitle);
		pnlTitle.setLayout(null);
		
		JLabel lblChonKH = new JLabel("Chọn Khách Hàng");
		lblChonKH.setFont(new Font("Tahoma", Font.BOLD, 20));
		setBorderTitle(pnlTitle, "");
		lblChonKH.setBounds(500, 17, 179, 30);
		pnlTitle.add(lblChonKH);
		
		JPanel pnlTable = new JPanel();
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setBounds(20, 265, 1133, 406);
		setBorderTitle(pnlTable, "Danh sách khách hàng");
		pnlTable.setLayout(null);
		pnlTable.setOpaque(false);
		String[] cols = { "STT", "Mã khách hàng", "Tên khách hàng", "Giới tính", "Số điện thoại","CCCD" };
		model = new DefaultTableModel(cols, 0);
		tblKhachHang = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		
		
		
		TableColumnModel columnModel = tblKhachHang.getColumnModel();
		
		int sttColumnWidth = 10;
		TableColumn sttColumn = columnModel.getColumn(0);
		sttColumn.setPreferredWidth(sttColumnWidth);

		
		int maKHColumnWidth = 10;
		TableColumn maLoaiDVColumn = columnModel.getColumn(1);
		maLoaiDVColumn.setPreferredWidth(maKHColumnWidth);

		
		int tenKHColumnWidth = 200;
		TableColumn tenLoaiDVColumn = columnModel.getColumn(2);
		tenLoaiDVColumn.setPreferredWidth(tenKHColumnWidth);
		
		
		setCustomTable(tblKhachHang);
		tblKhachHang.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblKhachHang);
		scrTable.setBounds(18, 20, 1100, 370);
		pnlTable.add(scrTable);
		getContentPane().add(pnlTable);
		
		JLabel lblMaKH = new JLabel("Mã khách hàng:");
		lblMaKH.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblMaKH.setBounds(92, 98, 115, 25);
		getContentPane().add(lblMaKH);
		
		txtMaKH = new JTextField();
		txtMaKH.setToolTipText("Tìm theo mã khách hàng");
		txtMaKH.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtMaKH.setBounds(227, 98, 190, 25);
		getContentPane().add(txtMaKH);
		txtMaKH.setColumns(10);
		
		JLabel lblTenKH = new JLabel("Tên khách hàng:");
		lblTenKH.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTenKH.setBounds(92, 150, 127, 25);
		getContentPane().add(lblTenKH);
		
		txtTenKH = new JTextField();
		txtTenKH.setToolTipText("Tìm theo tên khách hàng");
		txtTenKH.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtTenKH.setBounds(227, 150, 190, 25);
		getContentPane().add(txtTenKH);
		txtTenKH.setColumns(10);
		
		JLabel lblGioiTinh = new JLabel("Giới tính:");
		lblGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblGioiTinh.setBounds(92, 197, 115, 25);
		getContentPane().add(lblGioiTinh);
		
		cmbGioiTinh = new JComboBox();
		cmbGioiTinh.setToolTipText("Tìm theo giới tính khách hàng");
		cmbGioiTinh.addItem("Nam");
		cmbGioiTinh.addItem("Nữ");
		cmbGioiTinh.setFont(new Font("Tahoma", Font.BOLD, 15));
		cmbGioiTinh.setBounds(227, 197, 190, 25);
		getContentPane().add(cmbGioiTinh);
		
		JLabel lblSDT = new JLabel("Số điện thoại:");
		lblSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblSDT.setBounds(654, 98, 102, 25);
		getContentPane().add(lblSDT);
		
		txtSDT = new JTextField();
		txtSDT.setToolTipText("Tìm theo số điện thoại");
		txtSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtSDT.setBounds(772, 98, 190, 25);
		getContentPane().add(txtSDT);
		txtSDT.setColumns(10);
		
		JLabel lblCCCD = new JLabel("CCCD:");
		lblCCCD.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblCCCD.setBounds(654, 150, 46, 25);
		getContentPane().add(lblCCCD);
		
		txtCCCD = new JTextField();
		txtCCCD.setToolTipText("Tìm theo căn cước công dân");
		txtCCCD.setFont(new Font("Tahoma", Font.BOLD, 15));
		txtCCCD.setBounds(772, 150, 190, 25);
		getContentPane().add(txtCCCD);
		txtCCCD.setColumns(10);
		
		btnTimKiem = new btnMyButton(130, 40, "Tìm kiếm", new Dimension(60, 23), iconFind.getImage(), new Dimension(25,25), gra);
		btnTimKiem.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnTimKiem.setToolTipText("Tìm kiếm khách hàng");
		btnTimKiem.setBounds(991, 95, 130, 40);
		getContentPane().add(btnTimKiem);
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
		btnLamMoi.setToolTipText("Làm mới");
		btnLamMoi.setBounds(991, 140, 137, 40);
		getContentPane().add(btnLamMoi);
		AbstractAction actionLamMoi = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	btnLamMoi.doClick(); 
            }
        };
		KeyStroke keyStrokeLamMoi = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.ALT_DOWN_MASK);
		btnLamMoi.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeLamMoi, "shortcut");
		btnLamMoi.getActionMap().put("shortcut", actionLamMoi);
		
		btnChonKH = new btnMyButton(137, 40, "Chọn KH", new Dimension(70, 23), iconPick.getImage(), new Dimension(25,25), gra);
		btnChonKH.setToolTipText("Chọn khách hàng");
		btnChonKH.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = tblKhachHang.getSelectedRow();
                if (selectedRow != -1) {
                    String tenKhachHang = tblKhachHang.getValueAt(selectedRow, 2).toString();
                    String sdtKhachHang = tblKhachHang.getValueAt(selectedRow, 4).toString();
                    pnlDatPhong.setTenKH(tenKhachHang);
                    pnlDatPhong.setSDT(sdtKhachHang);
                }
                dispose();
            }
        });
		btnChonKH.setBounds(991, 185, 137, 40);
		getContentPane().add(btnChonKH);
		AbstractAction actionChonKhachHang = new AbstractAction() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    	btnChonKH.doClick();
		    }
		};

		KeyStroke keyStrokeChonKH = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.ALT_DOWN_MASK);
		btnChonKH.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStrokeChonKH, "shortcut");
		btnChonKH.getActionMap().put("shortcut", actionChonKhachHang);
		
		pnlMain = new JPanel();
		DocDuLieuDataBaseVaoTable();
		tblKhachHang.addMouseListener(this);
		btnTimKiem.addActionListener(this);
		btnLamMoi.addActionListener(this);
		fChonKH chonKH = new fChonKH(txtTenKH, txtSDT);
		chonKH.settxtTenKH(txtTenKH, txtSDT);
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
		List<KhachHang> list = daoKhachHang.loadKhachHangFromDatabase();
		model.setRowCount(0);
		for(KhachHang kh : list) {
			int stt = model.getRowCount() + 1;
			model.addRow(new Object[] { stt, kh.getMaKhachHang(), kh.getHoTen(), kh.getGioiTinh(), kh.getSoDT(), kh.getcCCD(), kh.getNgaySinh()});
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		Object o = e.getSource();
		if(o.equals(tblKhachHang)) {
			int row = tblKhachHang.getSelectedRow();
			txtMaKH.setText(model.getValueAt(row, 1).toString());
			txtTenKH.setText(model.getValueAt(row, 2).toString());
			cmbGioiTinh.setSelectedItem(model.getValueAt(row, 3).toString());
			txtSDT.setText(model.getValueAt(row, 4).toString());
			txtCCCD.setText(model.getValueAt(row, 5).toString());
		
			
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
		
		if (o.equals(btnTimKiem)) {
			String maKhachHang = txtMaKH.getText();
			String tenKhachHang = txtTenKH.getText();
			String gioiTinh = cmbGioiTinh.getSelectedItem().toString();
			String sdt = txtSDT.getText();
			String cCCD = txtCCCD.getText();
			List<KhachHang> list = new ArrayList<>();
			if(!maKhachHang.isEmpty()) {
				list = daoKhachHang.getLocMaKhachHang(maKhachHang);
			}
			else if (!tenKhachHang.isEmpty()) {
				list = daoKhachHang.getLocTenKhachHang(tenKhachHang);
			}
			else if (!sdt.isEmpty()) {
				list = daoKhachHang.getLocSDT(sdt);
			}
			else if (!cCCD.isEmpty()) {
				list = daoKhachHang.getLocCCCD(cCCD);
			}
			else if (!gioiTinh.isEmpty()) {
				list = daoKhachHang.getLocGioiTinh(gioiTinh);
			}
			
			if (!list.isEmpty()) {
				model.setRowCount(0);
				for(KhachHang kh : list) {
					int stt = model.getRowCount() + 1;
					model.addRow(new Object[] { stt, kh.getMaKhachHang(), kh.getHoTen(), kh.getGioiTinh(), kh.getSoDT(), kh.getcCCD(), kh.getNgaySinh()});
				}
				JOptionPane.showMessageDialog(this, "Đã tìm thấy " + list.size() + " kết quả!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
			} else {
		        // Hiển thị thông báo không tìm thấy kết quả
		        JOptionPane.showMessageDialog(this, "Không tìm thấy kết quả!");
		    }
			
		}
		
		else if (o.equals(btnLamMoi)) {
			lamMoi();
		}
	}
	
	public void lamMoi() {
		txtMaKH.setText("");
		txtTenKH.setText("");
		txtSDT.setText("");
		txtCCCD.setText("");
		DocDuLieuDataBaseVaoTable();
	}
}
