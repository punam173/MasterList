//This componet represents the main page with different pictures swiping
//used caraousel features to get the effect
//have four items and our content written over it
import React from 'react';
function Home() {
  return (
    <section id="intro">
        <div className="intro-container">
            <div id="introCarousel" className="carousel  slide carousel-fade" data-ride="carousel">
                <ol className="carousel-indicators "></ol>
                <div className="carousel-inner" role="listbox">
                    <div className="carousel-item active">
                        <div className="carousel-background">
                            <img src="img/intro-carousel/1.jpg" alt="" />
                        </div>
                    <div className="carousel-container">
                        <div className="carousel-content">
                            <h2 id="sumu">"A healthy outside <br/>starts from the inside"</h2> 
                        </div>
                    </div>
                </div>

                <div className="carousel-item">
                    <div className="carousel-background">
                        <img src="img/intro-carousel/2.jpg" alt="" />
                    </div>
                    <div className="carousel-container">
                        <div className="carousel-content">
                            <h2 id="sumu">"Take care of your body.<br/>It's the only place you have to live"</h2>
                        </div>
                    </div>
                </div>

                <div className="carousel-item">
                    <div className="carousel-background">
                        <img src="img/intro-carousel/3.jpg" alt="" />
                    </div>
                    <div className="carousel-container">
                        <div className="carousel-content">
                            <h2 id="sumu">"Your diet is a bank account.<br/>Good food choices are good investments"</h2>
                        </div>
                    </div>
                </div>

                <div className="carousel-item">
                    <div className="carousel-background">
                        <img src="img/intro-carousel/4.jpg" alt="" />
                    </div>
                    <div className="carousel-container">
                        <div className="carousel-content">
                            <h2 id="sumu">"Fuel your body for the win"</h2>    
                        </div>
                    </div>
                </div>
            </div>

            <a className="carousel-control-prev" href="#introCarousel" role="button" data-slide="prev">
                <span className="carousel-control-prev-icon ion-chevron-left" aria-hidden="true"></span>
                <span className="sr-only">Previous</span>
            </a>

            <a className="carousel-control-next" href="#introCarousel" role="button" data-slide="next">
                <span className="carousel-control-next-icon ion-chevron-right" aria-hidden="true"></span>
                <span className="sr-only">Next</span>
            </a>

        </div>
    </div>
   
 </section>
  );
}

export default Home;