//Programed by: Punam Rani Pal, CC163, 942331882
//This program creates a solution to manage the list of events using Hash table.
//Hash table stores stores all the eventswith the help of the chaining method.
//It allows users to search an event with a key and they can get maximum number
//of events given by the user. It also allows user to delete an event which has
//already been happened.It display all the event and also the list of event after
//deletion. This .h file has two class one for managing the information of an event
//and other class is for the hash table.Both have private and public sections. 
#include <iostream>
#include <cctype>
#include <cstring>
#include <fstream>

using namespace std;
//declaring sturct for event info
struct event
{
	char * name;
	char *location;
	char * date;
	char * time;
	char * description;
	char * keyword;
	int num_key;
};
//class of event info that manages all the information of an event with its member function
class event_info
{
	public:
		event_info();//constructor
		int copy( event & entry);//copy info from file or given source to a struc member
		int display();//display event info
		int copy_event( event & a);//copy an event from member data to a struct memebers.
		int remove_all();//remove all the event info
		~event_info();//destructor
	private:
		event data;//event type variable
};
//struct for head node 
struct e_node
{
	event_info info;
	e_node * next;
};
//struct for hash node
struct  h_node
{
	e_node *ptr;
	h_node * next;
};
class hash_table
{
	public:
		hash_table();//constructor thet initialise all the data members
		int hash_function( char * key);//calculate the index 
		int read_file( );//read info from an external file
		int insert_anew_event( event & entry, char ** array);//isert an event in hash table
		int retrieve( char * key, event_info array[], int & found );//match event for the key and then deep copy into the array
		int remove_list( char * user_date, char * user_time );//remove older event from the hash table
		int remove( char ** array, int num, char *event_name, event & ex );//removes an event
		int display_akey_event( int found, event_info array[] );//display events for the given key
		int display_all();//display all the events
		~hash_table();//destructor
	private:
		h_node ** hash;
		int hash_size;
		e_node * head;
		int key_store( char * key, char ** array );//seperates keys and store into an array
		int re_display_all( e_node * head );//resursive function to remove events
		int remove( h_node * & current, char * event_name, event & ex );//resursive to remove hash nodes
		int remove_older( e_node * & head, char * user_date, char * user_time, event & ex );//recursive to remove
};
