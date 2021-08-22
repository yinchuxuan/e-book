import React,{Component} from 'react';
import {Link} from 'react-router-dom';
import '../css/dreamweaver2.css';

class Card extends Component{ 
    render(){
        if(this.props.index%2 === 0){
            return(
            <div className="card-left" key={this.props.index}>
                    <div className="img2">
                        <img src={require('../pic/' + this.props.book.img.picUrl + '.jpg')} alt={this.props.book.name} height="100%" width="100%" />
                    </div>
                    <div className="info2">
                        <p>
                            {this.props.book.name}
                            <br/>
                            price: {this.props.book.price} yuan
                        </p>
                        <Link to={"/detail/" + this.props.book.name}>more details</Link>
                    </div>
                </div>
            );
        }
        else{
            return(
                <div className="card-right" key={this.props.index}>
                        <div className="img2">
                            <img src={require('../pic/' + this.props.book.img.picUrl + '.jpg')} alt={this.props.book.name} height="100%" width="100%" />
                        </div>
                        <div className="info2">
                            <p>
                                {this.props.book.name}
                                <br/>
                                price: {this.props.book.price} yuan
                            </p>
                            <Link to={"/detail/" + this.props.book.name}>more details</Link>
                        </div>
                    </div>
                );
        }
    }
}

class SearchBar extends Component{
    render(){
        return(
        <div className="parent2">
            <input type="text" className="search2" id="search" placeholder="search for book:" onChange={this.props.change} />
            <div className="myorder">
                <Link to={"/ManageDisplay/userOrder"}>myorder</Link>
            </div>
            <div className="cart">
                <Link to={"/orderItem/cart"}>cart</Link>
            </div>
        </div>
        );
    }
}

class BookDisplay extends Component{
    render(){
            return(
                <div className="bookDisplay">
                    {this.props.books.slice(0,4).map((item) => {
                       return <Card book={item} index={this.props.books.indexOf(item)}/>;
                    })}
                </div>
        );
    }
}

class BookSystem extends Component{
    constructor(props)
    {
        super(props);
        this.state={
            Books:[],
            BooksCp:[],
            isSearching:false,
        };
    }

    HandleChange(){
        let content = document.getElementById("search").value;
		let list = this.state.BooksCp.filter((item) => {
			return item.name.indexOf(content) !== -1
		})
		this.setState({
            Books: list,
            isSearching: true
		})
    }

    componentDidMount(){
        fetch("/DisplayController/display",{
            method:"GET",
            mode:"cors",
            credentials:"include"
        })
        .then(res => res.json())
        .then(data => {
            console.log(data)
            this.setState({Books: data})
            this.setState({BooksCp: data})
            })
        .catch(e => console.log('error:', e))
    }

    render(){
        return(
            <div className="container21">
                <div className="container22">
                    <div>
                        <SearchBar change={this.HandleChange.bind(this)}/>
                        <BookDisplay books={this.state.Books}/>
                    </div>  
                </div>
            </div>
        );
    }
}

export default BookSystem;

