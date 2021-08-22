    import React, { Component } from 'react';
import {Link} from 'react-router-dom';
import '../css/dreamweaver3.css';

class Book extends Component{
    constructor(props){
        super(props)
        this.state={
            book:{}
        }
    }

    componentDidMount(){
        let name = this.props.match.params.name;

        fetch("/DetailController/showDetail?name=" + name,
        {
            method:"GET",
            mode:"cors"
        })
        .then(res => res.json())
        .then(data => {
            console.log(data)
            this.setState({book: data})
            })
        .catch(e => console.log('error:', e)) 
    }

    jumpToCart(){
        let name = this.props.match.params.name;

        fetch("/CartController/joinCart?name=" + name,{credentials:"include"})
        .then(res => {
            if(res.ok){
                window.location.href="/orderItem/cart";
            }else{
                document.write("the request is failed!");
            }
        }).catch(e => console.log('error:', e)) 
    }

    produceInfo(book){
        if(JSON.stringify(book)=='{}'){
            return <h>no book</h>
        }else{
            return(
                    <div className="info3">
                        <div className="img3">
                            <img src={require("../pic/" + book.img.picUrl + ".jpg")} alt={book.name} width="100%" height="100%"/>
                        </div>
                        <div className="detail3">
                            <ul>
                                <li>name:{book.name}</li>
                                <li>author:{book.author}</li>
                                <li>ISBN:{book.isbn}</li>
                                <li>price:{book.price}</li>
                                <li>stock:{book.stock}</li>
                                <li>intro:{book.intro}</li>
                                <br/>
                                <button className="button3" onClick={this.jumpToCart.bind(this)}>add to your shopping cart</button>
                            </ul>
                        </div> 
                    </div>
            );
        }
    }

    render(){
        return(
            <div className="container3">
                <Link to={"/display"} className="return">return</Link>
                {this.produceInfo(this.state.book)}
            </div>
        );
    }
}

export default Book;