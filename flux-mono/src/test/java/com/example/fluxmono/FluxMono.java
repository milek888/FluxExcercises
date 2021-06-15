package com.example.fluxmono;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;
import reactor.test.StepVerifier;

import java.time.Duration;

import static reactor.core.scheduler.Schedulers.parallel;

public class FluxMono {

    @Test
    public void testFlatMap() {

        Flux<String> justFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .window(2)
                .flatMap(w -> w).log();

        StepVerifier.create(justFlux)
                .expectNextCount(6)
                .verifyComplete();
    }


    @Test
    public void testParalell() {

        Flux<String> justFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .subscribeOn(Schedulers.parallel())
               /* .doOnNext(s -> System.out.println(s + " ---- " + Thread.currentThread().getName()))*/
                .log();

        StepVerifier.create(justFlux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void testMerge() {

        Flux<String> justFlux1 = Flux.just("A", "B", "C");
        Flux<String> justFlux2 = Flux.just("1", "2", "3");
        Flux<String> mergeFlux = Flux.merge(justFlux1, justFlux2).log();

        StepVerifier.create(mergeFlux)
                .expectNext("A", "B", "C", "1", "2", "3")
                .verifyComplete();
    }

    @Test
    public void testMergeWithDelay() {

        Flux<String> justFlux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> justFlux2 = Flux.just("1", "2", "3").delayElements(Duration.ofSeconds(1));
        Flux<String> mergeFlux = Flux.merge(justFlux1, justFlux2).log();

        StepVerifier.create(mergeFlux)
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void testConcat() {

        Flux<String> justFlux1 = Flux.just("A", "B", "C");
        Flux<String> justFlux2 = Flux.just("1", "2", "3");
        Flux<String> concatFlux = Flux.concat(justFlux1, justFlux2).log();

        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C", "1", "2", "3")
                .verifyComplete();
    }

    @Test
    public void testConcatWithDelay() {

        Flux<String> justFlux1 = Flux.just("A", "B", "C").delayElements(Duration.ofSeconds(1));
        Flux<String> justFlux2 = Flux.just("1", "2", "3").delayElements(Duration.ofSeconds(1));
        Flux<String> concatFlux = Flux.concat(justFlux1, justFlux2).log();

        StepVerifier.create(concatFlux)
                .expectNext("A", "B", "C", "1", "2", "3")
                .verifyComplete();
    }

    @Test
    public void testZip() {

        Flux<String> justFlux1 = Flux.just("A", "B", "C");
        Flux<String> justFlux2 = Flux.just("1", "2", "3");
        Flux<String> zipedFlux = Flux.zip(justFlux1, justFlux2, (f1,f2) -> f1.concat(f2)).log();

        StepVerifier.create(zipedFlux)
                .expectNext("A1", "B2", "C3")
                .verifyComplete();
    }


    @Test
    public void testErrorHandling() {
        Flux<String> justFlux = Flux.just("A", "B", "C")
                .concatWith(Flux.error(NullPointerException::new))
                .onErrorResume(e -> {System.out.println(e.getMessage());
                return Flux.just("1", "2");});

        StepVerifier.create(justFlux)
                .expectNext("A", "B", "C")
               /* .expectError(NullPointerException.class)*/
                .expectNext("1", "2")
                .verifyComplete();
    }

    @Test
    public void testFluxWithIntervalAndSubscribe() throws InterruptedException {

        Flux<Long> intervalFlux = Flux
                .interval(Duration.ofMillis(200))
                .take(10);

        intervalFlux
                .subscribe(element -> System.out.println("element = " + element + " " + Thread.currentThread().getName()));

        Thread.sleep(3000);
    }

    @Test
    public void testFluxWithInterval2() {

        Flux<Long> intervalFlux = Flux
                .interval(Duration.ofMillis(200))
                .take(10)
                .log();

      StepVerifier.create(intervalFlux)
              .expectNextCount(10)
              .verifyComplete();
    }

    @Test
    public void testFluxWithRangeAndSheduller() {//TODO jesli nie zrobimy Thread.sleep() to nic nie wypisze bo  z powodu .subscribeOn(parallel())
                                                 //TODO strumien bedzie wyonywany w innym watku niz cala metoda. Watek metody (main) sie konczy i flux nie zdazy wypisac
        Flux<Integer> rangeFlux = Flux.range(1, 5)
                .subscribeOn(parallel());
        rangeFlux.subscribe(System.out::println, System.out::println, () -> System.out.println("completed flow"), sub -> sub.request(3));
    }

    @Test
    public void testFluxWithRangeAndSheduller2() throws InterruptedException{//TODO wersja ze sleep - tu wypisze wszystko oprawnie
        Flux<Integer> rangeFlux = Flux.range(1, 5)
                .subscribeOn(parallel());
        rangeFlux.subscribe(System.out::println, System.out::println, () -> System.out.println("completed flow"), sub -> sub.request(3));

        Thread.sleep(3);
    }

    @Test
    public void testFluxWithRange() {//TODO tu nie potrzeba sleepa bo wszystko idzie po klei w jednym watku
        Flux<Integer> rangeFlux = Flux.range(1, 5);
        rangeFlux.subscribe(System.out::println, System.out::println, () -> System.out.println("completed flow"), sub -> sub.request(3));
    }



}
