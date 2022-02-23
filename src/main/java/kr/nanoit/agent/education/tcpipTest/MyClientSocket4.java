package kr.nanoit.agent.education.tcpipTest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;



public class MyClientSocket4 {

    private Socket socket;
    private BufferedReader reader;
    private Scanner sc;
    private PrintWriter writer;

    public MyClientSocket4() {
        try {
            socket = new Socket("localhost", 10000);

            SocketThread st = new SocketThread();
            st.start();

            sc = new Scanner(System.in);
            writer = new PrintWriter(socket.getOutputStream(), true);	// 두번째 인자 값 true 넣으면 오토 flush()

            while(true) {
                //ALL : 안녕, MSG:ssar1:안녕
                String keyboard = sc.nextLine();
                writer.println(keyboard);
                writer.flush();
            }

        } catch (Exception e) {
            System.out.println("Connect Error");
            e.printStackTrace();
        }
    }

    class SocketThread extends Thread {

        @Override
        public void run() {
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String input = null;
                while((input = reader.readLine()) != null) {
                    System.out.println(input);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

    }

}
