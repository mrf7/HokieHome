import os
import sys

if len(sys.argv) < 2:
    print("Please provide a room name")
    exit()
room = sys.argv[1]
# Convert the string into hex
roomHex = " ".join("{:02x}".format(ord(c)) for c in room)
#Add zeros to make the advertising data 16 bytes. Since each byte is two characters and bytes are separated by spaces, 
# String should be 
while (len(roomHex) < 47): 
    roomHex = roomHex + " 00"
print(roomHex)
os.system("./altbeacon_transmit.sh " + '"' + roomHex + '"') 
