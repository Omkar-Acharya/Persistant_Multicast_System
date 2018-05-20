# Persistant_Multicast_System
Performing Temporal Persistant Mulitcast System using Java

Contributors:

    Akshay Mendki
    Omkar Acharya

This system simulates temporal persistant-multicast system.  Multicast system comprises of Participants, forming a group in which multicast messages are send. The communication between Participants is managed by Coordinator. Below are the commands simulated in the project. All the commands are sent by Participants.

1. register portnumber : Participant indicates that it wants to join the multicast group. It specifies the portnumber it wants to receive the multicast messages to.

2. deregister : Participant deregisters from the multicast group. No multicast messages are sent to the deregistered participant. 

3. disconnect : Participant temporary logs out of the group. No multicast messages are sent to the participant while it is disconnected. Coordinator will save the multicast messages, in case the participant reconnects again.

4. reconnect portnumber: Participant can join the multicast group after disconnecting. After reconnection it will get all the messages Coordinator has saved provided it satisfies temporal contstraint. According to temporal constraint, the coordinator will maintain the persistence of the multicast messages for a certain threshold of time, td. After td time is passed, only messages send in past td seconds will be sent to the participant.

5. msend message: Msend, multicasts the message to all the participants.

    
How to compile and run?

To compile java files use: javac *.java

To run Coordinator use: java Coordinator <configFileName>
To run Participant use: java Participant <configFileName>



Folder Structure:

--Participant
  --Participant.java
  --ParticipantThread.java
  --MyConfigFile.txt
  
--Coordinator
  --Coordinator.java
  --CoordinatorThread.java
  --CoordinatorProcess.java
  --GroupParticipants.java
  --MyConfigFile.txt
  

    

This project was done in its entirety by Akshay Mendki and Omkar Acharya. We hereby state that we have not received unauthorized help of any form.
