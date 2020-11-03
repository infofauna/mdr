package ch.cscf.jeci.test.various;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.concurrent.TimeUnit;

/**
 * Use this class to quickly run and test a piece of code that doesn't depend on the api of the context.
 * @author: henryp
 */
public class TestSandbox {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Test
    public void testDateApi(){

        Duration duration = Duration.ofSeconds(10000);

        //duration = Duration.ofHours(5).plusMinutes(12).plusSeconds(15);

        System.out.println(duration.toString());

        System.out.println(duration.getSeconds());
        System.out.println(duration.get(ChronoUnit.NANOS));
        System.out.println(duration.get(ChronoUnit.SECONDS));

        for(TemporalUnit unit : duration.getUnits()){
            System.out.println(unit);
        }

        System.out.println("AAA :"+TimeUnit.MINUTES.convert(duration.getSeconds(), TimeUnit.SECONDS));

        System.out.println(duration.toHours()+":"+duration.toMinutes()+":"+duration.getSeconds());
        System.out.println(duration.toMinutes());


    }


}
