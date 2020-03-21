package com.april.achieveit_common.utility;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

public class SnowFlakeIdGenerator
{
    //Initial Timestamp
    private static final long START_TIMESTAMP=1514736000000L;
    private static final int SERIAL_BITS_LENGTH=14;
    private static final int MACHINE_BITS_LENGTH=6;
    private static final int DATA_CENTER_BITS_LENGTH=2;

    private static final long MAXIMUM_SERIAL_NUM=~(-1L<<SERIAL_BITS_LENGTH);
    private static final long MAXIMUM_MACHINE_NUM=~(-1L<<MACHINE_BITS_LENGTH);
    private static final long MAXIMUM_DATA_CENTER_NUM=~(-1L<<DATA_CENTER_BITS_LENGTH);

    private static final int SERIAL_OFFSET=0;
    private static final int MACHINE_OFFSET=SERIAL_OFFSET+SERIAL_BITS_LENGTH;
    private static final int DATA_CENTER_OFFSET=MACHINE_OFFSET+MACHINE_BITS_LENGTH;
    private static final int TIMESTAMP_OFFSET=DATA_CENTER_OFFSET+DATA_CENTER_BITS_LENGTH;

    private long dataCenterId;
    private long machineId;
    private long serial=0L;
    private long lastTimeStamp=-1L;

    public SnowFlakeIdGenerator(long dataCenterId,long machineId)
    {
        if(dataCenterId>MAXIMUM_DATA_CENTER_NUM||dataCenterId<0)
        {
            throw new IllegalArgumentException("Invalid data center id.");
        }
        if(machineId>MAXIMUM_MACHINE_NUM||machineId<0)
        {
            throw new IllegalArgumentException("Invalid machine id.");
        }
        this.dataCenterId=dataCenterId;
        this.machineId=machineId;
        lastTimeStamp=System.currentTimeMillis();
    }

    public synchronized long getNextId()
    {
        long currentStamp=System.currentTimeMillis();
        if(currentStamp<lastTimeStamp)
        {
            throw new RuntimeException("Clock move backwards. Quitting due to unacceptable fatal error.");
        }
        if(currentStamp==lastTimeStamp)
        {
            serial=(serial+1)&MAXIMUM_SERIAL_NUM;
            if(serial==0L)
            {
                waitUntilNextMillisecond();
                currentStamp=System.currentTimeMillis();
            }
        }
        else
        {
            serial=0L;
        }
        lastTimeStamp=currentStamp;
        long result=(currentStamp-START_TIMESTAMP)<<TIMESTAMP_OFFSET|dataCenterId<<DATA_CENTER_OFFSET|machineId<<MACHINE_OFFSET|serial<<SERIAL_OFFSET;
        return result;
    }

    private void waitUntilNextMillisecond()
    {
        long currentStamp=System.currentTimeMillis();
        for(;currentStamp<=lastTimeStamp;)
            currentStamp=System.currentTimeMillis();
    }
}
