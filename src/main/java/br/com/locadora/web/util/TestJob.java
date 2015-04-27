package br.com.locadora.web.util;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
public class TestJob implements Job{
	@Override
	public void execute(final JobExecutionContext ctx)
			throws JobExecutionException {
		Calendar calendar= Calendar.getInstance();
		SimpleDateFormat dt = new SimpleDateFormat("dd/MM/yy hh:mm:ss"); 

		System.out.println("Job em ação \n" +dt.format(calendar.getTime()));

	}
}
