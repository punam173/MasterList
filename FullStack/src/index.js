//This component describe the layout of all the react component
//it renders all the pages into the html file using id root
//imports all the required components and tools
import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import App from './App';
import * as serviceWorker from './serviceWorker';

 
import Home from './home';
import Service from './services'; 
import About from './about';
import Contact from './contact';
import BMI from './bmi';
import NutritionSearch from './nutritionSearch';
import RecipeSearchApp from './RecipeSearchApp';
import Footer from './footer';

 
const appLink=(
<div>
<App />
<Home />
<About />
<Service />
<BMI />
<NutritionSearch />
<RecipeSearchApp />
<Contact />
<Footer />

</div>

)



 // ReactDOM.render(routing, document.getElementById('root'))

ReactDOM.render(appLink, document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
