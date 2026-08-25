# 📱 WA Important - Smart WhatsApp Message Manager

> **Never miss what truly matters in the noise of endless notifications.** 
> *Built for productivity and peace of mind.*

## 🚀 The Problem
In today's fast-paced world, our WhatsApp and WhatsApp Business accounts are flooded with hundreds of messages daily. From casual chats and group notifications to promotional spam, the sheer volume of noise makes it incredibly easy to miss the messages that actually matter—like an urgent text from a boss, a request from family, or critical keywords like "payment" or "urgent".

## 💡 The Solution
**WA Important** is a lightweight, privacy-first Android application that acts as a smart filter for your WhatsApp notifications. By securely listening to incoming messages, it categorizes them in real-time based on customizable, user-defined rules. 

Instead of opening WhatsApp to a sea of unread chats, open **WA Important** to see exactly what needs your immediate attention.

## ✨ Key Features
- **🔔 Live Message Capture**: Instantly reads and logs incoming messages from both **WhatsApp** and **WhatsApp Business**.
- **⭐ Smart Importance Filtering**: Create powerful filter rules combining **Sender Name** and **Keywords**. 
  - *Example 1:* Flag all messages from "Mom".
  - *Example 2:* Flag any message containing the word "urgent".
  - *Example 3:* Flag messages from "Boss" containing "meeting".
- **🗂️ Dual-Tab UI**: A clean, intuitive Jetpack Compose interface allowing seamless switching between "All Messages" and "⭐ Important" messages.
- **💾 Persistent Rules**: Your customized filtering rules are securely saved on the device and survive app restarts.
- **🔒 Privacy First**: 100% offline and local. No messages or personal data are ever sent to the cloud or third-party servers.

## 🛠️ Technical Implementation
- **Language**: Kotlin 
- **UI Framework**: Jetpack Compose (Material Design 3)
- **Architecture**: 
  - `NotificationListenerService` for seamless, low-overhead background interception of system notifications.
  - Reactive UI using `StateFlow` and Coroutines for instant screen updates without manual refreshing.
  - Efficient file-based local storage (`JSONObject` / `JSONArray`) for rule persistence.
- **Compatibility**: Android 8.0 (API 26) to Android 14 (API 34).

## 🚀 Getting Started
1. Install the APK on your Android device.
2. Grant **Notification Access** when prompted by the app.
3. Tap the **⚙ (Settings) icon** to configure your Important Filter rules.
4. Sit back and let WA Important curate your notifications!

---
*Built with ❤️ to bring focus back to your digital life.*
