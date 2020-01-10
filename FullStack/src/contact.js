//This component contains the layout for the contact form
//handles the event raised by the user using react handler
//takes name, email and message and the creator is notified with an email
//user third party mail server (smpt server as a host to connect to gmail)
import React, {Component} from 'react';
import axios from 'axios';

class Contact extends Component{
    handleSubmit(e){
        e.preventDefault();
        const name = document.getElementById('name').value;
        const email = document.getElementById('email').value;
        const message = document.getElementById('message').value;
        const subject = document.getElementById('subject').value;
        axios({
            method: "POST", 
            url:"http://localhost:3002/send", 
            data: {
                name: name,   
                email: email,  
                subject: subject,
                messsage: message,
            }
        }).then((response)=>{
            if (response.data.msg === 'success'){
                alert("Message Sent."); 
                this.resetForm()
            }else if(response.data.msg === 'fail'){
                alert("Message failed to send.")
            }
        })
    }

    resetForm(){
        document.getElementById("contact").value ="";
    }

    render(){
        return(
           
            <section id="contact" className="section-bg wow fadeInUp">
    <div className="container">

      <div className="section-header">
        <h3>Contact Us</h3>
        
      </div>

      <div className="row contact-info">

        <div className="col-md-6">
          <div className="contact-address">
            <i className="ion-ios-location-outline"></i>
            <h3>Address</h3>
            <address>1977 SW 6th Ave #210, Portland, OR 97201</address>
          </div>
        </div>

       

        <div className="col-md-6">
          <div className="contact-email">
            <i className="ion-ios-email-outline"></i>
            <h3>Email</h3>
            <p><a href="mailto:info@example.com">info@example.com</a></p>
          </div>
        </div>

      </div>

      <div className="form">
        <div id="sendmessage">Your message has been sent. Thank you!</div>
        <div id="errormessage"></div>
        <form id = "contact" action="" method="post" role="form" className="contactForm">
          <div className="form-row">
            <div className="form-group col-md-6">
              <input type="text" name="name" className="form-control" id="name" placeholder="Your Name" data-rule="minlen:4" data-msg="Please enter at least 4 chars" />
              <div className="validation"></div>
            </div>
            <div className="form-group col-md-6">
              <input type="email" className="form-control" name="email" id="email" placeholder="Your Email" data-rule="email" data-msg="Please enter a valid email" />
              <div className="validation"></div>
            </div>
          </div>
          <div className="form-group">
            <input type="text" className="form-control" name="subject" id="subject" placeholder="Subject" data-rule="minlen:4" data-msg="Please enter at least 8 chars of subject" />
            <div className="validation"></div>
          </div>
          <div className="form-group">
            <textarea className="form-control" name="message" id="message" rows="5" data-rule="required" data-msg="Please write something for us" placeholder="Message"></textarea>
            <div className="validation"></div>
          </div>
          <div className="text-center"><button type="button" onClick={this.handleSubmit.bind(this)}>Send Message</button></div>
        </form>
      </div>

    </div>
  </section>
        )
      
    }
}

export default Contact;