start locally:

SPRING_MAIL_USERNAME=<username> SPRING_MAIL_PASSWORD=<password> ./gradlew bootRun

check configuration parameters with:
heroku config

curl -i -X POST -H "Content-type:application/json" http://localhost:8080/v1/passwordRecovery -d '{ "email": "haliho@gmail.com"}'