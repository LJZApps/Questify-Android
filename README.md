<div align="center">
  <img src="app/src/main/res/mipmap-xxxhdpi/ic_launcher_round.png" alt="Questify Logo" width="150" height="150">
  <h1 align="center">Questify</h1>
  <p align="center">
    <b>Transform Your Daily Tasks into Epic Adventures</b>
    <br />
    <a href="https://github.com/yourusername/Questify/issues/new?assignees=&labels=bug&template=bug_report.md&title=">Report a Bug</a>
    ·
    <a href="https://github.com/yourusername/Questify/issues/new?assignees=&labels=enhancement&template=feature_request.md&title=">Request a Feature</a>
  </p>

  <p align="center">
    <a href="https://github.com/yourusername/Questify/blob/main/LICENSE">
      <img src="https://img.shields.io/github/license/yourusername/Questify?style=for-the-badge" alt="License">
    </a>
    <a href="https://github.com/yourusername/Questify/stargazers">
      <img src="https://img.shields.io/github/stars/yourusername/Questify?style=for-the-badge" alt="Stargazers">
    </a>
    <a href="https://github.com/yourusername/Questify/network/members">
      <img src="https://img.shields.io/github/forks/yourusername/Questify?style=for-the-badge" alt="Forks">
    </a>
  </p>
</div>

---

## About Questify

Questify is a gamified task management application designed to turn your everyday to-do list into an exciting adventure. By organizing your tasks as epic main quests and smaller side quests, you can earn points, collect trophies, and find motivation in a more engaging way.

### Key Features

- **Gamified Task Management**: Structure your tasks as main and side quests with varying difficulty levels.
- **Reward System**: Earn points for completing quests and exchange them for exciting rewards.
- **Trophy Collection**: Commemorate achievements and life milestones with unique trophies.
- **AI-Powered Task Evaluation**: An intelligent AI ensures fair and balanced rewards for your quests.
- **Interactive World Map**: Discover a mysterious map that unfolds as you explore your surroundings.
- **Health Integration**: Sync your health data and earn points for physical activities.
- **Cross-Platform**: Enjoy a seamless experience on both Android and iOS devices.

---

## Getting Started

Follow these instructions to get a local copy of Questify up and running on your machine.

### Prerequisites

- **Android Studio**: Arctic Fox (2020.3.1) or newer
- **JDK**: Version 19 or newer
- **Kotlin**: Version 1.9.0 or newer
- **Android SDK**: Version 30 or higher

### Installation

1. **Clone the Repository**
   ```bash
   git clone https://github.com/yourusername/Questify.git
   cd Questify
   ```

2. **Configure `local.properties`**
   Create a `local.properties` file in the project's root directory and add the following, replacing the placeholder values with your actual configuration:
   ```properties
   sdk.dir=/path/to/your/android/sdk
   DEBUG_BASE_URL="your_debug_base_url"
   RELEASE_BASE_URL="your_release_base_url"
   SENTRY_AUTH_TOKEN="your_sentry_auth_token"
   ```

3. **Set Up Release Signing (Optional)**
   For release builds, add the following to your `local.properties` file:
   ```properties
   STORE_FILE=/path/to/your/keystore.jks
   STORE_PASSWORD=your_store_password
   KEY_ALIAS=your_key_alias
   KEY_PASSWORD=your_key_password
   ```

4. **Sync Gradle and Run**
   Open the project in Android Studio, allow Gradle to sync, and then build and run the application on an emulator or a physical device.

---

## How to Contribute

We welcome all contributions! If you'd like to help improve Questify, please follow these steps:

1. **Fork the Project**
2. **Create a New Branch** (`git checkout -b feature/AmazingFeature`)
3. **Commit Your Changes** (`git commit -m 'Add some AmazingFeature'`)
4. **Push to the Branch** (`git push origin feature/AmazingFeature`)
5. **Open a Pull Request**

For more details, please see our [contributing guidelines](CONTRIBUTING.md).

---

## License

This project is licensed under the terms of the MIT License. See the [LICENSE](LICENSE) file for more details.

---

## Contact

Your Name - [your.email@example.com](mailto:your.email@example.com)

Project Link: [https://github.com/yourusername/Questify](https://github.com/yourusername/Questify)
