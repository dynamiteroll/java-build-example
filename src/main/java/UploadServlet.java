import java.io.*;
import java.time.Clock;
import java.util.Arrays;
import java.net.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

//import javax.servlet.http.*;
//import javax.servlet.*;

import org.json.simple.JSONObject;



public class UploadServlet extends HttpServlet {

   protected void doPost(HttpServletRequest request, HttpServletResponse response) {
      try {
         ObjectInputStream in = (ObjectInputStream) request.getInputStream();
         String caption = (String) in.readObject();
         
         Date myDate = (Date) in.readObject();
         
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         byte[] content = (byte[])in.readObject();
         baos.write(content);
         
         OutputStream outputStream = new FileOutputStream(new File(caption + ".jpg"));
         baos.writeTo(outputStream);
         outputStream.close();

         JSONObject jo = new JSONObject();
         
         Map<String, Comparable> imageMap = new LinkedHashMap<String, Comparable>(3);
         imageMap.put("caption", caption);
         imageMap.put("date", myDate);
         imageMap.put("image", caption + ".jpg");
         
         jo.put("photogallery", imageMap);

         
         PrintWriter out = new PrintWriter(response.getOutputStream(), true);
         out.write(jo.toJSONString());
         out.flush();
         out.close();
      } catch (Exception ex) {
         System.err.println(ex);
      }
   }

}