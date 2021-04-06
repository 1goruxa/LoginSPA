import React from 'React'


export default class Login extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            userName: '',
            userPassword: '',
            isOpen: true,
            loggedIn: false,
            userOnline: {
                userName: '',
                userEmail: '',
                userStatus: '',
                userBirthday: ''
            }
        };
    
        this.handleChangeName = this.handleChangeName.bind(this);
        this.handleChangePassword = this.handleChangePassword.bind(this);
        this.tryLogin = this.tryLogin.bind(this);
        this.sendStatusToApp = this.sendStatusToApp.bind(this);
      }

      sendStatusToApp =  () => { 
        this.props.updateOnlineUser(this.state.loggedIn, this.state.userOnline)
        }

      tryLogin(event) {
        this.setState({ loggedIn: false })  
        const requestString = "/login/?name=" + this.state.userName + "&password=" + this.state.userPassword;  
        fetch(requestString)
          .then(res => res.json())
          .then(
            (result) => {
                if (result.result){
                    this.setState({loggedIn:true})
                    this.state.userOnline.userName = result.userResponse.name;
                    this.state.userOnline.userEmail = result.userResponse.email;
                    this.state.userOnline.userStatus = result.userResponse.status;
                    this.state.userOnline.userBirthday = result.userResponse.birthday;
                }
                else{
                    alert(result.error)
                }
                this.sendStatusToApp()
            },     
            
          )
          this.state.userName = '';
          this.state.userPassword = '';
          event.preventDefault();
    }
    
      handleChangeName(event) {
        this.setState({userName: event.target.value});
      }

      handleChangePassword(event) {
        this.setState({userPassword: event.target.value});
      }

      
    render(){
        return(
          <React.Fragment>
              { this.props.isLoginOpened && (
                <div>
                    <div>
                        <h1>Login page</h1>
                        <p>Please login</p>
                        
                        <form style = {{ marginBottom: '1rem' }} onSubmit={this.tryLogin}>
                            <p>
                                Name:
                                <input type="text" id='name' value={this.state.userName} onChange={this.handleChangeName}/>
                            </p>
                            <p>
                                Password:
                                <input type="password" id='password' value={this.state.userPassword} onChange={this.handleChangePassword}/>
                            </p>
                            <button type='submit' id='login'>Login</button>
                            
                        </form>
                    </div>
                </div>
              )}
            </React.Fragment>               
         
        )
    }
}
           