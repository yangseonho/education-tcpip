package kr.nanoit.agent.education.tcpip;


import java.io.*;
import java.net.HttpURLConnection;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;


public class HttpConnect {

    private String id;
    private String pw;
    private String encryptpw = "";
    URL url;
    String inputLine;
    String xmlresult = "";


    HttpConnect(String id, String pw, String encryptpw){
        this.id = id;
        this.pw = pw;
        this.encryptpw = encryptpw;
    }



    public String Connect() throws IOException {
        url = new URL("http://ntmdist.nanoit.kr:80");

        //서버에 접근
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        System.out.println("[서버 접속 성공]");

        //입력 스트림 사용 여부
        conn.setDoInput(true);

        //전송 방식
        conn.setRequestMethod("POST");

        //conn객체에 있는 outputStream 사용 허용+
        conn.setDoOutput(true);


        System.out.println("=========== HTTP 통신 서버로 전송되는 값 =============== ");
        System.out.println("ID : " + id);
        System.out.println("PASSWORD : " + encryptpw);
        System.out.println("==================================================== ");

        //서버로 값 전달(요청)
        OutputStream os = conn.getOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(os, "utf-8");

        String parameters = "id="+id+"&password="+encryptpw;
        System.out.println("서버로 전송할 쿼리 스트링 : " + parameters);

        writer.write(parameters);
        writer.flush();//버퍼 비우기를 통해 요청작업으로 서버로 데이터 내보내기
        writer.close();

        int code = conn.getResponseCode();
        System.out.println("HTTP 통신 응답 코드 : " + code);
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8")); //서버에서 받은 값 br에 담김

        while ((inputLine = br.readLine()) != null) {
            xmlresult += inputLine.trim();
        }
        System.out.println("HTTP 통신 결과(Data) : " + xmlresult);
        os.close();


        return xmlresult;
    }
}
