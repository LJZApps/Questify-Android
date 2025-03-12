# Questify

## Transform Your Daily Tasks into Epic Adventures

Questify is a gamified task management app that turns your everyday tasks into exciting quests. Organize your to-do list as epic main quests and smaller side quests, earn points, collect trophies, and enjoy a more engaging and motivating way to manage your daily responsibilities.

![Questify Logo](app/src/main/ic_launcher-playstore.png)

## Why Questify?

### Gamified Task Management
- **Main and Side Quests**: Organize your tasks as epic main quests and smaller side quests
- **Difficulty Levels**: Set difficulty levels for your quests to earn appropriate rewards
- **Points System**: Earn points by completing quests and exchange them for rewards
- **Trophies**: Collect trophies for achievements and important life events

### Motivation Through Gamification
- **Progress Tracking**: Visualize your progress and achievements
- **Reward System**: Get rewarded for completing tasks, making productivity more enjoyable
- **Competitive Elements**: Compare stats with friends and motivate each other

### Smart Features
- **AI-Supported Task Evaluation**: Our intelligent AI ensures fair rewards for your main quests
- **Discoverable World Map**: Unlock a mysterious map as you move around and earn additional points
- **Health Integration**: Sync health data and earn points through physical activity

## Setup Instructions

### Prerequisites
- Android Studio Arctic Fox (2020.3.1) or newer
- JDK 19
- Kotlin 1.9.0 or newer
- Android SDK 30+

### Getting Started
1. **Clone the repository**
   ```
   git clone https://github.com/yourusername/Questify.git
   cd Questify
   ```

2. **Configure local.properties**
   Create a `local.properties` file in the project root with the following properties:
   ```
   sdk.dir=/path/to/your/android/sdk
   DEBUG_BASE_URL="your_debug_base_url"
   RELEASE_BASE_URL="your_release_base_url"
   SENTRY_AUTH_TOKEN="your_sentry_auth_token"
   
   # For release signing (optional for development)
   STORE_FILE=/path/to/your/keystore.jks
   STORE_PASSWORD=your_store_password
   KEY_ALIAS=your_key_alias
   KEY_PASSWORD=your_key_password
   ```

3. **Sync Gradle**
   Open the project in Android Studio and sync Gradle.

4. **Build and Run**
   Build the project and run it on an emulator or physical device.

## Features

- **Haupt- und Nebenquests**: Organize your daily tasks as epic main quests and smaller side quests. Complete them to earn valuable points!
- **Punkteshop**: Exchange your hard-earned points for cool rewards.
- **KI-Gestützte Aufgabenbewertung**: Our intelligent AI checks your main quests and ensures fair rewards.
- **Entdeckbare Weltkarte**: Discover a mysterious map that unlocks piece by piece as you move around and earn additional points!
- **Lebensbegleitende Trophäen**: Receive trophies for important life events like marriage, your first job, or moving into your own apartment.
- **Freundes-System**: Connect with friends, compare your stats, and motivate each other.
- **Gesundheits- und Lebensstatistiken**: Sync your health data, earn points through movement, and keep track of your life goals.
- **Plattformübergreifende Verfügbarkeit**: Questify runs natively on every platform, whether iPhone, Android, or other devices.
- **Anpassbare Benutzeroberfläche**: Choose from different themes and interfaces that match your style.
- **EpicQuest Battleground**: Enter the arena and become the ultimate champion! With the PvP mini-game in Questify, you can prove your skills against other users.

## Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## License

This project is licensed under the terms found in the [LICENSE](LICENSE) file.