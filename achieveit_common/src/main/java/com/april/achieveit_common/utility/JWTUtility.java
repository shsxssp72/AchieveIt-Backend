package com.april.achieveit_common.utility;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.codec.DecoderException;

import java.text.ParseException;
import java.util.Date;

public class JWTUtility
{
    public static String SignJWT(String subject,int expireSeconds,String issuer,String hexedSharedSecret) throws DecoderException, JOSEException
    {
        JWTClaimsSet claimsSet=new JWTClaimsSet.Builder().subject(subject)
                .issuer(issuer)
                .expirationTime(new Date(new Date().getTime()+expireSeconds*1000))
                .build();

        byte[] sharedSecret=CryptoUtility.HexStringToBytes(hexedSharedSecret);
        JWSSigner signer=new MACSigner(sharedSecret);
        SignedJWT signedJWT=new SignedJWT(new JWSHeader(JWSAlgorithm.HS512),
                                          claimsSet);
        signedJWT.sign(signer);

        return signedJWT.serialize();
    }
    public static boolean VerifyJWT(String jwt,String hexedSharedSecret) throws DecoderException, JOSEException, ParseException
    {
        byte[] sharedSecret=CryptoUtility.HexStringToBytes(hexedSharedSecret);
        JWSVerifier verifier=new MACVerifier(sharedSecret);
        SignedJWT signedJWT=SignedJWT.parse(jwt);
        return signedJWT.verify(verifier);
    }

    public static JWTClaimsSet getClaimsSetFromJWT(String jwt) throws ParseException
    {
        SignedJWT signedJWT=SignedJWT.parse(jwt);
        return signedJWT.getJWTClaimsSet();
    }

    public static String getIssuerFromJWT(String jwt) throws ParseException
    {
        return getClaimsSetFromJWT(jwt).getIssuer();
    }
    public static String getSubjectFromJWT(String jwt) throws ParseException
    {
        return getClaimsSetFromJWT(jwt).getSubject();
    }

}
