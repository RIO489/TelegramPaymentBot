package com.telegrambot.bot;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.telegrambot.entity.Payer;
import com.telegrambot.entity.Payment;
import com.telegrambot.entity.User;
import com.telegrambot.enums.PaymentStatus;
import com.telegrambot.enums.UserRole;
import com.telegrambot.expection.PayerException;
import com.telegrambot.mapper.PayerMapper;
import com.telegrambot.mapper.PaymentMapper;
import com.telegrambot.mapper.UserMapper;
import com.telegrambot.service.PayerService;
import com.telegrambot.service.PaymentService;
import com.telegrambot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.message.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Component
public class MessageHandler {
    @Autowired
    private PayerService payerService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private UserService userService;
    @Autowired
    private PayerMapper payerMapper;
    @Autowired
    private PaymentMapper paymentMapper;
    @Autowired
    private UserMapper userMapper;
    private TelegramClient telegramClient;

    private Map<Long, Payer> payerSessions = new HashMap<>();
    private Map<Long, User> userSessions = new HashMap<>();
    private Map<Long, String> authStates = new HashMap<>();


    public void setTelegramClient(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }

    public void handleUpdate(Update update) throws PayerException {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            if (authStates.containsKey(chatId) && authStates.get(chatId).equals("PHONE_NUMBER_REQUESTED")) {
                handlePayerLogin(chatId, payerSessions.get(chatId).getPhoneNumber(), messageText);
                return;
            }
            if (userSessions.containsKey(chatId)) {
                UserRole role = userSessions.get(chatId).getRole();
                switch (role) {
                    case MERCHANT:
                        handleMerchantCommand(chatId, messageText.split(" ")[0], messageText);
                        break;
                    case ADMIN:
                        handleAdminCommand(chatId, messageText.split(" ")[0], messageText);
                        break;
                    case CASHIER:
                        handleCashierCommand(chatId, messageText.split(" ")[0], messageText);
                        break;
                    default:
                        sendResponse(chatId, "Unknown role. Please contact support.");
                        break;
                }
                return;
            }


            switch (messageText.split(" ")[0]) {
                case BotCommands.START:
                    showInitialOptions(chatId);
                    break;
                case BotCommands.I_AM_PAYER:
                    requestPhoneNumber(chatId);
                    break;
                case BotCommands.I_AM_NOT_PAYER:
                    requestLoginCredentials(chatId);
                    break;
                case BotCommands.LOGIN:
                    handleLoginCommand(chatId, messageText);
                    break;
                case BotCommands.PAYMENTS:
                    handleGetPayments(chatId);
                    break;
                default:
                    sendResponse(chatId, "Unknown command. Use /start to get started.");
                    break;
            }
        } else if (update.hasMessage() && update.getMessage().hasContact()) {
            handleContactUpdate(update.getMessage());
        } else if (update.hasCallbackQuery()) {
            handleCallbackQuery(update.getCallbackQuery());
        }
    }

    // General Commands
    private void showInitialOptions(long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Please select your role:")
                .replyMarkup(showInitialOptions())
                .build();

        logAction("Showing initial options", chatId);

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private InlineKeyboardMarkup showInitialOptions() {
        InlineKeyboardButton buttonPayer = new InlineKeyboardButton("I am a payer");
        buttonPayer.setCallbackData("/i_am_payer");

        InlineKeyboardButton buttonNotPayer = new InlineKeyboardButton("I am not a payer");
        buttonNotPayer.setCallbackData("/i_am_not_payer");

        InlineKeyboardRow row = new InlineKeyboardRow();
        row.add(buttonNotPayer);
        row.add(buttonPayer);

        List<InlineKeyboardRow> rows = new ArrayList<>();
        rows.add(row);

        return new InlineKeyboardMarkup(rows);
    }

    private void requestPhoneNumber(long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Please share your phone number for authentication:")
                .replyMarkup(createPhoneKeyboard())
                .build();

        authStates.put(chatId, "PHONE_NUMBER_REQUESTED");
        logAction("Requesting phone number", chatId);

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private ReplyKeyboardMarkup createPhoneKeyboard() {
        KeyboardButton button = new KeyboardButton("Share my phone number");
        button.setRequestContact(true);

        KeyboardRow row = new KeyboardRow();
        row.add(button);

        List<KeyboardRow> keyboard = new ArrayList<>();
        keyboard.add(row);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup(keyboard);
        keyboardMarkup.setKeyboard(keyboard);
        keyboardMarkup.setOneTimeKeyboard(true);
        keyboardMarkup.setResizeKeyboard(true);

        return keyboardMarkup;
    }

    private void requestLoginCredentials(long chatId) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Please enter your login credentials in the format: /login <username> <password>")
                .build();

        authStates.put(chatId, "CREDENTIALS_REQUESTED");
        logAction("Requesting login credentials", chatId);

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private void handleLoginCommand(long chatId, String messageText) {
        String[] parts = messageText.split(" ");
        if (authStates.get(chatId).equals("CREDENTIALS_REQUESTED") && parts.length == 3) {
            String username = parts[1];
            String password = parts[2];
            handleUserLogin(chatId, username, password);
        } else if (authStates.get(chatId).equals("PHONE_NUMBER_REQUESTED") && parts.length == 3) {
            String phoneNumber = parts[1];
            String password = parts[2];
            handlePayerLogin(chatId, phoneNumber, password);
        } else {
            sendResponse(chatId, "Usage: /login <username> <password>");
        }
        logAction("Handling login command", chatId);
    }

    private void handlePayerLogin(long chatId, String phoneNumber, String password) {
        try {
            Payer payer = payerMapper.toEntity(payerService.findByCredentials(phoneNumber, password));
            sendResponse(chatId, "Login successful! Type /help for available options");
            payerSessions.put(chatId, payer);
            authStates.remove(chatId);
        } catch (PayerException e) {
            sendResponse(chatId, "Invalid password. Please try again.");
        }
        logAction("Handling payer login", chatId);
    }

    private void handleUserLogin(long chatId, String username, String password) {
        try {
            User user = userMapper.toEntity(userService.getByUsernameAndPassword(username, password));
            sendResponse(chatId, "Login successful! Type /help for available options");
            userSessions.put(chatId, user);
            authStates.remove(chatId);
        } catch (Exception e) {
            sendResponse(chatId, "Invalid username or password. Please try again.");
        }
        logAction("Handling user login", chatId);
    }

    private void handleContactUpdate(Message message) {
        long chatId = message.getChatId();
        String phoneNumber = message.getContact().getPhoneNumber();
        payerSessions.put(chatId, new Payer(null, null, null, phoneNumber, null));
        sendResponse(chatId, "Please enter your password:");
        logAction("Handling contact update", chatId);
    }

    private String getHelpMessage(UserRole role) {
        switch (role) {
            case MERCHANT:
                return "Available commands for Merchant:\n"
                        + "/add_payer - Add a new payer\n"
                        + "/import_registry - Import payment registry\n"
                        + "/broadcast_message - Send a message to all payers";
            case PAYER:
                return "Available commands for Payer:\n"
                        + "/pay - Pay for a payment instruction\n"
                        + "/payments - View your payments";
            case CASHIER:
                return "Available commands for Bank Cashier:\n"
                        + "/find_payer - Find payer by phone number or ID\n"
                        + "/confirm_payment - Confirm a payment";
            case ADMIN:
                return "Available commands for Admin:\n"
                        + "/create_merchant - Create a new merchant\n"
                        + "/add_cashier - Add a new cashier\n"
                        + "/broadcast_message - Send a message to all users";
            default:
                return "Unknown role.";
        }
    }

    private void sendResponse(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();

        try {
            telegramClient.execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        logAction("Sending response", chatId);

    }

    private void handleCallbackQuery(CallbackQuery callbackQuery) {
        String callbackData = callbackQuery.getData();
        long chatId = callbackQuery.getMessage().getChatId();

        switch (callbackData) {
            case BotCommands.I_AM_PAYER:
                requestPhoneNumber(chatId);
                break;
            case BotCommands.I_AM_NOT_PAYER:
                requestLoginCredentials(chatId);
                break;
            default:
                sendResponse(chatId, "Unknown action.");
                break;
        }
        logAction("Handling callback query", chatId);
    }

    //Payer Commands
    private void handleGetPayments(long chatId) {
        Payer payer = payerSessions.get(chatId);
        if (payer == null) {
            sendResponse(chatId, "You need to login first.");
            return;
        }

        try {
            List<Payment> payments = paymentService.findPaymentsByPayer(payerMapper.toDTO(payer))
                    .stream()
                    .map(paymentMapper::toEntity)
                    .toList();
            if (payments.isEmpty()) {
                sendResponse(chatId, "No payments found.");
            } else {
                StringBuilder response = new StringBuilder();
                double totalAmount = 0;

                for (Payment payment : payments) {
                    response.append("Amount: ").append(payment.getAmount())
                            .append(", Purpose: ").append(payment.getPaymentPurpose())
                            .append(", Recipient: ").append(payment.getRecipient()).append("\n");
                    totalAmount += payment.getAmount().doubleValue();
                }

                response.append("Total amount to pay: ").append(totalAmount).append("\n");
                response.append("Click here to pay: [payment link]"); // Замінити на реальне посилання

                sendResponse(chatId, response.toString());
            }
        } catch (Exception e) {
            sendResponse(chatId, "Error retrieving payments: " + e.getMessage());
        }
        logAction("Handling get payments", chatId);
    }


    // Merchant Commands
    private void handleMerchantCommand(long chatId, String command, String messageText) throws PayerException {
        switch (command) {
            case "/add_payer":
                sendResponse(chatId, "Please provide payer details in the format: /add_payer_data <name> <taxId> <phone> <password>");
                break;
            case "/add_payment":
                sendResponse(chatId, "Please provide payment details in the format: /add_payment_data <payerPhone> <amount> <purpose> <recipient>");
                break;
            case "/import_registry":
                handleImportRegistry(chatId);
                break;
            case "/broadcast_message":
                handleBroadcastMessage(chatId, messageText);
                break;
            case "/help":
                sendResponse(chatId, getHelpMessage(UserRole.MERCHANT));
                break;
            default:
                handleMerchantDataInput(chatId, command, messageText);
                break;
        }
    }

    private void handleMerchantDataInput(long chatId, String command, String messageText) throws PayerException {
        if (command.equals("/add_payer_data")) {
            handleAddPayerData(chatId, messageText);
        } else if (command.equals("/add_payment_data")) {
            handleAddPaymentData(chatId, messageText);
        } else {
            sendResponse(chatId, "Unknown command for Merchant. Use /help to see available commands.");
        }
    }

    private void handleBroadcastMessage(long chatId, String messageText) {
        String[] parts = messageText.split(" ", 2);
        if (parts.length == 2) {
            String broadcastMessage = parts[1];

            for (Map.Entry<Long, Payer> entry : payerSessions.entrySet()) {
                sendResponse(entry.getKey(), broadcastMessage);
            }
            sendResponse(chatId, "Message broadcasted to all payers.");
        } else {
            sendResponse(chatId, "Usage: /broadcast_message <message>");
        }
    }

    private void handleImportRegistry(long chatId) {
        sendResponse(chatId, "Please upload the payment registry file.");
        // Implement the logic to handle the registry file upload and processing
        // This can be done by listening to file uploads and parsing the content
    }

    private void handleAddPayerData(long chatId, String messageText) {
        try {
            String[] parts = messageText.split(" ");
            if (parts.length == 5) {
                String name = parts[1];
                String taxId = parts[2];
                String phone = parts[3];
                String password = parts[4];
                Payer payer = new Payer(null, name, taxId, phone, password);
                payerService.createPayer(payerMapper.toDTO(payer));
                sendResponse(chatId, "Payer added successfully.");
            } else {
                sendResponse(chatId, "Invalid format. Use /add_payer <name> <taxId> <phone> <password>");
            }
        } catch (Exception e) {
            System.out.println(e.getCause().getMessage());
            sendResponse(chatId, e.getCause().getCause().getMessage());
        }
    }

    private void handleAddPaymentData(long chatId, String messageText) throws PayerException {
        String[] parts = messageText.split(" ");
        if (parts.length == 5) {
            String payerPhone = parts[1];
            BigDecimal amount = new BigDecimal(parts[2]);
            String purpose = parts[3];
            String recipient = parts[4];
            Payer payer = payerMapper.toEntity(payerService.findByPhoneNumber(payerPhone));
            if (payer == null) {
                sendResponse(chatId, "Payer not found. Please check the phone number.");
                return;
            }
            Long generatedLong = new Random().nextLong();
            Payment payment = new Payment(generatedLong, payer, amount, purpose, recipient, PaymentStatus.UNPAID, null);
            paymentService.createPayment(paymentMapper.toDTO(payment));
            sendResponse(chatId, "Payment added successfully.");
        } else {
            sendResponse(chatId, "Invalid format. Use /add_payment_data <payerPhone> <amount> <purpose> <recipient>");
        }
    }

    //Admin Methods
    private void handleAdminCommand(long chatId, String command, String messageText) {
        switch (command) {
            case "/create_merchant":
                handleCreateMerchant(chatId, messageText);
                break;
            case "/add_cashier":
                handleAddCashier(chatId, messageText);
                break;
            case "/broadcast_message":
                handleBroadcastMessage(chatId, messageText);
                break;
            case "/help":
                sendResponse(chatId, getHelpMessage(UserRole.ADMIN));
                break;
            default:
                sendResponse(chatId, "Unknown command for Admin. Use /help to see available commands.");
                break;
        }
    }

    private void handleCreateMerchant(long chatId, String messageText) {
        String[] parts = messageText.split(" ");
        if (parts.length == 3) {
            String username = parts[1];
            String password = parts[2];
            User user = new User(null, username, password, UserRole.MERCHANT);
            userService.createUser(userMapper.toDTO(user));
            sendResponse(chatId, "Merchant created successfully.");
        } else {
            sendResponse(chatId, "Invalid format. Use /create_merchant <username> <password>");
        }
    }

    private void handleAddCashier(long chatId, String messageText) {
        String[] parts = messageText.split(" ");
        if (parts.length == 3) {
            String username = parts[1];
            String password = parts[2];
            User user = new User(null, username, password, UserRole.CASHIER);
            userService.createUser(userMapper.toDTO(user));
            sendResponse(chatId, "Cashier created successfully.");
        } else {
            sendResponse(chatId, "Invalid format. Use /add_cashier <username> <password>");
        }
    }

    // Cashier commands
    private void handleCashierCommand(long chatId, String command, String messageText) throws PayerException {
        switch (command) {
            case "/find_payer":
                handleFindPayer(chatId, messageText);
                break;
            case "/confirm_payment":
                handleConfirmPayment(chatId, messageText);
                break;
            case "/help":
                sendResponse(chatId, getHelpMessage(UserRole.CASHIER));
                break;
            default:
                sendResponse(chatId, "Unknown command for Cashier. Use /help to see available commands.");
                break;
        }
    }

    private void handleFindPayer(long chatId, String messageText) throws PayerException {
        String[] parts = messageText.split(" ");
        if (parts.length == 2) {
            String searchKey = parts[1];
            List<Payer> payers = payerService.findPayers(searchKey)
                    .stream()
                    .map(payerMapper::toEntity)
                    .toList();
            if (payers.isEmpty()) {
                sendResponse(chatId, "No payer found with the provided information.");
            } else {
                for (Payer payer : payers) {
                    sendResponse(chatId, "Payer found: " + payer.getFullName() + ", Tax ID: " + payer.getTaxId());
                }
            }
        } else {
            sendResponse(chatId, "Usage: /find_payer <phone/taxId/fullName>");
        }
    }

    private void handleConfirmPayment(long chatId, String messageText) {
        String[] parts = messageText.split(" ");
        if (parts.length == 3) {
            String taxId = parts[1];
            String paymentId = parts[2];
            try {
                Payment payment = paymentMapper.toEntity(paymentService.confirmPayment(taxId, paymentId));
                sendResponse(chatId, "Payment confirmed successfully. Payment ID: " + payment.getId());
            } catch (Exception e) {
                sendResponse(chatId, "Error confirming payment: " + e.getCause().getMessage());
            }
        } else {
            sendResponse(chatId, "Usage: /confirm_payment <taxId> <paymentId>");
        }
    }

    // Logging Methods
    private void logAction(String action, long chatId) {
        System.out.println("\n----------------------------");
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(dateFormat.format(date));
        System.out.println("Action: " + action + " | Chat ID: " + chatId);
    }
}
