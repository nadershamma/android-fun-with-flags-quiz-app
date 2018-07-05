# Fun with Flags
This is a simple quiz style guessing game. The player is presented with a flag and asked to guess
which country the flag is from. The player can choose flags [(1)](#1) from a number of regions including:

- Africa
- Asia
- Europe
- North America
- Oceania
- South America

<p><img src="https://raw.githubusercontent.com/nadershamma/android-fun-with-flags-quiz-app/master/misc/fun-with-flags-phone-demo.gif" alt="Screenshot of app on a phone" width="300px" heigh="auto" /></p>

They then choose the difficulty of the game where they will presented with 2, 4, 6 or 8 options to guess from.

<p><img src="https://raw.githubusercontent.com/nadershamma/android-fun-with-flags-quiz-app/master/misc/screenshots/Screenshot_1530800224.png" alt="Screenshot of app on a tablet" width="500px" height="auto" /></p>



The app was inspired by a project presented in the book [Android 6 for Programmers](http://www.deitel.com/Books/Android/Android6forProgrammersAnAppDrivenApproach/tabid/3671/Default.aspx). However there are a number of key differences in the implementation.

Firstly. This app utilises the [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) [Architecture Component](https://developer.android.com/topic/libraries/architecture/) to manage how data is shared between fragments and activities. 

Secondly the App utilises the [DialogFragment](https://developer.android.com/reference/android/support/v4/app/DialogFragment) to present the players score via an [Alert Dialog](https://developer.android.com/reference/android/app/AlertDialog) which in the book is implimented as an annonymous inner class within the MainFragment Activity. Doing so sends (the current) Android Studio into a small panic. Therefore the Dialog feature is implimented as a stand alone fragment and called using the Fragment Manager. 

Other improvements include creating stand alone event handlers rather than cramming everything into the activity class. 

<spen name="#1">1)</span> flags sourced from [https://www.free-country-flags.com/](https://www.free-country-flags.com/)
