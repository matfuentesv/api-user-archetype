FROM openjdk:21-ea-1-jdk

WORKDIR /app
COPY target/api-user-archetype-1.0.0.jar app.jar
COPY Wallet_GYBK4N4OFESPGB8J /app/oracle_wallet
EXPOSE 8081

CMD [ "java", "-jar", "app.jar" ]
