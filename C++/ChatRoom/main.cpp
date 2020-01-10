//Programed by Punam Rani Pal, CS163, 942331882
//this the main which calles in the functions created in the implemented file
//The ADT of cs_chat is created using class and data are organized in the
//different stuctures to be able to use by the client efficiently. It calls
//in the member function to create a chat room add data and remove data
//the information is passed in the function from the main.
#include "cs_chat.h"

int main ()
{

	cs_chat chat1; //instance of the class created
	user_details user_data;//instance of the struct user details
	room_info room_data;//instance of the room data struct
	msg_info user_msg_info;//instance of the message room struct
	char response = 'n';//variable to take in the response
	int no_of_members = 0;//no of members
	char member_option = 'n';//response for adding member
	user_details members_list[MEMBER];//array to atore the members
	//prompt user to fill in the details if they want to add a chat room
	cout << "Do you want to add a chat room: ";
	cin >> response;
	cin.ignore();
	while ( toupper ( response) == 'Y')
	{	
		cout << "Please give the name to the chat room: ";
		cin.get( room_data.chatroom_name, NAME_SIZE, '\n');
		cin.ignore(100, '\n');
		cout << "Please give the description of the chat room: ";
		cin.get( room_data.chatroom_description, DES, '\n');
		cin.ignore(100, '\n');	

		//fill the member for a chat room
		cout << "Do you want to add a member: ";
		cin >> member_option;
		cin.ignore();
		int i = 0;//index of the member list
		if ( toupper ( member_option ) == 'Y')
		{
			do
			{
				member_option == 'N';
				cout << "Please enter a user name: ";
				cin.get( members_list[i].name, SIZE, '\n');
				cin.ignore( 100, '\n');

				cout << "Please enter user's email_id: ";
				cin.get( members_list[i].email_id, ID, '\n');
				cin.ignore( 100, '\n');
				i++;
				++no_of_members;
				if ( no_of_members < MEMBER )
				{
					cout << "Do you want to add more member: ";
					cin >> member_option;
					cin.ignore();	
				}	
			} while ( toupper( member_option ) == 'Y');
		}
		int result = chat1.add_a_chatroom( room_data, members_list, no_of_members);
		if ( result == 1)
			cout << "A chat room had been added" << endl;
		else
			cout << "Adding is not successful" << endl;
		cout << "Do you want to add again a chat room: ";
		cin >> response;
		cin.ignore();	
	}
	//to post a message to an exiting chat room
	response = 'n';
	cout << "Do you want to post a message to an existing chat room: ";
	cin >> response;
	cin.ignore();
	
	if ( toupper( response ) == 'Y')
	{
		do
		{
		do
		{
			cout << "\nPlease give a chatroom name in which you want to post a msg: ";
		//	room_info a_chatroom_name;
			cin.get(room_data.chatroom_name, NAME_SIZE, '\n');
			cin.ignore(100, '\n');

			cout << "\nPlease enter name: ";
			cin.get(user_data.name, SIZE, '\n');
			cin.ignore(100, '\n');
			cout << "Please enter email_id: ";
			cin.get(user_data.email_id, ID, '\n');
			cin.ignore(100, '\n');
			bool compare1 = chat1.compare(room_data, user_data);
			if (!compare1)
			{
				cout << "The information provided does not match member records, Do you want to continue (y/n): ";
				cin >> response;
				cin.ignore();
			}
			else
			{
				//take message and time
				do
				{
				cout << "Please enter you message: ";
				cin.get( user_msg_info.msg, MSG, '\n' );
				cin.ignore( 100, '\n' );
				cout << "Please enter time in hours( just hours in 24 hrs clock eg: for 2:17pm input - 14.17): ";
				cin >> user_msg_info.time;
				cin.ignore();
				//cout << "Please enter time in minutes( just minutes eg: for 2:17 pm input - 17): "; 
				//cin >> user_msg_info.min;
				//cin.ignore();
				if ( chat1.post_a_msg( room_data, user_data, user_msg_info ) )
					cout << "Your message had been added to the cs_chatroom " << "-" << room_data.chatroom_name << endl;
				cout << "Do you want to continue adding messages to this chat room(y/n): ";
				cin >> response;
				cin.ignore();	 
				} while ( toupper ( response ) == 'Y' ); 
			}	
		} while ( toupper ( response ) == 'Y' );
		
			cout << "Do you want to continue adding messages to another chatroom(y/n): ";
			cin >> response;
			cin.ignore();	
		} while ( toupper ( response ) == 'Y' );	
	}
	
	//display messages of a perticular chat room
	response = 'n';
	cout << "Do you want to display messags of a specific chatroom: ";
	cin >> response;
	cin.ignore();
	if ( toupper (response) == 'Y')
	{
		do
		{
			cout << "Please enter the name of the chat room: ";
			cin.get(room_data.chatroom_name, NAME_SIZE, '\n');
			cin.ignore(100, '\n');

			cout << "\nPlease enter name: ";
			cin.get(user_data.name, SIZE, '\n');
			cin.ignore(100, '\n');
			cout << "Please enter email_id: ";
			cin.get(user_data.email_id, ID, '\n');
			cin.ignore(100, '\n');
			int compare2 = chat1.compare(room_data, user_data);
			if (compare2 == 0)
			{
				cout << "The information provided does not match member records, Do you want to continue (y/n): ";
				cin >> response;
				cin.ignore();
			}
			else
			{
				cout << "Message of cs_chatroom are the following : " << room_data.chatroom_name << " : " << endl;
				if( !(chat1.display_a_chatroom_msgs(room_data)))	
					cout << "There are no meseages to display" << endl;	
				cout << "Do you want to view the messages of another chatroom ?" << endl;
				cin >> response;
				cin.ignore();	
			}
		} while ( toupper ( response ) == 'Y' );
	}
	cout <<" Do you want to find the Chatrooms in which a specific user is participating? (y/n) : ";
	cin >> response;
	cin.ignore();
	
	if( toupper(response) == 'Y')
	{	
		do
		{	
			cout <<"Please input the user name: ";
			cin.get(user_data.name, SIZE, '\n');
			cin.ignore();
			cout <<"Please input the email id for the user: ";
			cin.get(user_data.email_id, ID,'\n');
			cin.ignore();
			cout << "The chatrooms in which a specific user is participating are :" << endl; 
			int chat_test = chat1.display_specific_user_rooms(user_data);
			if(chat_test == 0)
			{
				cout <<" No Chatrooms present";
			}
			cout << " Do you want to find the chatrooms in which a specific user is participating again? (y/n) : "<< endl;
			cin >> response;
			cin.ignore(); 
		}while(toupper(response) == 'Y');
	}
	response = 'n';	
	cout << "Do you want to display all the messages of a specific chatroom with a key word: ";
	cin >> response;
	cin.ignore();
	if ( toupper ( response ) == 'Y' )
	{
		
		do
		{	
			cout << "Please enter the name of the chat room: ";
			cin.get(room_data.chatroom_name, NAME_SIZE, '\n');
			cin.ignore(100, '\n');
			cout <<"Please input the user name: ";
			cin.get(user_data.name, SIZE, '\n');
			cin.ignore();
			cout <<"Please input the email id for the user: ";
			cin.get(user_data.email_id, ID,'\n');
			cin.ignore();
			int compare3 = chat1.compare(room_data, user_data);
			if (compare3 == 0)
			{
				cout << "The information provided does not match member records, Do you want to continue (y/n): ";
				cin >> response;
				cin.ignore();
			}
			else
			{
				char a_key[SIZE];
				cout << "Pleas give a key word : ";
				cin.get( a_key, SIZE, '\n');
				cin.ignore( 100, '\n');
				cout << "The message with the given key word : " << endl; 
				int key_word = chat1.display_msg_with_a_key( room_data, a_key );
				if(key_word == 0)
				{
					cout <<" No message with the given key word" << endl;
				}
				cout << " Do you want to find the message of a particular chatroom with a spaecific key word again? (y/n) : "<< endl;
				cin >> response;
				cin.ignore();
			} 
		}while(toupper(response) == 'Y');
	}
	//take time
	cout << "Do you want to delete the older messages: ";
	cin >> response;
	cin.ignore();
	if ( toupper( response ) == 'Y' )
	{
		do
		{	
			cout << "Please enter the name of the chat room: ";
			cin.get(room_data.chatroom_name, NAME_SIZE, '\n');
			cin.ignore(100, '\n');
			cout <<"Please input the user name: ";
			cin.get(user_data.name, SIZE, '\n');
			cin.ignore();
			cout <<"Please input the email id for the user: ";
			cin.get(user_data.email_id, ID,'\n');
			cin.ignore();
			int compare4 = chat1.compare(room_data, user_data);
			if (compare4 == 0)
			{
				cout << "The information provided does not match member records, Do you want to continue (y/n): ";
				cin >> response;
				cin.ignore();
			}
			else
			{
				cout << "Please enter time ( ex: 10 hr 12 min as (10.12)): ";
				cin >> user_msg_info.time;
				cin.ignore();
				 
				int remove_msg = chat1.remove_older_msg( user_msg_info, room_data );
				if( remove_msg == 0)
				
					cout <<" No msg older than the given time stamp " << endl;
				else	
				{ 
					cout << "Message of cs_chatroom are the following : " << room_data.chatroom_name << " : " << endl;
					if( !(chat1.display_a_chatroom_msgs(room_data)))	
						cout << "There are no meseages to display" << endl;
				}	
				cout << " Do you want delete the older messages than the given time stamp?(y/n) : "<< endl;
				cin >> response;
				cin.ignore();
			} 
		}while(toupper(response) == 'Y');
		
	}
		 
	
	return 0;
}

