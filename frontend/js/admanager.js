import React,{Component} from 'react';
import {Link} from 'react-router-dom';
import '../css/dreamweaver5.css';

class Admanager extends Component{
    render(){
        return(
            <div className = "container51">
                <Link to={"/"}>return</Link>
                <div className = "container52">
                    <Link to={"/ManageDisplay/book"}>book-manager</Link>
                    <br/>
                    <Link to={"/ManageDisplay/user"}>user-manager</Link>
                    <br/>
                    <Link to={"/ManageDisplay/order"}>order-manager</Link>
                    <br/>
                    <Link to={"/display"}>book-display</Link>
                </div>
            </div>
        );
    }
}

export default Admanager;