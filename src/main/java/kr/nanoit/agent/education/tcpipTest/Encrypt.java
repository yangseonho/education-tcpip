package kr.nanoit.agent.education.tcpipTest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Base64;

public class Encrypt {
    private String sendKey;
    private Key keySpec;

    Encrypt(String enckey) {

        try {
            byte[] keyBytes = new byte[16];
            byte[] b = enckey.getBytes("UTF-8");

            //keyBytes = b; 이것도 가능함
            System.arraycopy(b, 0, keyBytes, 0, keyBytes.length); //(1,2,3,4) 1 = '복사할 내용', 2 = '어디서부터 복사할 것인지', 3 = '어디에 넣을것인지' 4 = '데이터의 길이'

            SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
            this.sendKey = enckey.substring(0, 16);
            this.keySpec = keySpec;

        } catch (Exception e) {
            System.out.println("생성자 Err : " + e);
        }

    }

    public String DataEncrypt(String pw) { //실제 암호화 작업

        Cipher cipher;
        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(this.sendKey.getBytes()));

            byte[] encrypted = cipher.doFinal(pw.getBytes("UTF-8"));
            String Str = new String(Base64.getEncoder().encodeToString(encrypted));
            return Str;

        } catch (Exception e) {
            //TODO: handle exception
            System.out.println("DataEncrypt Err : " + e);
        }

        return null;
    }

}
