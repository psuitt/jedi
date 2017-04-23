package court.hack.jedi.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TextMessageService {

    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "ACba7066fb6fbcf3be9ea0b2033db805e3";
    public static final String AUTH_TOKEN = "dccd4396fa5d6590984c15d2f7638ef9";

    public static void sendSMSMessage(String toPhoneNumber, String messageContent){

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber("+"+toPhoneNumber), new PhoneNumber("+17193874773"),
                        messageContent)
                .create();

    }

    public static void sendMMSMessage(String toPhoneNumber, String messageContent, String mediaURL){

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber("+"+toPhoneNumber), new PhoneNumber("+17193874773"),
                        messageContent)
                .setMediaUrl(mediaURL)
                .create();

    }

}
