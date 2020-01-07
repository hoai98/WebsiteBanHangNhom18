package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import model.ConnectToDatabase;
import model.KhachHang;

public class KhachHangDAO implements ObjectDAO {
	public static Map<String, KhachHang> mapKhachHang = loadData();

	public KhachHangDAO() {

	}

	private static Map<String, KhachHang> loadData() {
		Map<String, KhachHang> mapTemp = new HashMap<>();
		try {
			ResultSet rs = new ConnectToDatabase().selectData("select * from TaiKhoan");
			while (rs.next()) {
				String taiKhoan = rs.getString(1);
				String matKhau = rs.getString(2);
				String ten = rs.getString(3);
				String gioiTinh = rs.getString(4);
				String soDienThoai = rs.getString(5);
				String email = rs.getString(6);
				String ngaySinh = rs.getString(7);
				String diaChi = rs.getString(8);
				String soLuongMua = rs.getString(9);
				String role = rs.getString(10);
				KhachHang kh = new KhachHang(ten, taiKhoan, matKhau, gioiTinh, soDienThoai, email, ngaySinh, diaChi,
						soLuongMua, role);
				mapTemp.put(kh.getTaiKhoan(), kh);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return mapTemp;
	}

	public boolean checkLogin(String userName, String passWord) {
		KhachHang kh = mapKhachHang.get(userName);
		if (kh != null) {
			if (kh.getMatKhau().equals(passWord)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean add(Object obj) {
		KhachHang kh = (KhachHang) obj;
		mapKhachHang.put(kh.getTaiKhoan(), kh);
		String sql = "insert into TaiKhoan values (?,?,?,?,?,?,?,?,?,?)";
		Connection connect = ConnectToDatabase.getConnect();
		try {
			PreparedStatement ppstm = connect.prepareStatement(sql);
			ppstm.setString(1, kh.getTaiKhoan());
			ppstm.setString(2, kh.getMatKhau());
			ppstm.setString(3, kh.getTen());
			ppstm.setString(4, kh.getGioiTinh());
			ppstm.setString(5, kh.getSoDienThoai());
			ppstm.setString(6, kh.getEmail());
			ppstm.setString(7, kh.getNgaySinh());
			ppstm.setString(8, kh.getDiaChi());
			ppstm.setString(9, kh.getSoLuongMua());
			ppstm.setString(10, kh.getRole());
			ppstm.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("error when add customer :" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean del(String id) {
		mapKhachHang.remove(id);
		try {
			new ConnectToDatabase().excuteSql("delete from TaiKhoan where taikhoan='" + id + "'");
			return true;
		} catch (Exception e) {
			System.out.println("error when delete customer :" + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean edit(String id, Object obj) {
		KhachHang kh = (KhachHang) obj;
		mapKhachHang.replace(id, kh);
		String sql = "update taikhoan set Tenkhachhang=?,Matkhau=?,Gioitinh=?,Sodienthoai=?,Email=?,Ngaysinh=?,Diachi=?,Soluotmua=?,Role=? where Tentaikhoan='"
				+ id + "'";
		Connection connect = ConnectToDatabase.getConnect();
		try {
			PreparedStatement ppstm = connect.prepareStatement(sql);
			ppstm.setString(1, kh.getTen());
			ppstm.setString(2, kh.getMatKhau());
			ppstm.setString(3, kh.getGioiTinh());
			ppstm.setString(4, kh.getSoDienThoai());
			ppstm.setString(5, kh.getEmail());
			ppstm.setString(6, kh.getNgaySinh());
			ppstm.setString(7, kh.getDiaChi());
			ppstm.setString(8, kh.getSoLuongMua());
			ppstm.setString(9, kh.getRole());
			ppstm.setString(10, id);
			ppstm.executeUpdate();
			return true;
		} catch (SQLException e) {
			System.out.println("error when edit customer :" + e.getMessage());
		}
		return false;
	}

	public boolean changePass(String userName, String newPass) {
		KhachHang kh = mapKhachHang.get(userName);
		if (kh != null) {
			kh.setMatKhau(newPass);
			mapKhachHang.replace(kh.getTaiKhoan(), kh);
			edit(kh.getTaiKhoan(), kh);
			return true;
		} else {
			return false;
		}
	}

	public static boolean sendMail(String to, String subject, String text) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("shopphoneltw@gmail.com", "abcdabcd");
			}
		});
		try {
			Message message = new MimeMessage(session);
			message.setHeader("Content-Type", "text/plain; charset=UTF-8");
			message.setFrom(new InternetAddress("shopphoneltw@gmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
		} catch (MessagingException e) {
			return false;
		}
		return true;
	}
	public boolean passwordRecovery(String userName,String email){
		KhachHang kh = mapKhachHang.get(userName);
		if(kh!=null){
			sendMail(email, "passWord recorvery", kh.getMatKhau());
			return true;
		}else{
			System.out.println("No account");
		}
		return false;
	}

	public static void main(String[] args) {
		System.out.println(new KhachHangDAO().passwordRecovery("anhdinh", "nguyenlephong1997@gmail.com"));
	}
}
