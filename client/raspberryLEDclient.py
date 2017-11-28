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
	socketIO.emit("roomIdent", sys.argv[3])
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
#sys.argv[1] is the ip address of the server, sys.argv[2] is the port number, sys.argv[3] is the room 
socketIO = SocketIO(sys.argv[1], sys.argv[2], LoggingNamespace)
socketIO.on('ON', turnon)
socketIO.on('OFF', turnoff)
socketIO.on("connect", onConnect)
socketIO.wait()
