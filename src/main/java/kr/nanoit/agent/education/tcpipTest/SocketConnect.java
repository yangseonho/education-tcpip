package kr.nanoit.agent.education.tcpipTest;


import java.io.*;
import java.net.Socket;

import static java.lang.Thread.sleep;

public class SocketConnect {

    private Socket socket;
    private InputStream is;
    private OutputStream os;
    private Thread thread;
    private final String ip;
    private final int port;
    private final String id;
    private final String encryptpw;
    private byte[] sendData = new byte[220];


    SocketConnect(String ip, int port, String id, String encryptpw){
        this.ip = ip;
        this.port = port;
        this.id =id;
        this.encryptpw = encryptpw;
    }

    public void Connection() throws IOException, InterruptedException {

        System.out.println("TCP 통신 시도");
        socket = new Socket(ip, port);
        System.out.println("서버 접속 성공");

        thread = new Thread();
        thread.start();


        while (true){
            System.out.println("쓰레드 테스트");
            sleep(2000);
        }
//        //스트림 닫기, 소켓 닫기
//        socket.close();

    }

    class SocketThread implements Runnable{

        @Override
        public void run() {
            try{

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
                os.close();
                is.close();

            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


}
