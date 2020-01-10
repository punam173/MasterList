//This component contains the code for calculating BMI given the input
//by the user. It handles the request using the react handler
//Takes weight in lbs and height in feet and inches
//Results also include if the value is underweight, normal or over weight
import React,{Component} from 'react';

class BMI extends Component {

    constructor(props) {

        super(props);
        this.state = { 
          wt: '',
          feet: '',
          inch:'',
          result: '0',
          answer:'',
          meaning:''
        };

        this.actionWtInputChange = this.actionWtInputChange.bind(this);
        this.actionFeetInputChange = this.actionFeetInputChange.bind(this);
        this.actionInchInputChange = this.actionInchInputChange.bind(this);
        this.actionResult=this.actionResult.bind(this);    
      }

      actionWtInputChange = (e) => {
        this.setState({ wt: Number(e.target.value)})
      }
      actionFeetInputChange = (e) => {
        this.setState({ feet: Number(e.target.value)})
      }
      actionInchInputChange = (e) => {
        this.setState({ inch: Number(e.target.value)})
      }
      actionResult = (e) => {

        e.preventDefault();
        let kg = this.state.wt * (0.4536); 
        let f1= this.state.feet * 12; 
        let inches = f1 + this.state.inch;
        let totalh = inches * 0.0254; 
        let x=kg / (totalh*totalh);

        this.setState({ result: x.toFixed(1) });
        this.setState({ answer: 'Your Calculated BMI: '+x.toFixed(1) }); 

        if(x< 18.5){
            this.setState({ meaning: 'Your BMI results indicate that you are underweight' });
           
        }else if(x > 18.5 && x< 25){
            this.setState({ meaning: 'Your BMI results indicate that you are healthy' });
          
        }else if (x > 25){
            this.setState({ meaning: 'Your BMI results indicate that you overweight' });
           
        }else {
            this.setState({ meaning: '  Please Fill in everything correctly' }); 
        }
      }  

render() {

  return (
    <section id="bmi">
        <div className="container">
            <div className="form-row">
                <div className="form-group col-md-6">
            </div>
            <div className="form-group col-md-6">
                <div class="card text-center">
                    <div class="card-header text-left">
                        <label><b>Know Your BMI</b></label>
                    </div>
                    <div class="card-body">
                        <div className="form">
                            <form action="" name="calform"  method="post" role="form" className="contactForm">
                                <div className="form-group">
                                    <input type="text" 
                                        onChange={this.actionWtInputChange}  
                                        className="form-control" 
                                         name="wt" 
                                        id="weight" 
                                        placeholder="Weight in lbs"  
                                        data-msg="Please enter Weight in lbs" />
                                <div className="validation"></div>
                                </div>
                                <div className="form-row">
                                    <div className="form-group col-md-6">
                                        <input type="text" 
                                            name="feet" 
                                            onChange={this.actionFeetInputChange} 
                                            className="form-control" id="feet" 
                                            placeholder="Height in feets"   
                                            data-msg="Please enter Height" />
                                        <div className="validation"></div>
                                    </div>
                                <div className="form-group col-md-6">
                                    <input type="text" 
                                        className="form-control" 
                                        name="inch" 
                                        onChange={this.actionInchInputChange} 
                                        id="inch" 
                                        placeholder="inches"   
                                        data-msg="Please enter a inch " />
                                    <div className="validation"></div>
                                </div>
                            </div>
        
                                <div className="text-center">
           
                                    <button type="button"  
                                        onClick={this.actionResult} >Submit</button></div>
                                <br />
                            <div className="form-group">
                            <h4  ><b>{this.state.answer}</b></h4>
                            <h5  >{this.state.meaning}</h5> 
                        </div>
                    </form>
                </div>
            </div>
            </div>
        </div>
    </div>
   </div>
    </section>
  );
}
}
export default BMI;