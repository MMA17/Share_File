package servlet;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "download", urlPatterns = { "/download" })
public class downloadfile extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public downloadfile() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		ServletContext context = getServletContext();
		String fileName = request.getParameter("file");
		String fullPath = context.getRealPath("/upload/" + fileName);
		Path path = Paths.get(fullPath);
		byte[] data = Files.readAllBytes(path);
		// Thiết lập thông tin trả về
		response.setContentType("application/octet-stream");
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setContentLength(data.length);
		InputStream inputStream = new BufferedInputStream(new ByteArrayInputStream(data));
		// Ghi file ra response outputstream.
		OutputStream outStream = response.getOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, bytesRead);
		}
		inputStream.close();
		outStream.close();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}