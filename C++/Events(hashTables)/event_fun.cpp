//Programed by: Punam Rani Pal, CC163, 942331882
//This .cpp file contains the function implementation of the member functions of
// both the classes.main allows clent to use the ADT of hash_table and use its member 
//function to get the desired result.clent ash user for the info im order to manipulate 
//the data. This program creates a solution to manage the list of events using Hash table.
//Hash table stores stores all the eventswith the help of the chaining method.
//It allows users to search an event with a key and they can get maximum number
//of events given by the user. It also allows user to delete an event which has
//already been happened.It display all the event and also the list of event after
//deletion. This .h file has two class one for managing the information of an event
//and other class is for the hash table.Both have private and public sections. 

#include "event.h"
//constructor of class event_info that inilizes all its data member.
//it returns nothing and takes no argument.
event_info::event_info()
{
	data.name = NULL;
	data.location = NULL;
	data.date = NULL;
	data.time = NULL;
	data.description = NULL;
	data.keyword = NULL;
	data.name = 0;
}
//destructor of event_info class that deallocate all theused memory and reinitializes
//its data member.It takes nothing and returns nothing
event_info::~event_info()
{
	
	if ( data.name )	
		delete [] data.name;
	if ( data.location )
		delete [] data.location;
	if ( data.date )	
		delete [] data.date;
	if ( data.time )
		delete [] data.time;	
	if ( data.description )
		delete [] data.description;	
	if ( data.keyword )
		delete [] data.keyword;

}
//This function copies information from a struct given in its arguments or from any source to
//it's data members. It returns an integer value to give success or failure. An struct of event type
// is passed as a argument
int event_info::copy(event & entry)                                  
{
	data.name = new char[ strlen( entry.name ) + 1 ];
	strcpy( data.name, entry.name );
	data.location = new char[ strlen( entry.location ) + 1 ];
	strcpy( data.location, entry.location );
	data.date = new char[ strlen( entry.date ) + 1 ];
	strcpy( data.date, entry.date );
	data.time = new char[ strlen( entry.time ) + 1 ];
	strcpy( data.time, entry.time );
	data.description = new char[ strlen( entry.description ) + 1 ];
	strcpy( data.description, entry.description );
	data.keyword = new char[ strlen( entry.keyword ) + 1 ];
	strcpy( data.keyword, entry.keyword );	
	return 1;	
}
//this funtion display all the info that an event has. It takes no argument and returns an integer
//value for its success or failure.
int event_info::display()
{
	cout << "Name: " << data.name << " | " << "Location: " << data.location << " |  " << "Date: " 
	<< data.date << " | " << "Time: " << data.time << " | " << "Description: " << data.description 
	<< " | " << "Keywords: " << data.keyword << endl << endl;	
	return 1;
}
//this function is the constructor of the class and it initializes all its data member and returns
//nothing and takes no argument.
hash_table::hash_table()
{
	hash_size = 51;
	hash = new h_node * [hash_size];
	for ( int i = 0; i < hash_size; ++i )
	{
		hash[i] = NULL;
	}
	head = NULL;	 
}
//This function calculate the indes of the hash function by a generated formula and it takes a key as an
//argument to calculate the index. It returns an integer value to return the success or failure.
int hash_table::hash_function( char * key )
{
	int sum = 0;
	int index = 0;
	int len = strlen( key );
	for ( int i = 0; i < len; ++i )
	{
		sum += key[i];
	}
	index = sum % hash_size;
	return index;
}
//This function insert an event into the hash table. It insert the same event in multiple position
//based on the calculated index by the hash function of different keys of the same function. It takes
//an array and an struct type object as an argument and returns an integer value as success or failure
int hash_table::insert_anew_event( event & entry, char ** array )
{
	e_node * temp = new e_node;
	int bucket = (temp -> info.copy( entry ));
	temp -> next = head;
	head = temp;
	for ( int i = 0; i < entry.num_key; ++i)
 	{	
		int result = hash_function( array[i] );
		h_node * h_temp = new h_node;
		h_temp -> ptr = temp;
		h_temp -> next = hash[ result ];
		hash[ result ] = h_temp;
	}
	
	return 1;	
}
//this function seperate the keys from the given file. it seperates maximum of five keys. it takes
//the keys obtained fro the file and then store it into the array given in the argument. It returns an integer value
//as a success or failure
int hash_table::key_store( char * key, char ** array )
{
	int size = 5;
	int size_per_key = 25;
	int found = 0;

	for ( int i = 0; i < size; ++i )
	{
		if ( *key == 0 )
			return found;
		
		array[i] = new char[25];	
		for ( int j = 0; j < size_per_key; ++j )
		{
			if ( * key == 0 || * key == ',' )
				array[i][j] = 0;
			else
			{
				array[i][j] = *key;
				++key;
			}
		}
		if ( *key != 0 )
			key += 2;
		++found;
	}
	
	return found;
}
//This function reads evnets info from the file Events.txt. It store all the info in local
//variable and then store the same into the struct using the copy function. seperating key function
//is also called insid ethis function to store the seperated keys into an array. It returns an integer value
//and takes no argument.
int  hash_table::read_file( )
{
	event an_event;
	ifstream in_file;
	in_file.open( "Events.txt" );
	if ( in_file )
	{
		an_event.name = new char[25];
		an_event.location = new char[30];
		an_event.date = new char[13];
		an_event.time = new char[7];
		an_event.description = new char[300];
		char ** keys = new char *[5];
		for( int i = 0; i < 5; ++i )
		{
				
			keys[i] = NULL;
		}
		an_event.keyword = new char[100];
		in_file.get( an_event.name, 25, '|');
		in_file.ignore( 100, '|');
		while ( in_file && !in_file.eof())
		{	
			in_file.get( an_event.location, 30, '|');	
			in_file.ignore( 100, '|');
			in_file.get( an_event.date, 13, '|');	
			in_file.ignore( 100, '|');
			in_file.get( an_event.time, 7, '|');	
			in_file.ignore( 100, '|');
			in_file.get( an_event.description, 300, '|');	
			in_file.ignore( 100, '|');
			in_file.get( an_event.keyword, 100, '\n');
			in_file.ignore( 100, '\n');
			
			int store = key_store( an_event.keyword, keys );
			an_event.num_key = store;
			int insertevent = insert_anew_event( an_event, keys );	
			in_file.get( an_event.name, 25, '|');
			in_file.ignore( 100, '|');
		}	
		
		in_file.close();
		in_file.clear();
		delete [] an_event.name;
		delete [] an_event.location;
		delete [] an_event.date;
		delete [] an_event.time;
		delete [] an_event.description;
		delete [] an_event.keyword;
		for ( int i = 0; i < 5; ++i )
		{
			delete [] keys[i];	
		}	
		delete [] keys;
		keys = NULL;
	}
	else 
		return 0;
	return 1;	
}
//this function display alll the events in the hash table by calling a recursive function 
//it returns an integer value and takes no argument 
int hash_table::display_all()
{
	return re_display_all( head );	
}
//this function is the recursive fuction to display all the events. It takes head as an
//argunemt which is passed by value.It returns an integer value
int hash_table::re_display_all( e_node * head )
{
	int ret = 0;
	if ( ! head )
		return ret;
	head -> info.display();
	ret = re_display_all( head -> next);
	ret = 1;
	return ret;	
}
//This function retrieve the matched events for the given key and copy the matched events in an array.
//it takes a key and a array and the maximum it can find as an argument and returns the number of matched found 
int hash_table::retrieve( char * key, event_info array[], int & found )
{
	event ev;	
	int result = hash_function( key );
	h_node * current = hash[result];
	int ret = 0;
	for ( int i = 0; i < found && current; ++i )
	{
		current->ptr->info.copy_event(ev);		
		if ( strstr ( ev.keyword, key) != NULL )
		{
				
			current ->ptr->info.copy_event(ev); 
			array[i].copy(ev);
			current = current ->next;
			++ret;	
		}			
	}
/*	
	delete [] ev.name;
	delete [] ev.location;
	delete [] ev.date;
	delete [] ev.time;
	delete [] ev.description;
	delete [] ev.keyword;
*/
	found = ret;
	return ret;
}
//This function copy the event from data member to the given struct. It takes a struct and its ojbect
//as a argument and returns an integer value
int event_info::copy_event( event & a)
{
	a.name = new char[strlen(data.name) + 1];
	strcpy(a.name, data.name);
	a.location = new char[strlen(data.location) + 1];
	strcpy(a.location, data.location);
	a.date = new char[strlen(data.date) + 1];
	strcpy(a.date, data.date);
	a.time = new char[strlen(data.time) + 1];
	strcpy(a.time, data.time);
   	a.description= new char[strlen(data.description) + 1];
	strcpy(a.description, data.description);
	a.keyword = new char[strlen(data.keyword) + 1];
	strcpy(a.keyword, data.keyword);
	return 1;
}
//This function display events matched by a given key word. It takes number of matched event found
//as an argument from the main and an array from themain which hahs the information. tr returns an integer value
//as a success and failure
int hash_table::display_akey_event( int found, event_info array[] )
{
	for ( int i = 0; i < found; ++i )
	{
		array[i].display();
	}
	return 1;
}
//This functionn removes the events from the hash table and remove all the nodes by calling a recursive function
//it takes an array, number of keys, event name and an object of the struct as arguments.
//it returns an integer value as success or failure
int hash_table::remove( char ** array, int num, char *event_name, event & ex )
{
	for ( int i = 0; i < num; ++i )
	{
		int hash_index = hash_function( array[i] );
		int result = remove( hash[hash_index], event_name, ex );
	}
	return 1;
}
//This function removes the noide sfrom the hash table and its a recursive fuction that has been
//caleed in th eabove function. It takes the hash address, an event name and an struct type object
//it returns an integer value as a success and failure
int hash_table::remove( h_node * & current, char * event_name, event & ex )
{	
	if ( ! current)
		return 0;
       	current->ptr->info.copy_event(ex);
	h_node * temp;
	int ret = 0;
	if ( strcmp( ex.name, event_name) == 0 )
	{ 
		temp = current;
		current = current ->  next;
	        temp -> ptr = NULL;
		delete temp;
		 ret = remove( current, event_name, ex );
		++ret;
	}
	else
		ret = remove( current -> next, event_name, ex );		  
	return ret;
}
//This function remove all the info that a class object has.It takes no argument and
//returns an integer value as a success and failure
int event_info::remove_all()
{
	delete [] data.name;
	data.name = NULL;
	delete [] data.location;
	data.location = NULL;
	delete [] data.date;
	data.date = NULL;
	delete [] data.time;
	data.time = NULL;
	delete [] data.description;
	data.description = NULL;
	delete [] data.keyword;
	data.keyword = NULL;
	return 1;	
}
//This function remove all the events from the list that is managed by the head and
//finally remove all the events that head is ponting to.It takes the time and date
//given by theuser in main and it removes oll the events that is older than the 
//given time fram. It returns an integer value as a success or failure
int hash_table::remove_list( char * user_date, char * user_time )
{
	event ex;
	return remove_older( head, user_date, user_time, ex );
		
	delete [] ex.name;
	delete [] ex.location;
	delete [] ex.date;
	delete [] ex.time;
	delete [] ex.description;
	delete [] ex.keyword;
}
//This function removes older events by  taking the address of the head , user time and date from the main
//as an srguments. It returns an integer value as a success and failure 
int hash_table::remove_older( e_node * & head,char *user_date, char *user_time, event & ex )
{
	char * event_name = new char[50];
	char all_key[100];
	char **temp_key = new char*[5];
	int num = 0;
	for ( int i = 0; i < 5; ++i )
	{
		temp_key[i] = NULL;
	}
	int ret = 0;
	e_node * e_temp;
	if ( ! head )
		return 0;
	head->info.copy_event(ex);
	if( strcmp(ex.date, user_date) < 0 )
	{
		strcpy( event_name, ex.name);
		strcpy( all_key, ex.keyword); 
		int a  = key_store( all_key, temp_key ); 
		num = a;	 	
		remove( temp_key, num, event_name, ex );
		e_temp = head;
		head = head -> next;
		e_temp->info.remove_all();
		delete e_temp;	
		ret = remove_older( head, user_date, user_time, ex );		
		++ret;
	}
	else
	{
			
		if ( strcmp( ex.time, user_time) < 0 && strcmp( ex.date, user_date ) == 0 )
		{
									
			strcpy( event_name, ex.name);
			strcpy( all_key, ex.keyword); 
			int a  = key_store( all_key, temp_key ); 
			num = a;
			remove( temp_key, num, event_name, ex );	
			e_temp = head;
			head = head -> next;
			e_temp->info.remove_all();
			delete e_temp;	
			ret = remove_older( head, user_date, user_time, ex );
			++ret; 	
		}
	}
	if ( ret == 0 )
		ret = remove_older( head -> next, user_date, user_time, ex );
		
	return ret;
}
//This function is the desructor of the hahs-table class. it takes no argument
//and returns nothing. It deallocates all the memory used by the functions
//and reinitialize all is data members
hash_table::~hash_table()
{
	
	if ( hash )
	{
		h_node * temp;
		for ( int i = 0; i < hash_size; ++i )
		{
			while ( hash[i] )
			{
				temp = hash[i];
				hash[i] = hash[i] -> next;
				temp -> ptr = NULL;
				delete temp;
				hash[i] = NULL;		
			}
		}	
		delete [] hash;
		hash = NULL;	
	}
	if ( head )
	{
		e_node * temp;
		while( head )
		{
			temp = head;
			head = head -> next;
			temp -> info.remove_all();
			delete temp;	
		}
		head = NULL;
	}		
}
