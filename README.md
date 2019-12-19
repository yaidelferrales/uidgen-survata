## uidgen

uidgen generates unique ids based on a combination of several random numbers, the timestamp, the proccess id and the current ipAddress.

## Requirements

Nodejs 10 or newer.

## Run

Navigate to the source code path and run:
* npm install.
* npm start OR npm run start-dev (for production or developement).

Then you can go to your browser and use http://localhost:3000/newId/[namespace] to generate unique ids.

## Solution

This solution tries to generate enough entropy as to make each generated id unique. To achieve the entropy level required we use a combination of 4 random numbers, the timestamp the process id and the computer Ip Address. By using the combination of process id and Ip address we get different ids from different computers, making horizontal scalability possible.

## Future work

* Add a counter to generate different Ids even if the proccess is called on the same millisecond.
* Add more computer specific variables to achieve a bigger level of entropy.