import RPi.GPIO as GPIO
import time
from socketIO_client import SocketIO
import sys



#https://pypi.python.org/pypi/socketIO-client

def turnon():
    GPIO.output(18,GPIO.HIGH)
    print "LED on"
    
    
def turnoff():
    GPIO.output(18,GPIO.LOW)
    print "LED off"


#sys.argv[1] is the ip address of the server, sys.argv[2] is the port number
socketIO = SocketIO(sys.argv[1], sys.argv[2])
socketIO.on('ON', turnon())
socketIO.on('OFF', turnoff())
