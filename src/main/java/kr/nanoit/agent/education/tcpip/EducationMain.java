package kr.nanoit.agent.education.tcpip;

<<<<<<< HEAD
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class EducationMain {
    public static void main(String[] args) {

        String id = "ascagent02";
        String pw = "I6Iqro6A24L5Ngq";
        String enckey = "gAPc4gkSF7FBB6cb";
        URL url = null;
        String inputLine;
        String buffer = "";

        Encrypt encrypt = new Encrypt(enckey);
        String encryptpw = encrypt.DataEncrypt(pw);
        System.out.println("암호화 성공 : " + encryptpw);

        try {
            url = new URL("http://ntmdist.nanoit.kr:80");

            //서버에 접근
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            System.out.println("[서버 접속 성공]");

            //입력 스트림 사용 여부
            conn.setDoInput(true);
            //출력스트림 사용 여부
            conn.setDoOutput(true);

            //??? 방식
            conn.setRequestMethod("POST");

            //conn객체에 있는 outputStream 사용 허용
            conn.setDoOutput(true);

            //서버로 값 전달(요청)
            OutputStream os = conn.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(os);

            System.out.println("====== 서버로 전송되는 값 ====== ");
            System.out.println("ID : " + id);
            System.out.println("PASSWORD : " + encryptpw);
            System.out.println("================================ ");
            writer.write("id=" + id + "&password=" + encryptpw.getBytes("UTF-8"));
            //writer.write("&password=" + encryptpw);

            writer.close();


            int code = conn.getResponseCode();
            System.out.println("응답 코드 : " + code);
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //서버에서 받은 값 br에 담김

            while ((inputLine = br.readLine()) != null) {
                buffer += inputLine.trim();
            }
            os.close();

            System.out.println(buffer);

=======
import org.w3c.dom.Document;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EducationMain {

    public static void main(String[] args) throws Exception {

        String id = "ascagent02";
        String pw = "I6Iqro6A24L5Ngq";
        String enckey = "gAPc4gkSF7FBB6cbGDZs4u7o1h4S";
        String encryptpw = "";
        String inputLine = "";
        String xmlresult = "";
        Map<String, String> parsedData;



        //패스워드 암호화 ===================================================================================================================
        System.out.println("패스워드 암호화");
        Crypt crypt = new Crypt(enckey);
        crypt.initialize();
        try {

            encryptpw = crypt.DataEncrypt(pw);
            System.out.println("암호화 성공 : " + encryptpw);

        }catch (Exception e){
            System.out.println("암호화 실패");
            System.out.println("DataEncrypt Exception Err : " + e);
        }
        //================================================================================================================================
        //서버 HTTP 통신 ===================================================================================================================
        System.out.println();
        System.out.println();
        System.out.println("HTTP 통신");

        try {
            HttpConnect httpConnect = new HttpConnect(id, pw, encryptpw);
            xmlresult = httpConnect.Connect();
>>>>>>> 362911c (2022-02-07 init)

        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e + "=> HttpURLConnection fail");
        }
<<<<<<< HEAD
=======
        //================================================================================================================================
        // XML 결과 파싱 ==================================================================================================================
        System.out.println();
        System.out.println();
        System.out.println("XML 파싱");
        System.out.println(xmlresult);
        XMLParser xmlParser  = new XMLParser(xmlresult);
        parsedData = new HashMap<>();
        try{
            parsedData = xmlParser.parsing();
        }catch (Exception e){
            System.out.println("Parsing Exception Err : " + e);
            e.printStackTrace();
        }
        System.out.println("XML에서 추출한 Data : " + parsedData);
        //================================================================================================================================
        // TCP 소켓 통신 ===================================================================================================================
        System.out.println();
        System.out.println();
        System.out.println("TCP 소켓 통신");

        try {
            SocketConnect socketConnect = new SocketConnect(parsedData.get("IP"), Integer.parseInt(parsedData.get("Port")));
            socketConnect.Connection(id, encryptpw);
        }catch (Exception e){
            System.out.println(e + " => TCP Socket Connect fail" );
            e.printStackTrace();
        }
        //================================================================================================================================
>>>>>>> 362911c (2022-02-07 init)

    }
}
