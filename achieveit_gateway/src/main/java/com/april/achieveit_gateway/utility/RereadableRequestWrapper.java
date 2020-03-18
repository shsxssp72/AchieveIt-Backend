package com.april.achieveit_gateway.utility;

import lombok.Getter;
import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class RereadableRequestWrapper extends HttpServletRequestWrapper
{
    @Getter
    private byte[] body;
    private BufferedReader reader;
    private ServletInputStream inputStream;

    public RereadableRequestWrapper(HttpServletRequest request)
    {
        super(request);
        loadBody(request);
    }

    @SneakyThrows
    private void loadBody(HttpServletRequest request)
    {
        body=IOUtils.toByteArray(request.getInputStream());
        inputStream=new RereadableInputStream(body);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException
    {
        return inputStream!=null?inputStream:super.getInputStream();
    }

    @Override
    public BufferedReader getReader() throws IOException
    {
        if(reader==null)
            reader=new BufferedReader(new InputStreamReader(inputStream,
                                                            getCharacterEncoding()));
        return reader;
    }

    private static class RereadableInputStream extends ServletInputStream
    {
        private final ByteArrayInputStream inputStream;

        public RereadableInputStream(byte[] bytes)
        {
            inputStream=new ByteArrayInputStream(bytes);
        }

        @Override
        public int read() throws IOException
        {
            return inputStream.read();
        }

        @Override
        public boolean isFinished()
        {
            return inputStream.available()==0;
        }

        @Override
        public boolean isReady()
        {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener)
        {
        }
    }
}
