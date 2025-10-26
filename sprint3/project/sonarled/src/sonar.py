import RPi.GPIO as GPIO
import time
import sys

GPIO.setmode(GPIO.BCM)

TRIG = 23
ECHO = 24

GPIO.setup(TRIG, GPIO.OUT)
GPIO.setup(ECHO, GPIO.IN)

print("Avvio misurazione continua. Premi Ctrl+C per interrompere.")

try:
    while True:
        GPIO.output(TRIG, False)
        time.sleep(0.5)

        GPIO.output(TRIG, True)
        time.sleep(0.00001)
        GPIO.output(TRIG, False)

        while GPIO.input(ECHO) == 0:
            pulse_start = time.time()

        while GPIO.input(ECHO) == 1:
            pulse_end = time.time()

        pulse_duration = pulse_end - pulse_start
        distance = pulse_duration * 17150
        distance = round(distance, 2)

        print("Distanza:", distance, "cm")
        sys.stdout.flush()
        time.sleep(1)

except KeyboardInterrupt:
    print("\nMisurazione interrotta dall'utente.")

finally:
    GPIO.cleanup()
