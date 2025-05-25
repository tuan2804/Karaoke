package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;

import connectDB.ConnectDB;
import dao.CT_HoaDon_Dao;
import dao.DichVu_Dao;
import dao.HoaDon_Dao;
import dao.KhachHang_Dao;
import dao.NhanVien_Dao;
import dao.Phong_Dao;
import entity.CT_HoaDon;
import entity.DichVu;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Phong;

public class pnTKDichVuSuDung extends JPanel {
	private Image imgBG = new ImageIcon(pnPhong.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconThongKe = new ImageIcon(pnTKDichVuSuDung.class.getResource("/image/thongke1.png"));
	private ImageIcon iconDoanhThu = new ImageIcon(pnTKDichVuSuDung.class.getResource("/image/doanhthu.png"));
	private ImageIcon iconHoaDon = new ImageIcon(pnTKDichVuSuDung.class.getResource("/image/hoadon1.png"));
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	private static JMonthChooser mchThangTK;
	private JYearChooser ychNamTK;
	private JDateChooser dateChooserNgay;
	private JButton btnThongKe;
	private JComboBox cbLocTheo;
//	private DefaultCategoryDataset dataset;
//  private JTextField tfTongHoaDon, tfDoanhThu;
    private HoaDon_Dao daoHoaDon;
    private CT_HoaDon_Dao daoCTHoaDon;
    private DichVu_Dao daoDichVu;
    private Phong_Dao daoPhong;
    private KhachHang_Dao daoKhachHang;
    private NhanVien_Dao daoNhanVien;
    private JLabel lblNam, lblLocTheo, lblIcon1, lblIcon2;
    private DefaultTableModel model;
//    private JTable tblDoanhThu;
    private JLabel lblTheoNgay;
    private JTable tblDSDV;
    
    
	public pnTKDichVuSuDung() {
		try {
			ConnectDB.getInstance().connect();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		daoHoaDon = new HoaDon_Dao();
		daoCTHoaDon = new CT_HoaDon_Dao();
		daoDichVu = new DichVu_Dao();
		daoKhachHang = new KhachHang_Dao();
		daoPhong = new Phong_Dao();
		daoNhanVien = new NhanVien_Dao();
		setLayout(null);
		setBounds(0, 0, 1559, 1040);
		setOpaque(false);
		
		// Sử dụng GridBagLayout
		GridBagLayout gridBagLayout = new GridBagLayout();
		
		JPanel pnlTimKiem = new JPanel();
		setBorderTitle(pnlTimKiem, "Thống kê theo tiêu chí");
		pnlTimKiem.setBounds(54, 99, 1448	, 161);
		add(pnlTimKiem);
		pnlTimKiem.setLayout(null);
		
//		tfTongHoaDon = new JTextField();
//		tfTongHoaDon.setBorder(null);
//		tfTongHoaDon.setOpaque(false);
//		tfTongHoaDon.setEnabled(false);
//		tfTongHoaDon.setDisabledTextColor(Color.RED);
//		tfTongHoaDon.setBounds(218, 125, 156, 25);
//		pnlTimKiem.add(tfTongHoaDon);
//		tfTongHoaDon.setFont(new Font("Tahoma", Font.BOLD, 15));
//		tfTongHoaDon.setColumns(10);
		
//		JLabel lblTongHoaDon = new JLabel("Tổng số dịch vụ:");
//		lblTongHoaDon.setBounds(84, 125, 124, 25);
//		pnlTimKiem.add(lblTongHoaDon);
//		lblTongHoaDon.setFont(new Font("Tahoma", Font.BOLD, 15));
		

		mchThangTK = new JMonthChooser();
		mchThangTK.setLocale(new Locale("Vi", "VN"));
		mchThangTK.getComboBox().setFont(new Font("Tahoma", Font.BOLD, 15));

		mchThangTK.setBounds(476, 41, 156, 25);
		pnlTimKiem.add(mchThangTK);
		
		JLabel lblThang = new JLabel("Theo tháng:");
		lblThang.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblThang.setBounds(377, 41, 89, 25);
		pnlTimKiem.add(lblThang);
		
		lblNam = new JLabel("Theo năm:");
		lblNam.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNam.setBounds(706, 41, 76, 25);
		pnlTimKiem.add(lblNam);
		
		ychNamTK = new JYearChooser();
		ychNamTK.setBounds(792, 41, 64, 25);
		pnlTimKiem.add(ychNamTK);
		ychNamTK.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		lblLocTheo = new JLabel("Thống kê theo:");
		lblLocTheo.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblLocTheo.setBounds(907, 41, 109, 25);
		pnlTimKiem.add(lblLocTheo);
		
		cbLocTheo = new JComboBox();
		cbLocTheo.addItem("Chọn tiêu chí");
		cbLocTheo.addItem("Thống kê theo ngày");
		cbLocTheo.addItem("Thống kê theo tháng");
		cbLocTheo.addItem("Thống kê theo năm");
		cbLocTheo.setFont(new Font("Tahoma", Font.BOLD, 15));
		cbLocTheo.setBounds(1026, 41, 187, 25);
		pnlTimKiem.add(cbLocTheo);
		cbLocTheo.setSelectedItem("");
		cbLocTheo.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent evt) {
		        cbLocTheoActionPerformed(evt);
		    }
		});
		
		btnThongKe = new btnMyButton(130, 40, "Thống kê", new Dimension(70, 23), iconThongKe.getImage(), new Dimension(30,25), gra);
		btnThongKe.setBounds(1281, 36, 130, 40);
		pnlTimKiem.add(btnThongKe);
			
		lblTheoNgay = new JLabel("Theo ngày:");
		lblTheoNgay.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTheoNgay.setBounds(55, 41, 89, 25);
		pnlTimKiem.add(lblTheoNgay);
		
		dateChooserNgay = new JDateChooser();
		dateChooserNgay.setFont(new Font("Tahoma", Font.BOLD, 15));
		dateChooserNgay.getCalendarButton().setFont(new Font("Tahoma", Font.BOLD, 15));
		dateChooserNgay.setLocale(new Locale("Vi", "VN"));
		dateChooserNgay.setBounds(154, 41, 164, 25);
		pnlTimKiem.add(dateChooserNgay);
		btnThongKe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
               btnThongKeActionPerformed(evt);
            }
        });
		
		JPanel pnlTitle = new JPanel(){
		    @Override
		    protected void paintComponent(Graphics g) {
		        super.paintComponent(g);
		        g.drawImage(imgBG, 0, 0, getWidth(), getHeight(), this);
		    }
		};
		pnlTitle.setLayout(null);
		setBorderTitle(pnlTitle, "");
		pnlTitle.setOpaque(false);
	    pnlTitle.setBounds(0, 0, 1554, 81);
	    add(pnlTitle);
	    
	    JLabel lblTitle = new JLabel("THỐNG KÊ DỊCH VỤ ĐƯỢC SỬ DỤNG NHIỀU NHẤT");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(450, 20, 700, 36);
		pnlTitle.add(lblTitle);
		
		JPanel pnlTable = new JPanel();
		pnlTable.setForeground(Color.BLACK);
		pnlTable.setBackground(Color.WHITE);
		pnlTable.setLayout(null);
		pnlTable.setOpaque(false);
		pnlTable.setBounds(54, 300, 1448, 450);
		setBorderTitle(pnlTable, "Danh Sách Dịch Vụ");
		
		String[] cols = {"Mã Dịch Vụ", "Tên Dịch Vụ", "Số Lượng"};
		model = new DefaultTableModel(cols, 0);
		tblDSDV = new JTable(model) {
			@Override
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		setCustomTable(tblDSDV);
		tblDSDV.setRowHeight(21);
		JScrollPane scrTable = setCustomScrollPaneNotScroll(tblDSDV);
		scrTable.setBounds(10, 25, 1430, 410);
		pnlTable.add(scrTable);
		add(pnlTable);
		daoCTHoaDon = new CT_HoaDon_Dao();
        daoKhachHang = new KhachHang_Dao();
        
		
		
		JPanel pnlThongBao = new JPanel();
		pnlThongBao.setForeground(Color.BLACK);
		pnlThongBao.setBackground(Color.WHITE);
		pnlThongBao.setLayout(null);
		pnlThongBao.setOpaque(false);
		pnlThongBao.setBounds(54, 800, 1448, 200);
		setBorderTitle(pnlThongBao, "THÔNG TIN THỐNG KÊ");
		add(pnlThongBao);
		
		JLabel lbTenDV = new JLabel("Tên Dịch Vụ:");
		lbTenDV.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lbTenDV.setBounds(40, 50, 200, 25);
		pnlThongBao.add(lbTenDV);
		
		JTextField tfTenDV = new JTextField();
		tfTenDV.setFont(new Font("Times New Roman", Font.BOLD, 17));
		tfTenDV.setBounds(150, 50, 200, 25);
		pnlThongBao.add(tfTenDV);
		
		JLabel lbSoLuongDV = new JLabel("Số Lượng Dịch Vụ:");
		lbSoLuongDV.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lbSoLuongDV.setBounds(550, 50, 200, 25);
		pnlThongBao.add(lbSoLuongDV);
		
		JTextField tfSoLuong = new JTextField();
		tfSoLuong.setFont(new Font("Times New Roman", Font.BOLD, 17));
		tfSoLuong.setBounds(700, 50, 200, 25);
		pnlThongBao.add(tfSoLuong);
		
		JLabel lbTongTien = new JLabel("Thành Tiền:");
		lbTongTien.setFont(new Font("Times New Roman", Font.BOLD, 17));
		lbTongTien.setBounds(1100, 50, 200, 25);
		pnlThongBao.add(lbTongTien);
		
		JTextField tfTongTien = new JTextField();
		tfTongTien.setFont(new Font("Times New Roman", Font.BOLD, 17));
		tfTongTien.setBounds(1210, 50, 200, 25);
		pnlThongBao.add(tfTongTien);
	}
	public void setCustomTable(JTable tbl) {
		tbl.setFont(fontNormal);
		tbl.getTableHeader().setFont(fontBold);
		tbl.getTableHeader().setForeground(Color.decode("#000000"));
		tbl.getTableHeader().setBackground(Color.decode("#1995AD"));
	}
	
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
	
	public void setBorderTitle(JScrollPane sbrDanhSachPhong, String title) {
		Border border = BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.decode("#1995AD"), 2),
				title, TitledBorder.LEADING, TitledBorder.TOP, fontBold, Color.black);
		sbrDanhSachPhong.setBorder(border);
	}
	private void btnThongKeActionPerformed(ActionEvent evt) {
//	    if(cbLocTheo.getSelectedItem().equals("Chọn tiêu chí")) {
//	    	JOptionPane.showMessageDialog(this, "Vui lòng chọn thống kê theo tiêu chí!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//	    	return;
//	    } else if (cbLocTheo.getSelectedItem().equals("Thống kê theo ngày")) {
//	        if (dateChooserNgay.getDate() == null) {
//	            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày bạn muốn thống kê", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
//	        } else {	
//	        	java.util.Date datetime = dateChooserNgay.getDate();
//	            java.sql.Date ngay = new java.sql.Date(datetime.getTime());
//	            ArrayList<CT_HoaDon> dsCTHD = daoCTHoaDon.getCTHDTheoNgay(ngay);
//	            for (CT_HoaDon cthd : dsCTHD) {
//	                List<CT_HoaDon> dsHD = daoCTHoaDon.loadDSCTHoaDonTheoMa(cthd.getMaHoaDon());             
//					DichVu dv = daoDichVu.getDichVuTheoMa(cthd.getDichVu().getMaDichVu());
//	                if (dsHD == null) {
//	                    model.addRow(new Object[] {	                
//	                        cthd.maDichVu(), dv.getTenDichVu(), cthd.getTongSoLuongDV()
//	                    });
//	                } else {	                    	                  
//	                	for (CT_HoaDon hd : dsHD) {
//	                        // Thực hiện các hành động với hd
//	                        model.addRow(new Object[] {
//	                            cthd.maDichVu(), dv.getTenDichVu(), cthd.getTongSoLuongDV()
//	                        });
//	                    }
//	                }
//	            }
//	        }
//	    } else if(cbLocTheo.getSelectedItem().equals("Thống kê theo tháng")){
//	    	
//	    } else if(cbLocTheo.getSelectedItem().equals("Thống kê theo năm")){
//	    	
//	    }
	}
	private void cbLocTheoActionPerformed(ActionEvent evt) {
	    String selectedOption = cbLocTheo.getSelectedItem().toString();
	    if (selectedOption.isEmpty()) {
	        ychNamTK.setEnabled(false);
	        mchThangTK.setEnabled(false);
	        dateChooserNgay.setEnabled(false);
	    } else if (selectedOption.equals("Thống kê theo tháng")) {
	        ychNamTK.setEnabled(true);
	        mchThangTK.setEnabled(true);
	        dateChooserNgay.setEnabled(false);
	    } else if (selectedOption.equals("Thống kê theo năm")) {
	        ychNamTK.setEnabled(true);
	        mchThangTK.setEnabled(false);
	        dateChooserNgay.setEnabled(false);
	    } else if (selectedOption.equals("Thống kê theo ngày")) {
	        ychNamTK.setEnabled(false);
	        mchThangTK.setEnabled(false);
	        dateChooserNgay.setEnabled(true);
	    }
	}
}