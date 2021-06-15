import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

public class FilmSubscriber implements Subscriber<Film> {

    @Override
    public void onSubscribe(Subscription subscription) {
        subscription.request(2);
    }

    @Override
    public void onComplete() {
        System.out.println("We are in onComplete");
    }

    @Override
    public void onNext(Film film) {
        System.out.println("We are in onNext");
        System.out.println("film = " + film);
    }

    @Override
    public void onError(Throwable throwable) {

    }
}
