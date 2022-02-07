package kr.nanoit.agent.education.tcpip;

import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PacketMakerTest {

    @Test
    void Should_Return_Login_Packet_Using_Login_Func() {
        // given
        String id = "test02";
        String password = "sdjkflkajsdvljsadlr";

        // when
        PacketMaker packetMaker = new PacketMaker();
        byte[] loginPacket = packetMaker.login(id, password);

        // then
        assertEquals(220, loginPacket.length);
        assertTrue(new String(loginPacket).contains(makeHeaderPacketType()));
        assertTrue(new String(loginPacket).contains(makeHeaderBodySize()));
        assertTrue(new String(loginPacket).contains(makeBodyId(id)));
        assertTrue(new String(loginPacket).contains(makeBodyPassword(password)));
    }

    private String makeBodyId(String id) {
        return id + padding(PacketMaker.SIZE_LOGIN_BODY_ID - id.length());
    }

    private String makeBodyPassword(String password) {
        return password + padding(PacketMaker.SIZE_LOGIN_BODY_PASSWORD - password.length());
    }

    private String makeHeaderBodySize() {
        return PacketMaker.SIZE_BODY_LENGTH + padding(PacketMaker.SIZE_HEADER_BODY_LENGTH - String.valueOf(PacketMaker.SIZE_BODY_LENGTH).length());
    }

    private String makeHeaderPacketType() {
        return PacketMaker.TYPE_LOGIN + padding(PacketMaker.SIZE_HEADER_PACKET_TYPE - PacketMaker.TYPE_LOGIN.length());
    }

    @Test
    void Should_Return_Expected_Variable_Using_Test_Padding_Func() {
        // given
        String id = "test01";
        String password = "dskajflkjaslejroi";
        String rawHeader = "LOGIN" + padding(5) + "200" + padding(7);
        String rawBody = id + padding(100 - id.length()) + password + padding(100 - password.length());

        // when
        byte[] actual = (rawHeader + rawBody).getBytes(StandardCharsets.UTF_8);

        // then
        assertEquals(20, rawHeader.length());
        assertEquals(200, rawBody.length());
        assertEquals(220, actual.length);
        assertTrue(new String(actual).contains("LOGIN"));
        assertTrue(new String(actual).contains("200"));
        assertTrue(new String(actual).contains(id));
        assertTrue(new String(actual).contains(password));
    }

    private String padding(int count) {
        return Stream.generate(() -> " ")
                .limit(count)
                .collect(Collectors.joining());
    }
}