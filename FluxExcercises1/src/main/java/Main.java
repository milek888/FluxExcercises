import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class Main {

    public static void main(String[] args) throws InterruptedException {


        Flux<Film> films = Flux.just(new Film("Indiana Johnes", "adventure", 1984),
                new Film("Seven", "thriller", 1996),
                new Film("It", "horror", 1978),
                new Film("Zwierzogrod", "cartoon", 2015));

        Flux<Film> films2 = Flux.empty();

        /*films.map(film -> film.getTitle().toUpperCase()).log().subscribe(System.out::println);*/

        //----------------------------------------------------------------------------------------------------------------

        /* films.log().subscribe(new FilmSubscriber());*/

        //----------------------------------------------------------------------------------------------------------------

/*        List<String> students = Arrays.asList("Agata", "Milosz", "Baśka", "Robert");
        Flux<String> studentsFlux = Flux.fromIterable(students);
        studentsFlux.subscribe(System.out::println);*/

        //----------------------------------------------------------------------------------------------------------------

/*        Flux<Integer> range = Flux.range(8, 10);
        range.subscribe(System.out::println);*/

        //----------------------------------------------------------------------------------------------------------------

  /*      Flux<Integer> range2 = Flux.range(8, 10);
        range2.map(value -> {
            if (value < 12) return 1;
            else throw new RuntimeException("ala ma kota");
        })
                .subscribe(System.out::println, System.out::println);
*/
        //----------------------------------------------------------------------------------------------------------------

/*        Flux.interval(Duration.ofMillis(100))
                .take(9)
                .doOnNext(System.out::println).subscribe();*/


        //----------------------------------------------------------------------------------------------------------------

/*        Flux<String> words = Flux.just("Milosz", "Mazurek", "kieubasa", "i", "sznurek");

        words.flatMap(word -> Flux.fromArray(word.split("")))
                .concatWith(Flux.just("11111", "222"))
                .distinct()
                .sort().zipWith(Flux.range(1,100), (letter, number) -> String.format("%2d. %s", number, letter))
                .subscribe(System.out::println);*/

        //----------------------------------------------------------------------------------------------------------------

/*        Flux<Student> students = Flux.just(new Student("Milosz",40), new Student("Agata",40),
                new Student("Robert", 35), new Student("Siergiej", 34), new Student("Marcn",36));

        students.flatMap(student -> {Mono<String> name = Mono.just(student.getFirtsName());
                                     Mono<Integer> age = Mono.just(student.getAge());
                                     return name.zipWith(age, (n, a) -> String.format("%2s is %d years old", n, a));
        }).subscribe(System.out::println);*/

        //----------------------------------------------------------------------------------------------------------------
        //films.map(film -> Main.processFilm2(film)).subscribe();

 /*       System.out.println("Thrad is = " + Thread.currentThread().getName());
       films.flatMap(film -> Main.processFilm(film), 2).subscribe(System.out::println );*/

        //films.map(film -> Main.processFilm2(film)).subscribeOn(Schedulers.parallel()).subscribe(System.out::println);
        //----------------------------------------------------------------------------------------------------------------
/*
        films.map(Film::getTitle)
                .subscribe(System.out::println, System.out::println, () -> System.out.println("Completed"), subscription -> subscription.request(3));*/

        //----------------------------------------------------------------------------------------------------------------
 /*       films.map(Film::getTitle).subscribe(new SampleSubscriber<String>());*/
        //----------------------------------------------------------------------------------------------------------------

/*        Flux<String> firstNames = Flux.just("John", "Jane");
        Flux<Long> delay = Flux.interval(Duration.ofMillis(5));
        Flux<String> firstNamesWithDelay = firstNames.zipWith(delay, (s, l) -> s);
        firstNamesWithDelay.subscribe(System.out::println);*/

        //----------------------------------------------------------------------------------------------------------------
       // CountDownLatch latch = new CountDownLatch(1);

/*        Mono<String> mono = Mono.just("hello ");
        Thread thread = new Thread(() -> mono
                .map(msg -> msg + "thread ")
                .subscribe(v ->
                        System.out.println(v + Thread.currentThread().getName())
                )
        );
        thread.start();
        thread.join();*/
        //latch.await();
        //----------------------------------------------------------------------------------------------------------------

/*        CountDownLatch latch = new CountDownLatch(1);

        Mono<String> mono2 = Mono.just("hello ");
        Thread thread2 = new Thread(() -> mono2
                .map(msg -> msg + "thread ")
                .subscribe(v ->
                        System.out.println(v + Thread.currentThread().getName())
                )
        );
        thread2.start();
        latch.await();*/


        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        System.out.println("111111111111111 " + Thread.currentThread().getName());

        final Flux<String> flux = Flux
                .range(1, 10)
                .map(i -> {
                    System.out.println("222222222222222222222222 " + Thread.currentThread().getName());
                    return 10 + i;
                })
                .publishOn(s)
                .map(i -> {
                    System.out.println("3333333333333333333 " + Thread.currentThread().getName());
                    return "value " + i;
                });

        flux.subscribe();

        //----------------------------------------------------------------------------------------------------------------
    }

    private static Mono<String> processFilm(Film film) {
        System.out.println("Thrad is = " + Thread.currentThread().getName());
        return Mono.just(film.getTitle() + " is " + film.getType() + " from year " + film.getYear());
    }

    private static String processFilm2(Film film) {
        System.out.println("Thrad is = " + Thread.currentThread().getName());
        return film.getTitle() + " is " + film.getType() + " from year " + film.getYear();
    }
}
