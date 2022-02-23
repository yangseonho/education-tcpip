package kr.nanoit.agent.education.tcpipTest;


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

        } catch (Exception e) {
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

        } catch (Exception e) {
            //TODO: handle exception
            System.out.println(e + "=> HttpURLConnection fail");
        }

        //================================================================================================================================
        // XML 결과 파싱 ==================================================================================================================
        System.out.println();
        System.out.println();
        System.out.println("XML 파싱");
        System.out.println(xmlresult);
        XMLParser xmlParser = new XMLParser(xmlresult);
        parsedData = new HashMap<>();
        try {
            parsedData = xmlParser.parsing();
        } catch (Exception e) {
            System.out.println("Parsing Exception Err : " + e);
            e.printStackTrace();
        }
        System.out.println("XML에서 추출한 Data : " + parsedData);

        //================================================================================================================================
        // TCP 소켓 통신 ===================================================================================================================
        System.out.println();
        System.out.println();
        System.out.println("TCP 소켓 통신");


        System.out.println("Client 인증");
        try {
            SocketConnect socketConnect = new SocketConnect(parsedData.get("IP"), Integer.parseInt(parsedData.get("Port")), id, encryptpw);
            socketConnect.Connection();
        } catch (Exception e) {
            System.out.println(e + " => TCP Socket Connect fail");
            e.printStackTrace();
        }

        //================================================================================================================================

    }
}