package Main;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.ArrayList;
import Main.Data.Card;
import Main.Data.CardType;
import Main.Data.Deck;
import Main.Data.Field;

// TODO: add comments

/**
 * 保存和加载主窗口的数据
 * 主窗口中的数据包括牌组和卡片
 * 保存和加载的数据格式为文本文件
 * 保存的文件名为"decks.mw"
 * 保存的文件格式为:
 * 1. 一行一个牌组的名字
 * 2. 一行一个卡片的类型
 * 3. 一行一个卡片的字段名
 * 4. 一行一个卡片的字段值
 * 5. 重复2-4直到所有卡片的字段都保存完毕
 * 6. 重复1-5直到所有牌组都保存完毕
 */
public class MainWindowSaveLoader {
    public static void saveDecks(ArrayList<Deck> decks) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new java.io.FileWriter("decks.mw"));
            for (Deck deck : decks) {
                writer.write(deck.getName());
                writer.newLine();
                if (deck.getCards().size() == 0) {
                    writer.newLine();
                }
                for (Card card : deck.getCards()) {
                    CardType type = card.getCardType();
                    writer.write(type.getName());
                    saveCardType(type);
                    int cardFieldCount = type.getTotalFieldCount();
                    for (int i = 0; i < cardFieldCount; i++) {
                        Field field = card.getFields()[i];
                        String fieldName = field.getName();
                        writer.write(fieldName);
                        writer.newLine();
                        Object value = field.getValue();
                        writer.write(value.toString());
                        writer.newLine();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * EXAMPLE OF DECK FILE CONTAINING 1 SAMPLE DECK.
     * Tt has the name "Deck 0", has a card type  "Default" with 2 fields, "Front" and "Back"
     * Everything under the deck name is 1 or more cards.
     * _____________________________________________________________
     * Deck 0
     * Default
     * 2
     * Front
     * Back
     * <!DOCTYPE html><html><head><meta charset="UTF-8"><title>Card</title><style>body{background-color: black; color: white;}</style></head><body><h1>{{front}}</h1></body></html>
     * <!DOCTYPE html><html><head><meta charset="UTF-8"><title>Card</title><style>body{background-color: black; color: white;}</style></head><body><h1>{{back}}</h1></body></html>
     * body{background-color: black; color: white;}
     * <h1>{{front}}</h1>
     * <h1>{{back}}</h1>
     * _____________________________________________________________
     * 
     * @return
     */
    public static ArrayList<Deck> loadDecks() {
        ArrayList<Deck> decks = new ArrayList<Deck>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new java.io.FileReader("decks.mw"));
            String line = reader.readLine();
            while (line != null) {
                Deck deck = new Deck(line); // creates deck where line is deck name
                line = reader.readLine();
                if (!line.equals("")) { // null means deck has no cards, so can skip
                    // everything below here is counting cards
                    String cardTypeName = line; 
                    line = reader.readLine(); // line is now the number of fields in the card type
                    int cardFieldCount = Integer.valueOf(line);
                    Field[] fieldNames = new Field[cardFieldCount]; 
                    for (int i = 0; i < cardFieldCount; i++) {
                        String fieldName = reader.readLine();
                        fieldNames[i] = new Field(fieldName);
                    }
                    String htmlFront = reader.readLine();
                    String htmlBack = reader.readLine();
                    String css = reader.readLine();
                    String htmlBodyFront = reader.readLine();
                    String htmlBodyBack = reader.readLine();
                    Field[] fields = new Field[cardFieldCount]; // x number of fields follow here
                    for (int i = 0; i < cardFieldCount; i++) { // each field is a pair
                        String fieldName = reader.readLine(); // consisting of a name and value
                        String fieldValue = reader.readLine();
                        fields[i] = new Field(fieldName, fieldValue);
                    }
                    CardType type = new CardType(cardTypeName, fields, htmlFront, htmlBack, css, htmlBodyFront, htmlBodyBack);
                    Card card = new Card(type, fields);
                    deck.addCard(card);
                }
                decks.add(deck);
                line = reader.readLine();
            }
            return decks;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static void saveCardType(CardType cardType) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new java.io.FileWriter("cardTypes.mw"));
            writer.write(getCardTypeString(cardType));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static String getCardTypeString(CardType cardType) {
        StringBuilder sb = new StringBuilder();
        String name = cardType.getName();
        int totalFieldCount = cardType.getTotalFieldCount();
        sb.append(name + "\n");
        sb.append(totalFieldCount + "\n");
        for (Field field : cardType.getFields()) {
            String fieldName = field.getName();
            sb.append(fieldName + "\n");
        }
        // TODO: make you able to save newlines in the html and css
        sb.append(cardType.getHtmlFront().replaceAll("\\r\\n|\\r|\\n", "") + "\n");
        sb.append(cardType.getHtmlBack().replaceAll("\\r\\n|\\r|\\n", "") + "\n");  
        sb.append(cardType.getCss().replaceAll("\\r\\n|\\r|\\n", "") + "\n");       
        sb.append(cardType.getHtmlBodyFront().replaceAll("\\r\\n|\\r|\\n", "") + "\n");
        sb.append(cardType.getHtmlBodyBack().replaceAll("\\r\\n|\\r|\\n", "") + "\n");
        return sb.toString();
    }

    private static ArrayList<CardType> getCardTypes() {
        ArrayList<CardType> cardTypes = new ArrayList<CardType>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new java.io.FileReader("cardTypes.mw"));
            String line = reader.readLine();
            while (line != null) {
                String name = line;
                int totalFieldCount = Integer.parseInt(reader.readLine());
                Field[] fields = new Field[totalFieldCount];
                for (int i = 0; i < totalFieldCount; i++) {
                    String fieldName = reader.readLine();
                    fields[i] = new Field(fieldName); // +2 because of name and totalFieldCount
                }
                String htmlFront = reader.readLine();
                String htmlBack = reader.readLine();
                String css = reader.readLine();
                String htmlBodyFront = reader.readLine();
                String htmlBodyBack = reader.readLine();
                cardTypes.add(new CardType(name, fields, htmlFront, htmlBack, css, htmlBodyFront, htmlBodyBack));
            }
            return cardTypes;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cardTypes;
    }

    public static void saveCardTypes(ArrayList<CardType> cardTypes) {
        BufferedWriter writer = null;
        try {
            writer = new BufferedWriter(new java.io.FileWriter("cardtypes.mw"));
            for (CardType cardType : cardTypes) {
                writer.write(getCardTypeString(cardType));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Saved to file");
    }

    // load card types
    public static ArrayList<CardType> loadCardTypes() {
        ArrayList<CardType> cardTypes = new ArrayList<CardType>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new java.io.FileReader("cardtypes.mw"));
            String line = reader.readLine();
            while (line != null) {
                String name = line;
                int totalFieldCount = Integer.parseInt(reader.readLine());
                Field[] fields = new Field[totalFieldCount];
                for (int i = 0; i < totalFieldCount; i++) {
                    fields[i] = new Field(reader.readLine());
                }
                String htmlFront = reader.readLine();
                String htmlBack = reader.readLine();
                String css = reader.readLine();
                String htmlBodyFront = reader.readLine();
                String htmlBodyBack = reader.readLine();
                cardTypes.add(new CardType(name, fields, htmlFront, htmlBack, css, htmlBodyFront, htmlBodyBack));
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return cardTypes;
    }
}
