//Layout for the page that contains the three services
//Recipe search, Nutrition finder and BMi camculation
import React from 'react';
function Service() {
  return (
    <section id="services">
    <div className="container">

      <header className="section-header">
        <h3>Services</h3>
        <p>Here we help you giving suggestions of different recipes with ingredients of your choice, 
        so that you can plan you diet with ease. Apart from convenience in preparing what you eat an 
        individual must always know the details about the meal.
        </p>
      </header>

      <div className="row about-cols">
        <div className="col-md-4 wow fadeInUp">
          <div className="about-col">
            <div className="img">
              <img src="img/about-mission.jpg" alt="" className="img-fluid" />
              <div className="icon"><i className="ion-ios-speedometer-outline"></i></div>
            </div>
            <h2 className="title"><a href="#recipeSearchApp">Recipe Explorer</a></h2>
            <p>
              Help your culinary imagination with any ingredient of your choice and make preparing 
              food fun and just like a breeze with our recipe finder. It gives you an array of delicious 
              and lips smacking options that you can choose and make your food experience worth it. 
            </p>
          </div>
        </div>

        <div className="col-md-4 wow fadeInUp" data-wow-delay="0.1s">
          <div className="about-col">
            <div className="img">
              <img src="img/about-plan.jpg" alt="" className="img-fluid" />
              <div className="icon"><i className="ion-ios-list-outline"></i></div>
            </div>
            <h2 className="title"><a href="#nutritionSearch" class="btn-get-started scrollto">Nutrition Finder</a></h2>
            <p>
              Know your food better to have a plan your meal beeter so that you have a broader vision of 
              what you are consuming and get the knowleged of all the nutrients that your food has. Add 
              your choice of ingredients to know how much claories, vitamins and other goodnedd is in there.
            </p>
          </div>
        </div>
        <div className="col-md-4 wow fadeInUp" data-wow-delay="0.1s">
          <div className="about-col">
            <div className="img">
              <img src="img/about-vision.jpg"alt="" className="img-fluid" />
              <div className="icon"><i className="ion-ios-list-outline"></i></div>
            </div>
            <h2 className="title"><a  href="#bmi"  class="btn-get-started scrollto">Know your BMI</a></h2>
            <p>
              Find about your weight with respect to your height by knowing your BMI(Body Mass Index). 
              National Institute of Health(NHI) now defines normal weight, overweight and obesity according 
              to BMI rather than traditional height/weight charts. Find yours now!
              </p>
          </div>
        </div>


      </div>

    </div>
  </section>
  );
}

export default Service;