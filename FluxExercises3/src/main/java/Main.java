import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import static java.time.Duration.ofSeconds;
import static reactor.core.scheduler.Schedulers.parallel;

public class Main {
    public static void main(String[] args) throws InterruptedException {

/*        CountDownLatch countDownLatch = new CountDownLatch(1);

        Flux.range(1, 10)
                .map(i -> i)
                .parallel()
                .runOn(parallel())
             *//*   .log()*//*
                .map(i -> i + 1)
                .doAfterTerminate(countDownLatch::countDown)
                .subscribe(x -> System.out.println(x + "  " + Thread.currentThread().getName()));

        countDownLatch.await();*/


        //--------------------------------------------------------------------------------------------------------------
/*        CountDownLatch latch1 = new CountDownLatch(1);

        Flux.range(0, 30)
                .flatMap(count -> Flux.just(count)
                        .subscribeOn(Schedulers.parallel())
                        .map(i -> highCpuProcess(i)))
                .doAfterTerminate(latch1::countDown)
                .subscribe(m -> System.out.println("Subscriber received - " + m + " on thread: " + Thread.currentThread().getName()));

        latch1.await();*/
        //---------------------------------------------------------------------------------------------------------------

/*        CountDownLatch latch2 = new CountDownLatch(1);

        Flux.range(0, 30)
                .flatMap(count -> Flux.just(count).map(i->i).subscribeOn(Schedulers.parallel()).map(i -> highCpuProcess(i)).log())
                .doAfterTerminate(latch2::countDown)
                .subscribe(m -> System.out.println("Subscriber received - " + m + " on thread: " + Thread.currentThread().getName()));

        latch2.await();*/

        //---------------------------------------------------------------------------------------------------------------
        CountDownLatch countDownLatch = new CountDownLatch(1);

        ConnectableFlux<Integer> cflux = Flux.range(1,10)/*.interval(Duration.ofMillis(500))*//*sample(ofSeconds(1))*/.publish();

        cflux.doAfterTerminate(countDownLatch::countDown).subscribe(x -> System.out.println("The first subscriber " + x));

/*        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cflux.subscribe(x -> System.out.println("The second subscriber " + x));
        });

        thread.run();*/

        cflux.connect();

        countDownLatch.await();

        //---------------------------------------------------------------------------------------------------------------

/*        ConnectableFlux<Object> publish = Flux.create(fluxSink -> {
            while(true) {
                fluxSink.next(System.currentTimeMillis());
            }
        }).sample(ofSeconds(2)).publish();


        publish.subscribe(System.out::println);
        publish.subscribe(System.out::println);

        publish.connect();*/

        //---------------------------------------------------------------------------------------------------------------

/*        Flux<String> source = Flux.fromIterable(Arrays.asList("ram", "sam", "dam", "lam"));

        ConnectableFlux<String> connectable = source.publish();
        connectable.subscribe(d -> System.out.println("Subscriber 1: "+d));
        connectable.subscribe(d -> System.out.println("Subscriber 2: "+d));
        connectable.connect();*/

        //---------------------------------------------------------------------------------------------------------------



    }

    private static int highCpuProcess(int i) {
        return i;
    }

}
