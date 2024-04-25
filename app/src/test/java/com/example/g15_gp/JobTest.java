package com.example.g15_gp;

import static org.junit.Assert.assertEquals;

import com.example.g15_gp.job.Job;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class JobTest {

    @Test
    public void validJobTypeTest() {
        Job job = new Job("Walking a dog", "NS", "15", "Part-time");
        job.setJobType("full-time");
        assertEquals("full-time", job.jobType);
    }

    @Test
    public void validJobNameTest() {
        Job job = new Job("Walking a dog", "NS", "15", "Part-time");
        job.setName("Babysitting");
        assertEquals("Babysitting", job.getName());
    }

    @Test
    public void validJobPayRateTest() {
        Job job = new Job("Walking a dog", "NS", "15", "Part-time");
        job.setPayRate("30");
        assertEquals("30", job.payRate);
    }

    @Test
    public void validJobLocationTest() {
        Job job = new Job("Walking a dog", "NS", "15", "Part-time");
        job.setLocation("Halifax");
        assertEquals("Halifax", job.location);
    }
}