start locally:

SPRING_MAIL_USERNAME=<username> SPRING_MAIL_PASSWORD=<password> ./gradlew bootRun

check configuration parameters with:
heroku config

curl -i -X POST -H "Content-type:application/json" http://localhost:8080/v1/passwordRecovery -d '{ "email": "haliho@gmail.com"}'



example request:

http://localhost:8080/v1/users/password/edit?reset_password_token=37b482e03e25c4fdad3ab1eff367eb5719233f8bc95fdefe0db8a690c44bea7e62da21a2095ca313e9d8615e8002ebee

