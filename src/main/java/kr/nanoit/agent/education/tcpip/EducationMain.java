package kr.nanoit.agent.education.tcpip;

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


        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e + "=> HttpURLConnection fail");
        }

    }
}