1. Description of Hardware requirements: 
There are three hardware components that are necessary for this project. The first component is a Raspberry Pi 3. These can act as bluetooth beacons when running an implementation of AltBeacon. They also function as the internet-connected hardware controller for our LED's. Using a Raspberry pi as a light also requires at least two male to female jumper cables, one LED per light, and one resistor between 100 and 1,000 ohms. The second hardware spec is that there must be a separate device functioning as the server. This requires the ability to run Maven. Finally, the last hardware requirement is that there must be a bluetooth capable android device that has the implemented app installed


2. Use cases:
    
    1.Turn On when user enters the room
    When a user walks into the room, the phone receives signals from present beacons.The phone chooses the strongest beacon signal and sends the associated room to the server.The server turns on the lights in the specified room.

    2.Turn Off light when user leaves the room
    When a user leaves the room, the phone no longer receives the signal from the beacon in that room or receives a stronger signal from another, closer beacon and room. The server turns off the lights of the room and beacon that the
    user has moved away from. 

    3.Manually control the light
    First, a user has to pick a room with the connected light on the app. The user can then adjust the brightness of the lights from the main page of our android app.
    Then, one scrollbar per light in the room are displayed on the app. The user can manipulate each scrollbar to change the brightness of a specific light.

    4.Add light
    There is an add light button located in the main page of the android app. The server provides a list of available light sources to the app. The user can then select a light and associate it with a specific room.
    
3.  Third party libraries and version information:
    
    1. JUnit
    2. Android 3.01
    3. AltBeacon
    4. Gradle
    5. Maven
    6. netty-socketio
    7. socket.io-client-java
    8. org.json
    9. RPi.GPIO
    10. socketIO-client 0.7.2 (Python Package Index)

    
4. How to build/access a running version of your application:
The android app: Your will need an android device or emulator to run the application. I would suggest using android studio so you can run the application through its emulators plus run the tests. The emulator would need to be using API with a minimum SDK version of 23. You should be able to just click run onto your target device or emulator. 
In order to use the app with the server, the Ip_Port variable in the SocketIO class will need to be changed to match your respective IP address in the form of "http://YOURIPADDRESS:9092"

The server: You will need to install Maven in order to use our server code. It can be started running the HokieHome.java file with Eclipse. This will start the server allowing the phone to now connect to the server. If the app was started before the server, just simply close the app and rerun it. 

The Raspberry Pi's Bluetooth Beacons: These are Raspberry pi's that have been set up to be bluetooth beacons by running the "startBeacon.py" script

The Raspberry Pi's Light Controllers: These are Raspberry pi's that have been set up to be light controllers by running the "LightDriver.py" script