#include <WiFi.h>          
#include <PubSubClient.h>
#include <DHT.h>
#include <Wire.h>
#include <SPI.h>
#include <MFRC522.h>
#include<LiquidCrystal_I2C.h>      

// Định nghĩa chân kết nối của DHT11
#define DHTPIN 25
#define DHTTYPE DHT11 // DHT 11

//RFID
#define RST_PIN   0   // Định nghĩa chân RST của RC522
#define SS_PIN    5  // Định nghĩa chân SDA(SS) của RC522

#define SCK_PIN 18
#define MISO_PIN 19
#define MOSI_PIN 23

#define LED_PIN_1 4
#define LED_PIN_2 15
#define LED_PIN_ERROR 32
#define LED_PIN_SUCCESS 33
#define BUTTON_PIN_1 26  
#define BUTTON_PIN_2 27    

#define NTP_SERVER     "pool.ntp.org"
#define UTC_OFFSET     7  

// MQTT
// publish
#define humidity_topic "humidity"
#define temperature_topic "temperature"
#define rfid_topic "rfid"
#define button_topic_1 "button1"
#define button_topic_2 "button2"

// subscribe
#define lightbulb1_topic "lightbulb1"
#define lightbulb2_topic "lightbulb2"

const char* ssid = "Luan";
const char* password = "12345678";
const char* mqtt_server = "192.168.43.134";

float t; // Nhiệt độ
float h; // Độ ẩm
byte degree[8] = {
  0B01110, 0B01010, 0B01110, 0B00000, 0B00000, 0B00000, 0B00000, 0B00000
};
bool led1State = LOW;
bool led2State = LOW;
int button1_state;       // the current state of button
int last_button1_state;
int button2_state;       // the current state of button
int last_button2_state;
int UID[4]; // ID RFID
String cardUID = "";
bool checkRfid = true;
bool isInitialized = false;

int interval = 20000; // Khoảng thời gian giữa các đọc dữ liệu DHT11
unsigned long previousMillis = 0; // Thời gian kể từ sự kiện cuối cùng được kích hoạt

unsigned long startTime;
unsigned long mqttTimeout = 20000;

WiFiClient espClient;
PubSubClient client(espClient);
// LCD
LiquidCrystal_I2C LCD = LiquidCrystal_I2C(0x27, 16, 2);
//DHT
DHT dht(DHTPIN, DHTTYPE);
// RFID
MFRC522 mfrc522(SS_PIN, RST_PIN);

void setup() {
  Serial.begin(115200);
  pinMode(LED_PIN_1, OUTPUT);
  pinMode(LED_PIN_2, OUTPUT);
  pinMode(LED_PIN_ERROR, OUTPUT);
  pinMode(LED_PIN_SUCCESS, OUTPUT);

  pinMode(BUTTON_PIN_1, INPUT_PULLUP);
  pinMode(BUTTON_PIN_2, INPUT_PULLUP);

  button1_state = digitalRead(BUTTON_PIN_1);
  button2_state = digitalRead(BUTTON_PIN_2);
  // digitalWrite(LED_PIN_1, led1State);
  // digitalWrite(LED_PIN_2, led2State);
  dht.begin();
  setup_wifi();
  setup_lcd();
  setup_rfid();
  client.setServer(mqtt_server, 1883);
  client.setCallback(callback);
}

void loop() {
  if (!client.connected()) {
    reconnect();
  }
  mqtt_DHT11();
  mqtt_Button();
  mqtt_Rfid();
  client.loop();
}

void reconnect() {
  while (!client.connected()) {
    Serial.print("Attempting MQTT connection...");
    String clientId = "ESP32Client-";
    clientId += String(random(0xffff), HEX);
    // Attempt to connect
    if (client.connect(clientId.c_str())) {
      Serial.println("connected");

      // Once connected, publish an announcement...
      client.publish(temperature_topic, String(t).c_str(), true);
      client.publish(humidity_topic, String(h).c_str(), true);
      //client.publish(rfid_topic, cardUID.c_str());
      client.publish(button_topic_1, String(led1State).c_str());
      client.publish(button_topic_2, String(led2State).c_str());

      // ... and resubscribe
      client.subscribe(lightbulb1_topic);
      client.subscribe(lightbulb2_topic);
      client.subscribe("pubrfid");
    } else {
      Serial.print("failed, rc=");
      Serial.print(client.state());
      Serial.println(" try again in 5 seconds");
      delay(5000);
    }
  }
}

void callback(char* topic, byte* payload, unsigned int length) {
  // Xử lý dữ liệu từ chủ đề MQTT
  String message = "";
  for (int i = 0; i < length; i++) {
    message += (char)payload[i];
  }
  Serial.println("Message received: " + message);

  subscribeLightBuld(topic, message);
  subscribeRfid(topic, message);
}

void mqtt_DHT11() {
  h = dht.readHumidity();
  t = dht.readTemperature();
  if (isnan(h) || isnan(t)) {
    Serial.println("Failed to read from DHT sensor!");
    return;
  }
  // Serial.print("Temperature: ");
  //   Serial.println(t);
  //   Serial.print("Humidity: ");
  //   Serial.println(h);
  unsigned long currentMillis = millis();
  if ((unsigned long)(currentMillis - previousMillis) >= interval) {
    previousMillis = currentMillis;
    client.publish(temperature_topic, String(t).c_str(), true);
    client.publish(humidity_topic, String(h).c_str(), true);
  }
}

// WiFI
void setup_wifi() {
  WiFi.begin(ssid, password); 
  while (WiFi.status() != WL_CONNECTED) { 
    delay(500);
    Serial.print(".");
  }
  Serial.println();
  Serial.print("IP address: ");
  Serial.println(WiFi.localIP());
}

// RFID
void setup_rfid() {
  SPI.begin(SCK_PIN, MISO_PIN, MOSI_PIN); // Init SPI bus
  mfrc522.PCD_Init(); // Init MFRC522
}
void loop_read_rfid() {
  cardUID = "";
  if(checkRfid == true){
    startTime = millis();
    if(!mfrc522.PICC_IsNewCardPresent()) return;
    if(!mfrc522.PICC_ReadCardSerial()) return;
    for (byte i = 0; i < mfrc522.uid.size; i++) {
      UID[i] = mfrc522.uid.uidByte[i];
      if (UID[i] < 0x10) {
          cardUID += "0"; // Add leading zero if the byte is less than 0x10
      }
      cardUID += String(UID[i]);
    }
    mfrc522.PICC_HaltA();
    mfrc522.PCD_StopCrypto1();
    checkRfid = false;
  }else if (millis() - startTime >= mqttTimeout) {
    default_lcd();
    checkRfid = true;
  }
}

void mqtt_Rfid() {
  loop_read_rfid();
  if(cardUID != "") {
    client.publish(rfid_topic, cardUID.c_str());
    Serial.print("Mã thẻ của bạn: ");
    Serial.println(cardUID);
    Serial.println("-----");
    checking_lcd(cardUID);
  }
}
// LCD
void setup_lcd() {
  LCD.init();  
  LCD.backlight();
  configTime(UTC_OFFSET * 3600, 0, NTP_SERVER);
  default_lcd();
}

void default_lcd(){
  isInitialized = false;
  struct tm timeinfo;
  if (!getLocalTime(&timeinfo)) {
    if(!isInitialized){
      LCD.clear();
      LCD.setCursor(0, 0);
      LCD.println("P201 - A2");
      isInitialized = true;
    }
    return;
  }else{
    if(!isInitialized){
      LCD.clear();
      LCD.setCursor(0, 0);
      LCD.println(&timeinfo, "%d:%m:%Y   %Z");
      LCD.setCursor(0, 1);
      LCD.println(&timeinfo, "%H:%M  ");
      LCD.setCursor(7, 1);
      LCD.println("P201 - A2");
      isInitialized = true;
    }
  }
}

void checking_lcd(String s) {
  isInitialized = false;
  if(!isInitialized){
    LCD.clear();
    LCD.setCursor(0, 0);
    LCD.println("Code: ");
    LCD.setCursor(6, 0);
    LCD.println(s);
    LCD.setCursor(0, 1);
    LCD.print("Checking!         ");
    isInitialized = true;
  }
}
///
void mqtt_Button() {

  last_button1_state = button1_state;      // save the last state
  button1_state = digitalRead(BUTTON_PIN_1);
  delay(10);
  if (last_button1_state == HIGH && button1_state == LOW) {
    led1State = !led1State;
    digitalWrite(LED_PIN_1, led1State);
    client.publish(button_topic_1, String(led1State).c_str());
  }

  last_button2_state = button2_state;      // save the last state
  button2_state = digitalRead(BUTTON_PIN_2);
  delay(10);
  if (last_button2_state == HIGH && button2_state == LOW) {
    led2State = !led2State;
    digitalWrite(LED_PIN_2, led2State);
    client.publish(button_topic_2, String(led2State).c_str());
  }
  delay(20); 
}

void subscribeRfid(char* topic, String message) {
  Serial.println(String(topic));
  if (String(topic) == "pubrfid") {
    isInitialized = false;
    if(message =="0"){
      if(!isInitialized){
        LCD.clear();
        LCD.setCursor(0, 0);
        LCD.println("No class schedule");
        isInitialized = true;
        digitalWrite(LED_PIN_ERROR, HIGH);
        delay(6000); 
      }
      digitalWrite(LED_PIN_ERROR, LOW);
      Serial.println("Không đúng lịch học");
    }else if(message =="1"){
      if(!isInitialized){
        LCD.clear();
        LCD.setCursor(0, 0);
        LCD.println("Wrong face");
        digitalWrite(LED_PIN_ERROR, HIGH);
        isInitialized = true;
        delay(6000); 
      }
      digitalWrite(LED_PIN_ERROR, LOW);
      Serial.println("Không đúng khuôn mặt"); 
    }else if(message == "2"){
      if(!isInitialized){
        LCD.clear();
        LCD.setCursor(0, 0);
        LCD.println("You have taken");
        LCD.setCursor(0, 1);
        LCD.println("Attendance");
        isInitialized = true;
        digitalWrite(LED_PIN_SUCCESS, HIGH);
        delay(6000); 
      }
      digitalWrite(LED_PIN_SUCCESS, LOW);
      Serial.println("Bạn đã điểm danh");
    }else if(message == "3"){
      if(!isInitialized){
        LCD.clear();
        LCD.setCursor(0, 0);
        LCD.println("Successful");
        LCD.setCursor(0, 1);
        LCD.println("Attendance");
        isInitialized = true;
        digitalWrite(LED_PIN_SUCCESS, HIGH);
        delay(6000); 
      }
      digitalWrite(LED_PIN_SUCCESS, LOW);
      Serial.println("Thành công");
    }else{
      if(!isInitialized){
        LCD.clear();
        LCD.setCursor(0, 0);
        LCD.println("Error Attendance");
        isInitialized = true;
        digitalWrite(LED_PIN_ERROR, HIGH);
        delay(6000); 
      }
      digitalWrite(LED_PIN_ERROR, LOW);
      Serial.println("Error Attendance");
    }
    default_lcd();
    checkRfid = true;
  }
}

void subscribeLightBuld(char* topic, String message) {
  if (String(topic) == lightbulb1_topic) {
    toggleLED(LED_PIN_1, led1State, button_topic_1);
  } else if (String(topic) == lightbulb2_topic) {
    toggleLED(LED_PIN_2, led2State, button_topic_2);
  }
}

void toggleLED(int pin, bool &state, String topic) {
  state = !state;
  String payload = state ? "1" : "0";
  Serial.println("Bóng đèn: " + payload);
  digitalWrite(pin, state);
  client.publish(topic.c_str(), payload.c_str());
}
