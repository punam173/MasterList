//Programed by: Punam Rani Pal, CS163, ID: 942331882//This file has fucntion definition and implementation of all the member functions
//this prigrams allows user to maintain a list of events and get them in sorted order
//it takes random data and insert all in sorted order, it then allows user to delete an event 
//with a matching name and also allow to delete all the events having a similar key
//it removes all the event using the postoder traveral and it display alll the events by using inoder taversal
#include "bst.h"

//this function ia a constructor, it initializes all the data members and takes nothing
//as an argument and returns nothing
event::event()
{
	data.name = NULL;
	data.location =  NULL;
	data.date = NULL;
	data.time = NULL;
	data.description = NULL;
	data.keys = NULL;	
}
event_info::event_info()
{

	name = NULL;
	location =  NULL;
	date = NULL;
	time = NULL;
	description = NULL;
	keys = NULL;	
}
//this function is a destructor, it delete all the allocated memory after their usage
//it also take snothing as an argument and returns nothing
event::~event()
{
//	remove_event();
}
event_info::~event_info()
{
	if ( name )
        delete [] name;
	if ( location ) 
	delete [] location;
	if ( date )
	delete [] date;
	if ( time )
	delete [] time;
	if ( description )
	delete [] description;
	if ( keys )
	delete [] keys;
}
//this function removes an event and takes no argiument and returns nothing
void event::remove_event()
{
	if ( data.name )
		delete [] data.name;	
	if( data.location )
		delete [] data.location;
	if ( data.date )
		delete [] data.date;
	if ( data.time )
		delete [] data.time;
	if ( data.description )
		delete [] data.description;
	if ( data.keys )
		delete [] data.keys;
}

//this function will copy an event into the class object memeber
//this will take a struct type object as an argument and returns an integer value
int event::copy_event( event_info & info )
{
	
	delete [] data.name;	
	data.name = new char[ strlen( info.name ) + 1 ];
	strcpy( data.name, info.name );		
	delete [] data.location;	
	data.location = new char[ strlen( info.location ) + 1 ];
	strcpy( data.location, info.location );		
	delete [] data.date;	
	data.date = new char[ strlen( info.date ) + 1 ];
	strcpy( data.date, info.date );		
	delete [] data.time;	
	data.time = new char[ strlen( info.time ) + 1 ];
	strcpy( data.time, info.time );		
	delete [] data.description;	
	data.description = new char[ strlen( info.description ) + 1 ];
	strcpy( data.description, info.description );	
	delete [] data.keys;	
	data.keys = new char[ strlen( info.keys ) + 1 ];	
	strcpy( data.keys, info.keys );	

}
//this function display an event and takes nothing as an argument
int event::display_event()
{
	
	cout << "Name: " << data.name << " | " << "Location: " << data.location << " |  " << "Date: " 
	<< data.date << " | " << "Time: " << data.time << " | " << "Description: " << data.description 
	<< " | " << "Keywords: " << data.keys << endl << endl;	
	return 1;
}
//this function copies an event info from a node to an structure type object passed
//as an argument an returns an integer value
int event::copy_from_node( event_info & a)
{
	if ( a.name )
		delete [] a.name;	
	a.name = new char[strlen(data.name) + 1];
	strcpy(a.name, data.name);
	if ( a.location )
		delete [] a.location;		
	a.location = new char[strlen(data.location) + 1];
	strcpy(a.location, data.location);
	if ( a.date )
		delete [] a.date;			
	a.date = new char[strlen(data.date) + 1];
	strcpy(a.date, data.date);
	if ( a.time )
		delete [] a.time;			
	a.time = new char[strlen(data.time) + 1];
	strcpy(a.time, data.time);	
	if ( a.description )
		delete [] a.description;			
	a.description= new char[strlen(data.description) + 1];
	strcpy(a.description, data.description);
	if ( a.keys )
		delete [] a.keys;
	a.keys = new char[strlen(data.keys) + 1];
	strcpy(a.keys, data.keys);
	return 1;
}

//this fuction is a constructor of class BST
//it initialize all its data member and takes no argument and returns nothing
BST::BST()
{
	root = NULL;
	height = 0;
}


//this function insert a new event in the tree. It first checks whether the root is emty.
//if the root is empty it adds a node at the root and sets both the pointers.
//if the root is not empty then it adds a node either on the left or right based on the name of the event.
//it takes an information from the file and then adds in the tree
//it returns an integer value as a success or failure.

node* BST::create_node( event_info & eve )
{
	node * n = new node;
	n -> entry.copy_event( eve );
	n -> left = NULL;
	n -> right = NULL;
	return n;
}
//this function inser an event into the tree by calling a recursive function 
//it takes event info type structure as an argument
int BST::insert_new_event( event_info & eve )
{
	return insert_new_event( root, eve ); 
}
//this function inser an event into the tree based on the value of root whether its greter or smaller
//it then returns an integer value as a success or failure, it takes root address and an event_info object
int BST::insert_new_event( node * & root, event_info & eve )
{
	int ret = 0;
	if ( ! root )
		root = create_node( eve );
	else
	{	
		event_info obj;
		root -> entry.copy_from_node( obj );
		if ( strcmp( eve.name, obj.name ) < 0 )
		{
			if ( root -> left != NULL )
			{
				ret = insert_new_event( root -> left, eve );
				++ret;
			}
			else
				root -> left = create_node( eve );		
		}
		else if ( strcmp( eve.name, obj.name ) >  0 )
		{			
			if ( root -> right != NULL )
			{
				ret = insert_new_event( root -> right, eve );
				++ret;
			}
			else
				root -> right = create_node( eve );		
		}
		else
			ret = 0;
		
	}
	
/*	delete [] obj.name;
	delete [] obj.location;
	delete [] obj .date;
	delete [] obj.time;
	delete [] obj.description;
	delete [] obj.keys;
*/
	return ret;
}
//this function reads the list of event's info from an external file an d copied it into a class
//object it also calls the insert function to add a new node in the bst tree
//it then delte the ojbect that  has been created inside this function
//it takes no argument an dreturns an integer value that shows the result
int  BST::read_file( )
{
	event_info an_event;
	int result = 0;
	ifstream in_file;
	in_file.open( "Events.txt" );
	if ( in_file )
	{
		an_event.name = new char[25];
		an_event.location = new char[30];
		an_event.date = new char[13];
		an_event.time = new char[7];
		an_event.description = new char[300];		
		an_event.keys = new char[100];
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
			in_file.get( an_event.keys, 100, '\n');
			in_file.ignore( 100, '\n');
			
			int result = insert_new_event( an_event );	
			in_file.get( an_event.name, 25, '|');
			in_file.ignore( 100, '|');
		}	
	}	
	in_file.close();
	in_file.clear();
	return result; 
}
//It display all the events in the tree
int BST::display_all()
{
	return display_all( root );
}
//this function dipslay all the events's info in the tree and if the root is empty then it returns zero.
//it takes nothing node root as an arguments as passed by value and returns an integer value
int BST::display_all( node * root)
{
	int ret = 0;
	if ( root )
	{
		if( root -> left )
		{
			ret = display_all( root -> left );
			++ret;	
		}
		root -> entry.display_event();
		if( root -> right )
		{
			ret = display_all( root -> right );
			++ret;
		}		

	}
	else
		ret = 0;
	return ret;
}


//this function will retrieve an event from the tree based on the matching name passed by the client
//the matching name event's ifo is then deep copy into an structure type object back to the main
//it takes an object of a struct passed by reference//and returns an boolean value as a success or a failure.
bool BST::retrieve( char * event_name, event_info & found )
{
	return retrieve( root, event_name, found );
}
//this function is the recursive function to get the retrieve evnet's info
//it takes node root as an argument paeesed by value and all the arguments are same as abve
bool BST::retrieve(  node * root, char * event_name, event_info & found )
{
//	event_name = toupper( event_name );
	event_info obj2;
	bool ret;
	if ( root )
	{
		root -> entry.copy_from_node( obj2 );
//		obj2.name = toupper( obj2.name);
		int len = strlen( obj2.name);
		for ( int i =0; i < len; ++i )
		{
			obj2.name[i] = toupper( obj2.name[i]);	
		}
		if ( strcmp( event_name , obj2.name ) == 0 )
		{	
			root -> entry.copy_from_node( found );
			ret = true;
		}
		
		else if ( strcmp ( event_name ,  obj2.name ) < 0 )
		{
			if ( root -> left )
				return retrieve( root -> left, event_name, found );

		}		 
		else
			return retrieve( root -> right, event_name, found );
	}
	else
		ret = false;	


	return ret;
}

//this function will remove an event from the list based on the event's name given by the user
//it then adjust the pointers and returns an integer value as a success and failure

int BST::remove_anevent( char * e_name )
{
	return remove_matchedevent( root, e_name );	
}
int BST::remove_matchedevent( node * & root, char * e_name )
{
	
	int ret = 0;
	event_info obj3;
	if ( ! root )
		return 0;	
	root -> entry.copy_from_node(obj3);	
	int len = strlen( obj3.name);		
	for ( int i =0; i < len; ++i )
	{
		obj3.name[i] = toupper( obj3.name[i]);	
	}
	node * temp = NULL;				
	node * current = NULL;
	node * previous = NULL;
	if ( strcmp ( e_name, obj3.name ) == 0 )
	{
		if ( root ->left == NULL && root -> right == NULL )
	{
			delete root; 
			root = NULL;	
		}	
		else if ( root -> left == NULL && root -> right != NULL )
		{
			temp = root;
			root = temp -> right;
			temp -> right = NULL;
			delete temp;
		}
			 
		else if ( root -> left != NULL && root -> right == NULL )
		{
			temp = root;
			root = temp -> left;
			temp -> left = NULL;
			delete temp;
		}
		else if ( root -> left != NULL && root -> right != NULL )
		{
			current = root -> right;
			while ( current -> left != NULL )
			{
				previous = current;
				current = current -> left;	
			}
			if ( current -> right != NULL )
			{
				previous -> left = current -> right;
			}
			current -> entry.copy_fromnode( root -> entry );	
			delete current;	
		}
		return 1;	
	}
	if ( strcmp( e_name, obj3.name ) < 0 )
		return remove_matchedevent( root -> left, e_name );
	return remove_matchedevent( root -> right, e_name ); 
	 
}
//this function copy an event from a node to an class object that passed in argument
void event::copy_fromnode( event & e )
{
	copy_from_node( e.data );	
}
//this function seperates the keywords of an events and stores it in an array

int BST::key_store( event_info & obj3, char **  array )
{
	int size = 10;
	int size_per_key = 25;
	int found = 0;
	char * temp = obj3.keys;
	temp = obj3.keys;
	for ( int i = 0; i < size; ++i )
	{
		if ( *temp == '\0' )
			return found;
		
//		array[i] = new char[25];	

		for ( int j = 0; j < size_per_key; ++j )
		{
			if ( *temp == '\0' || *temp == ',' )
				array[i][j] = '\0';
			else
			{
				array[i][j] = *temp;
				++temp;
			}
		}
		if ( *temp != '\0')
			temp += 2;
		++found;
	}
	
	return found;
}
//this function compares the given key by the user with the keys of an events
//ans returns an integer value
int BST::compare( char * user_key, event_info & obj3, char ** key_array )
{
	
	int num = key_store( obj3, key_array );
	for ( int i = 0; i < num; ++i )
	{
		cout << key_array[i] << endl;
		if( strcmp(user_key, key_array[i]) == 0 )
		return 1;
	} 
	return 0;
}

int BST::remove_allevent( char * user_key )
{
	return remove_all_matchedevent( root, user_key );
}
//this function removes all the event that has a matching key word passed by the user
//it also seperates the key word from the node and then using compoare function it then matches it
int BST::remove_all_matchedevent( node * & root, char * user_key )
{

	int ret = 0;
	event_info obj3;
	if ( ! root )
		return 0;	
	
	char ** key_array = new char* [10];	
	for( int i = 0; i< 10; ++i )
	{
		key_array[i] = new char[25];
	}
	root -> entry.copy_from_node(obj3);	
	int len = strlen( obj3.keys);		
	for ( int i =0; i < len; ++i )
	{
		obj3.keys[i] = toupper( obj3.keys[i]);	
	}
	node * temp = NULL;				
	node * current = NULL;
	node * previous = NULL;

	ret = remove_all_matchedevent( root -> left, user_key );

	ret = remove_all_matchedevent( root -> right, user_key ); 
	
	
	if ( compare( user_key, obj3, key_array  ))
	{
		if ( root ->left == NULL && root -> right == NULL )
		{
			delete root; 
			root = NULL;	
			++ret;
		}	
		else if ( root -> left == NULL && root -> right != NULL )
		{
			temp = root;
			root = temp -> right;
			temp -> right = NULL;
			delete temp;
			++ret;
		}
			 
		else if ( root -> left != NULL && root -> right == NULL )
		{
			temp = root;
			root = temp -> left;
			temp -> left = NULL;
			delete temp;
			++ret;
		}
		else if ( root -> left != NULL && root -> right != NULL )
		{
			current = root -> right;
			while ( current -> left != NULL )
			{
				previous = current;
				current = current -> left;	
			}
			if(previous == NULL)
			{	
				root -> right = current -> right;
			}

			else
			{
			previous -> left = current -> right;
			}
			current -> entry.copy_fromnode( root -> entry );	
			delete current;	
			++ret;					
		}
	}
	//if ( strcmp( user_key, obj3.name ) < 0 )
	for ( int i = 0; i < 10; ++i )
	{
	 	if( key_array[i] != NULL )
			delete [] key_array[i];		
	}
	delete [] key_array;
	return ret;	
}
//this function removes all the node from the tree an dset the riot to null
//it takes the root node's address  and delete alll the ifo
void BST::remove( node * & root )
{
	if( ! root )
		return;
	remove( root -> left );
	remove( root -> right );
	delete root;
	root = NULL;
	
}
//this function is the destructor of the BST class it deallocates all the used memory.
//it calls the remove function and performs the action required
BST::~BST()
{
	if ( root )
		remove( root );
}
