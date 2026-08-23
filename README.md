# WAImportant ??

> **Never miss a message that truly matters.**

---

## The Problem

WhatsApp is one of the most used messaging apps in the world — but it treats every message equally.
A "okay ??" from a random group and an urgent message from your doctor land in the same inbox, with the same notification, demanding the same attention.

Most of us are part of **dozens of groups** — family, friends, work, communities — and our notification shade is a flood. Genuinely important messages get buried. We scroll back. We miss things. We reply too late.

On top of this, **OEM Android devices** (especially from brands like vivo, iQOO, Xiaomi, and OnePlus) aggressively kill background apps and services to save battery. If a traditional listener app isn't on their whitelist, it simply stops working silently — and you never know what you missed.

---

## Our Ideology

**WAImportant** is built on a simple belief:

> Your attention is finite. Your phone should protect it.

We don't want to replace WhatsApp. We don't want to read your messages for you.
We want to act like a **smart filter layer** — sitting quietly in the background, watching what comes in, and surfacing only what genuinely needs your attention.

The intelligence is local. No messages ever leave your device. No cloud. No servers. No accounts.

---

## What We Are Solving

| Problem | Our Approach |
|--------|--------------|
| Too many notifications, hard to find urgent ones | Score every incoming WhatsApp message using configurable rules |
| Important messages buried in group chats | Detect group vs. direct messages and weight them differently |
| OEM devices killing background listener services | Foreground service with persistent notification keeps the listener alive |
| Privacy concerns with message-reading apps | 100% on-device processing — no data leaves your phone |
| One-size-fits-all notification systems | User-defined rules: keywords, senders, time-of-day sensitivity |

---

## How It Works

```
WhatsApp Notification
        ?
WaNotificationListener (NotificationListenerService)
        ?
   Message Parsing
   (sender, text, group vs DM, timestamp)
        ?
   Scoring Engine (com.waimportant.rules)
   ? keyword matching
   ? sender priority
   ? group vs direct weight
        ?
   ScoredMessage
   ? isImportant: true/false
   ? reason: why it was flagged
        ?
   [Future] Heads-up alert / summary notification
```

---

## Project Structure

```
com.waimportant
+-- data/
¦   +-- WhatsAppMessage.kt     # Core message model
¦   +-- ScoredMessage.kt       # Scored output model
+-- rules/                     # Scoring logic (coming soon)
+-- ui/                        # UI screens (coming soon)
+-- WaNotificationListener.kt  # Notification listener + foreground service
```

---

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose (ready, not yet wired)
- **Database:** Room + KSP (ready, not yet wired)
- **Async:** Kotlin Coroutines
- **Min SDK:** 26 (Android 8.0+)
- **Target SDK:** 34 (Android 14)

---

## Current Status

- [x] Project scaffold with Jetpack Compose + Room + KSP
- [x] `WhatsAppMessage` and `ScoredMessage` data models
- [x] `WaNotificationListener` — captures all incoming WhatsApp notifications
- [x] Parses both grouped (`EXTRA_MESSAGES`) and single (`EXTRA_TITLE/TEXT`) notifications
- [x] Foreground service wrapper to survive aggressive OEM battery management
- [x] Logcat output (`WA_CAPTURE` tag) for real-time message inspection
- [ ] Scoring / rules engine
- [ ] Room database persistence
- [ ] UI to view flagged messages
- [ ] vivo/iQOO autostart + whitelist deep-link guidance

---

## Privacy First

WAImportant **never**:
- Uploads your messages to any server
- Requires a login or account
- Shares data with third parties

It reads notification content the same way your smartwatch or Bluetooth headset does — using Android's standard `NotificationListenerService` API.

---

## Contributors

- [@Nikitha-04](https://github.com/Nikitha-04)

---

*Built with ?? for people who are tired of missing the messages that matter.*
