# Guess the Song - Music Quiz
## Team Members

| Team Member       | Workload                                                              |
| -------------     |:---------------------------------------------------------------------:|
| Christoph Tripolt | Templates, data model, spring security, testing                       |
| Anja Stadlhofer   | Comment and rate function, data model, dashboard                      |
| Michael Pölzl     | Quizmanagement, data model                                            |
| Gerhard Zeisler   | Data model, usermanagement, actual gameplay, songmanagement           |

## Installation
Just run the /fillUsers once. Afterwards the default users ("user", "admin") are created with the password "password".
After login as admin, additional users can be created.
## Features not implemented

## Known (security-) bugs
POST-mappings for many administrative functions (e.g. delete song/quiz) do not check the permission of the affected object. A user can delete the quiz of another user by manually editing the ID in the request.
CSRF tokens are not enabled due to problems with input fields.
