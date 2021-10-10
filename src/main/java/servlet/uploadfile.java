package servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.FileDAO;
import model.User;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
		maxFileSize = 1024 * 1024 * 10, // 10MB
		maxRequestSize = 1024 * 1024 * 50) // 50MB

public class uploadfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String appPath = request.getServletContext().getRealPath("");
        appPath = appPath.replace('\\', '/');
        String fullSavePath = null;
        if (appPath.endsWith("/")) {
            fullSavePath = appPath + "uploads";
        } else {
            fullSavePath = appPath + "/" + "uploads";
        }
        
        File fileSaveDir = new File(fullSavePath);
        if (!fileSaveDir.exists()) {
            fileSaveDir.mkdir();
        }
        String fileName ="";
		for (Part part : request.getParts()) {
			fileName = extractFileName(part);
			// refines the fileName in case it is an absolute path
			fileName = new File(fileName).getName();
			System.out.print(fullSavePath + "/" + fileName);
			part.write(fullSavePath + "/" + fileName);
		}
		// Lấy thông tin rồi ghi vào database file
		String temp_user_id = request.getParameter("user_id");
		System.out.print(temp_user_id);
		int user_id = Integer.parseInt(temp_user_id);
//		int user_id = 1;
		FileDAO file = new FileDAO();
		file.addFile(fileName, 1024, user_id, ".pdf");
		request.setAttribute("message", "Tải file lên thành công!");
		getServletContext().getRequestDispatcher("/dashboard.jsp").forward(request, response);
	}
	
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				return s.substring(s.indexOf("=") + 2, s.length() - 1);
			}
		}
		return "";
	}

	public File getFolderUpload() {
		File folderUpload = new File(System.getProperty("user.home") + "/Uploads");
		if (!folderUpload.exists()) {
			folderUpload.mkdirs();
		}
		return folderUpload;
	}
}