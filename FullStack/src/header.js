//Represents the menu bar and is the first part of the page
//contains nav elemets
//used built in libraries to get the effect of scrolling
import React from 'react';
function Header() {
  return (
    <div class="parent">
        <div className="container-fluid">
            <div id="logo" className="pull-left">
                <h1><a href="#intro" className="scrollto">FoodSmart</a></h1>      
            </div>
            <nav id="nav-menu-container">
                <ul className="nav-menu">
                    <li className="menu-active"><a href="#intro">Home</a></li>
                    <li><a href="#about">About Us</a></li>
                    <li><a href="#services">Services</a></li>
                    <li><a href="#contact">Contact</a></li>
                </ul>
            </nav>
         </div>
      
    </div>
  );
}
export default Header;