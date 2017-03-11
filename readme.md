#Publicaties generator
This code was written to quickly automate some manual work. The code is barely tested and far from production ready. Please do not use this in production.

## Getting Started
To start generating your own publicaties, you first have to place your own Slack API Token in the application.properties with the under the key 'slackApiToken'. Afterwards you can run the program. In the project root a file should be visible after running the program called publicaties.txt. This file will contain the publicaties form the last 7 days.

## TODO
* Fix HtmlUtil to try multiple ways to retrieve the page title instead of just a normal GET request which fails often
* Write more tests
* Write documentation/comments
* Add properly error handling