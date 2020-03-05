package com.april.achieveit_common.utility;


import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;


public class CryptoUtility
{
    public static String Base64Encoder(String input)
    {
        byte[] encodeBytes=Base64.encodeBase64(input.getBytes());
        return new String(encodeBytes);
    }

    public static String Base64Decoder(String input)
    {
        byte[] decodeBytes=Base64.decodeBase64(input.getBytes());
        return new String((decodeBytes));
    }

    public static String Sha3_512Encoder(String input,String salt)
    {
        return DigestUtils.sha3_512Hex(input+salt);
    }

    public static String HashPassword(String password,String username,String salt)
    {
        String realSalt=username+salt;
        return Sha3_512Encoder(password,realSalt);
    }

    public static String BytesToHexString(byte[] bytes)
    {
        return Hex.encodeHexString(bytes);
    }
    public static byte[] HexStringToBytes(String string) throws DecoderException
    {
        return Hex.decodeHex(string);
    }

}
