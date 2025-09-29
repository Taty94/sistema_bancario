FROM eclipse-temurin:21-jre

RUN addgroup --gid 1000 appgroup && \
    adduser --uid 1000 --gid 1000 --home /home/appuser --disabled-password appuser

WORKDIR /app

COPY --chown=appuser:appgroup build/libs/*.jar app.jar

USER appuser

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]