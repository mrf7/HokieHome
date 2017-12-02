import RPi.GPIO as GPIO
import time
from socketIO_client import SocketIO, LoggingNamespace
import sys
import logging
import os.path
LED_PIN = 21

#https://pypi.python.org/pypi/socketIO-client

def changeBrightness(brightness):
	led.ChangeDutyCycle(brightness)
	print("LED set to: ", brightness)	
    
def onConnect(): 
    if os.path.isfile('.room.db'):
        roomFileString = open('.room.db')
        room = roomFileString.read().strip()
    else:
        room = "!NOROOM"

    lightIdentifier = {"id": int(sys.argv[2]), "room": room}
    socketIO.emit("lightIdent", str(lightIdentifier))
    print('connected')

def setRoom(roomName): 
    # Write the room name to a file
    roomFileString = open('.room.db', 'w+')
    roomFileString.write(roomName)
    roomFileString.close()
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
socketIO.on('reconnect',onConnect)
socketIO.on('setRoom', setRoom)
socketIO.wait()
