# Quick Cash

Quick Cash is a modern Point of Sale (POS) cashier application designed for Android phones and tablets with Bluetooth receipt printing capabilities. This Malaysian-localized version provides comprehensive business management features for retail operations.

**Aplikasi Kasir / POS untuk handphone android / tablet dengan print bluetooth - Versi Malaysia**

**Credits:** This project is based on the original [KasirKu](https://github.com/trionoputra/KasirKu) by [trionoputra](https://github.com/trionoputra).

<img src="img1.png" width="400" alt="Quick Cash Login"></img>
<img src="img2.png" width="400" alt="Quick Cash Dashboard"></img>

<br/>

<img src="img3.png" width="400" alt="Quick Cash Products"></img>
<img src="img4.png" width="400" alt="Quick Cash Orders"></img>
<br/>
<img src="img5.png" width="400" alt="Quick Cash Reports"></img>
<img src="img6.jpg" width="400" alt="Quick Cash Settings"></img>

## Features

### Core Functionality
- **User Authentication** - Secure login system with user management
- **Master Data Management** - Product categories, items, and inventory control
- **Quick Order System** - Fast and intuitive ordering interface
- **Bluetooth Receipt Printing** - Wireless printing with proper permission handling
- **Comprehensive Settings** - Extensive configuration options
- **Malaysian E-Invoice Integration** - Built-in support for Malaysian tax requirements

### Enhanced Settings System
- **Printer Configuration** - USB, Network & Bluetooth printer setup
- **User Management** - Multi-user access control and permissions
- **License Activation** - Software licensing and activation status
- **Malaysian E-Invoice Setup** - Tax compliance and invoice generation
- **Kitchen Printer Setup** - Separate kitchen order printing
- **System Configuration** - App preferences and business settings

### Technical Improvements
- **AndroidX Migration** - Modern Android development framework
- **Runtime Permissions** - Proper Android 12+ permission handling
- **Bluetooth Security** - Enhanced Bluetooth connectivity with permission checks
- **Fragment Animations** - Smooth UI transitions and animations
- **Error Handling** - Comprehensive crash prevention and error management

## Technical Specifications

### Android Requirements
- **Minimum SDK:** Android 5.0 (API Level 21)
- **Target SDK:** Android 14 (API Level 34)
- **Architecture:** AndroidX framework with modern components
- **Language:** Java with Android SDK

### Dependencies
- **AndroidX Libraries** - Core, Fragment, AppCompat
- **Bluetooth SDK** - Custom Bluetooth service integration
- **Animation Framework** - YoYo animations with AndroidX Animator
- **Database** - SQLite with custom ORM implementation

### Permissions
- `BLUETOOTH_SCAN` - Required for discovering Bluetooth devices (Android 12+)
- `BLUETOOTH_CONNECT` - Required for connecting to Bluetooth printers (Android 12+)
- `BLUETOOTH_ADVERTISE` - Required for Bluetooth advertising features
- `READ_MEDIA_IMAGES` - Access to device images for product photos
- `READ_MEDIA_VIDEO` - Access to device videos for promotional content
- `READ_MEDIA_AUDIO` - Access to device audio files

## Installation & Setup

### Prerequisites
1. Android Studio Arctic Fox or later
2. Java JDK 8 or higher
3. Android SDK with API Level 34
4. Bluetooth-enabled Android device for testing

### Build Instructions
```bash
# Clone the repository
git clone <repository-url>
cd "Quick Cash"

# Build debug APK
./gradlew assembleDebug

# Install on connected device
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Configuration
1. **First Launch:** Configure basic settings through the settings menu
2. **Printer Setup:** Connect Bluetooth printer via Settings > Printer Configuration
3. **User Management:** Set up additional users via Settings > User Management
4. **Malaysian E-Invoice:** Configure tax settings via Settings > Malaysian E-Invoice Setup

## Project Structure

```
app/src/main/java/com/extropos/java/
├── activities/          # Main application activities
├── fragments/           # UI fragments and dialogs
├── adapters/           # RecyclerView and ListView adapters
├── models/             # Data models and entities
├── database/           # SQLite database management
├── utils/              # Utility classes and constants
└── services/           # Background services and Bluetooth handling
```

## Recent Updates

### Version 2.0 (Latest)
- ✅ **Package Migration** - Renamed from `com.chipo.cashier` to `com.extropos.java`
- ✅ **AndroidX Migration** - Updated to modern Android framework
- ✅ **Bluetooth Fixes** - Resolved Android 12+ permission issues
- ✅ **Settings Expansion** - Added 8 comprehensive setting categories
- ✅ **UI Improvements** - Fixed fragment animations and theme compatibility
- ✅ **Error Resolution** - Reduced lint errors from 116 to under 40
- ✅ **Crash Prevention** - Fixed multiple runtime crash scenarios

### Bug Fixes
- Fixed SecurityException in Bluetooth operations
- Resolved fragment animation crashes
- Fixed theme compatibility issues in settings
- Improved permission handling for modern Android versions
- Enhanced error handling and user feedback

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/new-feature`)
3. Commit your changes (`git commit -am 'Add new feature'`)
4. Push to the branch (`git push origin feature/new-feature`)
5. Create a Pull Request

## License

This project maintains the same licensing terms as the original KasirKu project. Please refer to the original repository for license details.

## Support

For issues, feature requests, or questions:
1. Check existing issues in the repository
2. Create a new issue with detailed description
3. Include device information and Android version for bug reports

## Acknowledgments

- **Original Creator:** [trionoputra](https://github.com/trionoputra) for the foundational KasirKu project
- **AndroidX Migration:** Modern Android development practices implementation
- **Malaysian Localization:** Enhanced features for Malaysian business requirements
- **Community Contributors:** All developers who contributed to improvements and bug fixes

## Development & Testing

- Run unit and instrumentation tests locally on a connected device or emulator.
- To run the new Move/Undo instrumentation test (requires a connected Android device or emulator):

```bash
# Build app and test APKs
./gradlew assembleDebug assembleDebugAndroidTest

# Install APKs to the connected device
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb install -r app/build/outputs/apk/androidTest/debug/app-debug-androidTest.apk

# Run the instrumentation test directly
adb shell am instrument -w com.extropos.java.test/androidx.test.runner.AndroidJUnitRunner
```

- The test created for verifying table move and undo flow is located at `app/src/androidTest/java/com/extropos/java/tests/MoveHistoryInstrumentedTest.java`.

- Notes:
	- Ensure a device or emulator is connected (`adb devices`).
	- The test performs DB inserts/updates and cleans up test data after running.
	- If `connectedAndroidTest` fails in CI due to UTP/protobuf classpath issues, run the test via `adb shell am instrument` as shown above.
