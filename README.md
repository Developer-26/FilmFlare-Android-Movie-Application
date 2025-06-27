
🎬 FilmFlare – Android Movie Discovery App
FilmFlare is a sleek and intuitive Android application that lets users browse movies by category, view in-depth details (descriptions, ratings, cast), and explore nearby cinemas within a 20km radius using Google Maps, Forget Password functionality. Packed with personalization 
features like dark mode and language toggling, FilmFlare delivers a tailored movie-discovery experience.

📱 Features
- 🎞️ Browse by Categories: Discover trending, upcoming, now playing, and top-rated movies.
- 📖 Detailed Movie Info: View plot summaries, ratings, genres, cast/crew, and more.
- 🗺️ Cinema Locator: Google Maps integration displays cinemas within 20km of the user's location.
- 🌙 Dark Mode: Seamlessly switch between light and dark themes.
- 🌐 Language Toggle: Switch app content language for a more personalized experience.
- ⚡ Responsive UI: Smooth performance and material design principles for a modern look and feel.

🛠️ Tech Stack
| Layer | Technology | 
| UI | XML, Material Design | 
| Language | Kotlin | 
| Architecture | MVVM | 
| Networking | Retrofit, Gson | 
| Location & Maps | Google Maps SDK, FusedLocationProvider | 
| Dependency Injection | Hilt | 
| Other | Glide, ViewModel, LiveData | 


ReadMe FilmFlare Application 
THASLYN GOVENDER 
Linkedin Profile: https://www.linkedin.com/in/thaslyn-govender-a4a408247 

Contents 
FilmFlare - Movie App  
DEFAULT LOGIN DETAILS  
PROJECT / APP NAME 
HARDWARE SPECS  
INSTALLATION INSTRUCTIONS  
Purpose  
Features  
ADDITIONAL FEAUTURES  
Technology Stack  
App Screens and Functionality  
API Integration  
Functional Requirements  
Non-Functional Requirements  
Frequently Asked Questions (FAQ) 
DEVELOPER INFO 
Acknowledgments
Screenshots
References 


FilmFlare - Movie App 
FilmFlare is a movie discovery and management app designed to provide users with a 
seamless experience to explore popular movies, track their favourite titles, and customize 
their interface settings. The app integrates with a RESTful API to fetch movie data and allows 
users to log in using Single Sign-On (SSO) as well as biometric login. Users can also manage 
their movie watchlist, explore films by genre, and customize their experience by enabling a 
dark mode across the app.

DEFAULT LOGIN DETAILS 
Email: admin@admin.com 
Password: admin1


PROJECT / APP NAME 
FilmFlare 
File name – filmflare 
Android Studio 
Version 1.1.0 


HARDWARE SPECS 
Recommended Hardware Specifications: 
Processor: Quad-core CPU (2.0 GHz or higher) 
RAM: 2 GB or higher 
Storage: 50 MB of free space for app installation and data storage 
Display: 720p resolution (1280x720) or higher 
Operating System: Android 7.0 (Nougat) or higher 
Minimum Hardware Specifications: 
Processor: Dual-core CPU (1.2 GHz or higher) 
RAM: 1 GB 
Storage: 20 MB of free space for app installation and data storage 
Display: 480p resolution (854x480) 
Operating System: Android 5.0 (Lollipop) or higher 
Additional Requirements: 
Internet Connection: Optional but recommended for features that may require online 
access, such as data synchronization with a cloud service. 
Battery: No specific requirements, but a device with good battery life will ensure extended 
usability. 

INSTALLATION INSTRUCTIONS 
1. Clone the Repository 
2. Open the Project in Android Studio 
Open Android Studio. 
Select File > Open. 
Navigate to the cloned repository directory and select it. 
Click OK to open the project. 
3. Set Up Firebase 
Create a Firebase Project: 
Go to the Firebase Console. 
Click on Add project and follow the setup wizard to create a new Firebase project.Connect the app to firebase with an Authentication, Storage, and Realtime Database. 
4. Sync Project with Gradle Files 
Update the gradle files. 
After updating the build.gradle files, click on the Sync Now button that appears at the top 
right of Android Studio to sync your project with the Gradle files. 
5. Run the App 
Connect your Android device to your development machine via USB, or set up an Android 
emulator. 
Click on the Run button in Android Studio or press Shift + F10 to build and run the app. 
The app should launch on your connected device or emulator. 
Alternatively: 
Download the included apk and install on an android device in order to run the app


Purpose 
FilmFlare simplifies the process of discovering, organizing, and accessing movie information 
while offering an enjoyable and user-friendly experience. The app empowers users to: 
• Log in or sign up using Single Sign-On (SSO) and enjoy secure access with biometric 
sign-in. 
• Explore popular movies with detailed information, including title, description, genre, 
duration, cast, and rating. 
• Add movies to a personalized watchlist and manage them dynamically. 
• Browse movies by genre, customize their experience through settings, toggle dark 
mode, and log out conveniently. 
• Use the built-in Google Maps feature to find nearby cinemas within a 20 km radius, 
click on cinema icons to get directions, and search for cinemas via the Google search 
bar. 
• Switch between multiple languages (Zulu, Afrikaans, and English) to enhance 
accessibility. 
• Receive periodic push notifications to stay updated on new releases and features. 
• Access movie details even without an internet connection, thanks to offline 
capabilities. 
Built for scalability, performance, and ease of use, FilmFlare enables users to enjoy seamless 
movie management, making it the ultimate app for movie enthusiasts 


Features 
1. User Authentication: 
o Users can register or log in using Single Sign-On (SSO). 
o Users can log in with biometric authentication for secure, convenient access. 
o Option to reset passwords via a reset email link. 
2. Movie Browsing: 
o Users can view a list of popular movies with details such as: 
▪ Title 
▪ Description 
▪ Rating 
▪ Genre 
▪ Duration 
▪ Cast 
o Tapping on a movie provides a detailed view with comprehensive 
information. 
3. Watchlist: 
o Users can dynamically add or remove movies from their personal watchlist. 
o The watchlist is accessible in a dedicated section for easy management. 
4. Genre-Based Movie Filtering: 
o Movies are organized by genre, allowing users to discover titles based on 
their interests. 
o Users can add or remove movies to/from the watchlist within each genre 
section. 
5. Google Maps Integration: 
o Users can locate nearby cinemas within a 20 km radius of their current 
location. 
o Users can click on cinema icons for directions via Google Maps and search for 
cinemas directly using a Google search bar.
6. Language Support: 
o Users can switch between Zulu, Afrikaans, and English, making the app more 
accessible to a diverse audience. 
7. Dark Mode: 
o A dark mode setting offers a comfortable viewing experience in low-light 
environments. 
8. Settings Page: 
o Users can enable dark mode and log out of their account. 
o Language preferences are customizable in the settings. 
9. Password Reset: 
o Users can request a password reset email from the login page to recover their 
account if they forget their password. 
10. Data Persistence: 
• User watchlists and preferences, such as dark mode and language selection, are 
stored in Firebase, ensuring data is available upon login. 
11. API Creation and Integration: 
• Movies are fetched from a custom REST API hosted on jsonbin.io. 
• The API was developed using JSON to provide structured movie data, including movie 
details and descriptions. 
12. Responsive User Interface: 
• A clean, intuitive layout with a bottom navigation bar allows seamless access to key 
features such as Popular Movies, User Watchlist, Movie Genres, and Settings. 
13. Offline Features: 
• Users can view movie details even without an internet connection, allowing access to 
previously loaded content at any time. 
14. Push Notifications: 
• Users receive periodic push notifications to stay informed about new releases, app 
updates, and relevant content, enhancing user engagement.


ADDITIONAL FEAUTURES 
1. Dark Mode: 
o Purpose: Enhances user experience by offering a dark theme, reducing eye 
strain in low-light environments. 
o Functionality: 
▪ Users can toggle dark mode from the settings page to switch between 
light and dark themes. 
▪ When activated, the entire app adopts the dark theme, with cohesive 
adjustments to backgrounds, text colors, and UI elements. 
▪ The theme preference is stored and remains persistent across 
sessions, so users don’t need to reapply their choice each time they 
open the app. 
2. Google Maps Integration for Nearby Cinemas: 
o Purpose: Improves convenience by helping users find cinemas near their 
current location. 
o Functionality: 
▪ The app uses Google Maps to display nearby cinemas within a 20 km 
radius. 
▪ Users can click on a cinema icon to get directions and navigate directly 
through Google Maps. 
▪ A Google search bar allows users to search for cinemas, making it 
easier to locate specific theaters. 
3. Language Support: 
o Purpose: Enhances accessibility for a diverse audience by supporting multiple 
languages. 
o Functionality: 
▪ Users can switch the app’s language between Zulu, Afrikaans, and 
English. 
▪ The selected language preference is stored, so the app remains in the 
chosen language across sessions, providing a tailored experience. 
4. Offline Features: 
o Purpose: Provides users with access to movie details without requiring an 
internet connection. 
o Functionality: 
▪ Users can view previously loaded movie details even when offline, 
ensuring a seamless experience regardless of connectivity. 
5. Push Notifications: 
o Purpose: Keeps users informed and engaged with timely updates and 
reminders. 
o Functionality: 
▪ Users receive periodic push notifications about new movie releases, 
app updates, and relevant content, enhancing overall user 
engagement with the app.


Technology Stack 
• Programming Language: Kotlin 
• Framework: Android SDK 
• Database: Firebase Realtime Database 
• Authentication: Firebase Authentication with Single Sign-On (SSO) as well 
Email/Password 
• API: RESTful API for movie data 
• External Libraries: 
o Retrofit for API integration 
o Glide for image loading 
o Material Design for UI components 
• Version Control: GitHub 


App Screens and Functionality 
Login and Register Page: 
• Allows users to log in using SSO. 
• Offers biometric login for secure and convenient access. 
• Users can log in with email and password. 
• Provides an option for users to reset their password. 
• Upon successful login, users are directed to the Movie List page. 
Movie List Page: 
• Displays a list of popular movies fetched from the REST API. 
• Each movie entry shows the title, poster image, rating, and genre. 
• Clicking on a movie navigates the user to the Movie Detail page. 
Movie Detail Page: 
• Shows detailed information about the movie, including title, description, rating, 
genre, duration, and cast. 
• Users can add or remove the movie from their watchlist with a single tap. 
Watchlist Page: 
• Displays all movies the user has added to their watchlist. 
• Users can remove movies from their watchlist here. 
Genre-Based Browsing Page: 
• Allows users to browse movies by specific genres. 
• Users can add movies from each genre to their watchlist. 
Map and Cinema Search Page: 
• Allows users to find nearby cinemas within a 20 km radius of their current location. 
• Displays cinema locations on Google Maps with clickable icons for directions. 
• Includes a search bar that enables users to find specific cinemas. 
• Clicking on a cinema icon opens Google Maps for navigation to that cinema. 
Settings Page: 
• Provides options for users to enable or disable dark mode. 
• Offers a language selection feature, allowing users to switch between English, 
Afrikaans, and Zulu. 
• Includes a logout button for users to exit their account.


API Integration 
FilmFlare connects to a custom RESTful API hosted online. This API is responsible for fetching 
movie data, including titles, descriptions, genres, ratings, and cast details. The app leverages 
Retrofit to handle HTTP requests to the API, ensuring efficient data fetching. 
FilmFlare API BIN: 
https://api.jsonbin.io/v3/b/66f804f7e41b4d34e4397fbc 
Firebase Authentication & Realtime Database 
Firebase authentication database where google and Email/Password accounts are saved 
Realtime database where watch listed movies 


Functional Requirements 
1. User Authentication 
o Login: The app must allow users to log in using Single Sign-On (SSO) via 
supported providers (e.g., Google) and biometric login. 
o Sign-Up: Users must be able to register for an account using SSO or manually 
using email and password. 
o Password Reset: The app must provide a password reset feature where users 
can request a password recovery email by entering their registered email 
address. 
o  
2. Movie Display 
o Popular Movies List: The app must display a list of popular movies upon 
successful login. 
o Movie Details: The app must allow users to click on a movie to view its 
details, including: 
▪ Title 
▪ Description 
▪ Rating 
▪ Genre 
▪ Duration 
▪ Cast 
o Movie Posters: Each movie must have a corresponding poster image 
displayed in the list. 
3. Watchlist Management 
o Add to Watchlist: Users must be able to add a movie to their watchlist from 
the movie details or genre section. 
o Remove from Watchlist: Users must be able to remove a movie from their 
watchlist. 
o View Watchlist: The app must provide a section where users can view all the 
movies they have added to their watchlist. 
4. Genre-Based Browsing 
o Browse by Genre: Users must be able to browse movies by specific genres 
(e.g., Action, Comedy, Drama, etc.). 
o Watchlist in Genre Section: Users must be able to add or remove movies 
to/from their watchlist directly within the genre-specific section. 
5. Google Maps Integration for Nearby Cinemas 
o Cinema Location Display: The app must display nearby cinemas on a map, 
showing cinemas within a 20 km radius of the user’s current location. 
o Cinema Search: The app must provide a search bar that allows users to search 
for specific cinemas. 
o Directions: Users must be able to click on a cinema icon to open Google Maps 
and get directions to that location. 
6. Settings Management 
o Dark Mode: Users must be able to toggle dark mode system-wide across the 
app from the settings page. 
o Language Selection: Users must be able to switch between languages (Zulu, 
Afrikaans, and English) from the settings page. 
o Logout: Users must be able to log out of their account from the settings page. 
7. Error Handling and Validation 
o Input Validation: The app must handle invalid inputs (e.g., incorrect email or 
password during login) and display appropriate error messages without 
crashing. 
o Network Error Handling: The app must handle cases where the API is 
unreachable or data cannot be fetched, displaying appropriate error 
messages or fallback UI. 
8. API Integration 
o Fetch Movies: The app must connect to a RESTful API to fetch movie data 
(titles, descriptions, ratings, genres, durations, cast, etc.) and display it in the 
app. 
o Watchlist Syncing: The app must synchronize the user's watchlist with the API, 
ensuring that any additions or removals are updated in the backend. 
9. Data Persistence 
o Local Data Caching: The app should cache some movie data locally to improve 
performance when browsing movies, especially for watchlist management 
and previously loaded data. 
o Preference Storage: The app must store user preferences (dark mode, 
language selection) locally to ensure they persist across sessions. 
10. Push Notifications 
• User Notifications: The app must send periodic push notifications to users about 
new movie releases, updates, and reminders, enhancing user engagement with 
the app.



Non-Functional Requirements  
Performance Requirements 
• Response Time: The app should load movie data (from the RESTful API) and display it 
within 2-3 seconds after a user logs in or interacts with the app. 
• API Latency: The API calls should have a maximum latency of 500ms for all major 
actions like fetching popular movies, adding/removing from watchlists, and retrieving 
genres. 
• Map Loading Time: The Google Maps feature should load and display nearby 
cinemas within 2-3 seconds after accessing the map page to ensure a smooth user 
experience. 
Usability Requirements 
• User Interface (UI) Design: The app should have an intuitive, clean, and user-friendly 
interface, ensuring users can easily navigate between screens (e.g., Login, Popular 
Movies, Watchlist, Settings). 
• Error Messaging: Errors such as invalid login credentials or network issues should be 
displayed with clear and helpful messages, guiding users on how to resolve the issue. 
• Dark Mode Support: Dark mode should be available system-wide and seamlessly 
toggle without affecting performance or readability. 
• Language Support: The app should allow users to select between English, Afrikaans, 
and Zulu, with language preferences stored for consistency across sessions. 
Security Requirements 
• Data Encryption: All sensitive data such as user credentials (email, passwords) and 
API tokens must be encrypted both in transit (using HTTPS) and at rest. 
• Authentication & Authorization: Strong authentication (e.g., SSO with Google, 
Facebook) must be enforced, and only authenticated users should have access to 
view movies and manage their watchlists. 
• Password Security: User passwords should be securely hashed (e.g., using bcrypt or 
similar algorithms) and stored securely to protect against unauthorized access. 
• Location Data Privacy: User location data used for the cinema map feature should be 
handled securely and only used temporarily during the session to avoid storing or 
misusing personal location information.



Frequently Asked Questions (FAQ) 
1. What is FilmFlare? 
Answer: FilmFlare is a movie application where users can log in or sign up to explore a wide 
range of popular movies. Users can view movie details, add movies to their watchlist, 
browse movies by genre, and even enable dark mode for a personalized experience. 
2. How can I register for FilmFlare? 
Answer: You can register for FilmFlare using the Sign-Up option on the login screen. You can 
either create an account using your email and password or sign up through Google Single 
Sign-On (SSO). 
3. What features are available in FilmFlare? 
Answer: FilmFlare offers the following features: 
• View a list of popular movies with detailed information such as title, rating, genre, 
cast, and duration. 
• Add or remove movies to/from your watchlist. 
• Browse movies by genre. 
• Enable dark mode across the entire app. 
• Reset your password via email if you forget it. 
• View and manage your watchlist. 
4. How can I add movies to my watchlist? 
Answer: You can add movies to your watchlist by clicking the "Watchlist" button on any 
movie's detail page. You can also remove movies from your watchlist by selecting the same 
option again. 
5. How do I reset my password if I forget it? 
Answer: On the login screen, click the "Forgot Password" link, and enter the email address 
associated with your account. You will receive an email with instructions to reset your 
password. 
6. Can I enable dark mode in FilmFlare? 
Answer: Yes, you can enable dark mode in the app by going to the Settings page and 
toggling the "Dark Mode" option. This will apply the dark mode system-wide across the app. 
7. How does the Single Sign-On (SSO) feature work? 
Answer: FilmFlare supports Google Single Sign-On (SSO). When you choose to sign up or log 
in with Google, you’ll be redirected to a secure Google authentication page. Once 
authenticated, you will be logged into FilmFlare without needing a separate password. 
8. How do I browse movies by genre? 
Answer: You can browse movies by genre by navigating to the "Genres" section of the app. 
Here, movies are categorized based on their genres, and you can view and add movies to 
your watchlist directly from this section. 
9. Is there a way to see all the movies I’ve watchlisted? 
Answer: Yes, you can view all the movies you've added to your watchlist by going to the 
"Watchlist" section in the app. This section displays all your saved movies, and you can 
remove any movie from the watchlist at any time. 
9. How does the map feature work in FilmFlare? 
Answer: The map feature uses Google Maps to find cinemas within a 20 km radius of your 
current location. It shows cinema icons that you can tap to get directions directly in Google 
Maps. 
10. Why isn’t the map loading or showing nearby cinemas? 
Answer: Ensure that you have a stable internet connection and have granted location 
permissions for the app. If the issue persists, try restarting the app or checking your device’s 
location settings. 
11. Can I search for specific cinemas on the map? 
Answer: Yes, there is a search bar in the map feature that allows you to look up specific 
cinemas or locations. 
12. How do I change the app’s language in FilmFlare? 
Answer: Go to the Settings page and select your preferred language from the options: 
English, Afrikaans, or Zulu.



DEVELOPER INFO 
• Developed by Thaslyn Govender 
• Linkedin Profile: https://www.linkedin.com/in/thaslyn-govender-a4a408247  


Acknowledgments 
I would like to express my sincere gratitude to everyone who contributed to the successful 
development of the FilmFlare application. 
Firstly, I am deeply grateful to my instructors and mentors for their continuous support and 
guidance throughout this project. Their expertise and feedback were instrumental in shaping 
both the functionality and design of the app. 
I would also like to acknowledge the invaluable resources that greatly assisted me during the 
development process. A special thanks to the creators and contributors of Stack Overflow, 
whose community-driven platform provided solutions to many technical challenges I 
encountered. The discussions and code snippets shared by experienced developers were 
essential in troubleshooting issues and improving my coding approach. 
Additionally, I would like to thank the content creators on YouTube who produced 
instructional videos related to Android development and Kotlin. These tutorials helped 
deepen my understanding of key concepts, such as user authentication, UI design, and best 
practices for app development. Their detailed explanations and practical demonstrations 
were incredibly helpful throughout the journey. 
Finally, I would like to recognize the importance of persistence and self-learning during this 
project. The challenges I faced helped me grow as a developer, and I am proud of the 
progress made through continuous learning and improvement. 
Thank you to all the resources and platforms that supported the completion of FilmFlare.




