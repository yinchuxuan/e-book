import React,{Component} from 'react';
import '../css/dreamweaver1.css';
import {Redirect} from 'react-router-dom';

class Mainpage extends Component{
    constructor(props){
        super(props);
        this.state={
            username:"",
            password:""
        }
    }

    ChangeName(e){
        this.setState({username:e.target.value});
    }

    ChangePassword(e){
        this.setState({password:e.target.value});
    }

    Register(e){
        window.location.href="/register";
    }

    submitForm(){
        fetch("/LoginController/login?username=" + this.state.username + "&password=" + this.state.password,
        {
            method:"GET",
            mode:"cors",
            credentials:"include"
        })
        .then(function(res){
            if(res.ok){
                return res.json();
            }else{
                console.log("request fails,the status is", res.status);
            }
        }).then(function(json){
            if(json.error==="noError"){
                if(json.isAd){
                    window.location.href="/admanager";
                    window.event.returnValue=false;
                }else{
                    window.location.href="/display";
                    window.event.returnValue=false;
                }
            }else if(json.error==="username"){
                alert("the username does not exist!");
            }else if(json.error==="password"){
                alert("the password is not correct!");
            }else if(json.error==="isBanned"){
                alert("this user is banned!");
            }
        }).catch(e => console.log('error:', e)) 
    }

    render(){
        return(
            <div id="container">
                <div id="form">
                    <form>
                        User's name:
                        <br/>
                        <input type="text" name="username" onChange={this.ChangeName.bind(this)}/>
                        <br/>
                        Password:
                        <br/>
                        <input type="password" name="password" onChange={this.ChangePassword.bind(this)} />
                        <br/>
                        <br/>
                        <input type="submit" name="login" value="login" onClick={this.submitForm.bind(this)}/>
                        &nbsp;
                        &nbsp;
                        &nbsp;
                        <input type="button" name="register" value="register" onClick = {this.Register.bind(this)}/>
                        <br/>
                    </form>
                </div>
            </div>
        );
    }
}

export default Mainpage;
