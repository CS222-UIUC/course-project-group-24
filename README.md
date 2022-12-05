# course-project-group-24
course-project-group-24 created by GitHub Classroom


(a) We created an android app that allows users to take a picture of a receipt with their phone and have it uploaded to a database for future usage.

(b) The app works by calling the built in camera permissions and redirects a user when they press a button. 

After a user takes a photo of the receipt they want to upload, it will return them to the home menu where the user can then press "Add Items", which calls a POST request to an OCR (optical character recognition) API that analyzes the receipt photo given and returns a JSON object.

We then parse the JSON object and insert it into our SQLite database for future usage. Further possibilities with this application is running analytics with the data we collect in the database. 

Since the OCR API gives much more information than just item and price, (for example, it will return the store name and the date), future capabilities can be expanded by offering users to be able to track previous grocery run prices and see how the prices increase year after year.

(c) 

1. Download android studio for mac/windows 

https://developer.android.com/studio/?gclid=CjwKCAiAp7GcBhA0EiwA9U0mtvMPgWuUaGKmybFd-e4ewTZ_sXMlBywkTNLOeeG8iJdgme4UgyJfaBoCjG4QAvD_BwE&gclsrc=aw.ds

2. git clone the repository into local device

3. setup an android device emulator (tested with Pixel 3a locally).

4. Build and run application through android studio.

5. Press the "Take Picture" button to take a photo of a receipt of choice.

6. Once redirected back to app, press "Add Items" button to upload into database.

7. Finally, press "Print Items" in order to see the database populate with the items from the receipt at the bottom of the page.

8. If you wish, you can clear the database using the "Clear Database" button.

(d) Jaden Lee (Frontend/Backend), Adriana (Frontend/Backend, Github actions, Test case writer)
