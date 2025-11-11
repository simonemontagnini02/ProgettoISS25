# 1. Immagine di base
# Usiamo un'immagine Java Runtime (JRE) ufficiale.
# Scegli la versione di Java corrispondente al tuo progetto (es. 17)
FROM eclipse-temurin:17-jdk

# 2. Definisci la directory di lavoro all'interno del container
WORKDIR /HoldGui

# 3. Copia il file .jar buildato
# Sostituisci 'target/il-tuo-file.jar' con il nome e percorso esatto del tuo JAR
COPY build/libs/HoldGui-0.0.1-SNAPSHOT.jar HoldGui.jar



# 4. Esponi la porta
# Se la tua app gira sulla porta 8080 (standard per Spring Boot), esponila.
EXPOSE 8080

# 5. Comando di avvio
# Questo comando verrà eseguito all'avvio del container.
CMD ["java", "-jar", "HoldGui.jar"]