import com.mifmif.common.regex.Generex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


class TextAnalyzerTest {
// Создаем файл test.txt, записываем в него ровно 10000 случайных слов со знаками и проверяем, корректно ли из файла количество слов подсчитывает наш getwords() и удаляем файл
    @Test
    void getWords() throws IOException {
        ArrayList<String> rightWords = new ArrayList<>();
        File file = new File("res/test.txt");
        file.createNewFile();
        Generex newWord = new Generex("[a-zA-Z0-9\\-']{8}");     // генерация слов (вытащил из maven)
        Generex trash = new Generex("[!\"\\#$%&()*+,./:;<=>?@\\[\\]^_`{|}~]{1,6}"); //генерация любых символов между словами 1-6 символов(пробелы, точки и тд)
        String randomWord;
        BufferedWriter writer = new BufferedWriter(new FileWriter("res/test.txt"));
        for (int i = 0; i < 10000; i++) {  //можно изменять количество сколько угодно
            randomWord = newWord.random();
            rightWords.add(randomWord.toLowerCase());
            writer.write(randomWord);
            writer.write(trash.random());
        }
        writer.close();
        String text = new String(Files.readAllBytes(Paths.get("res/test.txt")));
        TextAnalyzer analyzer = new TextAnalyzer(text);
//        Assertions.assertEquals(rightWords, analyzer.getWords());    // если будут какие-то несовпадения
        Assertions.assertEquals(rightWords.size(), analyzer.getWords().size());
        file.delete();
    }

    // Создаем файл test1.txt, записываем в него ровно 10000 случайных слов со знаками и повторяющимся случайным словом и проверяем, корректно ли все отрабатывает
    @Test
    void getMostFrequentWord() throws IOException {
        File file = new File("res/test1.txt");
        Generex newWord = new Generex("[a-zA-Z0-9\\-']{8}");     // генерация слов (вытащил из maven)
        file.createNewFile();
        String mostFrequentWord = new Generex("[a-zA-Z0-9\\-']{3,9}").random();
        Generex trash = new Generex("[!\"\\#$%&()*+,./:;<=>?@\\[\\]^_`{|}~]{1,6}"); //генерация любых символов между словами (пробелы, точки и тд)
        String randomWord;
        BufferedWriter writer = new BufferedWriter(new FileWriter("res/test1.txt"));
        for (int i = 0; i < 10000; i++) {  //можно изменять количество сколько угодно
            randomWord = newWord.random();
            writer.write(randomWord);
            writer.write(trash.random());
            writer.write(mostFrequentWord);
            writer.write(trash.random());
        }
        writer.close();
        String text = new String(Files.readAllBytes(Paths.get("res/test1.txt")));
        TextAnalyzer analyzer = new TextAnalyzer(text);
        Assertions.assertEquals(mostFrequentWord.toLowerCase(), analyzer.getMostFrequentWord());
        file.delete();
    }
}