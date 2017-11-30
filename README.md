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