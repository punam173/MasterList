//This componet gives fetch the data for each recipe
//it didplays the recipe details in a seperate page
import React, {Component} from 'react';

const APP_ID = '47ec4575'
const APP_KEY = 'df22d4a0fc945d65f7d3916746025134'
const recipes = []
export default class RecipeDetails extends Component{
    constructor(props){
        super(props)
        this.state ={
            recipes:recipes,
            url: `https://api.edamam.com/search?q=${this.props.id}&app_id=${APP_ID}&app_key=${APP_KEY}&from=0&to=1`,
        }
        console.log(this.state.url)
    }
    async getRecipes() {
        try {
          const data = await fetch(this.state.url)
          console.log("The data is ")
          console.log(data)
          const jsonData = await data.json()
          console.log(jsonData)
          if (jsonData.hits.length === 0) {
            this.setState(() => {
              return { error: 'Sorry, but your search did not return any results' }
            })
          }
          else {
            this.setState(() => {
              return {recipes: jsonData.hits[0].recipe}
            })
          }
        } catch (error) {
          console.log(error);
        }
      }
      componentDidMount() {
        this.getRecipes()

      }
      
    render(){
        const{
                image,
                url,
                shareAs,
                label,
                ingredientLines
            } = this.state.recipes;
        console.log(this.state.recipes)
        console.log(this.state.recipes.ingredients)
        const { handleIndex } = this.props
        return (
        <React.Fragment>
          <div className = "container">
              <div className = "row">
                  <div className = "col-10 mx-auto col-md-6 my-3">
                  <button type='button' className='btn btn-warning mb-5 text-capitalize' onClick={() => handleIndex(1)}>
                  Back to Recipe List
                  </button>         
                  <img src = {image} className = "d-block w-100" alt = "recipe" />
                </div>
                    {/*details*/}
                    <div className = "col-10 mx-auto col-md-6 my-3">
                        <h6 className = "text-uppercase">{label}</h6>
                        <a
                                href={url}
                                target='_blank' rel='noopener noreferrer' className='btn btn-primary mt-2 text-capitalize'>Publisher Webpage</a>
                            <a
                                href={shareAs}
                                target='_blank' rel='noopener noreferrer' className='btn btn-success mt-2 mx-3 text-capitalize'>Recipe URL</a> 
                            <ul className='list-group mt-4'>
                                <h2 className='mt-3 mb-4'>Ingredients</h2>
                                {
                                    ingredientLines && ingredientLines.map((item, index) => {
                                        return (
                                            <li key={index} className='list-group-item text-slanted'>
                                                {item}
                                            </li>
                                        )
                                    })
                                }
                            </ul>                               
                    </div>
                  </div>
              </div>
         
        </React.Fragment>
        )
    }
}