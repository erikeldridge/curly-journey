# Digits Playground

## Goals

Implement the minimum required to:

* Use Digits Sign In and Friend Finder on Android
* Log as much as possible
* Commit project to a public repo, eg generalize config


## Non-goals

* Build against the digits-android repo. Use the [digits-android's sample app](https://github.com/twitter/digits-android/tree/master/samples/app) for that


## Set up

1. Clone this repo
1. Import project into Android Studio
1. Use [Fabric's install documentation for Android Digits](https://fabric.io/kits/android/digits/install) to generate a key/secret pair, if you don't already have one
1. Note the TWITTER_KEY and TWITTER_SECRET injected into the sample code
1. Create an app/fabric.properties file
1. Copy TWITTER_KEY and TWITTER_SECRET into fabric.properties as:

        TWITTER_KEY=...
        TWITTER_SECRET=...

1. Sync gradle to generate BuildConfig
1. Build and install app on a device with an address book
1. Tap "Use my phone number" to log into app
1. Tap "Upload contacts" to upload contacts
1. Tap "Get contact matches" to get a list of your contacts who are using this app
1. Tap "Clear session" to log out
1. Filter logs by "Digits Playground" tag


## References

* [Fabric's Digits for Android documentation](https://fabric.io/kits/android/digits)
* [Digits' _Sign In with Phone Number_ Android tutorial](https://fabric.io/kits/android/digits/features)
* [Digits' _Find Your Friends_ documentation](https://docs.fabric.io/android/digits/find-friends.html)
* [Digits' _Find Your Friends_ iOS tutorial](https://fabric.io/kits/ios/digits/features)
* [Twitter's digits-android project](https://github.com/twitter/digits-android)
