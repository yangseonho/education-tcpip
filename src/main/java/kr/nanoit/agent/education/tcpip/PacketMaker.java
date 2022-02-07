package kr.nanoit.agent.education.tcpip;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PacketMaker {

    public static final String TYPE_LOGIN = "LOGIN";
    public static final int SIZE_HEADER_PACKET_TYPE = 10;
    public static final int SIZE_HEADER_BODY_LENGTH = 10;
    public static final int SIZE_BODY_LENGTH = 200;
    public static final int SIZE_LOGIN_BODY_ID = 100;
    public static final int SIZE_LOGIN_BODY_PASSWORD = 100;
    public static final int SIZE_LOGIN_FULL_SIZE = SIZE_HEADER_PACKET_TYPE + SIZE_HEADER_BODY_LENGTH + SIZE_BODY_LENGTH; //220

    //패킷 만드는 요청을 받는 메소드들(이거 오버로딩 시킬수 있을듯)
    public byte[] login(String id, String password) {
        return makeLogin(id, password).getBytes(StandardCharsets.UTF_8);
    }
    //public byte[] send(String id, String password) {return makeSend(id, password).getBytes(StandardCharsets.UTF_8);}


    //LOGIN 패킷을 만드는 메소드(이거 오버로딩 시킬수 있을듯)
    private String makeLogin(String id, String password) {
        System.out.println("전송 패킷 :" + makeLoginHeader().concat(makeLoginBody(id, password)));
        return makeLoginHeader().concat(makeLoginBody(id, password));//concat는 두개를 더할수 있는 메소드임( + 연산자는 보안에 위험하다고 함)
    }

//    //SEND 패킷을 만드는 메소드
//    private String makeSend(String id, String password) {
//        System.out.println("전송 패킷 :" + makeLoginHeader().concat(makeLoginBody(id, password)));
//        return makeLoginHeader().concat(makeLoginBody(id, password));//concat는 두개를 더할수 있는 메소드임( + 연산자는 보안에 위험하다고 함)
//    }

    //LOGIN 패킷에 헤더 부분을 만드는 메소드
    private String makeLoginHeader() {
        return stringAppendPadding(TYPE_LOGIN, SIZE_HEADER_PACKET_TYPE).concat(stringAppendPadding(String.valueOf(SIZE_BODY_LENGTH), SIZE_HEADER_BODY_LENGTH));
    }

    //LOGIN 패킷에 바디 부분을 만드는 메소드
    private String makeLoginBody(String id, String password) {
        return stringAppendPadding(id, SIZE_LOGIN_BODY_ID).concat(stringAppendPadding(password, SIZE_LOGIN_BODY_PASSWORD));
    }

    //문자열의 길이가 FINAL로 선언된 Byte의 크기보다 작을때 나머지 공간을 띄워쓰기로 채우는 메소드
    private String stringAppendPadding(String appendString, int count) {
        return appendString.concat(Stream.generate(() -> " ").limit(count - appendString.length()).collect(Collectors.joining()));
    }

}
