package ui;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Image;
import java.math.BigDecimal;
import java.util.Comparator;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;


import javax.swing.table.DefaultTableCellRenderer;

import dao.CT_HoaDon_Dao;
import dao.KhachHang_Dao;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

public class pnTKKhachHang extends JPanel implements MouseListener,ActionListener{
	private Image imgBG = new ImageIcon(pnTKKhachHang.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconFind = new ImageIcon(pnTKKhachHang.class.getResource("/image/find.png"));
	private GradientPaint gra = new GradientPaint(0, 0, new Color(255, 255, 255), getWidth(), 0, Color.decode("#FAFFD1"));
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private JTable tblTKKH,tblChiTietSoLan;
	private DefaultTableModel dtmTblTKKH, dtmTblChiTietSoLan;
	private CT_HoaDon_Dao daoCT_HoaDon;
	private KhachHang_Dao daoKhachHang;
	private JButton btnTimKiemTheoSDT;
	private TableRowSorter<TableModel> sorterTblTKKH;
	private JTextField txtSoDienThoai, txtTongKhach, txtTongChi;
	private JComboBox<String> cmbTimTheoTieuChi;
	private JScrollPane scrTKKH, scrChiTietSoLan;
	private JLabel lblTitle, lblTimTheoSDT, lblTongKhach, lblTongChi, lblTimTheoTieuChi; 
	/**
	 * Create the panel.
	 */
	public pnTKKhachHang() {
		JPanel pnMain = new JPanel();
		pnMain.setBounds(0, 0, 1614, 1207);
		add(pnMain);
		pnMain.setLayout(null);
		
		lblTitle = new JLabel("THỐNG KÊ KHÁCH HÀNG GHÉ QUÁN");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(502, 21, 478, 36);
		pnMain.add(lblTitle);
		
		JPanel pnlTitle = new JPanel(){
		    /**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        // Vẽ hình ảnh làm background
		        g.drawImage(imgBG, 0, 0, getWidth(), getHeight(), this);
		    }
		};
		pnlTitle.setLayout(null);
		setBorderTitlePanel(pnlTitle, "");
		pnlTitle.setOpaque(false);
	    pnlTitle.setBounds(0, 0, 1554, 81);
	    pnMain.add(pnlTitle);
			
		String[] colTKKH = {"Tên khách hàng", "Số điện thoại", "Số lần ghé quán"};
		dtmTblTKKH = new DefaultTableModel(colTKKH, 0);

		scrTKKH = new JScrollPane();
		tblTKKH = new JTable(dtmTblTKKH) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};

		setCustomTable(tblTKKH);
		setBorderTitle(scrTKKH, "Danh sách khách hàng đã ghé");
		scrTKKH.setViewportView(tblTKKH);
		scrTKKH.setBounds(130, 171, 1198, 373);
		pnMain.add(scrTKKH);
		daoCT_HoaDon = new CT_HoaDon_Dao();
		daoKhachHang = new KhachHang_Dao();
		for (String dsMa : daoCT_HoaDon.getDanhSachMaKhachHang()) {
			Object[] data = {daoKhachHang.getMaKhachHangTheoMa(dsMa).getHoTen(), daoKhachHang.getMaKhachHangTheoMa(dsMa).getSoDT(), daoCT_HoaDon.getSoLanXuatHien(dsMa)};
			dtmTblTKKH.addRow(data);
		}
        tblTKKH.setRowHeight(30);
        tblTKKH.setFont(new Font("Serif", Font.BOLD, 20));
        JTableHeader tableHeader = tblTKKH.getTableHeader();
		tableHeader.setPreferredSize(new Dimension(0, 30));
		tableHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
		tableHeader.setForeground(Color.decode("#000000"));
		tableHeader.setBackground(Color.decode("#1995AD"));

		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        tblTKKH.setDefaultRenderer(String.class, centerRenderer);
        int[] columnWidths = {350,350,350};
        for (int i = 0; i < colTKKH.length; i++) {
            TableColumn column = tblTKKH.getColumnModel().getColumn(i);
            column.setPreferredWidth(columnWidths[i]);
        }

        sorterTblTKKH = new TableRowSorter<>(tblTKKH.getModel());
        sorterTblTKKH.setSortable(0, false);
        sorterTblTKKH.setComparator(2, new Comparator<Integer>() {
	        @Override
	        public int compare(Integer o1, Integer o2) {
	            return o1.compareTo(o2);
	        }
	    });
		tblTKKH.setAutoCreateRowSorter(true);		
		((TableRowSorter<DefaultTableModel>) tblTKKH.getRowSorter()).toggleSortOrder(2);
		
		String[] colChiTietSoLan = {"STT","Ngày ghé","Số tiền chi"};
		dtmTblChiTietSoLan = new DefaultTableModel(colChiTietSoLan, 0);
		tblChiTietSoLan = new JTable(dtmTblChiTietSoLan);
		setCustomTable(tblChiTietSoLan);
		scrChiTietSoLan = new JScrollPane();
		scrChiTietSoLan.setBounds(130, 617, 1198, 347);
		scrChiTietSoLan.setViewportView(tblChiTietSoLan);
		pnMain.add(scrChiTietSoLan);
		setBorderTitle(scrChiTietSoLan, "Chi tiết khách hàng đã ghé");
		int[] columnWidths1 = {30,525,525};
        for (int i = 0; i < colChiTietSoLan.length; i++) {
            TableColumn column1 = tblChiTietSoLan.getColumnModel().getColumn(i);
            column1.setPreferredWidth(columnWidths1[i]);
        }
        tblChiTietSoLan.setRowHeight(30);
        tblChiTietSoLan.setDefaultRenderer(String.class, centerRenderer);
        tblChiTietSoLan.setFont(new Font("Serif", Font.BOLD, 20));
        JTableHeader tableHeader1 = tblChiTietSoLan.getTableHeader();
		tableHeader1.setPreferredSize(new Dimension(0, 30));
		tableHeader1.setFont(new Font("Tahoma", Font.BOLD, 13));
		tableHeader1.setForeground(Color.decode("#000000"));
		tableHeader1.setBackground(Color.decode("#1995AD"));
		
        txtSoDienThoai = new JTextField();
        txtSoDienThoai.setBounds(299, 114, 177, 25);
        pnMain.add(txtSoDienThoai);
        txtSoDienThoai.setColumns(10);
        
        lblTimTheoSDT = new JLabel("Tìm kiếm theo sdt:");
        lblTimTheoSDT.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTimTheoSDT.setBounds(130, 112, 159, 25);
        pnMain.add(lblTimTheoSDT);
        
        btnTimKiemTheoSDT = new btnMyButton(130, 40, "Tìm kiếm", new Dimension(60, 23), iconFind.getImage(), new Dimension(25,20), gra);
        btnTimKiemTheoSDT.setBounds(501, 112, 137, 36);
        pnMain.add(btnTimKiemTheoSDT);
        
        txtTongKhach = new JTextField();
        txtTongKhach.setColumns(10);
        txtTongKhach.setBounds(267, 566, 177, 25);
        pnMain.add(txtTongKhach);
        txtTongKhach.setText(String.valueOf(tblTKKH.getRowCount()));
        txtTongKhach.setOpaque(false);
        txtTongKhach.setEditable(true);
        txtTongKhach.setForeground(Color.red);
        txtTongKhach.setBorder(null);
        txtTongKhach.setFont(new Font("Tahoma", Font.BOLD, 15));
        
        lblTongKhach = new JLabel("Tổng khách ghé:");
        lblTongKhach.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTongKhach.setBounds(130, 566, 159, 25);
        pnMain.add(lblTongKhach);
        
        lblTongChi = new JLabel("Tổng tiền chi:");
        lblTongChi.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTongChi.setBounds(773, 975, 159, 25);
        pnMain.add(lblTongChi);
        
        txtTongChi = new JTextField();
        txtTongChi.setText("0");
        txtTongChi.setOpaque(false);
        txtTongChi.setForeground(Color.RED);
        txtTongChi.setFont(new Font("Tahoma", Font.BOLD, 15));
        txtTongChi.setEditable(true);
        txtTongChi.setColumns(10);
        txtTongChi.setBorder(null);
        txtTongChi.setBounds(940, 975, 177, 25);
        pnMain.add(txtTongChi);
        
        lblTimTheoTieuChi = new JLabel("Tìm theo tiêu chí:");
        lblTimTheoTieuChi.setFont(new Font("Tahoma", Font.BOLD, 15));
        lblTimTheoTieuChi.setBounds(710, 114, 164, 25);
        pnMain.add(lblTimTheoTieuChi);
        
        cmbTimTheoTieuChi = new JComboBox<String>();
        cmbTimTheoTieuChi.setBounds(859, 116, 258, 21);
        pnMain.add(cmbTimTheoTieuChi);
        
        cmbTimTheoTieuChi.addItem("Trống");
        cmbTimTheoTieuChi.addItem("Khách hàng có mức chi tiêu nhiều nhất");
        cmbTimTheoTieuChi.addItem("Khách hàng có mức chi tiêu thấp nhất");
        cmbTimTheoTieuChi.addItem("Khánh hàng có mức chi tiêu trung bình");
        
     
		tblTKKH.addMouseListener(this);
		btnTimKiemTheoSDT.addActionListener(this);
		cmbTimTheoTieuChi.addActionListener(this);
		
		setLayout(null);		
	}
	
	public void setCustomTable(JTable tbl) {
		tbl.setFont(fontNormal);
		tbl.getTableHeader().setFont(fontBold);
		tbl.getTableHeader().setForeground(Color.decode("#9B17EB"));
		tbl.getTableHeader().setBackground(new Color(255, 255, 255));
	}
	public void setBorderTitle(JScrollPane scr, String title) {
		Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#1995AD"), 2),
				title, TitledBorder.LEADING, TitledBorder.TOP, fontBold, Color.black);
		scr.setBorder(border);
	}
	public void setBorderTitlePanel(JPanel pnl, String title) {
		Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#1995AD"), 2),
				title, TitledBorder.LEADING, TitledBorder.TOP, fontBold, Color.black);
		pnl.setBorder(border);
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		dtmTblChiTietSoLan.setRowCount(0);
		int selectedViewRow = tblTKKH.getSelectedRow();
		int selectedModelRow = tblTKKH.convertRowIndexToModel(selectedViewRow);
		String maKH = String.valueOf(daoKhachHang.getMaKhachHangTheoSDT(String.valueOf(dtmTblTKKH.getValueAt(selectedModelRow, 1))));
		String soLan = String.valueOf(dtmTblTKKH.getValueAt(selectedModelRow, 2));
		int stt = 0;
		for (int i = 0; i < Integer.parseInt(soLan) ; i++) {
			Object[] data = {++stt,daoCT_HoaDon.getChiTietTungLanXuatHien(maKH, Integer.parseInt(soLan))[i][0],daoCT_HoaDon.getChiTietTungLanXuatHien(maKH, Integer.parseInt(soLan))[i][1]};
			dtmTblChiTietSoLan.addRow(data);		
		}
		BigDecimal tongChi = BigDecimal.ZERO;
		for (int j = 0; j < dtmTblChiTietSoLan.getRowCount(); j++) {
		    String valueAtColumn2 = String.valueOf(dtmTblChiTietSoLan.getValueAt(j, 2));
		    tongChi = tongChi.add(new BigDecimal(valueAtColumn2));
		}

		txtTongChi.setText(tongChi.toString());
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
	    Object o = e.getSource();
	    String selectedValue = (String) cmbTimTheoTieuChi.getSelectedItem();
	    if (o.equals(btnTimKiemTheoSDT)) {
	    	selectedValue = null;
	    	cmbTimTheoTieuChi.setSelectedIndex(0);
	    	int kiemTraTimKiem = 0;
	        for (int i = 0; i < dtmTblTKKH.getRowCount(); i++) {
	            String phoneNumberInModel = String.valueOf(dtmTblTKKH.getValueAt(i, 1));
	            if (txtSoDienThoai.getText().equals(phoneNumberInModel)) {
	            	int selectedModelRow = i; 
	            	int selectedViewRow = tblTKKH.getRowSorter().convertRowIndexToView(selectedModelRow);
	                tblTKKH.setRowSelectionInterval(selectedViewRow, selectedViewRow);
	                tblTKKH.scrollRectToVisible(tblTKKH.getCellRect(selectedViewRow, 0, true));
	                kiemTraTimKiem = 1;
	                dtmTblChiTietSoLan.setRowCount(0);
	                String maKH = String.valueOf(daoKhachHang.getMaKhachHangTheoSDT(String.valueOf(dtmTblTKKH.getValueAt(selectedModelRow, 1))));
	        		String soLan = String.valueOf(dtmTblTKKH.getValueAt(selectedModelRow, 2));
	        		int stt = 0;
	        		for (int j = 0; j < Integer.parseInt(soLan) ; j++) {
	        			Object[] data = {++stt,daoCT_HoaDon.getChiTietTungLanXuatHien(maKH, Integer.parseInt(soLan))[j][0],daoCT_HoaDon.getChiTietTungLanXuatHien(maKH, Integer.parseInt(soLan))[j][1]};
	        			dtmTblChiTietSoLan.addRow(data);		
	        		}
	        		BigDecimal tongChi = BigDecimal.ZERO;
	        		for (int j = 0; j < dtmTblChiTietSoLan.getRowCount(); j++) {
	        		    String valueAtColumn2 = String.valueOf(dtmTblChiTietSoLan.getValueAt(j, 2));
	        		    tongChi = tongChi.add(new BigDecimal(valueAtColumn2));
	        		}

	        		txtTongChi.setText(tongChi.toString());
	                break;
	            }
	        }
	        if(kiemTraTimKiem == 0) {
	        	JOptionPane.showMessageDialog(
			            null,
			            "Không tìm thấy",
			            "Thông báo",
			            JOptionPane.INFORMATION_MESSAGE
			    );
	        }
	    }
	    if (selectedValue != null) {
            if (selectedValue.equals("Trống")) {
            	tblTKKH.clearSelection();
            } else if (selectedValue.equals("Khách hàng có mức chi tiêu nhiều nhất")) {
            	txtSoDienThoai.setText("");
            	timKiemKhachHangTheoCacTieuChi(daoKhachHang.getMaKhachHangTheoMa(daoCT_HoaDon.getMaKhachHangChiLonNhat()).getSoDT());
            } else if (selectedValue.equals("Khách hàng có mức chi tiêu thấp nhất")) {
            	txtSoDienThoai.setText("");
            	timKiemKhachHangTheoCacTieuChi(daoKhachHang.getMaKhachHangTheoMa(daoCT_HoaDon.getMaKhachHangChiNhoNhat()).getSoDT());
            } else if (selectedValue.equals("Khánh hàng có mức chi tiêu trung bình")) {
            	txtSoDienThoai.setText("");
            	timKiemKhachHangTheoCacTieuChi(daoKhachHang.getMaKhachHangTheoMa(daoCT_HoaDon.getMaKhachHangChiTrungBinh()).getSoDT());
            }
        }
	}
	/**
	 * Phương thức tìm khách hàng theo tiêu chí chọn trong Jcombobox,tô màu hàng
	 * trong bảng tblTKKH, và hiện chi tiết số lần khách hàng ghé. Nếu không tìm thấy, thông báo không tìm thấy 
	 * @param maKhachHangTheoTieuChi là tiêu chí của Jcombobox
	 */
	public void timKiemKhachHangTheoCacTieuChi(String maKhachHangTheoTieuChi) {
		int kiemTraTimKiem = 0;
        for (int i = 0; i < dtmTblTKKH.getRowCount(); i++) {
            String phoneNumberInModel = String.valueOf(dtmTblTKKH.getValueAt(i, 1));
            if (maKhachHangTheoTieuChi.equals(phoneNumberInModel)) {
            	int selectedModelRow = i; 
            	int selectedViewRow = tblTKKH.getRowSorter().convertRowIndexToView(selectedModelRow);
                tblTKKH.setRowSelectionInterval(selectedViewRow, selectedViewRow);
                tblTKKH.scrollRectToVisible(tblTKKH.getCellRect(selectedViewRow, 0, true));
                kiemTraTimKiem = 1;
                dtmTblChiTietSoLan.setRowCount(0);
                String maKH = String.valueOf(daoKhachHang.getMaKhachHangTheoSDT(String.valueOf(dtmTblTKKH.getValueAt(selectedModelRow, 1))));
        		String soLan = String.valueOf(dtmTblTKKH.getValueAt(selectedModelRow, 2));
        		int stt = 0;
        		for (int j = 0; j < Integer.parseInt(soLan) ; j++) {
        			Object[] data = {++stt,daoCT_HoaDon.getChiTietTungLanXuatHien(maKH, Integer.parseInt(soLan))[j][0],daoCT_HoaDon.getChiTietTungLanXuatHien(maKH, Integer.parseInt(soLan))[j][1]};
        			dtmTblChiTietSoLan.addRow(data);		
        		}
        		BigDecimal tongChi = BigDecimal.ZERO;
        		for (int j = 0; j < dtmTblChiTietSoLan.getRowCount(); j++) {
        		    String valueAtColumn2 = String.valueOf(dtmTblChiTietSoLan.getValueAt(j, 2));
        		    tongChi = tongChi.add(new BigDecimal(valueAtColumn2));
        		}

        		txtTongChi.setText(tongChi.toString());
                break;
            }
        }
        if(kiemTraTimKiem == 0) {
        	JOptionPane.showMessageDialog(
		            null,
		            "Không tìm thấy",
		            "Thông báo",
		            JOptionPane.INFORMATION_MESSAGE
		    );
        }
	}	
}
