//Programed by Punam Rani Pal, CS163, ID: 942331882
//This file contain the implementation of the member fuctions of the ADT ctreated 
//for the cs_chat room. The fuctions takes the argument from the user and manipulate,
//store, add, remove data from the list. The data is taken from the user and not in the
//fuctions. Menber function act as a mediator between the main and the ADT created.
#include "cs_chat.h"

//this fuction is a constructor which returns nothing and takes no
//arguments. it initializes the data members
cs_chat::cs_chat()
{
	head = NULL;
	chatroom_counter = 0;
	msg_counter = 0;
}
//this function is a destructor returns nothing and takes no arguments
//it deallocated all the dynamic memory created and reinitializes the 
//variables and the data members
cs_chat::~cs_chat()
{
	if ( head )
	{
	
		chatroom_node * temp = NULL;
		while ( head -> next )
		{
			temp = head -> next;
			delete [] head -> chat_room_name;
			head -> chat_room_name = NULL;
			delete [] head -> description;
			head -> description = NULL;

			if( head -> info != NULL)
			{	
				for ( int i = 0; i < head -> total_members; ++i)
				{
					delete [] head -> info[i].user_name;
					head -> info[i].user_name = NULL;
					delete [] head -> info[i].user_email_id;
					head -> info[i].user_email_id = NULL;
				}
				delete [] head -> info;
				head -> info = NULL;
			}	
			head -> next = NULL;
			delete head;
			head = temp;
		}
		temp = NULL;
		delete [] head -> chat_room_name;
		delete [] head -> description; 
		head -> chat_room_name = NULL;
		head -> description = NULL;
		if( head -> info != NULL)
		{	
			for ( int i = 0; i < head -> total_members; ++i)
			{
				delete [] head -> info[i].user_name;
				head -> info[i].user_name = NULL;
				delete [] head -> info[i].user_email_id;
				head -> info[i].user_email_id = NULL;
			}
			delete [] head -> info;
			head -> info = NULL;
		}
		delete head;
		head = NULL;
	}	
}
//this function is for adding a new chat room that takes arguments from the main
//for the user deatils and room information,it also takes in the size of the members
//for a chat room. It returns the success and failure in 1 and 0. the chat room
//conatins the room name, room description and the list of member.
int cs_chat::add_a_chatroom( room_info & data, user_details array[], int member_size )
{
	if ( ! head )
	{
		head = new chatroom_node;
		head -> chat_room_name = new char[ strlen(data.chatroom_name) + 1 ];
		strcpy(head -> chat_room_name, data.chatroom_name);
		head -> description = new char [ strlen(data.chatroom_description) + 1 ];
		strcpy(head -> description, data.chatroom_description);
		head -> next = NULL;
		int member = copy_member( member_size, array );
		head -> msg_head = NULL;	
		++chatroom_counter;
		cout <<"\nChatroom: "<<head -> chat_room_name<<" has been added"<<endl;	
		return 1;
	}
	if ( ! head -> next )
	{
		head -> next = new chatroom_node;
		head -> next -> chat_room_name = new char[ strlen(data.chatroom_name) + 1 ];
		strcpy(head -> next -> chat_room_name, data.chatroom_name);
		head -> next -> description = new char [ strlen(data.chatroom_description) + 1 ];
		strcpy(head -> next -> description, data.chatroom_description);
		head -> next -> next = NULL;
		int member = copy_member( member_size, array ); 	
		head -> next -> msg_head = NULL;
		++chatroom_counter;
		cout<<"\nChatroom: "<<head -> next -> chat_room_name <<" has been added"<<endl;
		return 1;
	}
	else
	{
		chatroom_node * current = head;
		while ( current -> next )
		{
			current = current -> next;
		}
		current -> next = new chatroom_node;
		current -> next -> chat_room_name = new char[ strlen(data.chatroom_name) + 1 ];      	
		strcpy(current -> next -> chat_room_name, data.chatroom_name);                       	
		current -> next -> description = new char [ strlen(data.chatroom_description) + 1 ]; 				
		strcpy( current -> next -> description, data.chatroom_description);
	//	int member = add_chatroom_member( member_size, data );
		current -> next -> next = NULL; 
		int member = copy_member( member_size, array ); 	
		current -> next -> msg_head = NULL;
		++chatroom_counter;
		cout<<"\nChatroom: "<< current -> next -> chat_room_name <<" has been added"<<endl;
		return 1;
	}
	return 0;

}
//this function copies the members from the static array given in the argument
//and created a dynamic array of members, it takes member size as the argumant
//as well. it returns an interg value 0 and 1 as a success and failure
int cs_chat::copy_member( int member_size, user_details array[] )
{
	
	if ( ! head -> next )	
	{
		//cout <<"number of members" << member_size << endl;
		head -> info = new user_info[ member_size] ;
		for ( int i = 0; i < member_size; ++i)
		{
			head -> info[i].user_name = new char[ strlen(array[i].name) + 1 ];
			strcpy(head -> info[i].user_name, array[i].name);
			head -> info[i].user_email_id = new char[ strlen( array[i].email_id ) + 1 ];
			strcpy(head -> info[i].user_email_id, array[i].email_id);	
		}
		head -> total_members = member_size;
		return 1;
	}
	else
	{
		chatroom_node * current = head;
		while ( current -> next!= NULL)
		{	
			current = current -> next;
		}
		current -> info = new user_info[ member_size ];
		for ( int i =0; i < member_size; ++i)
		{
				current -> info[i].user_name = new char[ strlen(array[i].name) + 1];
				strcpy(current -> info[i].user_name, array[i].name);
				current -> info[i].user_email_id = new char[ strlen( array[i].email_id ) + 1];
				strcpy(current -> info[i].user_email_id, array[i].email_id);
		}
		current -> total_members = member_size;
		return 1;		
	}
	return 0;	
}
//this function compare the user details with the information given in the chatroom
//and give the authorization to the user to add data or get data. it takes room and
//user deatils. returns boolean value as a success and failure 
bool cs_chat::compare( room_info & data, user_details & entry )
{
	chatroom_node * current = head;
	while (current)
	{
		if ( strcmp( current -> chat_room_name, data.chatroom_name) == 0 )
		{
			for ( int i = 0; i < current -> total_members; ++i)
			{
				if ((strcmp( current -> info[i].user_name, entry.name) == 0) && (strcmp( current -> info[i].user_email_id, entry.email_id )) == 0 )
					return true;
			}
		}
		current = current -> next; 
	} 
	return false;
} 
//this function post a msg to the given chatroom details and takes in the argument 
//of room datails nad user datils. this returns integer value as a success and 
//failure
int cs_chat::post_a_msg(room_info & data, user_details & entry, msg_info & a_msg)
{	
	if (!head)
		return 0;
	
	chatroom_node * current = head;
	while(current)
	{
		if(strcmp(current -> chat_room_name, data.chatroom_name) == 0)
		{
			if ( !current -> msg_head )
			{
				current -> msg_head = new msg_node;
				current -> msg_head -> message = new char [strlen( a_msg.msg ) + 1 ];
				strcpy(current -> msg_head -> message, a_msg.msg);
				current -> msg_head -> msg_time = a_msg.time;
				//current -> msg_head -> msg_min = a_msg.min;
				current -> msg_head -> a_name = new char[ strlen( entry.name ) + 1 ];
				strcpy(current -> msg_head -> a_name, entry.name);
				current -> msg_head -> msg_next = NULL;
				current -> msg_tail = current -> msg_head;
				return 1; 
			}
			else
			{
				current -> msg_tail -> msg_next = new msg_node;
				current -> msg_tail -> msg_next -> message = new char [strlen( a_msg.msg ) + 1 ];
				strcpy( current -> msg_tail -> msg_next -> message, a_msg.msg);
				current -> msg_tail -> msg_next -> msg_time = a_msg.time;
			//	current -> msg_tail -> msg_next -> msg_min = a_msg.min;
				current -> msg_tail -> msg_next -> a_name = new char[ strlen( entry.name ) + 1 ];
				strcpy(current -> msg_tail -> msg_next -> a_name, entry.name);
				current -> msg_tail -> msg_next -> msg_next = NULL;
				current -> msg_tail = current -> msg_tail -> msg_next; 
				return 1;	
			}	
		}
		else
			current = current -> next;
	}
	return 0;
}
//this function display a chat room message and takes in the room
//information and returns integer value as success and failure
int cs_chat::display_a_chatroom_msgs(room_info & data)
{
	if(!head)
		return 0;
			
	else
	{
		chatroom_node * current = head;
		while ( current )
		{
			if (strcmp(current -> chat_room_name, data.chatroom_name) == 0)
			{	
				if( !current ->msg_head)
					return 0;
				msg_node * msg_current = current -> msg_head;
				while( msg_current )
				{
					cout << msg_current -> a_name << " : " << msg_current -> message << endl;
					msg_current = msg_current -> msg_next;  
				}
				return 1;
			}
			current = current -> next;			
		}
		return 0;
	}
}
//this function display all room a user is associated with.
//it takes in the room name and user details and returns 1 or 0 as
//a success and failure
int cs_chat::display_specific_user_rooms(user_details & entry)
{
	return display_specific_user_rooms_recursive( head, entry);

}
//this is the recursive function that get call in the abaove function and user
//details. this returns the result in integer value
int cs_chat::display_specific_user_rooms_recursive( chatroom_node * head, user_details & entry )
{
	if(!head)
		return 0;
	int flag1 = 0;
	for(int i = 0; i < head -> total_members; ++i)
	{
		if(strcmp(head ->info[i].user_name, entry.name) == 0)
		{
			cout << head ->chat_room_name << endl;
			flag1 = 1; 
		}
	}
	int flag2 = display_specific_user_rooms_recursive(head -> next, entry);
	return flag1 + flag2;
}
//This function display all the messages with the key word.
//it takes in the room info and the key word as arguments.
//returns result in integer value
int cs_chat::display_msg_with_a_key( room_info & data, char key[] )
{
	if ( ! head )
		return 0;
	else
	{
		int flag = 0;	
		chatroom_node * current = head;
		while ( current )
		{
			if ( strcmp ( current -> chat_room_name, data.chatroom_name ) == 0 )
			{
				if ( ! current -> msg_head )
					flag = 0;
				else
				{
					msg_node * msg_current = current -> msg_head;
					int i = 1;
					while ( msg_current )
					{
						if ( strstr ( msg_current -> message, key ) != 0 )
							cout << i << " : " << msg_current -> message << endl;
							++i;
						msg_current = msg_current -> msg_next;
					}
					flag = 1;
				}	
			}
			current = current -> next; 
		}
		return flag;
	}
}
//this function removes older message than the time stamp.
//takes in time stamp and roon info as arguments and returns result in
//integer
int cs_chat::remove_older_msg( msg_info & a_time, room_info & data )
{
/*
	if ( ! head )
		return 0;
	else
	{
		int flag = 0;	
		chatroom_node * current = head;
		while ( current )
		{
			if ( strcmp ( current -> chat_room_name, data.chatroom_name) == 0 )
			{	
				if ( ! current -> msg_head )
			  		return 0;
				/*if ( ! current -> msg_head -> msg_next )
				{
					if ( current -> msg_head -> msg_time < a_time.time )
					{
						delete current -> msg_head -> message;
						current -> msg_head -> message = NULL;
						delete current -> msg_head -> a_name;
						current -> msg_head -> a_name = NULL;
						delete current -> msg_head;
						current -> msg_head = current -> msg_tail = NULL;
						return 0;
					}
				}
				else
				{
				msg_node * msg_current = current -> msg_head;
				while ( msg_current -> msg_next )
				{
					if ( msg_current -> msg_time < a_time.time )
					{
						msg_current = msg_current -> msg_next;
						delete current -> msg_head -> message;
						current -> msg_head ->  message = NULL;
						delete current -> msg_head -> a_name;
						current -> msg_head -> a_name = NULL;
						current -> msg_head -> msg_next = NULL;
						delete current -> msg_head;
						current -> msg_head = msg_current;
						flag = 1;	
							
					}
					if ( flag = 0 )
						msg_current = msg_current -> msg_next;
				}
				if ( msg_current -> msg_time < a_time.time )
				{
					delete msg_current -> message;
					msg_current -> message = NULL;
					delete msg_current -> a_name;
					msg_current -> a_name = NULL;
					delete msg_current;
					current -> msg_head = current -> msg_tail = msg_current = NULL;
				}
			}
			current = current -> next;
		}
	}
*/
	if ( ! head )
		return 0;
	int flag = 0;
	chatroom_node * current = head;
	while ( current )
	{
		if ( strcmp ( current -> chat_room_name, data.chatroom_name ) == 0 )
		{
			if ( ! current -> msg_head )
				return 0;
			if ( ! current -> msg_head -> msg_next )
			{
				if ( current -> msg_head -> msg_time < a_time.time )
				{
					delete current -> msg_head -> message;
					current -> msg_head -> message = NULL;
					delete current -> msg_head -> a_name;
					current -> msg_head -> a_name = NULL;
					delete current -> msg_head;
					current -> msg_head = current -> msg_tail = NULL;
					return 1;
				}
			}
			msg_node * msg_current = current -> msg_head;
			msg_node * previous = NULL;
			while ( msg_current -> msg_next )
			{
				previous = msg_current;
				msg_current = msg_current -> msg_next;
				if ( previous -> msg_time < a_time.time )
				{
					current -> msg_head = msg_current;
					delete previous -> message;
					previous -> message = NULL;
					delete previous -> a_name;
					previous -> a_name = NULL;
					delete previous;
					previous = NULL;
					flag = 1;
				}
			}
			if ( msg_current -> msg_time < a_time.time )
			{
				delete msg_current -> message;
				msg_current -> message = NULL;
				delete msg_current -> a_name;
				msg_current -> a_name = NULL;
				delete msg_current ;
				msg_current = current -> msg_tail = current -> msg_head =  NULL;
				flag = 1;
			} 
		
		}
		if ( flag == 1 )
			return 1;
		current = current -> next;	
	}
}
