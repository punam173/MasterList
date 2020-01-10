//Programed by: Punam Rani Pal, CC163, 942331882
//Main allows clent to use the ADT of hash_table and use its member function to
//get the desired result.clent ash user for the info im order to manipulate the data
//This program creates a solution to manage the list of events using Hash table.
//Hash table stores stores all the eventswith the help of the chaining method.
//It allows users to search an event with a key and they can get maximum number
//of events given by the user. It also allows user to delete an event which has
//already been happened.It display all the event and also the list of event after
//deletion. This .h file has two class one for managing the information of an event
//and other class is for the hash table.Both have private and public sections. 
#include "event.h"

int main()
{
	hash_table obj1;//object of the hash_table class
	char response = 'n';//variable to take response
	//prompt to ask to display all events
	cout << "To see all the events type (y/n): ";
	cin >> response;
	cin.ignore( 100, '\n');
	if ( toupper( response ) == 'Y' )
	{	
		int read = obj1.read_file();//calling function to read from file
		if ( read == 0 )
			cout << "There are no events to display: " << endl;
		cout << "THE LIST OF EVENTS ARE: \n" << endl;
		int res = obj1.display_all();//calling function to display all 
	}
	response = 'n';
	//prompt to retrieve events	
	cout << "To retrieve events with a specific keywords type (y/n): ";
	cin >> response;
	cin.ignore( 100, '\n');	
	char key[25];//to store user key	
	int num_find = 0;//num of matched found
	//take keyword an then call funtion to retrieve and display	
	while ( toupper( response ) == 'Y' )		
	{
		cout << "Please enter a key word: ";
		cin.get( key, 25, '\n');
		cin.ignore( 100, '\n');
		key[0] = toupper( key[0] );
		int i = 0;
		while ( i < 25 )
		{
			if ( key[i] == ' ' )
				key[ i + 1 ] = toupper( key[ i + 1 ] );
			++i;
		}
		cout << "How many at most you want to find: ";
		cin >> num_find;
		cin.ignore( 100, '\n');
		event_info *array = new event_info[num_find];//to store the matched events
		int found_event = obj1.retrieve( key, array, num_find);
		cout << "\n" << found_event << " evnets have been matched with the given keyword: " << endl;
		if ( found_event == 0 )
		{
			cout << "\nThere are no events matched for the given key" << endl;
		}
		response = 'n';
		cout << "\nDo you want to display the match found of the given key word (y/n): ";
		cin >> response;
		cin.ignore ( 100, '\n');
		if ( toupper( response ) == 'Y' )
		{
			cout << "\nThe matched events are: " << endl;
			obj1.display_akey_event( num_find, array );
		}		
		cout << "To continue retrieve more events type (y/n): ";
		cin >> response;
		cin.ignore( 100, '\n' );
/*		for( int i = 0; i < found_event; ++i )
		{
			array[i].remove_all();
		}
		delete [] array;	
*/
	}
	//take date and time to delete older events than the given info
	char in_date[13];
	char in_time[8];
	
	cout << "Do you want to delete the event that has alredy been happened(y/n): ";
	cin >> response;
	cin.ignore( 100, '\n');
	while( toupper( response ) == 'Y' )
	{
		cout << "Please enter date as ( YYYY-MONTH-DAY ex: 2017-03-01 ): ";
		cin.get( in_date, 13, '\n' );
		cin.ignore( 100, '\n');
		cout << "Please enter date as ( 1300/ 0423) : ";
		cin.get(in_time, 8, '\n');
		cin.ignore( 100, '\n'); 
		int c = obj1.remove_list( in_date, in_time );
		response = 'n';
		cout << "To continue type (y/n): ";
		cin >> response;
		cin.ignore( 100, '\n');				
	}
	
	cout << "To display all the events after deletion type (y/n): ";
	cin >> response;
	cin.ignore( 100, '\n' );
	if ( toupper ( response ) == 'Y' );
	{
		int d = obj1.display_all();
	}

	return 0;
}
