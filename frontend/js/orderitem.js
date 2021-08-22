import React,{Component} from 'react';
import {Link} from 'react-router-dom';
import '../css/dreamweaver7.css';

let TotalCash = 0;

class BookItem extends Component{
    deleteItem(info){
        fetch("/CartController/deleteCartItem?name=" + info.name,{
            method:"GET",
            mode:"cors",
            credentials:"include"
        })
        .then(function(res){
            if(res.ok){
                TotalCash = TotalCash - info.price;
                window.location.reload();
            }else{
                console.log("request fails,the status is", res.status);
            }})
    }

    render(){
        if(this.props.usage=="cart"){
            return(
                <div className="item7">
                    <div className="img7">
                        <img src={require("../pic/" + this.props.info.img.picUrl + ".jpg")} alt="jingang" height="100%" width="100%" />
                    </div>
                    <div className="info7">
                        <p>
                        name: {this.props.info.name}
                        <br/>
                        price: {this.props.info.price} yuan
                        <br/>   
                        stock: {this.props.info.stock}
                        <br/>
                        <Link to={"/detail/" + this.props.info.name}>click here for more details</Link>
                        </p>
                        <br/>
                        <button className="button72" onClick={this.deleteItem.bind(this,this.props.info)}>delete</button>
                    </div>
                </div>
            );
        }else{
            return(
                <div className="item7">
                    <div className="img7">
                        <img src={require("../pic/" + this.props.info.img.picUrl + ".jpg")} alt="jingang" height="100%" width="100%" />
                    </div>
                    <div className="info7">
                        <p>
                        name: {this.props.info.name}
                        <br/>
                        price: {this.props.info.price} yuan
                        <br/>   
                        author: {this.props.info.author}
                        </p>
                        <br/>
                        <Link to={"/detail/" + this.props.info.name}>click here for more detials</Link>
                    </div>
                </div>
            );
        }
    }
}

class OrderItem extends Component{
    constructor(props){
        super(props);
        this.state={
            orderitem:[]
        }
    }

    componentDidMount(){
        let orderId = this.props.match.params.orderId;

        if(orderId=="cart"){
            fetch("/CartController/showCart",{
                method:"GET",
                mode:"cors",
                credentials:"include"
            })
            .then(res => res.json())
            .then(data => {
                console.log(data)
                this.setState({orderitem: data})
                })
            .catch(e => console.log('error:', e)) 
        }else{
            fetch("/OrderItemController/showOrderItem?orderId=" + this.props.match.params.orderId)
            .then(res => res.json())
            .then(data => {
                console.log(data)
                this.setState({orderitem: data})
                })
            .catch(e => console.log('error:', e)) 
        }
    }

    clearCart(){
        fetch("/CartController/clearCart?TotalCash=" + TotalCash,{
            method:"GET",
            mode:"cors",
            credentials:"include"
        })
        .then(function(res){
            if(res.ok){
                alert("the cart is cleared successfully!")
                window.location.reload();
            }else{
                console.log("request fails,the status is", res.status);
            }})
    }

    render(){
        if(this.props.match.params.orderId=="cart"){
            if(JSON.stringify(this.state.orderitem)=='[]')
            {
                return(
                <div className="container71">
                    <Link to={"/display"} className="return7">return</Link>
                        <div className="container72">
                            <h1 className="empty7">the cart is empty!</h1>
                        </div>
                </div>
                );
            }else{
                return(
                    <div className="container71">
                        <Link to={"/display"} className="return7">return</Link>
                        <div className="container72">
                            {this.state.orderitem.map((i) =>{
                                TotalCash = TotalCash + i.price;
                                return (<BookItem info = {i} usage = {"cart"}/>);
                            })}
                            <p className="TotalCash7">TotalCash: {TotalCash}</p>
                            <button className="button7" onClick={this.clearCart.bind(this)}>clear the cart</button>
                        </div>
                    </div>
                );
            }
        }else{
            return(
                <div className="container71">
                    <Link to={"/ManageDisplay/usage=order"} className="return7">return</Link>
                    <div className="container72">
                        {this.state.orderitem.map((i) =>{
                            TotalCash = TotalCash + i.price;
                            return (<BookItem info = {i} usage = {"orderItem"}/>);
                        })}
                    </div>
                </div>
            );
        }
    }
}

export default OrderItem;