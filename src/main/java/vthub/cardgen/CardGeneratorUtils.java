package vthub.cardgen;

import org.apache.commons.lang.ArrayUtils;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class CardGeneratorUtils
{

    private CardGeneratorUtils()
    {
    }

    public static int[] stringToInts(String s)
    {
        return Optional.ofNullable(s).orElse("")
                .chars()
                .map(c -> Integer.valueOf(Character.toString((char) c)))
                .toArray();
    }

    public static int luhnChecksum(int... ints)
    {
        int oddSum = IntStream.range(0, ints.length)
                .filter(i -> i % 2 != 0)
                .map(i -> ints[ints.length - i - 1])
                .map(i -> i * 2)
                .map(i -> i > 9 ? i - 9 : i)
                .sum();
        int evenSum = IntStream.range(0, ints.length)
                .filter(i -> i % 2 == 0)
                .map(i -> ints[ints.length - i - 1])
                .sum();
        return (oddSum + evenSum) % 10;
    }

    public static int luhnCheckDigit(int... ints)
    {
        int checksum = luhnChecksum(ArrayUtils.add(ints, 0));
        return checksum == 0 ? checksum : 10 - checksum;
    }

    public static String generateNumber()
    {
        int[] ints = IntStream.rangeClosed(1, 15)
                .map(i -> new Random().nextInt(10))
                .toArray();
        return Arrays.stream(ArrayUtils.add(ints, luhnCheckDigit(ints)))
                .boxed()
                .map(Object::toString)
                .collect(Collectors.joining());
    }

}
