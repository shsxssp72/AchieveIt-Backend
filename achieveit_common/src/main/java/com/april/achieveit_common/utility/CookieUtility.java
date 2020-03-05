package com.april.achieveit_common.utility;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;
import java.util.regex.Pattern;

@Slf4j
public class CookieUtility
{

    public static CookieBuilder CreateCookieBuilder(HttpServletResponse response)
    {
        return new CookieBuilder(response);
    }

    public static String getCookieValue(HttpServletRequest request,String cookieName,String charset) throws UnsupportedEncodingException
    {
        return getCookieValue(request,
                              cookieName, null,charset);
    }

    public static String getCookieValue(HttpServletRequest request,String cookieName,Map<String,String> filterParams,String charset) throws UnsupportedEncodingException
    {
        Cookie[] cookies=request.getCookies();
        if(cookieName==null||cookies==null)
            return null;
        String rawCookieValue=null;
        for(Cookie cookie: cookies)
        {
            if(cookieName.equals(cookie.getName()))
            {
                if(filterParams!=null)
                {
                    if(filterParams.containsKey("domain"))
                    {
                        if(!filterParams.get("domain")
                                .equals(cookie.getDomain()))
                        {
                            continue;
                        }
                    }
                    if(filterParams.containsKey("path"))
                    {
                        if(!filterParams.get("path")
                                .equals(cookie.getPath()))
                        {
                            continue;
                        }
                    }
                }
                rawCookieValue=cookie.getValue();
                break;
            }
        }
        if(rawCookieValue==null)
        {
            return null;
        }
        if(charset!=null)
        {
            return URLDecoder.decode(rawCookieValue,
                                     charset);
        }
        return rawCookieValue;
    }

    public static String getDomainName(HttpServletRequest request)
    {
        String domainName="";
        String requestUrl=request.getRequestURL()
                .toString();
        Pattern domainNamePattern=Pattern.compile("(?<=http://)[0-9a-zA-Z.]+(?=(:[0-9]+)?/\\w*)");
        if(!requestUrl.isEmpty())
        {
            var matcher=domainNamePattern.matcher(requestUrl);
            if(matcher.find())
            {
                domainName=matcher.group();
            }
            else
            {
                domainName=requestUrl;
            }
        }

        return domainName;
    }

    public static class CookieBuilder
    {
        private HttpServletResponse response;
        private HttpServletRequest request;
        private Integer maxAge;
        private String charset;
        private Boolean httpOnly;

        private CookieBuilder(HttpServletResponse response)
        {
            this.response=response;
        }

        public CookieBuilder request(HttpServletRequest request)
        {
            this.request=request;
            return this;
        }

        public CookieBuilder maxAge(int maxAge)
        {
            this.maxAge=maxAge;
            return this;
        }

        public CookieBuilder charset(String charset)
        {
            this.charset=charset;
            return this;
        }

        public CookieBuilder httpOnly()
        {
            this.httpOnly=true;
            return this;
        }

        public void build(String cookieName,String cookieValue)
        {
            try
            {
                if(StringUtils.isBlank(charset))
                {
                    charset="utf-8";
                }

                if(cookieValue==null)
                {
                    cookieValue="";
                }
                else if(StringUtils.isNotBlank(charset))
                {
                    cookieValue=URLEncoder.encode(cookieValue,
                                                  charset);
                }
                Cookie cookie=new Cookie(cookieName,
                                         cookieValue);
                if(maxAge!=null&&maxAge>0)
                    cookie.setMaxAge(maxAge);
                if(null!=request)
                    cookie.setDomain(getDomainName(request));
                cookie.setPath("/");

                cookie.setHttpOnly(httpOnly==null?false:httpOnly);
                response.addCookie(cookie);
            }
            catch(Exception e)
            {
                log.error("Cookie Error: "+e.getMessage());
            }
        }
    }
}
