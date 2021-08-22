import React,{Component} from 'react';
import '../css/dreamweaver4.css';

var reg = new RegExp("^[a-z0-9]+([._\\-]*[a-z0-9])*@([a-z0-9]+[-a-z0-9]*[a-z0-9]+.){1,63}[a-z0-9]+$");

class Register extends Component{
    constructor(props){
        super(props);
        this.state={
            username:"",
            password:"",
            repassword:"",
            email:""
        }
    }

    changeUsername(e){
        this.setState({username:e.target.value});
    }

    changePassword(e){
        this.setState({password:e.target.value});
    }

    changeRepassword(e){
        this.setState({repassword:e.target.value});
    }

    changeEmail(e){
        this.setState({email:e.target.value});
    }

    submitForm(){
        if(this.state.username===""){
            alert("username cannot be empty!");
        }else if(this.state.password===""){
            alert("password cannot be empty!");
        }else if(this.state.repassword!=this.state.password){
            alert("the repeat password is not correct!");
        }else if(!reg.test(this.state.email)){
            alert("the email is not correct!");
        }else{
            fetch("/RegisterController/register?username=" + this.state.username + 
            "&password=" + this.state.password + 
            "&repassword=" + this.state.repassword + 
            "&email=" + this.state.email,{
                method:'post',
                mode:"cors",
                credentials:"include"
            }).then(function(res){
                return res.json();
            }).then(function(json){
                if(json.error=="noError"){
                    alert("you register successfully!");
                    window.location.href="/";
                }else if(json.error==="username"){
                    alert("the username already exist!");
                }else if(json.error==="email"){
                    alert("the e-mail already exist!");
                }
            }).catch(e => console.log('error:', e)); 
        }
    }

    render(){
        return(
                <div id="container1">
                        <form id="form1">
                            username
                            <br/>
                            <input type="text" id="username" name="username" onChange={this.changeUsername.bind(this)}/>
                            <br/>
                            password:
                            <br/>
                            <input type="text" id="password" name="password" onChange={this.changePassword.bind(this)}/>
                            <br/>
                            repeat password:
                            <br/>
                            <input type="text" id="repassword" name="repassword" onChange={this.changeRepassword.bind(this)}/>
                            <br/>
                            e-mail:
                            <br/>
                            <input type="text" id="e-mail" name="e-mail" onChange={this.changeEmail.bind(this)}/>
                            <br/>
                            &nbsp;
                            <input type="submit" id="submit" value="submit" onClick={this.submitForm.bind(this)}/>
                        </form>
                </div>
        );
    }
}

export default Register;