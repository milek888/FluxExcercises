import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ParallelFlux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import static reactor.core.scheduler.Schedulers.parallel;

//TODO przyklady z
//TODO https://projectreactor.io/docs/core/release/reference/#advanced-parallelizing-parralelflux
//TODO https://javatechnicalwealth.com/blog/reactive-flatmap/

public class Main {

    public static void main(String[] args) throws InterruptedException {

/*        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        System.out.println("111111111111111 " + Thread.currentThread().getName());//TODO watek glowny main

        final Flux<String> flux = Flux
                .range(1, 10)
                .map(i -> {
                    System.out.println("222222222222222222222222 " + Thread.currentThread().getName());
                    return 10 + i;
                })//TODO do tad wszystko idzie w glownym watku main bo subscribe bylo wywolane w main
                .publishOn(s)
                .map(i -> {
                    System.out.println("3333333333333333333 " + Thread.currentThread().getName());
                    return "value " + i;
                });

        flux.subscribe();*///TODO subscribe jest wywolywane w glownym watku main

        //----------------------------------------------------------------------------------------------------------------
       /* Scheduler s2 = Schedulers.newParallel("parallel-scheduler", 4);

        System.out.println("111111111111111 " + Thread.currentThread().getName());//TODO to jest glowny watek main inny niz ten w ktorym jest odpalana cala operacja subscribe

        final Flux<String> flux2 = Flux
                .range(1, 10)
                .map(i -> {
                    System.out.println("222222222222222222222222 " + Thread.currentThread().getName());
                    return 10 + i;
                })//TODO do tad czyli do publish wszystko idzie w tym samym watku co operacja subscribe
                .publishOn(s2)//TODO od publish wszystko idzie w watkach z schedulera publish
                .map(i -> {
                    System.out.println("3333333333333333333 " + Thread.currentThread().getName());
                    return "value " + i;
                });
        Thread thread = new Thread(() -> {
            System.out.println("444444444444444444444444 " + Thread.currentThread().getName());
            flux2.subscribe(System.out::println);
        });
        thread.start();
        thread.join();*/

        //----------------------------------------------------------------------------------------------------------------

/*        Scheduler s3 = Schedulers.newParallel("parallel-scheduler", 4);
        System.out.println("111111111111111 " + Thread.currentThread().getName());

        final Flux<String> flux3 = Flux
                .range(1, 10)
                .map(i -> {
                    System.out.println("222222222222222222222222 " + Thread.currentThread().getName());
                    return 10 + i;
                })
                .subscribeOn(s3)//TODO wszystko jest pod tym schedulerem - caly chain zarowno operacje przed jak i po
                .map(i -> {
                    System.out.println("3333333333333333333 " + Thread.currentThread().getName());
                    return "value " + i;
                });

        flux3.subscribe();*/

        //----------------------------------------------------------------------------------------------------------------
     /*   Scheduler s4 = Schedulers.newParallel("parallel-scheduler", 4);
        System.out.println("111111111111111 " + Thread.currentThread().getName());

        final Flux<String> flux4 = Flux
                .range(1, 10)
                .map(i -> {
                    System.out.println("222222222222222222222222 " + Thread.currentThread().getName());
                    return 10 + i;
                })
                .subscribeOn(s4)
                .map(i -> {
                    System.out.println("3333333333333333333 " + Thread.currentThread().getName());
                    return "value " + i;
                });

        Thread thread = new Thread(() -> {//TODO tu sama opercja subscribe jest w swoim watkuodpalana a potem od razu cala reszta jet przelaczanana scgeduler z operacji subscribeOn
            System.out.println("444444444444444444444444 " + Thread.currentThread().getName());
            flux4.subscribe(System.out::println);
        });
        thread.start();
        thread.join();*/

        //----------------------------------------------------------------------------------------------------------------

/*        Scheduler s5 = Schedulers.newParallel("subscribe-scheduler", 4);
        Scheduler s6 = Schedulers.newParallel("publish-scheduler", 4);
        System.out.println("111111111111111 " + Thread.currentThread().getName());

        final Flux<String> flux5 = Flux
                .range(1, 10)
                .map(i -> {
                    System.out.println("222222222222222222222222 " + Thread.currentThread().getName());
                    return 10 + i;
                })//TODO do tad idzie w subscribe schedulerze
                .publishOn(s6)//TODO tutaj publish zmienia i stad wszystko idzie w jego schedulerze
                .map(i -> {
                    System.out.println("3333333333333333333333 " + Thread.currentThread().getName());
                    return 10 + i;
                })
                .subscribeOn(s5)
                .map(i -> {
                    System.out.println("44444444444444444444444 " + Thread.currentThread().getName());
                    return "value " + i;
                });

        flux5.subscribe();*/

        //----------------------------------------------------------------------------------------------------------------
/*        Scheduler s7 = Schedulers.single();//TODO dosn't work why ?
        System.out.println("111111111111111 " + Thread.currentThread().getName());

        Mono mono = Mono.just("1")
                .map(i -> {
                    System.out.println("222222222222222222222222 " + Thread.currentThread().getName());
                    return 10 + i;
                }).
                subscribeOn(s7).map(i -> {
            System.out.println("3333333333333333333 " + Thread.currentThread().getName());
            return "value " + i;
        });

        Thread thread = new Thread(() -> {
            System.out.println("44444444444444444444444444444 " + Thread.currentThread().getName());
            mono.subscribe(System.out::println);
        });

        thread.start();
        thread.join();*/
        //----------------------------------------------------------------------------------------------------------------
/*
        System.out.println("111111111111111 " + Thread.currentThread().getName());

        Flux<Integer> flux6 = Flux
                .range(1, 10).flatMap(num -> {
                    System.out.println("222222222222222222222222 " + Thread.currentThread().getName());
                    return Mono.just(num);
                }, 4);

        flux6.subscribe();
*/

        //----------------------------------------------------------------------------------------------------------------

/*       Integer number = Flux.range(1, 5).hide()
                .flatMap(v -> Flux.range(v * 10, 2)
                        .publishOn(Schedulers.newParallel("foo", 3)))
                //.flatMap(v -> Flux.range(10 * v, 2))
                .log()
                .blockFirst();

        System.out.println(number);*/

        //----------------------------------------------------------------------------------------------------------------

        Flux.just("a", "b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","r")
                .window(3)
                .flatMap(tripleFlux -> tripleFlux.map(Main::toUpperCase).subscribeOn(parallel()), 3)
                .doOnNext(System.out::println).blockLast();
        //TODO Schedulers.parallel() <==> parallel()
        //TODO subscribeOn(parallel()) inside flatMap powoduje ze w roznych watkach sie odpala,
        //TODO Schedulers.parallel() <==> parallel() powoduje tworzenie watkow
        //TODO concurrency 3 ogranicza ilos watkow do 3

        //----------------------------------------------------------------------------------------------------------------
 /*       Flux.just("a", "b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","r")
                .window(3)
                .flatMapSequential(tripleFlux -> tripleFlux.map(Main::toUpperCase).subscribeOn(parallel()))
                .doOnNext(System.out::println).blockLast();*/

        //TODO to samo co wyzej tylko zachowuje kolejnosc
        //TODO https://javatechnicalwealth.com/blog/reactive-flatmap/
        //----------------------------------------------------------------------------------------------------------------

/*       CountDownLatch countDownLatch = new CountDownLatch(5);;

        ParallelFlux parallelFlux = Flux.just("a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "r")
                .parallel(3).runOn(parallel()).doOnNext(x -> {//TODO (2)
                    System.out.println("1111111111111111111111111 " + Thread.currentThread().getName());
                    System.out.println(x);
                    countDownLatch.countDown();
                });
        Thread thread = new Thread(() -> {
            parallelFlux.subscribe(); //TODO (1)
        });
        thread.start();
        countDownLatch.await();*/

        //TODO Note that to actually perform the work in parallel, you should call ParallelFlux.runOn(Scheduler) afterward.
        //TODO nie wystarczy thread.join() bo watek main na joinie czeka na watk w ktorym subscribe (1) zostalo wywolane a ten wate sie konczy zanim sie jeszcze zaczna
        //TODO watki z runOn(parallel()) (2)

        //----------------------------------------------------------------------------------------------------------------
    }


    private static List<String> toUpperCase(String s) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return List.of(s.toUpperCase(), Thread.currentThread().getName());
    }
}
