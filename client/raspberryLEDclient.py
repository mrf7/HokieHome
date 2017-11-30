import RPi.GPIO as GPIO
import time
from socketIO_client import SocketIO, LoggingNamespace
import sys
import logging

LED_PIN = 21

#https://pypi.python.org/pypi/socketIO-client

def changeBrightness(brightness):
	led.ChangeDutyCycle(brightness)
	print("LED set to: ", brightness)	
    
def onConnect(): 
	lightIdentifier = {"id": int(sys.argv[2]),
						"room": sys.argv[3]}
	socketIO.emit("lightIdent", lightIdentifier)
	print('connected')
# Set up GPIO pins 
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(LED_PIN, GPIO.OUT)
led = GPIO.PWM(LED_PIN, 100)
led.start(0)
# Set up logger
logging.getLogger("socketIO-client").setLevel(logging.DEBUG)
logging.basicConfig()
#sys.argv[1] is the ip address of the server, sys.argv[2] is the id number, sys.argv[3] is the room 
socketIO = SocketIO(sys.argv[1], 9092, LoggingNamespace)
socketIO.on("changeBrightness", changeBrightness)
socketIO.on("connect", onConnect)
socketIO.wait()
