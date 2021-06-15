import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;

public class SampleSubscriber<T> extends BaseSubscriber<T> {

    public void hookOnSubscribe(Subscription subscription) {
        System.out.println("Inside Subscribed");
        request(1);
    }

    public void hookOnNext(T value) {
        System.out.println("Inside hookOnNext value = " + value);
        request(1);
    }
}
