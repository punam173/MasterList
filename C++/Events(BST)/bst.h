//Programed by: Punam Rani Pal, CS163, ID: 942331882
//this file has the ADT of BST an dother class that manages an event
//this prigrams allows user to maintain a list of events and get them in sorted order
//it takes random data and insert all in sorted order, it then allows user to delete an event 
//with a matching name and also allow to delete all the events having a similar key
//it removes all the event using the postoder traveral and it display alll the events by using inoder taversal
#include <iostream>
#include <cctype>
#include <cstring>
#include <fstream>
using namespace std;

struct event_info
{
	char * name;
	char * location;
	char * date;
	char * time;
	char * description;
	char * keys;
	~event_info();
	event_info();
};

class event
{
	public:
		event();//constructor
		int copy_event( event_info & info );//copy all the event fron source to an struct type object
		int copy_from_node( event_info & a );//copy events from a node to an object
		void copy_fromnode( event & e );//copy from node
		int display_event();//display all
		void remove_event();//remove an event
		~event();//destructor
		
	private:
		event_info data;
};

struct node
{
	event entry;
	node * left;
	node * right;
};

class BST
{
	public:
		BST();//constructor
		int insert_new_event( event_info & eve );//inser an event in the tree
		int read_file();//read from an external file
		int remove_anevent( char * e_name );//remove an event
		int remove_allevent( char * user_key );//remove all the eevent with a matching key word
		bool retrieve( char * event_name, event_info & found );//retrieve an event and copy it to a struct bact to the main
		int display_all();//display all the events
		~BST();//destructor
	private:
		node * root;
		int height;
		node* create_node( event_info & eve );//creates a new node
		int insert_new_event( node * & root, event_info & eve );//isnsert a new event
		int display_all( node * root );//display all
		bool retrieve( node * root, char * event_name, event_info & found );//retrieve an event
		int remove_matchedevent( node * & root, char * user_key );//remove a matched event
		int key_store( event_info & obj3, char ** array );//seperates keys into an array	
		int compare( char * user_key, event_info & obj3, char ** key_array );//copmare the keys
		int remove_all_matchedevent( node * & root, char * user_key );//remove all the matche events with the key
		void remove( node * & root );//removes all the events
};
