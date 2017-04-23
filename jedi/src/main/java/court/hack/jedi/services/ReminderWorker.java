package court.hack.jedi.services;

import court.hack.jedi.beans.ReminderBean;
import court.hack.jedi.repositories.EventRepository;

import java.util.Collection;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

public class ReminderWorker {

    private static EventRepository eventRepository = new EventRepository();

    static {
        Timer timer = new Timer();
        timer.schedule(new ReminderChecker(), 0, 30000);
    }

    private static class ReminderChecker extends TimerTask {
        public void run() {
            Collection<ReminderBean> collection = eventRepository.getPendingReminders();
            Iterator<ReminderBean> collectionIter = collection.iterator();
            while(collectionIter.hasNext()){
                ReminderBean reminderBean = (ReminderBean) collectionIter.next();
                if(null != reminderBean.getEmail()){
                    sendEmail(reminderBean);
                }
                if(null != reminderBean.getPhoneNumber()){
                    sendTextMessage(reminderBean);
                }
                reminderBean.setSentFlag("Y");
                eventRepository.updateEvent(reminderBean);
            }
        }

        private void sendEmail(ReminderBean reminderBean){
            String subject = "Reminder: " + reminderBean.getTitle();
            String body = reminderBean.getDesc();
            EmailService.sendEmail(reminderBean.getEmail(), subject, body);
        }

        private void sendTextMessage(ReminderBean reminderBean){
            String subject = "Reminder: " + reminderBean.getTitle();
            //TextMessageService.sendSMSMessage(reminderBean.getPhoneNumber(), subject);
            String body = reminderBean.getDesc();
            EmailService.sendEmail(reminderBean.getPhoneNumber() + "@messaging.sprintpcs.com", subject, body);
            EmailService.sendEmail(reminderBean.getPhoneNumber() + "@vtext.com", subject, body);
            EmailService.sendEmail(reminderBean.getPhoneNumber() + "@tmomail.net", subject, body);
        }

    }

}
