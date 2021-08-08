package network;

import application.Message;
import com.esotericsoftware.kryo.Kryo;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.util.Locale;

public class Register {

    public static void register(Kryo k){
        k.register(Message.class);
        k.register(LocalTime.class, new LocalTimeSerializer());
        k.register(DateTimeFormatter.class);
        k.register(Locale.class);
        k.register(DecimalStyle.class);




    }
}
