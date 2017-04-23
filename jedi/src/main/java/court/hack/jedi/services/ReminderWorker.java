package court.hack.jedi.services;

import court.hack.jedi.beans.ReminderBean;
import court.hack.jedi.repositories.EventReminderRepository;
import court.hack.jedi.repositories.EventRepository;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import java.util.Collection;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

@ApplicationScoped
@ManagedBean
public class ReminderWorker {

    private EventRepository eventRepository = new EventRepository();
    private EventReminderRepository eventReminderRepository = new EventReminderRepository();
    private EmailService emailService = new EmailService();

    private static Timer timer;

    public ReminderWorker() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new ReminderChecker(eventRepository, emailService, eventReminderRepository), 0, 30000);
        }
    }

    private class ReminderChecker extends TimerTask {

        private EventRepository eventRepository;
        private EmailService emailService;
        private EventReminderRepository eventReminderRepository;

        public ReminderChecker(final EventRepository eventRepository, final EmailService emailService, final EventReminderRepository eventReminderRepository) {
            this.eventRepository = eventRepository;
            this.emailService = emailService;
            this.eventReminderRepository = eventReminderRepository;
        }

        public void run() {
            Collection<ReminderBean> collection = this.eventRepository.getPendingReminders();
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
                this.eventReminderRepository.updateEvent(reminderBean);
            }
        }

        private void sendEmail(ReminderBean reminderBean){
            String subject = "Reminder: " + reminderBean.getTitle();
            String body = reminderBean.getDesc();
            this.emailService.sendEmail(reminderBean.getEmail(), subject, body);
        }

        private void sendTextMessage(ReminderBean reminderBean){
            String subject = "Reminder: " + reminderBean.getTitle();
            //TextMessageService.sendSMSMessage(reminderBean.getPhoneNumber(), subject);
            String body = reminderBean.getDesc();
            this.emailService.sendEmail(reminderBean.getPhoneNumber() + "@messaging.sprintpcs.com", subject, body);
            this.emailService.sendEmail(reminderBean.getPhoneNumber() + "@vtext.com", subject, body);
            this.emailService.sendEmail(reminderBean.getPhoneNumber() + "@tmomail.net", subject, body);
        }

    }

}
