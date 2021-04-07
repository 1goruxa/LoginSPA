import React from 'React'



export default class Details extends React.Component {
   
    constructor(props) {
        super(props);
        this.state = {
            userName: '',
            userEmail: '',
            userBirthday: '',
            initFlag: true,
            users: [],
            selectedUserName: 'admin',
            openNew: false,
            openManage: false,
            oldManageName:''
        
        };
        
        this.handleChangeName = this.handleChangeName.bind(this);
        this.handleChangeEmail = this.handleChangeEmail.bind(this);
        this.handleChangeBirthday = this.handleChangeBirthday.bind(this);
        this.handleSelectedUser = this.handleSelectedUser.bind(this);
        this.openNewForm = this.openNewForm.bind(this);
        this.manageUser = this.manageUser.bind(this);
    }

    handleSelectedUser = (event) => {
        this.setState({selectedUserName: event.target.value });
    }

    handleChangeName(event) {
        this.setState({userName: event.target.value});
    }

    handleChangeEmail = (event) => {
        this.setState({userEmail: event.target.value});
    }

    handleChangeBirthday = (event) => {
        this.setState({userBirthday: event.target.value});
    }

    onClickEdit(option){
        document.getElementById(option).disabled=false;    
    }

    manageUser =  () => {
        this.setState({openManage: true});
        fetch("/getuser?name=" + this.state.selectedUserName)
        .then(response => response.json())
            .then(data => {
                document.getElementById('manage_name').value = data.name;
                document.getElementById('manage_email').value = data.email;
                document.getElementById('manage_date_of_birth').value = data.birthday;
                this.setState({oldManageName: data.name})
            })
    }

    openNewForm = () => {
        this.setState({openNew : true});
    }

    saveUserChanges = () => {
        fetch('/update', {
            method: "POST",
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              oldName: this.props.userOnline.userName,   
              name: document.getElementById('name').value,
              email: document.getElementById('email').value,
              birthday: document.getElementById('birthday').value
            })})
          .then(response => response.json())
          .then(data => {
            if (data){
                alert("User updated")
            }
            else{
                alert("User not updated: " + data.error)
            }  
          }); 
        }
      
    saveUserChangesFromManage = () => {
    fetch('/update', {
        method: "POST",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            oldName: this.state.oldManageName,   
            name: document.getElementById('manage_name').value,
            email: document.getElementById('manage_email').value,
            birthday: document.getElementById('manage_date_of_birth').value
        })})
        .then(response => response.json())
        .then(data => {
        if (data){
            alert("User updated")
            
        }
        else{
            alert("User not updated: " + data.error)
        }  
        });  
        this.setState({openManage: false});  

    }

    logoutUser = () => {
        fetch('/logout', {
            method: "POST",
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
              name: this.props.userOnline.userName,
            })})
          .then(response => response.json())
          .then(data => {
            console.log('Logout status: ', data);    
          });  
        
        this.props.updateOnlineUser(false, this.props.userOnline.userName)
    }  

    getAll = () => {
        fetch('/getall?status=' + this.props.userOnline.userStatus)
            .then(response => response.json())
            .then(data => {
                if(data.result){
                    this.setState({users: data.users})
                }
                else{
                    alert("Can't get user list")
                }
            });

    }

    addNew = () => {
        console.log("trying to add user")
        fetch('/addnew', {
            method: "POST",
            headers: {
              'Accept': 'application/json',
              'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                status: this.props.userOnline.userStatus, 
                name: document.getElementById('new_name').value,
                email: document.getElementById('new_email').value,
                password: document.getElementById('new_password').value,
                birthday: document.getElementById('new_date_of_birth').value 
            })})
          .then(response => response.json())
          .then(data => {
            if (data.result){
                alert("New user added")
            }
            else{
                alert("User not added: " + data.error)
            }
            this.setState({ openNew : false });
          });  
    }

    componentDidUpdate() {
        if (this.state.userName != this.props.userOnline.userName 
                && this.state.userEmail != this.props.userOnline.userEmail
                && this.state.userBirthday != this.props.userOnline.userBirthday
                && this.state.initFlag){
            this.setState({userName: this.props.userOnline.userName});
            this.setState({userEmail: this.props.userOnline.userEmail});
            this.setState({userBirthday: this.props.userOnline.userBirthday});
            this.setState({initFlag : false});
            this.getAll()
        }
        
      }

    render(){
        return(
          <React.Fragment>
            { this.props.isDetailsOpened && (
                <div>
                    <p>User Details</p>
                    <div>
                        <div>
                            <form style = {{ marginBottom: '1rem' }}>
                                <p>
                                    Status: <input type="text" disabled value={this.props.userOnline.userStatus} /> 
                                </p>
                                <p>
                                    Name: <input type="text" id='name'
                                            disabled
                                            value={this.state.userName} 
                                            onChange={this.handleChangeName}
                                            />
                                    <button type='button' onClick={() => {this.onClickEdit("name")}}>&#9998;</button>
                                </p>
                                <p>
                                    Email: <input type="text" id='email' 
                                            disabled 
                                            value={this.state.userEmail} 
                                            onChange={this.handleChangeEmail}
                                            />
                                    <button type='button' onClick={() => {this.onClickEdit("email")}}>&#9998;</button>
                                </p>
                                <p>
                                    Birthday: <input type="date" id='birthday' 
                                            disabled 
                                            value={this.state.userBirthday} 
                                            onChange={this.handleChangeBirthday}
                                            />
                                    <button type='button' onClick={() => {this.onClickEdit("birthday")}}>&#9998;</button>
                                </p>
                                <button type='button' onClick={this.saveUserChanges}>Save</button>
                            </form>
                                <button onClick={this.logoutUser}>Exit</button>
                        </div>
                    </div>
                </div>
            )}
            {this.props.userOnline.userStatus === 'Admin' && (
                <div>
                    For Admins only:
                    <div>
                        <p>
                        <button onClick={this.manageUser}>Manage User</button> 
                        <select value={this.state.selectedUserName} onChange={this.handleSelectedUser}>  
                            { this.state.users.map(user => (
                                <option key={user.name}>
                                    {user.name}
                                </option>
                                )
                            ) 
                            }
                        </select>
                        </p>
                    </div>
                    <p>
                        <button onClick={this.openNewForm} id='new_user'>New User</button>
                    </p>
                </div>
                )}    
            {this.state.openNew && (
            <div>    
                <p>
                    <input type='text' id='new_name' placeholder='Enter name' />
                </p>
                <p>
                    <input type='text' id='new_email' placeholder='Enter Email' />
                </p>
                <p>
                    <input type='password' id='new_password' placeholder='Enter password' />
                </p>
                <p>
                    Date of birth:
                    <br />
                    <input type='date' id='new_date_of_birth' />
                </p>
                <button onClick={this.addNew} id='add_user'>Add User</button>     
            </div>
            )}
            {this.state.openManage && (
            <div>    
                <p>Name:
                    <input type='text' id='manage_name' />
                </p>
                <p>E-mail:
                    <input type='text' id='manage_email' />
                </p>
                <p>
                    Date of birth:
                    <br />
                    <input type='date' id='manage_date_of_birth' />
                </p>
                <button onClick={this.saveUserChangesFromManage}>Update User</button>     
            </div>
            )}

          </React.Fragment>
        )
    }
}

