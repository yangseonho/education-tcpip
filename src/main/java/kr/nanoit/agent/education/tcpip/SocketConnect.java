package kr.nanoit.agent.education.tcpip;

import java.io.*;
import java.net.Socket;

public class SocketConnect{

    private final String ip;
    private final int port;
    private byte[] sendData = new byte[220];

    public SocketConnect(String ip, int port){
        this.ip = ip;
        this.port = port;
    }

    public void Connection(String id, String encryptpw) throws IOException {

        System.out.println("TCP 통신 시도");
        Socket socket = new Socket(ip, port);
        System.out.println("서버 접속 성공");
        InputStream is;
        OutputStream os;


        //서버에 보내는 스트림 받기
        os = socket.getOutputStream();
        //================ 데이터 만들기 ================
        PacketMaker packetMaker = new PacketMaker();
        byte[] sendData = packetMaker.login(id,encryptpw);
        //===================================================

        //================ LOGIN 데이터 전송 ==================
        os.write(sendData);
        os.flush();
        System.out.println("데이터 Request 성공");
        //===================================================

        //서버에서 주는 스트림 가져오기
        //================ LOGIN 데이터 받기 ==================
        is  = socket.getInputStream();

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String receiveData = br.readLine();
        System.out.println("서버로부터 받은 응답 : " + receiveData);
        System.out.println("데이터 Response 성공");
        //===================================================


//        //================ SEND 데이터 전송 ===================
//        String msg = "임시로 메세지 String 만들었습니다.";
//
//        sendData = packetMaker.send();
//        os.write(sendData);
//        os.flush();
//        System.out.println("데이터 Request 성공");
//        //===================================================


        //스트림 닫기, 소켓 닫기
        os.close();
        is.close();
        socket.close();

    }
}
