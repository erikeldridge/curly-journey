# curly-journey

## Goals

Implement the minimum required to:

* use Digits Friend Finder on Android
* dump debug info, ie log everything
* commit project to a public repo, eg generalize keys and secrets


## Set up

1. Install Fabric plugin for Android Studio
1. Create a new app
1. Use the plugin to install Digits
1. Note the TWITTER_KEY and TWITTER_SECRET injected into the app
1. Define secret.properties file in project root
1. Copy TWITTER_KEY and TWITTER_SECRET into secret.properties as 
    twitter_key=...
    twitter_secret=...
1. Sync gradle to generate R.strings.twitter_key, etc
1. Build and install app on a device with an address book
1. Tap "use my phone number" to log into app
1. Tap "upload" upload contacts
1. Tap "get" to get a list of your contacts who are using this app


## References

* [Fabric's Android Studio plugin instructions](https://fabric.io/downloads/android-studio)
* [Digits' _Sign In with Phone Number_ Android tutorial](https://fabric.io/kits/android/digits/features)
* [Digits _Find Your Friends_ documentation](https://docs.fabric.io/android/digits/find-friends.html)
* [Digits' _Find Your Friends_ iOS tutorial](https://fabric.io/kits/ios/digits/features)
