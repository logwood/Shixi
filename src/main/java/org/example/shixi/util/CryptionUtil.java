package org.example.shixi.util;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Base64;

/**
 * AES 加解密工具
 * @author liulinchuan
 * @since 2023/1/13 17:49
 */
public class CryptionUtil {

//   private CryptionUtil() {
//   }

//   /**
//    * 盐值
//    */
//   private static final String SALT_KEY = "5f50119C4F534aee";

//   /**
//    * 向量值
//    */
//   private static final String VECTOR_KEY = "a06ceaAc57472B07";

//   private static final Key secretKey = new SecretKeySpec(SALT_KEY.getBytes(StandardCharsets.UTF_8), "AES");

//   private static final AlgorithmParameterSpec iv = new IvParameterSpec(VECTOR_KEY.getBytes(StandardCharsets.UTF_8));

//   private static final Cipher cipher;

//   static {
//       try {
//           cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
//       } catch (Exception e) {
//           throw new RuntimeException(e);
//       }
//   }

//   /**
//    * 加密
//    * @author liulinchuan
//    * @since 2023/1/13 18:00
//    * @param content 待加密文本
//    * @return 加密文本 Base64
//    */
//   public static String encrypt(String content) {
//       try {
//           cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
//           byte[] encrypted = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
//           return Base64.getEncoder().encodeToString(encrypted);
//       } catch (Exception e) {
//           throw new RuntimeException(e);
//       }
//   }

//   /**
//    * 解密
//    * @author liulinchuan
//    * @since 2023/1/13 18:01
//    * @param contentBase64 待解密文本 Base64
//    * @return 解密文本
//    */
//   public static String decrypt(String contentBase64) {
//       try {
//           cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
//           byte[] decrypted = cipher.doFinal(Base64.getDecoder().decode(contentBase64));
//           return new String(decrypted);
//       } catch (Exception e) {
//           throw new RuntimeException(e);
//       }
//   }

}
