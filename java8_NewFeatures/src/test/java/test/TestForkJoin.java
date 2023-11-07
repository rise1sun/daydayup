package test;

import org.junit.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.OptionalLong;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.LongStream;

public class TestForkJoin {

    /**
     * ForkJoin 框架
     */
    @Test
    public void test01() {
        Instant start = Instant.now();

        ForkJoinPool pool = new ForkJoinPool();
        ForkJoinCalculate task = new ForkJoinCalculate(0, 10000L);

        Long sum = pool.invoke(task);
        System.out.println(sum);

        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).getNano());
    }

    /**
     * 普通 for循环
     */
    @Test
    public void test02() {
        Instant start = Instant.now();

        Long sum = 0L;
        for (long i = 0; i < 100000000L; i++) {
            sum += i;
        }

        Instant end = Instant.now();
        System.out.println(Duration.between(start, end).getNano());
    }

    @Test
    public void test03() {

        OptionalLong reduce = LongStream.rangeClosed(1, 1000000L)
                .parallel()
                .reduce(Long::sum);
        long asLong = reduce.getAsLong();
        System.out.println(asLong);

    }

    @Test
    public void test04(){
        //串行流(单线程)：切换为并行流 parallel()
        //并行流：切换为串行流 sequential()
        long reduce = LongStream.rangeClosed(0, 100000000L)
                .parallel() //底层：ForkJoin
                .reduce(0, Long::sum);
        System.out.println(reduce);
    }


}
