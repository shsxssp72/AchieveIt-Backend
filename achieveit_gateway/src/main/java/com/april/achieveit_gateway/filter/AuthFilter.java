package com.april.achieveit_gateway.filter;

import com.april.achieveit_common.utility.CookieUtility;
import com.april.achieveit_common.utility.JWTUtility;
import com.april.achieveit_gateway.config.FilterProperty;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import javax.servlet.http.HttpServletRequest;

public class AuthFilter extends ZuulFilter
{
    private static Logger logger=LoggerFactory.getLogger(AuthFilter.class);

    @Autowired
    private FilterProperty filterProperty;

    @Value("${local.server-identity}")
    String serverIdentity;
    @Value("${local.shared-secret}")
    String sharedSecret;

    @Override
    public String filterType()
    {
        return FilterConstants.PRE_TYPE;
    }

    @Override
    public int filterOrder()
    {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1;
    }

    @Override
    public boolean shouldFilter()
    {
        RequestContext currentContext=RequestContext.getCurrentContext();
        HttpServletRequest request=currentContext.getRequest();

        String path=request.getRequestURI();

        for(String item: filterProperty.getAllowPaths())
        {
            if(path.startsWith(item))
                return false;
        }
        return true;
    }

    @Override
    public Object run() throws ZuulException
    {
        RequestContext currentContext=RequestContext.getCurrentContext();
        HttpServletRequest request=currentContext.getRequest();

        try
        {
            String token=CookieUtility.getCookieValue(request,"JWT",null);
            if(!JWTUtility.VerifyJWT(token,
                                     sharedSecret))
                throw new IllegalArgumentException();
            JWTUtility.getSubjectFromJWT(token);
            logger.info("Accepted request");
        }
        catch(Exception e)
        {
            logger.info("Refused request");
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(403);
        }

        return null;
    }
}
