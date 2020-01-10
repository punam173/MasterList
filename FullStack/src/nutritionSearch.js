//This component implements the code to get the nutrition
//it get sthe response in the specified field in the react contructor
import React,{Component} from 'react';
//permission to access the URL
const appId = '47379841';
const appKey = 'd28718060b8adfd39783ead254df7f92';
const url = `https://api.edamam.com/api/nutrition-details?app_id=${appId}&app_key=${appKey}`;
const nutrition = [];

class NutritionSearch extends Component{

    constructor(props) {
        super(props);
        this.state = { 
          input: nutrition,        
          calories: null,
          dietLabels:null,
          healthLabels: null,
          totalDaily: null,
          totalNutrients: null,
          totalWeight: null,
          uri: null,
          yield: null,
          ingredients:[]
      
        };
        this.onTextChange = this.onTextChange.bind(this); 
        this.onSearch=this.onSearch.bind(this);
        this.onRest=this.onRest.bind(this);
        this.baseState = this.state ;
         
      }
  
      onSearch = async(e) => {
        const response = await fetch(url, {
          method: 'post',
          body: JSON.stringify({"ingr":this.state.input}),
          headers: {
              'Accept-Language': 'en-US,en;q=0.9',
              'Content-Type': 'application/json', 
          }
      });
  
      const data = await response.json();
      if (data.error) {
        alert ('Please enter correct data');
            this.setState({ output:   data.error}); 
            this.setState({calories: 0});
            this.setState({totalWeight: 0});
            this.setState({uri: ''});
            this.setState({yield: 0});
        }
        else{
  
      console.log(data);
        this.setState({ output:   data.calories}); 
        this.setState({calories: data.calories});
        this.setState({dietLabels: data.dietLabels});
        this.setState({healthLabels: data.healthLabels});
        this.setState({totalDaily: Object.keys(data.totalDaily).map((key) => ({ key, value: data.totalDaily[key] }))});
        this.setState({totalNutrients: Object.keys(data.totalNutrients).map((key) => ({ key, value: data.totalNutrients[key] }))});
        this.setState({totalWeight: data.totalWeight});
        this.setState({uri: data.uri});
        this.setState({yield: data.yield});
        this.setState({ingredients: data.ingredients});
  
         
        } 
      }
      onTextChange = (e) => {
        var array = e.target.value.split(',');
  
        this.setState({ input: array})
      }
  
      onRest = (e) => { 
        this.setState(this.baseState);
      }    
render(){
   
  return (
    
    <div className="root" id="nutritionSearch">
        <div className="container"> 
            <div class="card-header" >
                <label><b>Know the Nutritional Content</b></label>
            </div>
        <div class="card-body">    
            <div className="form-row">
                <div className="form-group col-md-8">   
                    <div className="form-group">
                        <textarea
                            cols="30"
                            rows="7"
                            className="form-control"
                            placeholder="Enter Ingredients/Recipe Eg: 1 cup rice, 1 avocado "
                            onChange={this.onTextChange}
                        />
                    </div>
                    <div className="form-group">
                        <input
                            type="button"
                            value="Search"
                            className="btn btn-primary"
                            onClick={this.onSearch}
                        />
     
                        <input
                            type="button"
                            value="Reset"
                            className="btn primary"
                            onClick={this.onRest}
                        />
                    </div>
                    <div className="form-group" id="outtext">
                        <React.Fragment>
                                <table class="table">  
                                    <thead>    
                                        <tr>     
                                            <th>Qty</th>      
                                            <th>Unit</th>     
                                            <th>Food</th>     
                                            <th>Calories</th>	 
                                            <th>Weight</th>    
                                        </tr> 
                                    </thead>  
                                    <tbody>
                                        {this.state.ingredients.map((item, key) =>
                                            <tr>   
                                                <th>{item.parsed[0].quantity}</th>  
                                                <th>{item.parsed[0].measure}</th> 
                                                <th>{item.parsed[0].food}</th>   
                                                <th>{item.parsed[0].nutrients.ENERC_KCAL.quantity}</th>   
                                                <th>{item.parsed[0].weight}</th></tr>
                                            )
                                        }    
                                    </tbody>
                                </table>
                            </React.Fragment>
                        </div>
                    </div>
                <div >   
                <section class="performance-facts" id="performance-facts">	
                    <div class="performance-facts__header">	
                        <h1 class="performance-facts__title">Nutrition Facts</h1>		
                            <p><span id="lnumser">0</span>
                                servings per container
                            </p>	
                    </div>	
                    <table class="performance-facts__table">		
                        <thead>		
                            <tr>				
                                <th colspan="3" class="amps">Amount Per Serving</th>	
                            </tr>		
                        </thead>	
                        <tbody>			
                            <tr>		
                                <th colspan="2" id="lkcal-val-cal"><b>Calories</b></th>			
                                <td class="nob"> {this.state.output}</td>		
                            </tr>			
                                <tr class="thick-row">	
                                    <td colspan="3" class="small-info"><b>% Daily Value*</b></td>	
                                </tr>			
                                <tr>	
                                    <th colspan="2"><b>Total Fat</b> 1.1 g</th>		
                                    <td><b>2 %</b></td>			
                                </tr>	
                                <tr>			
                                    <td class="blank-cell"></td>			
                                    <th>Saturated Fat 0.3 g</th>	
                                    <td><b>2 %</b></td>	
                                </tr>			
                                <tr>	
                                    <td class="blank-cell"></td>
                                    <th>Trans Fat -</th>				
                                    <td></td>			
                                </tr>			
                                <tr>				
                                    <th colspan="2"><b>Cholesterol</b> -</th>				
                                    <td><b>-</b></td>			
                                </tr>			
                                <tr>				
                                    <th colspan="2"><b>Sodium</b> 2 mg</th>				
                                    <td><b>0 %</b></td>			
                                </tr>			
                                <tr>				
                                    <th colspan="2"><b>Total Carbohydrate</b> 154.7 g</th>				
                                    <td><b>52 %</b></td>			
                                </tr>			
                                <tr>				
                                    <td class="blank-cell"></td>				
                                    <th>Dietary Fiber -</th>				
                                    <td><b>-</b></td>			
                                </tr>			
                                <tr>				
                                    <td class="blank-cell"></td>				
                                    <th>Total Sugars -</th>				
                                    <td></td>			
                                </tr>			
                                <tr>				
                                    <td class="blank-cell"></td>				
                                    <th>Includes - Added Sugars</th>				
                                    <td></td>			
                                </tr>			
                                <tr class="thick-end">				
                                    <th colspan="2"><b>Protein</b> 12.9 g</th>				
                                    <td><b>26 %</b></td>			
                                </tr>		
                                
                        </tbody>	
                        </table>	
                    <table class="performance-facts__table--grid">		
                        <tbody>			
                            <tr>				
                                <th>Vitamin D -</th>				
                                <td><b>-</b></td>			
                            </tr>			
                            <tr>				
                                <th>Calcium 17.6 mg</th>				
                                <td><b>2 %</b></td>			
                            </tr>			
                            <tr>				
                                <th>Iron 1.6 mg</th>				
                                <td><b>9 %</b></td>			
                            </tr>			
                            <tr class="thin-end">				
                                <th>Potassium 167.7 mg</th>				
                                <td><b>4 %</b></td>			
                            </tr>		
                        </tbody>
                        
                    </table>	
                    <p class="small-info" id="small-nutrition-info">
                        *Percent Daily Values are based on a 2000 calorie diet
                    </p>
                </section>
                </div>
            </div>
        </div>
    </div>
    </div>  
  );
}
}
export default NutritionSearch;
