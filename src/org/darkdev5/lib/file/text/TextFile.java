package org.darkdev5.lib.file.text;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.darkdev5.lib.file.text.option.CountType;

/**
 *
 * @author darkDev5
 * @version 1.0
 * @since 1.8
 */
public @Getter
@Setter
class TextFile {

    private String filePath;

    public TextFile(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Reads entire file.
     *
     * @return Returns the text file content
     * @throws IOException Throws IOException if error has occurred during
     * process.
     */
    public String read() throws IOException {
        StringBuilder sb = new StringBuilder();

        Files.readAllLines(Paths.get(filePath))
                .forEach(line -> sb.append(line).append("\n"));
        return sb.toString();
    }

    /**
     * Reads first line of the file.
     *
     * @return Returns the first line of the file.
     * @throws IOException Throws IOException if error has occurred during
     * process.
     */
    public String readFirstLine() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        if (lines.isEmpty()) {
            return null;
        }

        return lines.get(0);
    }

    /**
     * Reads last line of the file.
     *
     * @return Returns the last line of the file.
     * @throws IOException Throws IOException if error has occurred during
     * process.
     */
    public String readLastLine() throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));

        if (lines.isEmpty()) {
            return null;
        }

        return lines.get(lines.size() - 1);
    }

    /**
     * Write text to the file. It will erase entire file and writes to the file.
     *
     * @param content The content you want to write to the file.
     * @return Returns true if write was successful and false if not.
     * @throws IOException Throws IOException if error has occurred during
     * process.
     */
    public boolean write(String content) throws IOException {
        FileWriter wr = new FileWriter(new File(filePath));
        wr.write(content);
        wr.close();

        return true;
    }

    /**
     * Write text to the file. It will erase entire file and writes to the file.
     *
     * @param content The content you want to write to the file.
     * @return Returns true if write was successful and false if not.
     * @throws IOException Throws IOException if error has occurred during
     * process.
     */
    public boolean write(List<String> content) throws IOException {
        FileWriter wr = new FileWriter(new File(filePath));

        for (int i = 0; i < content.size(); i++) {
            wr.write(content.get(i));

            if (i + 1 < content.size()) {
                wr.write(System.lineSeparator());
            }
        }

        wr.close();
        return true;
    }

    /**
     * Append text to the file.
     *
     * @param content The content you want to append to the file.
     * @return Returns true if append was successful and false if not.
     * @throws IOException Throws IOException if error has occurred during
     * process.
     */
    public boolean append(String content) throws IOException {
        FileWriter wr = new FileWriter(new File(filePath), true);
        wr.write(content);
        wr.close();

        return true;
    }

    /**
     * Append text to the file.
     *
     * @param content The content you want to append to the file.
     * @return Returns true if append was successful and false if not.
     * @throws IOException Throws IOException if error has occurred during
     * process.
     */
    public boolean append(List<String> content) throws IOException {
        FileWriter wr = new FileWriter(new File(filePath), true);

        for (int i = 0; i < content.size(); i++) {
            wr.write(content.get(i));

            if (i + 1 < content.size()) {
                wr.write(System.lineSeparator());
            }
        }

        wr.close();
        return true;
    }

    /**
     * Clears entire text file.
     *
     * @throws IOException Throws IOException if error has occurred during
     * process.
     */
    public void clear() throws IOException {
        FileWriter wr = new FileWriter(new File(filePath));
        wr.write("");
        wr.close();
    }

    /**
     * Reads entire file and start searching the key in the content of file.
     *
     * @param key The key you want to search.
     * @param wholeWord True if you want to set exact match and false if not.
     * @param caseSensitive True if case sensitivity is important and false if
     * not.
     * @return Returns true if match found and false if not.
     * @throws IOException Throws IOException if error has occurred during
     * process.
     */
    public boolean search(String key, boolean wholeWord, boolean caseSensitive) throws IOException {
        List<String> content = Files.readAllLines(Paths.get(filePath));
        List<String> words = new ArrayList<>();

        for (String line : content) {
            words.addAll(Arrays.asList(line.split("\\s+")));
        }

        for (String word : words) {
            if (!caseSensitive) {
                key = key.toLowerCase();
                word = word.toLowerCase();
            }

            if (wholeWord && key.equals(word)) {
                return true;
            } else if (!wholeWord && word.contains(key)) {
                return true;
            }

        }

        return false;
    }

    /**
     * Count empty lines, lines, words, characters and spaces easily from entire
     * file.
     *
     * @param types Specify the count types.
     * @return Returns the total items found in the file.
     * @throws IOException Throws IOException if error has occurred during
     * process.
     */
    public long count(CountType... types) throws IOException {
        List<String> content = Files.readAllLines(Paths.get(filePath));

        long emptyLines = 0, lines = 0, words = 0,
                characters = 0, spaces = 0, total = 0;

        for (String line : content) {
            if (line.equals("")) {
                emptyLines++;
            }

            lines++;

            for (String word : line.split("\\s+")) {
                words++;

                for (char character : word.toCharArray()) {
                    if (character == ' ') {
                        spaces++;
                    }

                    characters++;
                }
            }
        }

        for (CountType type : types) {
            switch (type) {
                case EmptyLine:
                    total += emptyLines;
                    break;

                case Line:
                    total += lines;
                    break;

                case Character:
                    total += characters;
                    break;

                case Word:
                    total += words;
                    break;

                case Spaces:
                    total += spaces;
                    break;

            }
        }

        return total;
    }
}
