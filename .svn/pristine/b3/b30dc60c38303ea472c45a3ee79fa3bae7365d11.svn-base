package br.com.locadora.web.util;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.annotation.WebListener;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @param Material de http://stackoverflow.com/questions/19573457/simple-example-for-quartz-2-2-and-tomcat-7
 */
@WebListener
public class QuartzListener extends QuartzInitializerListener{
	
	 @Override
	    public void contextInitialized(ServletContextEvent sce) {
	        super.contextInitialized(sce);
	        ServletContext ctx = sce.getServletContext();
	        StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QUARTZ_FACTORY_KEY);
	        
	        try {
	            Scheduler scheduler = factory.getScheduler();
	            JobDetail jobDetail = JobBuilder.newJob(TestJob.class).build();
	            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simple").withSchedule(
	                    CronScheduleBuilder.cronSchedule("0 0/9 * 1/1 * ? *")).startNow().build();
	            scheduler.scheduleJob(jobDetail, trigger);
	            scheduler.start();
	        } catch (Exception e) {
	        	System.out.println("Houve um erro na programação no job. "+ e);
	            ctx.log("Houve um erro na programação no job.", e);
	        }
	    }
	 
	 /* http://stackoverflow.com/questions/19573457/simple-example-for-quartz-2-2-and-tomcat-7
	  * 
	  * To avoid conflicts, do not set the default listener in the web.xml at the same time. 
	  * With this last example, the default number of threads is 10. 
	  * Since the scheduler started in stand-by mode, it is necessary to call scheduler.start();. 
	  * The "simple" identity is optional, but you can use it for reschedule the job (That's great!). e.g.:
	  * 
	  * ServletContext ctx = request.getServletContext();
		StdSchedulerFactory factory = (StdSchedulerFactory) ctx.getAttribute(QuartzListener.QUARTZ_FACTORY_KEY);
		Scheduler scheduler = factory.getScheduler();
		Trigger trigger = TriggerBuilder.newTrigger().withIdentity("simple").withSchedule(
        CronScheduleBuilder.cronSchedule(newCronExpression)).startNow().build();
		Date date = scheduler.rescheduleJob(new TriggerKey("simple"), trigger)
	  * **/
}
