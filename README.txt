-- CSCI 201 Summer 2014 --

	Project Name: ChatMe 
	
7/6/2014 Code Submission:

PLEASE READ

Implemented Features:
-create new account window
	 --> creating and storing the new account into the MySQL Database and sends you to
 	the LogIn window
-logging in checks if your credentials are consistent with the MySQL Database
-If credentials are correct, you will be sent to the buddy list window

Almost Implemented Features:
-BuddyList GUI: currently has a few layout manager issues.
	--> as soon as this is ironed out, the buddy list should dynamically update it's
	display when other users log on and off the server.
	--> forum-style group chats will also be dynamically visible for users to join
	
What We Still Have to Implement:
-Messaging: our "API" is set up to implement sending information from one user to all
users connected to the server (forum-group chat style). We just have to write an algorithm that only sends the
message to desired users on the server (one on one chats).
-Signing out --> pretty similar to signing in

If you try to run our code, you will fail!!!!!!!

You must configure your MySQL workbench to be ready to receive what we want to send it.

setting up mysql workbench:
- create database "ChatMeDB"
- in "ChatMeDB", create 3 tables
1) UserInfo - (uid, username, password, bio, img, primary key)
2) OnlineUsers - (uid, username, primary key)
3) Conversations (uid, convoName, moderator, content, primary key)

ALSO in ChatMeClient.java, line 38, you will have to change the IP address to "localhost",
or whatever IP address you want to set it to.

Once you have that...
1. Run ChatMeServer
2. Run ChatMeClient
*continue running ChatMeClients to your heart's desire*
fin