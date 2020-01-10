//Programed by: Punam Rani Pal, CS163, ID: 942331882
//this prigrams allows user to maintain a list of events and get them in sorted order
//it takes random data and insert all in sorted order, it then allows user to delete an event 
//with a matching name and also allow to delete all the events having a similar key
//it removes all the event using the postoder traveral and it display alll the events by using inoder taversal
//this file takes user information and the supplie it back to the ADT.
#include "bst.h"

int main()
{
	BST bst1;//object of the class BST
	event event1;//object of the class event
	event_info match_found;//object os the structure
	char name_event[51];
	char remove_event_name[61];
	char response = 'n';
	//promt to display the list of events
	cout << "To display all the events type(y/n): ";
	cin >> response;
	cin.ignore( 100, '\n');
	if ( toupper( response ) == 'Y' )
	{
		int read = bst1.read_file();
		if ( read == 0 )
			cout << "Duplicate event name has been been added in the list" << endl;
		bst1.display_all();
	}	
	//prompt to retrieve an event info
	response = 'n';
	cout << "Do you want to find a matched event(y/n):";
	cin >> response;
	cin.ignore( 100, '\n');
	while ( toupper( response ) == 'Y')
	{
		//prompt to enter an event name
		cout << "Please enter an event name to be matched: ";
		cin.get( name_event, 51, '\n' );
		cin.ignore( 100, '\n');
//		name_event[0] = toupper( name_event[0]);
		for ( int i = 0; i < 51; ++i )
		{
		//	if ( name_event[i] == ' ' )
				name_event[i] = toupper( name_event[i] );	
		} 

		int found_event = bst1.retrieve( name_event, match_found );
		if ( found_event )
			cout << "A matching event of the given event's name has been found" << endl;
		else
			cout << "No matching event of the given name has been found";
		response = 'n';
		cout << "To continue finding a matching event type( y/n): ";
		cin >> response;
		cin.ignore( 100, '\n');
	}
	//prompt to take an event name to remove it from the list
	response = 'n';
	cout << "To remove an event type (y/n): ";
	cin >> response;
	cin.ignore( 100, '\n' );
	while ( toupper( response ) == 'Y' )
	{
		//prompt to enter an event name
		cout << "Please enter an event name: ";
		cin.get( remove_event_name, 61, '\n' );
		cin.ignore( 100, '\n' );
	
		for ( int i = 0; i < 61; ++i )
		{
		//	if ( name_event[i] == ' ' )
				remove_event_name[i] = toupper( remove_event_name[i] );	
		} 
		int a = bst1.remove_anevent( remove_event_name );
		if ( a == 1 )
		{
			cout << "The matched evnet name is deleted" << endl;
		}
		else
		{
			cout << "The event with the given name is not found" << endl;
		}
	
		bst1.display_all();
		response = 'n';
		cout << "To continue deleting ann event with the name. type (y/n): ";
		cin >> response;
		cin.ignore( 100, '\n');	
	}		
	
	//prompt for removing all the event with matching key
	char key_to_match[20];
	response = 'n';
	cout << "To delete events a matching key, please type (y/n): ";
	cin >> response;
	cin.ignore( 100, '\n');
	while( toupper( response ) == 'Y')
	{
		cout << "Please enter a key word: ";
		cin.get( key_to_match, 20, '\n');
		cin.ignore( 100, '\n');
				
		for ( int i = 0; i < 20; ++i )
		{
		//	if ( name_event[i] == ' ' )
				key_to_match[i] = toupper( key_to_match[i] );	
		}
		int r = bst1.remove_allevent( key_to_match );
//		if ( r == 0 )
//		{
//			cout << "No event has the given key word ); " << endl;
//		} 
		bst1.display_all();
		response = 'n';
		cout << "To continue, type(y/n): ";
		cin >> response;
		cin.ignore( 100, '\n');
	}
	return 0;
};
