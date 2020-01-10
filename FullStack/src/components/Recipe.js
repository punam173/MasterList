//This component implements the code to get the recipe 
//it fives the recepie list with url details and recipe details
import React, {Component} from 'react'

export default class Recipe extends Component{
    render(){
        const{
            label,
            image,
            url,
            source
        } = this.props.recipe.recipe;  
    
        const {handleDetails} = this.props

        return(
            
        <React.Fragment>
           <div className="col-10 mx-auto col-md-6 col-lg-4 my-3">
                <div className = "card">
                <img src= {image}             
                className = "img-card-top"
                style = {{height:"14rem"}}
                alt = "recipe" />
            <div className = "card-body text-capitalize">
            <h6>{label}</h6>
            <h6 className = "text-warning text-slanted">
               Published by - {source}
            </h6>
            </div>
            <div className  ="card-footer">
            <button type='button' className='btn btn-primary text-capitalize' 
                                onClick={() => handleDetails (0,label)}>Details</button>
            <a href ={url} className = "btn btn-success mx-2 text-capitalize"
                 target = "_blank" rel="noopener noreferrer" >
                 recipe url 
                </a>
            </div>
            </div>
           </div>
        </React.Fragment>
        )
    }
}