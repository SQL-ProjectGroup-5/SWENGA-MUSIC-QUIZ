# Guess the Song - Music Quiz
## Team Members

| Team Member       | Workload                                                              |
| ------------------|:---------------------------------------------------------------------:|
| Christoph Tripolt | Templates (AdminLTE, Bootstrap and JavaScript), data model, spring security, testing                       |
| Anja Stadlhofer   | Comment and rate function, data model, dashboard                      |
| Michael Pölzl     | Quizmanagement, data model, management                                |
| Gerhard Zeisler   | Data model, usermanagement, actual gameplay, songmanagement           |

## Installation
1. Clone this repository or download the release.
2. Import existing project into eclipse
3. Add the db.properties
4. Modify Java Build Path of the project as needed
5. Change jpa-properties in dispatcher-servlet.xml to required attributes (``validate``, ``update``, or ``create-drop``)
6. Start project in eclipse (Tomcat)
7. If the database is empty, the demo users have to be created by accessing the url http://localhost:8080/fillUsers only ONCE.
8. Afterwards the application can be accessed by http://localhost:8080/GuessTheSong/

Please note that some features are blocked by modern browsers, for example autoplay of songs or custom timespan of playback.

## Known (security-) bugs
POST-mappings for many administrative functions (e.g. delete song/quiz, comment and results) do not check the permission of the affected object. A user can delete the quiz of another user by manually editing the ID in the request.
CSRF tokens are not enabled due to problems with input fields.
