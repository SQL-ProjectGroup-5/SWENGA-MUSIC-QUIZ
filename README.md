# Guess the Song - Music Quiz
## Team Members

| Team Member       | Workload                                                              |
| ------------------|:---------------------------------------------------------------------:|
| Christoph Tripolt | Templates (AdminLTE, Bootstrap and JavaScript), data model, spring security concept, testing                       |
| Anja Stadlhofer   | Comment and rate function, data model, dashboard                      |
| Michael Pölzl     | Quizmanagement, data model, projectmanagement                                |
| Gerhard Zeisler   | Data model, usermanagement, actual gameplay, songmanagement           |

## Installation
1. Clone this repository or download the release.
2. Import existing project into eclipse
3. Create a db.properties file in the src subdirectory of the project (a example is provided in the last section of this document).
4. Modify the Java Build Path of the project as needed
5. Change jpa-properties in dispatcher-servlet.xml to required attributes (``validate``, ``update``, or ``create-drop``)
6. Start project in eclipse (deploy to tomcat, "Run on Server")
7. If the database is empty, the demo users have to be created by accessing the url /fillUsers (e.g. http://localhost:8080/GuessTheSong/fillUsers) only ONCE.
8. Afterwards the application can be accessed by http://localhost:8080/GuessTheSong/
9. Login with either user/password or admin/password

Please note that some features are blocked by modern browsers, for example autoplay of songs or custom timespan of playback.

## Known (security-) issues
POST-mappings for many administrative functions (e.g. delete song/quiz, comment and results) do not check the permission of the affected object. Therefore, a user can delete the quiz of another user by manually editing the ID in the request.
CSRF tokens are not enabled due to problems with input fields.

## Example db.properties
The db.properties must contain the following:
````
db.url=jdbc:mysql://<yourServerIP>/<yourDatabase> 
db.username=username
db.password=password
````
