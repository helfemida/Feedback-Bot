package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.*;

public class BotController extends TelegramLongPollingBot {
    ArrayList<String> AminaS = new ArrayList<>();
    ArrayList<String> ElnurM = new ArrayList<>();
    ArrayList<String> NauryzbaiS = new ArrayList<>();
    int[][][] AminaRATING=new int[2][2][1000],ElnurRATING=new int[2][2][1000],NauryzbaiRATING=new int[2][2][1000];

    String teacherName;
    public boolean isAcceptible(String command, String input){
        char[] c1=command.toCharArray(),c2=input.toCharArray();
        int mistakesAcceptible=1;
        int mistakes=0;
        if(command.equals(input))return true;
        if(command.length()==input.length()){
            for(int i=0;i<command.length();i++){
                if(c1[i]!=c2[i]){
                    mistakes++;
                }
            }
            System.out.println(mistakes);
            return mistakes<=mistakesAcceptible?true:false;
        }
        return false;
    }
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
                    if(isAcceptible("/start",messageText)){
                        prepareAndSendMessage(chatId,"Welcome to our bot, please, use menu to rate/comment/contact your teacher.");
                    }
                    else if(messageText.equals("/rateteacher")){
                        rateButtons(chatId);
                    }
                    else if(Character.isDigit(messageText.charAt(0))){
                        prepareAndSendMessage(chatId,"Thanks for your feedback!");
                    }
                    else if(messageText.equals("Amina Shaikym") || messageText.equals("Nauryzbai Sapargali") || messageText.equals("Yelnur Mutaliyev")){
                        addRating(chatId);
                    }
                    else if(isAcceptible("/contact Amina",messageText)) {
                        prepareAndSendMessage(chatId, "amina.shaikym@sdu.edu.kz");
                    }
                    else if(isAcceptible( "/contact Yelnur", messageText)) {
                        prepareAndSendMessage(chatId, "yelnur.mutaliyev@sdu.edu.kz");
                    }

                     else if(isAcceptible("/contact Nauryzbai",messageText)) {
                         prepareAndSendMessage(chatId, "nauryzbai.sapargali@sdu.edu.kz");
                     }
                     else if(isAcceptible("/contact",messageText)) {
                         prepareAndSendMessage(chatId, "write in format: /contact NAME");
                     }
                    else if(isAcceptible( "/leaderboard",messageText)) {

                    }
                    else if(isAcceptible( "showallcomments",messageText)){
                        messageText = update.getMessage().getText();
                        switch (messageText) {
                            case "Amina":
                                allComments(chatId, AminaS);
                                break;
                            case "Nauryzbai":
                                allComments(chatId, NauryzbaiS);
                                break;
                            case "Elnur":
                                allComments(chatId, ElnurM);
                                break;
                            default:
                                prepareAndSendMessage(chatId, "Sorry this teacher is not included to our list");
                        }
                    }
                    else{
                        prepareAndSendMessage(chatId, "Sorry, command was not recognized");
                    }
                }
        }


    private void prepareAndSendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        executeMessage(message);
    }

    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        // TODO
        return "hackathon22_bot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "5922989223:AAHiSt-DMCzSpXxBYrGvD_GW9fhyfaZSstY";
    }

    private void allComments(long chatId,ArrayList<String> name){
        for (int i = 0; i < name.size(); i++) {
            prepareAndSendMessage(chatId, name.get(i));
        }
    }

    private void rateButtons (long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Choose the teacher: ");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add("Amina Shaikym");
        row.add("Yelnur Mutaliyev");
        row.add("Nauryzbai Sapargali");

        keyboardRows.add(row);

        row = new KeyboardRow();
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(keyboardMarkup);

        executeMessage(message);
    }
    private void addRating(long chatId){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText("Choose the points: ");

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add("1");
        row.add("2");
        row.add("3");
        row.add("4");
        row.add("5");

        keyboardRows.add(row);

        row = new KeyboardRow();
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(keyboardMarkup);

        executeMessage(message);
    }
}

