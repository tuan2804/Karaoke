package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.MenuItem;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import entity.Category;
import entity.NhanVien;
import entity.TaiKhoan;

	


public class ConvertMenu {
	private JPanel root;
	private String kinSelected ="";
	private NhanVien headerNV;
	private List<Category> listItem=null;
	
	public void getNV(NhanVien nv) {
		this.headerNV = nv;
	}
	
	public ConvertMenu(JPanel jpnRoot) {
		this.root=jpnRoot;
	}
	public void setView(MenuItem menu) {
		kinSelected = "trangChu";

		root.removeAll();
		root.setLayout(new BorderLayout());
		root.add(new pnTrangChu());
		root.validate();
		root.repaint();
		
	}
	public void setEvent(List<Category> listItem) {
		this.listItem = listItem;
		for(Category item : listItem) {
			item.getMenu().addMouseListener(new LabelEvent(item.getKind(), null));
		}
	}
	
	public class LabelEvent implements MouseListener{
		private JPanel node;
		private String kind;
		private JPanel jpnItem;
		private MenuItem menu;

		public LabelEvent(String kind, MenuItem menuItem) {
			this.kind = kind;
			this.menu = menuItem;
		}
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			
//				menu.setBackground(new Color(0, 128, 64));
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if(!kinSelected.equalsIgnoreCase(kind)) {
				
			}
		}
		private void setChangeBackground(String kind) {
			for (Category item : listItem) {
				if(item.getKind().equalsIgnoreCase(kind)) {
					item.getMenu().setBackground(Color.decode("#F1F1F2"));	
				}
				else {
					item.getMenu().setBackground(Color.decode("#1995AD"));
				}
			}
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			switch(kind) {
			case "TrangChu":
				node = new pnTrangChu();
				break;
			case "Phong":
				node = new pnPhong();
				break;
			case "loaiPhong":
				node = new pnLoaiPhong();
				break;
			case "nhanVien":
				node = new pnNhanVien();
				break;
			case "dichVu":
				node = new pnDichVu();
				break;
			case "loaiDV":
				node = new pnLoaiDichVu();
				break;
			case "khachHang":
				node = new pnKhachHang();
				break;
			case "cTHoaDon":
				node = new pnCTHoaDon();
				break;
			case "tKDoanhThu":
				node = new pnTKDoanhThu();
				break;
			case "tKKHGheQuan":
				node = new pnTKKhachHang();
				break;
			case "tKDichVu":
				node = new pnTKDichVuSuDung();
				break;
			case "datPhong":
				node = new pnDatPhong();
				break;
			case "timKiemPhong":
				node = new pnTimKiemPhong();
				break;
			case "timKiemNhanVien":
				node = new pnTimKiemNhanVien();
				break;
			case "timKiemKH":
				node = new pnTimKiemKhachHang();
				break;
			case "lapHoaDon":
				node = new pnNhanPhong();
				break;
			case "thanhToan":
				node = new pnThanhToan();
				break;
			case "timKiemDV":
				node = new pnTimKiemDichVu();
				break;
			default:
				//
				break;
			}
			root.removeAll();
			root.setLayout(new BorderLayout());
			root.add(node);
			root.validate();
			root.repaint();
			setChangeBackground(kind);
		}
	}

	
}


