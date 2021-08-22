import React,{Component} from 'react';
import {Link} from 'react-router-dom';
import '../css/dreamweaver6.css';

class SearchBar extends Component{
    render(){
        return(
        <div className="parent6">
            <input type="text" className="search6" id="search" placeholder="search for information:" onChange={this.props.change} />
        </div>
        );
    }
}

class Table extends Component{
    constructor(props){
        super(props);
        this.state={
            add:false,
            IsSales:false,
            IsUserCost:false,
            sales:0,
            cost:0,
            salesBookName:"",
            costUserName:"",
            bookName:"",
            price:"",
            stock:"",
            isbn:"",
            author:"",
            press:"",
            intro:"",
            picUrl:"",
            array:[],
            arrayCp:[]
        }
    }

    banUser(item){
        fetch("/ManagerController/amendUser?username=" + item.first,{
            method:"GET",
            mode:"cors",
            credentials:"include"
        })
        .then(res => {
            if(res.ok){
                window.location.reload();
            }else{
                console.log("request fails,the status is", res.status);
            }
        });
    }

    changeBook(item,col){
        var newValue = prompt("please input new value");
        if(newValue!==null){
            fetch("/ManagerController/amendBook?bookName=" + item.first + 
            "&col=" + col + 
            "&info=" + newValue,{
                method:"GET",
                mode:"cors",
                credentials:"include"
            })
            .then(res => {
                if(res.ok){
                    window.location.reload();
                }else{
                    console.log("request fails,the status is", res.status);
                }
            });
        }
    }

    deleteBook(item){
        var flag = window.confirm("Do you really want to delete this book from database?");
        if(flag){
            fetch("/ManagerController/deleteBook?name=" + item.first,{
                method:"GET",
                mode:"cors",
                credentials:"include"
            })
            .then(res => {
                if(res.ok){
                    alert("the book is removed from database successfully!")
                    window.location.reload();
                }else{
                    console.log("request fails,the status is", res.status);
                }
            });
        }
    }

    addBook(){
        var flag = window.confirm("Do you really want to add a book to your database?");
        if(flag){
            this.setState({
                add:true
            });
        }
    }

    cancel(){
        this.setState({
            add:false,
            IsSales:false,
            IsUserCost:false
        });
    }

    handleChange(e){
        const name = e.target.name;
        this.setState({
            [name]:e.target.value
        });
    }

    sendBook(){
        fetch("/ManagerController/addBook?bookName=" + this.state.bookName + 
        "&price=" + this.state.price + 
        "&stock=" + this.state.stock + 
        "&isbn=" + this.state.isbn + 
        "&author=" + this.state.author + 
        "&press=" + this.state.press + 
        "&intro=" + this.state.intro + 
        "&picUrl=" + this.state.picUrl,{
            method:"GET",
            mode:"cors",
            credentials:"include"
        })
        .then(res => {
            if(res.ok){
                alert("the book is added to database successfully!");
                window.location.reload();
            }else{
                console.log("request fails,the status is", res.status);
            }
        });
    }

    bookSales(item){
        fetch("/OrderItemController/oneBookOrderItem?bookName=" + item.first,{ 
            method:"GET",
            mode:"cors",
            credentials:"include"
        })
            .then(res => res.json())
            .then(data => {
                console.log(data)
                this.setState({array:data,arrayCp:data,IsSales:true,salesBookName:item.first});
                this.salesIncrement(1000000000000,"book");
                })
            .catch(e => console.log('error:', e)) 
    }

    salesIncrement(time,usage){
        let now = new Date().getTime();
        let count = 0;
        this.state.array.map((item) => {
            var str = item.time.substring(0,19);
            str = str.replace(/-/g,'/'); 
            var date = new Date(str).getTime();
            if((now - date)/1000 < time){
                if(usage==="book"){
                    var bookSet = item.bookSet.split(",");
                    for(var i=0;i<bookSet.length;i++){
                        if(bookSet[i]===this.state.salesBookName){
                            count = count + 1;
                        }
                    }
                }else if(usage==="user"){
                    if(item.username===this.state.costUserName){
                        count = count + item.TotalCash;
                    }
                }
            }
        });
        this.setState({
            sales:count,cost:count
        });
    }

    handleBookTime(e){
        if(e.target.id==="year"){
            this.salesIncrement(31536000,"book");
        }else if(e.target.id==="month"){
            this.salesIncrement(2592000,"book");
        }else{
            this.salesIncrement(604800,"book");
        }
    }

    handleUserTime(e){
        if(e.target.id==="year"){
            this.salesIncrement(31536000,"user");
        }else if(e.target.id==="month"){
            this.salesIncrement(2592000,"user");
        }else{
            this.salesIncrement(604800,"user");
        }
    }

    userCost(item){
        fetch("/OrderItemController/oneUserOrderItem?username=" + item.first,{ 
            method:"GET",
            mode:"cors",
            credentials:"include"
        })
            .then(res => res.json())
            .then(data => {
                console.log(data)
                this.setState({array:data,arrayCp:data,IsUserCost:true,costUserName:item.first});
                this.salesIncrement(1000000000000,"user");
                })
            .catch(e => console.log('error:', e)) 
    }

    render(){
        if(this.props.usage == "book"){
            if(this.state.add){
                return(
                    <div>
                        <table className="table">
                            <thead>
                                    <tr onClick={this.addBook.bind(this)}>
                                        <th className="head">bookName</th>
                                        <th className="head">price</th>
                                        <th className="head">stock</th>
                                        <th className="head">isbn</th>
                                        <th className="head">delete</th>
                                    </tr>
                            </thead>
                            <tbody>
                                {this.props.array.map((item) =>{
                                    return(
                                        <tr>
                                            <th onClick={this.bookSales.bind(this,item)}>{item.first}</th>
                                            <th onClick={this.changeBook.bind(this,item,"price")}>{item.second}</th>
                                            <th onClick={this.changeBook.bind(this,item,"stock")}>{item.third}</th>
                                            <th onClick={this.changeBook.bind(this,item,"isbn")}>{item.fourth}</th>
                                            <th onClick={this.deleteBook.bind(this,item)}>-</th>
                                        </tr>
                                    );
                                })}
                            </tbody>
                        </table>
                        <div className="form61">
                            <form>
                                <p>
                                bookName:
                                <br/>
                                <input type="text" onChange={this.handleChange.bind(this)} name="bookName"/>
                                <br/>
                                price:
                                <br/>
                                <input type="text" onChange={this.handleChange.bind(this)} name="price"/>
                                <br/>
                                stock:
                                <br/>
                                <input type="text" onChange={this.handleChange.bind(this)} name="stock"/>
                                <br/>
                                isbn:
                                <br/>
                                <input type="text" onChange={this.handleChange.bind(this)} name="isbn"/>
                                <br/>
                                author:
                                <br/>
                                <input type="text" onChange={this.handleChange.bind(this)} name="author"/>
                                <br/>
                                press:
                                <br/>
                                <input type="text" onChange={this.handleChange.bind(this)} name="press"/>
                                <br/>
                                intro:
                                <br/>
                                <input type="text" onChange={this.handleChange.bind(this)} name="intro"/>
                                <br/>
                                picUrl:
                                <br/>
                                <input type="text" onChange={this.handleChange.bind(this)} name="picUrl"/>
                                <br/>
                                </p>
                                <input type="button" onClick={this.sendBook.bind(this)} value="submit" className="btn61"/>
                                <input typr="button" onClick={this.cancel.bind(this)} value="cancel" className="btn62"/>
                            </form>
                        </div>
                    </div>
                );
            }else if(this.state.IsSales){
                return(
                    <div>
                        <table className="table">
                            <thead>
                                    <tr onClick={this.addBook.bind(this)}>
                                        <th className="head">bookName</th>
                                        <th className="head">price</th>
                                        <th className="head">stock</th>
                                        <th className="head">isbn</th>
                                        <th className="head">delete</th>
                                    </tr>
                            </thead>
                            <tbody>
                                {this.props.array.map((item) =>{
                                    return(
                                        <tr>
                                            <th onClick={this.bookSales.bind(this,item)}>{item.first}</th>
                                            <th onClick={this.changeBook.bind(this,item,"price")}>{item.second}</th>
                                            <th onClick={this.changeBook.bind(this,item,"stock")}>{item.third}</th>
                                            <th onClick={this.changeBook.bind(this,item,"isbn")}>{item.fourth}</th>
                                            <th onClick={this.deleteBook.bind(this,item)}>-</th>
                                        </tr>
                                    );
                                })}
                            </tbody>
                        </table>
                        <div className="form62">
                            <form>
                                <div>
                                    <input type="radio" name="time" id="year" onClick={this.handleBookTime.bind(this)}/>
                                    <label for="year">in a year</label>
                                </div>
                                <br/>
                                <div className="input64">
                                    <input type="radio" name="time" id="month" onClick={this.handleBookTime.bind(this)}/>
                                    <label for="month">in a month</label>
                                </div>
                                <br/>
                                <div>
                                    <input type="radio" name="time" id="week" onClick={this.handleBookTime.bind(this)}/>
                                    <label for="week">in a week</label>
                                </div>
                                <br/>
                                <p>
                                    bookName:{this.state.salesBookName}
                                    <br/>    
                                    sales volume:{this.state.sales}
                                    <br/>
                                    <input typr="button" onClick={this.cancel.bind(this)} value="cancel" className="btn63"/>
                                </p>
                            </form>
                        </div>
                    </div>
                );
            }
            else{
                return(
                    <div>
                        <table className="table">
                            <thead>
                                    <tr onClick={this.addBook.bind(this)}>
                                        <th className="head">bookName</th>
                                        <th className="head">price</th>
                                        <th className="head">stock</th>
                                        <th className="head">isbn</th>
                                        <th className="head">delete</th>
                                    </tr>
                            </thead>
                            <tbody>
                                {this.props.array.map((item) =>{
                                    return(
                                        <tr>
                                            <th onClick={this.bookSales.bind(this,item)}>{item.first}</th>
                                            <th onClick={this.changeBook.bind(this,item,"price")}>{item.second}</th>
                                            <th onClick={this.changeBook.bind(this,item,"stock")}>{item.third}</th>
                                            <th onClick={this.changeBook.bind(this,item,"isbn")}>{item.fourth}</th>
                                            <th onClick={this.deleteBook.bind(this,item)}>-</th>
                                        </tr>
                                    );
                                })}
                            </tbody>
                        </table>
                    </div>
                );
            }
        }else if(this.props.usage === "user"){
            if(this.state.IsUserCost){
                return(
                    <div>
                        <table className="table">
                            <thead>
                                    <tr>
                                        <th className="head">username</th>
                                        <th className="head">password</th>
                                        <th className="head">email</th>
                                        <th className="head">isBanned</th>
                                    </tr>
                            </thead>
                            <tbody>
                                {this.props.array.map((item) =>{
                                    return(
                                        <tr>
                                            <th onClick={this.userCost.bind(this,item)}>{item.first}</th>
                                            <th>{item.second}</th>
                                            <th>{item.third}</th>
                                            <th onClick={this.banUser.bind(this,item)}>{String(item.fourth)}</th>
                                        </tr>
                                    );
                                })}
                            </tbody>
                        </table>
                        <div className="form62">
                            <form>
                                <div>
                                    <input type="radio" name="time" id="year" onClick={this.handleUserTime.bind(this)}/>
                                    <label for="year">in a year</label>
                                </div>
                                <br/>
                                <div className="input64">
                                    <input type="radio" name="time" id="month" onClick={this.handleUserTime.bind(this)}/>
                                    <label for="month">in a month</label>
                                </div>
                                <br/>
                                <div>
                                    <input type="radio" name="time" id="week" onClick={this.handleUserTime.bind(this)}/>
                                    <label for="week">in a week</label>
                                </div>
                                <br/>
                                <p>
                                    userName:{this.state.costUserName}
                                    <br/>    
                                    Total cost:{this.state.cost}
                                    <br/>
                                    <input typr="button" onClick={this.cancel.bind(this)} value="cancel" className="btn63"/>
                                </p>
                            </form>
                        </div>
                    </div>
                );
            }else{
                return(
                    <table className="table">
                        <thead>
                                <tr>
                                    <th className="head">username</th>
                                    <th className="head">password</th>
                                    <th className="head">email</th>
                                    <th className="head">isBanned</th>
                                </tr>
                        </thead>
                        <tbody>
                            {this.props.array.map((item) =>{
                                return(
                                    <tr>
                                        <th onClick={this.userCost.bind(this,item)}>{item.first}</th>
                                        <th>{item.second}</th>
                                        <th>{item.third}</th>
                                        <th onClick={this.banUser.bind(this,item)}>{String(item.fourth)}</th>
                                    </tr>
                                );
                            })}
                        </tbody>
                    </table>
                );
            }
        }else if(this.props.usage == "order"){
            return(
                <table className="table">
                    <thead>
                            <tr>
                                <th className="head">orderId</th>
                                <th className="head">username</th>
                                <th className="head">time</th>
                                <th className="head">TotalCash</th>
                            </tr>
                    </thead>
                    <tbody>
                        {this.props.array.map((item) =>{
                            return(
                                <tr>
                                    <th>{item.first}</th>
                                    <th>{item.second}</th>
                                    <th>{item.third}</th>
                                    <th>{item.fourth}</th>
                                </tr>
                            );
                        })}
                    </tbody>
                </table>
            );
        }else if(this.props.usage == "userOrder"){
            return(
                <div>
                    <table className="table">
                        <thead>
                                <tr>
                                    <th className="head">orderId</th>
                                    <th className="head">time</th>
                                    <th className="head">TotalCash</th>
                                    <th className="head">details</th>
                                </tr>
                        </thead>
                        <tbody>
                            {this.props.array.map((item) =>{
                                return(
                                    <tr>
                                        <th>{item.first}</th>
                                        <th>{item.third}</th>
                                        <th>{item.fourth}</th>
                                        <th><Link to={"/orderItem/" + item.first}>click here</Link></th>
                                    </tr>
                                );
                            })}
                        </tbody>
                    </table>
                </div>
            );
        }
    }
}

class ManageDisplay extends Component{
    constructor(props){
        super(props);
        this.state={
            array:[],
            arrayCp:[]
        }
    }

    componentDidMount(){
        if(this.props.match.params.usage=="userOrder")
        {
            fetch("/OrderController/showOrderList",{
                method:"GET",
                mode:"cors",
                credentials:"include"
            })
                .then(res => res.json())
                .then(data => {
                    console.log(data)
                    this.setState({array:data,arrayCp:data});
                    })
                .catch(e => console.log('error:', e)) 
        }else{
            fetch("/ManagerController/" + this.props.match.params.usage,{ 
                method:"GET",
                mode:"cors",
                credentials:"include"
            })
                .then(res => res.json())
                .then(data => {
                    console.log(data)
                    this.setState({array:data,arrayCp:data});
                    })
                .catch(e => console.log('error:', e)) 
        }
    }

    HandleChange(){
        let content = document.getElementById("search").value;
        let list;
        if(this.props.match.params.usage==="order"){
            list = this.state.arrayCp.filter((item) => {
            return item.second.indexOf(content) !== -1;})
        }else{
		    list = this.state.arrayCp.filter((item) => {
            return item.first.indexOf(content) !== -1;})
        }
		this.setState({
            array: list
		})
    }

    handleTime(e){
        let now = new Date().getTime();
        let list;
        if(e.target.id==="year"){
            list = this.state.arrayCp.filter((item) =>{
                var str = item.third.substring(0,19);
                str = str.replace(/-/g,'/'); 
                var date = new Date(str).getTime();
                return (now - date)/1000 < 31536000;
            })
        }else if(e.target.id==="month"){
            list = this.state.arrayCp.filter((item) =>{
                var str = item.third.substring(0,19);
                str = str.replace(/-/g,'/'); 
                var date = new Date(str).getTime();
                return (now - date)/1000 < 2592000;
            })
        }else{
            list = this.state.arrayCp.filter((item) =>{
                var str = item.third.substring(0,19);
                str = str.replace(/-/g,'/'); 
                var date = new Date(str).getTime();
                return (now - date)/1000 < 604800;
            })
        }
        this.setState({
            array: list
		})
    }

    render(){
        if(this.props.match.params.usage=="userOrder"){
            return(
                <div className="container61">
                    <Link to={"/book-display"} className="return">return</Link>
                    <div className="container62">
                    <form>
                        <div className="input61">
                            <input type="radio" name="time" id="year" onClick={this.handleTime.bind(this)}/>
                            <label for="year">in a year</label>
                        </div>
                        <div className="input62">
                            <input type="radio" name="time" id="month" onClick={this.handleTime.bind(this)}/>
                            <label for="month">in a month</label>
                        </div>
                        <div className="input63">
                            <input type="radio" name="time" id="week" onClick={this.handleTime.bind(this)}/>
                            <label for="week">in a week</label>
                        </div>
                    </form>
                        <Table usage={this.props.match.params.usage} array={this.state.array}/>
                    </div>
                </div>
            );
        }else{
                return(
                    <div className="container61">
                        <Link to={"/admanager"} className="return">return</Link>
                        <div className="container62">
                            <SearchBar change={this.HandleChange.bind(this)} className="SearchBar6"/>
                            <Table usage={this.props.match.params.usage} array={this.state.array} className="table6"/>
                        </div>
                    </div>
                );
        }
    }
} 

export default ManageDisplay;