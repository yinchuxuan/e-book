import React from 'react';
import ReactDOM from 'react-dom';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom'
import Mainpage from './js/main-page';
import BookSystem from './js/display';
import Book from './js/book';
import Register from './js/register';
import Admanager from './js/admanager';
import ManageDisplay from './js/ManageDisplay';
import OrderItem from './js/orderitem';

ReactDOM.render(
    <Router>
        <Switch>
            <Route exact path='/' component={Mainpage}/>
            <Route path='/display' component={BookSystem}/>
            <Route path='/detail/:name' component={Book}/>
            <Route path='/register' component={Register}/>
            <Route path='/admanager' component={Admanager}/>
            <Route path='/ManageDisplay/:usage' component={ManageDisplay}/>
            <Route path='/orderItem/:orderId' component={OrderItem}/>
         </Switch>
    </Router>,
    document.getElementById('root')
);
