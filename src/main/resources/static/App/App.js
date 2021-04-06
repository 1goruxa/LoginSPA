import React from "react";
import ReactDom from "react-dom";
import Login from './Login/Login'
import Details from './Details/Details'

class App extends React.Component {
    state = {
        isLogin: false,
        isLoginOpened: true,
        isDetailsOpened: false,
        userOnline: {
            userName: '',
            userEmail: '',
            userStatus: '',
            userBirthday: ''
        }
      };
    
    updateOnlineUser = (dataFromLogin, user) => {
        this.state.userOnline.userName = user.userName;
        this.state.userOnline.userEmail = user.userEmail;   
        this.state.userOnline.userStatus = user.userStatus;   
        this.state.userOnline.userBirthday = user.userBirthday;       
        this.state.isLogin = dataFromLogin

        if (this.state.isLogin){
            this.state.isLoginOpened = false;
            this.state.isDetailsOpened = true;
        }
        else{
            
            this.state.isLoginOpened = true;
            this.state.isDetailsOpened = false;
        }
        ReactDom.render(<App />, document.getElementById('react'));      
     }

    render() {
        return (
            <div>
                <Login updateOnlineUser={this.updateOnlineUser} isLoginOpened={this.state.isLoginOpened}/>
                <Details updateOnlineUser={this.updateOnlineUser} isDetailsOpened={this.state.isDetailsOpened} userOnline={this.state.userOnline}/>
            </div>
        )
    }
}

ReactDom.render(<App />, document.getElementById('react'));