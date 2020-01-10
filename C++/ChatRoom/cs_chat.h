//Programed by Punam Rani Pal, CS163, 942331882
//This file contains the ADT that is created by the class. it has the data memebers
//defined in the private and the member function in the private. the data is 
//acessed by the member function and the client can use the function to create a cs chat
//room that will have room name, room description and list of members. membercan post messages 
//to a specific chat room and dete messages from a give chat room that are older than
//the given time stamp.
#include <iostream>
#include <cctype>
#include <cstring>
using namespace std;
//declaring constants
const int SIZE = 21;
const int ID = 31;
const int NAME_SIZE = 21;
const  int DES = 100;
const int MEMBER = 50;
const int MSG = 500;
struct user_details //struct to use by the client to ask user to fill the details
{
	char name[SIZE];
	char email_id[ID];
};

struct user_info //struct to group the room info
{
	char *user_name;
	char *user_email_id;
};
struct room_info
{
	char chatroom_name[NAME_SIZE];
	char chatroom_description[DES];
};
struct msg_info
{
	char msg[MSG];
	float time;
};

struct msg_node
{
	char *message;
	float msg_time;	
	msg_node *msg_next;
	char *a_name;
};
struct chatroom_node
{
	user_info *info;
	char *chat_room_name;
	char *description;
	msg_node * msg_head;
	msg_node * msg_tail;
	chatroom_node *next;
	int total_members;
};

class cs_chat
{
	public:
		cs_chat();//constructor
		~cs_chat();//destructor to deaalocate memory
		int add_a_chatroom( room_info & data, user_details array[], int member_size );//to add a chat room
		int copy_member( int member_size, user_details array[] );//to copy member
		bool compare( room_info & data, user_details & entry );//to authorize a user
		int post_a_msg( room_info & data, user_details & entry, msg_info & a_msg);//to post a message
		int display_a_chatroom_msgs( room_info & data);//to display a chat room messages
		int display_msg_with_a_key( room_info & data, char * key );//to display message with a key
		int display_specific_user_rooms(user_details & entry);//to display names of chat room
		int remove_older_msg( msg_info & a_time, room_info & data );//remove oplder message
	private:
		chatroom_node * head;//ponts to chat room
		int chatroom_counter;//count the chat room
		int msg_counter;//count the no of messages
		int display_specific_user_rooms_recursive(chatroom_node * head, user_details & entry );//recursive function
};
