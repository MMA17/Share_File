package control;
 
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import model.User;
import model.file;

 
public class UploadFunc {
    private final String boundary;
    private static final String LINE_FEED = "\r\n";
    private HttpURLConnection httpConn;
    private OutputStream outputStream;
    private PrintWriter writer;
 

    public void MultipartUploadUtility() {
    }
    
    public UploadFunc(String requestURL, String charset)
            throws IOException {
 
        // creates a unique boundary based on time stamp
        boundary = "===" + System.currentTimeMillis() + "===";
 
        URL url = new URL(requestURL);
        httpConn = (HttpURLConnection) url.openConnection();
        httpConn.setDoOutput(true); // indicates POST method
        httpConn.setDoInput(true);
        httpConn.setRequestProperty("Content-Type",
                "multipart/form-data; boundary=" + boundary);
        outputStream = httpConn.getOutputStream();
        writer = new PrintWriter(new OutputStreamWriter(outputStream, charset),
                true);
    }
 

    public void addFilePart(String fieldName, File uploadFile)
            throws IOException {
        String fileName = uploadFile.getName();
        writer.append("--" + boundary).append(LINE_FEED);
        writer.append(
                "Content-Disposition: form-data; name=\"" + fieldName
                        + "\"; filename=\"" + fileName + "\"")
                .append(LINE_FEED);
        writer.append(
                "Content-Type: "
                        + URLConnection.guessContentTypeFromName(fileName))
                .append(LINE_FEED);
        writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.flush();
    }
    
    public file getFileInfo(String fieldName, File uploadFile, User user) {
        
        String fileName = uploadFile.getName();
        int size = httpConn.getContentLength();
        String file_ext = fileName.substring(fileName.lastIndexOf(".")+1, fileName.length());
        file file = new file();
        file.setFile_name(fileName);
        file.setOwner(user.getUser_id());
        file.setSize(size);
        file.setFile_extension(file_ext);
        return file;
    }
 
    public void writeFileBytes(byte[] bytes, int offset, int length)
            throws IOException {
        outputStream.write(bytes, offset, length);
    }
 

    public void finish() throws IOException {
        outputStream.flush();
        writer.append(LINE_FEED);
        writer.flush();
 
        writer.append(LINE_FEED).flush();
        writer.append("--" + boundary + "--").append(LINE_FEED);
        writer.close();
 
        // check server's status code first
        int status = httpConn.getResponseCode();
        if (status == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    httpConn.getInputStream()));
            while (reader.readLine() != null) {
                // do nothing, but necessary to consume response from the server
            }
            reader.close();
            httpConn.disconnect();
        } else {
            throw new IOException("Server returned non-OK status: " + status);
        }
    }
}