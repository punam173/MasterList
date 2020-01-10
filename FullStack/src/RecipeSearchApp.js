//This component implements the code to get the recipes
//It gives the list of the search recipe for a given ingredients
import React, {Component} from "react";
 
import RecipeList from './components/RecipeList';
import RecipeDetails from './components/RecipeDetails';


const recipes = []
const APP_ID = '47ec4575'
const APP_KEY = 'df22d4a0fc945d65f7d3916746025134'

class RecipeSearchApp extends Component{
  state = {
    recipes :  recipes,
    url: `https://api.edamam.com/search?q=banana%20walnut&app_id=${APP_ID}&app_key=${APP_KEY}&from=0&to=9`,
    pageIndex:1,
    label : '',
    base_url: `https://api.edamam.com/search?q=chicken&app_id=${APP_ID}&app_key=${APP_KEY}&from=0&to=9`,    
    search: "",
    error: ''
  };

  async getRecipes() {
    try {
      const data = await fetch(this.state.url)
      const jsonData = await data.json()
      console.log(jsonData)
      if (jsonData.hits.length === 0) {
        this.setState(() => {
          return { error: 'Sorry, but your search did not return any results' }
        })
      }
      else {
        this.setState(() => {
          return {recipes: jsonData.hits}
        })
      }
    } catch (error) {
      console.log(error);
    }
  }
  componentDidMount() {
    this.getRecipes()
  }

  displayPage = (index) => {
    switch (index) {
      default:
      case 1:
        return (
          <RecipeList
            recipes={this.state.recipes}
            handleDetails={this.handleDetails}
            value={this.state.search}
            handleChange={this.handleChange}
            handleSubmit={this.handleSubmit}
            error={this.state.error}
          />)
      case 0:
        return (
          <RecipeDetails
            id={this.state.label.replace(/ /g, '%20')}
            ingredients = {this.state.ingredientLines}
            handleIndex={this.handleIndex}
          />)
    }
  }


  handleIndex = (index) => {
    this.setState({
      pageIndex: index
    })
  }

  handleDetails = (index, id) => {
    this.setState({
      pageIndex: index,
      label: id
    })
  }

  handleChange = (e) => {
    this.setState({
      search: e.target.value
    })
  }

  handleSubmit = (e) => {
    e.preventDefault()
    const { search } = this.state
    this.setState(() => {
      return { url: `https://api.edamam.com/search?q=${search}&app_id=${APP_ID}&app_key=${APP_KEY}&from=0&to=9`, search: "" }
    }, () => {
      this.getRecipes()
    })
  }

  render() {
    console.log(this.state.label)
    return (
      <section id="recipeSearchApp">
      <div className="container">
      <React.Fragment>
        {this.displayPage(this.state.pageIndex)}
      </React.Fragment>
      </div>
      </section>
    )
  }
}
export default RecipeSearchApp;