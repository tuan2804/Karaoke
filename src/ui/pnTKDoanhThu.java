package ui;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;

import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JComboBox;
import java.awt.FlowLayout;
import com.toedter.calendar.JDateChooser;

public class pnTKDoanhThu extends JPanel {
	private Image imgBG = new ImageIcon(pnPhong.class.getResource("/image/giaoDien.png")).getImage().getScaledInstance(350, 800, Image.SCALE_SMOOTH);
	private ImageIcon iconThongKe = new ImageIcon(pnTKDoanhThu.class.getResource("/image/thongke1.png"));
	private ImageIcon iconDoanhThu = new ImageIcon(pnTKDoanhThu.class.getResource("/image/doanhthu.png"));
	private ImageIcon iconHoaDon = new ImageIcon(pnTKDoanhThu.class.getResource("/image/hoadon1.png"));
	private Font fontBold = new Font("Dialog", Font.BOLD, 14);
	private Font fontNormal = new Font("Dialog", Font.PLAIN, 14);
	private GradientPaint gra = new GradientPaint(0, 0, Color.decode("#A1D6E2"), getWidth(), 0, Color.decode("#F1F1F2"));
	private JPanel pnlBieuDo;
	private static JMonthChooser mchThangTK;
	private JYearChooser ychNamTK;
	private JDateChooser dateChooserNgay;
	private JButton btnThongKe;
	private JComboBox cbLocTheo;
	private ChartPanel chartPanel;
	private DefaultCategoryDataset dataset;
    private static JFreeChart barChart;
    private JTextField tfTongHoaDon, tfDoanhThu;
    private HoaDon_Dao daoHoaDon;
    private CT_HoaDon_Dao daoCTHoaDon;
    private DichVu_Dao daoDichVu;
    private Phong_Dao daoPhong;
    private KhachHang_Dao daoKhachHang;
    private NhanVien_Dao daoNhanVien;
    private JLabel lblNam, lblLocTheo, lblIcon1, lblIcon2;
    private DefaultTableModel model;
    private JTable tblDoanhThu;
    private JLabel lblTheoNgay;
    
    
	public pnTKDoanhThu() {
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
		
		pnlBieuDo = new JPanel();
		pnlBieuDo.setOpaque(false);
		setBorderTitle(pnlBieuDo, "Biểu đồ doanh thu");
		pnlBieuDo.setBounds(54, 271, 1448, 758);
		add(pnlBieuDo);
		pnlBieuDo.setBackground(new Color(255,255,255));

		// Sử dụng GridBagLayout
		GridBagLayout gridBagLayout = new GridBagLayout();
		pnlBieuDo.setLayout(gridBagLayout);
		
		JPanel pnlTimKiem = new JPanel();
		setBorderTitle(pnlTimKiem, "Thống kê theo tiêu chí");
		pnlTimKiem.setBounds(54, 99, 1448, 161);
		add(pnlTimKiem);
		pnlTimKiem.setLayout(null);
		
		tfTongHoaDon = new JTextField();
		tfTongHoaDon.setBorder(null);
		tfTongHoaDon.setOpaque(false);
		tfTongHoaDon.setEnabled(false);
		tfTongHoaDon.setDisabledTextColor(Color.RED);
		tfTongHoaDon.setBounds(218, 125, 156, 25);
		pnlTimKiem.add(tfTongHoaDon);
		tfTongHoaDon.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfTongHoaDon.setColumns(10);
		
		JLabel lblTongHoaDon = new JLabel("Tổng số hóa đơn:");
		lblTongHoaDon.setBounds(84, 125, 124, 25);
		pnlTimKiem.add(lblTongHoaDon);
		lblTongHoaDon.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		tfDoanhThu = new JTextField();
		tfDoanhThu.setBounds(629, 125, 205, 25);
		pnlTimKiem.add(tfDoanhThu);
		tfDoanhThu.setBorder(null);
		tfDoanhThu.setOpaque(false);
		tfDoanhThu.setEnabled(false);
		tfDoanhThu.setDisabledTextColor(Color.RED);
		tfDoanhThu.setFont(new Font("Tahoma", Font.BOLD, 15));
		tfDoanhThu.setColumns(10);
		
		JLabel lblDoanhThu = new JLabel("Doanh thu:");
		lblDoanhThu.setBounds(530, 125, 89, 25);
		pnlTimKiem.add(lblDoanhThu);
		lblDoanhThu.setFont(new Font("Tahoma", Font.BOLD, 15));
		
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
		cbLocTheo.addItem("");
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
		
		lblIcon1 = new JLabel(iconHoaDon);
		lblIcon1.setBounds(40, 117, 46, 40);
		pnlTimKiem.add(lblIcon1);
		
		lblIcon2 = new JLabel(iconDoanhThu);
		lblIcon2.setBounds(489, 117, 46, 40);
		pnlTimKiem.add(lblIcon2);
		
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
	    
	    JLabel lblTitle = new JLabel("THỐNG KÊ DOANH THU");
		lblTitle.setForeground(Color.BLACK);
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Times New Roman", Font.BOLD, 25));
		lblTitle.setBounds(569, 20, 312, 36);
		pnlTitle.add(lblTitle);
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
	
	private JFreeChart createChartTheoThang() {
	    int thang = mchThangTK.getMonth() + 1;
	    String s = String.valueOf(thang);
	    DefaultCategoryDataset dataset = (DefaultCategoryDataset) createDatasetTheoThang();

	    JFreeChart barChart = ChartFactory.createBarChart(
	            "BIỂU ĐỒ DOANH THU KARAOKE NICE THÁNG " + s,
	            "Ngày", "Doanh Thu (VNĐ)",
	            dataset, PlotOrientation.VERTICAL, false, false, false);

	    customizeChart(barChart);

	    return barChart;
	}

	private JFreeChart createChartTheoNam() {
	    int nam = ychNamTK.getYear();
	    String s = String.valueOf(nam);
	    DefaultCategoryDataset dataset = (DefaultCategoryDataset) createDatasetTheoNam();

	    JFreeChart barChart = ChartFactory.createBarChart(
	            "BIỂU ĐỒ DOANH THU KARAOKE NICE NĂM " + s,
	            "Tháng", "Doanh thu (VNĐ)",
	            dataset, PlotOrientation.VERTICAL, false, false, false);

	    customizeChart(barChart);

	    return barChart;
	}
	
	
	
	private void customizeChart(JFreeChart chart) {
	    chart.setBackgroundPaint(Color.WHITE);
	    chart.getTitle().setPaint(Color.BLACK);

	    CategoryPlot plot = (CategoryPlot) chart.getPlot();
	    plot.setBackgroundPaint(Color.WHITE);
	    plot.setRangeGridlinePaint(Color.GRAY);
	    plot.getDomainAxis().setTickLabelPaint(Color.BLACK);
	    plot.getRangeAxis().setTickLabelPaint(Color.BLACK);

	    BarRenderer renderer = (BarRenderer) plot.getRenderer();
	    renderer.setSeriesPaint(0, Color.BLUE);

	    Font titleFont = new Font("SansSerif", Font.BOLD, 18);
	    Font axisLabelFont = new Font("SansSerif", Font.BOLD, 14);
	    Font tickLabelFont = new Font("SansSerif", Font.BOLD, 12);

	    chart.getTitle().setFont(titleFont);
	    plot.getDomainAxis().setLabelFont(axisLabelFont);
	    plot.getRangeAxis().setLabelFont(axisLabelFont);
	    plot.getDomainAxis().setTickLabelFont(tickLabelFont);
	    plot.getRangeAxis().setTickLabelFont(tickLabelFont);
	}
	
	private CategoryDataset createDatasetTheoThang() {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int thang = mchThangTK.getMonth()+1;
		int nam = ychNamTK.getYear();
		int k = 0;
		double tongDT =0;
		for(int j=1;j<=31;j++) {
			ArrayList<HoaDon> dsHD = daoHoaDon.getHDTheoThang(thang, nam,j);
			int i = 0;
			double tongDTN=0;

			for(HoaDon hd : dsHD) {
				List<CT_HoaDon> dsCTHD = daoCTHoaDon.loadDSCTHoaDonTheoMa(hd.getMaHoaDon());
				if(dsCTHD==null) {
					double tongtien = 0;
					tongtien = tongtien + hd.getGiaPhong();
					i++;
					tongDTN = tongDTN + tongtien;
				}else {
					double tongtien =0;
					double tienDV = 0;
					for(CT_HoaDon cthd : dsCTHD) {
						DichVu dv = daoDichVu.getDichVuTheoMa(cthd.getDichVu().getMaDichVu());
						tienDV = cthd.getSoLuongDat()*dv.getGiaBan() + tienDV;
					}
					tongtien =tienDV + hd.getGiaPhong();
					i++;
					tongDTN = tongDTN + tongtien;
				}
			}
			String ngay = String.valueOf(j);
	        dataset.addValue(tongDTN, "VNĐ", ngay);
	        k = k+i;
	        tongDT = tongDT + tongDTN;
			}
		DecimalFormat formatter = new DecimalFormat("###,###,###");
		tfTongHoaDon.setText(String.valueOf(k));
		tfDoanhThu.setText(formatter.format(tongDT)+" VNĐ");
        return dataset;
	}
		
	private CategoryDataset createDatasetTheoNam() {
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		int nam =ychNamTK.getYear();
		int m =0;
		double tongDTNam=0;
    	for(int l=1;l<=12;l++) {
    		int k=0;
    		double tongDTT=0;
    		for(int j=1;j<=31;j++) {
    			ArrayList<HoaDon> dsHD = daoHoaDon.getHDTheoThang(l, nam,j);
    			int i = 0;
    			double tongDTN=0;
    			for(HoaDon hd : dsHD) {
    				List<CT_HoaDon> dsCTHD = daoCTHoaDon.loadDSCTHoaDonTheoMa(hd.getMaHoaDon());
    				if(dsCTHD==null) {
    					double tongtien =0;
    					tongtien =tongtien + hd.getGiaPhong();
    					i++;
    					tongDTN = tongDTN + tongtien;
    				}else {
    					double tongtien =0;
    					double tienDV = 0;
    					for(CT_HoaDon cthd : dsCTHD) {
    						DichVu dv = daoDichVu.getDichVuTheoMa(cthd.getDichVu().getMaDichVu());
    						tienDV = cthd.getSoLuongDat()*dv.getGiaBan() + tienDV;
    					}
    					tongtien =tienDV + hd.getGiaPhong();
    					i++;
    					tongDTN = tongDTN + tongtien;
    				}
    			}
    			tongDTT = tongDTT + tongDTN;
    	        k = k+i;
    			}
    		String thang = String.valueOf(l);
	        dataset.addValue(tongDTT, "VNĐ", thang);
    		m = m+k;
    		tongDTNam = tongDTNam + tongDTT;	
    	}	
    	DecimalFormat formatter = new DecimalFormat("###,###,###");
		tfTongHoaDon.setText(String.valueOf(m));
		tfDoanhThu.setText(formatter.format(tongDTNam)+" VNĐ");
        return dataset;
	}
	
	
	
	private void btnThongKeActionPerformed(ActionEvent evt) {
	    pnlBieuDo.removeAll();
	    if(cbLocTheo.getSelectedItem().equals("")) {
	    	JOptionPane.showMessageDialog(this, "Vui lòng chọn thống kê theo tiêu chí!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	    	return;
	    } else if (cbLocTheo.getSelectedItem().equals("Thống kê theo tháng")) {
	        // Tạo biểu đồ theo tháng và hiển thị
	        JFreeChart chart = createChartTheoThang();
	        ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setOpaque(false);
	        chartPanel.setPreferredSize(new Dimension(1400, 700));
	        pnlBieuDo.setLayout(new BorderLayout()); // Thêm dòng này để đảm bảo layout hoạt động chính xác
	        pnlBieuDo.add(chartPanel, BorderLayout.CENTER);
	    } else if (cbLocTheo.getSelectedItem().equals("Thống kê theo năm")) {
	        // Tạo biểu đồ theo năm và hiển thị
	        JFreeChart chart = createChartTheoNam();
	        ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setOpaque(false);
	        chartPanel.setPreferredSize(new Dimension(1400, 700));
	        pnlBieuDo.setLayout(new BorderLayout()); // Thêm dòng này để đảm bảo layout hoạt động chính xác
	        pnlBieuDo.add(chartPanel, BorderLayout.CENTER);
	    } else if (cbLocTheo.getSelectedItem().equals("Thống kê theo ngày")) {
	        if (dateChooserNgay.getDate() == null) {
	            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày bạn muốn thống kê", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            // Khởi tạo model nếu chưa được khởi tạo
	            DefaultTableModel model = new DefaultTableModel();
	            model.addColumn("Mã hóa đơn");
	            model.addColumn("Họ tên khách hàng");
	            model.addColumn("Tên phòng");
	            model.addColumn("Doanh thu");

	            java.util.Date datetime = dateChooserNgay.getDate();
	            java.sql.Date ngay = new java.sql.Date(datetime.getTime());
	            ArrayList<HoaDon> dsHD = daoHoaDon.getHDTheoNgay(ngay);
	            double tongDT = 0;
	            DecimalFormat formatter = new DecimalFormat("###,###,###");
	            for (HoaDon hd : dsHD) {
	                List<CT_HoaDon> dsCTHD = daoCTHoaDon.loadDSCTHoaDonTheoMa(hd.getMaHoaDon());
	                Phong p = daoPhong.getPhongTheoMa(hd.getPhong().getMaPhong());
	    	        NhanVien nv = daoNhanVien.getNVtheoMa(hd.getNhanVien().getMaNhanVien());
	    	        KhachHang kh = daoKhachHang.getMaKhachHangTheoMa(hd.getKhachHang().getMaKhachHang());
	                if (dsCTHD == null) {
	                    model.addRow(new Object[] {
	                    		
	                            hd.getMaHoaDon(), kh.getHoTen(), p.getMaPhong(), formatter.format(hd.getGiaPhong())
	                    });
	                    tongDT += hd.getGiaPhong();
	                } else {
	                    double tongTien = 0;
	                    double tienDV = 0;
	                    for (CT_HoaDon cthd : dsCTHD) {
	                        DichVu dv = daoDichVu.getDichVuTheoMa(cthd.getDichVu().getMaDichVu());
	                        tienDV += cthd.getSoLuongDat() * dv.getGiaBan();
	                    }
	                    tongTien = tienDV + hd.getGiaPhong();
	                    model.addRow(new Object[] {
	                    		 hd.getMaHoaDon(), kh.getHoTen(), p.getMaPhong(), formatter.format(tongTien)
	                    });
	                    tongDT += tongTien;
	                }
	            }
	            String thd = String.valueOf(dsHD.size());
	            tfTongHoaDon.setText(thd);
	            tfDoanhThu.setText(formatter.format(tongDT) + " VNĐ");

	            
	            JTable table = new JTable(model);
	            JScrollPane scrollPane = new JScrollPane(table);
	            scrollPane.setOpaque(false);
	            table.setFont(new Font("Tahoma", Font.PLAIN, 14));
	            scrollPane.getViewport().setOpaque(false);
	            scrollPane.getViewport().setBackground(Color.WHITE);
	            JTableHeader tableHeader = table.getTableHeader();
	    		tableHeader.setPreferredSize(new Dimension(0, 30));

	    		table.setRowHeight(30);

	    		
	    		tableHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
	    		tableHeader.setForeground(Color.decode("#000000"));
	    		tableHeader.setBackground(Color.decode("#1995AD"));
	            pnlBieuDo.setLayout(new BorderLayout());
	            pnlBieuDo.add(scrollPane, BorderLayout.CENTER);
	        }
	    }

	    pnlBieuDo.revalidate();
	    pnlBieuDo.repaint();
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
