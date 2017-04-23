package court.hack.jedi.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class TextMessageService {

    // Find your Account Sid and Token at twilio.com/user/account
    public static final String ACCOUNT_SID = "ACba7066fb6fbcf3be9ea0b2033db805e3";
    public static final String AUTH_TOKEN = "ACba7066fb6fbcf3be9ea0b2033db805e3";

    public static void sendMessage(){

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message
                .creator(new PhoneNumber("+17192057903"), new PhoneNumber("+17193874773"),
                        "This is the ship that made the Kessel Run in fourteen parsecs?")
                .setMediaUrl("https://c1.staticflickr.com/3/2899/14341091933_1e92e62d12_b.jpg")
                .create();

        System.out.println(message.getSid());
    }

}
