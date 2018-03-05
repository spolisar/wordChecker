import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeSet;



/**
 * checks a file for unique words, and counts words that adhere to different attributes in regard to length (only counts unique words for these attributes)
 * removes enclosures such as (), [], {}, "", ''
 *   -this implementation may remove apostrophes on some plural possessive words
 * removes punctuation (does not include hyphenated words)
 */
public class wordChecker {
    private TreeSet<String> words;
    private String filename;
    private File file;
    private Scanner scanner;
    private int numUniqueWords; //number of uniqueWords
    private int numGT5; //number of words with more than 5 letters
    private int numLT5; //number of words with less than 5 letters
    private int numEven; //number of words with an even amount of letters
    private int numOdd; //number of words with an odd amount of letters




    public wordChecker(String filename) throws FileNotFoundException{
        this.filename = filename;
        this.file = new File(filename);
        if(! file.canRead()){
            throw new FileNotFoundException("file not found or is unreadable\n");
        }
        words = new TreeSet<>();
        this.scanner = new Scanner(file);
        scanner.useDelimiter("(\\s|\\.( |\\[)|\\,( |\\[)|\\(|\\)|\\[|\\]| \\'|\\' | \"|\" |[:;?!])");


    }

    /**
     * checks if a word has been found previously, if this is the first occurrence increment appropriate counters
     * @param word the word that is being checked
     * @return true if word is the first occurrence of that word in this file, false otherwise
     */
    private boolean checkWord(String word){
        if(word.equals(""))
            return false;
        if(! words.contains(word)){
            words.add(word);
            numUniqueWords ++;
            if(word.length() > 5)
                numGT5 ++;
            else if (word.length() < 5)
                numLT5 ++;
            if(word.length()%2 == 0)
                numEven ++;
            else if(word.length()%2 == 1)
                numOdd ++;
            return true;
        }
        else
            return false;
    }

    /**
     * goes through the file checking each word
     */
    public void processFile(){
        System.out.println(scanner.delimiter());
        while (scanner.hasNext()){
            String str = scanner.next().toLowerCase();
            checkWord(str);
        }
    }

    /**
     * prints the results of processing the file
     */
    public void printResults(){
        System.out.println("Results:");
        System.out.println("Number of unique words: " + numUniqueWords);
        System.out.println("Number of unique words with more than 5 letters: " + numGT5);
        System.out.println("Number of unique words with less than 5 letters: " + numLT5);
        System.out.println("Number of unique words with an even amount of letters: " + numEven);
        System.out.println("Number of unique words with an odd amount of letters: " + numOdd);
    }


    public static void main(String[] args){
        if(args.length != 1){
            System.out.println("incorrect call to wordChecker, wordChecker should be called in the form");
            System.out.println("java wordChecker <path_to_file>");
        }

        String filename = args[0];

        try {
            wordChecker wordChecker = new wordChecker(filename);
            wordChecker.processFile();
            wordChecker.printResults();
        }
        catch (FileNotFoundException exception){
            System.out.println("the file was not found, or it was unreadable");
            System.out.println("try again with a valid file");
        }
    }
}
