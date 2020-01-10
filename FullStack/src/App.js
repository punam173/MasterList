//Represents the linking of nav menu elements to 
//it's specific page
import React from 'react';

function App() {
  
  return (
    <header id="header">
        <div className="container-fluid">
          <div id="logo" className="pull-left">
            <h1><a href="#intro" className="scrollto">FoodSmart</a></h1>      
          </div>
        <nav id="nav-menu-container">
          <ul className="nav-menu">
            <li className="menu-active">
              <a href="#intro">Home</a>
            </li>
            <li>
              <a href="#about">About Us</a>
            </li>
            <li>
              <a href="#services">Services</a>
            </li>
            <li>
              <a href="#contact">Contact</a>
            </li>  
          </ul>
        </nav>
        </div>
    </header> 
   
  );
}
export default App;
