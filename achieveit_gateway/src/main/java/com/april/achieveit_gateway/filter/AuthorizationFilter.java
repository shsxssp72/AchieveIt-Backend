package com.april.achieveit_gateway.filter;

import com.april.achieveit_common.bean.ResponseContent;
import com.april.achieveit_common.bean.ResponseContentStatus;
import com.april.achieveit_common.utility.CookieUtility;
import com.april.achieveit_common.utility.JWTUtility;
import com.april.achieveit_gateway.config.FilterProperty;
import com.april.achieveit_gateway.policy.AuthorizationPolicy;
import com.april.achieveit_gateway.utility.RereadableRequestWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class AuthorizationFilter extends ZuulFilter
{
    private static Logger logger=LoggerFactory.getLogger(AuthorizationFilter.class);

    @Autowired
    private FilterProperty filterProperty;

    @Autowired
    private AuthorizationPolicy authorizationPolicy;

    @Value("${local.server-identity}")
    private String serverIdentity;
    @Value("${local.shared-secret}")
    private String sharedSecret;

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

        if(filterProperty.getAllowPaths()
                .contains(path))
            return false;
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
            String token=CookieUtility.getCookieValue(request,
                                                      "JWT");
            if(!JWTUtility.VerifyJWT(token,
                                     sharedSecret))
                throw new IllegalArgumentException();
            String userId=JWTUtility.getSubjectFromJWT(token);

            logger.info("Accepted request triggered by user: "+userId+" to request: "+request.getRequestURI());
            RereadableRequestWrapper requestWrapper=new RereadableRequestWrapper(request);

            authorizationPolicy.Verify(userId,
                                       requestWrapper);
            currentContext.setRequest(requestWrapper);
        }
        catch(Exception e)
        {
            logger.info("Refused request");
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseStatusCode(403);
            currentContext.setResponseBody(makeFailureResponseContentBody("Access Denied."));
        }
        return null;
    }

    @SneakyThrows
    private static String makeFailureResponseContentBody(String message)
    {
        ResponseContent result=new ResponseContent();
        result.setStatus(ResponseContentStatus.FAILURE);
        result.setMessage(message);

        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(result);
    }
}
