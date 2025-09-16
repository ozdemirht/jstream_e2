import java.util.Objects;
import java.util.stream.Stream;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!\n");
        StreamFilter aFilter = new StreamFilter();
        String[] lines = {    "QF: Hello"
                            , "LOL: World Hello!"
                            , "LOL: Hello World"
                            , "QF: World"
                            , "LOL: Our Earth is our World"
                            , "LOL: Our Earth is our World, Hello"
                        };
        Stream.of(lines)
                .map(aFilter::filter)
                .filter(Objects::nonNull)
                .forEach(System.out::println);
    }
}