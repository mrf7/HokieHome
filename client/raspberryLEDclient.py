import RPi.GPIO as GPIO
import time
from socketIO_client import SocketIO, LoggingNamespace
import sys
import logging



#https://pypi.python.org/pypi/socketIO-client

def turnon():
	GPIO.output(18,GPIO.HIGH)
	print("LED on")	
	    
    
def turnoff():
	GPIO.output(18,GPIO.LOW)
	print("LED off")
def onConnect(): 
        socketIO.emit("message", "mmm")
	print('connected')
GPIO.setmode(GPIO.BCM)
GPIO.setwarnings(False)
GPIO.setup(18, GPIO.OUT)

# Set up logger
logging.getLogger("socketIO-client").setLevel(logging.DEBUG)
logging.basicConfig()
#sys.argv[1] is the ip address of the server, sys.argv[2] is the port number
#socketIO = SocketIO(sys.argv[1], sys.argv[2])
socketIO = SocketIO(sys.argv[1], sys.argv[2], LoggingNamespace)
socketIO.on('ON', turnon)
socketIO.on('OFF', turnoff)
socketIO.on("connect", onConnect)
socketIO.wait()
