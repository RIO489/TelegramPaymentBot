Telegram Payment Bot
Telegram Payment Bot is a Telegram bot designed to help manage and process payments. The bot allows merchants to add new payers, import payment registries, and broadcast messages to all payers. Payers can authenticate themselves, view their payment details, and make payments through the bot. Cashiers can find payers and confirm payments manually.

Features
Merchant Functionality:

Add new payers
Import payment registries
Broadcast messages to all payers
Payer Functionality:

Authenticate using phone number and password
View payment details
Make payments
Cashier Functionality:

Find payers by phone number, full name, or tax ID
Confirm payments manually
Admin Functionality:

Add merchants and cashiers
Broadcast messages to all users
Getting Started
Prerequisites
Java 11 or higher
Maven
Telegram Bot Token (created using @BotFather)
Installation
Clone the repository:

sh
Копіювати код
git clone https://github.com/yourusername/telegram-payment-bot.git
cd telegram-payment-bot
Set up environment variables:

Ensure you have your Telegram Bot Token saved as an environment variable:

On Windows (PowerShell):

sh
Копіювати код
$env:TELEGRAM_BOT_TOKEN="YOUR_TELEGRAM_BOT_TOKEN"
On Linux or macOS:

sh
Копіювати код
export TELEGRAM_BOT_TOKEN="YOUR_TELEGRAM_BOT_TOKEN"
Build the project:

sh
Копіювати код
mvn clean install
Run the application:

sh
Копіювати код
java -jar target/TelegramBot-0.0.1-SNAPSHOT.jar
Configuration
The bot configuration is managed through the application.properties file located in src/main/resources/. Ensure the following properties are set correctly:

properties
Копіювати код
spring.jpa.show-sql=true
telegram.bot.name=TelegramPaymentBot
telegram.bot.token=${TELEGRAM_BOT_TOKEN}
server.port=8081
Usage
Start the bot by running the application as described above.

Interact with the bot using the following commands:

General Commands:

/start - Start interacting with the bot.
/help - Get help on available commands.
Payer Commands:

/pay - Pay for a payment instruction.
/payments - View your payments.
Merchant Commands:

/add_payer <name> <taxId> <phone> <password> - Add a new payer.
/import_registry - Import payment registry (future implementation).
/broadcast_message <message> - Send a message to all payers.
Cashier Commands:

/find_payer <phone/taxId/fullName> - Find payer by phone number, tax ID, or full name.
/confirm_payment <taxId> <paymentId> - Confirm a payment.
Admin Commands:

/create_merchant <name> <password> - Create a new merchant.
/add_cashier <name> <password> - Add a new cashier.
/broadcast_message <message> - Send a message to all users.
License
This project is licensed under the MIT License - see the LICENSE file for details.

Acknowledgements
Telegram Bot API documentation: Telegram Bot API
Spring Boot documentation: Spring Boot
Feel free to contribute and raise issues for any bugs or feature requests. Happy coding!
